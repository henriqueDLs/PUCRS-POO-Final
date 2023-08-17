public class TransportePessoa extends Transporte{
    private int qtdPessoasTransp;

    public TransportePessoa(int identificador, EspacoPorto origem, EspacoPorto destino, int qtdPessoasTransp) {
        super(identificador, origem, destino);
        this.qtdPessoasTransp = qtdPessoasTransp;
    }

    public double calculaDistancia() {
        double x = Math.pow(2,super.getOrigem().getCoordX()-super.getDestino().getCoordX());
        double y = Math.pow(2,super.getOrigem().getCoordY()-super.getDestino().getCoordY());
        double z = Math.pow(2,super.getOrigem().getCoordZ()-super.getDestino().getCoordZ());
        double distancia = Math.sqrt(x+y+z);
        return distancia;
    }

    public double calculaCusto() {
        double distancia = calculaDistancia();
        double fatorDistancia = 0;
        if(distancia<0.5){fatorDistancia=1000000;}
        else{fatorDistancia=100;}
        double custoDistancia = distancia * fatorDistancia;
        double custoTransportado = qtdPessoasTransp*100;
        return custoDistancia*custoTransportado;
    }

    public int getIdentificador() {
        return super.getIdentificador();
    }

    public EspacoPorto getOrigem() {
        return super.getOrigem();
    }

    public EspacoPorto getDestino() {
        return super.getDestino();
    }

    public String getEstado() {
        return super.getEstado();
    }

    public void setIdentificador(int identificador) {
        super.setIdentificador(identificador);
    }

    public void setOrigem(EspacoPorto origem) {
        super.setOrigem(origem);
    }

    public void setDestino(EspacoPorto destino) {
        super.setDestino(destino);
    }

    public void setEstado(String estado) {
        super.setEstado(estado);
    }

    public int getQtdPessoasTransp() {
        return qtdPessoasTransp;
    }

    public void setQtdPessoasTransp(int qtdPessoasTransp) {
        this.qtdPessoasTransp = qtdPessoasTransp;
    }
}
