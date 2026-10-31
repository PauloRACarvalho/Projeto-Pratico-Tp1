package Administracao;

import Tratamento_de_Excessoes.ValidarEmailException;
import Tratamento_de_Excessoes.ValidarSenhaTipo1Exception;
import Tratamento_de_Excessoes.ValidarSenhaTipo2Exception;

public class Administrador extends Usuario {

    public Administrador(String nome, String email, String senha, String perfil)
            throws ValidarEmailException, ValidarSenhaTipo1Exception, ValidarSenhaTipo2Exception {
        super(nome, email, senha, perfil);
    }

    @Override
    public String toString() {
        return "nome: " + nome + " email: " + email + " perfil: " + perfil;
    }
}

//    // 🔑 MÉTODOS DO DIAGRAMA (com lógica real)
//
//    public void cadastrarUsuario(Usuario usuario) {
//        // Validação: não permite cadastrar usuário sem nome ou email
//        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
//            System.out.println("ERRO: Usuário precisa ter um nome válido!");
//            return;
//        }
//        if (usuario.getEmail() == null || !usuario.validarEmail()) {
//            System.out.println("ERRO: Email inválido!");
//            return;
//        }
//        if (usuario.getSenha() == null || !usuario.validarSenha2()) {
//            System.out.println("ERRO: Senha inválida! Precisa de maiúscula e caractere especial.");
//            return;
//        }
//
//        // Adiciona na lista REAL
//        this.usuariosGerenciados.add(usuario);
//        System.out.println("SUCESSO: Usuário " + usuario.getNome() +
//                " cadastrado pelo administrador " + getNome());
//    }
//
//    public void editarUsuario(Usuario usuarioNovo, Usuario usuarioAntigo) {
//        // Procura o usuário antigo na lista
//        int indice = this.usuariosGerenciados.indexOf(usuarioAntigo);
//
//        if (indice != -1) {
//            // Substitui pelo novo
//            this.usuariosGerenciados.set(indice, usuarioNovo);
//            System.out.println("SUCESSO: Usuário " + usuarioAntigo.getNome() +
//                    " substituído por " + usuarioNovo.getNome());
//        } else {
//            System.out.println("ERRO: Usuário antigo não encontrado na lista!");
//        }
//    }
//
//    public void excluirUsuario(Usuario usuario) {
//        // Remove da lista REAL
//        boolean removido = this.usuariosGerenciados.remove(usuario);
//
//        if (removido) {
//            System.out.println("SUCESSO: Usuário " + usuario.getNome() +
//                    " excluído pelo administrador " + getNome());
//        } else {
//            System.out.println("ERRO: Usuário não encontrado na lista!");
//        }
//    }
//
//    // 🔑 MÉTODO EXTRA ÚTIL: Listar todos os usuários gerenciados
//    public void listarUsuarios() {
//        System.out.println("=== Usuários gerenciados por " + getNome() + " ===");
//        if (usuariosGerenciados.isEmpty()) {
//            System.out.println("Nenhum usuário cadastrado.");
//        } else {
//            for (Usuario u : usuariosGerenciados) {
//                System.out.println("- " + u.getNome() + " (" + u.getEmail() +
//                        ") - Perfil: " + u.getPerfil() +
//                        " - Status: " + u.getStatus());
//            }
//        }
//    }
//
//    // Getter da lista (caso precise acessar de fora)
//    public List<Usuario> getUsuariosGerenciados() {
//        return usuariosGerenciados;
//    }

