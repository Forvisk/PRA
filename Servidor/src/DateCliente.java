import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;


public class DateCliente {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String serverAddress = JOptionPane.showInputDialog(
	            "Enter IP Address of a machine that is\n" +
	            "running the date service on port 9090:");
		@SuppressWarnings("resource")
		Socket soc = new Socket(serverAddress, 9090);
		BufferedReader input = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		String answer = input.readLine();
		JOptionPane.showMessageDialog(null, answer);
		System.exit(0);
	}
}
