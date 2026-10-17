package com.java.examen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    /** Lee todos los registros de la tabla usuario. */
    public List<Usuario> listar() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nomUsuario, password, nombres, apellidos, direccion, telefono "
                + " FROM usuario ORDER BY id";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nomUsuario"),
                        rs.getString("password"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("direccion"),
                        rs.getString("telefono")
                ));
            }
        }
        return lista;
    }

    /** Inserta un nuevo usuario. Devuelve true si se insertó. */
    public boolean insertar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuario (nomUsuario, password, nombres, apellidos, direccion, telefono) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNomUsuario());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getNombres());
            ps.setString(4, u.getApellidos());
            ps.setString(5, u.getDireccion());
            ps.setString(6, u.getTelefono());
            return ps.executeUpdate() > 0;
        }
    }

    /** Elimina el usuario con el id indicado. Devuelve true si existía y se borró. */
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}

