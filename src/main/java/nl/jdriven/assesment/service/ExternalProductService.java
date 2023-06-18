package nl.jdriven.assesment.service;

import nl.jdriven.assesment.dto.ExternalProductDto;
import nl.jdriven.assesment.model.Product;
import nl.jdriven.assesment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExternalProductService {

    private ProductRepository productRepository;
    public ExternalProductService(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ExternalProductDto addOrUpdateExternalProduct(ExternalProductDto externalProduct){
        productRepository.save(externalProduct.toProduct());
        return null;
    }

    public void addOrUpdateExternalProducts(List<ExternalProductDto> externalProducts){
        List<Product> products = externalProducts.stream().map(p -> p.toProduct()).collect(Collectors.toList());
        productRepository.saveAll(products);
    }
}
