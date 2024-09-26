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
    static Persistencia persistencia = new Persistencia();
    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        List<Conversion> conversiones = new ArrayList<>();

        boolean salir = false;

        while (true){
            try {
                System.out.println("""
               *********************************************
               \s
                  ¡Bienvenido a nuestro conversor de moneda!
                 \s
                  1 - Realizar una conversión
                  2 - Historial de conversiones
                  3 - Salir
               \s
               *********************************************
               \s""");

                int opcion = teclado.nextInt();
                if (opcion == 1){
                    break;
                } else if (opcion ==2) {
                    System.out.println("*********************************************\n");
                    System.out.println("   Historial de conversiones:\n");
                    persistencia.leerLista();
                    System.out.println("\n*********************************************");
                } else if (opcion == 3) {
                    salir = true;
                    break;
                } else {
                    System.out.println("""
                            *********************************************
                            
                                 Opción no válida.
                            
                            *********************************************
                            """);
                }
            } catch (InputMismatchException e) {
                System.out.println("""
                            *********************************************
                            
                                 Solo se permiten caracteres numéricos.
                            
                            *********************************************
                            """);
                teclado.nextLine();
            }

        }

        if (!salir){
            do {
                ingresarValores(teclado);

                convertirMonto(monedaBase, monedaDeseada, conversiones);

                mostrarMenu(teclado, conversiones);

            } while (opcionContinuar != 5);
        }

        if (!salir){
            guardarCopia(teclado, conversiones);
        }

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
                System.out.println("""
                            *********************************************
                            
                                 Solo se permiten caracteres numéricos.
                            
                            *********************************************
                            """);
                scanner.nextLine();
            }
        }
    }

    static void convertirMonto(String monedaBase, String monedaDeseada, List<Conversion> conversiones){
        try{
            var conexion = new Conexion();
            String json = conexion.convertir(monedaBase, monedaDeseada, monto);
            if (json != null){
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
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Ocurrio un error: " + e.getMessage());
        }
    }

    static void mostrarMenu(Scanner scanner, List<Conversion> conversiones){
        while (true){
            try {
                System.out.println("""
                        *********************************************
                        
                           1 - Realizar otra conversión
                           2 - Invertir conversión
                           3 - Últimas conversiones
                           4 - Historial de conversiones
                           5 - Salir
                        
                        *********************************************
                        """);

                int opcion = scanner.nextInt();

                if (opcion == 1) {
                    break;
                } else if (opcion == 2) {
                    convertirMonto(monedaDeseada, monedaBase, conversiones);
                } else if (opcion == 3) {
                    System.out.println("*********************************************\n");
                    System.out.println("   Últimas conversiones:\n");
                    for (Conversion conversion : conversiones){
                        System.out.println("   " + conversion.toString());
                    }
                    System.out.println("\n*********************************************");
                } else if (opcion == 4) {
                    System.out.println("*********************************************\n");
                    System.out.println("   Historial de conversiones:\n");
                    persistencia.leerLista();
                    System.out.println("\n*********************************************");
                } else if (opcion == 5) {
                    opcionContinuar = 5;
                    break;
                } else {
                    System.out.println("""
                            *********************************************
                            
                                 Opción no válida.
                            
                            *********************************************
                            """);
                }
            } catch (InputMismatchException e) {
                System.out.println("""
                            *********************************************
                            
                                 Solo se permiten caracteres numéricos.
                            
                            *********************************************
                            """);
                scanner.nextLine();
            }
        }
    }

    static void guardarCopia(Scanner scanner, List<Conversion> conversiones){
        while (true){
            try {
                System.out.println("""
                *************************************************
               \s
                   ¿Desea registrar las últimas conversiones realizadas?
                  \s
                   1 - Si
                   2 - No
               \s
                *************************************************
               \s""");

                int opcion = scanner.nextInt();

                if (opcion == 1){
                    persistencia.guardarLista(conversiones);
                    System.out.println("""
                            *********************************************
                            
                                 Copia realizada.
                            
                            *********************************************
                            """);
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("""
                            *********************************************
                            
                                 Solo se permiten caracteres numéricos.
                            
                            *********************************************
                            """);
                scanner.nextLine();
            }
        }
    }
}