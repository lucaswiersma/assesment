package nl.jdriven.assesment.controller;

import nl.jdriven.assesment.dto.ProductDto;
import nl.jdriven.assesment.model.Product;
import nl.jdriven.assesment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ShoppingApiController {

    private final ProductService productService;

    public ShoppingApiController(@Autowired ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtoList =  products.stream()
                .map(product -> product.toProductDto())
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public CompletableFuture<Page<Product>> searchProducts(
            @RequestParam("term") String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.searchProducts(searchTerm, PageRequest.of(page, size));
    }
}
