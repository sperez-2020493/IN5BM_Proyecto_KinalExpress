
package org.samuelperez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.samuelperez.bean.Clientes;
import org.samuelperez.db.Conexion;
import org.samuelperez.report.GenerarReportes;
import org.samuelperez.system.Principal;



/**
 * @author Nombre: Samuel Alexander Perez Cap
 * Carnet: 2020493  Grado:IN5BM
 */

/**
 * Este controlador del Menu Clientes tiene una clase el cual se encarga de
 * manejar y controlar las acciones y eventos del menu clientes.
 */
public class MenuClientesController implements Initializable {

    // Referencia al escenario principal de la aplicación
    private Principal escenarioPrincipal;

    // Lista observable para almacenar objetos de tipo 'Cliente'
    private ObservableList<Clientes> listaClientes;

    // Enumeración para representar las diferentes operaciones que se pueden realizar
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    };
    
    // Variable para almacenar la operación actual
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    /*
    * Botones, Campos de texto y columnas para Clientes
     */
    @FXML
    private TextField txtCodigoCliente;

    @FXML
    private TextField txtNombresCliente;

    @FXML
    private TextField txtApellidosCliente;

    @FXML
    private TextField txtDireccionCliente;

    @FXML
    private TextField txtNitCliente;

    @FXML
    private TextField txtTelefonoCliente;

    @FXML
    private TextField txtCorreoCliente;

    @FXML
    private TableView tblClientes;

    @FXML
    private TableColumn colClienteC;

    @FXML
    private TableColumn colNitC;

    @FXML
    private TableColumn colNombreC;

    @FXML
    private TableColumn colApellidoC;

    @FXML
    private TableColumn colDireccionC;

    @FXML
    private TableColumn colTelefonoC;

    @FXML
    private TableColumn colCorreoC;

    @FXML
    private ImageView imgAgregar;

    @FXML
    private ImageView imgEditar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private ImageView imgReport;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReport;

    @FXML
    Button btnRegresar;

    /**
     * Se encarga de devolver al ecenario principal de la misma aplicacion.
     *
     * @return El ecenario principal.
     */
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    /**
     * Se encarga de establecer el escenario principal de la aplicacion.
     *
     * @param escenarioPrincipal el escenario principal.
     */
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    /**
     * El @Override se encarga de cargar el metodo CargarDatos llamando el
     * metodo en el.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    /**
     * Se encarga de la activacion del boton y su funcionamiento.
     */
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    /**
     * El metodo se encarga de cargar los datos de la base de datos de los
     * Clientes en la tabla de la vista.
     */
    public void cargarDatos() {
        tblClientes.setItems(getClientes());
        colClienteC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
        colNitC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("NITClientes"));
        colNombreC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombresCliente"));
        colApellidoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidosCliente"));
        colDireccionC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        colTelefonoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colCorreoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));

    }

    /**
     * El metodo Actualiza los campos de los txt seleccionando la tabla a la que
     * se encarga de mostrar El procedimiento se llama cuando se selecciona un
     * elemento de la tabla.
     */
    public void seleccionarElemento() {
        txtCodigoCliente.setText(String.valueOf(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        txtNombresCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombresCliente());
        txtApellidosCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getApellidosCliente());
        txtNitCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNITClientes());
        txtDireccionCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtTelefonoCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtCorreoCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCorreoCliente());

    }

    /**
     * El metodo obtiene los Clientes y su informacion de la base de datos.
     *
     * @return una lista que se puede observar de los clientes.
     */
    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarClientes();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                        resultado.getString("NITClientes"),
                        resultado.getString("nombresCliente"),
                        resultado.getString("apellidosCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaClientes = FXCollections.observableList(lista);

    }

    /**
     * El metodk agregar tiene la funcion de realizar una nueva tupla en la
     * tabla de la base de datos, esta tiene un switch con 2 casos, el caso
     * ninguno resive los datos y el tro caso es el que llama al metodo guardar
     * para guardar los datos de los txt y cargar la vista para mostrar la tupla
     * agregada.
     */
    public void Agregar() {
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
        Clientes registro = new Clientes();
        registro.setCodigoCliente(Integer.parseInt(txtCodigoCliente.getText()));
        registro.setNombresCliente((txtNombresCliente.getText()));
        registro.setApellidosCliente((txtApellidosCliente.getText()));
        registro.setNITClientes((txtNitCliente.getText()));
        registro.setTelefonoCliente((txtTelefonoCliente.getText()));
        registro.setDireccionCliente((txtDireccionCliente.getText()));
        registro.setCorreoCliente((txtCorreoCliente.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarClientes(?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITClientes());
            procedimiento.setString(3, registro.getNombresCliente());
            procedimiento.setString(4, registro.getApellidosCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
            listaClientes.add(registro);

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
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la Eliminacion del Registro", "Eliminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?)}");
                            procedimiento.setInt(1, ((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaClientes.remove(tblClientes.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar Un cliente para Eliminar");
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
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReport.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/samuelperez/images/MenuClientes_IconActualizar.png"));
                    imgReport.setImage(new Image("/org/samuelperez/images/MenuClientes_IconCancelar.png"));
                    activarControles();
                    txtCodigoCliente.setEditable(false);
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
     * Se encarga de cancelar el metodo de editar y reiniciar la vista al
     * precionar el boton Report, la vista vuelve a su estado de inicio.
     */
    public void cancelar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                imprimirReporte();
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReport.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/samuelperez/images/MenuClientes_IconEditarUsuario.png"));
                imgReport.setImage(new Image("/org/samuelperez/images/MenuClientes_IconReportUsuario.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void imprimirReporte() {
        Map parametros = new HashMap();
        parametros.put("codigoCliente", null);
        GenerarReportes.mostrarReportes("ReporteEjemplo.jasper", "Reporte de Clientes", parametros);
    }
    
    /**
     * El metodo actualizar se encarga de realizar la conexion al procedimiento
     * almacenado que se encarga de Actualizar la tupla seleccionada de la tabla
     * de la base de datos.
     */
    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarClientes(?,?,?,?,?,?,?)}");
            Clientes registro = ((Clientes) tblClientes.getSelectionModel().getSelectedItem());
            registro.setNombresCliente((txtNombresCliente.getText()));
            registro.setApellidosCliente((txtApellidosCliente.getText()));
            registro.setNITClientes((txtNitCliente.getText()));
            registro.setTelefonoCliente((txtTelefonoCliente.getText()));
            registro.setDireccionCliente((txtDireccionCliente.getText()));
            registro.setCorreoCliente((txtCorreoCliente.getText()));
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITClientes());
            procedimiento.setString(3, registro.getNombresCliente());
            procedimiento.setString(4, registro.getApellidosCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Se encarga de desactivar los textFiel.
     */
    public void desactivarControles() {
        txtCodigoCliente.setEditable(false);
        txtNombresCliente.setEditable(false);
        txtApellidosCliente.setEditable(false);
        txtNitCliente.setEditable(false);
        txtTelefonoCliente.setEditable(false);
        txtDireccionCliente.setEditable(false);
        txtCorreoCliente.setEditable(false);
    }

    /**
     * Se encarga de activar los textField.
     */
    public void activarControles() {
        txtCodigoCliente.setEditable(true);
        txtNombresCliente.setEditable(true);
        txtApellidosCliente.setEditable(true);
        txtNitCliente.setEditable(true);
        txtTelefonoCliente.setEditable(true);
        txtDireccionCliente.setEditable(true);
        txtCorreoCliente.setEditable(true);
    }

    /**
     * Se encarga de limpia los textField.
     */
    public void limpiarControles() {
        txtCodigoCliente.clear();
        txtNombresCliente.clear();
        txtApellidosCliente.clear();
        txtNitCliente.clear();
        txtTelefonoCliente.clear();
        txtDireccionCliente.clear();
        txtCorreoCliente.clear();
    }
}
