package client;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import objects.Categoria;
import objects.Produto;

public class Client {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static Scanner scanner;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("jpa");
        em = emf.createEntityManager();
        scanner = new Scanner(System.in);
        
        int opcaoPrincipal;

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Gerenciar Categorias");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("0. Sair");
            System.out.print("Escolha a funcionalidade desejada: ");
            opcaoPrincipal = scanner.nextInt();
            scanner.nextLine();

            switch (opcaoPrincipal) {
                case 1:
                    menuCategoria();
                    break;
                case 2:
                    menuProduto();
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcaoPrincipal != 0);

        scanner.close();
        em.close();
        emf.close();
    }

    private static void menuCategoria() {
        int opcao;
        do {
            System.out.println("\nMENU DE MANIPULAÇÃO - CATEGORIA");
            System.out.println("1. Inserir Categoria");
            System.out.println("2. Consultar Categorias");
            System.out.println("3. Remover Categoria");
            System.out.println("0. Voltar");
            System.out.print("Escolha a funcionalidade desejada: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Código: ");
                    Integer cod = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (em.find(Categoria.class, cod) != null) {
                        System.out.println("ERRO: Já existe uma categoria com o código " + cod + "!");
                    } else {
                        System.out.print("Descrição: ");
                        String desc = scanner.nextLine();
                        System.out.print("Ativo (Sim/Não): ");
                        String ativo = scanner.nextLine();

                        Categoria novaCategoria = new Categoria(cod, desc, ativo);
                        
                        em.getTransaction().begin();
                        em.persist(novaCategoria);
                        em.getTransaction().commit();
                        System.out.println("Categoria salva no banco de dados!");
                    }
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
                    break;
                    
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void menuProduto() {
        int opcao;
        do {
            System.out.println("\nMENU DE MANIPULAÇÃO - PRODUTO");
            System.out.println("1. Inserir Produto");
            System.out.println("2. Consultar Produtos");
            System.out.println("3. Remover Produto");
            System.out.println("0. Voltar");
            System.out.print("Escolha a funcionalidade desejada: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Código do Produto: ");
                    Integer cod = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (em.find(Produto.class, cod) != null) {
                        System.out.println("ERRO: Já existe um produto com o código " + cod + "!");
                    } else {
                        System.out.print("Nome do Produto: ");
                        String nome = scanner.nextLine();
                        System.out.print("Código da Categoria: ");
                        Integer categoriaId = scanner.nextInt();
                        scanner.nextLine();

                        Produto novoProduto = new Produto();
                        novoProduto.setCod(cod);
                        novoProduto.setNome(nome);
                        novoProduto.setCategoria(categoriaId);
                        
                        em.getTransaction().begin();
                        em.persist(novoProduto);
                        em.getTransaction().commit();
                        System.out.println("Produto salvo no banco de dados!");
                    }
                    break;

                case 2:
                    List<Produto> lista = em.createQuery("FROM Produto", Produto.class).getResultList();
                    if (lista.isEmpty()) {
                        System.out.println("Nenhum produto encontrado no banco.");
                    } else {
                        for (Produto p : lista) {
                            System.out.println(p.toString());
                        }
                    }
                    break;

                case 3:
                    System.out.print("Digite o Código do produto para remover: ");
                    Integer codRemover = scanner.nextInt();
                    
                    Produto prodRemover = em.find(Produto.class, codRemover);
                    
                    if (prodRemover != null) {
                        em.getTransaction().begin();
                        em.remove(prodRemover);
                        em.getTransaction().commit();
                        System.out.println("Produto removido do banco com sucesso!");
                    } else {
                        System.out.println("Código não encontrado no banco de dados.");
                    }
                    break;
                    
                case 0:
                    break;
                    
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}