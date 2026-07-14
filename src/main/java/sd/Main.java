package sd;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SistemaSuplemento sistema = new SistemaSuplemento();

        ArrayList<Tipo> tipos = new ArrayList<>();
        tipos.add(new Tipo("DESPORTO"));
        tipos.add(new Tipo("VOLUNTARIADO"));
        tipos.add(new Tipo("ASSOCIATIVISMO"));
        tipos.add(new Tipo("MOBILIDADE_INTERNACIONAL"));
        tipos.add(new Tipo("FORMACAO_COMPLEMENTAR"));
        tipos.add(new Tipo("REPRESENTACAO_ESTUDANTIL"));
        tipos.add(new Tipo("ATIVIDADE_CULTURAL"));
        tipos.add(new Tipo("OUTRA"));

        sistema.registarEstudante("Tomas", "tomas@upt.pt", 1001, "1234", "Engenharia Informatica", 3);
        sistema.registarGestor("Paula Morais", "pmorais@upt.pt", 2001, "abcd");

        System.out.println("=== Sistema de Gestao de Atividades para o Suplemento ao Diploma ===");

        boolean continuar = true;
        while (continuar) {
            try {
                System.out.println("\n1 - Login");
                System.out.println("2 - Registar como estudante");
                System.out.println("3 - Registar como gestor");
                System.out.println("0 - Sair");
                System.out.print("Opcao: ");
                int opcaoInicial = sc.nextInt();

                if (opcaoInicial == 0) {
                    continuar = false;
                    continue;
                } else if (opcaoInicial == 2) {
                    sc.nextLine();
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Numero: ");
                    int numero = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Password: ");
                    String password = sc.nextLine();
                    System.out.print("Curso: ");
                    String curso = sc.nextLine();
                    System.out.print("Ano curricular: ");
                    int ano = sc.nextInt();
                    sistema.registarEstudante(nome, email, numero, password, curso, ano);
                    continue;
                } else if (opcaoInicial == 3) {
                    sc.nextLine();
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Numero: ");
                    int numero = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Password: ");
                    String password = sc.nextLine();
                    sistema.registarGestor(nome, email, numero, password);
                    continue;
                } else if (opcaoInicial != 1) {
                    System.out.println("Opcao invalida.");
                    continue;
                }

                System.out.print("Numero: ");
                int numero = sc.nextInt();
                System.out.print("Password: ");
                String password = sc.next();

                Utilizador utilizador = sistema.login(numero, password);
                if (utilizador == null) {
                    continue;
                }

                if (utilizador instanceof Estudante) {
                    Estudante estudante = (Estudante) utilizador;
                    menuEstudante(sc, sistema, estudante, tipos);
                } else {
                    menuGestor(sc, sistema, utilizador, tipos);
                }
            } catch (Exception e) {
                System.out.println("Entrada invalida. Tenta novamente.");
                sc.nextLine();
            }
        }

        System.out.println("Programa terminado.");
    }

    private static void menuEstudante(Scanner sc, SistemaSuplemento sistema, Estudante estudante, ArrayList<Tipo> tipos) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int opcao = -1;
        while (opcao != 0) {
            try {
                System.out.println("\n--- Menu Estudante ---");
                System.out.println("1 - Registar atividade realizada");
                System.out.println("2 - Consultar estado das minhas atividades");
                System.out.println("0 - Sair");
                System.out.print("Opcao: ");
                opcao = sc.nextInt();

                if (opcao == 1) {
                    ArrayList<AtividadeElegivel> catalogo = sistema.getCatalogoAtividades();
                    if (catalogo.isEmpty()) {
                        System.out.println("Ainda nao ha atividades elegiveis registadas.");
                        continue;
                    }
                    for (AtividadeElegivel a : catalogo) {
                        System.out.println(a.getIdAtividade() + " - " + a.getNome() + " (" + a.getTipo().getDesignacao() + ") disponivel: " + a.isDisponivel());
                    }
                    System.out.print("ID da atividade elegivel: ");
                    int id = sc.nextInt();
                    AtividadeElegivel escolhida = null;
                    for (AtividadeElegivel a : catalogo) {
                        if (a.getIdAtividade() == id) escolhida = a;
                    }
                    if (escolhida == null) {
                        System.out.println("Atividade nao encontrada.");
                        continue;
                    }
                    System.out.print("Carga horaria realizada: ");
                    int cargaHoraria = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Data de realizacao (dd-mm-yyyy): ");
                    String dataTexto = sc.nextLine();
                    LocalDate data;
                    try {
                        data = LocalDate.parse(dataTexto, formato);
                    } catch (Exception e) {
                        System.out.println("Data invalida, a usar a data de hoje.");
                        data = LocalDate.now();
                    }
                    System.out.print("Comprovativo: ");
                    String comprovativo = sc.nextLine();
                    sistema.registarAtividadeEstudante(estudante, escolhida, data, cargaHoraria, comprovativo);
                } else if (opcao == 2) {
                    for (AtividadeRealizada ar : estudante.consultarEstadoAtividades()) {
                        String linha = ar.getDataRealizacao() + " - " + ar.getAtividadeElegivel().getNome() +
                                " (" + ar.getAtividadeElegivel().getTipo().getDesignacao() + ") - " +
                                ar.getCargaHorariaRealizada() + "h - " + ar.getEstado();
                        if (ar.getEstado() == EstadoValidacao.REJEITADA && ar.getObservacao() != null) {
                            linha += " (Motivo: " + ar.getObservacao() + ")";
                        }
                        System.out.println(linha);
                    }
                }
            } catch (Exception e) {
                System.out.println("Entrada invalida. Tenta novamente.");
                sc.nextLine();
            }
        }
    }

    private static void menuGestor(Scanner sc, SistemaSuplemento sistema, Utilizador gestor, ArrayList<Tipo> tipos) {
        int opcao = -1;
        while (opcao != 0) {
            try {
                System.out.println("\n--- Menu Gestor ---");
                System.out.println("1 - Criar atividade elegivel");
                System.out.println("2 - Inativar atividade elegivel");
                System.out.println("3 - Validar atividade realizada");
                System.out.println("4 - Ver estatisticas por tipo");
                System.out.println("5 - Gerar componente do Suplemento ao Diploma");
                System.out.println("6 - Editar atividade elegivel");
                System.out.println("7 - Pesquisar atividades por tipo e estudante");
                System.out.println("0 - Sair");
                System.out.print("Opcao: ");
                opcao = sc.nextInt();

                if (opcao == 1) {
                    sc.nextLine();
                    System.out.print("Nome da atividade: ");
                    String nome = sc.nextLine();
                    for (int i = 0; i < tipos.size(); i++) {
                        System.out.println(i + " - " + tipos.get(i).getDesignacao());
                    }
                    System.out.print("Tipo (indice): ");
                    int indiceTipo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Entidade responsavel: ");
                    String entidade = sc.nextLine();
                    System.out.print("Carga horaria minima: ");
                    int cargaMin = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Descricao: ");
                    String descricao = sc.nextLine();
                    sistema.criarAtividadeElegivel(nome, tipos.get(indiceTipo), entidade, cargaMin, descricao);
                } else if (opcao == 2) {
                    System.out.print("ID da atividade a inativar: ");
                    int id = sc.nextInt();
                    sistema.inativarAtividadeElegivel(id);
                } else if (opcao == 3) {
                    System.out.print("Numero do estudante: ");
                    int numeroEstudante = sc.nextInt();
                    Estudante estudante = null;
                    for (Utilizador u : sistema.getUtilizadores()) {
                        if (u instanceof Estudante && u.getNumero() == numeroEstudante) {
                            estudante = (Estudante) u;
                        }
                    }
                    if (estudante == null) {
                        System.out.println("Estudante nao encontrado.");
                        continue;
                    }
                    for (AtividadeRealizada ar : estudante.consultarEstadoAtividades()) {
                        if (ar.getEstado() == EstadoValidacao.PENDENTE) {
                            System.out.println(ar.getIdRegisto() + " - " + ar.getAtividadeElegivel().getNome());
                        }
                    }
                    System.out.print("ID do registo a validar: ");
                    int idRegisto = sc.nextInt();
                    AtividadeRealizada escolhida = null;
                    for (AtividadeRealizada ar : estudante.consultarEstadoAtividades()) {
                        if (ar.getIdRegisto() == idRegisto) escolhida = ar;
                    }
                    if (escolhida == null) {
                        System.out.println("Registo nao encontrado.");
                        continue;
                    }
                    System.out.print("Validar (V) ou Rejeitar (R)? ");
                    sc.nextLine();
                    String resposta = sc.nextLine();
                    System.out.print("Observacao: ");
                    String observacao = sc.nextLine();
                    EstadoValidacao novoEstado = resposta.equalsIgnoreCase("V") ? EstadoValidacao.VALIDADA : EstadoValidacao.REJEITADA;
                    sistema.validarAtividade(gestor, escolhida, novoEstado, observacao);
                } else if (opcao == 4) {
                    sistema.getEstatisticasPorTipo();
                } else if (opcao == 5) {
                    System.out.print("Numero do estudante: ");
                    int numEst = sc.nextInt();
                    Estudante est = null;
                    for (Utilizador u : sistema.getUtilizadores()) {
                        if (u instanceof Estudante && u.getNumero() == numEst) est = (Estudante) u;
                    }
                    if (est == null) {
                        System.out.println("Estudante nao encontrado.");
                        continue;
                    }
                    ArrayList<AtividadeRealizada> componente = sistema.gerarComponenteSD(est);
                    if (componente.isEmpty()) {
                        System.out.println("Este estudante ainda nao tem atividades validadas.");
                    } else {
                        System.out.println("--- Componente do Suplemento ao Diploma ---");
                        System.out.println("Estudante: " + est.getNome() + " | Curso: " + est.getCurso());
                        for (AtividadeRealizada ar : componente) {
                            System.out.println(ar.getAtividadeElegivel().getNome() + " - " + ar.getAtividadeElegivel().getTipo().getDesignacao() +
                                    " - " + ar.getAtividadeElegivel().getEntidadeResponsavel() + " - " + ar.getCargaHorariaRealizada() + "h");
                        }
                    }
                } else if (opcao == 6) {
                    System.out.print("ID da atividade a editar: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Novo nome: ");
                    String nome = sc.nextLine();
                    for (int i = 0; i < tipos.size(); i++) {
                        System.out.println(i + " - " + tipos.get(i).getDesignacao());
                    }
                    System.out.print("Novo tipo (indice): ");
                    int indiceTipo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nova entidade responsavel: ");
                    String entidade = sc.nextLine();
                    System.out.print("Nova carga horaria minima: ");
                    int cargaMin = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nova descricao: ");
                    String descricao = sc.nextLine();
                    sistema.editarAtividadeElegivel(id, nome, tipos.get(indiceTipo), entidade, cargaMin, descricao);
                } else if (opcao == 7) {
                    System.out.print("Numero do estudante: ");
                    int numEst = sc.nextInt();
                    Estudante est = null;
                    for (Utilizador u : sistema.getUtilizadores()) {
                        if (u instanceof Estudante && u.getNumero() == numEst) est = (Estudante) u;
                    }
                    if (est == null) {
                        System.out.println("Estudante nao encontrado.");
                        continue;
                    }
                    for (int i = 0; i < tipos.size(); i++) {
                        System.out.println(i + " - " + tipos.get(i).getDesignacao());
                    }
                    System.out.print("Tipo (indice): ");
                    int indiceTipo = sc.nextInt();
                    System.out.print("Mostrar apenas validadas? (S/N): ");
                    sc.nextLine();
                    String resposta = sc.nextLine();
                    boolean apenasValidadas = resposta.equalsIgnoreCase("S");
                    ArrayList<AtividadeRealizada> resultado = sistema.pesquisarPorTipoEEstudante(est, tipos.get(indiceTipo), apenasValidadas);
                    if (resultado.isEmpty()) {
                        System.out.println("Nenhuma atividade encontrada.");
                    } else {
                        for (AtividadeRealizada ar : resultado) {
                            System.out.println(ar.getDataRealizacao() + " - " + ar.getAtividadeElegivel().getNome() + " - " + ar.getEstado());
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Entrada invalida. Tenta novamente.");
                sc.nextLine();
            }
        }
    }
}