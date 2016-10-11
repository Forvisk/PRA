/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praservidorarquivo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author usuario
 */
class PRAServidorArquivo {

    public static final int PORTA = 62469;
    
    public static Map<String, IMetodo> mapMetodos;
    
    public static void main(String[] args){
        
        JanelaServidor.getInstance();
        
        PopulateMetodos();
        
        GerenciadorArquivo ga = GerenciadorArquivo.getInstance();
        ga.IniciaGerenciador();
        GerenciadorServidor gs = GerenciadorServidor.getInstance(PORTA);
    }

    private static void PopulateMetodos() {
        mapMetodos = new HashMap<>();
        
        // Atribuimos uma string a um retorno. No caso, uma função IMetodo.
        
        // Find encontra um indice
        // O indice esta em args[1]
        mapMetodos.put("find", new IMetodo(){
            @Override
            public String method(String[] args) {
                return GerenciadorArquivo.getInstance().FindIndex(args[1]);
            }
        });
    }
}
