package it.florentino.dark.timeplaapp;

import it.florentino.dark.timeplanapp.appcontroller.RegistrationController;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestRegistrationController {

    //Test have to fail with wrong ManagerID
    @Test
    void testCheckManagerIdByWrongId(){
        int wrongManagerId = 100000;
        RegistrationController testController = new RegistrationController();
        UserBean bean = null;

        try {
            bean = new UserBean("username", "email@gmail.com", "password", Role.EMPLOYEE, wrongManagerId);
        }catch(CredentialException e){
            //Do nothing
        }

        try{
            testController.checkManagerID(bean);
        }catch(CredentialException e){
            Assertions.fail();
        }catch(ServiceException e){
            //Do Nothing
        }
    }
}
