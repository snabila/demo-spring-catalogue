package id.co.nds.catalogue.repos.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import id.co.nds.catalogue.entities.RoleEntity;
import id.co.nds.catalogue.globals.GlobalConstant;
import id.co.nds.catalogue.models.RoleModel;

public class RoleSpec implements Specification<RoleEntity>{
    private RoleModel roleModel;

    public RoleSpec(RoleModel roleModel){
        super();
        this.roleModel = roleModel;
    }

    @Override
    public Predicate toPredicate(Root<RoleEntity> root, CriteriaQuery<?> cq,
        CriteriaBuilder cb){
            //Predicate p = cb.and();
            Predicate p = cb.disjunction();

            // fullname criteria
            if(roleModel.getName() != null && roleModel.getName().length() > 0){
                p.getExpressions().add(cb.like(cb.lower(root.get("name")),
                "%" + roleModel.getName().toLowerCase()+ "%"));
            }

            // rec_status criteria
            if(roleModel.getRecStatus() != null && (roleModel.getRecStatus().trim()
                .equalsIgnoreCase(GlobalConstant.REC_STATUS_ACTIVE) 
                || roleModel.getRecStatus().trim()
                        .equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE))){
                p.getExpressions().add(cb.equal(cb.upper(root.get("recStatus")),
                roleModel.getRecStatus().toUpperCase()));
            }

            cq.orderBy(cb.asc(root.get("id")));

            return p;
        }
}
