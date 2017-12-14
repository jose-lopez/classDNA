/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassDNA;

import java.io.File;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.J48;
import weka.classifiers.rules.ConjunctiveRule;
import weka.core.Instances;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import weka.core.Attribute;
import weka.core.FastVector;

/**
 *
 * @author cypres
 */
public class ClasificadorADN {

    MultilayerPerceptron mlp;
    ConjunctiveRule cr;
    J48 tree;
    File Carpeta;
    Instances datapredict;
    Instances predicteddata;
    String[] attributeNames;
    FastVector atts;
    Integer[] posiciones;
    String TextoModelo = "", TextoGen = "";
    Attribute ClassAttribute;
    int[] positivos;
    int detectados;

    public ClasificadorADN() {
    }

    public void CargarModelo(String ruta, int modelo) throws Exception {

        String path = System.getProperty("user.dir");
        ruta = path + "/" + ruta;

        switch (modelo) {
            case 0:
                cr = (ConjunctiveRule) weka.core.SerializationHelper.read(ruta);
                //cr.buildClassifier(datapredict);
                TextoModelo = "ConjunctiveRule-";

                break;
            case 1:
                mlp = (MultilayerPerceptron) weka.core.SerializationHelper.read(ruta);
                TextoModelo = "MultiLayerPerceptron-";
                break;
            case 2:
                tree = (J48) weka.core.SerializationHelper.read(ruta);
                TextoModelo = "TreeJ48-";
                break;
        }
        System.out.println("Modelo Cargado");
    }

    public void CargarData(String ruta) throws Exception {
        datapredict = new Instances(
                new BufferedReader(new FileReader(ruta)));
        datapredict.setClassIndex(datapredict.numAttributes() - 1);
        predicteddata = new Instances(datapredict);
    }

    public void InicializarVectorInstancias(int sitio) {

        atts = new FastVector(11);
//        attributeNames = new String[]{"B1","B2","B3","B4","B5","B6","B7","B8","B9","B10","CLASS"};
//        atts = new FastVector();
//        Arrays.stream(attributeNames).map(Attribute::new).forEach(atts::addElement);
//       



        for (int i = 0; i < 10; i++) {
            atts.addElement(new Attribute("B" + (i + 1)));
        }
        FastVector fvClassVal = new FastVector(2);
        if (sitio == 0) {
            
            fvClassVal.addElement("E-");
            fvClassVal.addElement("E+");
        }
        if (sitio == 1) {
            fvClassVal.addElement("I-");
            fvClassVal.addElement("I+");
            
        }
        if (sitio == 2) {
            fvClassVal.addElement("EZ-");
            fvClassVal.addElement("EZ+");
            
        }
        if (sitio == 3) {
            fvClassVal.addElement("ZE-");
            fvClassVal.addElement("ZE+");
            
        }
        ClassAttribute = new Attribute("CLASS", fvClassVal);
        atts.addElement(ClassAttribute);

    }

    public void crearAtributos(int sitio, int canAtrib,int[] vectorAtributos) {

        atts = new FastVector(canAtrib);

        for (int i = 0; i < canAtrib - 1; i++) {
            atts.addElement(new Attribute("B" + (vectorAtributos[i] + 1)));
        }
        FastVector fvClassVal = new FastVector(2);
        
        if (sitio == 0) {
            fvClassVal.addElement("E-");
            fvClassVal.addElement("E+");
        }
        if (sitio == 1) {
            fvClassVal.addElement("I-");
            fvClassVal.addElement("I+");
        }
        if (sitio == 2) {
            fvClassVal.addElement("EZ-");
            fvClassVal.addElement("EZ+");
        }
        
        ClassAttribute = new Attribute("CLASS", fvClassVal);
        atts.addElement(ClassAttribute);

    }

    public void SeleccionarCarpeta(File car) throws Exception {
        Carpeta = car;
        Carpeta.createNewFile();
    }

    public void Clasificar(int modelo) throws Exception {
        double clsLabel = 0;
        detectados = 0;
        for (int i = 0; i < datapredict.numInstances(); i++) {

            //System.out.println(datapredict.instance(i).toString());
            
            switch (modelo) {
                case (0):
                    clsLabel = cr.classifyInstance(datapredict.instance(i));
                    break;
                case (1):
                    clsLabel = mlp.classifyInstance(datapredict.instance(i));
                    break;
                case (2):
                    clsLabel = tree.classifyInstance(datapredict.instance(i));
                    break;
            }
            if (clsLabel == 1) {
                detectados++;
            }
            System.out.println("Instancia " + i + " respuesta: " + clsLabel);
            predicteddata.instance(i).setClassValue(clsLabel);


        }

        positivos = new int[detectados];
        int k = 0;
        for (int j = 0; j < predicteddata.numInstances(); j++) {
            if (predicteddata.instance(j).classValue() == 1) {
                positivos[k] = posiciones[j];
                k++;
            }
        }
        System.out.println("Datos Clasificados");
    }

    public int[] ClasificarTxt(File datos, int modelo, int sitio, String RutaModelo, boolean seleccionAtributos, int[]vectorAtributos) throws Exception {
        String genstr = "", genstrclean = "";
        switch (sitio) {
            case 0:
                genstr = "g,t";
                genstrclean = "gt";
                TextoGen = "Exon-Intron-GT-";
                break;
            case 1:
                genstr = "a,g";
                genstrclean = "ag";
                TextoGen = "Intron-Exon-AG-";
                break;
            case 2:
                TextoGen = "Exon-ZonaIntergenica-";
                break;
            case 3:
                TextoGen = "ZonaIntergenica-Exon-";
                break;                
        }
        LeerArchivo arcp = new LeerArchivo(datos.getPath());

        int lineas = arcp.CantidadOcurrencias(genstr);

        if (!seleccionAtributos) {
            InicializarVectorInstancias(sitio);
        } else {
            crearAtributos(sitio, vectorAtributos.length+1,vectorAtributos);
        }

        datapredict = new Instances("data", atts, lineas);
        posiciones = new Integer[lineas];

        int ConPos = 0;
        int contador=0;
        int longLinea, limInf, limSup;
        String linea = arcp.LeerLinea();
        String captura = null;
        linea = linea.replace("[", "");
        linea = linea.replace("]", "");
        linea = linea.replace(",", "");
        longLinea = linea.length();
        int ocurrencias=sitio<=1?lineas:longLinea, i=-1;
        System.out.println("Ocurrencias "+ocurrencias);
        for(int x=0;x<ocurrencias;x++){
            if(sitio<=1){
                i = linea.indexOf(genstrclean,i+1);
            }else{
                i=x;
            }
            
            captura = "";
            try {
                limInf = i - 5;
                limSup = i + 5+(sitio<=1?2:0);

                if (limInf > 0 && limSup < longLinea) {

                    captura = linea.substring(limInf, i);
                    captura = captura + linea.substring(i +(sitio<=1?2:0), limSup);
                    System.out.println("Limite inferior"+limInf+" Limite superior "+limSup+" Captura: "+captura);
                    contador++;
                    captura = captura.replace("a", "0");
                    captura = captura.replace("c", "1");
                    captura = captura.replace("g", "2");
                    captura = captura.replace("t", "3");

                    String[] bases = captura.split("");
                    int canAtrib = datapredict.numAttributes();
                    double[] attValues = new double[canAtrib];
                    for(int k=0; k<canAtrib-1;k++){
                        if (seleccionAtributos){
                            attValues[k]=Integer.parseInt(bases[vectorAtributos[k]]);
                        }
                        else
                            attValues[k] = Integer.parseInt(bases[k]);
                    }
                    attValues[canAtrib - 1] = datapredict.attribute(canAtrib - 1).addStringValue("?");
                    //datapredict.add(new Instance(1.0, attValues));

                    posiciones[ConPos] = i;
                    ConPos++;
                    
                }
            } catch (StringIndexOutOfBoundsException e) {
            }

        }
 
        datapredict.setClassIndex(datapredict.numAttributes() - 1);
        CargarModelo(RutaModelo, modelo);
        predicteddata = new Instances(datapredict);
        Clasificar(modelo);
        GenerarResultados(true);

        if (sitio == 1) {

            for (int pos = 0; pos < positivos.length; pos++) {

                positivos[pos] = positivos[pos] + 1;

            }

        }

        return (positivos);
    }

    public void GenerarResultados(boolean pos) throws Exception {
        EscribirArchivo arc;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
        String RutaResultado = "";
        RutaResultado = "results/Resultado-" + TextoGen + TextoModelo + timeStamp + ".txt";
        System.out.println("Resultados Generados en: " + RutaResultado);
        arc = new EscribirArchivo(RutaResultado, true);
        if (pos) {
            for (int i = 0; i < predicteddata.numInstances(); i++) {
                String result=predicteddata.instance(i).toString();
                    result = result.replace("0", "a");
                    result = result.replace("1", "c");
                    result = result.replace("2", "g");
                    result = result.replace("3", "t");
                arc.EscribirEnArchivo(posiciones[i] + ":" + result);
                        System.out.println("inicio resultado");
                        System.out.println(result);
                        System.out.println("fin resultado");  
            }
        } else {
            for (int i = 0; i < datapredict.numInstances(); i++) {
                arc.EscribirEnArchivo(predicteddata.instance(i).toString());
            }
        }
        arc.EscribirEnArchivo("Positivos Encontrados: " + Arrays.toString(positivos));
        System.out.println("Cantidad de positivos encontrados: " + detectados);
        // System.out.println(Arrays.toString(positivos));
    }
}
