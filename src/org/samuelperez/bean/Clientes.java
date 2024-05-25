
package org.samuelperez.bean;
/**
 * @author Nombre: Samuel Alexander Perez Cap
 * Carnet: 2020493  Grado:IN5BM
 */
public class Clientes {
    
    /**
    *Los Atrbutos de la clase.
    */
    private int codigoCliente;
    private String NITClientes;
    private String nombresCliente;
    private String apellidosCliente;
    private String direccionCliente;
    private String telefonoCliente;
    private String correoCliente;


    /**
     * El constructor vacío de la clase.
     */
    public Clientes(){

    }

    /**
     * El constructor de la clase Clientes.
     * 
     * @param codigoCliente el identificador unico del cliente.
     * @param NITClientes el codigo Nit del cliente.
     * @param nombresCliente los nombres del cliente.
     * @param apellidosCliente los apellidos del cliente.
     * @param direccionCliente la dirección de vivienda del cliente.
     * @param telefonoClente el numero telefonico del cliente.
     * @param correoCliente el correo electronico del cliente.
     */
    public Clientes(int codigoCliente, String NITClientes, String nombresCliente, String apellidosCliente, String direccionCliente, String telefonoCliente, String correoCliente) {
        this.codigoCliente = codigoCliente;
        this.NITClientes = NITClientes;
        this.nombresCliente = nombresCliente;
        this.apellidosCliente = apellidosCliente;
        this.direccionCliente = direccionCliente;
        this.telefonoCliente = telefonoCliente;
        this.correoCliente = correoCliente;
    }

    /**
     * Getters y setters de la Clase Cliente.
     */
    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNITClientes() {
        return NITClientes;
    }

    public void setNITClientes(String NITClientes) {
        this.NITClientes = NITClientes;
    }

    public String getNombresCliente() {
        return nombresCliente;
    }

    public void setNombresCliente(String nombresCliente) {
        this.nombresCliente = nombresCliente;
    }

    public String getApellidosCliente() {
        return apellidosCliente;
    }

    public void setApellidosCliente(String apellidosCliente) {
        this.apellidosCliente = apellidosCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoClente) {
        this.telefonoCliente = telefonoClente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

}