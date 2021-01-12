package Modelo;

public class EstadoAsistencia {
    private int id;
    private String estado;

    public EstadoAsistencia() {
    }

    public EstadoAsistencia(int id, String estado) {
        this.id = id;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
      return this.estado;
   }
    
}
