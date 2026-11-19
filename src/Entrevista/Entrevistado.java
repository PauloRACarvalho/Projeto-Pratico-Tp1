package Entrevista;

public class Entrevistado {
    private int codigo;
    private int idade;
    private String escolaridade;
    private String genero;
    private String classeSocial;

    public Entrevistado(int codigo, int idade, String escolaridade, String genero, String classeSocial) {
        this.codigo = codigo;
        this.idade = idade;
        this.escolaridade = escolaridade;
        this.genero = genero;
        this.classeSocial = classeSocial;
    }
    //Gets e Sets

    public int getCodigo() {
        return codigo;
    }

    public int getIdade() {
        return idade;
    }

    public String getClasseSocial() {
        return classeSocial;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public String getGenero() {
        return genero;
    }

    public void setClasseSocial(String classeSocial) {
        this.classeSocial = classeSocial;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
