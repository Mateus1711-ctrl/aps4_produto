package br.insper.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvarProduto(Produto produto) {
        // Verifica se o produto com mesmo id já existe
        if (produtoRepository.existsById(produto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto com esse ID já existe");
        }
        return produtoRepository.save(produto);
    }

    public Produto getProduto(String id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto decrementarEstoque(String id, int quantidade) {
        Produto produto = getProduto(id);
        if (produto.getQuantidade() < quantidade) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente para o produto " + id);
        }
        produto.setQuantidade(produto.getQuantidade() - quantidade);
        return produtoRepository.save(produto);
    }
}
