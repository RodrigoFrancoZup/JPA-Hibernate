package br.com.alura.jpa.testes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Conta;
import br.com.alura.jpa.modelo.Movimentacao;
import br.com.alura.jpa.modelo.TipoMovimentacao;

public class TestaRelacionamento {
	
	public static void main(String[] args) {
		//Montando a conta
		Conta conta = new Conta();
		conta.setTitular("Rodrigo");
		conta.setNumero(142536);
		conta.setAgencia(35);
		conta.setSaldo(3000.00);
		
		//Montando a movimentacao
		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setData(LocalDateTime.now());
		movimentacao.setDescricao("Conta do Bar");
		movimentacao.setValor(new BigDecimal(150.00));
		movimentacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacao.setConta(conta);
		
		// Criando EntityManger para executar funções da JPA
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		//Código a baixo dará erro
		//Pq movimentacao tem um atributo que esta no estado TRANSIENT
		//Movimentacao tem atributo do tipo CONTA e JPA não gerencia esse Objeto
		//Para dar certo o atributo tem que ser no minimo DETACHED
		/*
		em.getTransaction().begin();
		em.persist(movimentacao);
		em.getTransaction().commit();
		em.close();
		*/
		
		//Para rodar devemos fazer Conta ser Managed primeiro;
		//Para isso podemos salvar primeiro a conta, depois Movimentacao
		//Agora vai rodar:
		
		em.getTransaction().begin();
		em.persist(conta);
		em.persist(movimentacao);
		em.getTransaction().commit();
		em.close();

	}

}
