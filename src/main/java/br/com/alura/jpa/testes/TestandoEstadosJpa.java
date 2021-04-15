package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Conta;

public class TestandoEstadosJpa {

	public static void main(String[] args) {
		
		//Como j� visto, � um Objeto do estado Transient
		//Pois � apenas um Java Bean
		//Java Bean = Objeto com GET AND SETTERS
		Conta conta = new Conta();
		conta.setTitular("Alfredo");
		conta.setNumero(12345);
		conta.setAgencia(54321);
		conta.setSaldo(500.00);
		
		//CRIANDO ENTITY MANAGER
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();
		
		//Abrindo Transa��o
		em.getTransaction().begin();
		
		// Transient -> Managed
		em.persist(conta);
		
		// Objeto vai para o estado REMOVED
		// Managed -> Removed
		// Objeto � removido do contexto do JPA
		// Objeto n�o est� mais sincronizado com JPA
		// Registro do banco tamb�m � removido!
		em.remove(conta);
		
		//Executando a Transa��o
		em.getTransaction().commit();
	}
}
