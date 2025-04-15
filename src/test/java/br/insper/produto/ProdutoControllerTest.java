package br.insper.produto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class)
@Import(SecurityConfig.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetProdutos() throws Exception {
        Produto produto = new Produto();
        produto.setId("1");
        produto.setNome("Produto Teste");
        produto.setPreco(100.0);
        produto.setQuantidade(15);

        when(produtoService.listar()).thenReturn(Arrays.asList(produto));

        // GET sem autenticação deve retornar 200 conforme nossa SecurityConfig
        mockMvc.perform(get("/produtos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Produto Teste"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCadastrarProdutoComAdmin() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Produto Novo");
        produto.setPreco(200.0);
        produto.setQuantidade(8);

        when(produtoService.cadastrar(any(Produto.class))).thenReturn(produto);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Novo"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testCadastrarProdutoSemAdmin() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Produto Novo");
        produto.setPreco(200.0);
        produto.setQuantidade(8);

        // Usuário sem role ADMIN deverá receber 403 Forbidden
        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isForbidden());
    }
}
