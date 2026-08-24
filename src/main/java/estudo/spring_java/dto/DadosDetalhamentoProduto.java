package estudo.spring_java.dto;

import estudo.spring_java.model.Produto;

public record DadosDetalhamentoProduto(Long id, String nome, double preco) {

    public DadosDetalhamentoProduto(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getPreco());
    }
}
