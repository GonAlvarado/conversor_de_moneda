package Model;

import java.io.*;
import java.util.List;

public class Persistencia {
    public void guardarLista(List<Conversion> lista){
        try {
            FileWriter escritura = new FileWriter("historial.txt", true);
            for (Conversion conversion : lista){
                escritura.write(conversion.toString() + "\n");
            }
            escritura.close();
        } catch (IOException e) {
            System.out.println("   Error al registrar las conversiones");
        }

    }

    public void leerLista(){
        try {
            Reader lector = new FileReader("historial.txt");
            BufferedReader lectura = new BufferedReader(lector);
            String linea;
            while ((linea = lectura.readLine()) != null){
                System.out.println("   " + linea);
            }
        } catch (IOException e) {
            System.out.println("   No se ha registrado ninguna conversión.");
        }
    }
}
