package br.insper.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto) {
        return produtoService.salvarProduto(produto);
    }

  
    @GetMapping("/{id}")
    public Produto obterProduto(@PathVariable String id) {
        return produtoService.getProduto(id);
    }

  
    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos();
    }

    
    @PutMapping("/{id}/decrement")
    public Produto decrementarEstoque(@PathVariable String id, @RequestParam int quantidade) {
        return produtoService.decrementarEstoque(id, quantidade);
    }
}
