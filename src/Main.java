import Administracao.Administrador;
import Administracao.Analista;
import Administracao.Pesquisador;
import Administracao.Usuario;
import java.util.Scanner;
import Tratamento_de_Excessoes.*;
import Administracao.GerenciarUsuarios;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        GerenciarUsuarios gerenciarUsuarios = new GerenciarUsuarios();
        boolean isExecutando = true;
        int menu;
        Usuario logado = null;

        while(isExecutando) {
            //menu
            System.out.println("Escolha a opção desejada:");
            System.out.println("1 - Cadastro");
            System.out.println("2 - Login");
            System.out.println("3 - Logout");

            //usuario escolhe
            menu = entrada.nextInt();
            entrada.nextLine();

            if(menu == 1) {
                cadastrarUsuario(entrada, gerenciarUsuarios);
            } else if(menu == 2) {
                logado = fazerLogin(entrada, gerenciarUsuarios);
            } else if (menu == 3) {
                if(logado != null) {
                    System.out.println("Logout realizado. Até logo!");
                    logado = null;
                }

                isExecutando = false;
            } else {
                System.out.println("Opção inválida!");
            }

        }

        entrada.close();


    }

    public static void cadastrarUsuario(Scanner entrada, GerenciarUsuarios gerenciarUsuarios) {
        System.out.println("CADASTRAR NOVO USUÁRIO");
        System.out.println("Digite seu nome");
        String nome = entrada.nextLine();

        String perfil;
        while(true) {
            System.out.println("Administrador/Analista/Pesquisador?");
            perfil = entrada.nextLine().trim();
            if ("Administrador".equalsIgnoreCase(perfil) || "Analista".equalsIgnoreCase(perfil) || "Pesquisador".equalsIgnoreCase(perfil)) {
                break;
            }
            System.out.println("Erro: perfil inválido. Tente novamente.");
        }

        String email;
        while(true) {
            System.out.println("Digite seu email:");
            email = entrada.nextLine();

            try {
                Usuario.validarEmail(email);
                break;
            } catch (ValidarEmailException e) {
                System.out.println("Erro: "+ e.getMessage());
            }

        }

        String senha;
        while(true) {
            System.out.println("Digite sua senha:");
            senha = entrada.nextLine();

            try {
                Usuario.validarSenha(senha);
                break;
            } catch(ValidarSenhaTipo1Exception | ValidarSenhaTipo2Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }


        Usuario usuario;
        try {
            if ("Administrador".equals(perfil)) {
                usuario = new Administrador(nome, email, senha, perfil);
            } else if ("Analista".equals(perfil)) {
                usuario = new Analista(nome, email, senha, perfil);
            } else {
                usuario = new Pesquisador(nome, email, senha, perfil);
            }
        } catch (ValidarEmailException | ValidarSenhaTipo1Exception | ValidarSenhaTipo2Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        gerenciarUsuarios.adicionarUsuario(usuario);


    }

    public static Usuario fazerLogin(Scanner entrada, GerenciarUsuarios gerenciarUsuarios) {
        System.out.println("Digite seu email:");
        String email = entrada.nextLine().trim();
        System.out.println("Digite sua senha:");
        String senha = entrada.nextLine();

        Usuario usuario = gerenciarUsuarios.login(email,senha);
        if(usuario != null) {
            System.out.println("Bem vindo, " + usuario.getNome()+ "!");
        } else {
            System.out.println("Email ou senha incorretos.");
        }

        return usuario;

    }
}


