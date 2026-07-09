package sd;

import java.util.ArrayList;
import java.time.LocalDate;

public class Estudante extends Utilizador {
    private String curso;
    private int anoCurricular;
    private ArrayList<AtividadeRealizada> atividades;

    public Estudante(String nome, String email, int numero, String password, String curso, int anoCurricular) {
        super(nome, email, numero, password);
        this.curso = curso;
        this.anoCurricular = anoCurricular;
        this.atividades = new ArrayList<>();
    }

    public String getCurso() {
        return curso;
    }

    public int getAnoCurricular() {
        return anoCurricular;
    }

    public ArrayList<AtividadeRealizada> getAtividades() {
        return atividades;
    }

    public void registarAtividade(AtividadeElegivel elegivel, LocalDate data, int cargaHoraria, String comprovativo) {
        // TODO
    }

    public ArrayList<AtividadeRealizada> consultarEstadoAtividades() {
        return atividades;
    }

    public ArrayList<AtividadeRealizada> consultarHistorico() {
        return atividades;
    }
}