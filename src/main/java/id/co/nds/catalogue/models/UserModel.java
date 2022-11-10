package id.co.nds.catalogue.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserModel extends RecordModel {
    // main 
    private Integer id;

    @NotEmpty(message = "User fullname is required @")
    private String fullname;

    @NotEmpty(message = "User role ID is required @")
    private String roleId;

    @Pattern(regexp = "^(\\+62|0)\\d{9,12}",
    message = "User mobile number must start with 0 or +62 and followed by 9-12 digit numbers @")
    private String callNumber;
    
    private Integer creatorId;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getCallNumber() {
        return callNumber;
    }
    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public Integer getCreatorId() {
        return creatorId;
    }
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }
}
