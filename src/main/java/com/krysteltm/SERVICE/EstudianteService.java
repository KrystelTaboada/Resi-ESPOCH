/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.krysteltm.SERVICE;

/**
 *
 * @author KRYSTEL
 */

import com.krysteltm.DAO.EstudianteDAO;
import com.krysteltm.MODEL.Estudiante;
import java.util.List;

public class EstudianteService {

    private EstudianteDAO estudianteDAO = new EstudianteDAO();

    public boolean registrarEstudiante(Estudiante e) {

        if (e.getCedula().isEmpty() ||
            e.getNombre().isEmpty() ||
            e.getApellido().isEmpty() ||
            e.getCorreo().isEmpty()) {
            return false;
        }

        if (e.getPromedio() < 0 || e.getPromedio() > 10) {
            return false;
        }

        return estudianteDAO.insertar(e);
    }

    public List<Estudiante> obtenerEstudiantes() {
        return estudianteDAO.listar();
    }

    public Estudiante buscarPorCorreo(String correo) {
        return estudianteDAO.buscarPorCorreo(correo);
    }
    
    public boolean actualizarEstadoSolicitud(String cedula, String estado) {
        return estudianteDAO.actualizarEstado(cedula, estado);
    }
    public int contarPorEstado(String estado) {
        int contador = 0;
        List<Estudiante> estudiantes = obtenerEstudiantes();

        for (Estudiante e : estudiantes) {
            if (e.getEstadoSolicitud().equalsIgnoreCase(estado)) {
                contador++;
            }
        }
        return contador;
    }
    
    public int obtenerCuposDisponibles(int totalCupos) {
        int enResidencia = contarPorEstado("APROBADA");
        return totalCupos - enResidencia;
    }

}

