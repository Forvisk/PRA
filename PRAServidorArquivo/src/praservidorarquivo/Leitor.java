package praservidorarquivo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <h1>Leitor</h1>
 * Esta classe realiza a leitura e organização dos dados dos arquivos originais
 *
 * @author Adriano
 * @author Gustavo
 */
public class Leitor {

    /**
     * Não precisamos de nada no construtor
     */
    Leitor () {

    }

    /**
     * Esta função lê e organiza o arquivo numa árvore
     *
     * @param arqv é o nome do arquivo a ser aberto
     *
     * @see java.util.TreeSet
     */
    public void LeEProcessa ( String arqv ) {
        BufferedReader leitor;
        try {
            leitor = new BufferedReader ( new FileReader ( arqv ) );
        }
        catch ( Exception e ) {
            e.printStackTrace ();
            return;
        }

        int linha_atual = 0;
        int[] maiores = null;

        try {
            String linha = leitor.readLine ();
            String[] nomeCabecalhos = SeparaLinha ( linha );
            maiores = new int[ nomeCabecalhos.length + 1 ];
            linha_atual = leDados ( leitor, linha_atual, maiores );
            verificaTamanhoCabecalhos ( maiores, nomeCabecalhos );
            maiores[ maiores.length - 1 ] = Utilitarios.getNumberOfDigits ( linha_atual );
            GerenciadorArquivo.getInstance ().setValoresMaiores ( maiores );
        }
        catch ( IOException e ) {
            e.printStackTrace ();
        }
    }

    /**
     * Metodo que verifica o numero de caracteres do cabecalho para uso futuro
     *
     * @param maiores        contem o tamanho dos maiores elementos, em suas
     *                       respectivas posicoes
     * @param nomeCabecalhos contem os cabecalhos
     */
    private void verificaTamanhoCabecalhos ( int[] maiores,
            String[] nomeCabecalhos ) {
        int num = 0;
        for ( String i : nomeCabecalhos ) {
            GerenciadorArquivo.getInstance ().getCabecalho ().add ( i );
            if ( i.length () > maiores[ num ] ) {
                maiores[ num ] = i.length ();
            }
            num++;
        }
    }

    /**
     * Le os dados do arquivo
     *
     * @param leitor                origem dos dados
     * @param linhaAtual            linha que paro de ler os ultimos dados, para
     *                              podermos
     *                              adicionar essa informação na arvore
     * @param maioresTamanhosDeDado tamanho de cada dado da linha
     *
     * @return a linha atual
     *
     * @throws IOException
     */
    private int leDados ( BufferedReader leitor, int linhaAtual,
            int[] maioresTamanhosDeDado )
            throws IOException {
        String cLine;
        while ( ( cLine = leitor.readLine () ) != null ) {
            linhaAtual++;
            String[] dados = SeparaLinha ( cLine );
            Producao.AdicionaNaArvore ( linhaAtual, dados );
            int i = 0;

            for ( String string : dados ) {
                if ( string.length () > maioresTamanhosDeDado[ i ] ) {
                    maioresTamanhosDeDado[ i ] = string.length ();
                }
                i++;
            }
        }
        return linhaAtual;
    }

    /**
     * Separa a linha em diversos dados. Usavamos String.split() porém pode
     * haver {@code "} no meio do arquvio
     *
     * @param s String a ser separada
     *
     * @return Vetor contendo todos os campos da string separados pelo SEPARADOR
     */
    String[] SeparaLinha ( String s ) {
        final char ASPAS = '"', SEPARADOR = ';';

        boolean podeSeparar = true;
        ArrayList<String> res = new ArrayList<> ();
        String temp = "";

        for ( int i = 0; i < s.length (); ++i ) {
            char c = s.charAt ( i );
            if ( c == ASPAS ) {
                podeSeparar = !podeSeparar;
            }
            if ( c == SEPARADOR && podeSeparar ) {
                res.add ( temp );
                temp = "";
            } else {
                temp = temp + c;
            }
        }
        String[] arr = new String[ res.size () ];
        arr = res.toArray ( arr );
        return arr;
    }

}
