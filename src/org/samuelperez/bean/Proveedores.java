/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.samuelperez.bean;

/**
 *
 * @author David Perez
 */
public class Proveedores {

    public static void add(Proveedores registro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private int codigoProveedor;
    private String NITProveedor;
    private String nombresProveedor;
    private String apellidosProveedor;
    private String direccionProveedor;
    private String razonSocial;
    private String contactoPrincipal;
    private String paginaWeb;

    
    public Proveedores(){
    
    }

    public Proveedores(int codigoProveedor, String NITProveedor, String nombresProveedor, String apellidosProveedor, String direccionProveedor, String razonSocial, String contactoPrincipal, String paginaWeb) {
        this.codigoProveedor = codigoProveedor;
        this.NITProveedor = NITProveedor;
        this.nombresProveedor = nombresProveedor;
        this.apellidosProveedor = apellidosProveedor;
        this.direccionProveedor = direccionProveedor;
        this.razonSocial = razonSocial;
        this.contactoPrincipal = contactoPrincipal;
        this.paginaWeb = paginaWeb;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNITProveedor() {
        return NITProveedor;
    }

    public void setNITProveedor(String NITProveedor) {
        this.NITProveedor = NITProveedor;
    }

    public String getNombresProveedor() {
        return nombresProveedor;
    }

    public void setNombresProveedor(String nombresProveedor) {
        this.nombresProveedor = nombresProveedor;
    }

    public String getApellidosProveedor() {
        return apellidosProveedor;
    }

    public void setApellidosProveedor(String apellidosProveedor) {
        this.apellidosProveedor = apellidosProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getContactoPrincipal() {
        return contactoPrincipal;
    }

    public void setContactoPrincipal(String contactoPrincipal) {
        this.contactoPrincipal = contactoPrincipal;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    @Override
    public String toString() {
        return "Proveedores{"  + getCodigoProveedor() + getNITProveedor() + getNombresProveedor() +'}';
    }


    
    
}
