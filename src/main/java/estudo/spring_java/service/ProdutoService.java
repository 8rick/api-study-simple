package estudo.spring_java.service;

import estudo.spring_java.dto.DadosDetalhamentoProduto;
import estudo.spring_java.infra.exception.ProdutoNaoEncontradoException;
import estudo.spring_java.model.Produto;
import estudo.spring_java.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Page listarProdutos(Pageable paginacao) {
        return repository.findAll(paginacao)
                .map(DadosDetalhamentoProduto::new);
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);

    }

    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produtoAntigo = repository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto com ID " + id + " não foi encontrado!"));

        produtoAntigo.setId(id);
        produtoAntigo.setNome(produtoAtualizado.getNome());
        produtoAntigo.setPreco(produtoAtualizado.getPreco());

        return repository.save(produtoAntigo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ProdutoNaoEncontradoException("Produto com ID " + id + " não foi encontrado!");
        }
        repository.deleteById(id);
    }


}
