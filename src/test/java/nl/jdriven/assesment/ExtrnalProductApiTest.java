package nl.jdriven.assesment;

import nl.jdriven.assesment.dto.ExternalProductDto;
import nl.jdriven.assesment.dto.ExternalProductListDto;
import nl.jdriven.assesment.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ExtrnalProductApiTest {

    private static final ExternalProductDto product = new ExternalProductDto(1l,
            "Product 1", new BigDecimal(100.0), "Description 1");
    private static final ExternalProductDto product2 = new ExternalProductDto(2l,
            "Product 1", new BigDecimal(100.0), "Description 2");
    private static final ExternalProductDto product3 = new ExternalProductDto(3l,
            "Product 1", new BigDecimal(100.0), "Description 3");

    @Value("${server.port}")
    private int configPort;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository repository;

    @AfterEach
    private void setup(){
        repository.delete(product.toProduct());
        repository.delete(product2.toProduct());
        repository.delete(product3.toProduct());
    }



    @Test
    public void addProductShouldAddProduct()  {
        String url = "http://localhost:" + configPort + "/api/externalproducts/addproduct";
        ResponseEntity<ExternalProductDto> addedProduct = restTemplate.postForEntity(url, product, ExternalProductDto.class);
        Assertions.assertEquals(HttpStatus.CREATED, addedProduct.getStatusCode());
    }

    @Test
    public void addProductWithoutABodyShouldGiveA400()  {
        String url = "http://localhost:" + configPort + "/api/externalproducts/addproduct";
        ResponseEntity<ExternalProductDto> addedProduct = restTemplate.postForEntity(url, new ExternalProductDto(), ExternalProductDto.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, addedProduct.getStatusCode());
    }

    @Test
    public void addProductWithInvalidABodyShouldGiveA400()  {
        String url = "http://localhost:" + configPort + "/api/externalproducts/addproduct";
        ExternalProductDto externalProductDto = new ExternalProductDto();
        externalProductDto.setTitle("Product without id");
        externalProductDto.setDescription("Product without id should give an error");
        externalProductDto.setPrice(new BigDecimal(1));
        ResponseEntity<ExternalProductDto> addedProduct = restTemplate.postForEntity(url, externalProductDto, ExternalProductDto.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, addedProduct.getStatusCode());
    }

    @Test
    public void addProductsShouldAddAllProducts(){
        String url = "http://localhost:" + configPort + "/api/externalproducts/addproducts";
        List<ExternalProductDto> externalProducts = new ArrayList<>();
        externalProducts.add(product);
        externalProducts.add(product2);
        externalProducts.add(product3);
        ExternalProductListDto externalProductListDto = new ExternalProductListDto();
        externalProductListDto.setProducts(externalProducts);
        ResponseEntity<Void> addedProducts = restTemplate.postForEntity(url, externalProductListDto, Void.class);
        Assertions.assertNotNull(addedProducts);
        Assertions.assertEquals(HttpStatus.CREATED, addedProducts.getStatusCode());
    }

    @Test
    public void addProductsWithEmptyBodyShouldGive400(){
        String url = "http://localhost:" + configPort + "/api/externalproducts/addproducts";
        ExternalProductListDto externalProductListDto = new ExternalProductListDto();
        ResponseEntity<Void> addedProducts = restTemplate.postForEntity(url, externalProductListDto, Void.class);
        Assertions.assertNotNull(addedProducts);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, addedProducts.getStatusCode());
    }

    @Test
    public void addProductsWithoutAValidBodyShouldGive400(){
        String url = "http://localhost:" + configPort + "/api/externalproducts/addproducts";
        ExternalProductDto externalProductDto = new ExternalProductDto();
        externalProductDto.setTitle("Product without id");
        externalProductDto.setDescription("Product without id should give an error");
        externalProductDto.setPrice(new BigDecimal(1));
        List<ExternalProductDto> externalProducts = new ArrayList<>();
        externalProducts.add(externalProductDto);
        ExternalProductListDto externalProductListDto = new ExternalProductListDto();
        externalProductListDto.setProducts(externalProducts);
        ResponseEntity<Void> addedProducts = restTemplate.postForEntity(url, externalProductListDto, Void.class);
        Assertions.assertNotNull(addedProducts);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, addedProducts.getStatusCode());
    }

}