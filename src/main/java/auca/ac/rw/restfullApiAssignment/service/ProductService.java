package auca.ac.rw.restfullApiAssignment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auca.ac.rw.restfullApiAssignment.modal.ecommerce.Product;
import auca.ac.rw.restfullApiAssignment.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // ADD PRODUCT
    public String addNewProduct(Product product) {

        Optional<Product> existProduct = productRepository.findById(product.getId());

        if(existProduct.isPresent()){
            return "Product with id " + product.getId() + " already exists";
        }else{
            productRepository.save(product);
            return "Product added successfully";
        }
    }

    // GET ALL
    public List<Product> getAll(){
        return productRepository.findAll();
    }

    // CATEGORY
     public List<Product> searchByCategory(String CategoryName) {
        List<Product> products = productRepository.findByCategory(CategoryName);

        if (products !=null && !products.isEmpty()){
            return products;
        }else{
         return null;
        }
    }

    // BRAND
    public List<Product> searchByBrand(String Brand) {
        List<Product> products = productRepository.findByBrand(Brand);

        if (products !=null && !products.isEmpty()){
            return products;
        }else{
         return null;
        }
    }

    // SEARCH
    public List<Product> search(String keyword){
        return productRepository
        .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }

    // PRICE RANGE
     public List<Product> searchByPriceAndBrand(Double price, String brand) {
        List<Product> products = productRepository.findByPriceAndBrand(price, brand);

        if (products !=null && !products.isEmpty()){
            return products;
        }else{
         return null;
        }
    }

    // GET PRODUCT BY ID
public Optional<Product> getById(Long id){
    return productRepository.findById(id);
}


// SEARCH BY KEYWORD (name or description)
public List<Product> searchByKeyword(String keyword){
    List<Product> products = productRepository
        .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);

    if(products != null && !products.isEmpty()){
        return products;
    }else{
        return null;
    }
}


// PRICE RANGE
public List<Product> searchByPriceRange(Double min, Double max){
    List<Product> products = productRepository.findByPriceBetween(min, max);

    if(products != null && !products.isEmpty()){
        return products;
    }else{
        return null;
    }
}


// IN STOCK
public List<Product> getProductsInStock(){
    List<Product> products = productRepository.findByStockQuantityGreaterThan(0);

    if(products != null && !products.isEmpty()){
        return products;
    }else{
        return null;
    }
}

// UPDATE PRODUCT
public Product updateProduct(Long id, Product updatedProduct){
    Optional<Product> existing = productRepository.findById(id);

    if(existing.isPresent()){
        Product product = existing.get();

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setBrand(updatedProduct.getBrand());
        product.setCategory(updatedProduct.getCategory());
        product.setStockQuantity(updatedProduct.getStockQuantity());

        return productRepository.save(product);
    }
    return null;
}


// UPDATE STOCK
public Product updateStock(Long id, int quantity){
    Optional<Product> existing = productRepository.findById(id);

    if(existing.isPresent()){
        Product product = existing.get();
        product.setStockQuantity(quantity);
        return productRepository.save(product);
    }
    return null;
}


// DELETE PRODUCT
public boolean deleteProduct(Long id){
    Optional<Product> existing = productRepository.findById(id);

    if(existing.isPresent()){
        productRepository.deleteById(id);
        return true;
    }
    return false;
}

}
