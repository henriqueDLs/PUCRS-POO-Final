public class EspaconaveSubLuz extends Espaconave{
    private double velMaxImp;
    private String combustivel;

    public EspaconaveSubLuz(String nome, double velMaxImp, String combustivel) {
        super(nome);
        if(velMaxImp<=0.3){this.velMaxImp = velMaxImp;}
        else{this.velMaxImp=-1;}
        this.combustivel = combustivel;
    }

    public double getVelMaxImp() {
        return velMaxImp;
    }

    public void setVelMaxImp(double velMaxImp) {
        this.velMaxImp = velMaxImp;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }
}
