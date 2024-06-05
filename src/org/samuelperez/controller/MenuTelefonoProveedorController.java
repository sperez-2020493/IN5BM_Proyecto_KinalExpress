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
import org.samuelperez.bean.Proveedores;
import org.samuelperez.bean.TelefonoProveedor;
import org.samuelperez.db.Conexion;
import org.samuelperez.system.Principal;

/**
 * @author Nombre: Samuel Alexander Perez Cap Carnet: 2020493 Grado:IN5BM
 */
/**
 * Controlador del menú principal. La Clase se encarga de las acciones y eventos
 * del menú principal.
 */
public class MenuTelefonoProveedorController implements Initializable {

    private Principal escenarioPrincipal;

    // Lista observable para almacenar objetos de tipo 'TelefonoProveedor'
    private ObservableList<TelefonoProveedor> listaTelefonoProveedor;

    // Lista observable para almacenar objetos de tipo 'Proveedores'
    private ObservableList<Proveedores> listaProveedores;

    // Enumeración para representar las diferentes operaciones que se pueden realizar
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    };

    // Variable para almacenar la operación actual
    private MenuTelefonoProveedorController.operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private Button btnRegresar;

    @FXML
    private TableView tblTelefonoProveedor;

    @FXML
    private TableColumn colTelefonoProveedorT;

    @FXML
    private TableColumn colNumeroPrincipalT;

    @FXML
    private TableColumn colNumeroSecundarioT;

    @FXML
    private TableColumn colObservacionesT;

    @FXML
    private TableColumn colCodigoProveedorT;

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
    private TextField txtCodigoTelefonoProveedor;

    @FXML
    private TextField txtNumeroPrincipalT;

    @FXML
    private TextField txtNumeroSecundarioT;

    @FXML
    private TextField txtObservacionesT;

    @FXML
    private ComboBox cmbCodigoP;

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
        cmbCodigoP.setItems(getProveedores());
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
        tblTelefonoProveedor.setItems(getTelefonoProveedor());
        colTelefonoProveedorT.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoTelefonoProveedor"));
        colNumeroPrincipalT.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("numeroPrincipal"));
        colNumeroSecundarioT.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("numeroSecundario"));
        colObservacionesT.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("observaciones"));
        colCodigoProveedorT.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoProveedor"));
    }

    /**
     * El metodo Actualiza los campos de los txt seleccionando la tabla a la que
     * se encarga de mostrar El procedimiento se llama cuando se selecciona un
     * elemento de la tabla.
     */
    public void seleccionarElemento() {
        txtCodigoTelefonoProveedor.setText(String.valueOf(((TelefonoProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor()));
        txtNumeroPrincipalT.setText((((TelefonoProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getNumeroPrincipal()));
        txtNumeroSecundarioT.setText((((TelefonoProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getNumeroSecundario()));
        txtObservacionesT.setText((((TelefonoProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getObservaciones()));
        cmbCodigoP.getSelectionModel().select(buscarCodigoProveedor(((TelefonoProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
    }

    /**
     * Este método se utiliza para buscar una Tipo de Producto en la base de
     * datos realizando una conexion al metodo almacenado que se encarga de
     * buscar.
     *
     * @param codigoCargoEmpleado El codigo de tipo Producto de la tabla a
     * buscar.
     * @return La compra encontrada, o null si no se encuentra ninguna compra.
     */
    public Proveedores buscarCodigoProveedor(int codigoProveedor) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarProveedores(?);");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                        registro.getString("NITProveedor"),
                        registro.getString("nombresProveedor"),
                        registro.getString("apellidosProveedor"),
                        registro.getString("direccionProveedor"),
                        registro.getString("razonSocial"),
                        registro.getString("contactoPrincipal"),
                        registro.getString("paginaWeb"));
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
     * @return una lista que se puede observar de los Proveedores.
     */
    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarProveedores();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITProveedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableList(lista);

    }

    /**
     * El metodo realizar la conexion con el procedimiento almacenado que se
     * encarga de listar las informacion de la tabla y todas sus tuplas, esta
     * funciona con una ArrayList para poder manejar los datos.
     *
     * @return una lista que se puede observar de los Telefono Proveedor.
     */
    public ObservableList<TelefonoProveedor> getTelefonoProveedor() {
        ArrayList<TelefonoProveedor> lista = new ArrayList<>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarTelefonoProveedor();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TelefonoProveedor(resultado.getInt("codigoTelefonoProveedor"),
                        resultado.getString("numeroPrincipal"),
                        resultado.getString("numeroSecundario"),
                        resultado.getString("observaciones"),
                        resultado.getInt("codigoProveedor")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTelefonoProveedor = FXCollections.observableList(lista);

    }

    /**
     * El metodo agregar tiene la funcion de realizar una nueva tupla en la
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
        TelefonoProveedor registro = new TelefonoProveedor();
        registro.setCodigoTelefonoProveedor(Integer.parseInt(txtCodigoTelefonoProveedor.getText()));
        registro.setNumeroPrincipal(txtNumeroPrincipalT.getText());
        registro.setNumeroSecundario(txtNumeroSecundarioT.getText());
        registro.setObservaciones(txtObservacionesT.getText());
        registro.setCodigoProveedor(((Proveedores) cmbCodigoP.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarTelefonoProveedor(?,?,?,?,?);");
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
            procedimiento.execute();
            listaTelefonoProveedor.add(registro);
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
                if (tblTelefonoProveedor.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la Eliminacion del Registro", "Eliminar Telefono Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTelefonoProveedor(?)}");
                            procedimiento.setInt(1, ((TelefonoProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor());
                            procedimiento.execute();
                            listaTelefonoProveedor.remove(tblTelefonoProveedor.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar Un Telefono para Eliminar");
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
                if (tblTelefonoProveedor.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReport.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/samuelperez/images/MenuClientes_IconActualizar.png"));
                    imgReport.setImage(new Image("/org/samuelperez/images/MenuClientes_IconCancelar.png"));
                    activarControles();
                    txtCodigoTelefonoProveedor.setEditable(false);
                    tipoDeOperaciones = MenuTelefonoProveedorController.operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Email");
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTelefonoProveedor(?,?,?,?,?)}");
            TelefonoProveedor registro = (TelefonoProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem();
            registro.setCodigoTelefonoProveedor(Integer.parseInt(txtCodigoTelefonoProveedor.getText()));
            registro.setNumeroPrincipal(txtNumeroPrincipalT.getText());
            registro.setNumeroSecundario(txtNumeroSecundarioT.getText());
            registro.setObservaciones(txtObservacionesT.getText());
            registro.setCodigoProveedor(((Proveedores) cmbCodigoP.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
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
        txtCodigoTelefonoProveedor.setEditable(false);
        txtNumeroPrincipalT.setEditable(false);
        txtNumeroSecundarioT.setEditable(false);
        txtObservacionesT.setEditable(false);
        cmbCodigoP.setDisable(true);
    }

    /**
     * Se encarga de activar los textFiel.
     */
    public void activarControles() {
        txtCodigoTelefonoProveedor.setEditable(true);
        txtNumeroPrincipalT.setEditable(true);
        txtNumeroSecundarioT.setEditable(true);
        txtObservacionesT.setEditable(true);
        cmbCodigoP.setDisable(false);
    }

    /**
     * Se encarga de limpiar los textFiel.
     */
    public void limpiarControles() {
        txtCodigoTelefonoProveedor.clear();
        txtNumeroPrincipalT.clear();
        txtNumeroSecundarioT.clear();
        txtObservacionesT.clear();
        tblTelefonoProveedor.getSelectionModel().getSelectedItem();
        cmbCodigoP.getSelectionModel().getSelectedItem();
    }
}
