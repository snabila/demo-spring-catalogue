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
import org.springframework.web.bind.annotation.RestController;

import id.co.nds.catalogue.entities.RoleEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.models.RoleModel;
import id.co.nds.catalogue.services.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/add")
    public ResponseEntity<ResponseModel> postRoleController(@RequestBody RoleModel roleModel) {
        try {
            // request
            RoleEntity role = roleService.add(roleModel);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("New role successfully added");
            response.setData(role);
            return ResponseEntity.ok(response);

        } catch (ClientException e) {
            ResponseModel response = new ResponseModel();
            response.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry there is a failure on our server");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value = "/get")
    public ResponseEntity<ResponseModel> getAllRolesController() {
        try {
            // request
            List<RoleEntity> role = roleService.findAll();
   
            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfull");
            response.setData(role);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry there is a failure on our server");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value = "/get/search")
    public ResponseEntity<ResponseModel> searchRolesController(
            @RequestBody RoleModel roleModel) {
        try {
            // request
            List<RoleEntity> roles = roleService.findAllByCriteria(roleModel) ;

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Request Successfully");
            response.setData(roles);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry, there is a failur on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<ResponseModel> getRoleByIdController(@PathVariable String id) {
        try {
            // request
            RoleEntity role = roleService.findById(id);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfully");
            response.setData(role);
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
            response.setMsg("Sorry, there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ResponseModel> putRoleController(
            @RequestBody RoleModel roleModel) {
        try {
            // request
            RoleEntity role = roleService.edit(roleModel);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Role is successfully updated");
            response.setData(role);
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
    @DeleteMapping(value = "/delete")
    public ResponseEntity<ResponseModel> deleteRoleController(
            @RequestBody RoleModel roleModel) {
        try {
            // request
            RoleEntity role = roleService.delete(roleModel);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Role is successfully deleted");
            response.setData(role);
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
