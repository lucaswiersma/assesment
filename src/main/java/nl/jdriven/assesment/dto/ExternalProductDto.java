package nl.jdriven.assesment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import nl.jdriven.assesment.model.Product;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalProductDto {

    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private BigDecimal price;
    private String description;

    public ExternalProductDto() {
    }

    public ExternalProductDto(Long id, String title, BigDecimal price, String description) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product toProduct(){
        return new Product(id, title, description, price);
    }
}
