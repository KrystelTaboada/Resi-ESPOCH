
package com.krysteltm.SERVICE;

import com.krysteltm.DAO.UsuarioDAO;
import com.krysteltm.MODEL.Usuario;


public class LoginService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario autenticar(String usuario, String password) {

        if (usuario.isEmpty() || password.isEmpty()) {
            return null;
        }

        return usuarioDAO.login(usuario, password);
    }
}
