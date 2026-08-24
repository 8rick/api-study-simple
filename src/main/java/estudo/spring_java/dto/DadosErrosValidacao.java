package estudo.spring_java.dto;

import org.springframework.validation.FieldError;

// Record simples para formatar a resposta JSON de cada campo inválido
public record DadosErrosValidacao(String nome, String mensagem) {

    // Construtor auxiliar que converte o FielError do Spring no nosso DTO
    public DadosErrosValidacao(FieldError erro) {
        this(erro.getField(), erro.getDefaultMessage());
    }

}
