package Pesquisas;

public class SituacaoPesquisa {
    private int identificacao;
    private String status;

    public SituacaoPesquisa(int identificacao, String status){
        this.status = status;
        this.identificacao = identificacao;
    }

    public void adicionarStatus(String status){
        this.status = status;
        System.out.println("Status adicionado: " + status);
    }

    public void alterarStatus(String statusNovo){
        //vai validar primeiro o status, se for valido vai retornar true e entra no if
        if (ValidarTransicaoStatus(statusNovo)){
            this.status = statusNovo;
            System.out.println("Status alterado para: " + statusNovo);
        }else{
            System.out.println("Transição de status inválida!");
        }
    }

    public boolean ValidarTransicaoStatus(String statusNovo){
        //Não pode voltar de Concluído para em andamento
        if (this.status.equals("Concluído") && statusNovo.equals("Em andamento")){
            return false;
        }
        //Depois que cancela não tem como alterar
        if (this.status.equals("Cancelada")){
            return false;
        }
        return true;
    }


    //GET e SET
    public int getIdentificacao() {
        return identificacao;
    }

    public String getStatus() {
        return status;
    }

    public void setIdentificacao(int identificacao) {
        this.identificacao = identificacao;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

