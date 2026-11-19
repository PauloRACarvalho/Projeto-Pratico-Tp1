import Administracao.GerenciarUsuarios;
import Pesquisas.ArquivoPesquisas;
import view.TelaLogin;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            GerenciarUsuarios gerenciarUsuarios =
                    new GerenciarUsuarios();

            new TelaLogin(
                    gerenciarUsuarios,
                    ArquivoPesquisas.carregar()
            ).setVisible(true);
        });
    }
}