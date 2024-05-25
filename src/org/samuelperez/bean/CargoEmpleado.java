
package org.samuelperez.bean;

/**
 * @author Nombre: Samuel Alexander Perez Cap
 * Carnet: 2020493  Grado:IN5BM
 */

public class CargoEmpleado {
    
    /**
    *Los Atrbutos de la clase.
    */
    private int codigoCargoEmpleado;
    private String nombreCargo;
    private String descripcionCargo;

    
    /**
     * El constructor vacío de la clase.
     */
    public CargoEmpleado(){
    
    } 

    
    /**
     * El constructor de la clase Clientes.
     * 
     * @param codigoCargoEmpleado codigo identificador de Cargo Empleado.
     * @param nombreCargo nombre del cargo.
     * @param descripcionCargo descripcion del cargo.
     */
    public CargoEmpleado(int codigoCargoEmpleado, String nombreCargo, String descripcionCargo) {
        this.codigoCargoEmpleado = codigoCargoEmpleado;
        this.nombreCargo = nombreCargo;
        this.descripcionCargo = descripcionCargo;
    }

    /**
     * Getters y setters de la Clase Cliente.
     */
    public int getCodigoCargoEmpleado() {
        return codigoCargoEmpleado;
    }

    public void setCodigoCargoEmpleado(int codigoCargoEmpleado) {
        this.codigoCargoEmpleado = codigoCargoEmpleado;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }
    
    @Override
    public String toString() {
        return "| " + getCodigoCargoEmpleado() + " | " + getNombreCargo() + " | ";
    } 
}
