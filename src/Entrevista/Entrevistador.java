package Entrevista;

public class Entrevistador {
    private int codigo;
    private String nome;

    public Entrevistador(int codigo, String nome){
        this.codigo = codigo;
        this.nome = nome;
    }

    //GET e SET
    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
