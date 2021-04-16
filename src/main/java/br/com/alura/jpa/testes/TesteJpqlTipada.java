package br.com.alura.jpa.testes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.alura.jpa.modelo.Conta;
import br.com.alura.jpa.modelo.Movimentacao;

public class TesteJpqlTipada {
	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		// JPQL nao esta perfeita, temos que sumir com esse .id
		// .id lembra muito ainda entidade-relacionamento
		// String JPQL = "SELECT m from Movimentacao m WHERE m.conta.id = 2";

		/* Montando a JPQL boa, bem Orientada a Objeto! */
		/* Sumindo com o .id e vamos passar o objeto conta a baixo: */
		/* Temos que criar o objeto conta então! */
		Conta conta = new Conta();
		conta.setId(2L);

		// JPQL Orientada a Objeto!
		String JPQL = "SELECT m from Movimentacao m WHERE m.conta = :pConta order by m.valor desc";

		// Monta a query Tipada
		TypedQuery<Movimentacao> query = em.createQuery(JPQL, Movimentacao.class);
		// Vai trocar na Query :pConta pelo objeto conta!
		query.setParameter("pConta", conta);

		// Executa a query e pega resultado
		List<Movimentacao> resultList = query.getResultList();

		for (Movimentacao movimentacao : resultList) {
			System.out.println("Descrição: " + movimentacao.getDescricao());
			System.out.println("Tipo: " + movimentacao.getTipoMovimentacao());
			System.out.println("Valor: " + movimentacao.getValor());
		}
	}
}
