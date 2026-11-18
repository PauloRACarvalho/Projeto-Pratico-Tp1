package view;

import Administracao.Administrador;
import Administracao.Analista;
import Administracao.GerenciarUsuarios;
import Administracao.Pesquisador;
import Administracao.Usuario;
import Pesquisas.Pesquisa;
import Pesquisas.SituacaoPesquisa;

import javax.swing.*;
import java.awt.*;

/** Tela principal após a autenticação. */
public class TelaPrincipal extends JFrame {
    private static Pesquisa pesquisaAtual;
    private final Usuario usuario;
    private final GerenciarUsuarios gerenciarUsuarios;
    private final JLabel lblPesquisa = new JLabel();

    public TelaPrincipal(Usuario usuario, GerenciarUsuarios gerenciarUsuarios) {
        this.usuario = usuario;
        this.gerenciarUsuarios = gerenciarUsuarios;
        configurarTela();
    }

    private void configurarTela() {
        setTitle("Sistema de Pesquisa Eleitoral 2026");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(620, 390);
        setLocationRelativeTo(null);

        JPanel principal = new JPanel(new BorderLayout(12, 12));
        principal.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        JLabel titulo = new JLabel("Sistema de Pesquisa Eleitoral", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        JLabel saudacao = new JLabel("Olá, " + usuario.getNome() + " — " + usuario.getPerfil(), SwingConstants.CENTER);
        saudacao.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel cabecalho = new JPanel(new GridLayout(2, 1, 4, 4));
        cabecalho.add(titulo);
        cabecalho.add(saudacao);

        JPanel centro = new JPanel(new GridLayout(0, 1, 10, 10));
        JButton btnPesquisa = new JButton("Criar / selecionar pesquisa");
        JButton btnPartidos = new JButton("Partidos e candidatos");
        JButton btnSair = new JButton("Sair / trocar usuário");
        btnPesquisa.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnPartidos.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnSair.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        boolean podeCriarPesquisa = usuario instanceof Administrador || usuario instanceof Pesquisador;
        btnPesquisa.setEnabled(podeCriarPesquisa);
        btnPesquisa.addActionListener(e -> criarOuSelecionarPesquisa());
        btnPartidos.addActionListener(e -> abrirPartidosCandidatos());
        btnSair.addActionListener(e -> voltarLogin());

        centro.add(btnPesquisa);
        centro.add(btnPartidos);
        centro.add(btnSair);

        lblPesquisa.setHorizontalAlignment(SwingConstants.CENTER);
        lblPesquisa.setText(textoPesquisa());

        principal.add(cabecalho, BorderLayout.NORTH);
        principal.add(centro, BorderLayout.CENTER);
        principal.add(lblPesquisa, BorderLayout.SOUTH);
        setContentPane(principal);
    }

    private String textoPesquisa() {
        return pesquisaAtual == null
                ? "Nenhuma pesquisa selecionada."
                : "Pesquisa atual: " + pesquisaAtual.getCodigo() + " — " + pesquisaAtual.getCargo();
    }

    private void criarOuSelecionarPesquisa() {
        if (pesquisaAtual != null) {
            int opcao = JOptionPane.showConfirmDialog(this,
                    "Já existe uma pesquisa nesta execução:\n" + pesquisaAtual.getCodigo()
                            + " — " + pesquisaAtual.getCargo() + "\n\nDeseja criar outra pesquisa?",
                    "Pesquisa atual", JOptionPane.YES_NO_OPTION);
            if (opcao != JOptionPane.YES_OPTION) return;
        }

        JTextField codigo = new JTextField();
        JTextField cargo = new JTextField();
        JTextField periodo = new JTextField("Nov/2026");
        JTextField quantidade = new JTextField("1000");
        JTextField metodologia = new JTextField("Quantitativa");
        Object[] campos = {
                "Código da pesquisa:", codigo,
                "Cargo disputado:", cargo,
                "Período de coleta:", periodo,
                "Quantidade de entrevistados:", quantidade,
                "Metodologia:", metodologia
        };

        int resultado = JOptionPane.showConfirmDialog(this, campos, "Criar pesquisa",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado != JOptionPane.OK_OPTION) return;

        if (codigo.getText().isBlank() || cargo.getText().isBlank()
                || periodo.getText().isBlank() || metodologia.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int qtd = Integer.parseInt(quantidade.getText().trim());
            if (qtd <= 0) throw new NumberFormatException();
            pesquisaAtual = new Pesquisa(codigo.getText().trim(), cargo.getText().trim(),
                    periodo.getText().trim(), qtd, metodologia.getText().trim());
            pesquisaAtual.setSituacao(new SituacaoPesquisa(1, "Em Andamento"));
            lblPesquisa.setText(textoPesquisa());
            JOptionPane.showMessageDialog(this, "Pesquisa criada com sucesso.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "A quantidade de entrevistados deve ser um número inteiro positivo.",
                    "Valor inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirPartidosCandidatos() {
        if (pesquisaAtual == null) {
            JOptionPane.showMessageDialog(this,
                    "Nenhuma pesquisa foi criada nesta execução. Peça a um administrador ou pesquisador para criar uma pesquisa.",
                    "Pesquisa não encontrada", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        boolean podeEditar = usuario instanceof Administrador || usuario instanceof Pesquisador;
        new TelaPartidosCandidatos(this, pesquisaAtual, podeEditar).setVisible(true);
    }

    private void voltarLogin() {
        int opcao = JOptionPane.showConfirmDialog(this, "Deseja encerrar esta sessão?",
                "Logout", JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
            new TelaLogin(gerenciarUsuarios).setVisible(true);
            dispose();
        }
    }
}
