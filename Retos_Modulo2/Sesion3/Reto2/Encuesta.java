import java.util.Optional;

public class Encuesta {
    private final String paciente;
    private final String comentario; // Puede ser null
    private final int calificacion; // 1 a 5

    public Encuesta(String paciente, String comentario, int calificacion) {
        this.paciente = paciente;
        this.comentario = comentario;
        this.calificacion = calificacion;
    }

    public int getCalificacion() { return calificacion; }

    // Método seguro para obtener el comentario como Optional
    public Optional<String> getComentario() {
        return Optional.ofNullable(comentario);
    }
}