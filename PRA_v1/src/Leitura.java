import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Leitura {
	private File arquivo;
	private FileReader leitor;
	private BufferedReader buffer;
	
	Leitura(String arq) throws FileNotFoundException{
		arquivo = new File(arq);
		leitor = new FileReader(arquivo);
		buffer = new BufferedReader(leitor);
	}
	
	public String LerLinha() throws IOException{
		String linha = buffer.readLine();
		return linha;
	}
	
	//Aqui criamos a função para ler os arquivos e retornar uma string
}
