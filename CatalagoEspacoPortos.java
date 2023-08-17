import java.util.ArrayList;
import java.util.Scanner;

public class CatalagoEspacoPortos {
    private ArrayList<EspacoPorto> conjunto;
    private Scanner sc;

    public CatalagoEspacoPortos(){
        conjunto = new ArrayList<EspacoPorto>();
    }

    public boolean cadastraEspacoPorto(EspacoPorto a){
        try{
            for(EspacoPorto ep:conjunto){
                if(ep.getNumero() == a.getNumero()){
                    return false;
                }
            }
            conjunto.add(a);
            return true;
        }catch(Exception e){
            System.out.println("ERRO" + e.getMessage());
            return false;
        }
    }

    public void clear(){
        conjunto.clear();
    }

    public ArrayList<EspacoPorto> getConjunto() {
        return conjunto;
    }

    public EspacoPorto getEspacoPortoID(int id){
        EspacoPorto e = null;
        for(EspacoPorto ep : conjunto){
            if(ep.getNumero() == id){
                e = ep;
            }
        }
        return e;
    }
}
