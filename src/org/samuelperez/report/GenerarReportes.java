
package org.samuelperez.report;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.samuelperez.db.Conexion;

/**
 *
 * @author informatica
 */
public class GenerarReportes {
    public static void mostrarReportes(String nombreReporte, String titulo, Map parametros){
       InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
       try{
           JasperReport ReporteClientes2 = (JasperReport)JRLoader.loadObject(reporte);
           JasperPrint reporteImpreso = JasperFillManager.fillReport(ReporteClientes2, parametros, Conexion.getInstance().getConexion());
           JasperViewer visor = new JasperViewer(reporteImpreso, false);
           visor.setTitle(titulo);
           visor.setVisible(true);
       }catch(Exception e){
           e.printStackTrace();
       }
        
    }
}

/*
Interface MAP
    HashMap es uno de los objetos que implementa un conjubnto de ket-values.
    Tiene un controlador sin parametros new HashMap() y su finalidad suele referirse para agrupar
    informacion en un unico objeto.

    Tiene cierta similitud con la coleccion de objetos (ArrayList) pero con la
    diferencia que estos no tienen orden.

Hash hace referencia a una tecnica de organizacion de archivos, hashing (abierto-cerrado) en la cual
se almacena registros en una direccion que es generada por una funcion se aplica a la llave del registro

En java el Hash posee un espacio de memoria y cuando se guarda un objeto en dichop espacio se determina
su direccion aplicando una funcion a la llave que le indiquemos. Cada objeto se idebntifica mediante algun identificador
apropiado.
*/