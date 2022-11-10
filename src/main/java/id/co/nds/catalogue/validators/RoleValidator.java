package id.co.nds.catalogue.validators;

import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.globals.GlobalConstant;

public class RoleValidator {
    public void nullCheckRoleId(String id) throws ClientException {
        if (id == null) {
            throw new ClientException("Role id is required");
        }
    }

    public void notNullCheckRoleId(String id) throws ClientException {
        if (id != null) {
            throw new ClientException("Role id is auto generated, do not input id");
        }
    }

    public void nullCheckName(String name) throws ClientException {
        if (name == null) {
            throw new ClientException("Role name is required");
        }
    }

    public void nullCheckObject(Object o) throws NotFoundException {
        if (o == null) {
            throw new NotFoundException("Role not found");
        }
    }

    public void validateRoleId(String id) throws ClientException {
        if (id.length() != 5 || !id.startsWith("R")) {
            throw new ClientException(
                    "Role id must starts with 'R' followed by five digits");
        }
    }

    public void validateName(String name) throws ClientException {
        if (name.trim().equalsIgnoreCase("")) {
            throw new ClientException(
                    "Role name is required.");
        }
    }

    public void validateRecStatus(String id, String recStatus) throws ClientException {
        if (recStatus.trim().equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new ClientException(
                    "Role with id = " + id + " hass already been deleted.");
        }
    }
}
