package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Conta;

public class AprendentoEstadoDetached {
	
	public static void main(String[] args) {
		
		// Criando EntityManger para executar fun��es da JPA
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		// Criando Conta - Estado do Objeto ainda � Transient.
		// Transient = Objeto apenas em mem�ria, apenas no mundo java, JPA n�o conhece
		// esse objeto ainda!
		Conta conta = new Conta();
		conta.setTitular("Reinaldo");
		conta.setNumero(12345);
		conta.setAgencia(54321);
		conta.setSaldo(700.00);

		// Abrindo uma transa��o. � obrigat�rio para Criar, Alterar e Deletar um Objeto
		// no Banco!
		em.getTransaction().begin();

		// Mudando o estado do objeto para Managed.
		// Agora a JPA conhece o objeto e passa a gerneci�-lo
		// Objeto e JPA est�o sincronizados! O que mudar no Objeto reflete no Banco!
		em.persist(conta);

		// Executando de forma at�mica a transa��o.
		em.getTransaction().commit();
		
		//Fechando o ENTITY MANAGER
		//Objetos que eram gerenciados por esse EM, perdem o gerenciamento
		//Objetos que eram gerenciados por esse EM, perdem a sincroniza��o
		//Objetos que eram gerenciados por esse EM, passam a ter o estado DETACHED
		em.close();
		
		
		//N�o haver� mudan�a no banco!
		//N�o atualiza, pq objeto n�o ta sincronizado
		//O EM desse objeto n�o existe mais!
		conta.setTitular("Rei");
		
		//Comando roda, mas pq mostra ID se n�o h� mais a sincroniza��o com JAP?
		//Esse objeto j� foi Managed, e ele ganhou ID na �poca que era Managed!
		System.out.println("O id de Reinaldo: " + conta.getId() );
		
		// Criando novo ENTITY MANAGER, O em2
		EntityManager em2 = emf.createEntityManager();
		
		//Criando transa��o
		em2.getTransaction().begin();;
		
		// Metodo que transforma o estado de objeto de Detached para Manager
		// O Objeto volta a ser gerenciado pela JPA
		// Volta a ter Sincroniza��o entre Objeto e JPA
		// Aten��o: Os dados que coloquei (passado) nesse objeto vai refletir no banco
		// Nome do titular vai mudar de Reinaldo para Rei
		// Mudan�as Futuras, depois do c�digo v�o refletir no banco
		em2.merge(conta);
		
		// Executando Transa�ao
		em2.getTransaction().commit();
	}

}
