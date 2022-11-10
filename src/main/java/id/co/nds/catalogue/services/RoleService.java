package id.co.nds.catalogue.services;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.nds.catalogue.entities.RoleEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.globals.GlobalConstant;
import id.co.nds.catalogue.models.RoleModel;
import id.co.nds.catalogue.repos.RoleRepo;
import id.co.nds.catalogue.repos.specs.RoleSpec;
import id.co.nds.catalogue.validators.RoleValidator;

@Service
public class RoleService implements Serializable {
    @Autowired
    private RoleRepo roleRepo;

    RoleValidator roleValidator = new RoleValidator();

    public RoleEntity add(RoleModel roleModel) throws ClientException {

        // validation
        roleValidator.notNullCheckRoleId(roleModel.getId());
        roleValidator.nullCheckName(roleModel.getName());
        roleValidator.validateName(roleModel.getName());

        Long count = roleRepo.countByName(roleModel.getName());
        if (count > 0) {
            throw new ClientException("Role name already existed");
        }
        
        // process
        RoleEntity role = new RoleEntity();

        role.setName(roleModel.getName());
        role.setRecStatus(GlobalConstant.REC_STATUS_ACTIVE);
        role.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        role.setCreatorId(
            roleModel.getActorId() == null ? 0 : roleModel.getActorId());

        return roleRepo.save(role);
    }

    public List<RoleEntity> findAll() {
        List<RoleEntity> roles = new ArrayList<>();
        roleRepo.findAll().forEach(roles::add);

        return roles;
    }

    public List<RoleEntity> findAllByCriteria(RoleModel roleModel) {
        List<RoleEntity> categories = new ArrayList<>();
        RoleSpec specs = new RoleSpec(roleModel);
        roleRepo.findAll(specs).forEach(categories::add);

        return categories;
    }

    public RoleEntity findById(String id) throws ClientException, NotFoundException {
        // validation
        roleValidator.nullCheckRoleId(id);
        roleValidator.validateRoleId(id);

        // process
        RoleEntity role = roleRepo.findById(id).orElse(null);

        return role;
    }

    public RoleEntity edit(RoleModel roleModel) throws ClientException, NotFoundException {
        // validation
        roleValidator.nullCheckRoleId(roleModel.getId());
        roleValidator.validateRoleId(roleModel.getId());

        if (!roleRepo.existsById(roleModel.getId())) {
            throw new NotFoundException(
                    "Cannot find role with id: " + roleModel.getId());
        }

        // process
        RoleEntity role = new RoleEntity();
        role = findById(roleModel.getId());

        if (roleModel.getName() != null) {
            roleValidator.validateName(roleModel.getName());

            Long count = roleRepo.countByName(roleModel.getName());
            if (count > 0) {
                throw new ClientException("Role name is alredy existed");
            }

            role.setName(roleModel.getName());
        }

        role.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        role.setUpdaterId(
            roleModel.getActorId() == null ? 0 : roleModel.getActorId());

        return roleRepo.save(role);
    }

    public RoleEntity delete(RoleModel roleModel) throws ClientException, NotFoundException {
        // validation
        roleValidator.nullCheckRoleId(roleModel.getId());
        roleValidator.validateRoleId(roleModel.getId());

        if (!roleRepo.existsById(roleModel.getId())) {
            throw new NotFoundException(
                    "Cannot find role with id: " + roleModel.getId());
        }

        // process
        RoleEntity role = new RoleEntity();
        role = findById(roleModel.getId());

        if (role.getRecStatus().equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new ClientException(
                    "Role id (" + roleModel.getId() + ") has already been deleted");
        }

        role.setRecStatus(GlobalConstant.REC_STATUS_NON_ACTIVE);
        role.setDeletedDate(new Timestamp(System.currentTimeMillis()));
        role.setDeleterId(
            roleModel.getActorId() == null ? 0 : roleModel.getActorId());

        return roleRepo.save(role);
    }
}
