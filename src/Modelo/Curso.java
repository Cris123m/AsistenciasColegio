package Modelo;

public class Curso {
    private int id;
    private int nivel;
    private char paralelo;
    private int docenteId;

    public Curso() {
    }

    public Curso(int id, int nivel, char paralelo, int docenteId) {
        this.id = id;
        this.nivel = nivel;
        this.paralelo = paralelo;
        this.docenteId = docenteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public char getParalelo() {
        return paralelo;
    }

    public void setParalelo(char paralelo) {
        this.paralelo = paralelo;
    }

    public int getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(int docenteId) {
        this.docenteId = docenteId;
    }
    
    @Override
    public String toString() {
      return this.nivel+" "+this.paralelo;
   }
}
