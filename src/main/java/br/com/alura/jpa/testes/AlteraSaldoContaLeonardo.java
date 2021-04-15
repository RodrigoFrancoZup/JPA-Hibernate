package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Conta;

public class AlteraSaldoContaLeonardo {

	public static void main(String[] args) {
		// Criando EntityManger para executar fun��es da JPA
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();
		
		
		//O estado dessa conta � Managed.
		//Buscou do banco o Objeto volta Managed.
		//Managed = Objeto Sincronizado com o JPA(Banco)
		//O que eu mudar nesse objeto, mudar� no banco.
		Conta contaDoLeonardo = em.find(Conta.class, 1L);
		
		//Abrindo transa��o.
		em.getTransaction().begin();
		
		//Atulizando a conta do Leonard
		contaDoLeonardo.setSaldo(1000.00);
		
		//Executando Transa��o
		em.getTransaction().commit();
	}
}
