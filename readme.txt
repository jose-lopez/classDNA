EI_GT	=> Carpeta referente a la preparación de datos, generación y entrenamiento de modelos de clasificación de genes GT
		1Datos => 1 Archivo de datos positivos y 1 Directorio de datos negativos (ambos en formato .txt)
		2Datos Formato Arff EI => Archivo que mezcla datos positivos y negativos con el debido formato ARFF
		3Entrenamiento => Porción de datos de entrenamiento
		4Modelos => Modelos generados con el WEKA explorer usando los datos de las fases anteriores
		5Prediccon => Porción de los datos de prueba

IE_AG	=> Carpeta referente a la preparación de datos, generación y entrenamiento de modelos de clasificación de genes AG
		1Datos => 1 Archivo de datos positivos y 1 Directorio de datos negativos (ambos en formato .txt)
		2Datos Formato Arff AG => Archivo que mezcla datos positivos y negativos con el debido formato ARFF
		3Entrenamiento => Porción de datos de entrenamiento
		4Modelos => Modelos generados con el WEKA explorer usando los datos de las fases anteriores
		5Prediccon => Porción de los datos de prueba
	
libreria => Jar de Weka 3.5.6 que es la version compatible con la herramienta

results  => Directorio de archivos de salida de la herramienta. Patrón de nombre: Gen Seleccionado-Modelo Empleado-Estampa de tiempo.txt

src 	 => Paquetes de archivos fuentes:
		Archivos: Utilería para manipulación de archivos (LeerArchivo, EscribirArchivo)
		ClasificadorSinGUI: Herramienta de clasificación
			Clasificador.java => Clase Principal con metodo main que crea un objeto clasificador y llama al metodo ClasificarTXT para obtener como resultado los genes clasificados
			ClasificadorADN.java => Todos los métodos que implican el proceso de clasificación con funciones weka usando los modelos entrenados a través de java

test 	 => Directorio de Archivo de Prueba. Actualmente se lee gen_SST.txt

Happy Hacking!