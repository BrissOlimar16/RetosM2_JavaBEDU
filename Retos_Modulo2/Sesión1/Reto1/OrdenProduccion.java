public abstract class OrdenProduccion {
    private final String codigo;
    private final int cantidad;

    public OrdenProduccion(String codigo, int cantidad) {
        this.codigo = codigo;
        this.cantidad = cantidad;
    }

    public String getCodigo() { return codigo; }
    public int getCantidad() { return cantidad; }

    public abstract void mostrarResumen();
}