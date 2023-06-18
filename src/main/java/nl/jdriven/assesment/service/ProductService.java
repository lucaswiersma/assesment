package nl.jdriven.assesment.service;

import nl.jdriven.assesment.model.Product;
import nl.jdriven.assesment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductService {

    ProductRepository repository;

    public ProductService(@Autowired ProductRepository repository) {
        this.repository = repository;
    }

    public Product getProduct(long id){
        return repository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts(){
        return repository.findAll();
    }

    @Async
    public CompletableFuture<Page<Product>> searchProducts(String searchTerm, Pageable pageable) {
        Page<Product> result = repository.searchByNameOrDescription(searchTerm, pageable);
        return CompletableFuture.completedFuture(result);
    }


}
