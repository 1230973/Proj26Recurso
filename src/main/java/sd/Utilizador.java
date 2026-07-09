package sd;

public class Utilizador {
    private String nome;
    private String email;
    private int numero;
    private String password;

    public Utilizador(String nome, String email, int numero, String password) {
        this.nome = nome;
        this.email = email;
        this.numero = numero;
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumero() {
        return numero;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}