package id.co.nds.catalogue.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.nds.catalogue.entities.ProductEntity;
import id.co.nds.catalogue.entities.ProductInfoEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.ProductModel;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.services.ProductService;

@RestController
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(value = "/add")
    public ResponseEntity<ResponseModel> postProductController(
        @RequestBody ProductModel productModel) {
        try {
            // request
            ProductEntity product = productService.add(productModel);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("New product successfully added");
            response.setData(product);

            return ResponseEntity.ok(response);
        } catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());

            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry there is a failure on our server.");
            e.printStackTrace();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value = "/get")
    public ResponseEntity<ResponseModel> getAllProductsController() {
        try {
            // request
            List<ProductEntity> products = productService.findAll();

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfull");
            response.setData(products);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry there is a failure on our server.");
            e.printStackTrace();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value = "/get/search")
    public ResponseEntity<ResponseModel> searchProductsController(
        @RequestBody ProductModel productModel) {
        try {
            // request
            List<ProductEntity> products = productService.findAllByCriteria(productModel);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfull");
            response.setData(products);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry there is a failure on our server");
            e.printStackTrace();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value = "/get/info")
    public ResponseEntity<ResponseModel> getAllByCategoryController(@RequestParam String categoryId) {
        try {
            // request
            List<ProductInfoEntity> product = productService.findAllByCategory(categoryId);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfull");
            response.setData(product);
            return ResponseEntity.ok(response);
        } catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry there is a failure on our server");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value = "/get/category")
    public ResponseEntity<ResponseModel> getProductsByCategoryIdController(@RequestParam String categoryId) {
        try {
            // request
            List<ProductEntity> product = productService.findProductsByCategoryId(categoryId);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Request Successfull");
            response.setData(product);

            return ResponseEntity.ok(response);
        } catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failure on server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<ResponseModel> getProductByIdController(@PathVariable Integer id) {
        try {
            // request
            ProductEntity product = productService.findById(id);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfull");
            response.setData(product);

            return ResponseEntity.ok(response);
        } catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());

            return ResponseEntity.badRequest().body(response);
        } catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry there is a failure on our server");
            e.printStackTrace();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ResponseModel> putProductController(
        @RequestBody ProductModel productModel) {
        try {
            // request
            ProductEntity product = productService.edit(productModel);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Product is successfully updated");
            response.setData(product);

            return ResponseEntity.ok(response);
        } catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());

            return ResponseEntity.badRequest().body(response);
        } catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry there is a failure on our server");
            e.printStackTrace();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<ResponseModel> deleteProductController(
        @RequestBody ProductModel productModel) {
        try {
            // request
            ProductEntity product = productService.delete(productModel);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Product is successfully deleted");
            response.setData(product);

            return ResponseEntity.ok(response);

        } catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());

            return ResponseEntity.badRequest().body(response);
        } catch (NotFoundException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
