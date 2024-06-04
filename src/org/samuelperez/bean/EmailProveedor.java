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
public class EmailProveedor {

    /**
     * Los Atrbutos de la clase.
     */
    private int codigoEmailProveedor;
    private String emailProveedor;
    private String descripcion;
    private int codigoProveedor;

    /**
     * El constructor vacío de la clase.
     */
    public EmailProveedor() {

    }

    public EmailProveedor(int codigoEmailProveedor, String emailProveedor, String descripcion, int codigoProveedor) {
        this.codigoEmailProveedor = codigoEmailProveedor;
        this.emailProveedor = emailProveedor;
        this.descripcion = descripcion;
        this.codigoProveedor = codigoProveedor;
    }

    public int getCodigoEmailProveedor() {
        return codigoEmailProveedor;
    }

    public void setCodigoEmailProveedor(int codigoEmailProveedor) {
        this.codigoEmailProveedor = codigoEmailProveedor;
    }

    public String getEmailProveedor() {
        return emailProveedor;
    }

    public void setEmailProveedor(String emailProveedor) {
        this.emailProveedor = emailProveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }


}
