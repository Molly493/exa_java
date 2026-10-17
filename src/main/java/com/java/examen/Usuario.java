package com.java.examen;

public class Usuario {
    private int id;
    private String nomUsuario;
    private String password;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;

    public Usuario() { }

    public Usuario(int id, String nomUsuario, String password, String nombres,
                   String apellidos, String direccion, String telefono) {
        this.id = id;
        this.nomUsuario = nomUsuario;
        this.password = password;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getId() { return id; }
    public String getNomUsuario() { return nomUsuario; }
    public String getPassword() { return password; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }

    @Override
    public String toString() {
        return "ID: " + id
                + " | Usuario: " + nomUsuario
                + " | Nombres: " + nombres
                + " | Apellidos: " + apellidos
                + " | Dirección: " + direccion
                + " | Teléfono: " + telefono;
    }
}
