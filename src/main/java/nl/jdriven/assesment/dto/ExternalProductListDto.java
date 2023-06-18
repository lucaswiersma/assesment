package nl.jdriven.assesment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalProductListDto {

    @NotEmpty
    List<@Valid ExternalProductDto> products;

    public List<ExternalProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ExternalProductDto> products) {
        this.products = products;
    }
}
