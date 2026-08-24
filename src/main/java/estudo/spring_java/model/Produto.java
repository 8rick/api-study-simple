package estudo.spring_java.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Produto {

    @Id //diz ao banco que essa é PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco vi gerar um novo id automaticamente
    private Long id;
    private String nome;
    private  double preco;

    // Construtor vazio (Obrigatório para o Banco de Dados)
    public Produto() {}

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // Meus Getters (leitura)
    public Long getId() {return  id;}
    public String getNome() {return nome;}
    public double getPreco() {return preco;}

    // Setters para podermos atualizar os dados
    public void setNome(String nome) {this.nome = nome;}
    public void setPreco(double preco) {this.preco = preco;}
    public void setId(Long id) {this.id = id;}

}
