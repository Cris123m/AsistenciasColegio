package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocenteDAO {
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List listar(){
        List<Docente> datos = new ArrayList<>();
        String sql = "SELECT * FROM docentes";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Docente d = new Docente();
                d.setId(rs.getInt(1));
                d.setNombre(rs.getString(2));
                d.setApellido(rs.getString(3));
                d.setDireccion(rs.getString(4));
                d.setTelefono(rs.getString(5));
                 d.setUsuarioId(rs.getInt(6));
                datos.add(d);
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
    
    public int registrar(Docente d){
        String sql = "INSERT INTO `docentes`( `nombre`, `apellido`, `direccion`, `telefono`, `usuario_id`) VALUES (?,?,?,?,?)";
        int idGenerado=0;
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getApellido());
            ps.setString(3, d.getDireccion());
            ps.setString(4, d.getTelefono());
            ps.setInt(5, d.getUsuarioId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo guardar");
            }

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                     idGenerado = generatedKeys.getInt(1);
            }
            return idGenerado;
        } catch (SQLException e) {
            System.err.println("DocenteDAO: "+e);
            return 0;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public boolean modificar(Docente d){
        String sql = "UPDATE `docentes` SET `nombre`=?,`apellido`=?,`direccion`=?,`telefono`=?,`usuario_id`=? WHERE `id_docente`=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getApellido());
            ps.setString(3, d.getDireccion());
            ps.setString(4, d.getTelefono());
            ps.setInt(5, d.getUsuarioId());
            ps.setInt(6, d.getId());
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
    
    public boolean eliminar(Docente d){
        String sql = "DELETE FROM docentes WHERE  id_docente=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, d.getId());
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
    
    public boolean buscar(Docente d){
        String sql = "SELECT *  FROM docentes WHERE  id_docente=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, d.getId());
            rs = ps.executeQuery();
            if(rs.next()){
                d.setId(rs.getInt(1));
                d.setNombre(rs.getString(2));
                d.setApellido(rs.getString(3));
                d.setDireccion(rs.getString(4));
                d.setTelefono(rs.getString(5));
                 d.setUsuarioId(rs.getInt(6));
                return true;
            }
            return false;
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
    
}
