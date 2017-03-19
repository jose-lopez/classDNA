/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificador;


import archivos.LeerArchivo;
import java.io.*;
import java.util.Arrays;
import javax.swing.JFileChooser;

/**
 *
 * @author Liquid
 */


public class Clasificador {
    
public static ClasificadorADN Ocla;
File DatosTxt;
public static int modelo,gen;
public static int [] positivos;
    
public static void main(String [] args) throws Exception{
    String RutaModelo=null, RutaData;
    boolean repeat=true;
    BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
    Ocla = new ClasificadorADN();
    
    while(repeat){
        try{
            System.out.println("Seleccione Gen a identificar:");
            System.out.println("0. Exon-Intron (GT)");
            System.out.println("1. Intron-Exon (AG)");
            gen = Integer.parseInt(reader.readLine());

            System.out.println("Seleccione modelo a emplear:");
            System.out.println("0. Conjunctive Rule ");
            System.out.println("1. Multilayer Perceptron");
            System.out.println("2. Tree J48");
            modelo = Integer.parseInt(reader.readLine());
            if(gen > 1 || modelo > 2)repeat = true;
            else repeat=false;
        }catch(Exception e){
            repeat = true;            
        }
    }
        
    
    
    
    if(modelo == 0 && gen == 0)RutaModelo="EI_GT/4Modelos/ConjunctiveRule.model";
    if(modelo == 1 && gen == 0)RutaModelo="EI_GT/4Modelos/MultiLayerPerceptron.model";
    if(modelo == 2 && gen == 0)RutaModelo="EI_GT/4Modelos/TreeJ48.model";
    
    if(modelo == 0 && gen == 1)RutaModelo="IE_AG/4Modelos/ConjunctiveRule.model";
    if(modelo == 1 && gen == 1)RutaModelo="IE_AG/4Modelos/MultiLayerPerceptron.model";
    if(modelo == 2 && gen == 1)RutaModelo="IE_AG/4Modelos/TreeJ48.model";
    
    RutaData="test/gen_SST.txt";
    
    
    File datos = new File(RutaData);
    positivos = Ocla.ClasificarTxt(datos, modelo, gen,RutaModelo);
    
    System.out.println("Vector de positivos: "+ Arrays.toString(positivos));
    
    System.out.println("FIN DEL PROCESO");
    
}    
}
