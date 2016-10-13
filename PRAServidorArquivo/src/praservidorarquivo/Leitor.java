package praservidorarquivo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

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
     * @return uma arvore contendo todos os dados do arquivo em uma
     *         {@link java.util.TreeSet}
     *
     * @see java.util.TreeSet
     */
    TreeSet<Dado> LeEProcessa ( String arqv ) {
        BufferedReader leitor = null;
        try {
            leitor = new BufferedReader ( new FileReader ( arqv ) );
        }
        catch ( FileNotFoundException e ) {
            e.printStackTrace ();
        }

        String cLine;
        int linha_atual = 0;
        int[] maiores = null;

        try {
            String linha = leitor.readLine ();

            String[] nomeCabecalhos = SeparaLinha ( linha );

            maiores = new int[ nomeCabecalhos.length + 1 ];

            linha_atual = leDados ( leitor, linha_atual, maiores );

            verificaTamanhoCabecalhos ( maiores, nomeCabecalhos );

        }
        catch ( IOException e ) {
            e.printStackTrace ();
        }
        maiores[ maiores.length - 1 ] = Utilitarios.getNumberOfDigits ( linha_atual );

        GerenciadorArquivo.getInstance ().setValoresMaiores ( maiores );
        return null;
    }

    /**
     * Metodo que verifica o numero de caracteres do cabecalho para uso futuro
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
     * Metodo para ler os dados do arquivo de entrada
     */
    private int leDados ( BufferedReader leitor, int linha_atual, int[] maiores )
            throws IOException {
        String cLine;
        while ( ( cLine = leitor.readLine () ) != null ) {
            linha_atual++;
            String[] dados = SeparaLinha ( cLine );
            Producao.AdicionaNaArvore ( linha_atual, dados );
            int i = 0;

            for ( String string : dados ) {
                if ( string.length () > maiores[ i ] ) {
                    maiores[ i ] = string.length ();
                }
                i++;
            }
        }
        return linha_atual;
    }

    /**
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
