package org.samuelperez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.samuelperez.bean.DetalleFactura;
import org.samuelperez.bean.Empleados;
import org.samuelperez.bean.Factura;
import org.samuelperez.bean.Productos;
import org.samuelperez.db.Conexion;
import org.samuelperez.system.Principal;

/**
 * @author Nombre: Samuel Alexander Perez Cap Carnet: 2020493 Grado:IN5BM
 */
public class MenuDetalleFacturaController implements Initializable {

    // Referencia al escenario principal de la aplicación
    private Principal escenarioPrincipal;

    // Lista observable para almacenar objetos de tipo 'DetalleFactura'
    private ObservableList<DetalleFactura> listaDetalleFactura;

    // Lista observable para almacenar objetos de tipo 'Proveedores'
    private ObservableList<Productos> listaProductos;

    // Lista observable para almacenar objetos de tipo 'Empleados'
    private ObservableList<Factura> listaFactura;

    // Enumeración para representar las diferentes operaciones que se pueden realizar
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    };

    // Variable para almacenar la operación actual
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private TableView tblDetalleFactura;

    @FXML
    private Button btnRegresar;

    @FXML
    private Button btnAgregar;

    @FXML
    private ImageView imgAgregar;

    @FXML
    private Button btnEditar;

    @FXML
    private ImageView imgEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnReport;

    @FXML
    private ImageView imgReport;

    @FXML
    private TableColumn colDeatlleFactura;

    @FXML
    private TableColumn colPrecioUnitarioD;

    @FXML
    private TableColumn colCantidadD;

    @FXML
    private TableColumn colNumeroFactura;

    @FXML
    private TableColumn colCodigoProducto;

    @FXML
    private ComboBox cmbNumeroFactura;

    @FXML
    private ComboBox cmbCodigoProducto;

    @FXML
    private TextField txtDetalleFacturaD;

    @FXML
    private TextField txtPresioUnitarioD;

    @FXML
    private TextField txtCantidadD;

    /**
     * Se encarga de devolver el escenario.
     *
     * @return el escenario Principal.
     */
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    /**
     * Se encarga de establecer el ecenario Principal.
     *
     * @param escenarioPrincipal el escenario principal.
     */
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbNumeroFactura.setItems(getFacturas());
        cmbCodigoProducto.setItems(getProductos());
    }

    /**
     * Se encarga de Manejar las Acciones de los botones.
     *
     * @param event el evento que ocurrio en ese momento.
     */
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    /**
     * El metodo se encarga de cargar los datos de la tabla en la vista, segun
     * el nombre de cada columna.
     */
    public void cargarDatos() {
        tblDetalleFactura.setItems(getDetalleFactura());
        colDeatlleFactura.setCellValueFactory(new PropertyValueFactory<>("codigoDetalleFactura"));
        colPrecioUnitarioD.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colCantidadD.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<>("numeroFactura"));
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
    }

    /**
     * El metodo Actualiza los campos de los txt seleccionando la tabla a la que
     * se encarga de mostrar El procedimiento se llama cuando se selecciona un
     * elemento de la tabla.
     */
    public void seleccionarElemento() {
        txtDetalleFacturaD.setText(String.valueOf(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura()));
        txtPresioUnitarioD.setText(String.valueOf(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtCantidadD.setText(String.valueOf(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbNumeroFactura.getSelectionModel().select(buscarNumeroFactura(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        cmbCodigoProducto.getSelectionModel().select(buscarCodigoProducto(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoProducto()));
    }

    /**
     * Este método se utiliza para buscar una Tipo de Producto en la base de
     * datos realizando una conexion al metodo almacenado que se encarga de
     * buscar.
     *
     * @param numeroFactura El codigo de tipo Producto de la tabla a buscar.
     * @return La compra encontrada, o null si no se encuentra ninguna compra.
     */
    public Factura buscarNumeroFactura(int numeroFactura) {
        Factura resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarFacturav(?);");
            procedimiento.setInt(1, numeroFactura);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Factura(registro.getInt("numeroFactura"),
                        registro.getString("estado"),
                        registro.getDouble("totalFactura"),
                        registro.getString("fechaFactura"),
                        registro.getInt("codigoCliente"),
                        registro.getInt("codigoEmpleado"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * Este método se utiliza para buscar una Tipo de Producto en la base de
     * datos realizando una conexion al metodo almacenado que se encarga de
     * buscar.
     *
     * @param codigoProducto El codigo de tipo Producto de la tabla a buscar.
     * @return La compra encontrada, o null si no se encuentra ninguna compra.
     */
    public Productos buscarCodigoProducto(String codigoProducto) {
        Productos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarProductos(?);");
            procedimiento.setString(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Productos(registro.getString("codigoProducto"),
                        registro.getString("descripcionProducto"),
                        registro.getDouble("precioUnitario"),
                        registro.getDouble("precioDocena"),
                        registro.getDouble("precioMayor"),
                        registro.getString("imagenProducto"),
                        registro.getInt("existencia"),
                        registro.getInt("codigoTipoProducto"),
                        registro.getInt("codigoProveedor"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /**
     * El metodo realizar la conexion con el procedimiento almacenado que se
     * encarga de listar las informacion de la tabla y todas sus tuplas, esta
     * funciona con una ArrayList para poder manejar los datos.
     *
     * @return una lista que se puede observar de los Productos.
     */
    public ObservableList<Productos> getProductos() {
        ArrayList<Productos> lista = new ArrayList<Productos>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarProductos();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Productos(resultado.getString("codigoProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getString("imagenProducto"),
                        resultado.getInt("existencia"),
                        resultado.getInt("codigoTipoProducto"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductos = FXCollections.observableList(lista);

    }

    /**
     * El metodo realizar la conexion con el procedimiento almacenado que se
     * encarga de listar las informacion de la tabla y todas sus tuplas, esta
     * funciona con una ArrayList para poder manejar los datos.
     *
     * @return una lista que se puede observar de las Factura.
     */
    public ObservableList<Factura> getFacturas() {
        ArrayList<Factura> lista = new ArrayList<>();
        ResultSet resultado = null;
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_listarFactura();");
            resultado = p.executeQuery();
            while (resultado.next()) {
                lista.add(new Factura(resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("codigoEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaFactura = FXCollections.observableList(lista);
    }

    /**
     * El metodo realizar la conexion con el procedimiento almacenado que se
     * encarga de listar las informacion de la tabla y todas sus tuplas, esta
     * funciona con una ArrayList para poder manejar los datos.
     *
     * @return una lista que se puede observar de las Factura.
     */
    public ObservableList<DetalleFactura> getDetalleFactura() {
        ArrayList<DetalleFactura> lista = new ArrayList<>();
        ResultSet resultado = null;
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_ListarDetalleCompra();");
            resultado = p.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleFactura(resultado.getInt("codigoDetalleFactura"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getInt("numeroFactura"),
                        resultado.getString("codigoProducto")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDetalleFactura = FXCollections.observableList(lista);
    }
    
    /**
     * Se encarga de desactivar los textFiel.
     */
    public void desactivarControles() {
        txtDetalleFacturaD.setEditable(false);
        txtPresioUnitarioD.setEditable(false);
        txtCantidadD.setEditable(false);
        cmbNumeroFactura.setDisable(true);
        cmbCodigoProducto.setDisable(true);
    }

    /**
     * Se encarga de activar los textFiel.
     */
    public void activarControles() {
        txtDetalleFacturaD.setEditable(true);
        txtPresioUnitarioD.setEditable(true);
        txtCantidadD.setEditable(true);
        cmbNumeroFactura.setDisable(false);
        cmbCodigoProducto.setDisable(false);
    }

    /**
     * Se encarga de limpiar los textFiel.
     */
    public void limpiarControles() {
        txtDetalleFacturaD.clear();
        txtPresioUnitarioD.clear();
        txtCantidadD.clear();
        tblDetalleFactura.getSelectionModel().getSelectedItem();
        cmbNumeroFactura.getSelectionModel().getSelectedItem();
        cmbCodigoProducto.getSelectionModel().getSelectedItem();
    }
}
