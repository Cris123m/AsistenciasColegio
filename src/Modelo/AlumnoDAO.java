package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    //Funcion que devuelve la consulta de todos los alumnos
    public List listar(){
        List<Alumno> datos = new ArrayList<>();
        String sql = "SELECT * FROM alumnos";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Alumno d = new Alumno();
                d.setId(rs.getInt(1));
                d.setNombre(rs.getString(2));
                d.setApellido(rs.getString(3));
                d.setDireccion(rs.getString(4));
                d.setTelefono(rs.getString(5));
                d.setCursoId(rs.getInt(6));
                d.setUsuarioId(rs.getInt(7));
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
    
    public int registrar(Alumno d){
        String sql = "INSERT INTO `alumnos`(`nombre`, `apellido`, `direccion`, `telefono`, curso_id,`usuario_id`) VALUES (?,?,?,?,?,?)";
        int idGenerado=0;
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getApellido());
            ps.setString(3, d.getDireccion());
            ps.setString(4, d.getTelefono());
            ps.setInt(5, d.getCursoId());
            ps.setInt(6, d.getUsuarioId());
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
            System.err.println("AlumnoDAO: "+e);
            return 0;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public boolean modificar(Alumno d){
        String sql = "UPDATE `alumnos` SET `nombre`=?,`apellido`=?,`direccion`=?,`telefono`=?,curso_id=?,`usuario_id`=? WHERE `id_alumno`=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getApellido());
            ps.setString(3, d.getDireccion());
            ps.setString(4, d.getTelefono());
            ps.setInt(5, d.getCursoId());
            ps.setInt(6, d.getUsuarioId());
            ps.setInt(7, d.getId());
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
    
    public boolean eliminar(Alumno d){
        String sql = "DELETE FROM alumnos WHERE  id_alumno=?";
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
    
    public boolean buscar(Alumno d){
        String sql = "SELECT *  FROM alumnos WHERE  id_alumno=?";
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
                d.setCursoId(rs.getInt(6));
                d.setUsuarioId(rs.getInt(7));
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
    
    public List listarPorCurso(int idCurso){
        List<Alumno> datos = new ArrayList<>();
        String sql = "SELECT * FROM alumnos WHERE curso_id = ?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, idCurso);
            rs = ps.executeQuery();
            while(rs.next()){
                Alumno d = new Alumno();
                d.setId(rs.getInt(1));
                d.setNombre(rs.getString(2));
                d.setApellido(rs.getString(3));
                d.setDireccion(rs.getString(4));
                d.setTelefono(rs.getString(5));
                d.setCursoId(rs.getInt(6));
                d.setUsuarioId(rs.getInt(7));
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
    
}
