package com.fjlima.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fjlima.cursomc.domain.Categoria;
import com.fjlima.cursomc.domain.Cidade;
import com.fjlima.cursomc.domain.Cliente;
import com.fjlima.cursomc.domain.Endereco;
import com.fjlima.cursomc.domain.Estado;
import com.fjlima.cursomc.domain.ItemPedido;
import com.fjlima.cursomc.domain.Pagamento;
import com.fjlima.cursomc.domain.PagamentoComBoleto;
import com.fjlima.cursomc.domain.PagamentoComCartao;
import com.fjlima.cursomc.domain.Pedido;
import com.fjlima.cursomc.domain.Produto;
import com.fjlima.cursomc.domain.enums.EstadoPagamento;
import com.fjlima.cursomc.domain.enums.TipoCliente;
import com.fjlima.cursomc.repository.CategoriaRepository;
import com.fjlima.cursomc.repository.CidadeRepository;
import com.fjlima.cursomc.repository.ClienteRepository;
import com.fjlima.cursomc.repository.EstadoRepository;
import com.fjlima.cursomc.repository.ItemPedidoRepository;
import com.fjlima.cursomc.repository.PagamentoRepository;
import com.fjlima.cursomc.repository.PedidoRepository;
import com.fjlima.cursomc.repository.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", new Double(2000.00));
		Produto p2 = new Produto(null, "Impressora", new Double(800.00));
		Produto p3 = new Produto(null, "Mouse", new Double(80.00));

		/*
		 * cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		 * cat2.getProdutos().add(p2);
		 */

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		estadoRepository.saveAll(Arrays.asList(est1, est2));

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "1234455", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "99999", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.save(cli1);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped1, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

	}

}
