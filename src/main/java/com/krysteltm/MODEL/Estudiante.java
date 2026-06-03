
package com.krysteltm.MODEL;


public class Estudiante {

    private int id;
    private String cedula;
    private String facultad;
    private String carrera;
    private String nombre;
    private String apellido;
    private String semestre;
    private double promedio;
    private String beca;
    private String estadoSolicitud;
    private String correo;
    private String tipoBeca;
    private static final int TOTAL_CUPOS = 500;
    public Estudiante() {
    }

    public Estudiante(String cedula, String facultad, String carrera,
                      String nombre, String apellido, String semestre,
                      double promedio, String beca, String estadoSolicitud,
                      String correo, String tipoBeca) {
        this.cedula = cedula;
        this.facultad = facultad;
        this.carrera = carrera;
        this.nombre = nombre;
        this.apellido = apellido;
        this.semestre = semestre;
        this.promedio = promedio;
        this.beca = beca;
        this.estadoSolicitud = estadoSolicitud;
        this.correo = correo;
        this.tipoBeca = tipoBeca;
    }
    public String getNombreCompleto() {
        return (nombre != null ? nombre : "") + " " +
           (apellido != null ? apellido : "");
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getFacultad() { return facultad; }
    public void setFacultad(String facultad) { this.facultad = facultad; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }

    public double getPromedio() { return promedio; }
    public void setPromedio(double promedio) { this.promedio = promedio; }

    public String getBeca() { return beca; }
    public void setBeca(String beca) { this.beca = beca; }

    public String getEstadoSolicitud() { return estadoSolicitud; }
    public void setEstadoSolicitud(String estadoSolicitud) { this.estadoSolicitud = estadoSolicitud; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getTipoBeca() { return tipoBeca; }
    public void setTipoBeca(String tipoBeca) {this.tipoBeca = tipoBeca;}
}

