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
public abstract class ITemplateFuncao {

    abstract String[] processaArgumentos ( final String args );

    abstract String executaAcao ( final String[] args );

    public final String executa ( final String args ) {
        return executaAcao ( processaArgumentos ( args ) );
    }

}

class InserirTemplate extends ITemplateFuncao {

    @Override
    String[] processaArgumentos ( final String _args ) {
        //TODO: Faca codigo.
        return _args.split ( ";" );
    }

    @Override
    String executaAcao ( final String[] _args ) {
        //TODO: Faca codigo para inserir.
        String ret = _args[ 0 ];
        return ret;
    }

}

class EncontrarTemplate extends ITemplateFuncao {

    @Override
    String[] processaArgumentos ( final String _args ) {
        //TODO: Faca codigo.
        String[] ret = new String[ 1 ];
        ret[ 0 ] = _args.split ( ";" )[ 1 ];
        return ret;
    }

    @Override
    String executaAcao ( final String[] _args ) {
        //TODO: Faca codigo para buscar o dado.
        String ret = Arrays.toString ( _args );
        return ret;
    }

}

class FazAsCoisasTemplate {

    private void ProcessaMensagem ( String funcao, String[] argumentos ) {
        ITemplateFuncao func;
        switch ( funcao ) {
            case "find":
                func = new EncontrarTemplate ();
                break;
            case "insert":
                func = new InserirTemplate ();
                break;
            default:
                func = new ITemplateFuncao () {
                    @Override
                        String[] processaArgumentos ( String _args ) {
                            //TODO: Faca codigo para outra coisa.
                            String[] ret = null;
                            return ret;
                        }
                        
                        @Override
                        String executaAcao ( String[] _args ) {
                            //TODO: Faca codigo para o else.
                            String ret = null;
                            return ret;
                        }
                        
                };  break;
        }
        func.executa ( Arrays.toString ( argumentos ) );
    }

}
