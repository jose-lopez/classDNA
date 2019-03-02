/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generador;

import java.io.File;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;

public class Catalizador {

    private File carpeta;
    private File rutaV, rutaFW, rutaFC;    //rutas de los archivos Verdaderos
    private String RV, RF;
    private String nombreArchivoPositivo, nombreArchivoNegativo;
    private String hebra, tipoFalso;
    private int cantidad;
    private int cantidadInf;
    private int cantidadSup;
    private String tipo;
    private EscribirArchivo arc;
    private final LeerArchivo arc1 = new LeerArchivo();
    private final boolean band1 = false, band2 = false;
    private final JList LAP = new JList();
    private final JList LAN = new JList();
    private DefaultListModel AP;
    private DefaultListModel AN;

    public boolean initParam(String hebra, String tipo, String tipoFalso, int cantidadInf, int cantidadSup) {
        this.cantidadInf = cantidadInf;
        this.cantidadSup = cantidadSup;
        this.cantidad = cantidadInf + cantidadSup;
        this.tipo = tipo;
        this.hebra = hebra;
        boolean bothFiles = false;
        if (hebra.equals("B")) {
            bothFiles = true;
        }
        this.hebra = "W";
        this.tipoFalso = tipoFalso;

        this.AP = new DefaultListModel();
        this.AN = new DefaultListModel();

        //preparación de las rutas
        if (this.tipo.compareTo("EI") == 0) {
            this.rutaV = new File(this.tipo + "_GT/1Datos/Path.true");
            this.rutaFW = new File(this.tipo + "_GT/1Datos/duplexW_EI/Path.false");
            this.rutaFC = new File(this.tipo + "_GT/1Datos/duplexC_EI/Path.false");
        } else if (this.tipo.compareTo("IE") == 0) {
            this.rutaV = new File(this.tipo + "_AG/1Datos/Path.true");
            this.rutaFW = new File(this.tipo + "_AG/1Datos/duplexW_IE/Path.false");
            this.rutaFC = new File(this.tipo + "_AG/1Datos/duplexC_IE/Path.false");
        } else if (this.tipo.compareTo("EZ") == 0) {
            this.rutaV = new File(this.tipo + "/1Datos/Path.true");
            this.rutaFW = new File(this.tipo + "/1Datos/duplexW_EZ/Path.false");
            this.rutaFC = new File(this.tipo + "/1Datos/duplexC_EZ/Path.false");
        } else if (this.tipo.compareTo("ZE") == 0) {
            this.rutaV = new File(this.tipo + "/1Datos/Path.true");
            this.rutaFW = new File(this.tipo + "/1Datos/duplexW_ZE/Path.false");
            this.rutaFC = new File(this.tipo + "/1Datos/duplexC_ZE/Path.false");
        }
        this.nombreArchivoPositivo = "duplex" + this.hebra + "_" + this.tipo + ".txt";
        this.nombreArchivoNegativo = "duplex" + this.hebra + this.tipoFalso + "_" + this.tipo + ".txt";
//        this.rutaV = new File("Datos/Path.true");
//        this.rutaF = new File("Datos/duplexW_EI/Path.false");
        this.RV = rutaV.getAbsolutePath().replaceAll("Path.true", "") + this.nombreArchivoPositivo;
        this.RF = rutaFW.getAbsolutePath().replaceAll("Path.false", "") + this.nombreArchivoNegativo;
        System.out.println(RV);
        System.out.println(RF);

        // Archivos Positivos
        AP.addElement(RV);
        if (bothFiles) {
            this.nombreArchivoPositivo = "duplex" + "C" + "_" + this.tipo + ".txt";
            this.RV = rutaV.getAbsolutePath().replaceAll("Path.true", "") + this.nombreArchivoPositivo;
            AP.addElement(RV);
        }
        this.LAP.setModel(AP);

        //Archivos Negativos
        AN.addElement(RF);
        if (bothFiles) {
            this.nombreArchivoNegativo = "duplex" + "C" + this.tipoFalso + "_" + this.tipo + ".txt";
            this.RF = rutaFC.getAbsolutePath().replaceAll("Path.false", "") + this.nombreArchivoNegativo;
            AN.addElement(RF);
        }
        this.LAN.setModel(AN);

        return true;
    }

    public boolean exec() {
        try {
            int offset = 0;
            // TODO add your han,dling code here:

            if (this.tipo.compareTo("EI") == 0) {
                carpeta = new File(this.tipo + "_GT/2Datos Formato Arff EI/DataEI.arff");
                carpeta.delete();
                arc = new EscribirArchivo(carpeta.getPath(), true);
                arc.EscribirEnArchivo("@RELATION ExonIntron");
                offset = 6;
            } else if (this.tipo.compareTo("IE") == 0) {
                carpeta = new File(this.tipo + "_AG/2Datos Formato Arff IE/DataIE.arff");
                carpeta.delete();
                arc = new EscribirArchivo(carpeta.getPath(), true);
                arc.EscribirEnArchivo("@RELATION IntronExon");
                offset = 6;
            } else if (this.tipo.compareTo("EZ") == 0) {
                carpeta = new File(this.tipo + "/2Datos Formato Arff EZ/DataEZ.arff");
                carpeta.delete();
                arc = new EscribirArchivo(carpeta.getPath(), true);
                arc.EscribirEnArchivo("@RELATION ExonZona");
                offset = 2;
            } else if (this.tipo.compareTo("ZE") == 0) {
                carpeta = new File(this.tipo + "/2Datos Formato Arff ZE/DataZE.arff");
                carpeta.delete();
                arc = new EscribirArchivo(carpeta.getPath(), true);
                arc.EscribirEnArchivo("@RELATION ZonaExon");
                offset = 2;
            }

            if (cantidad > 0) {
                cantidadInf *= 2;
                cantidadSup *= 2;
            } else {
                System.err.println("La sub cadenas deben ser mayor a Cero (0)");
                exit(1);
            }

            for (int i = 1; i <= cantidad; i++) {
                arc.EscribirEnArchivo("@ATTRIBUTE B" + i
                        + " {a,c,g,t}");
            }

            arc.EscribirEnArchivo("@ATTRIBUTE CLASS {0,1}");
            arc.EscribirEnArchivo("@DATA");

            //---------------------Datos Positivos------------------------
            //Iterator item = archivosP.iterator();
            boolean band = false;
            int indice_inf = 0, indice_sup = 0;

            if (band == false) {//No seleccionaron ningun items

                ListModel lista = LAP.getModel();
                //System.out.println("" + lista.getSize());
                for (int i = 0; i < lista.getSize(); i++) {
                    //System.out.println(lista.getElementAt(i));
                    LeerArchivo arcp = new LeerArchivo(lista.getElementAt(i).toString());
                    int lineas = arcp.CantidadLineas();
                    String[] Data = new String[lineas];
                    Data = arcp.AbrirArchivo();

                    for (int j = 0; j < lineas; j++) {
                        String linea = Data[j];
                        String cadena1, cadena2;
                        if (linea.contains("p")) {
                            int k = linea.indexOf("p");

                            indice_inf = k - cantidadInf;
                            indice_sup = k + (offset - 1) + cantidadSup;
                            //System.out.println(linea.substring(indice_inf, indice_sup));

                            if (indice_inf >= 0 && indice_sup < linea.length()) {
                                //linea = linea.substring(indice_inf, indice_sup);
                                //linea = linea.replaceAll(",p,", ",");
                                cadena1 = linea.substring(indice_inf, k);
                                cadena2 = linea.substring(k + offset, indice_sup);
                                linea = cadena1 + cadena2;
                                arc.EscribirEnArchivo(linea + ",1");
                                //System.out.println(cadena1 + " " + cadena2);
                                //System.out.println(linea + ",1");
                            } else {
                                System.out.println("Positivo obviado por falta de datos:" + indice_inf + " " + indice_sup);

                            }//--if-else
                        }//--if
                    }//--for
                }//--for
            }//----if

            //---------------------Datos Negativos------------------------
            //System.out.println(LAN.getModel().getSize());
            if (LAN.getModel().getSize() > 0) {
                band = false;
                indice_inf = 0;
                indice_sup = 0;

                if (band == false) {//No seleccionaron ningun items

                    ListModel lista = LAN.getModel();

                    for (int i = 0; i < lista.getSize(); i++) {
                        //System.out.println(lista.getElementAt(i));
                        LeerArchivo arcp = new LeerArchivo(lista.getElementAt(i).toString());
                        int lineas = arcp.CantidadLineas();
                        String[] Data = new String[lineas];
                        Data = arcp.AbrirArchivo();

                        for (int j = 0; j < lineas; j++) {
                            String linea = Data[j];
                            String cadena1, cadena2;

                            if (linea.contains("p")) {
                                int k = linea.indexOf("p");

                                indice_inf = k - cantidadInf;
                                indice_sup = k + (offset - 1) + cantidadSup;

                                //System.out.println(linea.substring(indice_inf, indice_sup));
                                if (indice_inf >= 0 && indice_sup < linea.length()) {
                                    //linea = linea.substring(indice_inf, indice_sup);
                                    //linea = linea.replaceAll(",p,", ",");
                                    cadena1 = linea.substring(indice_inf, k);
                                    cadena2 = linea.substring(k + offset, indice_sup);
                                    linea = cadena1 + cadena2;
                                    arc.EscribirEnArchivo(linea + ",0");
                                    //System.out.println(linea + ",0");
                                } else {
                                    System.out.println("Negativo obviado por falta de datos:" + indice_inf + " " + indice_sup);

                                }//--if-else
                            }//--if
                        }//--for
                    }//--for
                }//----if
            }

        } catch (IOException ex) {
            Logger.getLogger(Catalizador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Catalizador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
