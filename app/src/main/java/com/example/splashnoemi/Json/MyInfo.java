package com.example.splashnoemi.Json;
import java.io.Serializable;
import java.util.List;

public class MyInfo implements Serializable {
    private String usuario;
    private String password;
    private String correo;
    private String edad;
    private String sexo;
    private String Tusu;
    private String hijos;
    private String Telefono;
    private String FechaNac;
    private List<MyData> contras;

    public List<MyData> getContras() {
        return contras;
    }

    public void setContras(List<MyData> contras) {
        this.contras = contras;
    }

    public MyInfo() {
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getCorreo() {
        return correo;
    }
    public void setedad(String edad) {
        this.edad = edad;
    }
    public String getedad() {
        return edad;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTusu() {
        return Tusu;
    }

    public void setTusu(String tusu) {
        Tusu = tusu;
    }

    public String getHijos() {
        return hijos;
    }

    public void setHijos(String hijos) {
        this.hijos = hijos;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
    public String getFechaNac() {
        return FechaNac;
    }

    public void setFechaNac(String fechaNac) {
        FechaNac = fechaNac;
    }


}
