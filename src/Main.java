import Administracao.Administrador;
import Administracao.Analista;
import Administracao.Pesquisador;
import Administracao.Usuario;
import Administracao.GerenciarUsuarios;
import java.util.Scanner;
import Tratamento_de_Excessoes.*;

import Pesquisas.Pesquisa;
import Pesquisas.Pergunta;
import Pesquisas.OpcaoResposta;
import Entrevista.Entrevista;
import Entrevista.Entrevistado;
import Entrevista.Entrevistador;
import Entrevista.Resposta;
import Pesquisas.Estatistica;
import Pesquisas.SituacaoPesquisa;

public class Main {
    public static void main(String[] args) {
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
                    System.out.println("1 - Criar Nova Pesquisa");
                    System.out.println("2 - Realizar Entrevista");
                    System.out.println("3 - Gerar Relatorio Estatistico");
                    System.out.println("4 - Cadastrar Usuario");
                    System.out.println("5 - Listar Usuarios");
                    System.out.println("6 - Ativar/Desativar Usuario");
                    System.out.println("7 - Logout (Voltar ao Menu Inicial)");
                } else if (logado instanceof Pesquisador) {
                    System.out.println("1 - Criar Nova Pesquisa");
                    System.out.println("2 - Realizar Entrevista");
                    System.out.println("3 - Gerar Relatorio Estatistico");
                    System.out.println("4 - Logout (Voltar ao Menu Inicial)");
                } else {
                    // Analista: apenas estatisticas
                    System.out.println("1 - Gerar Relatorio Estatistico");
                    System.out.println("2 - Logout (Voltar ao Menu Inicial)");
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
                // Administrador: pesquisa + gestao de usuarios
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
                // Pesquisador
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
                // Analista
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
            try {
                Usuario.validarEmail(email);
                break;
            } catch (ValidarEmailException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        // Valida a senha com regras de segurança
        String senha;
        while(true) {
            System.out.print("Digite sua senha (maiúscula + caractere especial): ");
            senha = entrada.nextLine();
            try {
                Usuario.validarSenha(senha);
                break;
            } catch(ValidarSenhaTipo1Exception | ValidarSenhaTipo2Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        // Cria o objeto usuário conforme o perfil
        try {
            Usuario usuario;
            if ("Administrador".equalsIgnoreCase(perfil)) {
                usuario = new Administrador(nome, email, senha, perfil);
            } else if ("Analista".equalsIgnoreCase(perfil)) {
                usuario = new Analista(nome, email, senha, perfil);
            } else {
                usuario = new Pesquisador(nome, email, senha, perfil);
            }
            gerenciarUsuarios.adicionarUsuario(usuario);
        } catch (Exception e) {
            System.out.println("Erro ao criar usuario: " + e.getMessage());
        }
    }

    // Realiza o login verificando email, senha e se o usuario esta ativo
    public static Usuario fazerLogin(Scanner entrada, GerenciarUsuarios gerenciarUsuarios) {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Digite seu email: ");
        String email = entrada.nextLine().trim();
        System.out.print("Digite sua senha: ");
        String senha = entrada.nextLine();

        Usuario usuario = gerenciarUsuarios.login(email, senha);
        if (usuario == null) {
            System.out.println("Email ou senha incorretos.");
            return null;
        }
        if (!usuario.isAtivo()) {
            System.out.println("Usuario desativado. Procure um administrador.");
            return null;
        }
        System.out.println("Bem-vindo, " + usuario.getNome() + "!");
        return usuario;
    }

    // Administrador ativa ou desativa um usuario pelo email
    public static void alterarStatusUsuario(Scanner entrada, GerenciarUsuarios gerenciarUsuarios, Usuario logado) {
        System.out.println("\n--- ATIVAR/DESATIVAR USUARIO ---");
        gerenciarUsuarios.listarUsuarios();
        System.out.print("\nEmail do usuario: ");
        String email = entrada.nextLine().trim();

        Usuario alvo = gerenciarUsuarios.buscarPorEmail(email);
        if (alvo == null) {
            System.out.println("Usuario nao encontrado.");
            return;
        }

        System.out.println("1 - Ativar");
        System.out.println("2 - Desativar");
        System.out.print("Escolha: ");
        int opcao = entrada.nextInt();
        entrada.nextLine(); // Limpar buffer

        if (opcao == 1) {
            gerenciarUsuarios.alterarStatus(email, true);
            System.out.println("Usuario " + alvo.getNome() + " ativado.");
        } else if (opcao == 2) {
            // Impede o administrador de desativar a si mesmo
            if (alvo.getEmail().equalsIgnoreCase(logado.getEmail())) {
                System.out.println("Voce nao pode desativar a propria conta.");
                return;
            }
            gerenciarUsuarios.alterarStatus(email, false);
            System.out.println("Usuario " + alvo.getNome() + " desativado.");
        } else {
            System.out.println("Opcao invalida!");
        }
    }

    // Cria nova pesquisa com pergunta e opções padrão
    public static Pesquisa criarPesquisa(Scanner entrada, Usuario criador) {
        System.out.println("\n--- CRIAR NOVA PESQUISA ---");
        System.out.print("Codigo da Pesquisa (ex: PESQ-01): ");
        String codigo = entrada.nextLine();
        System.out.print("Cargo disputado (ex: Prefeito): ");
        String cargo = entrada.nextLine();

        Pesquisa novaPesquisa = new Pesquisa(codigo, cargo, "Nov/2026", 1000, "Quantitativa");
        novaPesquisa.setSituacao(new SituacaoPesquisa(1, "Em Andamento"));

        // Cria pergunta padrão sobre intenção de voto
        Pergunta p1 = new Pergunta(1, "Em qual candidato você votaria?");
        p1.adicionarOpcao(new OpcaoResposta(1, "Candidato A"));
        p1.adicionarOpcao(new OpcaoResposta(2, "Candidato B"));
        p1.adicionarOpcao(new OpcaoResposta(3, "Nulo/Branco"));

        novaPesquisa.adicionarPergunta(p1);
        System.out.println("Pesquisa '" + codigo + "' criada com sucesso por " + criador.getNome() + "!");
        return novaPesquisa;
    }

    // Realiza entrevista simulando resposta de um cidadão
    public static void realizarEntrevista(Scanner entrada, Pesquisa pesquisa, Usuario logado) {
        if (pesquisa == null) {
            System.out.println("Aviso: Crie uma pesquisa primeiro (Opcao 1 do menu).");
            return;
        }

        System.out.println("\n--- REALIZAR ENTREVISTA ---");
        System.out.print("Idade do entrevistado: ");
        int idade = entrada.nextInt();
        entrada.nextLine(); // Limpar buffer
        System.out.print("Classe Social (A, B, C, D): ");
        String classe = entrada.nextLine();

        Entrevistado entrevistado = new Entrevistado(999, idade, "Ensino Medio", "Indefinido", classe);
        Entrevista novaEntrevista = new Entrevista(1001, "Concluida");

        Entrevistador entrevistador = new Entrevistador(1, logado.getNome());
        novaEntrevista.setEntrevistado(entrevistado);
        novaEntrevista.setEntrevistador(entrevistador);

        // Simula resposta do cidadão (opção 1)
        Resposta resp = new Resposta(5001, "1");
        novaEntrevista.adicionarResposta(resp);

        System.out.println("Entrevista registrada com sucesso para cidadão da Classe " + classe + "!");
    }

    // Gera relatório estatístico da pesquisa
    public static void gerarRelatorio(Pesquisa pesquisa) {
        if (pesquisa == null) {
            System.out.println("Aviso: Nenhuma pesquisa criada para gerar relatorio.");
            return;
        }
        System.out.println("\n--- GERANDO RELATORIO ---");
        Estatistica estatistica = new Estatistica(pesquisa);
        estatistica.getRelatorio();
    }
}