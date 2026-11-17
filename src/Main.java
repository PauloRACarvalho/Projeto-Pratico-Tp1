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
import Candidatos_Partidos.Candidato;
import Candidatos_Partidos.Partidos;

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
                    System.out.println("4 - Gerenciar Partidos e Candidatos");
                    System.out.println("5 - Cadastrar Usuario");
                    System.out.println("6 - Listar Usuarios");
                    System.out.println("7 - Ativar/Desativar Usuario");
                    System.out.println("8 - Logout (Voltar ao Menu Inicial)");
                } else if (logado instanceof Pesquisador) {
                    System.out.println("1 - Criar Nova Pesquisa");
                    System.out.println("2 - Realizar Entrevista");
                    System.out.println("3 - Gerar Relatorio Estatistico");
                    System.out.println("4 - Gerenciar Partidos e Candidatos");
                    System.out.println("5 - Logout (Voltar ao Menu Inicial)");
                } else {
                    // Analista pode consultar, mas nao alterar os dados eleitorais
                    System.out.println("1 - Gerar Relatorio Estatistico");
                    System.out.println("2 - Visualizar Partidos e Candidatos");
                    System.out.println("3 - Logout (Voltar ao Menu Inicial)");
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
                    gerenciarPartidosCandidatos(entrada, pesquisaAtual);
                } else if (menu == 5) {
                    cadastrarUsuario(entrada, gerenciarUsuarios);
                } else if (menu == 6) {
                    System.out.println("\n--- USUARIOS CADASTRADOS ---");
                    gerenciarUsuarios.listarUsuarios();
                } else if (menu == 7) {
                    alterarStatusUsuario(entrada, gerenciarUsuarios, logado);
                } else if (menu == 8) {
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
                    gerenciarPartidosCandidatos(entrada, pesquisaAtual);
                } else if (menu == 5) {
                    System.out.println("Logout realizado com sucesso.");
                    logado = null;
                } else {
                    System.out.println("Opcao invalida!");
                }
            } else {
                // Analista: consulta partidos e candidatos, sem permissao de alteracao
                if (menu == 1) {
                    gerarRelatorio(pesquisaAtual);
                } else if (menu == 2) {
                    visualizarPartidosCandidatos(pesquisaAtual);
                } else if (menu == 3) {
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

    // Menu de gerenciamento: exclusivo para Administrador e Pesquisador.
    public static void gerenciarPartidosCandidatos(Scanner entrada, Pesquisa pesquisa) {
        if (pesquisa == null) {
            System.out.println("Aviso: Crie ou selecione uma pesquisa antes de gerenciar partidos e candidatos.");
            return;
        }

        int opcao;
        do {
            System.out.println("\n--- GERENCIAR PARTIDOS E CANDIDATOS ---");
            System.out.println("Pesquisa: " + pesquisa.getCodigo());
            System.out.println("1 - Visualizar partidos e candidatos");
            System.out.println("2 - Cadastrar partido");
            System.out.println("3 - Cadastrar candidato");
            System.out.println("4 - Alterar situacao de partido");
            System.out.println("5 - Alterar situacao de candidato");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(entrada.nextLine());

            switch (opcao) {
                case 1:
                    visualizarPartidosCandidatos(pesquisa);
                    break;
                case 2:
                    cadastrarPartido(entrada, pesquisa);
                    break;
                case 3:
                    cadastrarCandidato(entrada, pesquisa);
                    break;
                case 4:
                    alterarSituacaoPartido(entrada, pesquisa);
                    break;
                case 5:
                    alterarSituacaoCandidato(entrada, pesquisa);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }

    // Consulta permitida aos tres perfis.
    public static void visualizarPartidosCandidatos(Pesquisa pesquisa) {
        if (pesquisa == null) {
            System.out.println("Aviso: Nenhuma pesquisa criada.");
            return;
        }

        System.out.println("\n--- PARTIDOS DA PESQUISA " + pesquisa.getCodigo() + " ---");
        if (pesquisa.getPartidos().isEmpty()) {
            System.out.println("Nenhum partido cadastrado.");
        } else {
            for (Partidos partido : pesquisa.getPartidos()) {
                System.out.println(partido + "\n--------------------");
            }
        }

        System.out.println("\n--- CANDIDATOS DA PESQUISA " + pesquisa.getCodigo() + " ---");
        if (pesquisa.getCandidatos().isEmpty()) {
            System.out.println("Nenhum candidato cadastrado.");
        } else {
            for (Candidato candidato : pesquisa.getCandidatos()) {
                System.out.println(candidato + "\n--------------------");
            }
        }
    }

    private static void cadastrarPartido(Scanner entrada, Pesquisa pesquisa) {
        System.out.println("\n--- CADASTRAR PARTIDO ---");
        System.out.print("Nome: ");
        String nome = entrada.nextLine();
        System.out.print("Sigla: ");
        String sigla = entrada.nextLine();
        System.out.print("Numero: ");
        int numero = Integer.parseInt(entrada.nextLine());

        for (Partidos existente : pesquisa.getPartidos()) {
            if (existente.getNumero() == numero || existente.getSigla().equalsIgnoreCase(sigla)) {
                System.out.println("Ja existe um partido com esse numero ou sigla nesta pesquisa.");
                return;
            }
        }
        pesquisa.adicionarPartido(new Partidos(nome, sigla, numero, "Ativo"));
        System.out.println("Partido cadastrado na pesquisa " + pesquisa.getCodigo() + "!");
    }

    private static void cadastrarCandidato(Scanner entrada, Pesquisa pesquisa) {
        System.out.println("\n--- CADASTRAR CANDIDATO ---");
        System.out.print("Nome: ");
        String nome = entrada.nextLine();
        System.out.print("Numero: ");
        int numero = Integer.parseInt(entrada.nextLine());
        System.out.print("Cargo: ");
        String cargo = entrada.nextLine();

        if (pesquisa.getPartidos().isEmpty()) {
            System.out.println("Cadastre um partido antes de cadastrar candidatos.");
            return;
        }
        System.out.println("Partidos disponiveis:");
        for (Partidos partido : pesquisa.getPartidos()) {
            System.out.println(partido.getSigla() + " - " + partido.getNome());
        }
        System.out.print("Sigla do partido do candidato: ");
        String sigla = entrada.nextLine();
        Partidos partidoEscolhido = null;
        for (Partidos partido : pesquisa.getPartidos()) {
            if (partido.getSigla().equalsIgnoreCase(sigla)) {
                partidoEscolhido = partido;
                break;
            }
        }
        if (partidoEscolhido == null) {
            System.out.println("Partido nao encontrado nesta pesquisa.");
            return;
        }
        for (Candidato existente : pesquisa.getCandidatos()) {
            if (existente.getNumero() == numero) {
                System.out.println("Ja existe um candidato com esse numero nesta pesquisa.");
                return;
            }
        }
        Candidato candidato = new Candidato(nome, numero, cargo, "Ativo");
        candidato.setPartido(partidoEscolhido);
        pesquisa.adicionarCandidato(candidato);
        System.out.println("Candidato cadastrado na pesquisa " + pesquisa.getCodigo() + "!");
    }

    private static void alterarSituacaoPartido(Scanner entrada, Pesquisa pesquisa) {
        if (pesquisa.getPartidos().isEmpty()) {
            System.out.println("Nao ha partidos cadastrados.");
            return;
        }
        for (Partidos partido : pesquisa.getPartidos()) {
            System.out.println(partido.getNumero() + " - " + partido.getSigla() + " (" + partido.getSituacao() + ")");
        }
        System.out.print("Numero do partido: ");
        int numero = Integer.parseInt(entrada.nextLine());
        Partidos selecionado = null;
        for (Partidos partido : pesquisa.getPartidos()) {
            if (partido.getNumero() == numero) { selecionado = partido; break; }
        }
        if (selecionado == null) {
            System.out.println("Partido nao encontrado.");
            return;
        }
        System.out.println("1 - Ativar\n2 - Desativar");
        System.out.print("Escolha: ");
        int acao = Integer.parseInt(entrada.nextLine());
        if (acao == 1) { selecionado.ativar(); }
        else if (acao == 2) { selecionado.desativar(); }
        else { System.out.println("Opcao invalida!"); return; }
        System.out.println("Situacao do partido atualizada.");
    }

    private static void alterarSituacaoCandidato(Scanner entrada, Pesquisa pesquisa) {
        if (pesquisa.getCandidatos().isEmpty()) {
            System.out.println("Nao ha candidatos cadastrados.");
            return;
        }
        for (Candidato candidato : pesquisa.getCandidatos()) {
            System.out.println(candidato.getNumero() + " - " + candidato.getNome() + " (" + candidato.getSituacao() + ")");
        }
        System.out.print("Numero do candidato: ");
        int numero = Integer.parseInt(entrada.nextLine());
        Candidato selecionado = null;
        for (Candidato candidato : pesquisa.getCandidatos()) {
            if (candidato.getNumero() == numero) { selecionado = candidato; break; }
        }
        if (selecionado == null) {
            System.out.println("Candidato nao encontrado.");
            return;
        }
        System.out.println("1 - Ativar\n2 - Desativar");
        System.out.print("Escolha: ");
        int acao = Integer.parseInt(entrada.nextLine());
        if (acao == 1) { selecionado.ativar(); }
        else if (acao == 2) { selecionado.desativar(); }
        else { System.out.println("Opcao invalida!"); return; }
        System.out.println("Situacao do candidato atualizada.");
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