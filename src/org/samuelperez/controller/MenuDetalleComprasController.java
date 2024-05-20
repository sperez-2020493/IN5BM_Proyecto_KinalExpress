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
import org.samuelperez.system.Principal;

/**
 *
 * @author David Perez
 */
public class MenuDetalleComprasController implements Initializable {
        private Principal escenarioPrincipal;
        
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
    @FXML Button btnRegresar;
    public void handleButtonAction (ActionEvent event){
        if(event.getSource() == btnRegresar){
            escenarioPrincipal.menuPrincipalView();
        }
    }   
}
