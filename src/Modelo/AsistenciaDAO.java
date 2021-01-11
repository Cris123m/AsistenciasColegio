package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAO {
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List listar(){
        List<Asistencia> datos = new ArrayList<>();
        String sql = "SELECT * FROM asistencias";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Asistencia d = new Asistencia();
                d.setId(rs.getInt(1));
                d.setFecha(rs.getDate(2));
                d.setAlumnoId(rs.getInt(3));
                d.setEstadoId(rs.getInt(4));
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
    
    public int registrar(Asistencia d){
        String sql = "INSERT INTO `asistencias`( `fecha`, `alumno_id`,`estado_id`) VALUES (?,?,?)";
        int idGenerado=0;
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, new Timestamp(d.getFecha().getTime()) );
            ps.setInt(2, d.getAlumnoId());
            ps.setInt(3, d.getEstadoId());
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
            System.err.println("AsistenciaDAO: "+e);
            return 0;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public boolean modificar(Asistencia d){
        String sql = "UPDATE `asistencias` SET `fecha`=?,`alumno_id`=?,`estado_id`=? WHERE `id_asistencia`=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, d.getFecha().toString());
            ps.setInt(2, d.getAlumnoId());
            ps.setInt(3, d.getEstadoId());
            ps.setInt(4, d.getId());
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
    
    public boolean eliminar(Asistencia d){
        String sql = "DELETE FROM asistencias WHERE  id_asistencia=?";
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
    
    public boolean buscar(Asistencia d){
        String sql = "SELECT *  FROM asistencias WHERE  id_asistencia=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, d.getId());
            rs = ps.executeQuery();
            if(rs.next()){
                d.setId(rs.getInt(1));
                d.setFecha(rs.getDate(2));
                d.setAlumnoId(rs.getInt(3));
                d.setEstadoId(rs.getInt(4));
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
    
    public int asistenciaAlumno(int AlumnoId){
        String sql = "SELECT COUNT(*) FROM `asistencias` WHERE alumno_id=? AND estado_id=1";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, AlumnoId);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            System.err.println(e);
            return 0;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
   
     public boolean asistenciaAlFecha(Asistencia a,String fecha){
        String sql = "SELECT * FROM `asistencias` WHERE alumno_id=? and fecha=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, a.getAlumnoId());
            ps.setString(2, fecha);
            rs = ps.executeQuery();
            if(rs.next()){
                a.setId(rs.getInt(1));
                a.setFecha(rs.getDate(2));
                a.setAlumnoId(rs.getInt(3));
                a.setEstadoId(rs.getInt(4));
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
     
     public int cantAsistenciasFecha(String fecha, int idCurso){
        String sql = "SELECT COUNT(*) FROM asistencias asis " +
                                "INNER JOIN alumnos alu ON asis.alumno_id=alu.id_alumno " +
                                "INNER JOIN cursos cu on cu.id_curso=alu.curso_id " +
                                "WHERE asis.fecha = ? AND cu.id_curso = ?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, fecha);
            ps.setInt(2, idCurso);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            System.err.println(e);
            return 0;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}
