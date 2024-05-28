package org.samuelperez.system;

import java.io.InputStream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.samuelperez.controller.MenuCargoEmpleadoController;
import org.samuelperez.controller.MenuClientesController;
import org.samuelperez.controller.MenuComprasController;
import org.samuelperez.controller.MenuDetalleComprasController;
import org.samuelperez.controller.MenuDetalleFacturaController;
import org.samuelperez.controller.MenuEmailProveedorController;
import org.samuelperez.controller.MenuEmpleadosController;
import org.samuelperez.controller.MenuFacturaController;
import org.samuelperez.controller.MenuPrincipalController;
import org.samuelperez.controller.MenuProductosController;
import org.samuelperez.controller.MenuProgramadorController;
import org.samuelperez.controller.MenuProveedoresController;
import org.samuelperez.controller.MenuTipoProductoController;

/**
 * @author Nombre: Samuel Alexander Perez Cap
 * Carnet: 2020493  Grado:IN5BM
 * 
 * @version 26/04/2024
 * Fecha de Modificacion:26/04/2024
 */

public class Principal extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;

    /**
     * Este metodo se encrga de llamar al inicio de la aplicacion.
     * 
     * @param escenarioPrincipal Es el eceneario pricipal de la aplicacion.
     * @throws Exception se encarga si ocurre algun error al cargar la vista del Menu.
     */
    
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Express");
        escenarioPrincipal.getIcons().add(new Image ("/org/samuelperez/images/KinalEpress_Logo.png"));
            menuPrincipalView();
        
        //Parent root = FXMLLoader.load(getClass().getResource("/org/samuelperez/view/MenuPrincipalView.fxml"));
        //Scene escena = new Scene(root);
        // escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    /**
     * Este metodo Public es el encargado de cambiar la escena que se muestra en el 
     * ecenario Principal.
     * 
     * @param fxmlname el nombre del archivo fxml de la vista a mostrar.
     * @param width Es el ancho de la ecena mostrada.
     * @param heigth Es la altura de la ecena mostrada.
     * @return Es el controlador de la vista.
     * @throws Exception Se encarga si sucede algun error al cargar la vista.
     */
    
    public Initializable cambiarEscena(String fxmlname, int width, int heigth) throws Exception {
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        InputStream file = Principal.class.getResourceAsStream("/org/samuelperez/view/" + fxmlname);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Principal.class.getResource("/org/samuelperez/view/" + fxmlname));

        escena = new Scene((AnchorPane) loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();

        resultado = (Initializable) loader.getController();

        return resultado;
    }

    /**
     * Este metodo PUBLIC VOID es el encargado de cargar la vista Principal de 
     * la Aplicacion.
     */
    public void menuPrincipalView() {
        try{
            MenuPrincipalController MenuPrincipalView = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 1280, 720);
            MenuPrincipalView.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();

        }
    }

    /**
     * Este metodo PUBLIC VOID es el encargado de cargar la vista de la Gestion de
     * los Clientes (MenuClientes.fxml).
     */
    public void menuClientesView() {
        try{
            MenuClientesController MenuClientes = (MenuClientesController)cambiarEscena("MenuClientes.fxml", 1280, 720);
            MenuClientes.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    
    /**
     * Este metodo PUBLIC VOID es el encargado de cargar la vista de la informacion
     * del Programador del programa.
     */
    public void menuProgramadorView() {
        try{
            MenuProgramadorController MenuProgramador = (MenuProgramadorController)cambiarEscena("MenuProgramador.fxml", 1280, 720);
            MenuProgramador.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    public void menuProveedoresView() {
        try{
            MenuProveedoresController MenuProveedores = (MenuProveedoresController)cambiarEscena("MenuProveedores.fxml", 1280, 720);
            MenuProveedores.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    
    public void menuCargoEmpleadoView() {
        try{
            MenuCargoEmpleadoController MenuCargoEmpleado = (MenuCargoEmpleadoController)cambiarEscena("MenuTipoEmpleado.fxml", 1280, 720);
            MenuCargoEmpleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }    

    
    public void menuTipoProductoView() {
        try{
            MenuTipoProductoController MenuTipoProducto = (MenuTipoProductoController)cambiarEscena("MenuTipoProducto.fxml", 1280, 720);
            MenuTipoProducto.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuProductosView() {
        try{
            MenuProductosController MenuProductos = (MenuProductosController)cambiarEscena("MenuProductos.fxml", 1280, 720);
            MenuProductos.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuComprasView() {
        try{
            MenuComprasController MenuCompras = (MenuComprasController)cambiarEscena("MenuCompras.fxml", 1280, 720);
            MenuCompras.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuDetalleCompraView() {
        try{
            MenuDetalleComprasController MenuDetalleCompras = (MenuDetalleComprasController)cambiarEscena("MenuDetalleCompras.fxml", 1280, 720);
            MenuDetalleCompras.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
       }
    }
    
    public void menuFacturaView() {
        try{
            MenuFacturaController MenuFactura = (MenuFacturaController)cambiarEscena("MenuFactura.fxml", 1280, 720);
            MenuFactura.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuDetalleFacturaView() {
        try{
            MenuDetalleFacturaController MenuDetalleFactura = (MenuDetalleFacturaController)cambiarEscena("MenuDetalleFactura.fxml", 1280, 720);
            MenuDetalleFactura.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    
    public void menuEmailView() {
        try{
            MenuEmailProveedorController MenuEmail = (MenuEmailProveedorController)cambiarEscena("MenuEmailProveedor.fxml", 1280, 720);
            MenuEmail.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuEmpleadosView() {
        try{
            MenuEmpleadosController MenuEmpleado = (MenuEmpleadosController)cambiarEscena("MenuEmpleados.fxml", 1280, 720);
            MenuEmpleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
