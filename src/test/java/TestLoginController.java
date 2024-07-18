
import it.florentino.dark.timeplanapp.appcontroller.LoginController;
import it.florentino.dark.timeplanapp.beans.LoginBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;




public class TestLoginController {

    //Test have to fail with wrong credential;
    @Test
    public void  TestAuthenticateByWrongCredentials(){
        LoginController testLogin = new LoginController();
        LoginBean bean = null;

        try {
             bean = new LoginBean("wrongEmail@gmail.com", "wrongPassword");
        }catch(CredentialException e){
            //Do nothing
        }

        try{
            testLogin.authenticate(bean);
        }catch(CredentialException e){
            Assertions.fail();
        }catch(ServiceException e){
            //Do nothing
        }


    }
}
