package sd;

import java.time.LocalDate;

public class AtividadeRealizada {
    private int idRegisto;
    private AtividadeElegivel atividadeElegivel;
    private LocalDate dataRealizacao;
    private int cargaHorariaRealizada;
    private EstadoValidacao estado;
    private Utilizador validadoPor;

    public AtividadeRealizada(int idRegisto, AtividadeElegivel atividadeElegivel, LocalDate dataRealizacao, int cargaHorariaRealizada) {
        this.idRegisto = idRegisto;
        this.atividadeElegivel = atividadeElegivel;
        this.dataRealizacao = dataRealizacao;
        this.cargaHorariaRealizada = cargaHorariaRealizada;
        this.estado = EstadoValidacao.PENDENTE;
    }

    public int getIdRegisto() {
        return idRegisto;
    }

    public AtividadeElegivel getAtividadeElegivel() {
        return atividadeElegivel;
    }

    public LocalDate getDataRealizacao() {
        return dataRealizacao;
    }

    public int getCargaHorariaRealizada() {
        return cargaHorariaRealizada;
    }

    public EstadoValidacao getEstado() {
        return estado;
    }

    public void setEstado(EstadoValidacao estado) {
        this.estado = estado;
    }

    public Utilizador getValidadoPor() {
        return validadoPor;
    }

    public void setValidadoPor(Utilizador validadoPor) {
        this.validadoPor = validadoPor;
    }
}