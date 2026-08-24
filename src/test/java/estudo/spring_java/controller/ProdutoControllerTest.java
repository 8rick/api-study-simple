package estudo.spring_java.controller;

import estudo.spring_java.infra.exception.ProdutoNaoEncontradoException;
import estudo.spring_java.model.Produto;
import estudo.spring_java.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.mockmvc.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProdutoController.class)
@AutoConfigureMockMvc
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].mensagem").exists());
    }

    @Test
    void deveRetornar404AoDeletarProdutoInexistente() throws Exception {
        doThrow(new ProdutoNaoEncontradoException("Produto com ID 999 não foi encontrado!"))
                .when(service).deletar(999L);

        mockMvc.perform(delete("/produtos/999"))
                .andExpect(status().isNotFound());
    }
}
