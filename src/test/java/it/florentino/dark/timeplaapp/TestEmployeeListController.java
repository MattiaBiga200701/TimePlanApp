package it.florentino.dark.timeplaapp;

import it.florentino.dark.timeplanapp.appcontroller.EmployeeListController;
import it.florentino.dark.timeplanapp.beans.EmployeeBean;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class TestEmployeeListController {

    //Test Have to file with an Employee not registered
    @Test
    void testInsertEmployeeByEmployeeNotRegistered(){

        EmployeeBean notRegisteredEmployee = null;
        EmployeeListController testController = new EmployeeListController();

        try{
            notRegisteredEmployee = new EmployeeBean("WrongName", "WrongSurname", ContractTypes.PART_TIME,"wrongEmail@gmail.com", 412453);
        }catch(InvalidInputException e){
            //Do nothing
        }

        try{
            testController.insertEmployee(notRegisteredEmployee);
        }catch(ServiceException e){
            //Do nothing
        }catch(InvalidInputException e){
            Assertions.fail();
        }
    }
}
