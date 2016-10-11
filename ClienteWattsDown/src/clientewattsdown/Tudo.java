/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Slack

package clientewattsdown;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author UDESC
 */
public class Tudo {
       private static Socket soc;

    Tudo() {
        starta();
    }



    private void starta() {
        ClienteScreem.getInstance();
        String serverAddress = JOptionPane.showInputDialog(
                "Enter IP Address of a machine that is\n"
                + "running the date service on port wharever:");
        try {
            soc = new Socket(serverAddress, 62469);
            try {
                while (true) {
                    receber();
                }
            } finally {
                soc.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ClienteWattsDown.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void enviar(String msg) {
        try {
            PrintWriter output = new PrintWriter(soc.getOutputStream(), true);
            output.println(msg);
            ClienteScreem.getInstance().addMessage(msg);
        } catch (IOException ex) {
            Logger.getLogger(ClienteWattsDown.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void receber() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            String answer = input.readLine();
            ClienteScreem.getInstance().addMessage(answer);
        } catch (IOException ex) {
            Logger.getLogger(ClienteWattsDown.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
