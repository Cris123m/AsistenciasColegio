package Modelo;

import java.util.Date;

public class Asistencia {
    private int id;
    private Date fecha;
    private int alumnoId;

    public Asistencia() {
    }

    public Asistencia(int id, Date fecha, int alumnoId) {
        this.id = id;
        this.fecha = fecha;
        this.alumnoId = alumnoId;
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
    
}
