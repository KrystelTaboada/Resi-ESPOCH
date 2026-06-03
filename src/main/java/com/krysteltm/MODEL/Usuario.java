
package com.krysteltm.MODEL;

public class Usuario {
    private int id;
    private String usuario;
    private String password;
    private String rol;

    //Getters
    public int getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    
    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
    
}
