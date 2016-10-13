package praservidorarquivo;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * <h1>Escritor</h1>
 * Esta classe executa o processo de escritura dos arquivos do trabalho
 * <p>
 * E sem machismo. Escritoras também são boas.
 *
 * @author Adriano
 * @author Gustavo
 *
 */
public class Escritor {

    private static final String INDICE = "indice.pra";			// Arquivo de indice que sera salvo
    private static final String ARQUIVO_INDENTADO = "indentado.pra";		// Arquivo principal indentado que sera salvo

    /**
     * Não precisamos enviar nada no construtor
     */
    Escritor () {

    }

    /**
     * Esta é a melhor função que fiz na minha vida @Gustavo
     */
    public void JKRolling () {

    }

    /**
     * Cria o arquivo de indice
     */
    public void escreveIndice () {

        /*
         * Apesar deste longo trecho, o que ele faz é checar o indice ( que
         * sempre estará
         * na ultima posicao dos vetores ) e adiciona no arquivo ( primeira
         * linha )
         */
        try ( Writer writer = new BufferedWriter ( new OutputStreamWriter ( new FileOutputStream ( INDICE ) ) ) ) {

            String linha = "Linha";
            String ID = "ID";

            int[] maiores = GerenciadorArquivo.getInstance ()
                    .getValoresMaiores ();

            int maiorIndiceSize = Math.max ( maiores[ GerenciadorArquivo.getInstance ().getValoresMaiores ().length - 1 ],
                    linha.length () + 1 );

            int maiorIDSize = Math.max ( maiores[ 1 ], ID.length () + 1 );

            String index = Utilitarios.repeatString ( " ", ( ( maiorIndiceSize + 1 ) - linha.length () ) );
            String id = Utilitarios.repeatString ( " ", ( ( maiorIDSize + 1 ) - ID.length () ) );

            escritorIndice ( writer, linha, ID, maiorIndiceSize, maiorIDSize, index, id );
            writer.close ();

        }
        catch ( UnsupportedEncodingException e ) {
            e.printStackTrace ();
        }
        catch ( FileNotFoundException e ) {
            e.printStackTrace ();
        }
        catch ( IOException e ) {
            e.printStackTrace ();
        }
    }

    /**
     * Realiza a escrita de uma string no arquivo de indice.
     *
     * @param writer          é o objeto usado para escrever no arquivo
     * @param conteudo_linha  é o nome do campo da linha que ficará no cabecalho
     * @param ID              é uma {@link String} com o nome do campo ID, onde
     *                        ficará o cabecalho
     * @param maiorIndiceSize é o maior numero de caracteres que o Indice pode
     *                        ter
     * @param maiorIDSize     é o maior numero de caracteres que um ID pode ter
     * @param index           é o indice a ser escrito
     * @param id              é o id a ser escrito
     *
     * @throws IOException pode ser que dê falha ao executar o método
     *                     {@link Writer#write(java.lang.String)}
     */
    private void escritorIndice ( Writer writer, String conteudo_linha,
            String ID,
            int maiorIndiceSize, int maiorIDSize,
            String index, String id ) throws IOException {

        writer.write ( conteudo_linha );
        writer.write ( index );
        writer.write ( ID );
        writer.write ( id );
        writer.write ( "\n" );

        for ( Dado dado : GerenciadorArquivo.getInstance ().getArvore () ) {
            index = Utilitarios.repeatString ( " ",
                    ( ( maiorIndiceSize + 1 ) - Utilitarios.getNumberOfDigits ( dado.getNumero_linha () ) ) );

            id = Utilitarios.repeatString ( " ", ( ( maiorIDSize + 1 ) - dado.getDados_da_linha ().get ( 1 ).length () ) );

            writer.write ( String.valueOf ( dado.getNumero_linha () ) );
            writer.write ( index );

            writer.write ( dado.getDados_da_linha ().get ( 1 ) );
            writer.write ( id );

            writer.write ( "\n" );

        }
    }

    /**
     * Inicia a operação de escrever os dados nos arquivos. Começa com o indice
     * no comeco do arquivo e depois parte para o conteduo em si.
     */
    public void escreveDados () {

        try ( Writer writer = new BufferedWriter ( new OutputStreamWriter ( new FileOutputStream ( ARQUIVO_INDENTADO ) ) ) ) {

            escritorCabecalho ( writer );
            writer.write ( "\n" );
            escritorDados ( writer );
            writer.close ();
        }
        catch ( UnsupportedEncodingException e ) {
            e.printStackTrace ();
        }
        catch ( FileNotFoundException e ) {
            e.printStackTrace ();
        }
        catch ( IOException e ) {
            e.printStackTrace ();
        }
    }

    /**
     * Metodo que escreve todos os dados no arquivo indentado
     *
     * @param writer {@link Writer} para o arquivo desejado
     *
     * @throws IOException
     */
    private void escritorDados ( Writer writer ) throws IOException {
        int num;
        for ( Dado d : GerenciadorArquivo.getInstance ().getArvore () ) {
            num = 0;

            for ( String i : d.getDados_da_linha () ) {
                int maxCabe = GerenciadorArquivo.getInstance ().getValoresMaiores ()[ num ];

                String index = Utilitarios.repeatString ( " ", ( ( maxCabe + 1 ) - i.length () ) );
                writer.write ( i );
                writer.write ( index );
                num++;
            }

            writer.write ( "\n" );

        }
    }

    /**
     * Metodo que escreve o cabecalho no arquivo
     *
     * @param writer {@link Writer} para o arquivo desejado
     *
     * @throws IOException
     */
    private void escritorCabecalho ( Writer writer ) throws IOException {
        int num = 0;

        for ( String i : GerenciadorArquivo.getInstance ().getCabecalho () ) {
            int maxCabe = GerenciadorArquivo.getInstance ().getValoresMaiores ()[ num ];

            String index = Utilitarios.repeatString ( " ", ( ( maxCabe + 1 ) - i.length () ) );
            writer.write ( i );
            writer.write ( index );

            num++;
        }
    }

}