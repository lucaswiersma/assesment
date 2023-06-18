package nl.jdriven.assesment.model;

import jakarta.persistence.*;
import nl.jdriven.assesment.dto.ProductDto;

import java.math.BigDecimal;

@Entity
@Table(name = "products",
       indexes = {@Index(name = "product_index",  columnList="name,description")})
public class Product {
    @Id
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    private BigDecimal price;

    public Product() {
    }

    public Product(Long productId, String name, String description, BigDecimal price) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long externalId) {
        this.productId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductDto toProductDto(){
        return new ProductDto(productId, name, description, price);
    }
}