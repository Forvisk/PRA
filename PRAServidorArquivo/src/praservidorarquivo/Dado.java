package praservidorarquivo;

import java.util.ArrayList;

/**
 * <h1>Dado</h1>
 * Esta classe é usada para guardar os dados do arquivo principal.
 * <p>
 * Possui um {@link ArrayList} com todos os itens lidos no arquivo para cada
 * linha, assim como uma váriavel que guarda a posição deste elemento no
 * arquivo.
 * <p>
 * Também implementa a classe Comparable, para podermos utilizar esta classe no
 * {@link java.util.TreeSet}.
 *
 * @author Adriano
 * @author Gustavo
 *
 */
public class Dado implements Comparable {

    /**
     * {@link ArrayList} de {@link String} que armazenará todos os dados
     * (separados por um demarcador, no caso ';') contidos na linha.
     * <p>
     * Cada dado em uma posição do {@link ArrayList}
     * <p>
     */
    private ArrayList<String> dadosDaLinha;

    /**
     * Demarca o número da linha que esta instância estava localizada no
     * arquivo.
     */
    private final int numeroLinha;

    /**
     * Booleano que indica se o Dado foi deletado ou não do nosso banco.
     */
    private boolean foiDeletado;

    /**
     * Booleano que indica se o Dado está ou não sendo editado.
     */
    private boolean estadoEditando;

    /**
     * Função que retornará todos os dados contidos em nosso
     * {@link ArrayList} {@link Dado#dadosDaLinha}
     *
     * @return os dados contidos no {@link ArrayList} do objeto
     */
    public ArrayList<String> getDadosDaLinha () {
        return dadosDaLinha;
    }

    /**
     * Retorna o número da linha em que este dado está localizado no seu arquivo
     * original
     *
     * @return o numero da linha do dado
     */
    public int getNumeroLinha () {
        return numeroLinha;
    }

    /**
     * Retorna se o dado foi ou não deletado do sistema
     *
     * @return booleano que informa o estado do dado
     */
    public boolean isDeletado () {
        return foiDeletado;
    }

    /**
     * Retorna se o dado esta ou não sendo editado
     *
     * @return estado do dado
     */
    public boolean taEditando () {
        return estadoEditando;
    }

    /**
     * Altera o estado do dado para deletado ou não
     *
     * @param estado o novo estado do dado
     */
    public void setDeletado ( boolean estado ) {
        foiDeletado = estado;
    }
    
    /**
     * Atualiza os dados
     * @param valores novos dados
     */
    public void setValores ( ArrayList<String> valores ) {
        dadosDaLinha = valores;
    }

    /**
     * Construtor da classe que recebe um {@link ArrayList} de {@link String}
     * que são os dados da linha, e o número da linha.
     *
     * @param dado  são todos os dados da linha
     * @param linha indica o numero da linha que o dado está
     */
    Dado ( ArrayList<String> dado, int linha ) {
        dadosDaLinha = dado;
        numeroLinha = linha;
        foiDeletado = false;
        estadoEditando = false;
    }

    /**
     * Muda o estado de edição do dado para bloquear ou liberar o dado para
     * outros clientes editarem o mesmo.
     *
     * @param estado estado de edição
     */
    public void setEstadoEditando ( boolean estado ) {
        estadoEditando = estado;
    }

    @Override
    public int compareTo ( Object arg0 ) {
        Dado outro = ( Dado ) arg0;
        if ( outro == null ) {
            return -1;
        }
        if ( this.numeroLinha >= outro.numeroLinha ) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString () {
        return String.format ( "Dado na posição %d, possui %s", getNumeroLinha (), getDadosDaLinha () );
    }
}
