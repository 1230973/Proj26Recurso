package sd;

import java.util.ArrayList;
import java.time.LocalDate;

public class SistemaSuplemento {
    private ArrayList<Utilizador> utilizadores;
    private ArrayList<AtividadeElegivel> catalogoAtividades;
    private int proximoIdAtividade = 1;
    private int proximoIdRegisto = 1;

    public SistemaSuplemento() {
        utilizadores = new ArrayList<>();
        catalogoAtividades = new ArrayList<>();
    }

    public ArrayList<Utilizador> getUtilizadores() {
        return utilizadores;
    }

    public ArrayList<AtividadeElegivel> getCatalogoAtividades() {
        return catalogoAtividades;
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
        AtividadeElegivel nova = new AtividadeElegivel(proximoIdAtividade, nome, tipo, entidadeResponsavel, cargaHorariaMinima);
        proximoIdAtividade++;
        catalogoAtividades.add(nova);
        tipo.addAtividadeElegivel(nova);
        System.out.println("Atividade elegível criada com ID " + nova.getIdAtividade());
        return nova;
    }

    public void editarAtividadeElegivel(int id, String nome, Tipo tipo, String entidadeResponsavel, int cargaHorariaMinima) {
        for (AtividadeElegivel a : catalogoAtividades) {
            if (a.getIdAtividade() == id) {
                a.setNome(nome);
                a.setTipo(tipo);
                a.setEntidadeResponsavel(entidadeResponsavel);
                a.setCargaHorariaMinima(cargaHorariaMinima);
                System.out.println("Atividade elegível editada.");
                return;
            }
        }
        System.out.println("Atividade elegível não encontrada.");
    }

    public void inativarAtividadeElegivel(int id) {
        for (AtividadeElegivel a : catalogoAtividades) {
            if (a.getIdAtividade() == id) {
                a.setDisponivel(false);
                System.out.println("Atividade elegível marcada como não disponível.");
                return;
            }
        }
        System.out.println("Atividade elegível não encontrada.");
    }

    public void registarAtividadeEstudante(Estudante estudante, AtividadeElegivel elegivel, LocalDate data, int cargaHoraria, String comprovativo) {
        if (!elegivel.isDisponivel()) {
            System.out.println("Esta atividade elegível não está disponível.");
            return;
        }
        if (cargaHoraria < elegivel.getCargaHorariaMinima()) {
            System.out.println("Carga horária insuficiente. Mínimo exigido: " + elegivel.getCargaHorariaMinima());
            return;
        }
        AtividadeRealizada nova = new AtividadeRealizada(proximoIdRegisto, elegivel, data, cargaHoraria);
        proximoIdRegisto++;
        nova.setComprovativo(comprovativo);
        estudante.registarAtividade(nova);
        System.out.println("Atividade registada com o estado PENDENTE.");
    }

    public void validarAtividade(Utilizador gestor, AtividadeRealizada atividade, EstadoValidacao estado, String observacao) {
        if (gestor instanceof Estudante) {
            System.out.println("Um estudante não pode validar atividades.");
            return;
        }
        atividade.setEstado(estado);
        atividade.setValidadoPor(gestor);
        atividade.setObservacao(observacao);
        System.out.println("Atividade atualizada para o estado " + estado);
    }

    public ArrayList<AtividadeRealizada> pesquisarPorTipoEEstudante(Estudante estudante, Tipo tipo) {
        // TODO - fica para amanha
        return null;
    }

    public ArrayList<AtividadeRealizada> gerarComponenteSD(Estudante estudante) {
        // TODO - fica para amanha
        return null;
    }

    public void getEstatisticasPorTipo() {
        // TODO - fica para amanha
    }
}