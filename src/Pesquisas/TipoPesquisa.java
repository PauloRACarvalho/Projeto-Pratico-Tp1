package Pesquisas;

import java.util.Scanner;
public class TipoPesquisa {
    private int identificacao;
    private String nome;

    public TipoPesquisa(int identificacao, String nome) {
        this.identificacao = identificacao;
        this.nome = nome;
    }

    public void cadastrarTipo(){
        Scanner entrada = new Scanner(System.in);

        System.out.println("Digite o nome do tipo de pesquisa: ");
        this.nome = entrada.nextLine();

        System.out.println("Digite a identificação (número): ");
        this.identificacao = entrada.nextInt();

        System.out.println("Tipo de pesquisa cadastrado com sucesso!");
    }

    public void alterarTipo(){
        Scanner entrada = new Scanner(System.in);
        System.out.println("Alterando tipo de pesquisa...");
        System.out.print("Novo nome: ");
        this.nome = entrada.nextLine();

        System.out.println("Tipo de pesquisa alterado com sucesso!");
    }

    //GET e SET

    public int getIdentificacao() {
        return identificacao;
    }

    public String getNome() {
        return nome;
    }

    public void setIdentificacao(int identificacao) {
        this.identificacao = identificacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
