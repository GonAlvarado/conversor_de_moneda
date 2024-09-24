package Controller;

import Model.Conexion;
import Model.Conversion;
import Model.ConversionRecord;
import Model.Persistencia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Principal {
    static String monedaBase;
    static String monedaDeseada;
    static Double monto;
    static int opcionContinuar;

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        List<Conversion> conversiones = new ArrayList<>();

        boolean control = true;

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        System.out.println("""
                *********************************************
                
                  ¡Bienvenido a nuestro conversor de moneda!
                
                *********************************************
                """);

        while(control){
            ingresarValores(teclado);

            try{
                var conexion = new Conexion();
                String json = conexion.convertir(monedaBase, monedaDeseada, monto);
                ConversionRecord conversionRecord = gson.fromJson(json, ConversionRecord.class);
                Conversion conversion = new Conversion(conversionRecord);
                conversion.setMonto(monto);
                LocalDateTime fecha = LocalDateTime.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                String fechaFormateada = fecha.format(formato);
                conversion.setFecha(fechaFormateada);
                conversiones.add(conversion);
                System.out.printf("""
                        *********************************************
                        
                           %s %s equivale a %s %s
                        
                        *********************************************
                        """, conversion.getMonto(), conversion.getMonedaBase(), conversion.getResultado(), conversion.getMonedaDeseada());
            } catch (JsonSyntaxException e) {
                System.out.println("Ocurrio un error: " + e.getMessage());
            }

            mostrarMenu(teclado, conversiones);

            if (opcionContinuar == 3){
                control = false;
            }
        }

        var persistir = new Persistencia();
        persistir.guardarLista(conversiones);

        System.out.println("""
                *********************************************
                
                   Gracias por utilizar nuestro servicio.
                
                *********************************************
                """);
    }

    static void ingresarValores(Scanner scanner){
        boolean control = true;

        while (control){
            try {
                System.out.println("""
                   *********************************************
                   \s
                       Ingrese la moneda que desea convertir:
                      \s
                       1 - Peso argentino
                       2 - Boliviano boliviano
                       3 - Real brasileño
                       4 - Peso chileno
                       5 - Peso colombiano
                       6 - Dólar estadounidense
                   \s
                   *********************************************
                   \s""");

                int opcionMonedaBase = scanner.nextInt();

                switch (opcionMonedaBase){
                    case 1:
                        monedaBase = "ARS";
                        break;
                    case 2:
                        monedaBase = "BOB";
                        break;
                    case 3:
                        monedaBase = "BRL";
                        break;
                    case 4:
                        monedaBase = "CLP";
                        break;
                    case 5:
                        monedaBase = "COP";
                        break;
                    case 6:
                        monedaBase = "USD";
                        break;
                    default:
                        System.out.println("""
                            *********************************************
                            
                                 Opción no válida.
                            
                            *********************************************
                            """);
                        continue;
                }

                System.out.println("""
                   *********************************************
                   \s
                       Ingrese la moneda que desea obtener:
                      \s
                       1 - Peso argentino
                       2 - Boliviano boliviano
                       3 - Real brasileño
                       4 - Peso chileno
                       5 - Peso colombiano
                       6 - Dólar estadounidense
                   \s
                   *********************************************
                   \s""");

                int opcionMonedaDeseada = scanner.nextInt();

                switch (opcionMonedaDeseada){
                    case 1:
                        monedaDeseada = "ARS";
                        break;
                    case 2:
                        monedaDeseada = "BOB";
                        break;
                    case 3:
                        monedaDeseada = "BRL";
                        break;
                    case 4:
                        monedaDeseada = "CLP";
                        break;
                    case 5:
                        monedaDeseada = "COP";
                        break;
                    case 6:
                        monedaDeseada = "USD";
                        break;
                    default:
                        System.out.println("""
                            *********************************************
                            
                               Opción no válida.
                            
                            *********************************************
                            """);
                        continue;
                }

                System.out.println("""
                    *********************************************
                    
                       Ingrese el monto que desea convertir:
                    
                    *********************************************
                    """);

                monto = scanner.nextDouble();

                control = false;
            } catch (InputMismatchException e) {
                System.out.println("Solo se permiten caracteres numéricos.");
                scanner.nextLine();
            }
        }
    }

    static void mostrarMenu(Scanner scanner, List<Conversion> conversiones){
        while (true){
            try {
                System.out.println("""
                    *********************************************
                    
                       1 - Realizar otra conversión
                       2 - Historial de convesiones
                       3 - Salir
                    
                    *********************************************
                    """);

                int opcion = scanner.nextInt();

                if(opcion == 1){
                    break;
                } else if (opcion == 2) {
                    System.out.println(conversiones);
                } else if (opcion == 3) {
                    opcionContinuar = 3;
                    break;
                } else {
                    System.out.println("""
                            *********************************************
                            
                                 Opción no válida.
                            
                            *********************************************
                            """);
                }
            } catch (InputMismatchException e) {
                System.out.println("Solo se permiten caracteres numéricos.");
                scanner.nextLine();
            }
        }
    }
}
