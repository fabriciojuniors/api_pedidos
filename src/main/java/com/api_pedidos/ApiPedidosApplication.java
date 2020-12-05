package com.api_pedidos;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.api_pedidos.domain.Categoria;
import com.api_pedidos.domain.Cidade;
import com.api_pedidos.domain.Cliente;
import com.api_pedidos.domain.Endereco;
import com.api_pedidos.domain.Estado;
import com.api_pedidos.domain.ItemPedido;
import com.api_pedidos.domain.Pagamento;
import com.api_pedidos.domain.PagamentoComBoleto;
import com.api_pedidos.domain.PagamentoComCartao;
import com.api_pedidos.domain.Pedido;
import com.api_pedidos.domain.Produto;
import com.api_pedidos.domain.enums.EstadoPagamento;
import com.api_pedidos.domain.enums.TipoCliente;
import com.api_pedidos.repositories.CategoriaRepository;
import com.api_pedidos.repositories.CidadeRepository;
import com.api_pedidos.repositories.ClienteRepository;
import com.api_pedidos.repositories.EnderecoRepository;
import com.api_pedidos.repositories.EstadoRepository;
import com.api_pedidos.repositories.ItemPedidoRepository;
import com.api_pedidos.repositories.PagamentoRepository;
import com.api_pedidos.repositories.PedidoRepository;
import com.api_pedidos.repositories.ProdutoRepository;

@SpringBootApplication
public class ApiPedidosApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired 
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiPedidosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria c1 = new Categoria(null, "Informática");
		Categoria c2 = new Categoria(null, "Escritório");
		Categoria c3 = new Categoria(null, "Cama mesa e banho");
		Categoria c4 = new Categoria(null, "Jogos");
		Categoria c5 = new Categoria(null, "Esportes");
		Categoria c6 = new Categoria(null, "Roupas");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		c1.getProdutos().addAll(Arrays.asList(p1,p2, p3));
		c2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(c1));
		p2.getCategorias().addAll(Arrays.asList(c1, c2));
		p3.getCategorias().addAll(Arrays.asList(c1));
		
		Estado e1 = new Estado(null, "Minas Gerais");
		Estado e2 = new Estado(null, "São Paulo");
		
		Cidade cit1 = new Cidade(null, "Uberlandia", e1);
		Cidade cit2 = new Cidade(null, "São Paulo", e2);
		Cidade cit3 = new Cidade(null, "Campinas", e2);
		
		e1.getCidades().addAll(Arrays.asList(cit1));
		e2.getCidades().addAll(Arrays.asList(cit2, cit3));
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva","maria@email.com", "36378912377", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("27363323", "98838393"));
		
		Endereco end1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "3822083", cli1, cit1);
		Endereco end2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, cit1);
		
		cli1.getEndereco().addAll(Arrays.asList(end1, end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:31"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, end2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		ItemPedido IP1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido IP2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido IP3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(IP1, IP2));
		ped2.getItens().addAll(Arrays.asList(IP3));
		
		p1.getItens().addAll(Arrays.asList(IP1));
		p2.getItens().addAll(Arrays.asList(IP3));
		p3.getItens().addAll(Arrays.asList(IP2));
		
		categoriaRepository.saveAll(Arrays.asList(c1, c2,c3,c4,c5,c6));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		estadoRepository.saveAll(Arrays.asList(e1, e2));
		cidadeRepository.saveAll(Arrays.asList(cit1,cit2,cit3));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		itemPedidoRepository.saveAll(Arrays.asList(IP1, IP2, IP3));
	}

	
}
