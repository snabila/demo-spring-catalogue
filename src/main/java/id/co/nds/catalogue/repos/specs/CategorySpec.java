package id.co.nds.catalogue.repos.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import id.co.nds.catalogue.entities.CategoryEntity;
import id.co.nds.catalogue.globals.GlobalConstant;
import id.co.nds.catalogue.models.CategoryModel;

public class CategorySpec implements Specification<CategoryEntity>{
    private CategoryModel categoryModel;

    public CategorySpec(CategoryModel categoryModel){
        super();
        this.categoryModel = categoryModel;
    }

    @Override
    public Predicate toPredicate(Root<CategoryEntity> root, CriteriaQuery<?> cq,
        CriteriaBuilder cb){
        //Predicate p = cb.and();
        Predicate p = cb.disjunction();

        // fullname criteria
        if(categoryModel.getName() != null && categoryModel.getName().length() > 0){
            p.getExpressions().add(cb.like(cb.lower(root.get("name")),
            "%" + categoryModel.getName().toLowerCase()+ "%"));
        }

        // rec_status criteria
        if(categoryModel.getRecStatus() != null && (categoryModel.getRecStatus().trim()
            .equalsIgnoreCase(GlobalConstant.REC_STATUS_ACTIVE) 
            || categoryModel.getRecStatus().trim()
                    .equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE))){
            p.getExpressions().add(cb.equal(cb.upper(root.get("recStatus")),
            categoryModel.getRecStatus().toUpperCase()));
        }

        cq.orderBy(cb.asc(root.get("id")));

        return p;
    }
}
