/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificador;

import archivos.LeerArchivo;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFileChooser;

/**
 *
 * @author Liquid
 */
public class Clasificador {

    public ClasificadorADN oCla;
    File DatosTxt;
    public int modelo, sitio, limInf, limSup;
    public List<Integer> positivos, positivos2;
    public double[] distGen, distPos;
    public String rutaSecuencia;
    ArrayList<Object> predicciones;
    double umbral;

    public void setDistGen(double[] distGen) {
        this.distGen = distGen;
    }

    public void setDistPos(double[] distPos) {
        this.distPos = distPos;
    }

    public void setPredicciones(ArrayList<Object> predicciones) {
        this.predicciones = predicciones;
    }

    public void setUmbral(double umbral) {
        this.umbral = umbral;
    }

    public void setLimInf(int limInf) {
        this.limInf = limInf;
    }

    public int getLimInf() {
        return limInf;
    }

    public int getLimSup() {
        return limSup;
    }

    public void setLimSup(int limSup) {
        this.limSup = limSup;
    }   

    public void setoCla(ClasificadorADN oCla) {
        this.oCla = oCla;
    }

    public void setPositivos(List<Integer> positivos) {
        this.positivos = positivos;
    }

    public ClasificadorADN getoCla() {
        return oCla;
    }

    public int getModelo() {
        return modelo;
    }

    public int getSitio() {
        return sitio;
    }

    public List<Integer> getPositivos() {
        return positivos;
    }

    public String getRutaSecuencia() {
        return rutaSecuencia;
    }

    public void setRutaSecuencia(String rutaSecuencia) {
        this.rutaSecuencia = rutaSecuencia;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public void setSitio(int sitio) {
        this.sitio = sitio;
    }
    
    public Clasificador() {
        this.predicciones = new ArrayList<>();
        
    }

    public Clasificador(int sitio, int modelo, String rutaSecuencia, int limInf, int limSup, double umbral) {
        
        this.predicciones = new ArrayList<>();
        setModelo(modelo);
        setSitio(sitio);
        setLimInf(limInf);
        setLimSup(limSup);        
        oCla = new ClasificadorADN();
        setRutaSecuencia(rutaSecuencia);
        setUmbral(umbral);

    }
    
    public static void main(String[] args) throws Exception {
        
        ArrayList<Object> predicciones = new ArrayList<>();
        
        int sitio = Integer.parseInt(args[0]);
        int modelo = Integer.parseInt(args[1]);
        String ruta = args[2];
        int limInf = Integer.parseInt(args[3]);
        int limSup = Integer.parseInt(args[4]);
        double umbral = Double.parseDouble(args[5]);
        
        Clasificador cla = new Clasificador(sitio, modelo, ruta, limInf, limSup, umbral);
        
        predicciones = cla.clasificador();
        
    }    

    public ArrayList<Object> clasificador() throws Exception {
        
        String rutaModelo ="";

        int vectorAtributos[]=null;
        
        boolean seleccionAtributos=false; 

        if (modelo == 0 && sitio == 0) {
            //home/jose/NetBeansProjects/clasificadorGTAG/EI_GT/4Modelos
            rutaModelo = "EI_GT/4Modelos/ConjunctiveRule.model";
        }
        if (modelo == 1 && sitio == 0) {// ancho 5, 5
            rutaModelo = "EI_GT/4Modelos/MultiLayerPerceptron.model";
            seleccionAtributos=true;
            vectorAtributos=new int[]{2,4,5,7};
            //vectorAtributos=new int[]{4,5,6,7,9}; 
        }
        if (modelo == 2 && sitio == 0) {
            rutaModelo = "EI_GT/4Modelos/TreeJ48.model";
        }        
        if (modelo == 3 && sitio == 0) {// ancho 5, 5
            rutaModelo = "EI_GT/4Modelos/BayesNet.model";
            seleccionAtributos=true;
            vectorAtributos=new int[]{4,5,6,7}; // New Model Weka-3.5
        }

        if (modelo == 0 && sitio == 1) {
            rutaModelo = "IE_AG/4Modelos/ConjunctiveRule.model";
            seleccionAtributos=false;
            vectorAtributos=new int[]{0,2,3,4,5,6}; // Ancho 5, 5
        }
        if (modelo == 1 && sitio == 1) { // Ancho 100, 5
            rutaModelo = "IE_AG/4Modelos/MultiLayerPerceptron.model";
            seleccionAtributos=false;  
            //vectorAtributos=new int[]{59,72,80,83,85,86,87,88,89,90,91,92,93,94,95,96,98,99,100,101,102,103}; // Ancho 100, 5
            vectorAtributos=new int[]{0,2,3,4,5,6};; // Ancho 5, 5
            
        }
        if (modelo == 2 && sitio == 1) {
            rutaModelo = "IE_AG/4Modelos/TreeJ48.model";
            seleccionAtributos=false;
            vectorAtributos=new int[]{0,2,3,4,5,6}; // Ancho 5, 5
        }        
        if (modelo == 3 && sitio == 1) { 
            rutaModelo = "IE_AG/4Modelos/BayesNet.model";
            seleccionAtributos=true;  
            // Ancho 100, 5
            //vectorAtributos=new int[]{59,72,80,83,85,86,87,88,89,90,91,92,93,94,95,96,98,99,100,101,102,103};
            vectorAtributos=new int[]{0,2,3,4,5,6};
        }
        
        if (modelo == 4 && sitio == 1) { 
            rutaModelo = "IE_AG/4Modelos/SMO.model";
            seleccionAtributos=false;  
            // Ancho 100, 5
            vectorAtributos=new int[]{59,72,80,83,85,86,87,88,89,90,91,92,93,94,95,96,98,99,100,101,102,103};
            //vectorAtributos=new int[]{0,2,3,4,6}; // Ancho 5, 5
        }
        
        if (modelo == 0 && sitio == 2) {
            rutaModelo = "EZ/4Modelos/Conjunctive2.0Rule.model";
        }
        if (modelo == 1 && sitio == 2) { // Ancho 50, 200
            rutaModelo = "EZ/4Modelos/MultiLayerPerceptron.model";
            seleccionAtributos=true; 
            vectorAtributos=new int[]{14,32,33,50,51,52,67,132,138,139,153,221,228,232,235,247};
            //vectorAtributos=new int[]{31,33,34,36,50,51,52,53,63,171,181,213,229,356,368,386,406};
        }
        if (modelo == 2 && sitio== 2) {
            rutaModelo = "EZ/4Modelos/TreeJ48.model";
        }        
        if (modelo == 3 && sitio == 2) { // Ancho 50, 200
            rutaModelo = "EZ/4Modelos/BayesNet.model";
            seleccionAtributos=true; 
            vectorAtributos=new int[]{31,33,34,36,50,51,52,53,63,122,139,143,171,181,214,229,356,368,386,406,466};
        }

        if (modelo == 0 && sitio == 3) {
            rutaModelo = "ZE/4Modelos/ConjunctiveRule.model";
        }
        if (modelo == 1 && sitio == 3) {// Ancho 500, 200
            rutaModelo = "ZE/4Modelos/MultilayerPerceptron.model";
            seleccionAtributos=true;
            vectorAtributos=new int[]{118,186,471,482,483,500,501,502,511,517,522,536,582,598,603,671};
            //vectorAtributos=new int[]{11,329,483,495,499,501,502,503,518};
        }
        if (modelo == 2 && sitio== 3) {
            rutaModelo = "ZE/4Modelos/TreeJ48.model";
        }
        if (modelo == 3 && sitio == 3) {// Ancho 500, 200
            rutaModelo = "ZE/4Modelos/BayesNet.model";
            seleccionAtributos=true;
            vectorAtributos=new int[]{329,483,495,501,502,503,518};
        }
        
        File datos = new File(rutaSecuencia);
        
        predicciones = oCla.ClasificarTxt(datos, modelo, sitio, rutaModelo, seleccionAtributos, vectorAtributos, limInf, limSup, umbral);
        
        positivos = (List<Integer>)predicciones.get(0);
        
        System.out.println("Vector de positivos: " + positivos.toString());     

        System.out.println("FIN DEL PROCESO");        

        return predicciones;

    }
}