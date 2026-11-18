package academy.digitallab.store.product.controller;

import academy.digitallab.store.product.entity.Category;
import academy.digitallab.store.product.entity.Product;
import academy.digitallab.store.product.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    public void whenProductsExist_shouldReturnOk() {
        Product product = new Product();
        List<Product> products = List.of(product);

        when(productService.listAllProduct()).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.listProduct(null);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(products);

        verify(productService, times(1)).listAllProduct();
    }

    @Test
    public void whenProductsEmpty_shouldReturnNoContent() {

        when(productService.listAllProduct()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Product>> response = productController.listProduct(null);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(response.getBody()).isNull();

        verify(productService, times(1)).listAllProduct();
    }

    @Test
    public void whenCategoryHasProducts_shouldReturnOk() {

        Long categoryId = 1L;
        Product product = new Product();
        List<Product> products = List.of(product);

        when(productService.findByCategory(any(Category.class))).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.listProduct(categoryId);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(products);

        verify(productService, times(1))
                .findByCategory(argThat( category ->
                categoryId.equals(category.getId()))
        );
    }

    @Test
    public void whenCategoryEmpty_shouldReturnNoContent() {

        Long categoryId = 1L;

        when(productService.findByCategory(any(Category.class))).thenReturn(Collections.emptyList());

        ResponseEntity<List<Product>> response = productController.listProduct(categoryId);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(response.getBody()).isNull();

        verify(productService, times(1))
                .findByCategory(argThat( category ->
                        categoryId.equals(category.getId()))
                );
    }

    @Test
    public void whenProductExists_shouldReturnOK() {
        Long productId = 1L;
        Product product = new Product();

        when(productService.getProduct(productId)).thenReturn(product);

        ResponseEntity<Product> response = productController.getProduct(productId);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(product);

        verify(productService, times(1)).getProduct(productId);
    }

    @Test
    public void whenProductDoesNotExist_shouldReturnNotFound() {
        Long productId = 1L;
        when(productService.getProduct(productId)).thenReturn(null);

        ResponseEntity<Product> response = productController.getProduct(productId);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(response.getBody()).isNull();

        verify(productService, times(1)).getProduct(productId);
    }

    @Test
    public void whenCreateProduct_shouldReturnCreated() {

        Product product = new Product();
        Product productCreated = new Product();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        when(productService.createProduct(product)).thenReturn(productCreated);

        ResponseEntity<Product> response = productController.createProduct(product, bindingResult);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isEqualTo(productCreated);

        verify(productService, times(1)).createProduct(product);
    }

    @Test
    public void whenProductExists_updateProduct_shouldReturnOk() {
        Long productId = 1L;
        Product product = new Product();
        Product productUpdated = new Product();

        when(productService.updateProduct(product)).thenReturn(productUpdated);

        ResponseEntity<Product> response = productController.updateProduct(productId, product);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isSameAs(product);

        verify(productService, times(1)).updateProduct(product);
    }

    @Test
    public void whenProductExists_deleteProduct_shouldReturnOk() {
        Long productId = 1L;
        Product productDeleted = new Product();

        when(productService.deleteProduct(productId)).thenReturn(productDeleted);

        ResponseEntity<Product> response = productController.deleteProduct(productId);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isSameAs(productDeleted);

        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    void whenProductDoesNotExist_deleteProduct_shouldReturnNotFound() {
        Long productId = 1L;

        when(productService.deleteProduct(productId)).thenReturn(null);

        ResponseEntity<Product> response = productController.deleteProduct(productId);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(response.getBody()).isNull();

        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    public void whenProductExist_updateStockProduct_shouldReturnOk(){
        Long productId = 1L;
        Double quantity = 20.0;
        Product productUpdate = new Product();

        when(productService.updateStock(productId, quantity)).thenReturn(productUpdate);

        ResponseEntity<Product> response = productController.updateStockProduct(productId, quantity);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isSameAs(productUpdate);

        verify(productService, times(1)).updateStock(productId, quantity);

    }



}
