package view;

import Candidatos_Partidos.Candidato;
import Candidatos_Partidos.Partidos;
import Pesquisas.Pesquisa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/** Consulta para os três perfis e edição apenas para administrador/pesquisador. */
public class TelaPartidosCandidatos extends JFrame {
    private final Pesquisa pesquisa;
    private final boolean podeEditar;
    private final DefaultTableModel modeloPartidos = new DefaultTableModel(
            new Object[]{"Nome", "Sigla", "Número", "Situação"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final DefaultTableModel modeloCandidatos = new DefaultTableModel(
            new Object[]{"Nome", "Número", "Cargo", "Partido", "Situação"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tabelaPartidos = new JTable(modeloPartidos);
    private final JTable tabelaCandidatos = new JTable(modeloCandidatos);

    public TelaPartidosCandidatos(JFrame pai, Pesquisa pesquisa, boolean podeEditar) {
        this.pesquisa = pesquisa;
        this.podeEditar = podeEditar;
        setTitle("Partidos e candidatos — " + pesquisa.getCodigo());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(820, 500);
        setLocationRelativeTo(pai);
        montarTela();
        atualizarTabelas();
    }

    private void montarTela() {
        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Partidos", criarPainelPartidos());
        abas.addTab("Candidatos", criarPainelCandidatos());
        JLabel permissao = new JLabel(podeEditar
                ? "Você pode cadastrar e alterar a situação dos registros."
                : "Modo de consulta: seu perfil não permite alterações.");
        permissao.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        add(abas, BorderLayout.CENTER);
        add(permissao, BorderLayout.SOUTH);
    }

    private JPanel criarPainelPartidos() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabelaPartidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        painel.add(new JScrollPane(tabelaPartidos), BorderLayout.CENTER);
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton adicionar = new JButton("Cadastrar partido");
        JButton situacao = new JButton("Ativar / desativar selecionado");
        adicionar.setEnabled(podeEditar);
        situacao.setEnabled(podeEditar);
        adicionar.addActionListener(e -> cadastrarPartido());
        situacao.addActionListener(e -> alterarSituacaoPartido());
        botoes.add(adicionar);
        botoes.add(situacao);
        painel.add(botoes, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarPainelCandidatos() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabelaCandidatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        painel.add(new JScrollPane(tabelaCandidatos), BorderLayout.CENTER);
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton adicionar = new JButton("Cadastrar candidato");
        JButton situacao = new JButton("Ativar / desativar selecionado");
        adicionar.setEnabled(podeEditar);
        situacao.setEnabled(podeEditar);
        adicionar.addActionListener(e -> cadastrarCandidato());
        situacao.addActionListener(e -> alterarSituacaoCandidato());
        botoes.add(adicionar);
        botoes.add(situacao);
        painel.add(botoes, BorderLayout.SOUTH);
        return painel;
    }

    private void atualizarTabelas() {
        modeloPartidos.setRowCount(0);
        for (Partidos p : pesquisa.getPartidos()) {
            modeloPartidos.addRow(new Object[]{p.getNome(), p.getSigla(), p.getNumero(), p.getSituacao()});
        }
        modeloCandidatos.setRowCount(0);
        for (Candidato c : pesquisa.getCandidatos()) {
            modeloCandidatos.addRow(new Object[]{c.getNome(), c.getNumero(), c.getCargo(),
                    c.getPartido() == null ? "Não informado" : c.getPartido().getSigla(), c.getSituacao()});
        }
    }

    private void cadastrarPartido() {
        JTextField nome = new JTextField();
        JTextField sigla = new JTextField();
        JTextField numero = new JTextField();
        Object[] campos = {"Nome:", nome, "Sigla:", sigla, "Número:", numero};
        if (JOptionPane.showConfirmDialog(this, campos, "Cadastrar partido",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
        try {
            String n = nome.getText().trim();
            String s = sigla.getText().trim();
            int num = Integer.parseInt(numero.getText().trim());
            if (n.isEmpty() || s.isEmpty() || num <= 0) throw new IllegalArgumentException();
            for (Partidos existente : pesquisa.getPartidos()) {
                if (existente.getNumero() == num || existente.getSigla().equalsIgnoreCase(s)) {
                    JOptionPane.showMessageDialog(this, "Já existe partido com esse número ou sigla.",
                            "Cadastro duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            pesquisa.adicionarPartido(new Partidos(n, s.toUpperCase(), num, "Ativo"));
            atualizarTabelas();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Informe nome, sigla e número inteiro positivo.",
                    "Dados inválidos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cadastrarCandidato() {
        if (pesquisa.getPartidos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cadastre um partido antes de cadastrar candidatos.",
                    "Nenhum partido", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JTextField nome = new JTextField();
        JTextField numero = new JTextField();
        JTextField cargo = new JTextField();
        JComboBox<Partidos> partido = new JComboBox<>(pesquisa.getPartidos().toArray(new Partidos[0]));
        partido.setRenderer((list, value, index, selected, focus) ->
                new JLabel(value == null ? "" : value.getSigla() + " — " + value.getNome()));
        Object[] campos = {"Nome:", nome, "Número:", numero, "Cargo:", cargo, "Partido:", partido};
        if (JOptionPane.showConfirmDialog(this, campos, "Cadastrar candidato",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
        try {
            String n = nome.getText().trim();
            String c = cargo.getText().trim();
            int num = Integer.parseInt(numero.getText().trim());
            if (n.isEmpty() || c.isEmpty() || num <= 0) throw new IllegalArgumentException();
            for (Candidato existente : pesquisa.getCandidatos()) {
                if (existente.getNumero() == num) {
                    JOptionPane.showMessageDialog(this, "Já existe candidato com esse número nesta pesquisa.",
                            "Cadastro duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            Candidato candidato = new Candidato(n, num, c, "Ativo");
            candidato.setPartido((Partidos) partido.getSelectedItem());
            pesquisa.adicionarCandidato(candidato);
            atualizarTabelas();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Informe nome, cargo e número inteiro positivo.",
                    "Dados inválidos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarSituacaoPartido() {
        int linha = tabelaPartidos.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um partido.");
            return;
        }
        Partidos partido = pesquisa.getPartidos().get(tabelaPartidos.convertRowIndexToModel(linha));
        if ("Ativo".equalsIgnoreCase(partido.getSituacao())) partido.desativar();
        else partido.ativar();
        atualizarTabelas();
    }

    private void alterarSituacaoCandidato() {
        int linha = tabelaCandidatos.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um candidato.");
            return;
        }
        Candidato candidato = pesquisa.getCandidatos().get(tabelaCandidatos.convertRowIndexToModel(linha));
        if ("Ativo".equalsIgnoreCase(candidato.getSituacao())) candidato.desativar();
        else candidato.ativar();
        atualizarTabelas();
    }
}
