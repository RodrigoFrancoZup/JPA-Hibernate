package br.com.alura.jpa.testes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Categoria;
import br.com.alura.jpa.modelo.Conta;
import br.com.alura.jpa.modelo.Movimentacao;
import br.com.alura.jpa.modelo.TipoMovimentacao;

public class TestaRelacionamentoMovimentacaoCategoria {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		Categoria categoria = new Categoria("Viagem");
		Categoria categoria2 = new Categoria("Negocio");

		Conta conta = new Conta();
		conta.setId(2L);

		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setDescricao("Viagem � S�o Paulo");
		movimentacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacao.setData(LocalDateTime.now());
		movimentacao.setValor(new BigDecimal(300.00));
		movimentacao.setCategorias(Arrays.asList(categoria, categoria2));

		Movimentacao movimentacao2 = new Movimentacao();
		movimentacao2.setDescricao("Viagem � Minas Gerais");
		movimentacao2.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacao2.setData(LocalDateTime.now());
		movimentacao2.setValor(new BigDecimal(400.0));
		movimentacao2.setCategorias(Arrays.asList(categoria, categoria2));

		movimentacao.setConta(conta);
		movimentacao2.setConta(conta);

		em.getTransaction().begin();
		em.persist(categoria);
		em.persist(categoria2);
		em.persist(movimentacao);
		em.persist(movimentacao2);
		em.getTransaction().commit();
	}
}
