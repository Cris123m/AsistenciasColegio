package Modelo;

import java.util.Date;

public class Asistencia {
    private int id;
    private Date fecha;
    private int alumnoId;
    private int estadoId;

    public Asistencia() {
    }

    public Asistencia(int id, Date fecha, int alumnoId, int estadoId) {
        this.id = id;
        this.fecha = fecha;
        this.alumnoId = alumnoId;
        this.estadoId = estadoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

}
