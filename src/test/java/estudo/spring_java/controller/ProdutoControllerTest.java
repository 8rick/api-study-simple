package estudo.spring_java.controller;

import estudo.spring_java.dto.DadosDetalhamentoProduto;
import estudo.spring_java.infra.exception.ProdutoNaoEncontradoException;
import estudo.spring_java.model.Produto;
import estudo.spring_java.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProdutoService service;

    @Test
    void deveCriarProdutoQuandoDadosForemValidos() throws Exception {
        Produto produto = new Produto("Notebook", 3500.0);
        produto.setId(1L);

        when(service.salvar(any(Produto.class))).thenReturn(produto);

        mockMvc.perform(post("/produtos")
                        .contentType("application/json")
                        .content("""
                                {
                                  "nome": "Notebook",
                                  "preco": 3500.0
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Notebook"))
                .andExpect(jsonPath("$.preco").value(3500.0));
    }

    @Test
    void deveRetornar400QuandoNomeForInvalido() throws Exception {
        mockMvc.perform(post("/produtos")
                        .contentType("application/json")
                        .content("""
                                {
                                  "nome": "",
                                  "preco": 100.0
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar400QuandoPrecoForInvalido() throws Exception {
        mockMvc.perform(post("/produtos")
                        .contentType("application/json")
                        .content("""
                                {
                                  "nome": "Notebook",
                                  "preco": 0
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveListarProdutos() throws Exception {
        Produto produto = new Produto("Notebook", 3500.0);
        produto.setId(1L);

        DadosDetalhamentoProduto detalhe = new DadosDetalhamentoProduto(produto);
        when(service.listarProdutos(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(detalhe)));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].nome").value("Notebook"))
                .andExpect(jsonPath("$.content[0].preco").value(3500.0));
    }

    @Test
    void deveAtualizarProduto() throws Exception {
        Produto produto = new Produto("Notebook Gamer", 4500.0);
        produto.setId(1L);

        when(service.atualizar(any(Long.class), any(Produto.class))).thenReturn(produto);

        mockMvc.perform(put("/produtos/1")
                        .contentType("application/json")
                        .content("""
                                {
                                  "nome": "Notebook Gamer",
                                  "preco": 4500.0
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Notebook Gamer"))
                .andExpect(jsonPath("$.preco").value(4500.0));

        verify(service).atualizar(any(Long.class), any(Produto.class));
    }

    @Test
    void deveDeletarProduto() throws Exception {
        mockMvc.perform(delete("/produtos/1"))
                .andExpect(status().isOk());

        verify(service).deletar(1L);
    }

    @Test
    void deveRetornar404AoDeletarProdutoInexistente() throws Exception {
        doThrow(new ProdutoNaoEncontradoException("Produto com ID 999 não foi encontrado!"))
                .when(service).deletar(999L);

        mockMvc.perform(delete("/produtos/999"))
                .andExpect(status().isNotFound());
    }
}
