/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteFreeV2;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.FlowLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import org.json.simple.JSONObject;

/**
 *
 * @author Gustavo Diel ( UDESC )
 */
public class Resposta extends JFrame implements ActionListener {

    JSONObject objeto;

    public Resposta ( JSONObject json ) {
        super ( "Add component on JFrame at runtime" );
        objeto = json;
        configuraJanela ();
    }

    private void JSONObjectToListTuple ( List<String> left, List<String> right, JSONObject objeto ) {
        for ( Object obj : objeto.keySet () ) {
            String keyStr = ( String ) obj;
            Object keyvalue = objeto.get ( keyStr );

            if ( keyvalue instanceof JSONObject ) {
                JSONObjectToListTuple(left, right, (JSONObject) keyvalue);
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

        JSONObjectToListTuple(left, right, objeto);

        JPanel containerLeft = new JPanel ();
        containerLeft.setBorder ( BorderFactory.createTitledBorder ( "ID" ) );
        BoxLayout layoutLeft = new BoxLayout ( containerLeft, BoxLayout.Y_AXIS );
        containerLeft.setLayout ( layoutLeft );

        for ( int j = 0, n = left.size (); j < n; j++ ) {
            JLabel button = new JLabel ( left.get ( j ) );
            button.setAlignmentX ( Component.LEFT_ALIGNMENT );
            containerLeft.add ( button );
        }

        JPanel containerRight = new JPanel ();
        containerRight.setBorder ( BorderFactory.createTitledBorder ( "Valor" ) );
        BoxLayout layoutRight = new BoxLayout ( containerRight, BoxLayout.Y_AXIS );
        containerRight.setLayout ( layoutRight );

        for ( int j = 0, n = right.size (); j < n; j++ ) {
            JLabel button = new JLabel ( right.get ( j ) );
            button.setAlignmentX ( Component.LEFT_ALIGNMENT );
            containerRight.add ( button );
        }

        getContentPane ().add ( containerLeft );
        getContentPane ().add ( containerRight );

    }

    private void configuraJanela () {

        //setLayout ( new BoxLayout ( this.getContentPane (), BoxLayout.PAGE_AXIS ) );
        setLayout ( new FlowLayout () );
        addJSON ();

        pack ();

        //this.panel.setLayout ( new FlowLayout () );
        // add ( panel );
        JButton button = new JButton ( "CLICK HERE" );
        //add ( button, BorderLayout.SOUTH );

        button.addActionListener ( this );
        setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        setLocationRelativeTo ( null );
        setVisible ( true );
    }

    public void actionPerformed ( ActionEvent evt ) {

    }
}
