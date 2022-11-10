package id.co.nds.catalogue.services;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.nds.catalogue.entities.ProductEntity;
import id.co.nds.catalogue.entities.ProductInfoEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.globals.GlobalConstant;
import id.co.nds.catalogue.models.ProductModel;
import id.co.nds.catalogue.repos.ProductInfoRepo;
import id.co.nds.catalogue.repos.ProductRepo;
import id.co.nds.catalogue.repos.specs.ProductSpec;
import id.co.nds.catalogue.validators.CategoryValidator;
import id.co.nds.catalogue.validators.ProductValidator;

@Service
public class ProductService implements Serializable {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductInfoRepo productInfoRepo;

    ProductValidator productValidator = new ProductValidator();
    CategoryValidator categoryValidator = new CategoryValidator();

    public ProductEntity add(ProductModel productModel) throws ClientException {
        // validation
        productValidator.notNullCheckProductId((productModel.getId()));
        productValidator.nullCheckName((productModel.getName()));
        productValidator.validateName((productModel.getName()));
        productValidator.nullCheckQuantity((productModel.getQuantity()));
        productValidator.validateQuantity((productModel.getQuantity()));
        productValidator.nullCheckCategoryId((productModel.getCategoryId()));
        productValidator.validateCategoryId((productModel.getCategoryId()));

        Long count = productRepo.countByName(productModel.getName());
        if (count > 0) {
            throw new ClientException("Product name already existed");
        }

        // process
        ProductEntity product = new ProductEntity();
        product.setName(productModel.getName());
        product.setQuantity(productModel.getQuantity());
        product.setCategoryId(productModel.getCategoryId());
        product.setRecStatus(GlobalConstant.REC_STATUS_ACTIVE);
        product.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        product.setCreatorId(
            productModel.getActorId() == null ? 0 : productModel.getActorId());

        return productRepo.save(product);
    }

    public List<ProductEntity> findAll() {
        List<ProductEntity> products = new ArrayList<>();
        productRepo.findAll().forEach(products::add);

        return products;
    }

    public List<ProductInfoEntity> findAllByCategory(String categoryId) throws ClientException, NotFoundException {
        // validation
        categoryValidator.nullCheckCategoryId(categoryId);
        categoryValidator.validateCategoryId(categoryId);

        // process
        List<ProductInfoEntity> product = productInfoRepo.findAllByCategory(categoryId);
        productValidator.nullCheckObject(product);

        System.out.println(product);

        return product;
    }

    public List<ProductEntity> findProductsByCategoryId(String categoryId) throws ClientException, NotFoundException {
        // validation
        categoryValidator.nullCheckCategoryId(categoryId);
        categoryValidator.validateCategoryId(categoryId);

        // process
        List<ProductEntity> product = productRepo.findProductsByCategoryId(categoryId);
        productValidator.nullCheckObject(product);

        return product;
    }
    
    public List<ProductEntity> findAllByCriteria(ProductModel productModel) {
        List<ProductEntity> products = new ArrayList<>();
        ProductSpec specs = new ProductSpec(productModel);
        productRepo.findAll(specs).forEach(products::add);

        return products;
    }

    public ProductEntity findById(Integer id) throws ClientException, NotFoundException {
        // validation
        productValidator.nullCheckProductId(id);
        productValidator.validateProductId(id);

        // process
        ProductEntity product = productRepo.findById(id).orElse(null);
        productValidator.nullCheckObject(product);

        return product;
    }

    public ProductEntity edit(ProductModel productModel) throws ClientException, NotFoundException {
        productValidator.nullCheckProductId(productModel.getId());
        productValidator.validateProductId(productModel.getId());

        if(!productRepo.existsById(productModel.getId())) {
            throw new NotFoundException(
                "Cannot found product with id: " + productModel.getId());
        }

        // process
        ProductEntity product = new ProductEntity();
        product = findById(productModel.getId());

        if (productModel.getName() != null) {
            productValidator.validateName(productModel.getName());

            Long count = productRepo.countByName(productModel.getName());
            if (count > 0) {
                throw new ClientException("Product name already existed");
            }

            product.setName(productModel.getName());
        }

        if (productModel.getQuantity() != null) {
            productValidator.validateQuantity(productModel.getQuantity());

            product.setQuantity(productModel.getQuantity());
        }
        
        if (productModel.getCategoryId() != null) {
            productValidator.validateCategoryId(productModel.getCategoryId());

            product.setCategoryId(productModel.getCategoryId());
        }

        product.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        product.setUpdaterId(productModel.getActorId() == null ? 0 : productModel.getActorId());

        return productRepo.save(product);
    }

    public ProductEntity delete(ProductModel productModel) throws ClientException, NotFoundException {
        // validation
        productValidator.nullCheckProductId(productModel.getId());
        productValidator.validateProductId(productModel.getId());

        if (!productRepo.existsById(productModel.getId())) {
            throw new NotFoundException((
                "Cannot find product with id: " + productModel.getId()));
        }

        // process
        ProductEntity product = new ProductEntity();
        product = findById(productModel.getId());

        if (product.getRecStatus().equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new ClientException(
                "Product id (" + productModel.getId() + ") has already been deleted.");
        }

        product.setRecStatus(GlobalConstant.REC_STATUS_NON_ACTIVE);
        product.setDeletedDate(new Timestamp(System.currentTimeMillis()));
        product.setDeleterId(productModel.getActorId() == null ? 0 : productModel.getActorId());

        return productRepo.save(product);
    }
}
