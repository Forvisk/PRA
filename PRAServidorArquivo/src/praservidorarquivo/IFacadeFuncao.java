/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praservidorarquivo;

/**
 *
 * @author Gustavo Diel ( UDESC )
 */
public interface IFacadeFuncao {

    public String facaOperacao ( String[] args );

}

//FuncaoProcurarDado.java:
class FuncaoFacadeProcurarDado implements IFacadeFuncao {

    @Override
    public String facaOperacao ( String[] args ) {
        // TODO: Código para buscar dados para o cliente.
        return "Feito!";
    }

}

//FuncaoInserirDado.java:
class FuncaoFacadeInserirDado implements IFacadeFuncao {

    @Override
    public String facaOperacao ( String[] args ) {
        // TODO: Código para inserir dado.
        return "Faca o codigo!";
    }

}

class Funcoes {

    private final IFacadeFuncao funcaoInserir;
    private final IFacadeFuncao funcaoProcurar;

    public Funcoes () {
        funcaoInserir = new FuncaoFacadeInserirDado ();
        funcaoProcurar = new FuncaoFacadeProcurarDado ();
    }

    public String procurarDado ( final String[] args ) {
        return funcaoProcurar.facaOperacao ( args );
    }

    public String inserirDado ( final String[] args ) {
        return funcaoInserir.facaOperacao ( args );
    }

}

class FazAsCoisasFacade {

    private void ProcessaMensagem ( String funcao, String[] argumentos ) {
        Funcoes funcoes = new Funcoes ();
        String resultado = "";
        if ( funcao.equals ( "find" ) ) {
            resultado = funcoes.procurarDado ( argumentos );
        } else if ( funcao.equals ( "insert" ) ) {
            resultado = funcoes.inserirDado (argumentos );
        }
    }

}
