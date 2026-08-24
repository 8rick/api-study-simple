package estudo.spring_java.controller;

import estudo.spring_java.dto.ProdutoDTO;
import estudo.spring_java.model.Produto;
import estudo.spring_java.service.ProdutoService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public Page<?> listarTodos(
            @ParameterObject
            @PageableDefault(size = 5, sort = "nome") Pageable paginacao) {
        return service.listarProdutos(paginacao);
    }

    @PostMapping
    public Produto criarProduto(@Valid @RequestBody ProdutoDTO dto) {
        Produto novoProduto = new Produto(dto.getNome(), dto.getPreco());
        return service.salvar(novoProduto);
    }

    @PutMapping("/{id}")
    public Produto atualizarProduto(
            @PathVariable Long id,
            @RequestBody Produto produto) {
        return service.atualizar(id, produto);
    }

    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id) {
        service.deletar(id);
    }
}
