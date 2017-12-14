/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassDNA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


/**
 *
 * @author cypres
 */
    
public class LeerArchivo {
    
    private String ruta;
    
    
    public LeerArchivo(){
        this.ruta = null;
    }
    public LeerArchivo(String rut){
        this.ruta = rut;
    }
    public String[] AbrirArchivo() throws Exception{
        
        FileReader fr = new FileReader(this.ruta);
        BufferedReader texto = new BufferedReader(fr);
        
        int numerolinea = CantidadLineas();
        String[] Datos = new String[numerolinea];
        
        for (int i = 0; i < numerolinea; i++) {
            Datos[i] = texto.readLine();
        }
        texto.close();
        return Datos;
    }
    public String LeerLinea()throws Exception{
        FileReader fr = new FileReader(this.ruta);
        BufferedReader texto = new BufferedReader(fr);
        int cant = 0;
        String linea;
        linea=texto.readLine();
        return linea;
    }
    public int CantidadLineas()throws Exception{
        FileReader fr = new FileReader(this.ruta);
        BufferedReader texto = new BufferedReader(fr);
        int cant = 0;
        String linea;
        
        while ((linea = texto.readLine()) != null) {            
            cant+=1;
        }
        texto.close();
        return cant;
    }
    public int CantidadCaracteres()throws Exception{
        FileReader fr = new FileReader(this.ruta);
        BufferedReader texto = new BufferedReader(fr);
        int cant = 0;
        String linea;
        
        while ((linea = texto.readLine()) != null) {            
            cant+=linea.length();
        }
        texto.close();
        return cant;
    }
    public int CantidadOcurrencias(String sitio) throws Exception{
        int ocurrencias =0;
        String linea="";
        FileReader fr = new FileReader(this.ruta);
        BufferedReader texto = new BufferedReader(fr);
        linea = texto.readLine();
        linea.length();
        ocurrencias= linea.split(sitio).length -1;
        System.out.println(ocurrencias + " ocurrencias del gen encontradas.");
        return ocurrencias;
    }
    public void prueba(String gen)throws Exception{
        String linea="",captura="";
        int contador=0;
        FileReader fr = new FileReader(this.ruta);
        BufferedReader texto = new BufferedReader(fr);
        linea = texto.readLine();
        linea = linea.replace("[", "");
        linea = linea.replace("]", "");
        linea = linea.replace(",", "");
        for (int i = -1; (i = linea.indexOf(gen, i + 1)) != -1; ) {
            captura="";
            captura = linea.substring(i-4, i+6);
            System.out.println("Ocurrencia "+contador+" gen "+captura+" en "+i);         
            contador++;
        }
    }
    public void metodo (String ruta, String clase, EscribirArchivo arc) throws Exception{
        LeerArchivo arcp = new LeerArchivo(ruta);
            int lineas = arcp.CantidadLineas();
            String[] Data = new String[lineas];
            Data = arcp.AbrirArchivo();
            
            for (int i = 0; i < lineas; i++) {
                String linea = Data[i];
                if (linea.contains("p")) {
                    int j = linea.indexOf("p");
                    linea = linea.substring(j-10, j+11);
                    //linea = linea.replaceAll(",p,", ",");
                    arc.EscribirEnArchivo(linea+clase);
                }
            }
    }
}
