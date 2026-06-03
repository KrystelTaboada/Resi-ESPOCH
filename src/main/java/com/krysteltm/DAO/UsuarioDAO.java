
package com.krysteltm.DAO;

import com.krysteltm.UTIL.ConexionSQLite;
import com.krysteltm.MODEL.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public Usuario login(String usuario, String password) {

        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND password = ?";

        try (Connection conn = ConexionSQLite.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setUsuario(rs.getString("usuario"));
                u.setRol(rs.getString("rol"));
                return u;
            }

        } catch (Exception e) {
            System.out.println("Error login DAO: " + e.getMessage());
        }

        return null;
    }
}
