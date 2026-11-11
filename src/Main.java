import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    private static Usuario usuario = new Usuario();
    private static Impressora impressora = new Impressora();

    private static boolean conexaoConfigurada = false;
    private static boolean conexaoAberta = false;

    private static int tipo;
    private static String modelo;
    private static String porta;
    private static int parametro;

    private static int lerNumero(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Digite apenas números!");
            scanner.nextLine();
            System.out.print("Tente novamente: ");
        }

        int numero = scanner.nextInt();
        scanner.nextLine();

        return numero;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Controle de Login (com limite de tentativas)
        if (!usuario.realizarLogin(scanner)) {
            System.out.println("Acesso negado. Encerrando programa.");
            return;
        }

        int opcao = -1;

        // 2. Loop do Menu Principal
        while (opcao != 0) {
            exibirMenu();
            System.out.print("Digite a opção desejada: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer do teclado
            } else {
                System.out.println("Opção inválida!");
                scanner.nextLine(); // Limpar entrada incorreta
                continue;
            }

            // Validação de conexão aberta para funcionalidades de impressão/comando
            if (opcao >= 3 && opcao <= 13 && !conexaoAberta) {
                System.out.println("Conexão fechada. Configure (opção 1) e abra a conexão (opção 2) antes.\n");
                continue;
            }

            // Opções do menu
            switch (opcao) {
                case 1:
                    configurarConexao(scanner);
                    break;
                case 2:
                    abrirConexao();
                    break;
                case 3:
                    imprimirTexto(scanner);
                    break;
                case 4:
                    imprimirQRCode(scanner);
                    break;
                case 5:
                    imprimirCodigoDeBarras(scanner);
                    break;
                case 6:
                    imprimirXmlSAT();
                    break;
                case 7:
                    imprimirXmlCancelamentoSAT(scanner);
                    break;
                case 8:
                    abrirGavetaElgin();
                    break;
                case 9:
                    abrirGaveta(scanner);
                    break;
                case 10:
                    emitirSinalSonoro(scanner);
                    break;
                case 11:
                    avancarPapel(scanner);
                    break;
                case 12:
                    impressora.cortarPapel(0);
                    break;
                case 13:
                    verificarStatusImpressora();
                    break;
                case 0:
                    fecharConexaoESair();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println();
        }

        scanner.close();
    }

    // --- MENU ---

    private static void exibirMenu() {
        System.out.println("============ MENU IMPRESSORA ============");
        System.out.println(" 1 - Configurar conexão");
        System.out.println(" 2 - Abrir conexão");
        System.out.println(" 3 - Imprimir texto");
        System.out.println(" 4 - Imprimir QR Code");
        System.out.println(" 5 - Imprimir código de barras");
        System.out.println(" 6 - Imprimir XML SAT");
        System.out.println(" 7 - Imprimir XML de cancelamento SAT");
        System.out.println(" 8 - Abrir gaveta Elgin");
        System.out.println(" 9 - Abrir gaveta");
        System.out.println("10 - Sinal sonoro");
        System.out.println("11 - Avançar papel");
        System.out.println("12 - Cortar papel");
        System.out.println("13 - Status da impressora");
        System.out.println(" 0 - Fechar conexão e sair");
        System.out.println("=========================================");
    }

    // --- MÉTODOS DAS AÇÕES DA IMPRESSORA ---

    private static void configurarConexao(Scanner scanner) {
        if (conexaoAberta) {
            System.out.println("Feche a conexão antes de alterar os parâmetros.");
            return;
        }

        System.out.print("Tipo de conexão: ");
        tipo = lerNumero(scanner);

        if (tipo < 1 || tipo > 5) {
            System.out.println("Tipo de conexão inválido. Informe um valor de 1 a 5.");
            conexaoConfigurada = false;
            return;
        }

        System.out.print("Modelo da impressora: ");
        modelo = scanner.nextLine();

        System.out.print("Porta de conexão: ");
        porta = scanner.nextLine();

        System.out.print("Parâmetro: ");
        parametro = lerNumero(scanner);

        if (tipo == 1 && parametro != 0) {
            System.out.println("Para conexão USB, o parâmetro deve ser 0.");
            conexaoConfigurada = false;
            return;
        }

        if (modelo.trim().isEmpty() || porta.trim().isEmpty()) {
            conexaoConfigurada = false;
            System.out.println("Modelo e porta não podem ficar vazios.");
            return;
        }

        conexaoConfigurada = true;

        System.out.println("Parâmetros configurados com sucesso.");
    }

    private static void abrirConexao() {
        if (!conexaoConfigurada) {
            System.out.println("Configure os parâmetros primeiro.");
            return;
        }

        conexaoAberta = impressora.abrirConexao(
                tipo, modelo, porta, parametro);
    }

    private static void imprimirTexto(Scanner scanner) {
        System.out.print("Texto a ser impresso: ");
        String texto = scanner.nextLine();

        if (texto.trim().isEmpty()) {
            System.out.println("O texto não pode ficar vazio.");
            return;
        }

        System.out.print("Posição (0-esquerda, 1-centro, 2-direita): ");
        int posicao = lerNumero(scanner);

        if (posicao < 0 || posicao > 2) {
            System.out.println("A posição deve ser 0, 1 ou 2.");
            return;
        }

        System.out.print("Estilo (0 a 15): ");
        int estilo = lerNumero(scanner);

        if (estilo < 0 || estilo > 15) {
            System.out.println("O estilo deve estar entre 0 e 15.");
            return;
        }

        System.out.print("Tamanho: ");
        int tamanho = lerNumero(scanner);

        if (tamanho < 0 || tamanho > 119 ||
                tamanho % 16 > 7) {
            System.out.println("Tamanho inválido.");
            return;
        }

        System.out.print("Quantas cópias? ");
        int copias = lerNumero(scanner);

        if (copias <= 0) {
            System.out.println("A quantidade de cópias deve ser maior que zero.");
            return;
        }

        for (int i = 1; i <= copias; i++) {
            System.out.println("Impressão " + i + " de " + copias);
            impressora.imprimirTexto(texto + "\n", posicao, estilo, tamanho);
        }
    }

    private static void imprimirQRCode(Scanner scanner) {
        System.out.print("Conteúdo do QR Code: ");
        String conteudo = scanner.nextLine();

        if (conteudo.trim().isEmpty()) {
            System.out.println("O conteúdo do QR Code não pode ficar vazio.");
            return;
        }

        System.out.print("Tamanho (1 a 6): ");
        int tamanho = lerNumero(scanner);

        if (tamanho < 1 || tamanho > 6) {
            System.out.println("O tamanho deve estar entre 1 e 6.");
            return;
        }

        System.out.print("Nível de correção (1 a 4): ");
        int nivelCorrecao = lerNumero(scanner);

        if (nivelCorrecao < 1 || nivelCorrecao > 4) {
            System.out.println("O nível de correção deve estar entre 1 e 4.");
            return;
        }

        impressora.imprimirQRCode(conteudo, tamanho, nivelCorrecao);
    }

    private static void imprimirCodigoDeBarras(Scanner scanner) {
        System.out.print("Tipo (0 a 8): ");
        int tipoCodigo = lerNumero(scanner);

        if (tipoCodigo < 0 || tipoCodigo > 8) {
            System.out.println("O tipo deve estar entre 0 e 8.");
            return;
        }

        System.out.print("Código de barras: ");
        String codigo = scanner.nextLine();

        if (codigo.trim().isEmpty()) {
            System.out.println("O código de barras não pode ficar vazio.");
            return;
        }

        System.out.print("Altura (1 a 255): ");
        int altura = lerNumero(scanner);

        if (altura < 1 || altura > 255) {
            System.out.println("A altura deve estar entre 1 e 255.");
            return;
        }

        System.out.print("Largura (1 a 6): ");
        int largura = lerNumero(scanner);

        if (largura < 1 || largura > 6) {
            System.out.println("A largura deve estar entre 1 e 6.");
            return;
        }

        System.out.print("HRI (1 a 4): ");
        int hri = lerNumero(scanner);

        if (hri < 1 || hri > 4) {
            System.out.println("O HRI deve estar entre 1 e 4.");
            return;
        }

        impressora.imprimirCodigoBarras(tipoCodigo, codigo, altura, largura, hri);
    }

    private static void imprimirXmlSAT() {
        try {
            String xml = Files.readString(Path.of("XMLSAT.xml"));

            impressora.imprimirXMLSAT(xml, 0);

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo XML SAT.");
        }
    }

    private static void imprimirXmlCancelamentoSAT(Scanner scanner) {
        try {
            String xml = Files.readString(Path.of("CANC_SAT.xml"));

            System.out.print("Digite a assinatura do QR Code da venda: ");
            String assinatura = scanner.nextLine();

            if (assinatura.trim().isEmpty()) {
                System.out.println("A assinatura não pode ficar vazia.");
                return;
            }

            impressora.imprimirXMLCancelamentoSAT(xml, assinatura, 0);

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo XML de cancelamento.");
        }
    }

    private static void abrirGavetaElgin() {
        System.out.println("Abrindo gaveta Elgin...");

        impressora.abrirGavetaElgin(0, 5, 5);
    }

    private static void abrirGaveta(Scanner scanner) {
        System.out.print("Pino (0 ou 1): ");
        int pino = lerNumero(scanner);

        if (pino != 0 && pino != 1) {
            System.out.println("O pino deve ser 0 ou 1.");
            return;
        }

        System.out.print("Tempo inicial (1 a 255): ");
        int ti = lerNumero(scanner);

        System.out.print("Tempo final (1 a 255): ");
        int tf = lerNumero(scanner);

        if (ti < 1 || ti > 255 || tf < 1 || tf > 255) {
            System.out.println("Os tempos devem estar entre 1 e 255.");
            return;
        }

        impressora.abrirGaveta(pino, ti, tf);
    }

    private static void emitirSinalSonoro(Scanner scanner) {
        System.out.print("Quantidade: ");
        int quantidade = lerNumero(scanner);

        System.out.print("Tempo inicial: ");
        int tempoInicio = lerNumero(scanner);

        System.out.print("Tempo final: ");
        int tempoFim = lerNumero(scanner);

        if (quantidade < 1 || quantidade > 63 ||
                tempoInicio < 1 || tempoInicio > 25 ||
                tempoFim < 1 || tempoFim > 25) {

            System.out.println("Quantidade deve estar entre 1 e 63, e os tempos entre 1 e 25.");
            return;
        }

        impressora.sinalSonoro(quantidade, tempoInicio, tempoFim);
    }

    private static void avancarPapel(Scanner scanner) {
        System.out.print("Quantidade de linhas para avançar: ");
        int linhas = lerNumero(scanner);

        if (linhas <= 0) {
            System.out.println("A quantidade de linhas deve ser maior que zero.");
            return;
        }

        impressora.avancarPapel(linhas);
    }

    private static void verificarStatusImpressora() {
        impressora.consultarStatus(5);
    }

    private static void fecharConexaoESair() {
        impressora.fecharConexao();
        conexaoAberta = impressora.estaConectada();

        System.out.println("Encerrando o sistema...");
    }
}