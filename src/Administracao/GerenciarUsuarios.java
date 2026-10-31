package Administracao;
import java.util.List;
import java.util.ArrayList;

public class GerenciarUsuarios {
    private List<Usuario> usuarioList = new ArrayList<>();

    public boolean emailExists(String email) {
        for (Usuario u : usuarioList) {
            if(email.equalsIgnoreCase(u.getEmail())) {
                return true;
            }
        }

        return false;
    }

    public void adicionarUsuario(Usuario usuario) {
        if(emailExists(usuario.getEmail())) {
            System.out.println("Usuário já cadastrado.");
        }
        usuarioList.add(usuario);
        System.out.println("Usuário cadastrado com sucesso!");
    }
}