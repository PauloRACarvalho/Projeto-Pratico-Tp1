package view;

import Administracao.Pesquisador;
import Pesquisas.Pesquisa;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

// criar pesquisas, realizar entrevistas e gerar relatórios
public class TelaPesquisador extends JFrame {
    private final Pesquisador pesquisador;
    private final List<Pesquisa> pesquisas;
    private final JPanel paginas = new JPanel(new CardLayout());
    private final JLabel titulo = new JLabel("Visão geral");

    public TelaPesquisador(Pesquisador pesquisador) { this(pesquisador, pesquisador.getMinhasPesquisas()); }
    public TelaPesquisador(Pesquisador pesquisador, List<Pesquisa> pesquisas) {
        this.pesquisador=pesquisador;
        this.pesquisas=pesquisas==null?new ArrayList<>():pesquisas;
        setTitle("Sistema Eleitoral - Pesquisador");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1180,740);
        setMinimumSize(new Dimension(1000,650));
        setLocationRelativeTo(null);
        montar();
        mostrar("Visão geral");
    }
    private void montar() {
        setLayout(new BorderLayout());
        JPanel lateral=new JPanel();
        lateral.setBackground(ComponentesPainel.LATERAL);
        lateral.setPreferredSize(new Dimension(245,0));
        lateral.setLayout(new BoxLayout(lateral,BoxLayout.Y_AXIS));
        JLabel marca=new JLabel("<html><b>SISTEMA ELEITORAL</b><br><font size='3'>Painel do Pesquisador</font></html>");
        marca.setForeground(Color.WHITE);
        marca.setFont(new Font("Segoe UI",Font.PLAIN,16));
        marca.setBorder(BorderFactory.createEmptyBorder(28,20,30,8));
        lateral.add(marca);

        // trecho dos botões
        for(String item:new String[]{"Visão geral","Criar pesquisa","Perguntas","Partidos e candidatos","Realizar entrevista","Gerar relatórios"}) {
            JButton b=menu(item);
            b.addActionListener(e->{
                if(item.equals("Criar pesquisa")){
                    OperacoesGUI.criarPesquisa(this,pesquisador,pesquisas,pesquisador);
                    atualizarVisao();
                } else if(item.equals("Perguntas")) new TelaPerguntas(this,pesquisador,pesquisas).setVisible(true);
                else if(item.equals("Partidos e candidatos")) abrirPartidosCandidatos();
                else if(item.equals("Realizar entrevista")) OperacoesGUI.realizarEntrevista(this,pesquisador,pesquisas);
                else if(item.equals("Gerar relatórios")) OperacoesGUI.gerarRelatorio(this,pesquisas,"Relatório");
                else mostrar(item); }); lateral.add(b);
        }
        lateral.add(Box.createVerticalGlue());
        JButton sair=menu("Sair");
        sair.addActionListener(e->dispose());
        lateral.add(sair);
        add(lateral,BorderLayout.WEST);
        JPanel topo=new JPanel(new BorderLayout());
        topo.setBackground(Color.WHITE);
        topo.setBorder(BorderFactory.createEmptyBorder(18,28,18,28));
        titulo.setFont(new Font("Segoe UI",Font.BOLD,23));
        topo.add(titulo,BorderLayout.WEST);
        topo.add(new JLabel("Olá, "+pesquisador.getNome()+"  |  PESQUISADOR"),BorderLayout.EAST);
        add(topo,BorderLayout.NORTH);
        paginas.setBackground(ComponentesPainel.FUNDO);
        paginas.add(visao(),"Visão geral"); add(paginas,BorderLayout.CENTER);
    }
    private void abrirPartidosCandidatos() {
        if (pesquisas.isEmpty()) {
            ComponentesPainel.aviso(this, "Cadastre uma pesquisa antes de gerenciar partidos e candidatos.");
            return;
        }
        Pesquisa pesquisa = (Pesquisa) JOptionPane.showInputDialog(this, "Selecione a pesquisa:",
                "Partidos e candidatos", JOptionPane.QUESTION_MESSAGE, null,
                pesquisas.toArray(), pesquisas.get(0));
        if (pesquisa != null) new TelaPartidosCandidatos(this, pesquisa, pesquisador, pesquisas).setVisible(true);
    }
    private JButton menu(String n){
        JButton b=new JButton(n);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE,48));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setForeground(Color.WHITE);
        b.setBackground(ComponentesPainel.LATERAL);
        b.setBorder(BorderFactory.createEmptyBorder(13,20,13,8));
        b.setFocusPainted(false);return b;
    }
    private void mostrar(String n){
        titulo.setText(n);
        ((CardLayout)paginas.getLayout()).show(paginas,n);
    }
    private void atualizarVisao(){
        paginas.remove(0);
        paginas.add(visao(),"Visão geral",0);
        mostrar("Visão geral");
    }
    private JPanel visao(){
        JPanel p=ComponentesPainel.pagina("Visão geral","Acompanhe suas pesquisas e acesse as atividades de coleta.");
        int concluidas=0;
        for(Pesquisa q:pesquisador.consultarPesquisas())if(q.getSituacao()!=null&&"Concluída".equalsIgnoreCase(q.getSituacao().getStatus()))concluidas++;
        JPanel cards=new JPanel(new GridLayout(1,3,14,14));
        cards.setOpaque(false);
        cards.add(ComponentesPainel.card("Minhas pesquisas",String.valueOf(pesquisador.consultarPesquisas().size()),"Pesquisas cadastradas"));
        cards.add(ComponentesPainel.card("Concluídas",String.valueOf(concluidas),"Coletas encerradas"));
        cards.add(ComponentesPainel.card("Entrevistados previstos",pesquisador.consultarPesquisas().stream().mapToInt(Pesquisa::getQtdeEntrevistados).sum()+"","Soma das pesquisas"));
        p.add(cards,BorderLayout.NORTH);JLabel nota=new JLabel("<html><b>Permissões do Pesquisador</b><br><br>Criar pesquisas, realizar entrevistas e gerar relatórios.</html>");
        JPanel caixa=new JPanel(new BorderLayout());
        caixa.setBackground(Color.WHITE);
        caixa.setBorder(BorderFactory.createEmptyBorder(22,22,22,22));
        caixa.add(nota,BorderLayout.NORTH);
        p.add(caixa,BorderLayout.CENTER);
        return p;
    }
}
