package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public List listar(){
        List<Curso> datos = new ArrayList<>();
        String sql = "SELECT * FROM cursos";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Curso c = new Curso();
                c.setId(rs.getInt(1));
                c.setNivel(rs.getInt(2));
                c.setParalelo(rs.getString(3).charAt(0));
                 c.setDocenteId(rs.getInt(4));
                datos.add(c);
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
    
    public int registrar(Curso c){
        String sql = "INSERT INTO `cursos`(  `nivel`, `paralelo`, `docente_id`) VALUES (?,?,?)";
        int idGenerado=0;
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, c.getNivel());
            ps.setString(2, c.getParalelo()+"");
            ps.setInt(3, c.getDocenteId());
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
            System.err.println("CursoDAO: "+e);
            return 0;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    public boolean modificar(Curso c){
        String sql = "UPDATE `cursos` SET `nivel`=?,`paralelo`=?,`docente_id`=? WHERE `id_curso`=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, c.getNivel());
            ps.setString(2, c.getParalelo()+"");
            ps.setInt(3, c.getDocenteId());
            ps.setInt(4, c.getId());
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
    
    public boolean eliminar(Curso c){
        String sql = "DELETE FROM cursos WHERE  id_curso=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, c.getId());
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
    
    public boolean buscar(Curso c){
        String sql = "SELECT *  FROM cursos WHERE  id_curso=?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, c.getId());
            rs = ps.executeQuery();
            if(rs.next()){
                c.setId(rs.getInt(1));
                c.setNivel(rs.getInt(2));
                c.setParalelo(rs.getString(3).charAt(0));
                 c.setDocenteId(rs.getInt(4));
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
    
    public boolean buscarCurso(Curso c){
        String sql = "SELECT *  FROM cursos WHERE  nivel = ? AND paralelo = ?";
        try {
            con=conectar.getConnection();
            ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, c.getNivel());
            ps.setString(2, c.getParalelo()+"");
            rs = ps.executeQuery();
            if(rs.next()){
                c.setId(rs.getInt(1));
                c.setNivel(rs.getInt(2));
                c.setParalelo(rs.getString(3).charAt(0));
                 c.setDocenteId(rs.getInt(4));
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
