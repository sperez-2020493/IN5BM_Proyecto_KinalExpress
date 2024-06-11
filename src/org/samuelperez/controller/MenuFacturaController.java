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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.samuelperez.bean.Clientes;
import org.samuelperez.bean.Empleados;
import org.samuelperez.bean.Factura;
import org.samuelperez.bean.Productos;
import org.samuelperez.db.Conexion;
import org.samuelperez.report.GenerarReportes;
import org.samuelperez.system.Principal;

/**
 * @author Nombre: Samuel Alexander Perez Cap Carnet: 2020493 Grado:IN5BM
 */
public class MenuFacturaController implements Initializable {

    private Principal escenarioPrincipal;

    // Lista observable para almacenar objetos de tipo 'Factura'
    private ObservableList<Factura> listaFactura;

    // Lista observable para almacenar objetos de tipo 'Clientes'
    private ObservableList<Clientes> listaClientes;

    // Lista observable para almacenar objetos de tipo 'Empleados'
    private ObservableList<Empleados> listaEmpleados;

    // Enumeración para representar las diferentes operaciones que se pueden realizar
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    };

    // Variable para almacenar la operación actual
    private MenuFacturaController.operaciones tipoDeOperaciones = operaciones.NINGUNO;

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
    private TableView tblFactura;

    @FXML
    private TableColumn colFactura;

    @FXML
    private TableColumn colEstadoE;

    @FXML
    private TableColumn colTotalF;

    @FXML
    private TableColumn colFechaF;

    @FXML
    private TableColumn colCodigoClienteF;

    @FXML
    private TableColumn colCodigoEmpleadoF;

    @FXML
    private ComboBox cmbCodigoCliente;

    @FXML
    private ComboBox cmbCodigoEmpleado;

    @FXML
    private TextField txtFacturaF;

    @FXML
    private TextField txtEstadoF;

    @FXML
    private TextField txtTotalF;

    @FXML
    private TextField txtFechaF;

    @FXML
    private Button btnDetalleFactura;

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
        cmbCodigoCliente.setItems(getClientes());
        cmbCodigoEmpleado.setItems(getEmpleados());
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
        if (event.getSource() == btnDetalleFactura) {
            escenarioPrincipal.menuDetalleFacturaView();
        }
    }

    /**
     * El metodo se encarga de cargar los datos de la tabla en la vista, segun
     * el nombre de cada columna.
     */
    public void cargarDatos() {
        tblFactura.setItems(getFacturas());
        colFactura.setCellValueFactory(new PropertyValueFactory<>("numeroFactura"));
        colEstadoE.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colTotalF.setCellValueFactory(new PropertyValueFactory<>("totalFactura"));
        colFechaF.setCellValueFactory(new PropertyValueFactory<>("fechaFactura"));
        colCodigoClienteF.setCellValueFactory(new PropertyValueFactory<>("codigoCliente"));
        colCodigoEmpleadoF.setCellValueFactory(new PropertyValueFactory<>("codigoEmpleado"));
    }

    /**
     * El metodo Actualiza los campos de los txt seleccionando la tabla a la que
     * se encarga de mostrar El procedimiento se llama cuando se selecciona un
     * elemento de la tabla.
     */
    public void seleccionarElemento() {
        txtFacturaF.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        txtEstadoF.setText((((Factura) tblFactura.getSelectionModel().getSelectedItem()).getEstado()));
        txtTotalF.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getTotalFactura()));
        txtFechaF.setText((((Factura) tblFactura.getSelectionModel().getSelectedItem()).getFechaFactura()));
        cmbCodigoCliente.getSelectionModel().select(buscarCodigoCliente(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        cmbCodigoEmpleado.getSelectionModel().select(buscarCodigoEmpleado(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
    }

    /**
     * Este método se utiliza para buscar una Tipo de Producto en la base de
     * datos realizando una conexion al metodo almacenado que se encarga de
     * buscar.
     *
     * @param codigoCliente El codigo de tipo Producto de la tabla a buscar.
     * @return La compra encontrada, o null si no se encuentra ninguna compra.
     */
    public Clientes buscarCodigoCliente(int codigoCliente) {
        Clientes resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarClientes(?);");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Clientes(registro.getInt("codigoCliente"),
                        registro.getString("NITClientes"),
                        registro.getString("nombresCliente"),
                        registro.getString("apellidosCliente"),
                        registro.getString("direccionCliente"),
                        registro.getString("telefonoCliente"),
                        registro.getString("correoCliente"));
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
     * @param codigoCargoEmpleado El codigo de tipo Producto de la tabla a
     * buscar.
     * @return La compra encontrada, o null si no se encuentra ninguna compra.
     */
    public Empleados buscarCodigoEmpleado(int codigoEmpleado) {
        Empleados resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_BuscarEmpleados(?);");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Empleados(registro.getInt("codigoEmpleado"),
                        registro.getString("nombresEmpleado"),
                        registro.getString("apellidosEmpleado"),
                        registro.getDouble("sueldo"),
                        registro.getString("direccion"),
                        registro.getString("turno"),
                        registro.getInt("CodigoCargoEmpleado"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

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
        Factura registro = new Factura();
        registro.setNumeroFactura(Integer.parseInt(txtFacturaF.getText()));
        registro.setEstado(txtEstadoF.getText());
        registro.setTotalFactura(Double.parseDouble(txtTotalF.getText()));
        registro.setFechaFactura(txtFechaF.getText());
        registro.setCodigoCliente(((Clientes) cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoEmpleado(((Empleados) cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarFactura(?,?,?,?,?,?);");
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());
            procedimiento.execute();
            listaFactura.add(registro);
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
                if (tblFactura.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la Eliminacion del Registro", "Eliminar Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmailProveedor(?)}");
                            procedimiento.setInt(1, ((Factura) tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
                            procedimiento.execute();
                            listaFactura.remove(tblFactura.getSelectionModel().getSelectedItem());
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
                if (tblFactura.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReport.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/samuelperez/images/MenuClientes_IconActualizar.png"));
                    imgReport.setImage(new Image("/org/samuelperez/images/MenuClientes_IconCancelar.png"));
                    activarControles();
                    txtFacturaF.setEditable(false);
                    tipoDeOperaciones = MenuFacturaController.operaciones.ACTUALIZAR;
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarFactura(?,?,?,?,?,?)}");
            Factura registro = (Factura) tblFactura.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(Integer.parseInt(txtFacturaF.getText()));
            registro.setEstado(txtEstadoF.getText());
            registro.setTotalFactura(Double.parseDouble(txtTotalF.getText()));
            registro.setFechaFactura(txtFechaF.getText());
            registro.setCodigoCliente(((Clientes) cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            registro.setCodigoEmpleado(((Empleados) cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());
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
        parametros.put("numeroFactura", null);
        GenerarReportes.mostrarReportes("ReporteFactura.jasper", "Reporte de Factura", parametros);
    }
    
    /**
     * Se encarga de desactivar los textFiel.
     */
    public void desactivarControles() {
        txtFacturaF.setEditable(false);
        txtEstadoF.setEditable(false);
        txtTotalF.setEditable(false);
        txtFechaF.setEditable(false);
        cmbCodigoCliente.setDisable(true);
        cmbCodigoEmpleado.setDisable(true);
    }

    /**
     * Se encarga de activar los textFiel.
     */
    public void activarControles() {
        txtFacturaF.setEditable(true);
        txtEstadoF.setEditable(true);
        txtTotalF.setEditable(true);
        txtFechaF.setEditable(true);
        cmbCodigoCliente.setDisable(false);
        cmbCodigoEmpleado.setDisable(false);
    }

    /**
     * Se encarga de limpiar los textFiel.
     */
    public void limpiarControles() {
        txtFacturaF.clear();
        txtEstadoF.clear();
        txtTotalF.clear();
        txtFechaF.clear();
        tblFactura.getSelectionModel().getSelectedItem();
        cmbCodigoCliente.getSelectionModel().getSelectedItem();
        cmbCodigoEmpleado.getSelectionModel().getSelectedItem();
    }
}
