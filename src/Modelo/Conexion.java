package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    Connection con;
    public Connection getConnection(){
        String url ="jdbc:mysql://localhost:3306/colegio";
        String user="root";
        String pass ="root";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=(Connection) DriverManager.getConnection(url,user,pass);
        } catch (Exception e) {
            //System.out.println("Error en conexión: "+e.getMessage());
            e.printStackTrace();
        }
        return con;
    }
}
