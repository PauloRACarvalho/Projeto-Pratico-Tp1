package Tratamento_de_Excessoes;
public class ValidarEmailException extends Exception {
    public ValidarEmailException(String mensagem) {
        super(mensagem);
    }
}