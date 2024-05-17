public class Moneda {

    private String nombre;
    private float valor;

    public Moneda(String nombre, float valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public float convertir(Moneda moneda, float cantidad) {
        return cantidad * this.valor / moneda.getValor();
    }

    @Override
    public String toString() {
        return "Moneda{" +
                "nombre='" + nombre + '\'' +
                ", valor=" + valor +
                '}';
    }

}
