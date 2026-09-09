package objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Produto {

    @Id
    private Integer cod;

    @Column(name="nomeprod")
    private String nome;

    @Column(name="categprod")
    private Integer categoria;

    // Construtor vazio necessário ao Hibernate[cite: 1]
    public Produto() {
    }

    public Produto(Integer cod, String nome, Integer categoria) {
        this.cod = cod;
        this.nome = nome;
        this.categoria = categoria;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Produto [cod=" + cod + ", nome=" + nome + ", categoria=" + categoria + "]";
    }
}