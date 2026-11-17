package Administracao;

import Tratamento_de_Excessoes.ValidarEmailException;
import Tratamento_de_Excessoes.ValidarSenhaTipo1Exception;
import Tratamento_de_Excessoes.ValidarSenhaTipo2Exception;

public class Administrador extends Usuario {

    public Administrador(String nome, String email, String senha, String perfil)
            throws ValidarEmailException, ValidarSenhaTipo1Exception, ValidarSenhaTipo2Exception {
        super(nome, email, senha, perfil);
    }

    @Override
    public String toString() {
        return "nome: " + nome + " email: " + email + " perfil: " + perfil;
    }
}


