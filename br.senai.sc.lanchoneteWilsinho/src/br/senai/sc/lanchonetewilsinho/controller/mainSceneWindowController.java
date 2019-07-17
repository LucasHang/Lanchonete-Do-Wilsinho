/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.controller;

import br.senai.sc.lanchonetewilsinho.BrSenaiScLanchoneteWilsinho;
import br.senai.sc.lanchonetewilsinho.DoWork;
import br.senai.sc.lanchonetewilsinho.MeuAlerta;
import br.senai.sc.lanchonetewilsinho.dao.DAOFactory;
import br.senai.sc.lanchonetewilsinho.model.Funcionario;
import com.sun.javafx.cursor.CursorFrame;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import static javax.management.Query.lt;

/**
 * FXML Controller class
 *
 * @author Bratva
 */
public class mainSceneWindowController implements Initializable {

    @FXML
    private Button btnEntrar;
    @FXML
    private PasswordField passFieldLogin;
    @FXML
    private TextField txtFieldLogin;
    @FXML
    private Label lblCapsAlert;
    @FXML
    private AnchorPane paneLogin;
    @FXML
    private Label lblSenha;
    @FXML
    private Label lblLogin;

    /**
     * Initializes the controller class.
     */
    Funcionario funcionario;
    static Funcionario funcLogado;
    boolean capsOn;
    DoWork task;
    Thread tredi;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        capsOn = Toolkit.getDefaultToolkit().getLockingKeyState(20);
        lblCapsAlert.setVisible(capsOn);
        
        addEffectEvent();

    }

    @FXML
    private void btnEntrarOnAction(ActionEvent event) {
        task = new DoWork();
        tredi = new Thread(task);
        tredi.start();

        try {
            if (loginVrified()) {
                funcLogado = funcionario;
            } else {
                task.cancel();

                throw new SQLException("Senha Incorreta");
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }

        task.setOnRunning(ev -> {
            paneLogin.setCursor(Cursor.WAIT);

        });

        task.setOnSucceeded(ev -> {
            paneLogin.setCursor(Cursor.DEFAULT);
            try {
                BrSenaiScLanchoneteWilsinho.mudarTela("menu");

            } catch (IOException ex) {
                Logger.getLogger(mainSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
            }
        });

    }

    private Boolean loginVrified() throws SQLException {

        funcionario = DAOFactory.getFuncionarioDAO().getFuncionarioByLogin(txtFieldLogin.getText());

        if (funcionario != null) {
            return funcionario.getSenha().equals(passFieldLogin.getText());
        } else {
            task.cancel();
            try {
                tredi.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(mainSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
            }
            throw new SQLException("Funcionário não encontrado");
        }

    }

    @FXML
    private void ENTER(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            btnEntrarOnAction(null);

        }
    }

    @FXML
    private void CAPSLOCK(KeyEvent event) {
        if (event.getCode() == KeyCode.CAPS) {
            capsOn = !capsOn;
            lblCapsAlert.setVisible(capsOn);
        }
    }

    private void addEffectEvent(){
        DoWork.createButtonEffectEvent(btnEntrar, "buttonEffect");
        DoWork.createFieldEffectEvent(txtFieldLogin, "textfieldEffect");
        DoWork.createFieldEffectEvent(passFieldLogin, "textfieldEffect");
    }

}
