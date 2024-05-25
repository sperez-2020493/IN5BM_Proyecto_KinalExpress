/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.samuelperez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.samuelperez.system.Principal;

/**
 * @author Nombre: Samuel Alexander Perez Cap
 * Carnet: 2020493  Grado:IN5BM
 */

public class MenuEmailProveedorController implements Initializable {
    
    private Principal escenarioPrincipal;
        
    @FXML
    private TableView tblTipoProducto;

    @FXML
    private TableColumn colEmailProveedorE;

    @FXML
    private TableColumn colEmailE;

    @FXML
    private TableColumn colDescripcionE;

    @FXML
    private TableColumn colProveedoresE;

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
    private TextField txtCodigoProveedor;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtCodigoEmailProveedor;

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

    /**
     * Se encarga de Manejar las Acciones de los botones.
     * 
     * @param event el evento que ocurrio en ese momento.
     */
    public void handleButtonAction (ActionEvent event){
        if(event.getSource() == btnRegresar){
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
