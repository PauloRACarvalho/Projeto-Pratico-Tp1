package view;

import Administracao.Administrador;
import Administracao.Pesquisador;
import Administracao.Usuario;
import Pesquisas.ArquivoPesquisas;
import Pesquisas.OpcaoResposta;
import Pesquisas.Pergunta;
import Pesquisas.Pesquisa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/** Cadastro e consulta de perguntas e opções vinculadas a uma pesquisa. */
public class TelaPerguntas extends JFrame {
    private final List<Pesquisa> pesquisas;
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Identificação", "Enunciado", "Opções"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tabela = new JTable(modelo);
    private final JComboBox<Pesquisa> seletor;

    public TelaPerguntas(JFrame pai, Usuario usuario, List<Pesquisa> pesquisas) {
        if (!(usuario instanceof Administrador) && !(usuario instanceof Pesquisador)) {
            throw new SecurityException("Somente administradores e pesquisadores podem gerenciar perguntas.");
        }
        this.pesquisas = pesquisas;
        this.seletor = new JComboBox<>(pesquisas.toArray(new Pesquisa[0]));
        setTitle("Perguntas da pesquisa");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(850, 500);
        setLocationRelativeTo(pai);
        montarTela();
        atualizarTabela();
    }

    private void montarTela() {
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(new JLabel("Pesquisa:"));
        seletor.setPreferredSize(new Dimension(320, 28));
        topo.add(seletor);
        seletor.addActionListener(e -> atualizarTabela());

        JButton adicionar = new JButton("Cadastrar pergunta");
        JButton remover = new JButton("Remover pergunta");
        adicionar.addActionListener(e -> cadastrarPergunta());
        remover.addActionListener(e -> removerPergunta());

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(adicionar);
        botoes.add(remover);

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(26);
        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
    }

    private Pesquisa pesquisaSelecionada() {
        return (Pesquisa) seletor.getSelectedItem();
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        Pesquisa pesquisa = pesquisaSelecionada();
        if (pesquisa == null) return;
        for (Pergunta p : pesquisa.getPerguntas()) {
            StringBuilder opcoes = new StringBuilder();
            for (OpcaoResposta op : p.getOpcoes()) {
                if (opcoes.length() > 0) opcoes.append(" | ");
                opcoes.append(op.getDescricao());
            }
            modelo.addRow(new Object[]{p.getIdentificacao(), p.getEnunciado(), opcoes.toString()});
        }
    }

    private void cadastrarPergunta() {
        Pesquisa pesquisa = pesquisaSelecionada();
        if (pesquisa == null) {
            JOptionPane.showMessageDialog(this, "Cadastre uma pesquisa antes de adicionar perguntas.");
            return;
        }
        JTextField enunciado = new JTextField(28);
        JTextArea opcoes = new JTextArea(6, 28);
        opcoes.setLineWrap(true);
        opcoes.setWrapStyleWord(true);
        JPanel formulario = new JPanel(new BorderLayout(6, 6));
        JPanel campo = new JPanel(new BorderLayout(4, 4));
        campo.add(new JLabel("Enunciado da pergunta:"), BorderLayout.NORTH);
        campo.add(enunciado, BorderLayout.CENTER);
        formulario.add(campo, BorderLayout.NORTH);
        JPanel campoOpcoes = new JPanel(new BorderLayout(4, 4));
        campoOpcoes.add(new JLabel("Opções de resposta (uma por linha):"), BorderLayout.NORTH);
        campoOpcoes.add(new JScrollPane(opcoes), BorderLayout.CENTER);
        formulario.add(campoOpcoes, BorderLayout.CENTER);

        if (JOptionPane.showConfirmDialog(this, formulario, "Cadastrar pergunta",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) return;

        String texto = enunciado.getText().trim();
        String[] linhas = opcoes.getText().split("\\R");
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o enunciado da pergunta.");
            return;
        }
        java.util.List<String> validas = new java.util.ArrayList<>();
        for (String linha : linhas) if (!linha.trim().isEmpty()) validas.add(linha.trim());
        if (validas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe pelo menos uma opção de resposta.");
            return;
        }
        int idPergunta = pesquisa.getPerguntas().stream()
                .mapToInt(Pergunta::getIdentificacao).max().orElse(0) + 1;
        Pergunta pergunta = new Pergunta(idPergunta, texto);
        for (int i = 0; i < validas.size(); i++) {
            pergunta.adicionarOpcao(new OpcaoResposta(i + 1, validas.get(i)));
        }
        pesquisa.adicionarPergunta(pergunta);
        salvar();
        atualizarTabela();
    }

    private void removerPergunta() {
        int linha = tabela.getSelectedRow();
        Pesquisa pesquisa = pesquisaSelecionada();
        if (linha < 0 || pesquisa == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma pergunta.");
            return;
        }
        int id = (int) modelo.getValueAt(tabela.convertRowIndexToModel(linha), 0);
        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja remover a pergunta selecionada?", "Confirmar remoção", JOptionPane.YES_NO_OPTION);
        if (confirmacao != JOptionPane.YES_OPTION) return;
        pesquisa.getPerguntas().removeIf(p -> p.getIdentificacao() == id);
        salvar();
        atualizarTabela();
    }

    private void salvar() {
        ArquivoPesquisas.salvar(pesquisas);
    }
}
