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
    boolean seleccionAtributos;

    public void setDatosTxt(File DatosTxt) {
        this.DatosTxt = DatosTxt;
    }

    public void setPositivos2(List<Integer> positivos2) {
        this.positivos2 = positivos2;
    }

    public void setSeleccionAtributos(boolean seleccionAtributos) {
        this.seleccionAtributos = seleccionAtributos;
    }

    public File getDatosTxt() {
        return DatosTxt;
    }

    public List<Integer> getPositivos2() {
        return positivos2;
    }

    public double[] getDistGen() {
        return distGen;
    }

    public double[] getDistPos() {
        return distPos;
    }

    public ArrayList<Object> getPredicciones() {
        return predicciones;
    }

    public double getUmbral() {
        return umbral;
    }

    public boolean isSeleccionAtributos() {
        return seleccionAtributos;
    }

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

    public Clasificador(int sitio, int modelo, String rutaSecuencia, int limInf, int limSup, double umbral, boolean seleccionAtributos) {

        this.predicciones = new ArrayList<>();
        setModelo(modelo);
        setSitio(sitio);
        setLimInf(limInf);
        setLimSup(limSup);
        oCla = new ClasificadorADN();
        setRutaSecuencia(rutaSecuencia);
        setUmbral(umbral);
        this.setSeleccionAtributos(seleccionAtributos);

    }

    public static void main(String[] args) throws Exception {

        ArrayList<Object> predicciones = new ArrayList<>();

        int sitio = Integer.parseInt(args[0]);
        int modelo = Integer.parseInt(args[1]);
        String ruta = args[2];
        int limInf = Integer.parseInt(args[3]);
        int limSup = Integer.parseInt(args[4]);
        double umbral = Double.parseDouble(args[5]);
        boolean seleccionAtributos = Boolean.parseBoolean(args[5]);

        Clasificador cla = new Clasificador(sitio, modelo, ruta, limInf, limSup, umbral,seleccionAtributos);

        predicciones = cla.clasificador();

    }

    public ArrayList<Object> clasificador() throws Exception {

        String rutaModelo = "";

        int vectorAtributos[] = null;

        String tipoTrans = "";

        switch (sitio) {

            case 0:
                
                tipoTrans = "EI_GT";

                if (seleccionAtributos) {
                    vectorAtributos = new int[]{0, 2, 3, 4, 5, 6}; // Ancho 5, 5
                } else {
                    vectorAtributos = new int[this.limInf + this.limSup + 1];
                }

                break;

            case 1:
                
                tipoTrans = "IE_AG";

                if (seleccionAtributos) {
                    vectorAtributos = new int[]{0, 2, 3, 4, 5, 6}; // Ancho 5, 5
                } else {
                    vectorAtributos = new int[this.limInf + this.limSup + 1];
                }

                break;

            case 2:
                
                tipoTrans = "EZ";

                if (seleccionAtributos) {
                    vectorAtributos = new int[]{2, 4, 5, 7};
                } else {
                    vectorAtributos = new int[this.limInf + this.limSup + 1];
                }

                break;

            case 3:
                
                tipoTrans = "ZE";

                if (seleccionAtributos) {
                    vectorAtributos = new int[]{2, 4, 5, 7};
                } else {
                    vectorAtributos = new int[this.limInf + this.limSup + 1];
                }

                break;

        }

        switch (modelo) {

            case 0:

                rutaModelo = tipoTrans + "/4Modelos/ConjunctiveRule.model";
                break;

            case 1:

                rutaModelo = tipoTrans + "/4Modelos/MultiLayerPerceptron.model";
                break;

            case 2:

                rutaModelo = tipoTrans + "4Modelos/TreeJ48.model";
                break;

            case 3:

                rutaModelo = tipoTrans + "/4Modelos/BayesNet.model";
                break;

            case 4:

                rutaModelo = tipoTrans + "/4Modelos/SMO.model";
                break;

        }

        File datos = new File(rutaSecuencia);

        predicciones = oCla.clasificar(datos, modelo, sitio, rutaModelo, seleccionAtributos, vectorAtributos, limInf, limSup, umbral);

        positivos = (List<Integer>) predicciones.get(0);

        System.out.println("Vector de positivos: " + positivos.toString());

        System.out.println("FIN DEL PROCESO");

        return predicciones;

    }
}
