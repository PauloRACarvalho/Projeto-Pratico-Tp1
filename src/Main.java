import Administracao.GerenciarUsuarios;
import Pesquisas.ArquivoPesquisas;
import view.TelaLogin;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** Ponto de entrada do sistema: inicia  a interface gráfica. */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                System.err.println("Não foi possível aplicar o tema Nimbus: " + e.getMessage());
            }

            GerenciarUsuarios gerenciarUsuarios = new GerenciarUsuarios();
            new TelaLogin(gerenciarUsuarios, ArquivoPesquisas.carregar()).setVisible(true);
        });
    }
}
