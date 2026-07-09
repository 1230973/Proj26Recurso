package sd;

import java.util.ArrayList;

public class Tipo {
    private String designacao;
    private ArrayList<AtividadeElegivel> atividadesElegiveis;

    public Tipo(String designacao) {
        this.designacao = designacao;
        this.atividadesElegiveis = new ArrayList<>();
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public ArrayList<AtividadeElegivel> getAtividadesElegiveis() {
        return atividadesElegiveis;
    }

    public void addAtividadeElegivel(AtividadeElegivel atividade) {
        atividadesElegiveis.add(atividade);
    }
}