package estudo.spring_java.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProdutoDTO {

    @NotBlank(message = "O nome do produto é obrigatório e não pode ficar em branco.")
    @Size(min = 3, max = 60, message = "O nome deve ter entre 3 e 60 caracteres.")
    private String nome;

    @Positive(message = "O preço dever ser um valor maior que zero.")
    private double preco;

    //Getter(Leitura) e Setters(Alteração)
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public double getPreco() {return preco;}
    public void setPreco(double preco) {this.preco = preco;}
}
