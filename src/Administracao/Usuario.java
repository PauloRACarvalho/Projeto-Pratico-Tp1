package Administracao;
import Tratamento_de_Excessoes.ValidarEmailException;
import Tratamento_de_Excessoes.ValidarSenhaTipo1Exception;
import Tratamento_de_Excessoes.ValidarSenhaTipo2Exception;
import java.util.ArrayList;
import java.util.List;


public class Usuario {
    protected String nome;
    protected String email;
    protected String senha;
    protected String perfil;
    protected String status;


    //construtor
    public Usuario(String nome, String email, String senha, String perfil) throws ValidarEmailException, ValidarSenhaTipo1Exception, ValidarSenhaTipo2Exception{
        this.nome = nome;
        this.perfil = perfil;

        validarEmail(email);
        validarSenha(senha);

        this.email = email;
        this.senha = senha;

    }

    public static void validarEmail(String email) throws ValidarEmailException{
        if (email == null || !email.matches("^[\\w.+-]+@gmail\\.com$")) {
            throw new ValidarEmailException("O formato de email é inválido. Tente: user@gmail.com");
        }

    }

    public static void validarSenha(String senha) throws ValidarSenhaTipo1Exception, ValidarSenhaTipo2Exception{
        //metodo .matches() é uma biblioteca regex, vamos usar para verificar letras maiuscula e caracteres especiais
        if (senha == null || senha.isEmpty()){
           throw new ValidarSenhaTipo1Exception("O campo 'senha' precisa estar preenchido."); //erro do tipo 1: senha está vazia

        }
        boolean temMaiuscula = senha.matches(".*[A-Z].*");
        boolean temEspecial = senha.matches(".*([^a-zA-Z0-9]).*");

        if (!temMaiuscula || !temEspecial){
            throw new ValidarSenhaTipo2Exception("Senha precisa ter caracteres especiais Ex: #,*... e letras maiúsculas!");

        }

    }




//    public void ativar(){
//        this.status = "Ativo";
//        System.out.println("Usuário " + nome + " ativado.");
//    }
//
//    public void desativar(){
//        this.status = "Desativado";
//        System.out.println("Usuario " + nome + " desativado.");
//    }

    //Get e Set
    public String getNome() {
        return nome;
    }

    public String getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getPerfil() {
        return perfil;
    }

    public String getSenha() {
        return senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmail(String email) throws ValidarEmailException {
        validarEmail(email);
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public void setSenha(String senha) throws ValidarSenhaTipo1Exception, ValidarSenhaTipo2Exception {validarSenha(senha);
    }
}
