/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.samuelperez.bean.CargoEmpleado;
import org.samuelperez.bean.Empleados;
import org.samuelperez.db.Conexion;
import org.samuelperez.system.Principal;

/**
 * @author Nombre: Samuel Alexander Perez Cap Carnet: 2020493 Grado:IN5BM
 */
/**
 * Controlador del menú principal. La Clase se encarga de las acciones y eventos
 * del menú principal.
 */
public class MenuEmpleadosController implements Initializable {

    private Principal escenarioPrincipal;
    
    // Lista observable para almacenar objetos de tipo 'Empleado'
    private ObservableList<Empleados> listaEmpleados;
    
    // Lista observable para almacenar objetos de tipo 'CargoEmpleado'
    private ObservableList<CargoEmpleado> listaCargoEmpleado;

    // Enumeración para representar las diferentes operaciones que se pueden realizar
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    };

    // Variable para almacenar la operación actual
    private MenuEmpleadosController.operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private Button btnRegresar;

    @FXML
    private TableView tblEmpleados;

    @FXML
    private TableColumn colEmpleado;

    @FXML
    private TableColumn colNombresE;

    @FXML
    private TableColumn colApellidosE;

    @FXML
    private TableColumn colSueldoE;

    @FXML
    private TableColumn colDireccionE;

    @FXML
    private TableColumn colTurnoE;

    @FXML
    private TableColumn colCargoEmpleadoE;

    @FXML
    private TextField txtCodigoEmplado;

    @FXML
    private TextField txtNombresE;

    @FXML
    private TextField txtApelldosE;

    @FXML
    private TextField txtSueldoE;

    @FXML
    private TextField txtDireccionE;

    @FXML
    private TextField txtTurnoE;

    @FXML
    private ComboBox cmbCodigoCargoEmpleado;

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
        cmbCodigoCargoEmpleado.setItems(getCargoEmpleado());
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
        tblEmpleados.setItems(getEmpleados());
        colEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoEmpleado"));
        colNombresE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombresEmpleado"));
        colApellidosE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidosEmpleado"));
        colSueldoE.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colDireccionE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccion"));
        colTurnoE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("turno"));
        colCargoEmpleadoE.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("CodigoCargoEmpleado"));
    }

    /**
     * El metodo Actualiza los campos de los txt seleccionando la tabla a la que
     * se encarga de mostrar El procedimiento se llama cuando se selecciona un
     * elemento de la tabla.
     */
    public void seleccionarElemento() {
        txtCodigoEmplado.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNombresE.setText((((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado()));
        txtApelldosE.setText((((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado()));
        txtSueldoE.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        txtDireccionE.setText((((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getDireccion()));
        txtTurnoE.setText((((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getTurno()));
        cmbCodigoCargoEmpleado.getSelectionModel().select(buscarCargoEmpleado(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
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
    public CargoEmpleado buscarCargoEmpleado(int codigoCargoEmpleado) {
        CargoEmpleado resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarCargoEmpleado(?);");
            procedimiento.setInt(1, codigoCargoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new CargoEmpleado(registro.getInt("codigoCargoEmpleado"),
                        registro.getString("nombreCargo"),
                        registro.getString("descripcionCargo"));
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
     * El metodo realizar la conexion con el procedimiento almacenado que se
     * encarga de listar las informacion de la tabla y todas sus tuplas, esta
     * funciona con una ArrayList para poder manejar los datos.
     *
     * @return una lista que se puede observar de los Empleados.
     */
    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<>();
        ResultSet resultado = null;

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarEmpleados();");
            resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("CodigoCargoEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableList(lista);

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
        Empleados registro = new Empleados();
        registro.setCodigoEmpleado(Integer.parseInt(txtCodigoEmplado.getText()));
        registro.setNombresEmpleado(txtNombresE.getText());
        registro.setApellidosEmpleado(txtApelldosE.getText());
        registro.setSueldo(Double.parseDouble(txtSueldoE.getText()));
        registro.setDireccion(txtDireccionE.getText());
        registro.setTurno(txtTurnoE.getText());
        registro.setCodigoCargoEmpleado(((CargoEmpleado) cmbCodigoCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarEmpleados(?,?,?,?,?,?,?);");
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
            procedimiento.execute();
            listaEmpleados.add(registro);
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la Eliminacion del Registro", "Eliminar un Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleados(?)}");
                            procedimiento.setInt(1, ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar Un Empleado para Eliminar");
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReport.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/samuelperez/images/MenuClientes_IconActualizar.png"));
                    imgReport.setImage(new Image("/org/samuelperez/images/MenuClientes_IconCancelar.png"));
                    activarControles();
                    txtCodigoEmplado.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmpleados(?,?,?,?,?,?,?)}");
            Empleados registro = ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem());
            registro.setCodigoEmpleado(Integer.parseInt(txtCodigoEmplado.getText()));
            registro.setNombresEmpleado(txtNombresE.getText());
            registro.setApellidosEmpleado(txtApelldosE.getText());
            registro.setSueldo(Double.parseDouble(txtSueldoE.getText()));
            registro.setDireccion(txtDireccionE.getText());
            registro.setTurno(txtTurnoE.getText());
            registro.setCodigoCargoEmpleado(((CargoEmpleado) cmbCodigoCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
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
        txtCodigoEmplado.setEditable(false);
        txtNombresE.setEditable(false);
        txtApelldosE.setEditable(false);
        txtSueldoE.setEditable(false);
        txtDireccionE.setEditable(false);
        txtTurnoE.setEditable(false);
        cmbCodigoCargoEmpleado.setDisable(true);
    }

    /**
     * Se encarga de activar los textFiel.
     */
    public void activarControles() {
        txtCodigoEmplado.setEditable(true);
        txtNombresE.setEditable(true);
        txtApelldosE.setEditable(true);
        txtSueldoE.setEditable(true);
        txtDireccionE.setEditable(true);
        txtTurnoE.setEditable(true);
        cmbCodigoCargoEmpleado.setDisable(false);
    }

    /**
     * Se encarga de limpiar los textFiel.
     */
    public void limpiarControles() {
        txtCodigoEmplado.clear();
        txtNombresE.clear();
        txtApelldosE.clear();
        txtSueldoE.clear();
        txtDireccionE.clear();
        txtTurnoE.clear();
        tblEmpleados.getSelectionModel().getSelectedItem();
        cmbCodigoCargoEmpleado.getSelectionModel().getSelectedItem();
    }
}
