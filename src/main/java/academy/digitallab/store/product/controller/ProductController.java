package academy.digitallab.store.product.controller;

import academy.digitallab.store.product.entity.Category;
import academy.digitallab.store.product.entity.Product;
import academy.digitallab.store.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name = "categoryId", required = false) Long categoryId) {
        List<Product> products = new ArrayList<>();
        if(null == categoryId) {
            products = productService.listAllProduct();

            if(products.isEmpty())
                return ResponseEntity.noContent().build();
        }
        else {
            products = productService.findByCategory(Category.builder().id(categoryId).build());

            if(products.isEmpty())
                return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(products);
    }

    @GetMapping(value ="/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        if (null == product)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product productCreate = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);

        Product productUpdated = productService.updateProduct(product);
        if (null == productUpdated)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(product);

    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        Product productDeleted = productService.deleteProduct(id);
        if(productDeleted == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(productDeleted);

    }

    @PutMapping(value="/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id, @RequestParam(value = "quantity", required = true) Double quantity) {
        Product product = productService.updateStock(id, quantity);


        if (null == product)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(product);

    }


}
