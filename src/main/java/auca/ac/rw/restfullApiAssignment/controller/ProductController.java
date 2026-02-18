package auca.ac.rw.restfullApiAssignment.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import auca.ac.rw.restfullApiAssignment.modal.ecommerce.Product;
import auca.ac.rw.restfullApiAssignment.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ADD PRODUCT
    @PostMapping("/addProduct")
    public ResponseEntity<?> addNewProduct(@RequestBody Product product) {
        String response = productService.addNewProduct(product);

        if(response.contains("successfully")){
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    // GET ALL
    @GetMapping("/all")
    public List<Product> getAllProducts(){
        return productService.getAll();
    }

    // GET BY CATEGORY
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> searchProductsByCategory(@RequestParam String Category){
        List<Product> getProducts = productService.searchByCategory(Category);

        if(getProducts != null){
            return new ResponseEntity<>(getProducts, HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>("Product with that category are not found", HttpStatus.NOT_FOUND);
        }
    }      

    // GET BY BRAND
    @GetMapping(value = "/brand/{brand}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> searchProductsByBrand(@RequestParam String Brand){
        List<Product> getProducts = productService.searchByBrand(Brand);

        if(getProducts != null){
            return new ResponseEntity<>(getProducts, HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>("Brand are not found", HttpStatus.NOT_FOUND);
        }
    }

    // PRICE
    @GetMapping(value = "/priceandbrand", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchProductsByPriceAndBrand(@RequestParam Double price, String brand){
        List<Product> getProducts = productService.searchByPriceAndBrand(price, brand);

        if(getProducts != null){
            return new ResponseEntity<>(getProducts, HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>("Product with that price and brand are not found", HttpStatus.NOT_FOUND);
        }
    } 

    // GET PRODUCT BY ID
@GetMapping("/{productId}")
public ResponseEntity<?> getProductById(@PathVariable Long productId){
    Optional<Product> product = productService.getById(productId);

    if(product.isPresent()){
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }else{
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }
}


// SEARCH PRODUCTS BY KEYWORD (name or description)
@GetMapping("/searchByKeyword")
public ResponseEntity<?> searchProductsByKeyword(@RequestParam String keyword){
    List<Product> products = productService.searchByKeyword(keyword);

    if(products != null && !products.isEmpty()){
        return new ResponseEntity<>(products, HttpStatus.OK);
    }else{
        return new ResponseEntity<>("No matching products found", HttpStatus.NOT_FOUND);
    }
}


// GET PRODUCTS IN PRICE RANGE
@GetMapping("/price-range")
public ResponseEntity<?> getProductsByPriceRange(@RequestParam Double min,
                                            @RequestParam Double max){
    List<Product> products = productService.searchByPriceRange(min, max);

    if(products != null && !products.isEmpty()){
        return new ResponseEntity<>(products, HttpStatus.OK);
    }else{
        return new ResponseEntity<>("No products found in this price range", HttpStatus.NOT_FOUND);
    }
}


// GET PRODUCTS IN STOCK
@GetMapping("/in-stock")
public ResponseEntity<?> getProductsInStock(){
    List<Product> products = productService.getProductsInStock();

    if(products != null && !products.isEmpty()){
        return new ResponseEntity<>(products, HttpStatus.OK);
    }else{
        return new ResponseEntity<>("No products in stock", HttpStatus.NOT_FOUND);
    }
}

// UPDATE PRODUCT
@PutMapping("/{productId}")
public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                        @RequestBody Product updatedProduct){

    Product product = productService.updateProduct(productId, updatedProduct);

    if(product != null){
        return new ResponseEntity<>(product, HttpStatus.OK);
    }else{
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }
}


// UPDATE STOCK QUANTITY
@PatchMapping("/{productId}/stock")
public ResponseEntity<?> updateStock(@PathVariable Long productId,
                                    @RequestParam int quantity){

    Product product = productService.updateStock(productId, quantity);

    if(product != null){
        return new ResponseEntity<>(product, HttpStatus.OK);
    }else{
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }
}


// DELETE PRODUCT
@DeleteMapping("/{productId}")
public ResponseEntity<?> deleteProduct(@PathVariable Long productId){

    boolean deleted = productService.deleteProduct(productId);

    if(deleted){
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }else{
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }
}


}