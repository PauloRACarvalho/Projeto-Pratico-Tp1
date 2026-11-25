package Pesquisas;

import java.util.ArrayList;
import java.util.List;
public class Pergunta {
    private int identificacao;
    private String enunciado;


    private List<OpcaoResposta> opcoes;

    public Pergunta(int identificacao, String enunciado ){
        this.identificacao = identificacao;
        this.enunciado = enunciado;

        this.opcoes = new ArrayList<>();
    }

    //Gets e Sets
    public int getIdentificacao() {
        return identificacao;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setIdentificacao(int identificacao) {
        this.identificacao = identificacao;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }


    //  adicionar pergunta com opção de resposta
    public void adicionarOpcao(OpcaoResposta opcao) {
        if (this.opcoes == null) {
            this.opcoes = new ArrayList<>(); // Garante que a lista existe
        }
        this.opcoes.add(opcao);
        System.out.println("Opção adicionada à pergunta " + this.identificacao);
    }

    // alterar o enunciado da pergunta
    public void alterarEnunciado(String novoEnunciado) {
        this.enunciado = novoEnunciado;
        System.out.println("Enunciado da pergunta " + this.identificacao + " alterado com sucesso.");
    }

    //  Get das opções
    public List<OpcaoResposta> getOpcoes() {
        return this.opcoes;
    }
}
