package id.co.nds.catalogue.controllers;

import java.util.List;

import javax.validation.Valid;

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

import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.entities.UserInfoEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.models.ResponseModel;
import id.co.nds.catalogue.models.UserModel;
import id.co.nds.catalogue.services.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
    public ResponseEntity<ResponseModel> postUserController(
        @Valid @RequestBody UserModel userModel) {
        try {
            // request
            UserEntity user = userService.add(userModel);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("New user is successfully added");
            response.setData(user);

            return ResponseEntity.ok(response);
        // } catch (ClientException e) {
        //     ResponseModel response = new ResponseModel();
        //     response.setMsg(e.getMessage());

        //     return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry there is a failure on our server. " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value = "/get")
    public ResponseEntity<ResponseModel> getAllUsersController() {
        try {
            // request
            List<UserEntity> user = userService.findAll();
   
            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfull");
            response.setData(user);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry there is a failure on our server.");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value = "/get/search")
    public ResponseEntity<ResponseModel> searchUsersController(
            @RequestBody UserModel userModel) {
        try {
            // request
            List<UserEntity> users = userService.findAllByCriteria(userModel) ;

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Request Successfull");
            response.setData(users);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ResponseModel response = new ResponseModel();
            response.setMsg("Sorry there is a failure on our server");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(value = "/get/info")
    public ResponseEntity<ResponseModel> getAllByRoleController(@RequestParam String roleName) {
        try {
            // request
            // List<UserInfoEntity> user = userService.findAllByRole(roleId);
            List<UserInfoEntity> user = userService.findUsersByRoleName(roleName);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfull");
            response.setData(user);
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

    @GetMapping(value = "/get/role")
    public ResponseEntity<ResponseModel> getUsersByRoleIdController(@RequestParam String roleName) {
        try {
            // request
            // List<UserEntity> user = userService.findUsersByRoleId(roleId);
            List<UserEntity> user = userService.findUsersByRoleNameWhereNoActive(roleName);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Request Successfull");
            response.setData(user);

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
    public ResponseEntity<ResponseModel> getUserByIdController(@PathVariable Integer id) {
        try {
            // request
            UserEntity user = userService.findById(id);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("Request successfull");
            response.setData(user);

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
    public ResponseEntity<ResponseModel> putUserController(
        @RequestBody UserModel userModel) {
        try {
            // request
            UserEntity user = userService.edit(userModel);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("User is successfully updated");
            response.setData(user);

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
    public ResponseEntity<ResponseModel> deleteUserController(
            @RequestBody UserModel userModel) {
        try {
            // request
            UserEntity user = userService.delete(userModel);

            // response
            ResponseModel response = new ResponseModel();
            response.setMsg("User is successfully deleted");
            response.setData(user);

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
