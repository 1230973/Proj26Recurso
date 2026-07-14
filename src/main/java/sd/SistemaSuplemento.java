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
        System.out.println("Estudante registado com sucesso.");
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
        System.out.println("Gestor registado com sucesso.");
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

    public AtividadeElegivel criarAtividadeElegivel(String nome, Tipo tipo, String entidadeResponsavel, int cargaHorariaMinima, String descricao) {
        AtividadeElegivel nova = new AtividadeElegivel(proximoIdAtividade, nome, tipo, entidadeResponsavel, cargaHorariaMinima, descricao);
        proximoIdAtividade++;
        catalogoAtividades.add(nova);
        tipo.addAtividadeElegivel(nova);
        System.out.println("Atividade elegível criada com ID " + nova.getIdAtividade());
        return nova;
    }

    public void editarAtividadeElegivel(int id, String nome, Tipo tipo, String entidadeResponsavel, int cargaHorariaMinima, String descricao) {
        for (AtividadeElegivel a : catalogoAtividades) {
            if (a.getIdAtividade() == id) {
                if (a.getTipo() != tipo) {
                    a.getTipo().removeAtividadeElegivel(a);
                    tipo.addAtividadeElegivel(a);
                }
                a.setNome(nome);
                a.setTipo(tipo);
                a.setEntidadeResponsavel(entidadeResponsavel);
                a.setCargaHorariaMinima(cargaHorariaMinima);
                a.setDescricao(descricao);
                System.out.println("Atividade elegível editada.");
                return;
            }
        }
        System.out.println("Atividade elegível não encontrada.");
    }

    public void inativarAtividadeElegivel(int id) {
        for (AtividadeElegivel a : catalogoAtividades) {
            if (a.getIdAtividade() == id) {
                if (temAtividadesRealizadasAssociadas(a)) {
                    System.out.println("Nao e possivel inativar: ja existem atividades realizadas associadas a esta atividade elegivel.");
                    return;
                }
                a.setDisponivel(false);
                System.out.println("Atividade elegível marcada como não disponível.");
                return;
            }
        }
        System.out.println("Atividade elegível não encontrada.");
    }

    private boolean temAtividadesRealizadasAssociadas(AtividadeElegivel elegivel) {
        for (Utilizador u : utilizadores) {
            if (u instanceof Estudante) {
                Estudante e = (Estudante) u;
                for (AtividadeRealizada ar : e.consultarEstadoAtividades()) {
                    if (ar.getAtividadeElegivel() == elegivel) {
                        return true;
                    }
                }
            }
        }
        return false;
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

    public ArrayList<AtividadeRealizada> pesquisarPorTipoEEstudante(Estudante estudante, Tipo tipo, boolean apenasValidadas) {
        ArrayList<AtividadeRealizada> resultado = new ArrayList<>();
        for (AtividadeRealizada ar : estudante.consultarEstadoAtividades()) {
            if (ar.getAtividadeElegivel().getTipo() == tipo) {
                if (!apenasValidadas || ar.getEstado() == EstadoValidacao.VALIDADA) {
                    resultado.add(ar);
                }
            }
        }
        resultado.sort((a, b) -> a.getDataRealizacao().compareTo(b.getDataRealizacao()));
        return resultado;
    }

    public ArrayList<AtividadeRealizada> gerarComponenteSD(Estudante estudante) {
        ArrayList<AtividadeRealizada> validadas = new ArrayList<>();
        for (AtividadeRealizada ar : estudante.consultarEstadoAtividades()) {
            if (ar.getEstado() == EstadoValidacao.VALIDADA) {
                validadas.add(ar);
            }
        }
        if (validadas.size() > 8) {
            System.out.println("Atencao: existem " + validadas.size() + " atividades validadas. So as primeiras 8 vao para o Suplemento ao Diploma.");
            ArrayList<AtividadeRealizada> primeiras8 = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                primeiras8.add(validadas.get(i));
            }
            return primeiras8;
        }
        return validadas;
    }

    public void getEstatisticasPorTipo() {
        ArrayList<String> jaContados = new ArrayList<>();
        String tipoMaisComum = null;
        int maiorTotal = -1;
        for (AtividadeElegivel a : catalogoAtividades) {
            String designacao = a.getTipo().getDesignacao();
            if (jaContados.contains(designacao)) continue;
            jaContados.add(designacao);
            int total = 0;
            for (Utilizador u : utilizadores) {
                if (u instanceof Estudante) {
                    Estudante e = (Estudante) u;
                    for (AtividadeRealizada ar : e.consultarEstadoAtividades()) {
                        if (ar.getEstado() == EstadoValidacao.VALIDADA && ar.getAtividadeElegivel().getTipo().getDesignacao().equals(designacao)) {
                            total++;
                        }
                    }
                }
            }
            System.out.println(designacao + ": " + total + " atividade(s) validada(s)");
            if (total > maiorTotal) {
                maiorTotal = total;
                tipoMaisComum = designacao;
            }
        }
        if (tipoMaisComum != null && maiorTotal > 0) {
            System.out.println("Tipo mais comum: " + tipoMaisComum + " (" + maiorTotal + " atividade(s))");
        }
    }
}