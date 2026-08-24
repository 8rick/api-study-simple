package estudo.spring_java.repository;

import estudo.spring_java.model.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository repository;

    @Test
    void deveSalvarEConsultarProduto() {
        Produto produto = repository.save(new Produto("Notebook", 3500.0));

        assertNotNull(produto.getId());

        Produto encontrado = repository.findById(produto.getId()).orElseThrow();

        assertEquals("Notebook", encontrado.getNome());
        assertEquals(3500.0, encontrado.getPreco());
    }

    @Test
    void deveListarProdutosComPaginacao() {
        repository.save(new Produto("Notebook", 3500.0));
        repository.save(new Produto("Mouse", 120.0));

        Page<Produto> pagina = repository.findAll(PageRequest.of(0, 5));

        assertEquals(2, pagina.getTotalElements());
        assertTrue(pagina.getContent().stream()
                .anyMatch(produto -> produto.getNome().equals("Notebook")));
        assertTrue(pagina.getContent().stream()
                .anyMatch(produto -> produto.getNome().equals("Mouse")));
    }

    @Test
    void deveExcluirProduto() {
        Produto produto = repository.save(new Produto("Teclado", 200.0));
        Long id = produto.getId();

        repository.deleteById(id);

        assertTrue(repository.findById(id).isEmpty());
    }
}
