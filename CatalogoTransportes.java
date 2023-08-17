import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class CatalogoTransportes {
    private Queue<Transporte> filaTransportesPEN;
    private ArrayList<Transporte> todosTransp;
    private Scanner sc;

    public CatalogoTransportes(){
        filaTransportesPEN = new LinkedList<>();
        todosTransp = new ArrayList<>();
    };

    public boolean cadastraTransporte(Transporte a){
        try{
            for(Transporte t : filaTransportesPEN){
                if(t.getIdentificador() == a.getIdentificador()){
                    return false;
                }
            }
            filaTransportesPEN.add(a);
            todosTransp.add(a);
            return true;
        }catch(Exception e){
            System.out.println("ERRO: " + e.getMessage());
            return false;
        }
    }

    public boolean AlterarEstado(int identificador, String estado){
        Transporte transporte = getTransporteID(identificador);
        if(estado.toUpperCase().equals("CANCELADO")){transporte.setEstado(estado.toUpperCase()); return true;}
        if(estado.toUpperCase().equals("FINALIZADO")){transporte.setEstado(estado.toUpperCase()); return true;}
        if(transporte.getEstado().equals("CANCELADO")||transporte.getEstado().equals("FINALIZADO")){
            return false;
        }
        else{
            transporte.setEstado(estado.toUpperCase());
            return true;
        }
    }

    public Transporte getTransporteID(int id){
        try{
            Transporte transporte = null;
            for(Transporte t : todosTransp){
                if(t.getIdentificador() == id){
                    transporte = t;
                }
            }
            return transporte;
        }catch(Exception e){
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
    }

    public void clear(){
        filaTransportesPEN.clear();
    }

    public Queue<Transporte> getFilaTransportesPEN() {
        return filaTransportesPEN;
    }

    public Transporte getTranspID(int id){
        Transporte t = null;
        for(Transporte ep : todosTransp){
            if(ep.getIdentificador() == id){
                t = ep;
            }
        }
        return t;
    }

    public ArrayList<Transporte> getTodosTransp() {
        return todosTransp;
    }
}
