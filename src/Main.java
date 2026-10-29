import Administracao.Usuario;
import java.util.Scanner;
import Tratamento_de_Excessoes.*;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        //criação do perfil do usuario - cadastro

        try {
            System.out.println("CADASTRAR NOVO USUÁRIO");
            System.out.println("Digite seu nome");
            String nome = entrada.nextLine();
            System.out.println("Administrador/Analista/Pesquisador?");
            String perfil = entrada.nextLine();

            //precisam ser incluidos em um while para o usuario digitar ate ser valido
            System.out.println("Digite seu email");
            String email = entrada.nextLine();
            System.out.println("Digite sua senha");
            String senha = entrada.nextLine();

            Usuario usuario =  new Usuario(nome,email,senha,perfil);

            System.out.println(usuario.getNome());
            System.out.println(usuario.getEmail());
            System.out.println(usuario.getSenha());
            System.out.println(usuario.getPerfil());
        } catch (ValidarEmailException|ValidarSenhaTipo1Exception|ValidarSenhaTipo2Exception excessao) {
            System.out.println("Erro: " + excessao.getMessage());
        }



        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
    }
}

