package Modelo;

public class Usuario {
    private int id;
    private String user;
    private String password;
    private int tipoId;

    public Usuario() {
    }

    public Usuario(int id, String user, String password, int tipoId) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.tipoId = tipoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    
}
