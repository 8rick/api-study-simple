package estudo.spring_java.infra.exception;

public class ProdutoNaoEncontradoException extends RuntimeException{
    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }
}
