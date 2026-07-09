package sd;

import java.util.ArrayList;
import java.time.LocalDate;

public class SistemaSuplemento {
    private ArrayList<Utilizador> utilizadores;
    private ArrayList<AtividadeElegivel> catalogoAtividades;

    public SistemaSuplemento() {
        utilizadores = new ArrayList<>();
        catalogoAtividades = new ArrayList<>();
    }

    public Estudante registarEstudante(String nome, String email, int numero, String password, String curso, int anoCurricular) {
        for (Utilizador u : utilizadores) {
            if (u.getNumero() == numero) {
                System.out.println("Já existe um utilizador com esse número.");
                return null;
            }
        }
        Estudante estudante = new Estudante(nome, email, numero, password, curso, anoCurricular);
        utilizadores.add(estudante);
        return estudante;
    }

    public Utilizador registarGestor(String nome, String email, int numero, String password) {
        for (Utilizador u : utilizadores) {
            if (u.getNumero() == numero) {
                System.out.println("Já existe um utilizador com esse número.");
                return null;
            }
        }
        Utilizador gestor = new Utilizador(nome, email, numero, password);
        utilizadores.add(gestor);
        return gestor;
    }

    public Utilizador login(int numero, String password) {
        for (Utilizador u : utilizadores) {
            if (u.getNumero() == numero && u.getPassword().equals(password)) {
                return u;
            }
        }
        System.out.println("Numero ou password invalidos.");
        return null;
    }

    public AtividadeElegivel criarAtividadeElegivel(String nome, Tipo tipo, String entidadeResponsavel, int cargaHorariaMinima) {
        // TODO
        return null;
    }

    public void editarAtividadeElegivel(int id) {
        // TODO
    }

    public void inativarAtividadeElegivel(int id) {
        // TODO
    }

    public void registarAtividadeEstudante(Estudante estudante, AtividadeElegivel elegivel, LocalDate data, int cargaHoraria, String comprovativo) {
        // TODO
    }

    public void validarAtividade(Utilizador gestor, AtividadeRealizada atividade, EstadoValidacao estado, String observacao) {
        // TODO
    }

    public ArrayList<AtividadeRealizada> pesquisarPorTipoEEstudante(Estudante estudante, Tipo tipo) {
        // TODO
        return null;
    }

    public ArrayList<AtividadeRealizada> gerarComponenteSD(Estudante estudante) {
        // TODO
        return null;
    }

    public void getEstatisticasPorTipo() {
        // TODO
    }
}