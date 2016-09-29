package pra;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Collections;

public class Escritor {

	private static final String INDICE = "indice.pra";
	private static final String ARQUIVO_INDENTADO = "indentado.pra";

	Escritor() {

	}

	public void JKRolling() {

	}

	public void escreveIndice() {

		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(INDICE)))) {
			
			String linha = "Linha";
			String ID = "ID";
			
			int maiorIndiceSize = Math.max(Main.getValoresMaiores()[Main.getValoresMaiores().length-1], linha.length()+1);
			int maiorIDSize = Math.max(Main.getValoresMaiores()[1], ID.length() + 1);
			
			String index = Utilitarios.repeatString(" ", ((maiorIndiceSize+1) - linha.length()));
			String id = Utilitarios.repeatString(" ", ((maiorIDSize+1) - ID.length()));
			writer.write(linha);
			writer.write(index);
			writer.write(ID);
			writer.write(id);
			
			writer.write("\n");
			for (Dado d : Main.getArvore()){
				index = Utilitarios.repeatString(" ", ((maiorIndiceSize+1) - Utilitarios.getNumberOfDigits(d.nrLinha)));
				id = Utilitarios.repeatString(" ", ((maiorIDSize+1) - d.leitura.get(1).length()));
				writer.write(String.valueOf(d.nrLinha));
				writer.write(index);
				
				writer.write(d.leitura.get(1));
				writer.write(id);
				
				writer.write("\n");
				
			}
			writer.close();
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void escreveDados() {

		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(ARQUIVO_INDENTADO)))) {
			
			int num = 0;
			
			for (String i : Main.getCabecalho()){
				int maxCabe = Main.getValoresMaiores()[num];
				
				String index = Utilitarios.repeatString(" ", ((maxCabe+1) - i.length()));
				writer.write(i);
				writer.write(index);
				
				num++;
			}
			
			writer.write("\n");
			
			for (Dado d : Main.getArvore()){
				num = 0;
				
				for (String i : d.leitura){
					int maxCabe = Main.getValoresMaiores()[num];
					
					String index = Utilitarios.repeatString(" ", ((maxCabe+1) - i.length()));
					writer.write(i);
					writer.write(index);
					num++;
				}
				
				writer.write("\n");
				
			}
			writer.close();
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
