
package org.samuelperez.controller;

import org.samuelperez.system.Principal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * @author Nombre: Samuel Alexander Perez Cap
 * Carnet: 2020493  Grado:IN5BM
 */


/**
 * Controlador del menú principal.
 * La Clase se encarga de las acciones y eventos del menú principal.
*/
public class MenuPrincipalController implements Initializable {
    private Principal escenarioPrincipal;
  
    @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnMenuProgramador;
    @FXML MenuItem btnMenuProveedores;
    @FXML MenuItem btnMenuCargoEmpleado;
    @FXML MenuItem btnTipoProducto;
    @FXML MenuItem btnMenuProductos;
    @FXML MenuItem btnMenuCompras;
    @FXML MenuItem btnMenuFactura;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

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
     * Se encarga de Manejar las Acciones de los botones.
     * 
     * @param event el evento que ocurrio en ese momento.
     */
    @FXML
    public void handleButtonAction (ActionEvent event){
        if(event.getSource() == btnMenuClientes){
            escenarioPrincipal.menuClientesView();
        }

        if(event.getSource() == btnMenuProgramador){
            escenarioPrincipal.menuProgramadorView();
        }

        if(event.getSource() == btnMenuProveedores){
            escenarioPrincipal.menuProveedoresView();
        }

        if(event.getSource() == btnMenuCargoEmpleado){
            escenarioPrincipal.menuCargoEmpleadoView();
        }
        if(event.getSource() == btnTipoProducto){
            escenarioPrincipal.menuTipoProductoView();
        }
        if(event.getSource() == btnMenuProductos){
            escenarioPrincipal.menuProductosView();
        }
        if(event.getSource() == btnMenuCompras){
            escenarioPrincipal.menuComprasView();
        }
        if(event.getSource() == btnMenuFactura){
            escenarioPrincipal.menuFacturaView();
        }
    }
    
}
