package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Conta;

public class CriaContaComSaldo {
	public static void main(String[] args) {
		// Criando EntityManger para executar funções da JPA
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		// Criando Conta - Estado do Objeto ainda é Transient.
		// Transient = Objeto apenas em memória, apenas no mundo java, JPA não conhece
		// esse objeto ainda!
		Conta conta = new Conta();
		conta.setTitular("Juliano");
		conta.setNumero(12345);
		conta.setAgencia(54321);
		conta.setSaldo(500.00);

		// Abrindo uma transação. É obrigatório para Criar, Alterar e Deletar um Objeto
		// no Banco!
		em.getTransaction().begin();

		// Mudando o estado do objeto para Managed.
		// Agora a JPA conhece o objeto e passa a gerneciá-lo
		// Objeto e JPA estão sincronizados! O que mudar no Objeto reflete no Banco!
		em.persist(conta);

		// Executando de forma atômica a transação.
		em.getTransaction().commit();
	}
}
