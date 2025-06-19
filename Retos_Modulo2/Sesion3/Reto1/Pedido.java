import java.util.Optional;

public class Pedido {
    private final String cliente;
    private final String tipoEntrega; // "domicilio" o "local"
    private final String telefono; // Puede ser null

    public Pedido(String cliente, String tipoEntrega, String telefono) {
        this.cliente = cliente;
        this.tipoEntrega = tipoEntrega;
        this.telefono = telefono;
    }

    public String getCliente() { return cliente; }
    public String getTipoEntrega() { return tipoEntrega; }

    // 📞 Método seguro para obtener el teléfono como Optional
    public Optional<String> getTelefono() {
        return Optional.ofNullable(telefono);
    }
}