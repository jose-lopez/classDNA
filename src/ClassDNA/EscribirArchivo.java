/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassDNA;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author cypres
 */
public class EscribirArchivo {
    
    private String ruta;
    private boolean permiso = false;
    
    public EscribirArchivo(String rut){
        this.ruta = rut;
    }
    
    public EscribirArchivo(String rut,boolean per){
        this.ruta = rut;
        this.permiso = per;
    }
    
    public void EscribirEnArchivo(String texto)throws IOException{
        FileWriter write = new FileWriter(this.ruta, permiso);
        PrintWriter print = new PrintWriter(write);
        System.out.println(texto);
        print.printf("%s"+"%n", texto);
        print.close();
    }
}
