package clienteFreeV2;

import cliente_pra.Cliente;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author UDESC
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main ( String[] args ) {
        Cliente cliente = Cliente.getInstance ();
        cliente.inicio ();
    }

}
