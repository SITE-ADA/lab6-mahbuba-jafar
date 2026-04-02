package az.edu.ada.wm2.lab6.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products;

    public Category() {
        this.id = UUID.randomUUID();
    }

    public Category(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}