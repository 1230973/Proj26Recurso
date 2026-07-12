package sd;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

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
            System.out.print("\nNumero (ou 0 para terminar o programa): ");
            int numero = sc.nextInt();

            if (numero == 0) {
                continuar = false;
                continue;
            }

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
        }

        System.out.println("Programa terminado.");
    }

    private static void menuEstudante(Scanner sc, SistemaSuplemento sistema, Estudante estudante, ArrayList<Tipo> tipos) {
        int opcao = -1;
        while (opcao != 0) {
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
                System.out.print("Comprovativo: ");
                String comprovativo = sc.nextLine();
                sistema.registarAtividadeEstudante(estudante, escolhida, LocalDate.now(), cargaHoraria, comprovativo);
            } else if (opcao == 2) {
                for (AtividadeRealizada ar : estudante.consultarEstadoAtividades()) {
                    System.out.println(ar.getIdRegisto() + " - " + ar.getAtividadeElegivel().getNome() + " - " + ar.getEstado());
                }
            }
        }
    }

    private static void menuGestor(Scanner sc, SistemaSuplemento sistema, Utilizador gestor, ArrayList<Tipo> tipos) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Menu Gestor ---");
            System.out.println("1 - Criar atividade elegivel");
            System.out.println("2 - Inativar atividade elegivel");
            System.out.println("3 - Validar atividade realizada");
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
                sistema.criarAtividadeElegivel(nome, tipos.get(indiceTipo), entidade, cargaMin);
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
            }
        }
    }
}