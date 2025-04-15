package br.insper.produto;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    public ProdutoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarProdutos() {
        Produto produto = new Produto();
        produto.setId("1");
        produto.setNome("Camiseta");
        produto.setPreco(50.0);
        produto.setQuantidade(10);

        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto));

        List<Produto> produtos = produtoService.listar();
        assertNotNull(produtos);
        assertEquals(1, produtos.size());
        assertEquals("Camiseta", produtos.get(0).getNome());
    }

    @Test
    public void testCadastrarProduto() {
        Produto produto = new Produto();
        produto.setNome("Tênis");
        produto.setPreco(120.0);
        produto.setQuantidade(5);

        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto cadastrado = produtoService.cadastrar(produto);
        assertNotNull(cadastrado);
        assertEquals("Tênis", cadastrado.getNome());
    }

    @Test
    public void testBuscarPorId() {
        Produto produto = new Produto();
        produto.setId("2");
        produto.setNome("Boné");
        produto.setPreco(30.0);
        produto.setQuantidade(20);

        when(produtoRepository.findById("2")).thenReturn(Optional.of(produto));

        Produto buscado = produtoService.buscarPorId("2");
        assertNotNull(buscado);
        assertEquals("Boné", buscado.getNome());
    }
}
