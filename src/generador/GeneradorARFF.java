/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generador;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 *
 * @author Liquid
 */
public class GeneradorARFF {

    public ClasificadorADN oCla;
    File DatosTxt;    
    public int[] positivos, positivos2;   
    
    static int sitio;
    static String hebraTipo;                       // tipo de hebra, [ W | C ]
    static String tipo;                               // tipo de prueba [ EI | IE | ZE | EZ ]
    static String tipoFalso;                       // tipo de archivo falso [ cie | cei | cez | cze ]
    static int cant; 

    public void setoCla(ClasificadorADN oCla) {
        this.oCla = oCla;
    }

    public void setPositivos(int[] positivos) {
        this.positivos = positivos;
    }

    public ClasificadorADN getoCla() {
        return oCla;
    }    

    public int getSitio() {
        return sitio;
    }

    public int[] getPositivos() {
        return positivos;
    }
    

    public void setSitio(int sitio) {
        GeneradorARFF.sitio = sitio;
    }

    public GeneradorARFF(int sitio, String hebraTipo, String tipoFalso, int cant) {
      
        GeneradorARFF.sitio = sitio;
        GeneradorARFF.hebraTipo=hebraTipo;
        GeneradorARFF.tipoFalso=tipoFalso;
        GeneradorARFF.cant=cant;
        
    }

    public static void main(String[] args) throws Exception {    

        int sitio;        
        sitio = Integer.parseInt(args[0]);       
                
        //Atributos de entrada del generador ARFF
        //inicio del generador ARFF
        switch (sitio) {
            case 0:
                tipo="EI";
                break;
            case 1:
                tipo="IE";
                break;
            case 2:
                tipo="EZ";
                break;
            case 3:
                tipo="ZE";
                break;
            default:
                break;
        }
        //cargaParametros(); // carga paràmetros de entrada del catalizador
        Catalizador cat = new Catalizador();
        cat.initParam(args[1],tipo, args[2],Integer.parseInt(args[3]));
        cat.exec();    
        

        System.out.println("Datos generados para zona " + tipo);

    }

    //carga parametros del generador ARFF
    public static void cargaParametros(){
        // escojo el tipo de hebra
            Scanner s = new Scanner(System.in);     // lectura por teclado, solo para captar los parametros
            
            System.out.println("\nCOMO VALORES INGRESAR \nTIPO DE HEBRA: W\n");
            System.out.println("\nIngrese tipo de Hebra: \n\t[ W | C ]");
            do {
                
                hebraTipo = s.nextLine();
                if (hebraTipo.compareTo("W")!=0&&hebraTipo.compareTo("C")!=0)
                    System.out.println("\nDato no valido, ingrese una de las opciones \n\t[ W | C ]");
                
            } while (hebraTipo.compareTo("W")!=0&&hebraTipo.compareTo("C")!=0);
            
            // escojo el tipo de prueba
//            System.out.println("\nIngrese tipo de prueba: \n\t[ EI | IE | ZE | EZ ]");
//            do {
//                
//                tipo = s.nextLine();
//                if (tipo.compareTo("EI")!=0&&tipo.compareTo("IE")!=0&&tipo.compareTo("ZE")!=0&&tipo.compareTo("EZ")!=0)
//                    System.out.println("\nDato no valido, ingrese una de las opciones \n\t[ EI | IE | ZE | EZ ]");
//                
//            } while (tipo.compareTo("EI")!=0&&tipo.compareTo("IE")!=0&&tipo.compareTo("ZE")!=0&&tipo.compareTo("EZ")!=0);

            // escojo el tipo de archivo falso para la prueba
            System.out.println("\nIngrese tipo de archivo falso para la prueba: \n\t[ cie | cei | cez | cze ]");
            do {
                
                tipoFalso = s.nextLine();
                if ((tipoFalso.compareTo("cie")!=0&&tipoFalso.compareTo("cei")!=0&&tipoFalso.compareTo("cez")!=0&&tipoFalso.compareTo("cze")!=0)||tipoFalso.compareToIgnoreCase("c"+tipo)==0)
                    System.out.println("\nDato no valido, ingrese una de las opciones \n\t[ cie | cei | cez | cze ]");
                
            } while ((tipoFalso.compareTo("cie")!=0&&tipoFalso.compareTo("cei")!=0&&tipoFalso.compareTo("cez")!=0&&tipoFalso.compareTo("cze")!=0)||tipoFalso.compareToIgnoreCase("c"+tipo)==0);
        
            // escojo el tamaño de la subcadena
            System.out.println("\nIngrese tamaño de la subcadena: \n\t[ 1 - 10 ]");
            do {
                
                cant = s.nextInt();
                if (cant < 1 || cant > 10)
                    System.out.println("\nDato no valido, ingrese una de las opciones \n\t[ 1 - 10 ]");
                
            } while (cant < 1 || cant > 10);
    }
   
}
