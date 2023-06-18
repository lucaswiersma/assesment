package nl.jdriven.assesment;

import nl.jdriven.assesment.dto.ProductDto;
import nl.jdriven.assesment.model.Product;
import nl.jdriven.assesment.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ShoppingApiTest {

    private static final Product product = new Product(1l, "Product 1","Description 1", new BigDecimal(100.0));

    @Value("${server.port}")
    private int configPort;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    private void setup(){
        repository.save(product);
    }

    @AfterEach
    private void tearDown(){
        repository.delete(product);
    }

    @Test
    public void getProductShouldReturnListOfProducts()  {
        String url = "http://localhost:" + configPort + "/api/products";
        List<ProductDto> response = restTemplate.getForObject(url, List.class);
        Assertions.assertEquals(1, response.size());
    }

    @Test
    public void getProductWithIdOneShouldReturnProductOne(){
        String url = "http://localhost:" + configPort + "/api/products/1";
        ProductDto response = restTemplate.getForObject(url, ProductDto.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Product 1", response.getName());
    }

    @Test
    public void getProductWithUnknownIdShouldReturn404(){
        String url = "http://localhost:" + configPort + "/api/products/2";
        ResponseEntity<ProductDto> response = restTemplate.exchange(url,
                HttpMethod.GET, null,  ProductDto.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}