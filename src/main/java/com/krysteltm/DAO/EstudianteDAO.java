/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.krysteltm.DAO;

/**
 *
 * @author KRYSTEL
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.krysteltm.MODEL.Estudiante;
import com.krysteltm.UTIL.ConexionSQLite;
import java.sql.SQLException;

public class EstudianteDAO {

    // INSERTAR
    public boolean insertar(Estudiante e) {

        String sql = """
            INSERT INTO estudiantes
            (cedula, facultad, carrera, nombre, apellido, semestre,
             promedio, beca, estado_solicitud, correo)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getCedula());
            ps.setString(2, e.getFacultad());
            ps.setString(3, e.getCarrera());
            ps.setString(4, e.getNombre());
            ps.setString(5, e.getApellido());
            ps.setString(6, e.getSemestre());
            ps.setDouble(7, e.getPromedio());
            ps.setString(8, e.getBeca());
            ps.setString(9, e.getEstadoSolicitud());
            ps.setString(10, e.getCorreo());

            return ps.executeUpdate() > 0;

        } catch (Exception ex) {
            System.out.println("Error insertando estudiante: " + ex.getMessage());
            return false;
        }
    }

    // LISTAR TODOS
    public List<Estudiante> listar() {

        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Estudiante e = new Estudiante();
                e.setId(rs.getInt("id"));
                e.setCedula(rs.getString("cedula"));
                e.setFacultad(rs.getString("facultad"));
                e.setCarrera(rs.getString("carrera"));
                e.setNombre(rs.getString("nombre"));
                e.setApellido(rs.getString("apellido"));
                e.setSemestre(rs.getString("semestre"));
                e.setPromedio(rs.getDouble("promedio"));
                e.setBeca(rs.getString("beca"));
                e.setEstadoSolicitud(rs.getString("estado_solicitud"));
                e.setCorreo(rs.getString("correo"));
                e.setTipoBeca(rs.getString("tipo_beca"));

                lista.add(e);
            }

        } catch (Exception ex) {
            System.out.println("Error listando estudiantes: " + ex.getMessage());
        }

        return lista;
    }

    // BUSCAR POR CORREO
    public Estudiante buscarPorCorreo(String correo) {

        String sql = "SELECT * FROM estudiantes WHERE correo = ?";

        try ( 
            Connection conn = ConexionSQLite.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Estudiante e = new Estudiante();
                e.setId(rs.getInt("id"));
                e.setCedula(rs.getString("cedula"));
                e.setFacultad(rs.getString("facultad"));
                e.setCarrera(rs.getString("carrera"));
                e.setNombre(rs.getString("nombre"));
                e.setApellido(rs.getString("apellido"));
                e.setSemestre(rs.getString("semestre"));
                e.setPromedio(rs.getDouble("promedio"));
                e.setBeca(rs.getString("beca"));
                e.setEstadoSolicitud(rs.getString("estado_solicitud"));
                e.setCorreo(rs.getString("correo"));
                e.setTipoBeca(rs.getString("tipo_beca"));
                return e;
            }

        } catch (Exception ex) {
            System.out.println("Error buscando estudiante: " + ex.getMessage());
        }

        return null;
    }
    public boolean actualizarEstado(int id, String estado) {

        String sql = "UPDATE estudiantes SET estado_solicitud = ? WHERE id = ?";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error actualizando estado: " + e.getMessage());
            return false;
        }
    }
    
    public boolean actualizarEstado(String cedula, String estado) {

        String sql = "UPDATE estudiantes SET estado_solicitud = ? WHERE cedula = ?";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setString(2, cedula);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

