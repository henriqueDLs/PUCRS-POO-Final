import java.util.ArrayList;
import java.util.Scanner;

public class CatalogoEspaconaves {
    private ArrayList<Espaconave> frota;
    private ArrayList<Espaconave> total;
    private Scanner sc;

    public CatalogoEspaconaves(){
        frota = new ArrayList<Espaconave>();
        total = new ArrayList<Espaconave>();
    }

    public boolean cadastraEspacoNave(Espaconave a){
        try{
            for(Espaconave e : frota){
                if(e.getNome().equals(a.getNome())){
                    return false;
                }
            }
            frota.add(a);
            total.add(a);
            return true;
        }catch(Exception e){
            System.out.println("ERRO: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Espaconave> getTotal() {
        return total;
    }

    public void clear(){
        frota.clear();
    }

    public ArrayList<Espaconave> getFrota() {
        return frota;
    }

    public Espaconave getEspNome(String nome){
        Espaconave t = null;
        for(Espaconave ep : frota){
            if(ep.getNome().equals(nome)){
                t = ep;
            }
        }
        return t;
    }
}
