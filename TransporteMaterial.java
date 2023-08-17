public class TransporteMaterial extends Transporte{
    private String descMat;
    private double carga;

    public TransporteMaterial(int identificador, EspacoPorto origem, EspacoPorto destino,double carga,String descMat) {
        super(identificador, origem, destino);
        this.descMat = descMat;
        this.carga = carga;
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
        double custoTransportado = carga*500;
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

    public String getDescMat() {
        return descMat;
    }

    public void setDescMat(String descMat) {
        this.descMat = descMat;
    }

    public double getCarga() {
        return carga;
    }

    public void setCarga(double carga) {
        this.carga = carga;
    }
}
