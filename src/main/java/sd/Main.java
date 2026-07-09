package sd;

public class Main {
    public static void main(String[] args) {
        SistemaSuplemento sistema = new SistemaSuplemento();

        sistema.registarEstudante("Tomas", "tomas@upt.pt", 1001, "1234", "Engenharia Informatica", 3);
        sistema.registarGestor("Paula Morais", "pmorais@upt.pt", 2001, "abcd");

        Utilizador u1 = sistema.login(1001, "1234");
        System.out.println(u1 != null ? "Login OK: " + u1.getNome() : "Login falhou");

        Utilizador u2 = sistema.login(1001, "errada");
        System.out.println(u2 != null ? "Login OK: " + u2.getNome() : "Login falhou (esperado)");

        Estudante duplicado = sistema.registarEstudante("Outro", "outro@upt.pt", 1001, "5678", "Gestao", 1);
        System.out.println(duplicado == null ? "Registo duplicado bloqueado (esperado)" : "Erro: deixou registar duplicado");
    }
}