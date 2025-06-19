import reactor.core.publisher.Flux;
import java.time.Duration;
import java.util.Random;

public class MonitoreoUCI {

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();

        // Simular flujo de cada paciente
        Flux<EventoVital> paciente1 = generarSignosVitales(1, random);
        Flux<EventoVital> paciente2 = generarSignosVitales(2, random);
        Flux<EventoVital> paciente3 = generarSignosVitales(3, random);

        // 🔗 Fusionar los flujos de los 3 pacientes
        Flux.merge(paciente1, paciente2, paciente3)
                .filter(EventoVital::esCritico) // 🔍 Filtrar solo eventos críticos
                .sort((e1, e2) -> e1.prioridad() - e2.prioridad()) // 🔝 Priorizar FC sobre otros eventos
                .delayElements(Duration.ofSeconds(1)) // ⏳ Backpressure (procesar lentamente)
                .subscribe(System.out::println);

        Thread.sleep(15000); // Esperar para ver el flujo completo
    }

    // 🔧 Método para generar flujo de signos vitales por paciente
    private static Flux<EventoVital> generarSignosVitales(int pacienteId, Random random) {
        return Flux.interval(Duration.ofMillis(300))
                .map(tick -> {
                    // Generar datos aleatorios
                    int fc = 40 + random.nextInt(100); // 40-140 bpm
                    int pas = 80 + random.nextInt(80); // 80-160 mmHg sistólica
                    int pad = 50 + random.nextInt(50); // 50-100 mmHg diastólica
                    int spo2 = 85 + random.nextInt(15); // 85-100%

                    return new EventoVital(pacienteId, fc, pas, pad, spo2);
                })
                .take(10); // Limitar cantidad de eventos por paciente
    }

    // 📄 Clase auxiliar para los eventos de signos vitales
    static class EventoVital {
        private final int pacienteId;
        private final int fc;
        private final int pas;
        private final int pad;
        private final int spo2;

        public EventoVital(int pacienteId, int fc, int pas, int pad, int spo2) {
            this.pacienteId = pacienteId;
            this.fc = fc;
            this.pas = pas;
            this.pad = pad;
            this.spo2 = spo2;
        }

        // 🩺 Detectar si el evento es crítico
        public boolean esCritico() {
            return fc < 50 || fc > 120 || pas < 90 || pas > 140 || pad < 60 || pad > 90 || spo2 < 90;
        }

        // 🔝 Prioridad para ordenar eventos (FC=1, SpO2=2, PA=3)
        public int prioridad() {
            if (fc < 50 || fc > 120) return 1;
            if (spo2 < 90) return 2;
            return 3;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (fc < 50 || fc > 120) sb.append("⚠️ Paciente ").append(pacienteId).append(" - FC crítica: ").append(fc).append(" bpm\n");
            if (spo2 < 90) sb.append("⚠️ Paciente ").append(pacienteId).append(" - SpO2 baja: ").append(spo2).append("%\n");
            if (pas < 90 || pas > 140 || pad < 60 || pad > 90)
                sb.append("⚠️ Paciente ").append(pacienteId).append(" - PA crítica: ").append(pas).append("/").append(pad).append(" mmHg\n");
            return sb.toString().trim();
        }
    }
}



