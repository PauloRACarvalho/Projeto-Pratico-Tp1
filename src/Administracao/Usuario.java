package Administracao;
import Tratamento_de_Excessoes.ValidarEmailException;
import Tratamento_de_Excessoes.ValidarSenhaTipo1Exception;
import Tratamento_de_Excessoes.ValidarSenhaTipo2Exception;


public class Usuario {
    //so terão esses dois estados, por isso sao constantes :)
    public static final String ATIVO = "Ativo";
    public static final String DESATIVADO = "Desativado";

    //podem ser usados pelas subclasses e pelas classes :)
    protected String nome;
    protected String email;
    protected String senha;
    protected String perfil;
    protected String status;


    public Usuario(String nome, String email, String senha, String perfil) throws ValidarEmailException, ValidarSenhaTipo1Exception, ValidarSenhaTipo2Exception{
        this.nome = nome;
        this.perfil = perfil;

        validarEmail(email);
        validarSenha(senha);

        this.email = email;
        this.senha = senha;
        this.status = ATIVO;

    } // é instanciado em TelaLogin e TelaCadastro

    public static void validarEmail(String email) throws ValidarEmailException{
        if (email == null || !email.matches("^[\\w.+-]+@(gmail|hotmail)\\.com$")) { //verifica se segue esse padrão
            throw new ValidarEmailException("O formato de email é inválido. Tente: user@gmail ou user@hotmail.com");
        }

    } // static pq não precisa ser instanciado para ser usado. Um exemplo esta em tela cadastro

    public static void validarSenha(String senha) throws ValidarSenhaTipo1Exception, ValidarSenhaTipo2Exception{
        //verificar letras maiuscula e caracteres especiais com regex
        if (senha == null || senha.isEmpty()){
            throw new ValidarSenhaTipo1Exception("O campo 'senha' precisa estar preenchido."); //erro do tipo 1: senha está vazia

        }
        boolean temMaiuscula = senha.matches(".*[A-Z].*");
        boolean temEspecial = senha.matches(".*([^a-zA-Z0-9]).*");//verfifica se não é numero ou letra

        if (!temMaiuscula || !temEspecial){
            throw new ValidarSenhaTipo2Exception("Senha precisa ter caracteres especiais Ex: #,*... e letras maiúsculas!");

        }

    }

    public void ativar(){ //usado em gerenciar usuário

        this.status = ATIVO;
    }

    public void desativar(){ //usado em gerenciar usuário

        this.status = DESATIVADO;
    }

    public boolean isAtivo(){ //usado na tela do admin e na tela de login

        return ATIVO.equalsIgnoreCase(status);
    }

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
        validarEmail(email); //se o email for valido ele retorna
        this.email = email;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public void setSenha(String senha) throws ValidarSenhaTipo1Exception, ValidarSenhaTipo2Exception {
        validarSenha(senha); //se a senha for válida ela retorna
        this.senha = senha;
    }
}