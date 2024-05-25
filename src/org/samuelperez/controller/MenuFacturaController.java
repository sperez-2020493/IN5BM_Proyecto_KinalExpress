
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
import org.samuelperez.bean.Clientes;
import org.samuelperez.bean.Empleados;
import org.samuelperez.bean.Factura;
import org.samuelperez.bean.Productos;
import org.samuelperez.db.Conexion;
import org.samuelperez.system.Principal;

/**
 * @author Nombre: Samuel Alexander Perez Cap
 * Carnet: 2020493  Grado:IN5BM
 */

public class MenuFacturaController implements Initializable {

    private Principal escenarioPrincipal;
    private ObservableList<Factura> listaFactura;
    private ObservableList<Clientes> listarClientes;
    private ObservableList<Empleados> listarEmpleados;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    };

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
        // TODO
    }

    public void cargarDatos() {
        tblFactura.setItems(getFacturas());
        colFactura.setCellValueFactory(new PropertyValueFactory<>("numeroFactura"));
        colEstadoE.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colTotalF.setCellValueFactory(new PropertyValueFactory<>("totalFactura"));
        colFechaF.setCellValueFactory(new PropertyValueFactory<>("fechaFactura"));
        colCodigoEmpleadoF.setCellValueFactory(new PropertyValueFactory<>("codigoCliente"));
        colCodigoEmpleadoF.setCellValueFactory(new PropertyValueFactory<>("codigoEmpleado"));
    }

    public void seleccionarElemento() {

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
                        resultado.getInt("clienteID"),
                        resultado.getInt("codigoEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaFactura = FXCollections.observableList(lista);
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
