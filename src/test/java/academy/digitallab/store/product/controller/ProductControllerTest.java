package academy.digitallab.store.product.controller;

import academy.digitallab.store.product.entity.Product;
import academy.digitallab.store.product.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    public void listProduct_shouldReturnOk_whenProductsExist() {
        Product product = new Product();
        List<Product> products = List.of(product);

        when(productService.listAllProduct()).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.listProduct();

        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        Assertions.assertThat(products).isEqualTo(response.getBody());

        verify(productService, times(1)).listAllProduct();
    }


}
