import Administracao.GerenciarUsuarios;
import Pesquisas.ArquivoPesquisas;
import view.TelaLogin;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** Ponto de entrada do sistema: inicia  a interface gráfica. */
public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
        SwingUtilities.invokeLater(() -> {
=======
        Scanner entrada = new Scanner(System.in);
        GerenciarUsuarios gerenciarUsuarios = new GerenciarUsuarios();
        boolean isExecutando = true;
        int menu;

        Usuario logado = null;
        Pesquisa pesquisaAtual = null;

        System.out.println("========================================");
        System.out.println("  SISTEMA DE PESQUISA ELEITORAL 2026");
        System.out.println("========================================");

        // Loop principal do sistema
        while(isExecutando) {
            System.out.println("\n----------------------------------------");

            // Exibe menu diferente conforme o usuario esteja logado e seu perfil
            if (logado == null) {
                System.out.println("--- MENU INICIAL ---");
                System.out.println("1 - Cadastro");
                System.out.println("2 - Login");
                System.out.println("3 - Sair do Sistema");
            } else {
                System.out.println("--- MENU DO USUARIO: " + logado.getNome() + " ---");
                if (logado instanceof Administrador) {
                    System.out.println("1 - Criar Pesquisa");
                    System.out.println("2 - Realizar Entrevista");
                    System.out.println("3 - Gerar Relatorio");
                    System.out.println("4 - Cadastrar Usuario");
                    System.out.println("5 - Listar Usuarios");
                    System.out.println("6 - Ativar/Desativar Usuario");
                    System.out.println("7 - Logout");
                } else if (logado instanceof Pesquisador) {
                    System.out.println("1 - Criar Pesquisa");
                    System.out.println("2 - Realizar Entrevista");
                    System.out.println("3 - Gerar Relatorio");
                    System.out.println("4 - Logout");
                } else {
                    System.out.println("1 - Gerar Relatorio Estatistico");
                    System.out.println("2 - Logout");
                }
            }
            System.out.print("Escolha a opcao desejada: ");

            menu = entrada.nextInt();
            entrada.nextLine(); // Limpar buffer do scanner

            // Menu para usuário não logado
            if (logado == null) {
                if(menu == 1) {
                    cadastrarUsuario(entrada, gerenciarUsuarios);
                } else if(menu == 2) {
                    logado = fazerLogin(entrada, gerenciarUsuarios);
                } else if (menu == 3) {
                    System.out.println("Saindo do sistema. Ate logo!");
                    isExecutando = false;
                } else {
                    System.out.println("Opcao invalida!");
                }
            } else if (logado instanceof Administrador) {
                if (menu == 1) {
                    pesquisaAtual = criarPesquisa(entrada, logado);
                } else if (menu == 2) {
                    realizarEntrevista(entrada, pesquisaAtual, logado);
                } else if (menu == 3) {
                    gerarRelatorio(pesquisaAtual);
                } else if (menu == 4) {
                    cadastrarUsuario(entrada, gerenciarUsuarios);
                } else if (menu == 5) {
                    System.out.println("\n--- USUARIOS CADASTRADOS ---");
                    gerenciarUsuarios.listarUsuarios();
                } else if (menu == 6) {
                    alterarStatusUsuario(entrada, gerenciarUsuarios, logado);
                } else if (menu == 7) {
                    System.out.println("Logout realizado com sucesso.");
                    logado = null;
                } else {
                    System.out.println("Opcao invalida!");
                }
            } else if (logado instanceof Pesquisador) {
                if (menu == 1) {
                    pesquisaAtual = criarPesquisa(entrada, logado);
                } else if (menu == 2) {
                    realizarEntrevista(entrada, pesquisaAtual, logado);
                } else if (menu == 3) {
                    gerarRelatorio(pesquisaAtual);
                } else if (menu == 4) {
                    System.out.println("Logout realizado com sucesso.");
                    logado = null;
                } else {
                    System.out.println("Opcao invalida!");
                }
            } else {
                // Analista: acesso restrito à geração de relatório estatístico
                if (menu == 1) {
                    gerarRelatorio(pesquisaAtual);
                } else if (menu == 2) {
                    System.out.println("Logout realizado com sucesso.");
                    logado = null;
                } else {
                    System.out.println("Opcao invalida!");
                }
            }
        }
        entrada.close();
    }

    // Realiza o cadastro de novo usuário com validações
    public static void cadastrarUsuario(Scanner entrada, GerenciarUsuarios gerenciarUsuarios) {
        System.out.println("\n--- CADASTRAR NOVO USUARIO ---");
        System.out.print("Digite seu nome: ");
        String nome = entrada.nextLine();

        // Valida o perfil do usuário
        String perfil;
        while(true) {
            System.out.print("Perfil (Administrador/Analista/Pesquisador): ");
            perfil = entrada.nextLine().trim();
            if ("Administrador".equalsIgnoreCase(perfil) ||
                    "Analista".equalsIgnoreCase(perfil) ||
                    "Pesquisador".equalsIgnoreCase(perfil)) {
                break;
            }
            System.out.println("Erro: perfil invalido. Tente novamente.");
        }

        // Valida o email com verificação de formato
        String email;
        while(true) {
            System.out.print("Digite seu email (ex: user@gmail.com): ");
            email = entrada.nextLine();
>>>>>>> origin/main
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                System.err.println("Não foi possível aplicar o tema Nimbus: " + e.getMessage());
            }

            GerenciarUsuarios gerenciarUsuarios = new GerenciarUsuarios();
            new TelaLogin(gerenciarUsuarios, ArquivoPesquisas.carregar()).setVisible(true);
        });
    }
}
