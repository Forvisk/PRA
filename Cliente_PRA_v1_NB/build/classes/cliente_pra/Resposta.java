/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente_pra;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.json.simple.JSONObject;

/**
 *
 * @author Gustavo Diel ( UDESC )
 */
public class Resposta extends JFrame implements ActionListener {

    private JSONObject objeto;
    private String tipo;
    private boolean lockedWindows;

    private HashMap<JTextField, JTextField> mapa;

    public Resposta ( JSONObject json, boolean lockWIndows, String tipo ) {
        super ();
        mapa = new HashMap<> ();

        objeto = json;
        lockedWindows = lockWIndows;
        this.tipo = tipo;
        configuraJanela ();
    }

    private void JSONObjectToListTuple ( List<String> left, List<String> right,
            JSONObject objeto ) {
        for ( Object obj : objeto.keySet () ) {
            String keyStr = ( String ) obj;
            Object keyvalue = objeto.get ( keyStr );

            if ( keyvalue instanceof JSONObject ) {
                JSONObjectToListTuple ( left, right, ( JSONObject ) keyvalue );
            } else {
                left.add ( keyStr );
                right.add ( String.valueOf ( keyvalue ) );
                // TODO: É String
            }
        }
    }

    private void addJSON () {
        List<String> left = new ArrayList<> ();
        List<String> right = new ArrayList<> ();

        JSONObjectToListTuple ( left, right, objeto );

        JPanel containerLeft = new JPanel ();
        containerLeft.setBorder ( BorderFactory.createTitledBorder ( "ID" ) );
        BoxLayout layoutLeft = new BoxLayout ( containerLeft, BoxLayout.Y_AXIS );
        containerLeft.setLayout ( layoutLeft );

        JPanel containerRight = new JPanel ();
        containerRight.setBorder ( BorderFactory.createTitledBorder ( "Valor" ) );
        BoxLayout layoutRight = new BoxLayout ( containerRight, BoxLayout.Y_AXIS );
        containerRight.setLayout ( layoutRight );

        for ( int j = 0, n = left.size (); j < n; j++ ) {
            if ( left.get ( j ).equals ( "acao" ) || left.get ( j ).equals ( "indice" ) ) {
                continue;
            }
            JTextField button = new JTextField ( left.get ( j ) );
            button.setEnabled ( false );
            button.setAlignmentX ( Component.LEFT_ALIGNMENT );
            containerLeft.add ( button );

            String nameRight = right.get ( j );
            if ( nameRight.isEmpty () ) {
                nameRight = "             ";
            }
            JTextField button2 = new JTextField ( nameRight );
            button2.setEditable ( !lockedWindows );
            button2.setAlignmentX ( Component.LEFT_ALIGNMENT );
            containerRight.add ( button2 );

            mapa.put ( button, button2 );
        }

        getContentPane ().add ( containerLeft );
        getContentPane ().add ( containerRight );

    }

    private void configuraJanela () {

        //setLayout ( new BoxLayout ( this.getContentPane (), BoxLayout.PAGE_AXIS ) );
        setLayout ( new FlowLayout () );
        addJSON ();

        //this.panel.setLayout ( new FlowLayout () );
        // add ( panel );
        if ( !tipo.equals ( Producao.FIND ) ) {
            JButton button = new JButton ( "Salvar" );
            add ( button );
            button.addActionListener ( this );
        }
        pack ();

        setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE );
        setLocationRelativeTo ( null );
        setVisible ( true );
    }

    public void actionPerformed ( ActionEvent evt ) {
        String l = ( tipo.equals ( Producao.INCLUDE_REQUEST ) ? Producao.INCLUDE_COMMIT : Producao.MODIFY_COMMIT );
        StringBuilder sb = new StringBuilder ();
        sb.append ( "{\"acao\": \"" );
        sb.append ( l );
        sb.append ( "\", \"status\":{" );
        for ( Entry<JTextField, JTextField> entr : mapa.entrySet () ) {
            sb.append ( "\"" );
            sb.append ( entr.getKey ().getText () );
            sb.append ( "\":\"" );
            sb.append ( entr.getValue ().getText ().replace ( "\"", "\\\"" ) );
            sb.append ( "\"," );
        }
        sb.deleteCharAt ( sb.lastIndexOf ( "," ) );
        if ( l.equals ( Producao.MODIFY_COMMIT ) ) {
            sb.append ( "}, \"indice\": \"" );
            sb.append ( objeto.get ( "indice" ) );
            sb.append ( "\"}" );
        } else {
            sb.append ( "\"}}" );
        }

        String resp = new String ( sb );
        Cliente.getInstance ().enviar ( l, resp );
        System.out.println ( "Msg: " + resp );
        dispose ();
    }

}
