package ProgramacionIII.tpe;

public class Procesador {

    private String id_procesador;
    private String codigo;
    private boolean esta_refrigerado;
    private int anio_funcionamiento;

    public Procesador(String id_procesador, String codigo, boolean esta_refrigerado, int anio_funcionamiento) {
        this.id_procesador = id_procesador;
        this.codigo = codigo;
        this.esta_refrigerado = esta_refrigerado;
        this.anio_funcionamiento = anio_funcionamiento;
    }

    public String getId_procesador() {
        return id_procesador;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isEsta_refrigerado() {
        return esta_refrigerado;
    }

    public int getAnio_funcionamiento() {
        return anio_funcionamiento;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setEsta_refrigerado(boolean esta_refrigerado) {
        this.esta_refrigerado = esta_refrigerado;
    }

    public void setAnio_funcionamiento(int anio_funcionamiento) {
        this.anio_funcionamiento = anio_funcionamiento;
    }

}