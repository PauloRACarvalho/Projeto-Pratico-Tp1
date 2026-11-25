package Administracao;
//Interface usada
import java.util.List;
import java.util.ArrayList;
//pacote para escrever em arquivos
import java.io.*;
import Tratamento_de_Excessoes.ValidarEmailException;
import Tratamento_de_Excessoes.ValidarSenhaTipo1Exception;
import Tratamento_de_Excessoes.ValidarSenhaTipo2Exception;

public class GerenciarUsuarios {
    private static final String ARQUIVO = "usuarios.txt"; //final: variavel ARQUIVO não pode ser redefinida, static: pertence à classe
    private List<Usuario> usuarioList = new ArrayList<>();

    public GerenciarUsuarios() {

        carregar(); //busca os usuarios existentes no txt
    } //é instanciado na Main

    public boolean emailExists(String email) {
        for (Usuario u : usuarioList) {
            if(email.equalsIgnoreCase(u.getEmail())) {
                return true;
            }
        }

        return false;
    }

    public void adicionarUsuario(Usuario usuario) {
        if(emailExists(usuario.getEmail())) { //verificando se o usuario existe pelo email
            System.out.println("Usuário já cadastrado.");
            return;
        }
        usuarioList.add(usuario);
        salvar(); //salva no txt
        System.out.println("Usuário cadastrado com sucesso!");
    }

    public Usuario login(String email, String senha) { //instanciado em TelaLogin
        for(Usuario u : usuarioList) {
            if(u.getEmail().equalsIgnoreCase(email) && u.getSenha().equals(senha)) {
                return u;
            }
        }

        return null;
    }

    // Busca um usuario pelo email para ativar ou desativar depois
    public Usuario buscarPorEmail(String email) {
        for (Usuario u : usuarioList) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    // Retorna uma cópia da lista para consulta pela interface gráfica.
    public List<Usuario> getUsuarios() {

        return new ArrayList<>(usuarioList);
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

    // Ativa ou desativa um usuario pelo email
    public boolean alterarStatus(String email, boolean ativar) {
        Usuario u = buscarPorEmail(email);
        if (u == null) {
            return false;
        }
        if (ativar) {
            u.ativar(); //ta na classe Usuário
        } else {
            u.desativar(); //ta na classe Usuário
        }
        salvar(); //salva no arquivo
        return true;
    }

    private void salvar() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for(Usuario u : usuarioList) { //para cada usuário, escreve as infos no arquivox
                bw.write(u.getNome() + ";" + u.getEmail() + ";" + u.getSenha() + ";" + u.getPerfil() + ";" + u.getStatus());
                bw.newLine();
            }
        } catch(IOException e) { //IOException: problemas para escrever no arquivo
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    private void carregar() {
        File f = new File(ARQUIVO); //usuarios.txt
        if(!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) { //BufferedReader: ler do arquivo :)
            String linha;
            while((linha = br.readLine()) != null) { //enquanto houverem linhas preenchidas
                String[] p = linha.split(";"); //vai pegar os dados, usando ; como critério
                // 4 campos = arquivo antigo (sem status); 5 campos = com status
                if (p.length == 4 || p.length == 5) {
                    String nome = p[0], email = p[1], senha = p[2], perfil = p[3];
                    String status = (p.length == 5) ? p[4] : Usuario.ATIVO; //explicar que na primeira versão eu nao estava considerando o status ativo ou inativo
                    Usuario u = null;

                    try { //cria o perfil de acordo com a função :)
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

                    if (u != null) { //em caso de se ocorresse um perfil com função diferente - isso era usado antes da interface, quando o usuario podia digitar sua função
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