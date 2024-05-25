
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.samuelperez.bean.Compras;
import org.samuelperez.bean.DetalleCompra;
import org.samuelperez.bean.Productos;
import org.samuelperez.db.Conexion;
import org.samuelperez.system.Principal;


/**
 * @author Nombre: Samuel Alexander Perez Cap
 * Carnet: 2020493  Grado:IN5BM
 */

public class MenuDetalleComprasController implements Initializable {

    // Referencia al escenario principal de la aplicación
    private Principal escenarioPrincipal;

    // Lista observable para almacenar objetos de tipo 'DetalleCompra'
    private ObservableList<DetalleCompra> listaDetalleCompra;

    // Lista observable para almacenar objetos de tipo 'Producto'
    private ObservableList<Productos> listaProductos;

    // Lista observable para almacenar objetos de tipo 'Compras'
    private ObservableList<Compras> listaCompras;

    // Enumeración para representar las diferentes operaciones que se pueden realizar
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    };

    private MenuDetalleComprasController.operaciones tipoDeOperaciones = operaciones.NINGUNO;

    // Botones, tabla, columnas, combox e imágenes de la interfaz de la vista 
    @FXML
    private Button btnRegresar;

    @FXML
    private TableView tblDetalleCompras;

    @FXML
    private TableColumn colDetalleCompraD;

    @FXML
    private TableColumn colConstoUnitarioD;

    @FXML
    private TableColumn colCantidadD;

    @FXML
    private TableColumn colCodigoProductoD;

    @FXML
    private TableColumn colNumeroProductoD;

    @FXML
    private Button btnAgregar;

    @FXML
    private ImageView imgAgregar;

    @FXML
    private Button btnEditar;

    @FXML
    private ImageView imgEditar;

    @FXML
    private Button btnReport;

    @FXML
    private ImageView imgReport;

    @FXML
    private Button btnEliminar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private ComboBox cmbCodigoProductos;

    @FXML
    private ComboBox cmbNumeroDocumento;

    @FXML
    private TextField txtCodigoDetalleC;

    @FXML
    private TextField txtCostoUnitario;

    @FXML
    private TextField txtCantidad;

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

    /**
     * El @Override se encarga de cargar el metodo CargarDatos llamando el
     * metodo en el y los combox donde estan las Fk.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbCodigoProductos.setItems(getProductos());
        cmbNumeroDocumento.setItems(getCompras());
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
        tblDetalleCompras.setItems(getDetalleCompra());
        colDetalleCompraD.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("codigoDetalleCompra"));
        colConstoUnitarioD.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("costoUnitario"));
        colCantidadD.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidad"));
        colCodigoProductoD.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("codigoProducto"));
        colNumeroProductoD.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("numeroDocumento"));
    }

    /**
     * El metodo Actualiza los campos de los txt seleccionando la tabla a la que
     * se encarga de mostrar El procedimiento se llama cuando se selecciona un
     * elemento de la tabla.
     */
    public void seleccionarElemento() {
        txtCodigoDetalleC.setText(String.valueOf(((DetalleCompra) tblDetalleCompras.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra()));
        txtCostoUnitario.setText(String.valueOf(((DetalleCompra) tblDetalleCompras.getSelectionModel().getSelectedItem()).getCostoUnitario()));
        txtCantidad.setText(String.valueOf(((DetalleCompra) tblDetalleCompras.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbCodigoProductos.getSelectionModel().select(buscarProducto(((DetalleCompra) tblDetalleCompras.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        cmbNumeroDocumento.getSelectionModel().select(buscarCompra(((DetalleCompra) tblDetalleCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
    }

    /**
     * Este método se utiliza para buscar un producto en la base de datos realizando una conexion al Procedimiento almacenado
     * encargado de la busqueda de los Productos.
     * 
     * @param codigoProducto El código del producto a buscar.
     * @return El producto encontrado, o null si no se encuentra ningún producto.
    */
    public Productos buscarProducto(String codigoProducto) {
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
    * Este método se utiliza para buscar una compra en la base de datos realizando una conexion al producto
    * almacenado que se encarga de buscar.
    * 
    * @param numeroDocumento El número de documento de la compra a buscar.
    * @return La compra encontrada, o null si no se encuentra ninguna compra.
    */
    public Compras buscarCompra(int numeroDocumento) {
        Compras resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarCompra(?);");
            procedimiento.setInt(1, numeroDocumento);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Compras(registro.getInt("numeroDocumento"),
                        registro.getString("fechaDocumento"),
                        registro.getString("descripcion"),
                        registro.getDouble("totalDocumento"));
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
     * @return una lista que se puede observar de las Compras.
     */
    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarCompras();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                        resultado.getString("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCompras = FXCollections.observableList(lista);

    }

    /**
     * El metodo realizar la conexion con el procedimiento almacenado que se
     * encarga de listar las informacion de la tabla y todas sus tuplas, esta
     * funciona con una ArrayList para poder manejar los datos.
     * 
     * @return una lista que se puede observar de los Detalle Compra.
     */
    public ObservableList<DetalleCompra> getDetalleCompra() {
        ArrayList<DetalleCompra> lista = new ArrayList<DetalleCompra>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarDetalleCompra();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleCompra(resultado.getInt("codigoDetalleCompra"),
                        resultado.getDouble("costoUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getString("codigoProducto"),
                        resultado.getInt("numeroDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaDetalleCompra = FXCollections.observableList(lista);

    }

    /**
     * El metodk agregar tiene la funcion de realizar una nueva tupla en la
     * tabla de la base de datos, esta tiene un switch con 2 casos, el caso
     * ninguno resive los datos y el tro caso es el que llama al metodo guardar
     * para guardar los datos de los txt y cargar la vista para mostrar la tupla
     * agregada.
     */
    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReport.setDisable(true);
                imgAgregar.setImage(new Image("/org/samuelperez/images/MenuClientes_IconGuardar.png"));
                imgEliminar.setImage(new Image("/org/samuelperez/images/MenuClientes_IconCancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                cargarDatos();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReport.setDisable(false);
                imgAgregar.setImage(new Image("/org/samuelperez/images/MenuClientes_IconAgregarUsuario.png"));
                imgEliminar.setImage(new Image("/org/samuelperez/images/MenuClientes_IconEliminarUsuario.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }

    /**
     * El metodo Guardar es el que se encarga de realizar la conexion con el
     * procedimiento almacenado que guarda los datos enviados por el usuario en
     * los txt como una nueva tupla en la tabla.
     */
    public void guardar() {
        DetalleCompra registro = new DetalleCompra();
        registro.setCodigoDetalleCompra(Integer.parseInt(txtCodigoDetalleC.getText()));
        registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setCodigoProducto(((Productos) cmbCodigoProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
        registro.setNumeroDocumento(((Compras) cmbNumeroDocumento.getSelectionModel().getSelectedItem()).getNumeroDocumento());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarDetalleCompra(?,?,?,?,?);;");
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();
            listaDetalleCompra.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * El metodo eliminar se encarga de hacer la conexion al procedimiento
     * almacenado encargado de eliminar una tupla, sabiendo el ID que identifica
     * dicha tupla seleccionada en la tabla de la vista.
     */
    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReport.setDisable(false);
                imgAgregar.setImage(new Image("/org/samuelperez/images/MenuClientes_IconAgregarUsuario.png"));
                imgEliminar.setImage(new Image("/org/samuelperez/images/MenuClientes_IconEliminarUsuario.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblDetalleCompras.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la Eliminacion del Registro", "Eliminar Detalle de Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDetalleCompra(?)}");
                            procedimiento.setInt(1, ((DetalleCompra) tblDetalleCompras.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
                            procedimiento.execute();
                            listaDetalleCompra.remove(tblDetalleCompras.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar Un Proveedor para Eliminar");
                }
        }
    }

    /**
     * El metodo editar se encaga de llamar al metodo llamado actualizar, este
     * se encuentra en un switch con 2 casos. El caso ninguno es en el cual el
     * usuario no haya seleccionado alguna tupla para editar, y el caso
     * Actualizar es el encargado de realizar la actualizacion y llamar al
     * metedo que se encarga de esta.
     */
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblDetalleCompras.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReport.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/samuelperez/images/MenuClientes_IconActualizar.png"));
                    imgReport.setImage(new Image("/org/samuelperez/images/MenuClientes_IconCancelar.png"));
                    activarControles();
                    txtCodigoDetalleC.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReport.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/samuelperez/images/MenuClientes_IconEditarUsuario.png"));
                imgReport.setImage(new Image("/org/samuelperez/images/MenuClientes_IconReportUsuario.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                desactivarControles();
                limpiarControles();
                cargarDatos();
                break;
        }

    }

    /**
     * El metodo actualizar se encarga de realizar la conexion al procedimiento
     * almacenado que se encarga de Actualizar la tupla seleccionada de la tabla
     * de la base de datos.
     */
    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarDetalleCompra(?,?,?,?,?)}");
            DetalleCompra registro = ((DetalleCompra) tblDetalleCompras.getSelectionModel().getSelectedItem());
            registro.setCodigoDetalleCompra(Integer.parseInt(txtCodigoDetalleC.getText()));
            registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setCodigoProducto(((Productos) cmbCodigoProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
            registro.setNumeroDocumento(((Compras) cmbNumeroDocumento.getSelectionModel().getSelectedItem()).getNumeroDocumento());
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Se encarga de cancelar el metodo de editar y reiniciar la vista al
     * precionar el boton Report, la vista vuelve a su estado de inicio.
     */
    public void cancelar() {
        desactivarControles();
        limpiarControles();
        btnEditar.setText("Editar");
        btnReport.setText("Reporte");
        btnAgregar.setDisable(false);
        btnEliminar.setDisable(false);
        imgEditar.setImage(new Image("/org/samuelperez/images/MenuClientes_IconEditarUsuario.png"));
        imgReport.setImage(new Image("/org/samuelperez/images/MenuClientes_IconReportUsuario.png"));
        tipoDeOperaciones = operaciones.NINGUNO;
    }

    /**
     * Se encarga de desactivar los textFiel.
     */
    public void desactivarControles() {
        txtCodigoDetalleC.setEditable(false);
        txtCostoUnitario.setEditable(false);
        txtCantidad.setEditable(false);
        cmbCodigoProductos.setDisable(true);
        cmbNumeroDocumento.setDisable(true);
    }

    /**
     * Se encarga de activar los textFiel.
     */
    public void activarControles() {
        txtCodigoDetalleC.setEditable(true);
        txtCostoUnitario.setEditable(true);
        txtCantidad.setEditable(true);
        cmbCodigoProductos.setDisable(false);
        cmbNumeroDocumento.setDisable(false);
    }

    /**
     * Se encarga de limpiar los textFiel.
     */
    public void limpiarControles() {
        txtCodigoDetalleC.clear();
        txtCostoUnitario.clear();
        txtCantidad.clear();
        cmbCodigoProductos.getSelectionModel().getSelectedItem();
        cmbNumeroDocumento.getSelectionModel().getSelectedItem();
    }
}
