import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Queue;
import java.util.Scanner;

public class ACMESpace {
    private Scanner entrada = new Scanner(System.in);
    private Dados d1 = new Dados();

    public void executa() {
        try {
            int opcao = 0;
            do {
                menu();
                opcao = entrada.nextInt();
                entrada.nextLine();
                switch (opcao) {
                    case 0:
                        cadastrarEspPort();
                        break;
                    case 1:
                        cadastrarEspac();
                        break;
                    case 2:
                        cadastrarTransp();
                        break;
                    case 3:
                        consultarTransp();
                        break;
                    case 4:
                        alterarEstTransp();
                        break;
                    case 5:
                        carregarDadosIn();
                        break;
                    case 6:
                        designarTransp();
                        break;
                    case 7:
                        salvarDados();
                        break;
                    case 8:
                        carregarDados();
                        break;
                    case 9:
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                        break;
                }
            }
            while (opcao != 9);
        }
        catch (InputMismatchException e){
            System.out.println("Valor invalido, diferente tipo de dado esperado!");
        }
    }

    private void menu() {
        System.out.println("====================================");
        System.out.println("Opcoes:");
        System.out.println("[0] Cadastrar novo espaco-porto");
        System.out.println("[1] Cadastrar nova espaconave");
        System.out.println("[2] Cadastrar novo transporte");
        System.out.println("[3] Consultar todos os transportes");
        System.out.println("[4] Alterar estado de um transporte");
        System.out.println("[5] Carregar dados iniciais");
        System.out.println("[6] Designar transporte");
        System.out.println("[7] Salvar dados");
        System.out.println("[8] Carregar dados");
        System.out.println("[9] Finalizar sistema");
    }

    private void cadastrarEspPort(){
        try {
            int numero = 0;
            String nome = "";
            double coordx = 0, coordy = 0, coordz = 0;
            System.out.println("Digite o numero do espaco-porto");
            numero = entrada.nextInt();
            entrada.nextLine();
            System.out.println("Digite o nome do espaco-porto");
            nome = entrada.nextLine();
            System.out.println("Digite a coordenada x do espaco-porto");
            coordx = entrada.nextDouble();
            System.out.println("Digite a coordenada y do espaco-porto");
            coordy = entrada.nextDouble();
            System.out.println("Digite a coordenada z do espaco-porto");
            coordz = entrada.nextDouble();
            EspacoPorto e = new EspacoPorto(numero, nome, coordx, coordy, coordz);
            boolean a = d1.getcEP().cadastraEspacoPorto(e);
            if (!a){
                System.out.println("Erro! Ja existe um espaco-porto com este numero!");
            }
            else {
                d1.getcEP().cadastraEspacoPorto(e);
                System.out.println("Espaco-porto cadastrado com sucesso!");
            }
        }
        catch(InputMismatchException e){
            System.out.println("Valor invalido, diferente tipo de dado esperado!");
        }
        catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void cadastrarEspac(){
        try {
            System.out.println("Deseja cadastrar uma espaconave FTL ou SubLuz? \n[1] FTL\n[2] SubLuz");
            int opcao = entrada.nextInt();
            entrada.nextLine();
            String nome = "";
            System.out.println("Digite o nome da espaconave");
            nome = entrada.nextLine();
            System.out.println("Digite o numero do espaco-porto");
            int numEspP = entrada.nextInt();
            entrada.nextLine();
            if(opcao==1){
                Espaconave FTL;
                System.out.println("Digite a velocidade maxima Warp");
                double vel = entrada.nextDouble();
                System.out.println("Digite o limite de transporte");
                int limite = entrada.nextInt();
                entrada.nextLine();
                if(!d1.cadastrarEsp(new EspaconaveFTL(nome,vel,limite))){
                    System.out.println("Erro! Ja existe uma espaconave com este nome!");
                }
                else{
                    FTL = new EspaconaveFTL(nome,vel,limite);
                    if(d1.getEspacoPortoID(numEspP)==null){
                        FTL.setLocalAtual(new EspacoPorto(11,"Terra",0.00,0.00,0.00));
                    }
                    else{
                        FTL.setLocalAtual(d1.getEspacoPortoID(numEspP));
                    }
                    d1.cadastrarEsp(FTL);
                }
            }
            else if(opcao==2){
                Espaconave SubLuz;
                System.out.println("Digite a velocidade maxima de impulso (limite: 0.3 Warp)");
                double vel = entrada.nextDouble();
                System.out.println("Digite o combustivel da espaconave (nuclear ou ion)");
                String comb = entrada.nextLine();
                entrada.nextLine();
                if(!d1.cadastrarEsp(new EspaconaveSubLuz(nome,vel,comb))){
                    System.out.println("Erro! Ja existe uma espaconave com este nome!");
                }
                else{
                    SubLuz = new EspaconaveSubLuz(nome,vel,comb);
                    if(d1.getEspacoPortoID(numEspP)==null){
                        SubLuz.setLocalAtual(new EspacoPorto(11,"Terra",0.00,0.00,0.00));
                    }
                    else{
                        SubLuz.setLocalAtual(d1.getEspacoPortoID(numEspP));
                    }
                    d1.cadastrarEsp(SubLuz);
                    System.out.println("Espaconave cadastrada com sucesso");
                }
            }
            else{
                System.out.println("Erro! Opcao invalida!");
                return;
            }
        }
        catch(InputMismatchException e){
            System.out.println("Valor invalido, diferente tipo de dado esperado!");
        }
        catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void cadastrarTransp(){
        try {
            System.out.println("Deseja cadastrar um transporte de pessoas ou materiais? [1] Pessoas [2] Materiais");
            int opcao = entrada.nextInt();
            entrada.nextLine();
            System.out.println("Digite o identificador do transporte");
            int identificador = entrada.nextInt();
            entrada.nextLine();
            System.out.println("Digite o numero do espaco-porto de origem");
            int numEspPO = entrada.nextInt();
            EspacoPorto origem = d1.getEspacoPortoID(numEspPO);
            if(origem==null){
                System.out.println("Nao existe um espaco porto com este numero!");
                return;
            }
            entrada.nextLine();
            System.out.println("Digite o numero do espaco-porto de destino");
            int numEspPD = entrada.nextInt();
            EspacoPorto destino = d1.getEspacoPortoID(numEspPD);
            if(destino==null){
                System.out.println("Nao existe um espaco porto com este numero!");
                return;
            }
            entrada.nextLine();
            if(opcao==1){
                Transporte t;
                System.out.println("Digite a quantidade de pessoas transportadas");
                int qtdP = entrada.nextInt();
                entrada.nextLine();
                if(!d1.cadastarTransp(new TransportePessoa(identificador,origem,destino,qtdP))){
                    System.out.println("Erro! Ja existe um transporte com este identificador!");
                }
                else{
                    t = new TransportePessoa(identificador,origem,destino,qtdP);
                    d1.cadastarTransp(t);
                    System.out.println("Transporte cadastrado com sucesso!");
                }
            }
            else if(opcao==2){
                Transporte t;
                System.out.println("Digite a descricao do material");
                String desc = entrada.nextLine();
                System.out.println("Digite a carga");
                double carga = entrada.nextDouble();
                if(!d1.cadastarTransp(new TransporteMaterial(identificador,origem,destino,carga,desc))){
                    System.out.println("Erro! Ja existe um transporte com este identificador!");
                }
                else{
                    t = new TransporteMaterial(identificador,origem,destino,carga,desc);
                    d1.cadastarTransp(t);
                }
            }
            else{
                System.out.println("Erro! Opcao invalida!");
                return;
            }
        }
        catch(InputMismatchException e){
            System.out.println("Valor invalido, diferente tipo de dado esperado!");
        }
        catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void consultarTransp(){
        try {
            ArrayList<Transporte> todosTransp = d1.getcT().getTodosTransp();
            for (Transporte t : todosTransp) {
                System.out.println("==========================");
                System.out.println("Identificador: " + t.getIdentificador());
                System.out.println("Origem: " + t.getOrigem().getNumero());
                System.out.println("Destino: " + t.getDestino().getNumero());
                System.out.println("Estado: " + t.getEstado());
                if (t instanceof TransportePessoa) {
                    System.out.println("Quantidade de pessoas: " + ((TransportePessoa) t).getQtdPessoasTransp());
                } else if (t instanceof TransporteMaterial) {
                    System.out.println("Descricao do material: " + ((TransporteMaterial) t).getDescMat());
                    System.out.println("Carga: " + ((TransporteMaterial) t).getCarga());
                }
            }
        }
        catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void alterarEstTransp(){
        try {
            System.out.println("Digite o identificador do transporte que deseja alterar");
            int identificador = entrada.nextInt();
            entrada.nextLine();
            Transporte t = d1.getTransporteID(identificador);
            if (t == null) {
                System.out.println("Nao foi encontrado nenhum transporte com este id!");
                return;
            } else {
                System.out.println("==========================");
                System.out.println("Identificador: " + t.getIdentificador());
                System.out.println("Origem: " + t.getOrigem().getNumero());
                System.out.println("Destino: " + t.getDestino().getNumero());
                System.out.println("Estado: " + t.getEstado());
                if (t instanceof TransportePessoa) {
                    System.out.println("Quantidade de pessoas: " + ((TransportePessoa) t).getQtdPessoasTransp());
                    System.out.println("==========================");
                } else if (t instanceof TransporteMaterial) {
                    System.out.println("Descricao do material: " + ((TransporteMaterial) t).getDescMat());
                    System.out.println("Carga: " + ((TransporteMaterial) t).getCarga());
                    System.out.println("==========================");
                }
            }
            System.out.println("Digite o novo estado (PENDENTE,TRANSPORTANDO,CANCELADO,FINALIZADO)");
            String estado = entrada.nextLine();
            if (!d1.AlterarEstado(identificador, estado)) {
                System.out.println("Erro! Este transporte ja foi CANCELADO ou FINALIZADO");
                return;
            } else {
                d1.AlterarEstado(identificador, estado);
                System.out.println("Alteracao de estado concluida!");
            }
        }
        catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void carregarDadosIn(){
        try {
            System.out.println("Digite o nome do arquivo");
            String nomeArquivo = entrada.nextLine();
            if (!d1.carregarDadosIniciais(nomeArquivo)) {
                System.out.println("Nao foi possivel carregar o arquivo!");
                return;
            } else {
                d1.carregarDadosIniciais(nomeArquivo);
                ArrayList<EspacoPorto> a = d1.getcEP().getConjunto();
                ArrayList<Espaconave> b = d1.getcE().getTotal();
                ArrayList<Transporte> c = d1.getcT().getTodosTransp();
                System.out.println();
                System.out.println("==========================");
                System.out.println("Dados dos espaco-portos");
                System.out.println("==========================");
                System.out.println();
                for (EspacoPorto ep : a) {
                    System.out.println("==========================");
                    System.out.println("Numero: " + ep.getNumero());
                    System.out.println("Nome: " + ep.getNome());
                    System.out.println("coordX: " + ep.getCoordX());
                    System.out.println("coordY: " + ep.getCoordY());
                    System.out.println("coordZ: " + ep.getCoordZ());
                }
                System.out.println();
                System.out.println("==========================");
                System.out.println("Dados das espaconaves");
                System.out.println("==========================");
                System.out.println();
                for (Espaconave e : b) {
                    System.out.println("==========================");
                    System.out.println("Nome: " + e.getNome());
                    System.out.println("Local atual: " + e.getLocalAtual().getNumero());
                    if (e instanceof EspaconaveFTL) {
                        System.out.println("Velocidade maxima WARP: " + ((EspaconaveFTL) e).getVelMaxWarp());
                        System.out.println("Limite de transporte: " + ((EspaconaveFTL) e).getLimiteTransp());
                    } else if (e instanceof EspaconaveSubLuz) {
                        System.out.println("Velocidade maxima de impulso: " + ((EspaconaveSubLuz) e).getVelMaxImp());
                        System.out.println("Combustivel: " + ((EspaconaveSubLuz) e).getCombustivel());
                    }
                }
                System.out.println();
                System.out.println("==========================");
                System.out.println("Dados dos transportes");
                System.out.println("==========================");
                System.out.println();
                for (Transporte t : c) {
                    System.out.println("==========================");
                    System.out.println("Identificador: " + t.getIdentificador());
                    System.out.println("Origem: " + t.getOrigem().getNumero());
                    System.out.println("Destino: " + t.getDestino().getNumero());
                    System.out.println("Estado: " + t.getEstado());
                    if (t instanceof TransportePessoa) {
                        System.out.println("Quantidade de pessoas: " + ((TransportePessoa) t).getQtdPessoasTransp());
                    } else if (t instanceof TransporteMaterial) {
                        System.out.println("Descricao do material: " + ((TransporteMaterial) t).getDescMat());
                        System.out.println("Carga: " + ((TransporteMaterial) t).getCarga());
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void designarTransp(){
        try {
            if (!d1.designarTransporte()) {
                System.out.println("Nao foi possivel designar o transporte desejado (espaconaves insuficientes ou todos transportes ja utilizados)");
                return;
            }
            Espaconave e = d1.getE1();
            Transporte t = d1.getT1();
            System.out.println("Dados do transporte designado:");
            System.out.println("==========================");
            System.out.println("Identificador: " + t.getIdentificador());
            System.out.println("Origem: " + t.getOrigem().getNumero());
            System.out.println("Destino: " + t.getDestino().getNumero());
            System.out.println("Estado: " + t.getEstado());
            if (t instanceof TransportePessoa) {
                System.out.println("Quantidade de pessoas: " + ((TransportePessoa) t).getQtdPessoasTransp());
            } else if (t instanceof TransporteMaterial) {
                System.out.println("Descricao do material: " + ((TransporteMaterial) t).getDescMat());
                System.out.println("Carga: " + ((TransporteMaterial) t).getCarga());
            }
            System.out.println();
            System.out.println("Dados da espaconave designada:");
            System.out.println("==========================");
            System.out.println("Nome: " + e.getNome());
            System.out.println("Local atual: " + e.getLocalAtual().getNumero());
            if (e instanceof EspaconaveFTL) {
                System.out.println("Velocidade maxima WARP: " + ((EspaconaveFTL) e).getVelMaxWarp());
                System.out.println("Limite de transporte: " + ((EspaconaveFTL) e).getLimiteTransp());
            } else if (e instanceof EspaconaveSubLuz) {
                System.out.println("Velocidade maxima de impulso: " + ((EspaconaveSubLuz) e).getVelMaxImp());
                System.out.println("Combustivel: " + ((EspaconaveSubLuz) e).getCombustivel());
            }
        }
        catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void salvarDados(){
        try {
            System.out.println("Deseja salvar em que tipo de arquivo? [1] txt [2] csv [3] dat");
            int opcao = entrada.nextInt();
            entrada.nextLine();
            System.out.println("Deseja salvar em quantos arquivos?");
            int opcaoArquivos = entrada.nextInt();
            entrada.nextLine();
            for (int i = 0; i < opcaoArquivos; i++) {
                System.out.println("Digite o nome do arquivo para salvar os dados:");
                String nomeArquivo = entrada.nextLine();
                if (!d1.salvarDados(nomeArquivo, opcao)) {
                    System.out.println("Nao foi possivel salvar os dados nesse arquivo!");
                    return;
                } else {
                    d1.salvarDados(nomeArquivo, opcao);
                    System.out.println("Dados salvos com sucesso!");
                }
            }
        }
        catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void carregarDados(){
        try{
        System.out.println("Deseja carregar um arquivo de que tipo? [1] txt [2] csv [3] dat");
        int opcao = entrada.nextInt();
        entrada.nextLine();
        System.out.println("Digite o nome do arquivo para carregar os dados");
        String nomeArquivo = entrada.nextLine();
        if(opcao == 2){
            if(!d1.carregarDadosCSV(nomeArquivo)){
                System.out.println("Nao foi possivel carregar os dados deste arquivo!");
                return;
            }
            else{
                d1.carregarDadosCSV(nomeArquivo);
                System.out.println("Dados carregados com sucesso!");
            }
        }
        else if(opcao == 1){
            if(!d1.carregarDadosTXTDAT(nomeArquivo,opcao)){
                System.out.println("Nao foi possivel carregar os dados deste arquivo!");
                return;
            }
            else{
                d1.carregarDadosTXTDAT(nomeArquivo,opcao);
                System.out.println("Dados carregados com sucesso!");
            }
        }
        else{
            if(!d1.carregarDadosTXTDAT(nomeArquivo,opcao)){
                System.out.println("Nao foi possivel carregar os dados deste arquivo!");
                return;
            }
            else{
                d1.carregarDadosTXTDAT(nomeArquivo,opcao);
                System.out.println("Dados carregados com sucesso!");
            }
          }
        }
        catch(Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
