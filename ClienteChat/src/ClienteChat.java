import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;


public class ClienteChat {

	public static void main(String[] args) throws IOException {
		String serverAddress = JOptionPane.showInputDialog(
	            "Enter IP Address of a machine that is\n" +
	            "running the date service on port wharever:");
		Socket soc = new Socket(serverAddress, 62469);
		try {
			while(true){
				enviar( soc);
				receber( soc);
				/*if(receberAlgo( soc)){
					
				}*/
			}
		} finally {
			soc.close();
		}
	}
	
	private static void enviar( Socket soc) throws IOException{
		PrintWriter output = new PrintWriter(soc.getOutputStream(), true);
		output.println( JOptionPane.showInputDialog("Digite sua mensagem"));
	}
	
	private static void receber( Socket soc) throws IOException{
		BufferedReader input = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		String answer = input.readLine();
		JOptionPane.showMessageDialog(null, answer);
		System.out.println( answer);
		System.exit(0);
	}
	
	/*private static boolean receberAlgo( Socket soc){
		if()
		return false;
	}*/
}
