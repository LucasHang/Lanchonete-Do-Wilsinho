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
import br.senai.sc.lanchonetewilsinho.model.Cliente;
import br.senai.sc.lanchonetewilsinho.model.Funcionario;
import br.senai.sc.lanchonetewilsinho.model.Item_Venda;
import br.senai.sc.lanchonetewilsinho.model.Produto;
import br.senai.sc.lanchonetewilsinho.model.Venda;
import java.awt.Color;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.security.Provider.Service;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private TableColumn<Venda, Integer> tblColumnHora;
    @FXML
    private TableColumn<Item_Venda, Integer> tblColumnProduto;
    @FXML
    private Button btnAdicionarItem;
    @FXML
    private Button btnRemover;
    @FXML
    private Button btnCancelarAcao;
    @FXML
    private TextField txtValorTotal;
    @FXML
    private AnchorPane paneVenda;

    /**
     * Initializes the controller class.
     */
    Venda novaVenda = null;
    Venda vendaSelecionada = null;
    Item_Venda novoItem = null;
    Item_Venda itemSelecionado = null;
    Boolean estoqueInsuficiente;

    DoWork task;
    Thread tredi;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            fillComboBoxes("all");
        } catch (SQLException ex) {
            Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
        mascararComboBox();

        btnCarregarOnAction(null);

        mascararTableView();

        addListenner();

    }

    @FXML
    private void btnAdicionarItemOnAction(ActionEvent event) {
        if(novoItem == null){
           novoItem = new Item_Venda(); 
        }
        bindFieldsItem_Venda(novoItem);
    }

    @FXML
    private void btnSalvarItemOnAction(ActionEvent event) {
        try {
            qtdProdutoEmEstoque(novoItem);
        } catch (RuntimeException ex) {
            Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
        if (estoqueInsuficiente) {
            return;
        }

        unbindFieldsItem_Venda(novoItem);
        try {
            novoItem.calculaValorItem();
        } catch (SQLException ex) {
            Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
        tableItems.getItems().add(novoItem);
        novaVenda.getItens().add(novoItem);
        novaVenda.calculaValorTotalCompra();
        novoItem = null;
        clearFields("itemvenda");
    }

    @FXML
    private void btnFinalizarCompraOnAction(ActionEvent event) {
        unbindFieldsVenda(novaVenda);
        unbindFieldsVenda(vendaSelecionada);

        unbindFieldsItem_Venda(novoItem);
        unbindFieldsItem_Venda(itemSelecionado);

        task = new DoWork();
        tredi = new Thread(task);
        tredi.start();

        try {

            if (novaVenda != null) {
                novaVenda.setDataVenda(takeActualDate("date"));
                novaVenda.setHoraVenda(takeActualDate("hour"));
                novaVenda.calculaValorTotalCompra();
                DAOFactory.getVendaDAO().save(novaVenda);
            } else {
                if (vendaSelecionada != null) {
                    DAOFactory.getVendaDAO().update(vendaSelecionada);
                }else{
                    task.cancel();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }

        task.setOnRunning(ev -> {
            paneVenda.setCursor(Cursor.WAIT);

        });

        task.setOnSucceeded(ev -> {
            try {
                btnCarregarOnAction(null);
                btnCancelarAcaoOnAction(null);
                fillComboBoxes("produto");
            } catch (SQLException ex) {
                Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
            }
            paneVenda.setCursor(Cursor.DEFAULT);
        });

    }

    @FXML
    private void btnCarregarOnAction(ActionEvent event) {
        try {
            if (txtCarregar.getText().isEmpty()) {
                tableVendas.setItems(FXCollections.observableArrayList(DAOFactory.getVendaDAO().getAll()));
            } else {
                List<Venda> vendas = DAOFactory.getVendaDAO().procurarVenda(txtCarregar.getText());
                if (!vendas.isEmpty()) {
                    tableVendas.setItems(FXCollections.observableArrayList(vendas));
                } else {
                    tableVendas.setItems(FXCollections.observableArrayList(DAOFactory.getVendaDAO().getVendaByData(txtCarregar.getText())));
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();

        }

    }

    @FXML
    private void btnCadastrarVendaOnAction(ActionEvent event) {
        disableFields(false);
        limpaTableItems();
        unbindFieldsItem_Venda(itemSelecionado);
        unbindFieldsVenda(vendaSelecionada);
        vendaSelecionada = null;
        itemSelecionado = null;
        if(novaVenda == null && novoItem == null){
            novaVenda = new Venda();
            novoItem = new Item_Venda();
        }
        bindFieldsVenda(novaVenda);
        bindFieldsItem_Venda(novoItem);
    }

    @FXML
    private void btnRemoverOnAction(ActionEvent event) {

        task = new DoWork();
        tredi = new Thread(task);
        tredi.start();

        task.setOnRunning(ev -> {
            paneVenda.setCursor(Cursor.WAIT);
            if (vendaSelecionada != null) {

                try {
                    vendaSelecionada.getItens().forEach(item -> {
                        try {
                            DAOFactory.getItem_vendaDAO().delete(item);
                        } catch (SQLException ex) {
                            Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                        }
                    });
                    DAOFactory.getVendaDAO().delete(vendaSelecionada);

                } catch (SQLException ex) {
                    Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                }
            }
        });

        task.setOnSucceeded(ev -> {
            tableVendas.getItems().remove(vendaSelecionada);
            paneVenda.setCursor(Cursor.DEFAULT);
        });

    }

    @FXML
    private void btnCancelarAcaoOnAction(ActionEvent event) {
        clearFields("venda");
        clearFields("itemvenda");
        disableFields(true);
        novoItem = null;
        itemSelecionado = null;
        novaVenda = null;
        vendaSelecionada = null;
        limpaTableItems();
    }

    public void qtdProdutoEmEstoque(Item_Venda item) throws RuntimeException {

        Produto oUmProduto;
        try {
            oUmProduto = DAOFactory.getProdutoDAO().getProdutoByCodigo(item.getProduto());
            if (item.getQtdComprada() > oUmProduto.getQuantidadeProd()) {
                estoqueInsuficiente = true;
                throw new RuntimeException("Quantidade em estoque do produto insuficiente" + " (" + oUmProduto.getQuantidadeProd() + ")");
            } else {
                estoqueInsuficiente = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }
    }

    private Integer takeActualDate(String tipo) {
        Date date = new Date();
        switch (tipo) {
            case "date":
                SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy/MM/dd");
                List<String> anoMesDia = Arrays.asList(dataFormat.format(date).split("/"));
                String dataNumeric = new String();
                for (String value : anoMesDia) {
                    dataNumeric = dataNumeric + value;
                }
                Integer x;
                x = parseInt(dataNumeric);
                return x;
            case "hour":
                SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
                List<String> horaMinuto = Arrays.asList(hourFormat.format(date).split(":"));
                String hourNumeric = new String();
                for (String value : horaMinuto) {
                    hourNumeric = hourNumeric + value;
                }
                Integer y;
                y = parseInt(hourNumeric);
                return y;
        }
        return null;
    }

    private void fillComboBoxes(String combo) throws SQLException {
        switch (combo) {
            case "produto":
                comboProduto.getItems().clear();
                FXCollections.observableArrayList(DAOFactory.getProdutoDAO().getAll()).forEach(produto -> {

                    comboProduto.getItems().add(produto.getCodigo());
                });
                break;

            case "funcionario":
                comboFuncionario.getItems().clear();
                FXCollections.observableArrayList(DAOFactory.getFuncionarioDAO().getAll()).forEach(funcionario -> {
                    comboFuncionario.getItems().add(funcionario.getCodigo());
                });
                break;

            case "cliente":
                comboCliente.getItems().clear();
                FXCollections.observableArrayList(DAOFactory.getClienteDAO().getAll()).forEach(cliente -> {
                    comboCliente.getItems().add(cliente.getCodigo());
                });
                break;

            case "all":
                comboProduto.getItems().clear();
                comboFuncionario.getItems().clear();
                comboCliente.getItems().clear();
                FXCollections.observableArrayList(DAOFactory.getProdutoDAO().getAll()).forEach(produto -> {
                    comboProduto.getItems().add(produto.getCodigo());
                });
                FXCollections.observableArrayList(DAOFactory.getClienteDAO().getAll()).forEach(cliente -> {
                    comboCliente.getItems().add(cliente.getCodigo());
                });

                FXCollections.observableArrayList(DAOFactory.getFuncionarioDAO().getAll()).forEach(funcionario -> {
                    comboFuncionario.getItems().add(funcionario.getCodigo());
                });
                break;
        }

    }

    private void mascararTableView() {

        tblColumnHora.setCellFactory((TableColumn<Venda, Integer> param) -> {
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
                            String hourString = "";
                            List<String> aux = Arrays.asList(item.toString().split(""));
                            for (String hh : aux.subList(0, 2)) {
                                hourString += hh;
                            }
                            hourString += ":";
                            for (String mm : aux.subList(2, 4)) {
                                hourString += mm;
                            }
                            setText(hourString);
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
                        getStyleClass().remove("sem-estoque");
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            Produto selectedProduct;
                            try {
                                selectedProduct = DAOFactory.getProdutoDAO().getProdutoByCodigo(item);
                                if (selectedProduct.getQuantidadeProd() <= 0) {
                                    getStyleClass().add("sem-estoque");
                                    setText(item.toString() + "-" + selectedProduct.getDescricaoProd() + "[SEM ESTOQUE]");
                                } else {
                                    setText(item.toString() + "-" + selectedProduct.getDescricaoProd());
                                }

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
                if (object != null && object != 0) {
                    try {
                        return object.toString() + "-" + DAOFactory.getProdutoDAO().getProdutoByCodigo(object).getDescricaoProd();
                    } catch (SQLException ex) {
                        Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                    }
                }

                return null;
            }

            @Override
            public Integer fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    Integer p;
                    p = Integer.parseInt(string.substring(0, 0));

                    return p;
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
                                setText(item.toString() + "-" + DAOFactory.getFuncionarioDAO().getFuncionarioByCodigo(item).getLogin());
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
                if (object != null && object != 0) {
                    try {
                        return object.toString() + "-" + DAOFactory.getFuncionarioDAO().getFuncionarioByCodigo(object).getLogin();
                    } catch (SQLException ex) {
                        Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                    }
                }
                return null;
            }

            @Override
            public Integer fromString(String string) {
                if (string != null && !string.isEmpty()) {

                    Integer f;
                    f = Integer.parseInt(string.substring(0, 0));

                    return f;

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
                                setText(DAOFactory.getClienteDAO().getClienteByCodigo(item).getCpf() + "-" + DAOFactory.getClienteDAO().getClienteByCodigo(item).getNome());
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
                if (object != null && object != 0) {
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
                if (string != null && !string.isEmpty()) {
                    try {
                        Integer c;
                        c = DAOFactory.getClienteDAO().getClienteByCpf(string.substring(0, 10)).getCodigo();

                        return c;
                    } catch (SQLException ex) {
                        Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
                    }
                }

                return null;
            }
        });
    }

    private void bindFieldsVenda(Venda venda) {
        if (venda != null) {
            comboCliente.valueProperty().bindBidirectional(venda.clienteProperty());
            comboFuncionario.valueProperty().bindBidirectional(venda.funcionarioProperty());
            txtValorTotal.textProperty().bindBidirectional(venda.valorTotalCompraProperty(), new NumberStringConverter());
        }

    }

    private void unbindFieldsVenda(Venda venda) {
        if (venda != null) {
            comboCliente.valueProperty().unbindBidirectional(venda.clienteProperty());
            comboFuncionario.valueProperty().unbindBidirectional(venda.funcionarioProperty());
            txtValorTotal.textProperty().unbindBidirectional(venda.valorTotalCompraProperty());
        }
    }

    private void bindFieldsItem_Venda(Item_Venda itemvenda) {
        if (itemvenda != null) {
            txtQtdProduto.textProperty().bindBidirectional(itemvenda.qtdCompradaProperty(), new NumberStringConverter());
            comboProduto.valueProperty().bindBidirectional(itemvenda.produtoProperty());

        }

    }

    private void unbindFieldsItem_Venda(Item_Venda itemvenda) {
        if (itemvenda != null) {
            txtQtdProduto.textProperty().unbindBidirectional(itemvenda.qtdCompradaProperty());
            comboProduto.valueProperty().unbindBidirectional(itemvenda.produtoProperty());
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
                txtValorTotal.clear();
                break;

            case "itemvenda":
                comboProduto.setValue(null);
                txtQtdProduto.clear();
                break;
        }

    }

    private void limpaTableItems() {
        if (!tableItems.getItems().isEmpty()) {
            tableItems.getItems().clear();
        }

    }

    private void addListenner() {
        tableVendas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            clearFields("itemvenda");
            disableFields(false);
            novaVenda = null;
            novoItem = null;
            unbindFieldsVenda(oldValue);
            bindFieldsVenda(newValue);
            vendaSelecionada = newValue;
            tableItems.setItems(FXCollections.observableArrayList(vendaSelecionada.getItens()));
        });
    }

    @FXML
    private void ENTER(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            task = new DoWork();
            tredi = new Thread(task);
            tredi.start();

            task.setOnRunning(ev -> {
                paneVenda.setCursor(Cursor.WAIT);
            });

            task.setOnSucceeded(ev -> {

                btnCarregarOnAction(null);
                paneVenda.setCursor(Cursor.DEFAULT);
            });

        }
    }

}
