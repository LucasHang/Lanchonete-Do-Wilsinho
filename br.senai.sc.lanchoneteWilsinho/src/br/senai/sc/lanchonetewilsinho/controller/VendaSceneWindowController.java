/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senai.sc.lanchonetewilsinho.controller;

import br.senai.sc.lanchonetewilsinho.MeuAlerta;
import br.senai.sc.lanchonetewilsinho.dao.DAOFactory;
import br.senai.sc.lanchonetewilsinho.model.Cliente;
import br.senai.sc.lanchonetewilsinho.model.Funcionario;
import br.senai.sc.lanchonetewilsinho.model.Item_Venda;
import br.senai.sc.lanchonetewilsinho.model.Produto;
import br.senai.sc.lanchonetewilsinho.model.Venda;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author Bratva
 */
public class VendaSceneWindowController implements Initializable {

    @FXML
    private ComboBox<Integer> comboFuncionario;
    @FXML
    private ComboBox<Integer> comboCliente;
    @FXML
    private ComboBox<Integer> comboProduto;
    @FXML
    private Button btnSalvarItem;
    @FXML
    private TextField txtQtdProduto;
    @FXML
    private Label labelValorTotal;
    @FXML
    private Button btnFinalizarCompra;
    @FXML
    private TableView<Venda> tableVendas;
    @FXML
    private TextField txtCarregar;
    @FXML
    private Button btnCadastrarVenda;
    @FXML
    private TableView<Item_Venda> tableItems;
    @FXML
    private TableColumn<Funcionario, Integer> tblColumnFuncionario;
    @FXML
    private TableColumn<Cliente, Integer> tblColumnCliente;
    @FXML
    private TableColumn<Cliente, Integer> tblColumnCpf;
    @FXML
    private TableColumn<Venda, Double> tblColumnValor;
    @FXML
    private TableColumn<Venda, Integer> tblColumnData;
    @FXML
    private TableColumn<Item_Venda, Integer> tblColumnProduto;
    @FXML
    private Button btnAdicionarItem;

    /**
     * Initializes the controller class.
     */
    Venda novaVenda = null;
    Venda vendaSelecionada = null;
    Item_Venda novoItem = null;
    Item_Venda itemSelecionado = null;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            fillComboBoxes();
        } catch (SQLException ex) {
            Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mascararComboBox();

        btnCarregarOnAction(null);

        mascararTableView();

        tableVendas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            disableFields(false);
            unbindFieldsVenda(oldValue);
            bindFieldsVenda(newValue);
            vendaSelecionada = newValue;
        });

    }

    @FXML
    private void btnAdicionarItemOnAction(ActionEvent event) {
        novoItem = new Item_Venda();
        tableItems.getItems().add(novoItem);
        bindFieldsItem_Venda(novoItem);
    }

    @FXML
    private void btnSalvarItemOnAction(ActionEvent event) {
        unbindFieldsItem_Venda(novoItem);
        try {
            novoItem.setValorItem();
        } catch (SQLException ex) {
            Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
        novaVenda.getItens().add(novoItem);
        novaVenda.setValorTotalCompra();
        clearFields("itemvenda");
    }

    @FXML
    private void btnFinalizarCompraOnAction(ActionEvent event) {
        unbindFieldsVenda(novaVenda);
        unbindFieldsVenda(vendaSelecionada);

        unbindFieldsItem_Venda(novoItem);
        unbindFieldsItem_Venda(itemSelecionado);

        try {
            if (novaVenda != null) {
                novaVenda.setValorTotalCompra();
                DAOFactory.getVendaDAO().save(novaVenda);
            } else {
                if (vendaSelecionada != null) {
                    DAOFactory.getVendaDAO().update(vendaSelecionada);
                }
            }
            btnCarregarOnAction(null);
            tableItems.setItems(null);
            clearFields("venda");
            disableFields(true);
        } catch (SQLException ex) {
            Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
    }

    @FXML
    private void btnCarregarOnAction(ActionEvent event) {
        try {
            tableVendas.setItems(FXCollections.observableArrayList(DAOFactory.getVendaDAO().getAll()));
        } catch (SQLException ex) {
            Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
    }

    @FXML
    private void btnCadastrarVendaOnAction(ActionEvent event) {
        disableFields(false);
        novaVenda = new Venda();
        novoItem = new Item_Venda();
        tableItems.getItems().add(novoItem);
        bindFieldsVenda(novaVenda);
        bindFieldsItem_Venda(novoItem);
    }

    private Integer takeActualDate() {

        Date date = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy/MM/dd");
        List<String> anoMesDia = Arrays.asList(dataFormat.format(date).split("/"));
        String dataNumeric = new String();
        for (String value : anoMesDia) {
            dataNumeric = dataNumeric + value;
        }

        return parseInt(dataNumeric);
    }

    private void fillComboBoxes() throws SQLException {
        FXCollections.observableArrayList(DAOFactory.getProdutoDAO().getAll()).forEach(produto -> {
            comboProduto.getItems().add(produto.getCodigo());
        });
        FXCollections.observableArrayList(DAOFactory.getClienteDAO().getAll()).forEach(cliente -> {
            comboCliente.getItems().add(cliente.getCodigo());
        });

        FXCollections.observableArrayList(DAOFactory.getFuncionarioDAO().getAll()).forEach(funcionario -> {
            comboFuncionario.getItems().add(funcionario.getCodigo());
        });

    }

    private void mascararTableView() {
        tblColumnFuncionario.setCellFactory((TableColumn<Funcionario, Integer> param) -> {
            TableCell cell = new TableCell<Funcionario, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(null);
                    setGraphic(null);
                    if (!empty) {
                        if (item == null || item == 0) {
                            setText("Esperando");
                        } else {
                            try {
                                Funcionario funcionario = DAOFactory.getFuncionarioDAO().getFuncionarioByCodigo(item);
                                setText(funcionario.getNome() + "(" + funcionario.getLogin() + ")");
                            } catch (SQLException ex) {
                                Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                            }

                        }
                    }
                }

                @Override
                public void updateSelected(boolean upd) {
                    super.updateSelected(upd);
                }

                private String getString() {
                    return getItem() == null ? "" : getItem().toString();
                }
            };
            return cell;
        });

        tblColumnCliente.setCellFactory((TableColumn<Cliente, Integer> param) -> {
            TableCell cell = new TableCell<Cliente, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(null);
                    setGraphic(null);
                    if (!empty) {
                        if (item == null || item == 0) {
                            setText("Esperando");
                        } else {
                            try {
                                setText(DAOFactory.getClienteDAO().getClienteByCodigo(item).getNome());
                            } catch (SQLException ex) {
                                Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                            }

                        }
                    }
                }

                @Override
                public void updateSelected(boolean upd) {
                    super.updateSelected(upd);
                }

                private String getString() {
                    return getItem() == null ? "" : getItem().toString();
                }
            };
            return cell;
        });

        tblColumnCpf.setCellFactory((TableColumn<Cliente, Integer> param) -> {
            TableCell cell = new TableCell<Cliente, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(null);
                    setGraphic(null);
                    if (!empty) {
                        if (item == null || item == 0) {
                            setText("Esperando");
                        } else {
                            try {
                                setText(DAOFactory.getClienteDAO().getClienteByCodigo(item).getCpf());
                            } catch (SQLException ex) {
                                Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                            }

                        }
                    }
                }

                @Override
                public void updateSelected(boolean upd) {
                    super.updateSelected(upd);
                }

                private String getString() {
                    return getItem() == null ? "" : getItem().toString();
                }
            };
            return cell;
        });

        tblColumnValor.setCellFactory((TableColumn<Venda, Double> param) -> {
            TableCell cell = new TableCell<Venda, Double>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(null);
                    setGraphic(null);
                    if (!empty) {
                        if (item == null || item == 0) {
                            setText("Esperando");
                        } else {

                            String valor = "R$ " + item;
                            setText(valor);

                        }
                    }
                }

                @Override
                public void updateSelected(boolean upd) {
                    super.updateSelected(upd);
                }

                private String getString() {
                    return getItem() == null ? "" : getItem().toString();
                }
            };
            return cell;
        });

        tblColumnData.setCellFactory((TableColumn<Venda, Integer> param) -> {
            TableCell cell = new TableCell<Venda, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(null);
                    setGraphic(null);
                    if (!empty) {
                        if (item == null || item == 0) {
                            setText("Esperando");
                        } else {
                            String dataString = "";
                            List<String> aux = Arrays.asList(item.toString().split(""));
                            for (String num : aux.subList(6, 8)) {
                                dataString += num;
                            }
                            dataString += "/";
                            for (String num : aux.subList(4, 6)) {
                                dataString += num;
                            }
                            dataString += "/";
                            for (String num : aux.subList(0, 4)) {
                                dataString += num;
                            }
                            setText(dataString);

                        }
                    }
                }

                @Override
                public void updateSelected(boolean upd) {
                    super.updateSelected(upd);
                }

                private String getString() {
                    return getItem() == null ? "" : getItem().toString();
                }
            };
            return cell;
        });

        tblColumnProduto.setCellFactory((TableColumn<Item_Venda, Integer> param) -> {
            TableCell cell = new TableCell<Item_Venda, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(null);
                    setGraphic(null);
                    if (!empty) {
                        if (item == null || item == 0) {
                            setText("Esperando");
                        } else {
                            try {
                                setText(DAOFactory.getProdutoDAO().getProdutoByCodigo(item).getDescricaoProd());
                            } catch (SQLException ex) {
                                Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                            }

                        }
                    }
                }

                @Override
                public void updateSelected(boolean upd) {
                    super.updateSelected(upd);
                }

                private String getString() {
                    return getItem() == null ? "" : getItem().toString();
                }
            };
            return cell;
        });
    }
    
        

    private void mascararComboBox() {

        comboProduto.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {
            @Override
            public ListCell<Integer> call(ListView<Integer> l) {
                return new ListCell<Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            try {
                                setText(item.toString()+"-"+DAOFactory.getProdutoDAO().getProdutoByCodigo(item).getDescricaoProd());
                            } catch (SQLException ex) {
                                Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                            }
                        }
                    }
                };
            }
        });

        comboProduto.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                if(object != null && object != 0){
                    try {
                        return object.toString()+"-" + DAOFactory.getProdutoDAO().getProdutoByCodigo(object).getDescricaoProd();
                    } catch (SQLException ex) {
                        Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                    }
                }
                
                return null;
            }

            @Override
            public Integer fromString(String string) {
                if(string != null && !string.isEmpty()){
                    return Integer.parseInt(string.substring(0,0));
                }
                return null;
            }
        });

        comboFuncionario.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {
            @Override
            public ListCell<Integer> call(ListView<Integer> l) {
                return new ListCell<Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            try {
                                setText(item.toString()+"-"+DAOFactory.getFuncionarioDAO().getFuncionarioByCodigo(item).getLogin());
                            } catch (SQLException ex) {
                                Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                            }
                        }
                    }
                };
            }
        });

        comboFuncionario.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                if(object != null && object != 0){
                    try {
                        return object.toString()+"-" + DAOFactory.getFuncionarioDAO().getFuncionarioByCodigo(object).getLogin();
                    } catch (SQLException ex) {
                        Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                    }
                }
                return null;
            }

            @Override
            public Integer fromString(String string) {
                if(string != null && !string.isEmpty()){
                  
                    return Integer.parseInt(string.substring(0,0));
                    
                } 
                return null;
            }
        });

        comboCliente.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {
            @Override
            public ListCell<Integer> call(ListView<Integer> l) {
                return new ListCell<Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            try {
                                setText(DAOFactory.getClienteDAO().getClienteByCodigo(item).getCpf()+"-"+DAOFactory.getClienteDAO().getClienteByCodigo(item).getNome());
                            } catch (SQLException ex) {
                                Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                            }
                        }
                    }
                };
            }
        });

        comboCliente.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                if(object != null && object != 0){
                    try {
                        return DAOFactory.getClienteDAO().getClienteByCodigo(object).getCpf() + "-" + DAOFactory.getClienteDAO().getClienteByCodigo(object).getNome();
                    } catch (SQLException ex) {
                        Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                    }
                }
                
                return null;
            }

            @Override
            public Integer fromString(String string) {
                if(string != null && !string.isEmpty()){
                    return Integer.parseInt(string.substring(0,10));
                }
                
                return null;
            }
        });
    }

    private void bindFieldsVenda(Venda venda) {
        if (venda != null) {
            comboCliente.valueProperty().bindBidirectional(venda.clienteProperty().asObject());
            comboFuncionario.valueProperty().bindBidirectional(venda.funcionarioProperty().asObject());
            labelValorTotal.textProperty().bindBidirectional(venda.valorTotalCompraProperty(), new NumberStringConverter());
        }

    }

    private void unbindFieldsVenda(Venda venda) {
        if (venda != null) {
            comboCliente.valueProperty().unbindBidirectional(venda.clienteProperty().asObject());
            comboFuncionario.valueProperty().unbindBidirectional(venda.funcionarioProperty().asObject());
            labelValorTotal.textProperty().bindBidirectional(venda.valorTotalCompraProperty(), new NumberStringConverter());
        }
    }

    private void bindFieldsItem_Venda(Item_Venda itemvenda) {
        if (itemvenda != null) {
            txtQtdProduto.textProperty().bindBidirectional(itemvenda.qtdCompradaProperty(), new NumberStringConverter());
            comboProduto.valueProperty().bindBidirectional(itemvenda.produtoProperty().asObject());

        }

    }

    private void unbindFieldsItem_Venda(Item_Venda itemvenda) {
        if (itemvenda != null) {
            txtQtdProduto.textProperty().unbindBidirectional(itemvenda.qtdCompradaProperty());
            comboProduto.valueProperty().unbindBidirectional(itemvenda.produtoProperty().asObject());
        }
    }

    private void disableFields(Boolean value) {
        comboCliente.setDisable(value);
        comboFuncionario.setDisable(value);
        comboProduto.setDisable(value);
        txtQtdProduto.setDisable(value);

    }

    public Boolean validationForm() {
        Boolean invalido = false;

        if (comboCliente.valueProperty().isNull().get()) {
            comboCliente.getStyleClass().add("invalido");
            invalido = true;
        } else {
            comboCliente.getStyleClass().remove("invalido");
        }

        if (comboFuncionario.valueProperty().isNull().get()) {
            comboFuncionario.getStyleClass().add("invalido");
            invalido = true;
        } else {
            comboFuncionario.getStyleClass().remove("invalido");
        }

        if (comboProduto.valueProperty().isNull().get()) {
            comboProduto.getStyleClass().add("invalido");
            invalido = true;
        } else {
            comboProduto.getStyleClass().remove("invalido");
        }

        if (txtQtdProduto.textProperty().isNull().get()) {
            txtQtdProduto.getStyleClass().add("invalido");
            invalido = true;
        } else {
            txtQtdProduto.getStyleClass().remove("invalido");
        }

        return invalido;
    }

    private void clearFields(String tipo) {
        switch (tipo) {
            case "venda":
                comboCliente.setValue(null);
                comboFuncionario.setValue(null);
                comboProduto.setValue(null);
                txtQtdProduto.clear();
                break;

            case "itemvenda":
                comboProduto.setValue(null);
                txtQtdProduto.clear();
                break;
        }

    }

}
