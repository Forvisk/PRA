import java.io.IOException;


public class Principal {

//AQUI COMEÇAMOS A NÂO DSABER O QUE FAZER POIS JAVA
	
	private static final String ARQUIVO01 = "";
	private static final String ARQUIVO02 = "";
	private static final String ARQUIVO03 = "";
	
	private static final Producao producao = new Producao();
	
	private static boolean leituraArquivo() throws IOException{
		Leitura leitor = new Leitura(ARQUIVO01);
		String cabecalho = leitor.LerLinha();
		String linha = "";
		if(! chegouNoFinalDoArquivo(cabecalho)){
			return false;
		}
		producao.recebeCabecalho(cabecalho);
		linha = leitor.LerLinha();
		while(! chegouNoFinalDoArquivo(linha)){
			if(! producao.recebeLinha(linha))
				return false;
			linha = leitor.LerLinha();
		}
		return true;
	}
	
	private static boolean chegouNoFinalDoArquivo(String linha) {
		return linha == null;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//aqui colocamos o inicio para ler o arquivo
		//aqui pegamos os dados dos arquivos e enfiamos numa estrutura de dados
		//aqui pegamos a estrutura de dados e enfiamos em um novo arquivo
		

		try {
			leituraArquivo();
		} catch (IOException e) {
			// Logar o erro de leitura caso ocorra
		}
		
		
		
		
	}

	
}
