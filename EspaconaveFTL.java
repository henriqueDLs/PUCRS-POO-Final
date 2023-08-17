public class EspaconaveFTL extends Espaconave{
    private double velMaxWarp;
    private int limiteTransp;

    public EspaconaveFTL(String nome, double velMaxWarp, int limiteTransp){
        super(nome);
        this.velMaxWarp = velMaxWarp;
        this.limiteTransp = limiteTransp;
    }

    public double getVelMaxWarp() {
        return velMaxWarp;
    }

    public void setVelMaxWarp(double velMaxWarp) {
        this.velMaxWarp = velMaxWarp;
    }

    public int getLimiteTransp() {
        return limiteTransp;
    }

    public void setLimiteTransp(int limiteTransp) {
        this.limiteTransp = limiteTransp;
    }
}
