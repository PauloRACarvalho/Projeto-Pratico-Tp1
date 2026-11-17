package view;

import Administracao.Usuario;
import Candidatos_Partidos.Candidato;
import Candidatos_Partidos.Partidos;
import Pesquisas.Pesquisa;
import Pesquisas.Pergunta;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Painel de consulta do perfil Analista.
 * Recebe a lista de pesquisas mantida pela aplicação; não cria dados fictícios.
 */
public class TelaAnalista extends JFrame {
    private final Usuario usuario;
    private final List<Pesquisa> pesquisas;
    private final JPanel conteudo = new JPanel(new BorderLayout());
    private final CardLayout cards = new CardLayout();
    private final JPanel paginas = new JPanel(cards);
    private final JLabel tituloPagina = new JLabel("Visão geral");
    private final Map<String, JPanel> paineis = new LinkedHashMap<>();
    private final Color azul = new Color(37, 99, 235);
    private final Color fundo = new Color(245, 247, 250);

    public TelaAnalista(Usuario usuario, List<Pesquisa> pesquisas) {
        this.usuario = usuario;
        this.pesquisas = pesquisas == null ? new ArrayList<>() : pesquisas;
        setTitle("Sistema de Pesquisas Eleitorais - Analista");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1050, 680));
        setSize(1200, 760);
        setLocationRelativeTo(null);
        montarInterface();
        mostrarPagina("Visão geral");
    }

    private void montarInterface() {
        setLayout(new BorderLayout());
        JPanel lateral = new JPanel();
        lateral.setBackground(new Color(30, 41, 59));
        lateral.setPreferredSize(new Dimension(235, 0));
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        JLabel marca = new JLabel("<html><b>SISTEMA ELEITORAL</b><br><font size='3'>Painel do Analista</font></html>");
        marca.setForeground(Color.WHITE);
        marca.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        marca.setBorder(BorderFactory.createEmptyBorder(28, 20, 30, 10));
        lateral.add(marca);

        String[] itens = {"Visão geral", "Pesquisas", "Candidatos e partidos",
            "Entrevistas", "Relatórios estatísticos", "Comparar pesquisas"};
        for (String item : itens) {
            JButton b = new JButton(item);
            b.setHorizontalAlignment(SwingConstants.LEFT);
            b.setForeground(Color.WHITE);
            b.setBackground(new Color(30, 41, 59));
            b.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            b.setBorder(BorderFactory.createEmptyBorder(13, 20, 13, 8));
            b.setFocusPainted(false);
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
            b.addActionListener(e -> mostrarPagina(item));
            lateral.add(b);
        }
        lateral.add(Box.createVerticalGlue());
        JButton sair = new JButton("Sair");
        sair.setForeground(Color.WHITE);
        sair.setBackground(new Color(30, 41, 59));
        sair.setBorder(BorderFactory.createEmptyBorder(14, 20, 22, 8));
        sair.setHorizontalAlignment(SwingConstants.LEFT);
        sair.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        sair.addActionListener(e -> dispose());
        lateral.add(sair);
        add(lateral, BorderLayout.WEST);

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(Color.WHITE);
        topo.setBorder(BorderFactory.createEmptyBorder(18, 28, 18, 28));
        tituloPagina.setFont(new Font("Segoe UI", Font.BOLD, 23));
        tituloPagina.setForeground(new Color(15, 23, 42));
        String nome = usuario == null ? "Analista" : usuario.getNome();
        JLabel perfil = new JLabel("Olá, " + nome + "  |  ANALISTA");
        perfil.setForeground(new Color(100, 116, 139));
        topo.add(tituloPagina, BorderLayout.WEST);
        topo.add(perfil, BorderLayout.EAST);
        add(topo, BorderLayout.NORTH);

        paginas.setBackground(fundo);
        adicionarPagina("Visão geral", criarVisaoGeral());
        adicionarPagina("Pesquisas", criarTabelaPesquisas());
        adicionarPagina("Candidatos e partidos", criarCandidatosPartidos());
        adicionarPagina("Entrevistas", criarEntrevistas());
        adicionarPagina("Relatórios estatísticos", criarRelatorios());
        adicionarPagina("Comparar pesquisas", criarComparacao());
        conteudo.setBackground(fundo);
        conteudo.add(paginas, BorderLayout.CENTER);
        add(conteudo, BorderLayout.CENTER);
    }

    private void adicionarPagina(String nome, JPanel painel) {
        paineis.put(nome, painel);
        paginas.add(painel, nome);
    }

    private void mostrarPagina(String nome) {
        tituloPagina.setText(nome);
        cards.show(paginas, nome);
        JPanel p = paineis.get(nome);
        if (p != null) {
            p.revalidate();
            p.repaint();
        }
    }

    private JPanel pagina(String titulo, String subtitulo) {
        JPanel p = new JPanel(new BorderLayout(0, 18));
        p.setBackground(fundo);
        p.setBorder(BorderFactory.createEmptyBorder(25, 28, 25, 28));
        JPanel cab = new JPanel();
        cab.setOpaque(false);
        cab.setLayout(new BoxLayout(cab, BoxLayout.Y_AXIS));
        JLabel t = new JLabel(titulo);
        t.setFont(new Font("Segoe UI", Font.BOLD, 22));
        JLabel s = new JLabel(subtitulo);
        s.setForeground(new Color(100, 116, 139));
        s.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cab.add(t); cab.add(Box.createVerticalStrut(5)); cab.add(s);
        p.add(cab, BorderLayout.NORTH);
        return p;
    }

    private JPanel cartao(String rotulo, String valor, String observacao) {
        JPanel c = new JPanel();
        c.setBackground(Color.WHITE);
        c.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226,232,240)),
            BorderFactory.createEmptyBorder(17,18,17,18)));
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        JLabel r = new JLabel(rotulo); r.setForeground(new Color(100,116,139));
        JLabel v = new JLabel(valor); v.setFont(new Font("Segoe UI",Font.BOLD,28));
        v.setForeground(new Color(15,23,42));
        JLabel o = new JLabel(observacao); o.setForeground(new Color(100,116,139));
        c.add(r); c.add(Box.createVerticalStrut(8)); c.add(v); c.add(Box.createVerticalStrut(5)); c.add(o);
        return c;
    }

    private JPanel criarVisaoGeral() {
        JPanel p = pagina("Visão geral", "Resumo das informações disponíveis para consulta.");
        JPanel kpis = new JPanel(new GridLayout(1, 3, 14, 14));
        kpis.setOpaque(false);
        kpis.add(cartao("Pesquisas cadastradas", String.valueOf(pesquisas.size()), "Na lista recebida pelo painel"));
        int candidatos=0, partidos=0, perguntas=0;
        Set<String> siglas = new HashSet<>();
        for (Pesquisa q : pesquisas) {
            candidatos += q.getCandidatos().size();
            perguntas += q.getPerguntas().size();
            for (Partidos x : q.getPartidos()) siglas.add(x.getSigla());
        }
        partidos = siglas.size();
        kpis.add(cartao("Candidatos vinculados", String.valueOf(candidatos), "Somatório das pesquisas"));
        kpis.add(cartao("Partidos vinculados", String.valueOf(partidos), "Siglas distintas"));
        JPanel aviso = new JPanel(new BorderLayout());
        aviso.setBackground(Color.WHITE);
        aviso.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(226,232,240)),
                BorderFactory.createEmptyBorder(20,20,20,20)));
        JLabel msg = new JLabel("<html><b>Informações sobre os dados</b><br><br>"
            + "O projeto atualmente mantém perguntas e opções cadastradas, mas não possui uma coleção central "
            + "de pesquisas nem um vínculo completo entre entrevistas, respostas e pesquisas. "
            + "Por isso, o painel não apresenta percentuais de intenção de voto nem totais de entrevistas inventados."
            + "<br><br>Perguntas cadastradas: " + perguntas + "</html>");
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        aviso.add(msg, BorderLayout.CENTER);
        JPanel centro = new JPanel(new BorderLayout(0,16)); centro.setOpaque(false);
        centro.add(kpis, BorderLayout.NORTH); centro.add(aviso, BorderLayout.CENTER);
        p.add(centro, BorderLayout.CENTER);
        return p;
    }

    private JTable tabela(String[] colunas, Object[][] dados) {
        DefaultTableModel m = new DefaultTableModel(dados, colunas) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable t = new JTable(m);
        t.setRowHeight(29);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        t.setAutoCreateRowSorter(true);
        return t;
    }

    private JPanel comTabela(JTable tabela) {
        JPanel c = new JPanel(new BorderLayout());
        c.setBackground(Color.WHITE);
        c.setBorder(BorderFactory.createLineBorder(new Color(226,232,240)));
        c.add(new JScrollPane(tabela), BorderLayout.CENTER);
        return c;
    }

    private JPanel criarTabelaPesquisas() {
        JPanel p = pagina("Pesquisas", "Consulte os dados cadastrais das pesquisas.");
        String[] cols = {"Código","Cargo","Período","Entrevistados previstos","Metodologia","Situação"};
        Object[][] dados = new Object[pesquisas.size()][cols.length];
        for (int i=0;i<pesquisas.size();i++) {
            Pesquisa q=pesquisas.get(i);
            dados[i]=new Object[]{q.getCodigo(),q.getCargo(),q.getPeriodoColeta(),q.getQtdeEntrevistados(),
                q.getMetodologia(),q.getSituacao()==null?"Não informada":q.getSituacao().getStatus()};
        }
        JTable t=tabela(cols,dados);
        JButton detalhes=new JButton("Ver detalhes");
        detalhes.addActionListener(e -> {
            int row=t.getSelectedRow();
            if(row<0){aviso("Selecione uma pesquisa.");return;}
            Pesquisa q=pesquisas.get(t.convertRowIndexToModel(row));
            StringBuilder sb=new StringBuilder();
            sb.append("Código: ").append(q.getCodigo()).append("\nCargo: ").append(q.getCargo())
              .append("\nPeríodo: ").append(q.getPeriodoColeta()).append("\nEntrevistados previstos: ")
              .append(q.getQtdeEntrevistados()).append("\nMetodologia: ").append(q.getMetodologia())
              .append("\nPerguntas: ").append(q.getPerguntas().size())
              .append("\nCandidatos vinculados: ").append(q.getCandidatos().size());
            JOptionPane.showMessageDialog(this,sb.toString(),"Detalhes da pesquisa",JOptionPane.INFORMATION_MESSAGE);
        });
        JPanel sul=new JPanel(new FlowLayout(FlowLayout.RIGHT)); sul.setOpaque(false); sul.add(detalhes);
        p.add(comTabela(t),BorderLayout.CENTER); p.add(sul,BorderLayout.SOUTH);
        return p;
    }

    private JPanel criarCandidatosPartidos() {
        JPanel p=pagina("Candidatos e partidos","Consulta somente leitura dos registros vinculados às pesquisas.");
        List<Object[]> cand=new ArrayList<>(), part=new ArrayList<>();
        Set<String> vistos=new HashSet<>();
        for(Pesquisa q:pesquisas){
            for(Partidos x:q.getPartidos()){
                String key=x.getSigla()+"#"+x.getNumero();
                if(vistos.add(key)) part.add(new Object[]{x.getNome(),x.getSigla(),x.getNumero(),x.getSituacao()});
            }
            for(Candidato x:q.getCandidatos()){
                cand.add(new Object[]{x.getNome(),x.getNumero(),x.getCargo(),
                    x.getPartido()==null?"Não informado":x.getPartido().getSigla(),x.getSituacao(),q.getCodigo()});
            }
        }
        JTable tp=tabela(new String[]{"Partido","Sigla","Número","Situação"},part.toArray(new Object[0][]));
        JTable tc=tabela(new String[]{"Candidato","Número","Cargo","Partido","Situação","Pesquisa"},cand.toArray(new Object[0][]));
        JTabbedPane tabs=new JTabbedPane();
        tabs.addTab("Candidatos",comTabela(tc)); tabs.addTab("Partidos",comTabela(tp));
        p.add(tabs,BorderLayout.CENTER);
        return p;
    }

    private JPanel criarEntrevistas() {
        JPanel p=pagina("Entrevistas e respostas","Consulta dos registros de coleta.");
        JPanel aviso=new JPanel(new BorderLayout());
        aviso.setBackground(Color.WHITE);
        aviso.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(226,232,240)),
            BorderFactory.createEmptyBorder(22,22,22,22)));
        aviso.add(new JLabel("<html><b>Consulta indisponível na estrutura atual</b><br><br>"
            +"As classes Entrevista e Resposta existem, mas as entrevistas não estão armazenadas em uma lista "
            +"acessível a partir de Pesquisa ou de um repositório central. Para exibi-las aqui, será necessário "
            +"integrar o módulo de coleta e associar as entrevistas às pesquisas.</html>"),BorderLayout.NORTH);
        p.add(aviso,BorderLayout.NORTH);
        return p;
    }

    private JPanel criarRelatorios() {
        JPanel p=pagina("Relatórios estatísticos","Selecione uma pesquisa para consultar os dados estruturais e exportar um resumo.");
        JPanel form=new JPanel(new FlowLayout(FlowLayout.LEFT,12,12));
        form.setBackground(Color.WHITE);
        JComboBox<Pesquisa> combo=new JComboBox<>(pesquisas.toArray(new Pesquisa[0]));
        combo.setRenderer((list,value,index,selected,focus)->new JLabel(value==null?"":value.getCodigo()+" - "+value.getCargo()));
        JButton gerar=new JButton("Exportar relatório (.txt)");
        JLabel nota=new JLabel("Não há respostas coletadas vinculadas às opções; percentuais eleitorais não podem ser calculados.");
        nota.setForeground(new Color(180,83,9));
        gerar.addActionListener(e->{
            Pesquisa q=(Pesquisa)combo.getSelectedItem();
            if(q==null){aviso("Não há pesquisas disponíveis.");return;}
            JFileChooser fc=new JFileChooser(); fc.setSelectedFile(new File("relatorio_"+q.getCodigo()+".txt"));
            if(fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
                try{salvarRelatorio(q,fc.getSelectedFile());aviso("Relatório exportado com sucesso.");}
                catch(IOException ex){aviso("Erro ao salvar: "+ex.getMessage());}
            }
        });
        form.add(new JLabel("Pesquisa:")); form.add(combo); form.add(gerar);
        JPanel texto=new JPanel(new BorderLayout()); texto.setBackground(Color.WHITE);
        texto.setBorder(BorderFactory.createEmptyBorder(18,18,18,18));
        texto.add(nota,BorderLayout.NORTH);
        p.add(form,BorderLayout.NORTH); p.add(texto,BorderLayout.CENTER);
        return p;
    }

    private void salvarRelatorio(Pesquisa q, File arquivo) throws IOException {
        try(Writer w=new OutputStreamWriter(new FileOutputStream(arquivo),StandardCharsets.UTF_8)){
            w.write("RELATÓRIO DE CONSULTA - PESQUISA ELEITORAL\n\n");
            w.write("Código: "+q.getCodigo()+"\nCargo: "+q.getCargo()+"\nPeríodo: "+q.getPeriodoColeta()
                +"\nEntrevistados previstos: "+q.getQtdeEntrevistados()+"\nMetodologia: "+q.getMetodologia()
                +"\nSituação: "+(q.getSituacao()==null?"Não informada":q.getSituacao().getStatus())+"\n\n");
            w.write("PERGUNTAS E OPÇÕES CADASTRADAS\n");
            for(Pergunta pergunta:q.getPerguntas()){
                w.write("\n"+pergunta.getIdentificacao()+". "+pergunta.getEnunciado()+"\n");
                pergunta.getOpcoes().forEach(op->{
                    try{w.write(" - "+op.getDescricao()+"\n");}catch(IOException ex){throw new UncheckedIOException(ex);}
                });
            }
            w.write("\nOBSERVAÇÃO: o modelo atual não armazena as escolhas dos entrevistados vinculadas "
                +"às opções. Portanto, este relatório não calcula frequências ou percentuais de votação.\n");
        } catch(UncheckedIOException ex){throw ex.getCause();}
    }

    private JPanel criarComparacao() {
        JPanel p=pagina("Comparar pesquisas","Compare informações cadastrais de duas pesquisas.");
        JPanel topo=new JPanel(new FlowLayout(FlowLayout.LEFT,12,12)); topo.setBackground(Color.WHITE);
        JComboBox<Pesquisa> a=new JComboBox<>(pesquisas.toArray(new Pesquisa[0]));
        JComboBox<Pesquisa> b=new JComboBox<>(pesquisas.toArray(new Pesquisa[0]));
        JButton comparar=new JButton("Comparar");
        JTextArea resultado=new JTextArea(); resultado.setEditable(false); resultado.setFont(new Font("Monospaced",Font.PLAIN,13));
        comparar.addActionListener(e->{
            Pesquisa x=(Pesquisa)a.getSelectedItem(), y=(Pesquisa)b.getSelectedItem();
            if(x==null||y==null){aviso("Cadastre ou carregue pelo menos duas pesquisas.");return;}
            if(x==y){aviso("Selecione duas pesquisas diferentes.");return;}
            resultado.setText("COMPARAÇÃO DE PESQUISAS\n\n"
                +"Campo                 "+x.getCodigo()+"                 "+y.getCodigo()+"\n"
                +"Cargo                 "+x.getCargo()+"                 "+y.getCargo()+"\n"
                +"Período               "+x.getPeriodoColeta()+"                 "+y.getPeriodoColeta()+"\n"
                +"Entrevistados prev.   "+x.getQtdeEntrevistados()+"                 "+y.getQtdeEntrevistados()+"\n"
                +"Metodologia           "+x.getMetodologia()+"                 "+y.getMetodologia()+"\n\n"
                +"Comparação limitada aos dados cadastrais. Não existem respostas vinculadas para comparar percentuais.");
        });
        topo.add(new JLabel("Pesquisa A:"));topo.add(a);topo.add(new JLabel("Pesquisa B:"));topo.add(b);topo.add(comparar);
        resultado.setBorder(BorderFactory.createEmptyBorder(18,18,18,18));
        JPanel painelTexto=new JPanel(new BorderLayout()); painelTexto.setBackground(Color.WHITE);
        painelTexto.add(new JScrollPane(resultado));
        p.add(topo,BorderLayout.NORTH);p.add(painelTexto,BorderLayout.CENTER);
        return p;
    }

    private void aviso(String texto) {
        JOptionPane.showMessageDialog(this,texto,"Painel do Analista",JOptionPane.INFORMATION_MESSAGE);
    }
}
