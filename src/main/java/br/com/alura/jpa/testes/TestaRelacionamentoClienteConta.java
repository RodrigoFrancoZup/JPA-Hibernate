package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.JoinColumn;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Cliente;
import br.com.alura.jpa.modelo.Conta;

public class TestaRelacionamentoClienteConta {

	public static void main(String[] args) {
		Conta conta = new Conta();
		conta.setId(1l);

		Cliente cliente = new Cliente();
		cliente.setNome("Rodrigo Lima");
		cliente.setEndereco("Rua Palmeiras");
		cliente.setProfissao("Policia");
		cliente.setConta(conta);
		
		Cliente cliente2 = new Cliente();
		cliente.setNome("Roberto Lima");
		cliente.setEndereco("Rua São Paulo");
		cliente.setProfissao("Bombeiro");
		cliente.setConta(conta);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		//O trecho abaixo roda, mesmo o atributo Conta anotado @OneToOne
		//Na teoria uma mesma conta só devia ter UM CLIENTE!
		//@OneToOne não cria a regra UNIQUE na chave estrangeira!
		/* 
		 em.getTransaction().begin();
		 em.persist(cliente);
		 em.persist(cliente2);
		 em.getTransaction().commit();
		*/
		
		//Adicionar @JoinColumn(unique = true) no atributo conta na classe Cliente
		//Com isso o trecho para de funcionar!
		//Atenção! @JoinColumn SÓ FUNCIONA após deletar tabela e criar novamente!
		
		 em.getTransaction().begin();
		 em.persist(cliente);
		 em.getTransaction().commit();
	}
}
