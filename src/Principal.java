import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Principal {

    public static void main(String[] args) throws IOException, InterruptedException{
        Map<String, Moneda> monedas = new HashMap<>();

        String url = "https://v6.exchangerate-api.com/v6/5b3d0cf98edb069a53963edc/latest/USD";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();

        Gson gson = new Gson();
        ExchangeRates exchangeRates = gson.fromJson(json, ExchangeRates.class);

        // Lista para almacenar las monedas seleccionadas
        Map<String, Float> rates = exchangeRates.getConversion_rates();

        // Filtra las monedas específicas
        if (rates.containsKey("ARS")) {
            Moneda ars = new Moneda("ARS - Peso argentino", rates.get("ARS"));
            monedas.put("ARS", ars);
        }
        if (rates.containsKey("CRC")) {
            Moneda crc = new Moneda("CRC - Colon Costarricence", rates.get("CRC"));
            monedas.put("CRC", crc);
        }
        if (rates.containsKey("USD")) {
            Moneda usd = new Moneda("USD - Dólar estadounidense", rates.get("USD"));
            monedas.put("USD", usd);
        }

        mostrarMenu(monedas);

    }

    public static void mostrarMenu(Map<String, Moneda> monedas) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 7) {
            System.out.println("\nMenú de Conversión de Monedas");
            System.out.println("1. Convertir ARS a USD");
            System.out.println("2. Convertir USD a ARS");
            System.out.println("3. Convertir ARS a CRC");
            System.out.println("4. Convertir CRC a ARS");
            System.out.println("5. Convertir USD a CRC");
            System.out.println("6. Convertir CRC a USD");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    convertirMoneda(monedas, "ARS", "USD");
                    break;
                case 2:
                    convertirMoneda(monedas, "USD", "ARS");
                    break;
                case 3:
                    convertirMoneda(monedas, "ARS", "CRC");
                    break;
                case 4:
                    convertirMoneda(monedas, "CRC", "ARS");
                    break;
                case 5:
                    convertirMoneda(monedas, "USD", "CRC");
                    break;
                case 6:
                    convertirMoneda(monedas, "CRC", "USD");
                    break;
                case 7:
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
                    break;
            }
        }
        scanner.close();
    }

    public static void convertirMoneda(Map<String, Moneda> monedas, String from, String to) {
        Scanner scanner = new Scanner(System.in);

        Moneda monedaFrom = monedas.get(from);
        Moneda monedaTo = monedas.get(to);

        System.out.printf("Ingrese la cantidad de %s a convertir: ", monedaFrom.getNombre());
        float cantidad = scanner.nextFloat();

        float resultado = monedaTo.convertir(monedaFrom, cantidad);
        System.out.printf("%.2f %s son %.2f %s\n", cantidad, monedaFrom.getNombre(), resultado, monedaTo.getNombre());
    }

}

