import java.util.concurrent.*;

public class MovilidadApp {

    public static void main(String[] args) {
        System.out.println("🚦 Iniciando simulación de viaje...\n");

        // 🏁 Ejecutar las operaciones en paralelo
        CompletableFuture<String> rutaFuture = calcularRuta();
        CompletableFuture<Double> tarifaFuture = estimarTarifa();

        // 🔗 Combina ambas operaciones cuando ambas terminan
        CompletableFuture<Void> viajeCompleto = rutaFuture.thenCombine(tarifaFuture,
                        (ruta, tarifa) -> {
                            return "🚗 Ruta calculada: " + ruta + " | Tarifa estimada: $" + tarifa;
                        })
                .thenAccept(System.out::println) // 📤 Imprime el resultado final
                .exceptionally(ex -> { // ❌ Manejo de errores
                    System.out.println("🚨 Error al procesar el viaje: " + ex.getMessage());
                    return null;
                });

        // Esperar que todo el proceso asincrónico termine
        viajeCompleto.join();
    }

    // 🗺️ Simula el cálculo de la ruta
    public static CompletableFuture<String> calcularRuta() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("🗺️ Calculando ruta...");
            dormir(3); // Simula latencia de 3 segundos
            return "Centro -> Norte";
        });
    }

    // 💰 Simula la estimación de la tarifa
    public static CompletableFuture<Double> estimarTarifa() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("💰 Estimando tarifa...");
            dormir(2); // Simula latencia de 2 segundos
            return 75.50;
        });
    }

    // 💤 Método auxiliar para simular latencia
    public static void dormir(int segundos) {
        try {
            TimeUnit.SECONDS.sleep(segundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}