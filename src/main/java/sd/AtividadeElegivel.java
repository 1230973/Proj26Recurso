package sd;

public class AtividadeElegivel {
    private int idAtividade;
    private String nome;
    private Tipo tipo;
    private String entidadeResponsavel;
    private int cargaHorariaMinima;
    private String descricao;
    private boolean disponivel;

    public AtividadeElegivel(int idAtividade, String nome, Tipo tipo, String entidadeResponsavel, int cargaHorariaMinima, String descricao) {
        this.idAtividade = idAtividade;
        this.nome = nome;
        this.tipo = tipo;
        this.entidadeResponsavel = entidadeResponsavel;
        this.cargaHorariaMinima = cargaHorariaMinima;
        this.descricao = descricao;
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

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getEntidadeResponsavel() {
        return entidadeResponsavel;
    }

    public void setEntidadeResponsavel(String entidadeResponsavel) {
        this.entidadeResponsavel = entidadeResponsavel;
    }

    public int getCargaHorariaMinima() {
        return cargaHorariaMinima;
    }

    public void setCargaHorariaMinima(int cargaHorariaMinima) {
        this.cargaHorariaMinima = cargaHorariaMinima;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}