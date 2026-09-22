package br.unb.cic.tp1.view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal() {
        setTitle("Sistema TP1 - Protótipo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout simples para testar
        JLabel label = new JLabel("Protótipo - Tela Principal", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);
    }
}