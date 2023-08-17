public class Espaconave {

	private String nome;

	private EspacoPorto localAtual;

	public Espaconave(String nome) {
		this.nome = nome;
		this.localAtual = new EspacoPorto(11,"Terra",0.00,0.00,0.00);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public EspacoPorto getLocalAtual() {
		return localAtual;
	}

	public void setLocalAtual(EspacoPorto localAtual) {
		this.localAtual = localAtual;
	}
}
