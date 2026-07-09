package sd;

public class AtividadeElegivel {
    private int idAtividade;
    private String nome;
    private Tipo tipo;
    private String entidadeResponsavel;
    private int cargaHorariaMinima;
    private boolean disponivel;

    public AtividadeElegivel(int idAtividade, String nome, Tipo tipo, String entidadeResponsavel, int cargaHorariaMinima) {
        this.idAtividade = idAtividade;
        this.nome = nome;
        this.tipo = tipo;
        this.entidadeResponsavel = entidadeResponsavel;
        this.cargaHorariaMinima = cargaHorariaMinima;
        this.disponivel = true;
    }

    public int getIdAtividade() {
        return idAtividade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getEntidadeResponsavel() {
        return entidadeResponsavel;
    }

    public int getCargaHorariaMinima() {
        return cargaHorariaMinima;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}