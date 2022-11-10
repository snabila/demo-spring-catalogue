package id.co.nds.catalogue.validators;

import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.globals.GlobalConstant;

public class CategoryValidator {
    public void nullCheckCategoryId(String id) throws ClientException {
        if (id == null) {
            throw new ClientException("Category ID is required");
        }
    }

    public void notNullCheckCategoryId(String id) throws ClientException {
        if (id != null) {
            throw new ClientException("Category ID is auto generated, do not input ID");
        }
    }

    public void nullCheckName(String name) throws ClientException {
        if (name == null) {
            throw new ClientException("Category name is required");
        }
    }

    public void nullCheckObject(Object o) throws NotFoundException {
        if (o == null) {
            throw new NotFoundException("Category is not found");
        }
    }

    public void validateCategoryId(String id) throws ClientException {
        if (id.length() != 6 || !id.startsWith("PC")) {
            throw new ClientException("Category ID must starts with 'PC' and contains four numbers");
        }
    }

    public void validateName(String name) throws ClientException {
        if (name.trim().equalsIgnoreCase("")) {
            throw new ClientException("Category name is required");
        }
    }

    public void validateRecStatus(String id, String recStatus) throws ClientException {
        if (recStatus.equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new ClientException("Category with ID = " + id + " has already been deleted");
        }
    }
}
