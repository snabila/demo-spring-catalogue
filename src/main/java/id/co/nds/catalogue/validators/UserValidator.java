package id.co.nds.catalogue.validators;

import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.globals.GlobalConstant;

public class UserValidator {
    public static boolean isNumeric(String str) { 
        try {  
            Double.parseDouble(str);  
            return true;
        } catch(NumberFormatException e){  
            return false;  
        }  
      }

    public void nullCheckUserId(Integer id) throws ClientException{
        if(id == null){
            throw new ClientException("User id is required");
        }
    }

    public void notNullCheckUserId(Integer id) throws ClientException{
        if(id != null){
            throw new ClientException("User id is auto generated, do not input id");
        }
    }

    public void nullCheckfullname(String fullname) throws ClientException{
        if(fullname == null){
            throw new ClientException("User fullname is required");
        }
    }

    public void nullCheckRoleId(String roleId) throws ClientException{
        if(roleId == null){
            throw new ClientException("User role id is required");
        }
    }
    
    public void nullCheckObject(Object o) throws ClientException{
        if(o == null){
            throw new ClientException("User is not found");
        }
    }

    public void validateUserId(Integer id) throws ClientException{
        if(id <= 0){
            throw new ClientException("User id input is invalid");
        }
    }

    public void validateFullname(String fullname) throws ClientException{
        if(fullname.trim().equalsIgnoreCase("")){
            throw new ClientException("User fullname is required");
        }
    }

    public void validateRoleId(String roleId) throws ClientException{
        if(roleId.length() != 5 || !roleId.startsWith("R") || !isNumeric(roleId.substring(1,5)) ){
            throw new ClientException(
                "User role id contains five digits and start with 'R' and with 4 digits number");
        }
    }

    public void validateCallNumber(String callNumber) throws ClientException{
        if((callNumber.trim().equalsIgnoreCase("")) && 
            (callNumber.length() < 9 || callNumber.length() > 12 ) || (!callNumber.startsWith("0") && !callNumber.startsWith("+62")) || 
            (callNumber.startsWith("0") && !isNumeric(callNumber.substring(0,callNumber.length()))) || 
            (callNumber.startsWith("+62") && !isNumeric(callNumber.substring(1,callNumber.length())))) {
            throw new ClientException(
                "User call numbers start with 0 or +62 followed by 9 - 12 digits number");
        }
    }

    public void validateStatus(String id, String recStatus) throws ClientException{
        if(recStatus.equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)){
            throw new ClientException(
                "Product with id = " + id + " is alredy been deleted.");
        }
    }
}
