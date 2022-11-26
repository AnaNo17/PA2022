package com.example.splashnoemi.Json;

import java.io.Serializable;

public class MyMessage implements Serializable {
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    private String correo;
    private String mensaje;
}
