package Administracao;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import Tratamento_de_Excessoes.ValidarEmailException;
import Tratamento_de_Excessoes.ValidarSenhaTipo1Exception;
import Tratamento_de_Excessoes.ValidarSenhaTipo2Exception;

public class GerenciarUsuarios {
    private static final String ARQUIVO = "usuarios.txt";
    private List<Usuario> usuarioList = new ArrayList<>();

    public GerenciarUsuarios() {
        carregar();
    }

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
            return;
        }
        usuarioList.add(usuario);
        salvar();
        System.out.println("Usuário cadastrado com sucesso!");
    }

    public Usuario login(String email, String senha) {
        for(Usuario u : usuarioList) {
            if(u.getEmail().equalsIgnoreCase(email) && u.getSenha().equals(senha)) {
                return u;
            }
        }

        return null;
    }

    // Busca um usuario pelo email (retorna null se nao existir)
    public Usuario buscarPorEmail(String email) {
        for (Usuario u : usuarioList) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    // Mostra todos os usuarios cadastrados
    public void listarUsuarios() {
        if (usuarioList.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado.");
            return;
        }
        for (Usuario u : usuarioList) {
            System.out.println("- " + u.getNome() + " | " + u.getEmail()
                    + " | " + u.getPerfil() + " | " + u.getStatus());
        }
    }

    // Ativa ou desativa um usuario pelo email. Retorna false se nao encontrar.
    public boolean alterarStatus(String email, boolean ativar) {
        Usuario u = buscarPorEmail(email);
        if (u == null) {
            return false;
        }
        if (ativar) {
            u.ativar();
        } else {
            u.desativar();
        }
        salvar();
        return true;
    }

    private void salvar() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for(Usuario u : usuarioList) {
                bw.write(u.getNome() + ";" + u.getEmail() + ";" + u.getSenha() + ";" + u.getPerfil() + ";" + u.getStatus());
                bw.newLine();
            }
        } catch(IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    private void carregar() {
        File f = new File(ARQUIVO);
        if(!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while((linha = br.readLine()) != null) {
                String[] p = linha.split(";");
                // 4 campos = arquivo antigo (sem status); 5 campos = com status
                if (p.length == 4 || p.length == 5) {
                    String nome = p[0], email = p[1], senha = p[2], perfil = p[3];
                    String status = (p.length == 5) ? p[4] : Usuario.ATIVO;
                    Usuario u = null;

                    try {
                        if ("Administrador".equalsIgnoreCase(perfil)) {
                            u = new Administrador(nome, email, senha, perfil);
                        } else if ("Analista".equalsIgnoreCase(perfil)) {
                            u = new Analista(nome, email, senha, perfil);
                        } else if ("Pesquisador".equalsIgnoreCase(perfil)) {
                            u = new Pesquisador(nome, email, senha, perfil);
                        }
                    } catch (ValidarEmailException | ValidarSenhaTipo1Exception | ValidarSenhaTipo2Exception e) {
                        System.out.println("Dados inválidos no arquivo, linha ignorada: " + email);
                    }

                    if (u != null) {
                        u.setStatus(status);
                        usuarioList.add(u);
                    }
                }
            }
        } catch(IOException e) {
            System.out.println("Erro ao carregar "+e.getMessage());
        }
    }
}