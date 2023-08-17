public abstract class Transporte {

	private int identificador;

	private EspacoPorto origem;

	private EspacoPorto destino;

	private String estado;

	public Transporte(int identificador, EspacoPorto origem, EspacoPorto destino) {
		this.identificador = identificador;
		this.origem = origem;
		this.destino = destino;
		this.estado = "PENDENTE";
	}

	public abstract double calculaDistancia();

	public abstract double calculaCusto();

	public int getIdentificador() {
		return identificador;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}

	public EspacoPorto getOrigem() {
		return origem;
	}

	public void setOrigem(EspacoPorto origem) {
		this.origem = origem;
	}

	public EspacoPorto getDestino() {
		return destino;
	}

	public void setDestino(EspacoPorto destino) {
		this.destino = destino;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado.toUpperCase();
	}
}
