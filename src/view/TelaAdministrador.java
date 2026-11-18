package view;

import Administracao.*;
import Pesquisas.Pesquisa;
import Tratamento_de_Excessoes.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/** Painel do Administrador: pesquisas, entrevistas, relatórios e gestão de usuários. */
public class TelaAdministrador extends JFrame {
    private final Usuario usuario;
    private final GerenciarUsuarios gerenciador;
    private final List<Pesquisa> pesquisas;
    private final JPanel paginas = new JPanel(new CardLayout());
    private final JLabel titulo = new JLabel("Visão geral");

    public TelaAdministrador(Usuario usuario, GerenciarUsuarios gerenciador, List<Pesquisa> pesquisas) {
        this.usuario = usuario; this.gerenciador = gerenciador;
        this.pesquisas = pesquisas == null ? new ArrayList<>() : pesquisas;
        setTitle("Sistema Eleitoral - Administrador"); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1180, 740); setMinimumSize(new Dimension(1000, 650)); setLocationRelativeTo(null);
        montar(); mostrar("Visão geral");
    }

    private void montar() {
        setLayout(new BorderLayout());
        JPanel lateral = new JPanel(); lateral.setBackground(ComponentesPainel.LATERAL);
        lateral.setPreferredSize(new Dimension(245, 0)); lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        JLabel marca = new JLabel("<html><b>SISTEMA ELEITORAL</b><br><font size='3'>Painel do Administrador</font></html>");
        marca.setForeground(Color.WHITE); marca.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        marca.setBorder(BorderFactory.createEmptyBorder(28, 20, 30, 8)); lateral.add(marca);
        for (String item : new String[]{"Visão geral", "Criar pesquisa", "Realizar entrevista", "Gerar relatórios", "Usuários"}) {
            JButton botao = menu(item); botao.addActionListener(e -> {
                if (item.equals("Criar pesquisa")) { OperacoesGUI.criarPesquisa(this, usuario, pesquisas, null); atualizarVisao(); }
                else if (item.equals("Realizar entrevista")) OperacoesGUI.realizarEntrevista(this, usuario, pesquisas);
                else if (item.equals("Gerar relatórios")) OperacoesGUI.gerarRelatorio(this, pesquisas, "Relatório");
                else mostrar(item);
            }); lateral.add(botao);
        }
        lateral.add(Box.createVerticalGlue()); JButton sair = menu("Sair"); sair.addActionListener(e -> dispose()); lateral.add(sair);
        add(lateral, BorderLayout.WEST);
        JPanel topo = new JPanel(new BorderLayout()); topo.setBackground(Color.WHITE);
        topo.setBorder(BorderFactory.createEmptyBorder(18, 28, 18, 28)); titulo.setFont(new Font("Segoe UI", Font.BOLD, 23));
        topo.add(titulo, BorderLayout.WEST); topo.add(new JLabel("Olá, " + usuario.getNome() + "  |  ADMINISTRADOR"), BorderLayout.EAST); add(topo, BorderLayout.NORTH);
        paginas.setBackground(ComponentesPainel.FUNDO); paginas.add(visao(), "Visão geral"); paginas.add(usuarios(), "Usuários"); add(paginas, BorderLayout.CENTER);
    }

    private JButton menu(String texto) { JButton b = new JButton(texto); b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48)); b.setHorizontalAlignment(SwingConstants.LEFT); b.setForeground(Color.WHITE); b.setBackground(ComponentesPainel.LATERAL); b.setBorder(BorderFactory.createEmptyBorder(13, 20, 13, 8)); b.setFocusPainted(false); return b; }
    private void mostrar(String nome) { titulo.setText(nome); ((CardLayout) paginas.getLayout()).show(paginas, nome); }
    private void atualizarVisao() { paginas.remove(paginas.getComponent(0)); paginas.add(visao(), "Visão geral", 0); mostrar("Visão geral"); }

    private JPanel visao() {
        JPanel p = ComponentesPainel.pagina("Visão geral", "Acesso às funções administrativas do sistema.");
        List<Usuario> lista = gerenciador.getUsuarios(); int ativos = 0;
        for (Usuario u : lista) if (u.isAtivo()) ativos++;
        JPanel cards = new JPanel(new GridLayout(1, 3, 14, 14)); cards.setOpaque(false);
        cards.add(ComponentesPainel.card("Usuários cadastrados", String.valueOf(lista.size()), "Contas no sistema"));
        cards.add(ComponentesPainel.card("Usuários ativos", String.valueOf(ativos), "Contas habilitadas"));
        cards.add(ComponentesPainel.card("Pesquisas cadastradas", String.valueOf(pesquisas.size()), "Pesquisas disponíveis"));
        p.add(cards, BorderLayout.NORTH);
        JLabel nota = new JLabel("<html><b>Permissões do Administrador</b><br><br>Criar pesquisas, realizar entrevistas, gerar relatórios e gerenciar usuários.</html>");
        JPanel caixa = new JPanel(new BorderLayout()); caixa.setBackground(Color.WHITE); caixa.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22)); caixa.add(nota, BorderLayout.NORTH); p.add(caixa, BorderLayout.CENTER);
        return p;
    }

    private JPanel usuarios() {
        JPanel p = ComponentesPainel.pagina("Gerenciar usuários", "Cadastrar, listar e ativar ou desativar contas.");
        JTable tabela = tabelaUsuarios(); JButton cadastrar = new JButton("Cadastrar usuário"), atualizar = new JButton("Atualizar lista"), status = new JButton("Ativar / desativar selecionado");
        cadastrar.addActionListener(e -> cadastrarUsuario());
        atualizar.addActionListener(e -> tabela.setModel(modeloUsuarios()));
        status.addActionListener(e -> {
            int linha = tabela.getSelectedRow(); if (linha < 0) { ComponentesPainel.aviso(this, "Selecione um usuário."); return; }
            String email = String.valueOf(tabela.getValueAt(tabela.convertRowIndexToModel(linha), 1)); Usuario alvo = gerenciador.buscarPorEmail(email);
            if (alvo == null) return;
            if (alvo.getEmail().equalsIgnoreCase(usuario.getEmail())) { ComponentesPainel.aviso(this, "Você não pode alterar o status da própria conta."); return; }
            boolean ativar = !alvo.isAtivo();
            if (JOptionPane.showConfirmDialog(this, (ativar ? "Ativar" : "Desativar") + " a conta de " + alvo.getNome() + "?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                gerenciador.alterarStatus(email, ativar); tabela.setModel(modeloUsuarios());
            }
        });
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT)); botoes.setOpaque(false); botoes.add(cadastrar); botoes.add(atualizar); botoes.add(status);
        p.add(ComponentesPainel.comTabela(tabela), BorderLayout.CENTER); p.add(botoes, BorderLayout.SOUTH); return p;
    }
    private DefaultTableModel modeloUsuarios() { return new DefaultTableModel(dadosUsuarios(), new String[]{"Nome", "Email", "Perfil", "Status"}) { public boolean isCellEditable(int r, int c) { return false; } }; }
    private Object[][] dadosUsuarios() { List<Usuario> us = gerenciador.getUsuarios(); Object[][] d = new Object[us.size()][4]; for (int i=0;i<us.size();i++) { Usuario u=us.get(i); d[i]=new Object[]{u.getNome(),u.getEmail(),u.getPerfil(),u.getStatus()}; } return d; }
    private JTable tabelaUsuarios() { return new JTable(modeloUsuarios()); }

    private void cadastrarUsuario() {
        JTextField nome = new JTextField(), email = new JTextField(); JPasswordField senha = new JPasswordField();
        JComboBox<String> perfil = new JComboBox<>(new String[]{"Administrador", "Pesquisador", "Analista"});
        JPanel form = new JPanel(new GridLayout(0,2,8,8)); form.add(new JLabel("Nome:")); form.add(nome); form.add(new JLabel("E-mail:")); form.add(email); form.add(new JLabel("Senha:")); form.add(senha); form.add(new JLabel("Perfil:")); form.add(perfil);
        if (JOptionPane.showConfirmDialog(this, form, "Cadastrar usuário", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
        try {
            String n=nome.getText().trim(), em=email.getText().trim(), s=new String(senha.getPassword());
            if(n.isEmpty()) throw new IllegalArgumentException("Informe o nome.");
            if(gerenciador.emailExists(em)) throw new IllegalArgumentException("Este e-mail já está cadastrado.");
            Usuario.validarEmail(em); Usuario.validarSenha(s); String tipo=(String)perfil.getSelectedItem(); Usuario novo;
            if("Administrador".equals(tipo)) novo=new Administrador(n,em,s,tipo); else if("Pesquisador".equals(tipo)) novo=new Pesquisador(n,em,s,tipo); else novo=new Analista(n,em,s,tipo);
            gerenciador.adicionarUsuario(novo);
            paginas.remove(1); paginas.add(usuarios(), "Usuários", 1); mostrar("Usuários");
        } catch (ValidarEmailException | ValidarSenhaTipo1Exception | ValidarSenhaTipo2Exception | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cadastro não realizado", JOptionPane.ERROR_MESSAGE);
        }
    }
}
