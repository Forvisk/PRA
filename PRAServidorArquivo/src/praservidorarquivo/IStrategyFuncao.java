/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praservidorarquivo;

/**
 * Interface IStrategyFuncao
 *
 * @author Gustavo Diel ( UDESC )
 */
interface IStrategyFuncao {

    public String facaOperacao ( String[] args );

}

//FuncaoProcurarDado.java:
class FuncaoStrategyProcurarDado implements IStrategyFuncao {

    @Override
    public String facaOperacao ( String[] args ) {
        // TODO: Código para buscar dados para o cliente.
        return "Feito!";
    }

}

//FuncaoInserirDado.java:
class FuncaoStrategyInserirDado implements IStrategyFuncao {

    @Override
    public String facaOperacao ( String[] args ) {
        // TODO: Código para inserir dado.
        return "Fca o codigo!";
    }

}

class FazAsCoisasStrategy {
//E quando formos precisar executar a operação, fazemos:

    private void ProcessaMensagem ( String funcao, String[] argumentos ) {
        IStrategyFuncao func = detectaTipoDeFuncao ( funcao );
        func.facaOperacao ( argumentos );
    }

    private IStrategyFuncao detectaTipoDeFuncao ( String funcao ) {
        if ( funcao.equals ( "find" ) ) {
            return new FuncaoStrategyProcurarDado ();
        } else if ( funcao.equals ( "insert" ) ) {
            return new FuncaoStrategyInserirDado ();

        }
        return null;
    }

}
