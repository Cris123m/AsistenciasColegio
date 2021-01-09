package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoDAO {
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List listar(){
        List<Tipo> datos = new ArrayList<>();
        String sql = "SELECT * FROM tipos";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Tipo t = new Tipo();
                t.setId(rs.getInt(1));
                t.setTipo(rs.getString(2));
                datos.add(t);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return datos;
    }
    
}
