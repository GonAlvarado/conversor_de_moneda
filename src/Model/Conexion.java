package Model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Conexion {
    public String convertir(String monedaBase, String monedaDeseada, double monto){
        try {
            String api = "https://v6.exchangerate-api.com/v6/9cde34d9ad9414360fe2320a/pair/"+monedaBase+"/"+monedaDeseada+"/"+monto;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(api)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            System.out.println("  Error al realizar la conversión");
        }
        return null;
    }
}
