package estudo.spring_java.infra.exception;

import estudo.spring_java.dto.DadosErrosValidacao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorErros {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErrosValidacao>> tratarErro400(
            MethodArgumentNotValidException ex) {

        List<FieldError> erros = ex.getFieldErrors();

        List<DadosErrosValidacao> resposta = erros.stream()
                .map(DadosErrosValidacao::new)
                .toList();

        return ResponseEntity.badRequest().body(resposta);
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<String> tratarErro404(ProdutoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
