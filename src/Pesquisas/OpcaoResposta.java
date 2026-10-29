package Pesquisas;

public class OpcaoResposta {
    private int identficacao;
    private String descricao;

    public OpcaoResposta(int identificacao, String descricao){
        this.descricao = descricao;
        this.identficacao = identificacao;

    }
    //GET e SET


    public int getIdentficacao() {
        return identficacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setIdentficacao(int identficacao) {
        this.identficacao = identficacao;
    }
}