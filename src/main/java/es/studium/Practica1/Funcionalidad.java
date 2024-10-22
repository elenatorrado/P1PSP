package es.studium.Practica1;

import java.io.File;
import java.io.IOException;

public class Funcionalidad {

    // Declaramos la función para buscar archivos con la extensión especificada
    // Recibe como parámetros la extensión y la ruta del directorio
    public static String buscarArchivos(String extension, String aplicacion) {

        // Representa la Unidad de Almacenamiento que utilizamos para el programa
        // El objeto 'fichero' representa el directorio (o archivo) donde vamos a buscar
        File fichero = new File(aplicacion);

        // Verificamos si el fichero (directorio) existe
        if (!fichero.exists()) {
            // Si no existe, retornamos un mensaje de error
            return "El directorio o archivo " + aplicacion + " no existe.\n";
        }

        // Verificamos si la ruta especificada es realmente un directorio
        if (!fichero.isDirectory()) {
            // Si no es un directorio, retornamos un mensaje de error
            return aplicacion + " no es un directorio.\n";
        }

        // Listamos todos los archivos y subdirectorios dentro del directorio
        File[] almacenamientoC = fichero.listFiles();

        // Usamos StringBuilder para acumular las rutas de los archivos encontrados
        StringBuilder acumuladorRutas = new StringBuilder();

        // Verificamos si el directorio contiene archivos o está vacío
        if (almacenamientoC != null && almacenamientoC.length > 0) {
            // Recorremos el contenido del directorio
            for (File documento : almacenamientoC) {

                // Si el documento es un directorio, llamamos recursivamente al método
                // Esto nos permite buscar también en subdirectorios
                if (documento.isDirectory()) {
                    // Llamada recursiva para buscar dentro del subdirectorio
                    acumuladorRutas.append(buscarArchivos(extension, documento.getAbsolutePath()));
                }
                // Si el documento es un archivo y tiene la extensión especificada
                else if (documento.getName().endsWith(extension)) {
                    // Acumulamos la ruta completa del archivo en el StringBuilder
                    acumuladorRutas.append(documento.getAbsolutePath()).append("\n");
                }
            }
        } else {
            // Si el directorio está vacío, retornamos un mensaje
            return "El directorio " + aplicacion + " está vacío.\n";
        }

        // Devolvemos todas las rutas encontradas con la extensión especificada
        return acumuladorRutas.toString();
    }

    // Método para ejecutar un archivo .exe
    // Recibe como parámetro la ruta completa del archivo que se desea ejecutar
    public static void ejecutarArchivo(String filePath) {
        try {
            // Mostramos en la consola qué archivo estamos intentando ejecutar
            System.out.println("Intentando ejecutar: " + filePath);

            // Usamos ProcessBuilder para ejecutar el archivo
            // Ejecutamos cmd.exe con el parámetro /c que permite ejecutar un comando
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", filePath);
            // Iniciamos el proceso que ejecuta el archivo
            pb.start();
        } catch (IOException e) {
            // Si ocurre algún error (por ejemplo, si el archivo no es ejecutable o la ruta está mal)
            // se captura la excepción y se imprime el error en la consola para depuración
            e.printStackTrace();
        }
    }
}
