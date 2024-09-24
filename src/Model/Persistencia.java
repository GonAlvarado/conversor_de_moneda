package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Persistencia {
    public void guardarLista(List<Conversion> lista){
        try {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            FileWriter escritura = new FileWriter("historial.json", true);
            escritura.write("\n");
            escritura.write(gson.toJson(lista));
            escritura.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
