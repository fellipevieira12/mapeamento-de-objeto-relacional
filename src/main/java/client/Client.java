package client;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import objects.Categoria;

public class Client {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- MENU DE MANIPULAÇÃO (JPA) ---");
            System.out.println("1. Inserir Categoria");
            System.out.println("2. Consultar Categorias");
            System.out.println("3. Remover Categoria");
            System.out.println("0. Sair");
            System.out.print("Escolha a funcionalidade desejada: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Código: ");
                    Integer cod = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Descrição: ");
                    String desc = scanner.nextLine();
                    System.out.print("Ativo (S/N): ");
                    String ativo = scanner.nextLine();

                    Categoria novaCategoria = new Categoria(cod, desc, ativo);
                    
                    em.getTransaction().begin();
                    em.persist(novaCategoria);
                    em.getTransaction().commit();
                    System.out.println("Categoria salva no banco de dados!");
                    break;

                case 2:
                    List<Categoria> lista = em.createQuery("FROM Categoria", Categoria.class).getResultList();
                    if (lista.isEmpty()) {
                        System.out.println("Nenhum registro encontrado no banco.");
                    } else {
                        for (Categoria c : lista) {
                            System.out.println(c.toString());
                        }
                    }
                    break;

                case 3:
                    System.out.print("Digite o Código da categoria para remover: ");
                    Integer codRemover = scanner.nextInt();
                    
                    // Busca o objeto no banco antes de remover
                    Categoria catRemover = em.find(Categoria.class, codRemover);
                    
                    if (catRemover != null) {
                        em.getTransaction().begin();
                        em.remove(catRemover);
                        em.getTransaction().commit();
                        System.out.println("Registro removido do banco com sucesso!");
                    } else {
                        System.out.println("Código não encontrado no banco de dados.");
                    }
                    break;
                    
                case 0:
                    System.out.println("Encerrando...");
                    break;
                    
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
        em.close();
        emf.close();
    }
}