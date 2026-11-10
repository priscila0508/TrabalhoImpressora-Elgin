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
            if (opcao >= 3 && opcao <= 12 && !conexaoAberta) {
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
        System.out.println("============== MENU IMPRESSORA ==============");
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
        System.out.println("12 - Status da impressora");
        System.out.println(" 0 - Fechar conexão e sair");
        System.out.println("=============================================");
    }

    // --- MÉTODOS DAS AÇÕES DA IMPRESSORA ---

    private static void configurarConexao(Scanner scanner) {
        System.out.print("Tipo de conexão: ");
        tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Modelo da impressora: ");
        modelo = scanner.nextLine();

        System.out.print("Porta de conexão: ");
        porta = scanner.nextLine();

        System.out.print("Parâmetro: ");
        parametro = scanner.nextInt();
        scanner.nextLine();

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

        System.out.print("Posição: ");
        int posicao = scanner.nextInt();

        System.out.print("Estilo: ");
        int estilo = scanner.nextInt();

        System.out.print("Tamanho: ");
        int tamanho = scanner.nextInt();

        System.out.print("Quantas cópias? ");
        int copias = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= copias; i++) {
            System.out.println("Impressão " + i + " de " + copias);

            impressora.imprimirTexto(texto, posicao, estilo, tamanho);
        }
    }

    private static void imprimirQRCode(Scanner scanner) {
        System.out.print("Conteúdo do QR Code: ");
        String conteudo = scanner.nextLine();

        System.out.print("Tamanho: ");
        int tamanho = scanner.nextInt();

        System.out.print("Nível de correção: ");
        int nivelCorrecao = scanner.nextInt();
        scanner.nextLine();

        impressora.imprimirQRCode(conteudo, tamanho, nivelCorrecao);
    }

    private static void imprimirCodigoDeBarras(Scanner scanner) {
        System.out.print("Tipo: ");
        int tipo = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Código de barras: ");
        String codigo = scanner.nextLine();

        System.out.print("Altura: ");
        int altura = scanner.nextInt();

        System.out.print("Largura: ");
        int largura = scanner.nextInt();

        System.out.print("HRI: ");
        int hri = scanner.nextInt();
        scanner.nextLine();

        impressora.imprimirCodigoBarras(tipo, codigo, altura, largura, hri);
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
        int pino = scanner.nextInt();

        System.out.print("Tempo inicial (1 a 255): ");
        int ti = scanner.nextInt();

        System.out.print("Tempo final (1 a 255): ");
        int tf = scanner.nextInt();
        scanner.nextLine();

        impressora.abrirGaveta(pino, ti, tf);
    }

    private static void emitirSinalSonoro(Scanner scanner) {
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();

        System.out.print("Tempo inicial: ");
        int tempoInicio = scanner.nextInt();

        System.out.print("Tempo final: ");
        int tempoFim = scanner.nextInt();
        scanner.nextLine();

        impressora.sinalSonoro(quantidade, tempoInicio, tempoFim);
    }

    private static void avancarPapel(Scanner scanner) {
        System.out.print("Quantidade de linhas para avançar: ");
        int linhas = scanner.nextInt();
        scanner.nextLine();

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