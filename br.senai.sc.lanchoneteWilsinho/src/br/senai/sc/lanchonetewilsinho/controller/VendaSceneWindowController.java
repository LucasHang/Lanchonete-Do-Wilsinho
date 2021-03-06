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
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
    @FXML
    private Button btnCarregar;

    /**
     * Initializes the controller class.
     */
    Venda novaVenda = null;
    Venda vendaSelecionada = null;
    Item_Venda novoItem = null;
    Item_Venda itemSelecionado = null;

    List<Venda> vendasCarregadas = null;
    List<Produto> produtosCarregados = null;
    List<Cliente> clientesCarregados = null;

    List<Venda> auxiliarVenda = new ArrayList<>();
    List<Cliente> auxiliarCliente = new ArrayList<>();
    List<Produto> auxiliarProduto = new ArrayList<>();

    Boolean estoqueInsuficiente;
    
    static Integer qntAntigaProduto;

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

        addEffectEvent();

    }

    @FXML
    private void btnAdicionarItemOnAction(ActionEvent event) {
        txtQtdProduto.setDisable(false);
        comboProduto.setDisable(false);
        if (novoItem == null) {
            novoItem = new Item_Venda();
        }
        bindFieldsItem_Venda(novoItem);
    }

    @FXML
    private void btnSalvarItemOnAction(ActionEvent event) {

        if (validationForm("itemvenda")) {
            return;
        }

        comboProduto.getStyleClass().remove("invalido");
        txtQtdProduto.getStyleClass().remove("invalido");

        if (novoItem != null) {
            try {
                qtdProdutoEmEstoque(novoItem);
            } catch (RuntimeException ex) {
                Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
            }
        }
        if (itemSelecionado != null) {
            try {
                qtdProdutoEmEstoque(itemSelecionado);
            } catch (RuntimeException ex) {
                Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
            }
        }

        if (estoqueInsuficiente) {
            return;
        }

        unbindFieldsItem_Venda(itemSelecionado);
        unbindFieldsItem_Venda(novoItem);

        try {
            if (novoItem != null) {
                novoItem.calculaValorItem();
                tableItems.getItems().add(novoItem);
                if (vendaSelecionada != null) {
                    vendaSelecionada.getItens().add(novoItem);
                    vendaSelecionada.calculaValorTotalCompra();
                }
                if (novaVenda != null) {
                    novaVenda.getItens().add(novoItem);
                    novaVenda.calculaValorTotalCompra();
                }

            } else {
                if (itemSelecionado != null) {
                    DAOFactory.getProdutoDAO().updateQtdEstoque(itemSelecionado.getProduto(), (qntAntigaProduto*(-1)));
                    
                    itemSelecionado.calculaValorItem();
                    vendaSelecionada.calculaValorTotalCompra();
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(VendaSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
            MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
        }

        novoItem = null;
        itemSelecionado = null;
        clearFields("itemvenda");
        txtQtdProduto.setDisable(true);
        comboProduto.setDisable(true);
    }

    @FXML
    private void btnFinalizarCompraOnAction(ActionEvent event) {
        if (validationForm("all")) {
            return;
        }

        comboCliente.getStyleClass().remove("invalido");
        tableItems.getStyleClass().remove("invalido");

        unbindFieldsVenda(novaVenda);
        unbindFieldsVenda(vendaSelecionada);

        unbindFieldsItem_Venda(novoItem);
        unbindFieldsItem_Venda(itemSelecionado);

        task = new DoWork();
        tredi = new Thread(task);
        tredi.start();

        try {

            if (novaVenda != null) {
                novaVenda.setFuncionario(mainSceneWindowController.funcLogado.getCodigo());
                novaVenda.setDataVenda(takeActualDate("date"));
                novaVenda.setHoraVenda(takeActualDate("hour"));
                novaVenda.calculaValorTotalCompra();
                DAOFactory.getVendaDAO().save(novaVenda);
                vendasCarregadas.add(novaVenda);
            } else {
                if (vendaSelecionada != null) {
                    
                    DAOFactory.getVendaDAO().update(vendaSelecionada);
                    vendasCarregadas = null;
                } else {
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

        if (vendasCarregadas == null) {
            try {

                vendasCarregadas = DAOFactory.getVendaDAO().getAll();

            } catch (SQLException ex) {
                Logger.getLogger(ClienteSceneWindowController.class.getName()).log(Level.SEVERE, null, ex);
                MeuAlerta.alertaErro(ex.getMessage()).showAndWait();
            }
        }
        tableVendas.setItems(FXCollections.observableArrayList(vendasCarregadas));

    }

    @FXML
    private void btnCadastrarVendaOnAction(ActionEvent event) {
        disableFields(false);
        limpaTableItems();
        unbindFieldsItem_Venda(itemSelecionado);
        unbindFieldsVenda(vendaSelecionada);
        vendaSelecionada = null;
        itemSelecionado = null;
        if (novaVenda == null && novoItem == null) {
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
        unbindFieldsItem_Venda(itemSelecionado);
        unbindFieldsItem_Venda(novoItem);
        unbindFieldsVenda(novaVenda);
        unbindFieldsVenda(vendaSelecionada);
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
                if (horaMinuto.size() < 4) {
                    hourNumeric = "0";
                }
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
                produtosCarregados = DAOFactory.getProdutoDAO().getAll();
                FXCollections.observableArrayList(produtosCarregados).forEach(produto -> {
                    comboProduto.getItems().add(produto.getCodigo());
                });
                break;
            case "cliente":
                comboCliente.getItems().clear();
                clientesCarregados = DAOFactory.getClienteDAO().getAll();
                FXCollections.observableArrayList(clientesCarregados).forEach(cliente -> {
                    comboCliente.getItems().add(cliente.getCodigo());
                });
                break;

            case "all":
                comboProduto.getItems().clear();
                comboCliente.getItems().clear();
                produtosCarregados = DAOFactory.getProdutoDAO().getAll();
                clientesCarregados = DAOFactory.getClienteDAO().getAll();
                FXCollections.observableArrayList(produtosCarregados).forEach(produto -> {
                    comboProduto.getItems().add(produto.getCodigo());
                });
                FXCollections.observableArrayList(clientesCarregados).forEach(cliente -> {
                    comboCliente.getItems().add(cliente.getCodigo());
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
                            if (aux.size() < 4) {
                                for (String hh : aux.subList(0, 1)) {
                                    hourString += hh;
                                }
                                hourString += ":";
                                for (String mm : aux.subList(1, 3)) {
                                    hourString += mm;
                                }
                                setText(hourString);
                            } else {
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
                    p = Integer.parseInt(string.substring(0, 1));

                    return p;
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
                        c = DAOFactory.getClienteDAO().getClienteByCpf(string.substring(0, 11)).getCodigo();

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
            txtValorTotal.textProperty().bindBidirectional(venda.valorTotalCompraProperty(), new NumberStringConverter());
        }

    }

    private void unbindFieldsVenda(Venda venda) {
        if (venda != null) {
            comboCliente.valueProperty().unbindBidirectional(venda.clienteProperty());
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
        comboProduto.setDisable(value);
        txtQtdProduto.setDisable(value);

    }

    public Boolean validationForm(String tipo) {
        Boolean invalido = false;
        switch (tipo) {
            case "all":
                if (comboCliente.valueProperty().isNull().get()) {
                    comboCliente.getStyleClass().add("invalido");
                    invalido = true;
                } else {
                    comboCliente.getStyleClass().remove("invalido");
                }
                if (tableItems.getItems() == null || tableItems.getItems().isEmpty()) {
                    tableItems.getStyleClass().add("invalido");
                    invalido = true;
                } else {
                    tableItems.getStyleClass().remove("invalido");
                }

                return invalido;

            case "itemvenda":

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
        return null;
    }

    private void clearFields(String tipo) {
        switch (tipo) {
            case "venda":
                comboCliente.setValue(null);
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
            unbindFieldsVenda(novaVenda);
            unbindFieldsItem_Venda(novoItem);
            novaVenda = null;
            novoItem = null;
            unbindFieldsVenda(oldValue);
            bindFieldsVenda(newValue);
            vendaSelecionada = newValue;
            if (vendaSelecionada != null) {
                tableItems.setItems(FXCollections.observableArrayList(vendaSelecionada.getItens()));
            }

            tableItems.getSelectionModel().selectedItemProperty().addListener((observable2, oldValue2, newValue2) -> {
                unbindFieldsItem_Venda(oldValue2);
                bindFieldsItem_Venda(newValue2);
                qntAntigaProduto = newValue2.getQtdComprada();
                itemSelecionado = newValue2;

            });

        });

        txtCarregar.textProperty().addListener((Observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                task = new DoWork();
                tredi = new Thread(task);
                tredi.start();

                task.setOnRunning(ev -> {
                    paneVenda.setCursor(Cursor.WAIT);
                });

                task.setOnSucceeded(ev -> {
                    tableVendas.setItems(FXCollections.observableArrayList(vendasCarregadas));
                    paneVenda.setCursor(Cursor.DEFAULT);
                });

            }
        });

    }

    private void addEffectEvent() {
        DoWork.createButtonEffectEvent(btnCarregar, "buttonEffect");
        DoWork.createButtonEffectEvent(btnAdicionarItem, "button3Effect");
        DoWork.createButtonEffectEvent(btnCadastrarVenda, "buttonEffect");
        DoWork.createButtonEffectEvent(btnFinalizarCompra, "buttonEffect");
        DoWork.createButtonEffectEvent(btnRemover, "buttonEffect");
        DoWork.createButtonEffectEvent(btnSalvarItem, "buttonEffect");
        DoWork.createButtonEffectEvent(btnCancelarAcao, "button2Effect");

        DoWork.createFieldEffectEvent(txtCarregar, "textfieldEffect");
        DoWork.createFieldEffectEvent(txtQtdProduto, "textfieldEffect");

        DoWork.createComboEffectEvent(comboCliente, "comboBoxEffect");
        DoWork.createComboEffectEvent(comboProduto, "comboBoxEffect");

    }

    @FXML
    private void enterCarregar(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            task = new DoWork();
            tredi = new Thread(task);
            tredi.start();

            vendasCarregadas.forEach(venda -> {

                if (venda.getNomeCli().equals(txtCarregar.getText()) || venda.getNomeFun().equals(txtCarregar.getText()) || venda.getDataVenda().equals(BrSenaiScLanchoneteWilsinho.dateToIntegerConverter(txtCarregar.getText()))) {
                    auxiliarVenda.add(venda);
                }

            });

            task.setOnRunning(ev -> {
                paneVenda.setCursor(Cursor.WAIT);
            });

            task.setOnSucceeded(ev -> {
                if (txtCarregar.getText().isEmpty()) {
                    tableVendas.setItems(FXCollections.observableArrayList(vendasCarregadas));
                }
                tableVendas.setItems(FXCollections.observableArrayList(auxiliarVenda));
                auxiliarVenda = new ArrayList<>();

                paneVenda.setCursor(Cursor.DEFAULT);
            });

        }
    }

}
