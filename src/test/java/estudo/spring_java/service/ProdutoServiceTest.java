package estudo.spring_java.service;

import estudo.spring_java.infra.exception.ProdutoNaoEncontradoException;
import estudo.spring_java.model.Produto;
import estudo.spring_java.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    @Test
    void deveSalvarProduto() {
        Produto produto = new Produto("Notebook", 3500.0);
        produto.setId(1L);

        when(repository.save(produto)).thenReturn(produto);

        Produto resultado = service.salvar(produto);

        assertEquals(1L, resultado.getId());
        assertEquals("Notebook", resultado.getNome());
        assertEquals(3500.0, resultado.getPreco());
        verify(repository).save(produto);
    }

    @Test
    void deveListarProdutosComPaginacao() {
        Produto produto = new Produto("Notebook", 3500.0);
        produto.setId(1L);
        Page<Produto> pagina = new PageImpl<>(List.of(produto));
        PageRequest paginacao = PageRequest.of(0, 5);

        when(repository.findAll(paginacao)).thenReturn(pagina);

        Page<?> resultado = service.listarProdutos(paginacao);

        assertEquals(1, resultado.getTotalElements());
        verify(repository).findAll(paginacao);
    }

    @Test
    void deveAtualizarProdutoExistente() {
        Produto produtoExistente = new Produto("Notebook", 3000.0);
        produtoExistente.setId(1L);
        Produto dadosAtualizados = new Produto("Notebook Gamer", 4500.0);

        when(repository.findById(1L)).thenReturn(Optional.of(produtoExistente));
        when(repository.save(produtoExistente)).thenReturn(produtoExistente);

        Produto resultado = service.atualizar(1L, dadosAtualizados);

        assertEquals(1L, resultado.getId());
        assertEquals("Notebook Gamer", resultado.getNome());
        assertEquals(4500.0, resultado.getPreco());
        verify(repository).findById(1L);
        verify(repository).save(produtoExistente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(
                ProdutoNaoEncontradoException.class,
                () -> service.atualizar(999L, new Produto("Produto", 100.0))
        );

        verify(repository).findById(999L);
        verify(repository, never()).save(any());
    }

    @Test
    void deveDeletarProdutoExistente() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deletar(1L);

        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarProdutoInexistente() {
        when(repository.existsById(999L)).thenReturn(false);

        assertThrows(
                ProdutoNaoEncontradoException.class,
                () -> service.deletar(999L)
        );

        verify(repository).existsById(999L);
        verify(repository, never()).deleteById(999L);
    }
}
