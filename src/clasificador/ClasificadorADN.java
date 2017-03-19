/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificador;

import archivos.EscribirArchivo;
import archivos.LeerArchivo;
import java.io.File;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.J48;
import weka.classifiers.rules.ConjunctiveRule;
import weka.core.Instances;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.SparseInstance;
import java.util.Date;

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
    String TextoModelo="",TextoGen="";
    Attribute ClassAttribute;
    int [] positivos;
    int detectados;
    
    public ClasificadorADN(){
        
    }
    public void CargarModelo(String ruta, int modelo) throws Exception{
        
        switch (modelo){
            case 0:
                cr = (ConjunctiveRule) weka.core.SerializationHelper.read(ruta);
                //cr.buildClassifier(datapredict);
                TextoModelo="ConjunctiveRule-";
                
                break;
            case 1:
                mlp = (MultilayerPerceptron) weka.core.SerializationHelper.read(ruta);
                TextoModelo="MultiLayerPerceptron-";
                break;
            case 2:
                tree = (J48) weka.core.SerializationHelper.read(ruta);
                TextoModelo="TreeJ48-";
                break;
        }
        System.out.println("Modelo Cargado");
    }
    public void CargarData(String ruta) throws Exception{
        datapredict = new Instances(
                            new BufferedReader(new FileReader(ruta)));
        datapredict.setClassIndex(datapredict.numAttributes() - 1);
        predicteddata = new Instances(datapredict);
    }
    public void CrearInstancias(int gen){
        atts = new FastVector(11);
//        attributeNames = new String[]{"B1","B2","B3","B4","B5","B6","B7","B8","B9","B10","CLASS"};
//        atts = new FastVector();
//        Arrays.stream(attributeNames).map(Attribute::new).forEach(atts::addElement);
//       
        
        
       
       for (int i=0;i<10;i++){
           atts.addElement(new Attribute("B"+(i+1)));
       } 
       if(gen==0){
           FastVector fvClassVal = new FastVector(2);
           fvClassVal.addElement("E-");
           fvClassVal.addElement("E+");
           ClassAttribute = new Attribute("CLASS", fvClassVal);
       }
       if(gen==1){
           FastVector fvClassVal = new FastVector(2);
           fvClassVal.addElement("I-");
           fvClassVal.addElement("I+");
           ClassAttribute = new Attribute("CLASS", fvClassVal);
       }
       atts.addElement(ClassAttribute);
       
    }
    public void SeleccionarCarpeta(File car)throws Exception{
        Carpeta = car;
        Carpeta.createNewFile();
    }
    public void Clasificar(int modelo)throws Exception{
        double clsLabel=0;
        detectados=0;
        for (int i = 0; i < datapredict.numInstances(); i++) {
            
            //System.out.println(datapredict.instance(i).toString());
            
            switch (modelo){
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
            if(clsLabel==1)detectados++;
            System.out.println("marcador "+i+" respuesta: "+clsLabel);            
            predicteddata.instance(i).setClassValue(clsLabel);
            
           
        }
        
        positivos = new int [detectados];
        int k=0;
        for (int j = 0; j < predicteddata.numInstances(); j++) {
            if(predicteddata.instance(j).classValue()==1){
                positivos[k]=posiciones[j];
                k++;
            }
        }
        System.out.println("Datos Clasificados");
    }
    public int [] ClasificarTxt(File datos, int modelo, int gen, String RutaModelo) throws Exception{
        String genstr="",genstrclean="";
            switch (gen){
                case 0:
                    genstr="g,t";
                    genstrclean="gt";
                    TextoGen="Exon-Intron-GT-";
                    break;
                case 1:
                    genstr="a,g";
                    genstrclean="ag";
                    TextoGen="Intron-Exon-AG-";
                    break;
            }
            LeerArchivo arcp = new LeerArchivo(datos.getPath());
         
            int lineas = arcp.CantidadOcurrencias(genstr);
            CrearInstancias(gen);
            datapredict = new Instances("data", atts, lineas);
            
            
            
            //String[] Data = new String[lineas];
            posiciones = new Integer[lineas];
                       
            
            
            int ConPos = 0;
            int CanCar = 0;
            int contador=0;
            String linea = arcp.LeerLinea();
            String captura;
            linea = linea.replace("[", "");
            linea = linea.replace("]", "");
            linea = linea.replace(",", "");
            for (int i = -1; (i = linea.indexOf(genstrclean, i + 1)) != -1; ) {
                captura="";
                try{
                    captura = linea.substring(i-5, i+7);
                    captura = captura.replace(genstrclean, "");
                    //System.out.println("Ocurrencia "+contador+" gen "+captura+" en "+i);         
                    contador++;
                    captura = captura.replace("a", "0");
                    captura = captura.replace("c", "1");
                    captura = captura.replace("g", "2");
                    captura = captura.replace("t", "3");

                    String[] bases = captura.split("");



                    double[] attValues = new double[datapredict.numAttributes()];         


                    for (int j = 0; j < 10; j++) {

                        attValues[j] = Integer.parseInt(bases[j]);
                    }
                    attValues[10] = datapredict.attribute(10).addStringValue("?");


                    datapredict.add(new Instance(1.0, attValues));

                    posiciones[ConPos] = i;
                    ConPos++;            
                }catch(StringIndexOutOfBoundsException e){}
                
            }
            
            datapredict.setClassIndex(datapredict.numAttributes()-1);
            CargarModelo(RutaModelo, modelo);
            predicteddata = new Instances(datapredict);
            Clasificar(modelo);
            GenerarArff(true);
            return(positivos);
    }
    
    public void GenerarArff(boolean pos)throws Exception{
        EscribirArchivo arc;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
        String RutaResultado="";
        RutaResultado="results/Resultado-"+TextoGen+TextoModelo+timeStamp+".txt";
        System.out.println("Resultados Generados en: "+RutaResultado);
        arc = new EscribirArchivo(RutaResultado, true);
        if (pos) {
            for (int i = 0; i < datapredict.numInstances(); i++) {
                arc.EscribirEnArchivo(posiciones[i]+":"+predicteddata.instance(i).toString());
            }
        } else {
            for (int i = 0; i < datapredict.numInstances(); i++) {
                arc.EscribirEnArchivo(predicteddata.instance(i).toString());
            }
        }
        arc.EscribirEnArchivo("Positivos Encontrados: "+Arrays.toString(positivos));
        System.out.println("Cantidad de positivos encontrados: "+detectados);
       // System.out.println(Arrays.toString(positivos));
    }
}
