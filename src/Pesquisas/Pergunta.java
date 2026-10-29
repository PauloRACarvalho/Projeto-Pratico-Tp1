package Pesquisas;

import java.util.ArrayList;
import java.util.List;
public class Pergunta {
    private int identificacao;
    private String enunciado;

    //Relacionamento: uma ou mais opcoes de resposta
    private List<OpcaoResposta> opcoes;

    public Pergunta(int identificacao, String enunciado ){
        this.identificacao = identificacao;
        this.enunciado = enunciado;
        //inicializa a Lista vazia
        this.opcoes = new ArrayList<>();
    }

    //GET e SET
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

    //pode adicionar métodos como adicionar opcao de pergunta ou alterar enunciado

    //  Método para adicionar uma opção de resposta à pergunta
    public void adicionarOpcao(OpcaoResposta opcao) {
        if (this.opcoes == null) {
            this.opcoes = new ArrayList<>(); // Garante que a lista existe
        }
        this.opcoes.add(opcao);
        System.out.println("Opção adicionada à pergunta " + this.identificacao);
    }

    //  Método para alterar o enunciado da pergunta
    public void alterarEnunciado(String novoEnunciado) {
        this.enunciado = novoEnunciado;
        System.out.println("Enunciado da pergunta " + this.identificacao + " alterado com sucesso.");
    }

    //  Getter das opções (ESSENCIAL para a classe Estatistica funcionar)
    public List<OpcaoResposta> getOpcoes() {
        return this.opcoes;
    }
}
