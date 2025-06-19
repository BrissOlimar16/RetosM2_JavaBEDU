public class Profesional implements Runnable {

    private final String nombre;
    private final RecursoMedico recurso;

    public Profesional(String nombre, RecursoMedico recurso) {
        this.nombre = nombre;
        this.recurso = recurso;
    }

    @Override
    public void run() {
        recurso.usar(nombre); // 👨‍⚕️ Intenta usar el recurso
    }
}