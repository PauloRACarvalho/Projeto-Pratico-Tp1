package Pesquisas;

public class PlanoAmostral {
    private int tamanhoPrevisto;
    private String metodologia;


    public PlanoAmostral(int tamanhoPrevisto, String metodologia){
        this.tamanhoPrevisto = tamanhoPrevisto;
        this.metodologia = metodologia;
    }

    //GET e SET
    public int getTamanhoPrevisto() {
        return tamanhoPrevisto;
    }

    public String getMetodologia() {
        return metodologia;
    }

    public void setMetodologia(String metodologia) {
        this.metodologia = metodologia;
    }

    public void setTamanhoPrevisto(int tamanhoPrevisto) {
        this.tamanhoPrevisto = tamanhoPrevisto;
    }
}

