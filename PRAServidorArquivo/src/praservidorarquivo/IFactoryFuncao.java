/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praservidorarquivo;

import java.util.Arrays;

/**
 *
 * @author Gustavo Diel ( UDESC )
 */
public interface IFactoryFuncao {

    public String executa ( final String[] args );

}

class InserirFactory implements IFactoryFuncao {

    @Override
    public String executa ( final String[] _args ) {
        //TODO: Faca codigo para inserir.
        return "Inserido :3";
    }

}

class BuscaFactory implements IFactoryFuncao {

    @Override
    public String executa ( final String[] _args ) {
        //TODO: Faca codigo para buscar dado :3.
        return "Tá ai vey <3 :3";
    }

}

class FuncaoFactory{
    public static IFactoryFuncao getFuncao(final String func){
        if (func == null || func.isEmpty ()){
            return null;
        }
        if (func.equalsIgnoreCase ( "find" )){
            return new BuscaFactory ();
        } else if (func.equalsIgnoreCase ( "insert")){
            return new InserirFactory ();
        } else {
            return ( String[] _args ) -> {
                //TODO: Faca codigo para o else.
                return Arrays.toString ( _args );
            };
        }
    }
}


class FazAsCoisasFactory{
    private void ProcessaMensagem ( String funcao, String[] argumentos ) {
        IFactoryFuncao func = FuncaoFactory.getFuncao ( funcao );
        func.executa ( argumentos );
    }
}