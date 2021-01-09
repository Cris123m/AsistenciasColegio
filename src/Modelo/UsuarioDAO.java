package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List listar(){
        List<Usuario> datos = new ArrayList<>();
        String sql = "SELEC * FROM usuarios";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Usuario u = new Usuario();
                u.setId(rs.getInt(1));
                u.setUser(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setTipoId(rs.getInt(4));
                datos.add(u);
            }
        } catch (Exception e) {
        }
        return datos;
    }
    
    public int registrar(Usuario u){
        String sql = "INSERT INTO `usuarios`( `user`, `password`, `tipo_id`) VALUES (?,?,?)";
        int idGenerado=0;
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getUser());
            ps.setString(2, u.getPassword());
            ps.setInt(3, u.getTipoId());
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
            System.err.println("UsuarioDAO:"+e);
            return 0;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public boolean modificar(Usuario u){
        String sql = "UPDATE `usuarios` SET `user`=?,`password`=?,`tipo_id`=? WHERE  `id_usuario`=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getUser());
            ps.setString(2, u.getPassword());
            ps.setInt(3, u.getTipoId());
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
        String sql = "DELETE FROM usuarios WHERE  id_usuario=?";
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
    
    public Usuario buscarId(int id){
        String sql = "SELECT *  FROM usuarios WHERE  id_usuario=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            Usuario u = new Usuario();
            if(rs.next()){
                u.setId(rs.getInt(1));
                u.setUser(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setTipoId(rs.getInt(4));
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
    
    public boolean buscar(Usuario u){
        String sql = "SELECT *  FROM usuarios WHERE  user=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getUser());
            rs = ps.executeQuery();
            if(rs.next()){
                u.setId(rs.getInt(1));
                u.setUser(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setTipoId(rs.getInt(4));
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
    
    public boolean login(Usuario u){
        String sql = "SELECT *  FROM usuarios WHERE  user=? AND password=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getUser());
            ps.setString(2, u.getPassword());
            rs = ps.executeQuery();
            if(rs.next()){
                u.setId(rs.getInt(1));
                u.setUser(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setTipoId(rs.getInt(4));
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
