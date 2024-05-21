package it.florentino.dark.timeplanapp.graphiccontroller;


import it.florentino.dark.timeplanapp.appcontroller.RegistrationController;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegistrationGraphicController extends GraphicController {


    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmPassword;

    @FXML
    public void onHyperLinkClicked(){
        try {
            this.getScenePlayer().showScene("GUI/LoginPage.fxml", "StyleSheets/LoginStyle.css");
        }catch(SetSceneException sE){
            System.exit(-1);
        }
    }
    @FXML
    public void onRegisterButtonClick(){
        String user = this.username.getText();
        String email = this.email.getText();
        String pass = this.password.getText();
        String clonePass = this.confirmPassword.getText();

        RegistrationController controller = new RegistrationController();
        try{
            UserBean newUser = new UserBean(user, email, pass, Role.EMPLOYEE);
        }catch(CredentialException e){
            e.printStackTrace();
        }


    }
}
