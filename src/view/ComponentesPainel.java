package view;

import java.awt.*;
import javax.swing.*;

/** Componentes visuais compartilhados pelos painéis de perfil. */
final class ComponentesPainel {
    static final Color FUNDO = new Color(245,247,250);
    static final Color LATERAL = new Color(30,41,59);
    static final Color AZUL = new Color(37,99,235);
    private ComponentesPainel() {}

    static JPanel pagina(String titulo, String subtitulo) {
        JPanel p = new JPanel(new BorderLayout(0,16)); p.setBackground(FUNDO);
        p.setBorder(BorderFactory.createEmptyBorder(24,28,24,28));
        JPanel cab = new JPanel(); cab.setOpaque(false); cab.setLayout(new BoxLayout(cab,BoxLayout.Y_AXIS));
        JLabel t = new JLabel(titulo); t.setFont(new Font("Segoe UI",Font.BOLD,22)); t.setForeground(new Color(15,23,42));
        JLabel st = new JLabel(subtitulo); st.setFont(new Font("Segoe UI",Font.PLAIN,13)); st.setForeground(new Color(100,116,139));
        cab.add(t); cab.add(Box.createVerticalStrut(5)); cab.add(st); p.add(cab,BorderLayout.NORTH); return p;
    }
    static JPanel card(String nome,String valor,String detalhe) {
        JPanel p=new JPanel(); p.setBackground(Color.WHITE); p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226,232,240)),BorderFactory.createEmptyBorder(16,18,16,18)));
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS)); JLabel a=new JLabel(nome); a.setForeground(new Color(100,116,139));
        JLabel b=new JLabel(valor); b.setFont(new Font("Segoe UI",Font.BOLD,27)); b.setForeground(new Color(15,23,42));
        JLabel c=new JLabel(detalhe); c.setForeground(new Color(100,116,139)); p.add(a);p.add(Box.createVerticalStrut(8));p.add(b);p.add(Box.createVerticalStrut(4));p.add(c);return p;
    }
    static JTable tabela(String[] col,Object[][] dados) {
        JTable t=new JTable(dados,col); t.setRowHeight(29); t.setFont(new Font("Segoe UI",Font.PLAIN,13));
        t.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,13)); t.setAutoCreateRowSorter(true);
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); return t;
    }
    static JPanel comTabela(JTable t) { JPanel p=new JPanel(new BorderLayout());p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createLineBorder(new Color(226,232,240)));p.add(new JScrollPane(t));return p; }
    static void aviso(Component c,String msg) { JOptionPane.showMessageDialog(c,msg,"Sistema eleitoral",JOptionPane.INFORMATION_MESSAGE); }
}
