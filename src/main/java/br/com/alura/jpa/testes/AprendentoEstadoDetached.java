package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Conta;

public class AprendentoEstadoDetached {
	
	public static void main(String[] args) {
		
		// Criando EntityManger para executar funções da JPA
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		// Criando Conta - Estado do Objeto ainda é Transient.
		// Transient = Objeto apenas em memória, apenas no mundo java, JPA não conhece
		// esse objeto ainda!
		Conta conta = new Conta();
		conta.setTitular("Reinaldo");
		conta.setNumero(12345);
		conta.setAgencia(54321);
		conta.setSaldo(700.00);

		// Abrindo uma transação. É obrigatório para Criar, Alterar e Deletar um Objeto
		// no Banco!
		em.getTransaction().begin();

		// Mudando o estado do objeto para Managed.
		// Agora a JPA conhece o objeto e passa a gerneciá-lo
		// Objeto e JPA estão sincronizados! O que mudar no Objeto reflete no Banco!
		em.persist(conta);

		// Executando de forma atômica a transação.
		em.getTransaction().commit();
		
		//Fechando o ENTITY MANAGER
		//Objetos que eram gerenciados por esse EM, perdem o gerenciamento
		//Objetos que eram gerenciados por esse EM, perdem a sincronização
		//Objetos que eram gerenciados por esse EM, passam a ter o estado DETACHED
		em.close();
		
		
		//Não haverá mudança no banco!
		//Não atualiza, pq objeto não ta sincronizado
		//O EM desse objeto não existe mais!
		conta.setTitular("Rei");
		
		//Comando roda, mas pq mostra ID se não há mais a sincronização com JAP?
		//Esse objeto já foi Managed, e ele ganhou ID na época que era Managed!
		System.out.println("O id de Reinaldo: " + conta.getId() );
		
		// Criando novo ENTITY MANAGER, O em2
		EntityManager em2 = emf.createEntityManager();
		
		//Criando transação
		em2.getTransaction().begin();;
		
		// Metodo que transforma o estado de objeto de Detached para Manager
		// O Objeto volta a ser gerenciado pela JPA
		// Volta a ter Sincronização entre Objeto e JPA
		// Atenção: Os dados que coloquei (passado) nesse objeto vai refletir no banco
		// Nome do titular vai mudar de Reinaldo para Rei
		// Mudanças Futuras, depois do código vão refletir no banco
		em2.merge(conta);
		
		// Executando Transaçao
		em2.getTransaction().commit();
	}

}
