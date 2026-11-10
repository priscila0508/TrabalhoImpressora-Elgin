import com.sun.jna.Library;
import com.sun.jna.Native;

import java.nio.file.Paths;

/**
 * Interface JNA que representa a DLL da Elgin. NÃO ALTERAR.
 * A DLL é escolhida pela arquitetura da JVM (x64 ou x86), a partir da pasta
 * "Bibliotecas E1 Impressora" que deve estar na raiz do projeto.
 */
public interface ImpressoraDLL extends Library {

    ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(caminhoDll(), ImpressoraDLL.class);

    static String caminhoDll() {
        String arq = System.getProperty("os.arch").contains("64") ? "x64" : "x86";
        return Paths.get("Bibliotecas E1 Impressora", arq, "E1_Impressora01.dll")
                .toAbsolutePath().toString();
    }

    int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);

    int FechaConexaoImpressora();

    int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);

    int Corte(int avanco);

    int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);

    int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);

    int AvancaPapel(int linhas);

    int StatusImpressora(int param);

    int AbreGavetaElgin(int pino, int ti, int tf);

    int AbreGaveta(int pino, int ti, int tf);

    int SinalSonoro(int qtd, int tempoInicio, int tempoFim);

    int ModoPagina();

    int LimpaBufferModoPagina();

    int ImprimeModoPagina();

    int ModoPadrao();

    int PosicaoImpressaoHorizontal(int posicao);

    int PosicaoImpressaoVertical(int posicao);

    int ImprimeXMLSAT(String dados, int param);

    int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
}
