
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.samuelperez.bean.CargoEmpleado;
import org.samuelperez.db.Conexion;
import org.samuelperez.system.Principal;

/**
 * @author Nombre: Samuel Alexander Perez Cap
 * Carnet: 2020493  Grado:IN5BM
 */

public class MenuCargoEmpleadoController implements Initializable {

    // Referencia al escenario principal de la aplicación
    private Principal escenarioPrincipal;
    
    // Lista observable para almacenar objetos de tipo 'Cargo Empleado'
    private ObservableList<CargoEmpleado> listaCargoEmpleado;

    // Enumeración para representar las diferentes operaciones que se pueden realizar
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    };

    // Variable para almacenar la operación actual
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    
    // Botones,tabla, columnas e imágenes de la interfaz de la vista 
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
    private TableView tblCargoEmpleado;

    @FXML
    private TableColumn colCargoEmpleadoC;

    @FXML
    private TableColumn colNombreCargoC;

    @FXML
    private TableColumn colDescripcionC;

    @FXML
    private TextField txtCodigoCargoEmpleado;

    @FXML
    private TextField txtDescripcionCargoEmpleado;

    @FXML
    private TextField txtNombreCargoEmpleado;

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
     * metodo en el.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
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
        tblCargoEmpleado.setItems(getCargoEmpleado());
        colCargoEmpleadoC.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("codigoCargoEmpleado"));
        colNombreCargoC.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("nombreCargo"));
        colDescripcionC.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("descripcionCargo"));
    }

    /**
     * El metodo Actualiza los campos de los txt seleccionando la tabla a la que
     * se encarga de mostrar El procedimiento se llama cuando se selecciona un
     * elemento de la tabla.
     */
    public void seleccionarElemento() {
        txtCodigoCargoEmpleado.setText(String.valueOf(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        txtNombreCargoEmpleado.setText(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getNombreCargo());
        txtDescripcionCargoEmpleado.setText(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getDescripcionCargo());
    }

    /**
     * El metodo realizar la conexion con el procedimiento almacenado que se
     * encarga de listar las informacion de la tabla y todas sus tuplas, esta
     * funciona con una ArrayList para poder manejar los datos.
     * 
     * @return una lista que se puede observar de los CargoEmpleado.
     */
    public ObservableList<CargoEmpleado> getCargoEmpleado() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarCargoEmpleado();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCargoEmpleado = FXCollections.observableList(lista);

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
        CargoEmpleado registro = new CargoEmpleado();
        registro.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCargoEmpleado.getText()));
        registro.setNombreCargo((txtNombreCargoEmpleado.getText()));
        registro.setDescripcionCargo((txtDescripcionCargoEmpleado.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCargoEmpleado(?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
            listaCargoEmpleado.add(registro);

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
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la Eliminacion del Registro", "Eliminar CARGO EMPLEADO", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCargoEmpleado(?)}");
                            procedimiento.setInt(1, ((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            procedimiento.execute();
                            listaCargoEmpleado.remove(tblCargoEmpleado.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un Cargo tipo de Encargo para Eliminar");
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
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReport.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/samuelperez/images/MenuClientes_IconActualizar.png"));
                    imgReport.setImage(new Image("/org/samuelperez/images/MenuClientes_IconCancelar.png"));
                    activarControles();
                    txtCodigoCargoEmpleado.setEditable(false);
                    tipoDeOperaciones = MenuCargoEmpleadoController.operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Cargo");
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
     * El metodo actualizar se encarga de realizar la conexion al procedimiento
     * almacenado que se encarga de Actualizar la tupla seleccionada de la tabla
     * de la base de datos.
     */
    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarCargoEmpleado(?,?,?)}");
            CargoEmpleado registro = (CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem();
            registro.setNombreCargo((txtNombreCargoEmpleado.getText()));
            registro.setDescripcionCargo((txtDescripcionCargoEmpleado.getText()));
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Se encarga de desactivar los textFiel.
     */
    public void desactivarControles() {
        txtCodigoCargoEmpleado.setEditable(false);
        txtNombreCargoEmpleado.setEditable(false);
        txtDescripcionCargoEmpleado.setEditable(false);
    }

    /**
     * Se encarga de activar los textFiel.
     */
    public void activarControles() {
        txtCodigoCargoEmpleado.setEditable(true);
        txtNombreCargoEmpleado.setEditable(true);
        txtDescripcionCargoEmpleado.setEditable(true);
    }

    /**
     * Se encarga de limpiar los textFiel.
     */
    public void limpiarControles() {
        txtCodigoCargoEmpleado.clear();
        txtNombreCargoEmpleado.clear();
        txtDescripcionCargoEmpleado.clear();
    }
}
