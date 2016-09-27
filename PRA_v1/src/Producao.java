
public class Producao {
	
	private static final String SEPARADOR = ";"; 
	private int numeroDados = 0;
	
	Producao(){
		System.out.println("Iniciando producao");
	}
	
	public void recebeCabecalho(String cabecalho){
		String campos[] = cabecalho.split(SEPARADOR);
		numeroDados = campos.length;
		for(int i = 0; i < campos.length; i++ ){
			System.out.println(campos[i]);
		}
	}
	
	public boolean recebeLinha(String linha){
		String dados[] = linha.split(SEPARADOR);
		if(dados.length != numeroDados){
			return false;
		}
		for(int i = 0; i < dados.length; i++ ){
			System.out.println(dados[i]);
		}
		return true;
	}
	
}
