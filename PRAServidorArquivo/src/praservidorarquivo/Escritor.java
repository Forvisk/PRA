/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praservidorarquivo;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * @author Adriano e Gustavo
 * Esta classe executa o processo de escritura dos arquivos do trabalho
 *
 */
public class Escritor {

	private static final String INDICE 				= "indice.pra";			// Arquivo de indice que sera salvo
	private static final String ARQUIVO_INDENTADO	= "indentado.pra";		// Arquivo principal indentado que sera salvo

	/**
	 * Não precisamos enviar nada no construtor
	 */
	Escritor() {

	}

	/**
	 * Esta é a melhor função que fiz na minha vida @Gustavo
	 */
	public void JKRolling() {

	}

	/**
	 * Cria o arquivo de indice
	 */
	public void escreveIndice() {

		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(INDICE)))) {

			String linha = "Linha";
			String ID = "ID";

			int maiorIndiceSize = Math.max(PRAServidorArquivo.getValoresMaiores()[PRAServidorArquivo.getValoresMaiores().length - 1],
					linha.length() + 1);
			int maiorIDSize = Math.max(PRAServidorArquivo.getValoresMaiores()[1], ID.length() + 1);

			String index = Utilitarios.repeatString(" ", ((maiorIndiceSize + 1) - linha.length()));
			String id = Utilitarios.repeatString(" ", ((maiorIDSize + 1) - ID.length()));
			escritorIndice(writer, linha, ID, maiorIndiceSize, maiorIDSize, index, id);
			writer.close();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Realiza a escrita dos indices
	 * */
	private void escritorIndice(Writer writer, String linha, String ID, int maiorIndiceSize, int maiorIDSize,
				String index, String id) throws IOException {
		writer.write(linha);
		writer.write(index);
		writer.write(ID);
		writer.write(id);

		writer.write("\n");
		for (Dado d : PRAServidorArquivo.getArvore()) {
			index = Utilitarios.repeatString(" ",
					((maiorIndiceSize + 1) - Utilitarios.getNumberOfDigits(d.getNrLinha())));
			id = Utilitarios.repeatString(" ", ((maiorIDSize + 1) - d.getLeitura().get(1).length()));
			writer.write(String.valueOf(d.getNrLinha()));
			writer.write(index);

			writer.write(d.getLeitura().get(1));
			writer.write(id);

			writer.write("\n");

		}
	}

	/**
	 * Realiza a escritura do arquivo indentado
	 */
	public void escreveDados() {

		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ARQUIVO_INDENTADO)))) {

			escritorCabecalho(writer);

			writer.write("\n");
			
			escritorDados(writer);
			
			writer.close();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que escreve os dados no arquivo
	 */
	private void escritorDados(Writer writer) throws IOException {
		int num;
		for (Dado d : PRAServidorArquivo.getArvore()) {
			num = 0;

			for (String i : d.getLeitura()) {
				int maxCabe = PRAServidorArquivo.getValoresMaiores()[num];

				String index = Utilitarios.repeatString(" ", ((maxCabe + 1) - i.length()));
				writer.write(i);
				writer.write(index);
				num++;
			}

			writer.write("\n");

		}
	}
	/**
	 * Metodo que escreve os cabecalhos do arquivo
	 * */
	private void escritorCabecalho(Writer writer) throws IOException {
		int num = 0;

		for (String i : PRAServidorArquivo.getCabecalho()) {
			int maxCabe = PRAServidorArquivo.getValoresMaiores()[num];

			String index = Utilitarios.repeatString(" ", ((maxCabe + 1) - i.length()));
			writer.write(i);
			writer.write(index);

			num++;
		}
	}

}

