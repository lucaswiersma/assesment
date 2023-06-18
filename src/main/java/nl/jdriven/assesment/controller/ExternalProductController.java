package nl.jdriven.assesment.controller;

import jakarta.validation.Valid;
import nl.jdriven.assesment.dto.ExternalProductDto;
import nl.jdriven.assesment.dto.ExternalProductListDto;
import nl.jdriven.assesment.service.ExternalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/externalproducts")
public class ExternalProductController {

    private ExternalProductService productService;

    private static final String BASE_PATH = "/api/products";
    private static final String PRODUCT_PATH = "/api/products/%s";

    public ExternalProductController(@Autowired  ExternalProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/addproduct")
    public ResponseEntity<ExternalProductDto> addOrUpdateProduct(@RequestBody @Valid ExternalProductDto productToUpdate) {
        try {
            productService.addOrUpdateExternalProduct(productToUpdate);
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        URI uri = URI.create(String.format(PRODUCT_PATH, productToUpdate.getId()));
        return ResponseEntity.created(uri).body(productToUpdate);
    }

    @PostMapping("/addproducts")
    public ResponseEntity<Void> addProducts(@RequestBody @Valid ExternalProductListDto productsToUpdate) {
        productService.addOrUpdateExternalProducts(productsToUpdate.getProducts());
        return ResponseEntity.created(URI.create(BASE_PATH)).build();
    }

}
