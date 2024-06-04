
package org.samuelperez.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Nombre: Samuel Alexander Perez Cap
 * Carnet: 2020493  Grado:IN5BM
 */

public class Conexion {

    private Connection conexion;//Es la conexion a la base de datos.
    private static Conexion instancia;//Es la instancia única de la clase Conexion.

    
    /**
     * El constructor se engarga de establecer la conexión a la base de datos.
     */
    public Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBKinalExpress2020493?useSSL=false", "2020493_IN5BM", "abc123**");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException a) {
            a.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * El método se engarga de devolver la instancia única de la clase Conexion.
     *Si la insdtancia no existe este crea una nueva.
     * 
     * @return la instancia única de la clase Conexion.
     */
    public static Conexion getInstance(){
        if(instancia == null){
           instancia = new Conexion();
        }
        return instancia;
    }

    /**
     * El método se engarga de devolver la conexión a la base de datos.
     * 
     * @return la conexión a la base de datos.
     */
    public Connection getConexion() {
        return conexion;
    }

    
    /**
     * El metodo establece la conexión a la base de datos de la Aplicacion
     * KinalExpress.
     * 
     * @param conexion la conexión a la base de datos.
     */
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

}
