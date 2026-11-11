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
        if (conexaoAberta) {
            System.out.println("Feche a conexão antes de alterar os parâmetros.");
            return;
        }

        System.out.print("Tipo de conexão: ");
        tipo = lerNumero(scanner);

        System.out.print("Modelo da impressora: ");
        modelo = scanner.nextLine();

        System.out.print("Porta de conexão: ");
        porta = scanner.nextLine();

        System.out.print("Parâmetro: ");
        parametro = lerNumero(scanner);

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

        System.out.print("Posição: ");
        int posicao = lerNumero(scanner);

        System.out.print("Estilo: ");
        int estilo = lerNumero(scanner);

        System.out.print("Tamanho: ");
        int tamanho = lerNumero(scanner);

        System.out.print("Quantas cópias? ");
        int copias = lerNumero(scanner);

        if (copias <= 0) {
            System.out.println("A quantidade de cópias deve ser maior que zero.");
            return;
        }

        for (int i = 1; i <= copias; i++) {
            System.out.println("Impressão " + i + " de " + copias);

            impressora.imprimirTexto(texto, posicao, estilo, tamanho);
        }
    }

    private static void imprimirQRCode(Scanner scanner) {
        System.out.print("Conteúdo do QR Code: ");
        String conteudo = scanner.nextLine();

        if (conteudo.isEmpty()) {
            System.out.println("O conteúdo do QR Code não pode ficar vazio.");
            return;
        }

        System.out.print("Tamanho: ");
        int tamanho = lerNumero(scanner);

        System.out.print("Nível de correção: ");
        int nivelCorrecao = lerNumero(scanner);

        if (tamanho <= 0) {
            System.out.println("O tamanho deve ser maior que zero.");
            return;
        }

        impressora.imprimirQRCode(conteudo, tamanho, nivelCorrecao);
    }

    private static void imprimirCodigoDeBarras(Scanner scanner) {
        System.out.print("Tipo: ");
        int tipoCodigo = lerNumero(scanner);

        System.out.print("Código de barras: ");
        String codigo = scanner.nextLine();

        if (codigo.isEmpty()) {
            System.out.println("O código de barras não pode ficar vazio.");
            return;
        }

        System.out.print("Altura: ");
        int altura = lerNumero(scanner);

        System.out.print("Largura: ");
        int largura = lerNumero(scanner);

        System.out.print("HRI: ");
        int hri = lerNumero(scanner);

        if (altura <= 0 || largura <= 0) {
            System.out.println("A altura e a largura devem ser maiores que zero.");
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

        if (quantidade <= 0 || tempoInicio < 0 || tempoFim < 0) {
            System.out.println("Informe valores válidos para o sinal sonoro.");
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