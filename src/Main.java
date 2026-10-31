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
                try {
                    System.out.println("CADASTRAR NOVO USUÁRIO");
                    System.out.println("Digite seu nome");
                    String nome = entrada.nextLine();

                    System.out.println("Administrador/Analista/Pesquisador?");
                    String perfil = entrada.nextLine();

                    System.out.println("Digite seu email:");
                    String email = entrada.nextLine();
                    Usuario.validarEmail(email);

                    System.out.println("Digite sua senha:");
                    String senha = entrada.nextLine();
                    Usuario.validarSenha(senha);

                    Usuario usuario;
                    if ("Administrador".equals(perfil)) {
                        usuario = new Administrador(nome, email, senha, perfil);
                    } else if ("Analista".equals(perfil)) {
                        usuario = new Analista(nome, email, senha, perfil);
                    } else if ("Pesquisador".equals(perfil)) {
                        usuario = new Pesquisador(nome, email, senha, perfil);
                    }else {
                        System.out.println("Erro: perfil inválido.");
                        return;
                    }

                    gerenciarUsuarios.adicionarUsuario(usuario);

                    System.out.println(usuario);

                } catch (ValidarEmailException | ValidarSenhaTipo1Exception | ValidarSenhaTipo2Exception excessao) {
                    System.out.println("Erro: " + excessao.getMessage());
                }
            } else if(menu == 2) {
                System.out.println("Digite seu email:");
                String email = entrada.nextLine();


                System.out.println("Digite sua senha");
                String senha = entrada.nextLine();



            }

        }


    }
}