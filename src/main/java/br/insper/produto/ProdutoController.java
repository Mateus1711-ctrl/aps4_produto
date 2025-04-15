package br.insper.produto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // GET: Aberto a qualquer usuário autenticado (ou público, conforme a política da aplicação de usuário)
    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoService.listar();
        return ResponseEntity.ok(produtos);
    }

    // POST: Restrito a usuários ADMIN (verificado na SecurityConfig)
    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody Produto produto) {
        Produto cadastrado = produtoService.cadastrar(produto);
        return ResponseEntity.ok(cadastrado);
    }
}
