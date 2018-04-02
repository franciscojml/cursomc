package com.fjlima.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class DBService {

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

	public void instantiateTestDatabase() throws ParseException {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		Produto p1 = new Produto(null, "Computador", new Double(2000.00));
		Produto p2 = new Produto(null, "Impressora", new Double(800.00));
		Produto p3 = new Produto(null, "Mouse", new Double(80.00));
		Produto p4 = new Produto(null, "Mesa de Escritório", new Double(300.00));
		Produto p5 = new Produto(null, "Toalha", new Double(50.00));
		Produto p6 = new Produto(null, "Colcha", new Double(200.00));
		Produto p7 = new Produto(null, "TV true color", new Double(1200.00));
		Produto p8 = new Produto(null, "Roçadeira", new Double(800.00));
		Produto p9 = new Produto(null, "Abajour", new Double(100.00));
		Produto p10 = new Produto(null, "Pendente", new Double(180.00));
		Produto p11 = new Produto(null, "Shampoo", new Double(90.00));

		/*
		 * cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		 * cat2.getProdutos().add(p2);
		 */

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		estadoRepository.saveAll(Arrays.asList(est1, est2));

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "franciscojml@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
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
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}
