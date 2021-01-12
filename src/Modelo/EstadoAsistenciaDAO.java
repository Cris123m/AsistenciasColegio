package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoAsistenciaDAO {
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List listar(){
        List<EstadoAsistencia> datos = new ArrayList<>();
        String sql = "SELEC * FROM estadoAsistencia";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                EstadoAsistencia u = new EstadoAsistencia();
                u.setId(rs.getInt(1));
                u.setEstado(rs.getString(2));
                datos.add(u);
            }
        } catch (Exception e) {
        }
        return datos;
    }
    
    public int registrar(EstadoAsistencia u){
        String sql = "INSERT INTO `estadoAsistencia`(`estado`) VALUES (?)";
        int idGenerado=0;
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getEstado());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo guardar");
            }

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                     idGenerado = generatedKeys.getInt(1);
            }
            u.setId(idGenerado);
            return idGenerado;
        } catch (Exception e) {
            System.err.println("EstadoAsistenciaDAO:"+e);
            return 0;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public boolean modificar(EstadoAsistencia u){
        String sql = "UPDATE `estadoAsistencia` SET `estado`=? WHERE  `id_estado_asistencia`=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getEstado());
            ps.setInt(4, u.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public boolean eliminar(int id){
        String sql = "DELETE FROM estadoAsistencia WHERE  id_estado_asistencia=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public EstadoAsistencia buscar(int id){
        String sql = "SELECT *  FROM estadoAsistencia WHERE  id_estado_asistencia=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            EstadoAsistencia u = new EstadoAsistencia();
            if(rs.next()){
                u.setId(rs.getInt(1));
                u.setEstado(rs.getString(2));
                return u;
            }
            return null;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}
