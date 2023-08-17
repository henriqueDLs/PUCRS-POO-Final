import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Queue;

public class Dados {
    private CatalogoEspaconaves cE = new CatalogoEspaconaves();
    private CatalagoEspacoPortos cEP = new CatalagoEspacoPortos();
    private CatalogoTransportes cT = new CatalogoTransportes();
    private ArrayList<Espaconave> cEClone = cE.getFrota();
    private Transporte t1;
    private Espaconave e1;

    public boolean carregarDadosIniciais(String nomeArquivo) {
        try {
            File dir = new File(".");
            File[] foundFiles = dir.listFiles((dir1, name) -> name.startsWith(nomeArquivo));
            if (foundFiles == null) {
                return false;
            }
            for (File a : foundFiles) {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(a), "ISO-8859-1"));
                String linha = "";
                String linha1 = br.readLine();
                String[] linha1sep = linha1.split(":");
                if (linha1sep[2].equals("x")) {
                    while ((linha = br.readLine()) != null) {
                        linha = Normalizer.normalize(linha, Normalizer.Form.NFKD);
                        linha = linha.replaceAll("[^\\p{ASCII}]", "");
                        String[] linhaAr = linha.split(":");
                        double x = Double.parseDouble(linhaAr[2]);
                        double y = Double.parseDouble(linhaAr[3]);
                        double z = Double.parseDouble(linhaAr[4]);
                        EspacoPorto ep = new EspacoPorto(Integer.parseInt(linhaAr[0]), linhaAr[1], x, y, z);
                        cEP.cadastraEspacoPorto(ep);
                    }
                    br.close();
                }
            }
            for (File e : foundFiles) {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(e), "ISO-8859-1"));
                String linha = "";
                String linha1 = br.readLine();
                String[] linha1sep = linha1.split(":");
                if (linha1sep[2].equals("espacoporto")) {
                    while ((linha = br.readLine()) != null) {
                        linha = Normalizer.normalize(linha, Normalizer.Form.NFKD);
                        linha = linha.replaceAll("[^\\p{ASCII}]", "");
                        String[] linhaAr = linha.split(":");
                        Espaconave t;
                        if (Integer.parseInt(linhaAr[0]) == 1) {
                            t = new EspaconaveSubLuz(linhaAr[1], Double.parseDouble(linhaAr[3]), linhaAr[4]);
                        } else {
                            t = new EspaconaveFTL(linhaAr[1], Double.parseDouble(linhaAr[3]), Integer.parseInt(linhaAr[4]));
                        }
                        if(cEP.getEspacoPortoID(Integer.parseInt(linhaAr[2]))==null){
                            t.setLocalAtual(new EspacoPorto(11,"Terra",0.00,0.00,0.00));
                        }
                        else{t.setLocalAtual(cEP.getEspacoPortoID(Integer.parseInt(linhaAr[2])));}
                        if(t instanceof EspaconaveSubLuz){
                            if(((EspaconaveSubLuz) t).getVelMaxImp()==-1){}
                            else cE.cadastraEspacoNave(t);
                        }
                        else cE.cadastraEspacoNave(t);
                    }
                    br.close();
                }
                if (linha1sep[2].equals("origem")) {
                    while ((linha = br.readLine()) != null) {
                        linha = Normalizer.normalize(linha, Normalizer.Form.NFKD);
                        linha = linha.replaceAll("[^\\p{ASCII}]", "");
                        String[] linhaAr = linha.split(":");
                        Transporte t;
                        if (Integer.parseInt(linhaAr[0]) == 1) {
                            t = new TransportePessoa(Integer.parseInt(linhaAr[1]), cEP.getEspacoPortoID(Integer.parseInt(linhaAr[2])), cEP.getEspacoPortoID(Integer.parseInt(linhaAr[3])), Integer.parseInt(linhaAr[4]));
                        } else {
                            t = new TransporteMaterial(Integer.parseInt(linhaAr[1]), cEP.getEspacoPortoID(Integer.parseInt(linhaAr[2])), cEP.getEspacoPortoID(Integer.parseInt(linhaAr[3])), Double.parseDouble(linhaAr[4]), linhaAr[5]);
                        }
                        cT.cadastraTransporte(t);
                    }
                    br.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean salvarDados(String nomeArquivo, int opcao) {
        String linha = "";
        try {
            FileWriter fw = null;
            if (opcao == 1) {
                fw = new FileWriter("" + nomeArquivo + ".txt");
            }
            if (opcao == 2) {
                fw = new FileWriter("" + nomeArquivo + ".csv");
            }
            if (opcao == 3) {
                fw = new FileWriter("" + nomeArquivo + ".dat");
            }
            BufferedWriter bw = new BufferedWriter(fw);
            if (!cEP.getConjunto().isEmpty()) {
                if (opcao == 1 || opcao == 3) {
                    bw.write("numero;nome;x;y;z");
                    bw.newLine();
                }
                if (opcao == 2) {
                    bw.write("numero,nome,x,y,z");
                    bw.newLine();
                }
            }
            for (EspacoPorto ep : cEP.getConjunto()) {
                if (opcao == 1) {
                    linha = "" + ep.getNumero() + ";" + ep.getNome() + ";" + ep.getCoordX() + ";" + ep.getCoordY() + ";" + ep.getCoordZ();
                }
                if (opcao == 2) {
                    linha = "" + ep.getNumero() + "," + ep.getNome() + "," + ep.getCoordX() + "," + ep.getCoordY() + "," + ep.getCoordZ();
                }
                if (opcao == 3) {
                    linha = "" + ep.getNumero() + ";" + ep.getNome() + ";" + ep.getCoordX() + ";" + ep.getCoordY() + ";" + ep.getCoordZ();
                }
                bw.write(linha);
                bw.newLine();
            }
            if (!cE.getFrota().isEmpty()) {
                if (opcao == 1 || opcao == 3) {
                    bw.write("tipo;nome;espacoporto;velocidade;combustivel_limite");
                    bw.newLine();
                }
                if (opcao == 2) {
                    bw.write("tipo,nome,espacoporto,velocidade,combustivel_limite");
                    bw.newLine();
                }
            }
            for (Espaconave e : cE.getTotal()) {
                if (e instanceof EspaconaveFTL) {
                    if (opcao == 1) {
                        linha = "" + 2 + ";" + e.getNome() + ";" + e.getLocalAtual().getNumero() + ";" + ((EspaconaveFTL) e).getVelMaxWarp() + ";" + ((EspaconaveFTL) e).getLimiteTransp();
                    }
                    if (opcao == 2) {
                        linha = "" + 2 + "," + e.getNome() + "," + e.getLocalAtual().getNumero() + "," + ((EspaconaveFTL) e).getVelMaxWarp() + "," + ((EspaconaveFTL) e).getLimiteTransp();
                    }
                    if (opcao == 3) {
                        linha = "" + 2 + ";" + e.getNome() + ";" + e.getLocalAtual().getNumero() + ";" + ((EspaconaveFTL) e).getVelMaxWarp() + ";" + ((EspaconaveFTL) e).getLimiteTransp();
                    }
                    bw.write(linha);
                    bw.newLine();
                } else if (e instanceof EspaconaveSubLuz) {
                    if (opcao == 1) {
                        linha = "" + 1 + ";" + e.getNome() + ";" + e.getLocalAtual().getNumero() + ";" + ((EspaconaveSubLuz) e).getVelMaxImp() + ";" + ((EspaconaveSubLuz) e).getCombustivel();
                    }
                    if (opcao == 2) {
                        linha = "" + 1 + "," + e.getNome() + "," + e.getLocalAtual().getNumero() + "," + ((EspaconaveSubLuz) e).getVelMaxImp() + "," + ((EspaconaveSubLuz) e).getCombustivel();
                    }
                    if (opcao == 3) {
                        linha = "" + 1 + ";" + e.getNome() + ";" + e.getLocalAtual().getNumero() + ";" + ((EspaconaveSubLuz) e).getVelMaxImp() + ";" + ((EspaconaveSubLuz) e).getCombustivel();
                    }
                    bw.write(linha);
                    bw.newLine();
                }
            }
            if (!cT.getFilaTransportesPEN().isEmpty()) {
                if (opcao == 1 || opcao == 3) {
                    bw.write("tipo;identificador;origem;destino;quantidade_carga;descricao");
                    bw.newLine();
                }
                if (opcao == 2) {
                    bw.write("tipo,identificador,origem,destino,quantidade_carga,descricao");
                    bw.newLine();
                }
            }
            for (Transporte t : cT.getTodosTransp()) {
                if (t instanceof TransportePessoa) {
                    if (opcao == 1) {
                        linha = "" + 1 + ";" + t.getIdentificador() + ";" + t.getOrigem().getNumero() + ";" + t.getDestino().getNumero() + ";" + ((TransportePessoa) t).getQtdPessoasTransp();
                    }
                    if (opcao == 2) {
                        linha = "" + 1 + "," + t.getIdentificador() + "," + t.getOrigem().getNumero() + "," + t.getDestino().getNumero() + "," + ((TransportePessoa) t).getQtdPessoasTransp();
                    }
                    if (opcao == 3) {
                        linha = "" + 1 + ";" + t.getIdentificador() + ";" + t.getOrigem().getNumero() + ";" + t.getDestino().getNumero() + ";" + ((TransportePessoa) t).getQtdPessoasTransp();
                    }
                    bw.write(linha);
                    bw.newLine();
                } else if (t instanceof TransporteMaterial) {
                    if (opcao == 1) {
                        linha = "" + 2 + ";" + t.getIdentificador() + ";" + t.getOrigem().getNumero() + ";" + t.getDestino().getNumero() + ";" + ((TransporteMaterial) t).getCarga() + ";" + ((TransporteMaterial) t).getDescMat();
                    }
                    if (opcao == 2) {
                        linha = "" + 2 + "," + t.getIdentificador() + "," + t.getOrigem().getNumero() + "," + t.getDestino().getNumero() + "," + ((TransporteMaterial) t).getCarga() + "," + ((TransporteMaterial) t).getDescMat();
                    }
                    if (opcao == 3) {
                        linha = "" + 2 + ";" + t.getIdentificador() + ";" + t.getOrigem().getNumero() + ";" + t.getDestino().getNumero() + ";" + ((TransporteMaterial) t).getCarga() + ";" + ((TransporteMaterial) t).getDescMat();
                    }
                    bw.write(linha);
                    bw.newLine();
                }
            }
            bw.close();
        } catch (IOException e) {
            System.err.println("Erro: " + e);
            return false;
        }
        return true;
    }

    public boolean carregarDadosTXTDAT(String nomeArquivo, int opcao) {
        try {
            BufferedReader br = null;
            if (opcao == 1) {
                br = new BufferedReader(new InputStreamReader(new FileInputStream("" + nomeArquivo + ".txt"), StandardCharsets.ISO_8859_1));
            } else if (opcao == 3) {
                br = new BufferedReader(new InputStreamReader(new FileInputStream("" + nomeArquivo + ".dat"), StandardCharsets.ISO_8859_1));
            }
            String linha = "";
            String linha1 = br.readLine();
            if (linha1.split(";")[2].equals("x")) {
                while ((linha = br.readLine()) != null) {
                    linha = Normalizer.normalize(linha, Normalizer.Form.NFKD);
                    linha = linha.replaceAll("[^\\p{ASCII}]", "");
                    String[] linhaAr1 = linha.split(";");
                    if (linha.split(";")[2].equals("espacoporto")) {
                        break;
                    }
                    EspacoPorto ep;
                    double x = Double.parseDouble(linhaAr1[2]);
                    double y = Double.parseDouble(linhaAr1[3]);
                    double z = Double.parseDouble(linhaAr1[4]);
                    ep = new EspacoPorto(Integer.parseInt(linhaAr1[0]), linhaAr1[1], x, y, z);
                    if (cEP.getEspacoPortoID(ep.getNumero()) == null) {
                        cEP.cadastraEspacoPorto(ep);
                    }
                }
            }
            while ((linha = br.readLine()) != null) {
                linha = Normalizer.normalize(linha, Normalizer.Form.NFKD);
                linha = linha.replaceAll("[^\\p{ASCII}]", "");
                String[] linhaAr1 = linha.split(";");
                if (linha.split(";")[2].equals("origem")) {
                    break;
                }
                Espaconave t;
                if (Integer.parseInt(linhaAr1[0]) == 1) {
                    t = new EspaconaveSubLuz(linhaAr1[1], Double.parseDouble(linhaAr1[3]), linhaAr1[4]);
                } else {
                    t = new EspaconaveFTL(linhaAr1[1], Double.parseDouble(linhaAr1[3]), Integer.parseInt(linhaAr1[4]));
                }
                t.setLocalAtual(cEP.getEspacoPortoID(Integer.parseInt(linhaAr1[2])));
                if(t instanceof EspaconaveSubLuz){
                    if(((EspaconaveSubLuz) t).getVelMaxImp()==-1){}
                    else cE.cadastraEspacoNave(t);
                }
                else cE.cadastraEspacoNave(t);
            }
            while ((linha = br.readLine()) != null) {
                linha = Normalizer.normalize(linha, Normalizer.Form.NFKD);
                linha = linha.replaceAll("[^\\p{ASCII}]", "");
                String[] linhaAr1 = linha.split(";");
                Transporte t;
                if (Integer.parseInt(linhaAr1[0]) == 1) {
                    t = new TransportePessoa(Integer.parseInt(linhaAr1[1]), cEP.getEspacoPortoID(Integer.parseInt(linhaAr1[2])), cEP.getEspacoPortoID(Integer.parseInt(linhaAr1[3])), Integer.parseInt(linhaAr1[4]));
                } else {
                    t = new TransporteMaterial(Integer.parseInt(linhaAr1[1]), cEP.getEspacoPortoID(Integer.parseInt(linhaAr1[2])), cEP.getEspacoPortoID(Integer.parseInt(linhaAr1[3])), Double.parseDouble(linhaAr1[4]), linhaAr1[5]);
                }
                if (cT.getTranspID(t.getIdentificador()) == null) {
                    cT.cadastraTransporte(t);
                }
            }
            br.close();
        }
        catch(IOException e){
            return false;
        }
        return true;
    }

    public boolean carregarDadosCSV(String nomeArquivo) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("" + nomeArquivo + ".csv"), StandardCharsets.ISO_8859_1));
            String linha = "";
            String linha1 = br.readLine();
            if (linha1.split(",")[2].equals("x")) {
                while ((linha = br.readLine()) != null) {
                    linha = Normalizer.normalize(linha, Normalizer.Form.NFKD);
                    linha = linha.replaceAll("[^\\p{ASCII}]", "");
                    String[] linhaAr1 = linha.split(",");
                    if (linha.split(",")[2].equals("espacoporto")) {
                        break;
                    }
                    EspacoPorto ep;
                    double x = Double.parseDouble(linhaAr1[2]);
                    double y = Double.parseDouble(linhaAr1[3]);
                    double z = Double.parseDouble(linhaAr1[4]);
                    ep = new EspacoPorto(Integer.parseInt(linhaAr1[0]), linhaAr1[1], x, y, z);
                    if (cEP.getEspacoPortoID(ep.getNumero()) == null) {
                        cEP.cadastraEspacoPorto(ep);
                    }
                }
            }
            while ((linha = br.readLine()) != null) {
                linha = Normalizer.normalize(linha, Normalizer.Form.NFKD);
                linha = linha.replaceAll("[^\\p{ASCII}]", "");
                String[] linhaAr1 = linha.split(",");
                if (linha.split(",")[2].equals("origem")) {
                    break;
                }
                Espaconave t;
                if (Integer.parseInt(linhaAr1[0]) == 1) {
                    t = new EspaconaveSubLuz(linhaAr1[1], Double.parseDouble(linhaAr1[3]), linhaAr1[4]);
                } else {
                    t = new EspaconaveFTL(linhaAr1[1], Double.parseDouble(linhaAr1[3]), Integer.parseInt(linhaAr1[4]));
                }
                t.setLocalAtual(cEP.getEspacoPortoID(Integer.parseInt(linhaAr1[2])));
                if(t instanceof EspaconaveSubLuz){
                    if(((EspaconaveSubLuz) t).getVelMaxImp()==-1){}
                    else cE.cadastraEspacoNave(t);
                }
                else cE.cadastraEspacoNave(t);
            }
            while ((linha = br.readLine()) != null) {
                linha = Normalizer.normalize(linha, Normalizer.Form.NFKD);
                linha = linha.replaceAll("[^\\p{ASCII}]", "");
                String[] linhaAr1 = linha.split(",");
                Transporte t;
                if (Integer.parseInt(linhaAr1[0]) == 1) {
                    t = new TransportePessoa(Integer.parseInt(linhaAr1[1]), cEP.getEspacoPortoID(Integer.parseInt(linhaAr1[2])), cEP.getEspacoPortoID(Integer.parseInt(linhaAr1[3])), Integer.parseInt(linhaAr1[4]));
                } else {
                    t = new TransporteMaterial(Integer.parseInt(linhaAr1[1]), cEP.getEspacoPortoID(Integer.parseInt(linhaAr1[2])), cEP.getEspacoPortoID(Integer.parseInt(linhaAr1[3])), Double.parseDouble(linhaAr1[4]), linhaAr1[5]);
                }
                if (cT.getTranspID(t.getIdentificador()) == null) {
                    cT.cadastraTransporte(t);
                }
            }
            br.close();
        }
        catch(IOException e){
            return false;
        }
        return true;
    }

    public boolean designarTransporte(){
        if(cEClone.isEmpty()){
            return false;
        }
        if(cT.getFilaTransportesPEN().isEmpty()){
            return false;
        }
        Transporte t = cT.getFilaTransportesPEN().element();
        if(t.getEstado().equals("PENDENTE")){
            for(Espaconave e : cEClone){
                if(e instanceof EspaconaveFTL){
                    if(t instanceof TransportePessoa) {
                        if (((TransportePessoa) t).getQtdPessoasTransp() <= ((EspaconaveFTL) e).getLimiteTransp()) {
                            t1 = t;
                            e1 = e;
                            t.setEstado("TRANSPORTANDO");
                            cT.getFilaTransportesPEN().remove();
                            cEClone.remove(e);
                            return true;
                        }
                        break;
                    }
                    if(t instanceof TransporteMaterial) {
                        if (((TransporteMaterial) t).getCarga() <= ((EspaconaveFTL) e).getLimiteTransp()) {
                            t1 = t;
                            e1 = e;
                            t.setEstado("TRANSPORTANDO");
                            cT.getFilaTransportesPEN().remove();
                            cEClone.remove(e);
                            return true;
                        }
                        break;
                    }
                }
                else if(e instanceof EspaconaveSubLuz){
                    t1 = t;
                    e1 = e;
                    t.setEstado("TRANSPORTANDO");
                    cT.getFilaTransportesPEN().remove();
                    cEClone.remove(e);
                    return true;
                }
                else{
                    break;
                }
            }
        }
        else{
            cT.getFilaTransportesPEN().remove();
            if(cEClone.isEmpty()){return false;}
            designarTransporte();
        }
        return false;
    }

    public boolean cadastarEspPort(EspacoPorto e){
        return cEP.cadastraEspacoPorto(e);
    }

    public boolean cadastrarEsp(Espaconave e){
        return cE.cadastraEspacoNave(e);
    }

    public boolean cadastarTransp(Transporte e){
        return cT.cadastraTransporte(e);
    }

    public EspacoPorto getEspacoPortoID(int id){
        EspacoPorto e = null;
        for(EspacoPorto ep : cEP.getConjunto()){
            if(ep.getNumero() == id){
                e = ep;
            }
        }
        return e;
    }

    public Transporte getTransporteID(int id){
        return cT.getTransporteID(id);
    }

    public boolean AlterarEstado(int identificador, String estado){
        return cT.AlterarEstado(identificador,estado);
    }

    public Transporte getT1() {
        return t1;
    }

    public Espaconave getE1() {
        return e1;
    }

    public CatalogoEspaconaves getcE() {
        return cE;
    }

    public CatalagoEspacoPortos getcEP() {
        return cEP;
    }

    public CatalogoTransportes getcT() {
        return cT;
    }


}
