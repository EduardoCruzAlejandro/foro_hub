package com.forohub.ForoHub.models;

public class Login {
    private String correoElectronico;
    private String contrasena;

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setPassword(String password) {
        this.contrasena= password;
    }
}
