package it.florentino.dark.timeplanapp.graphiccontroller;


import it.florentino.dark.timeplanapp.appcontroller.RegistrationController;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private Label errorLabel;

    @FXML
    public void onHyperLinkClicked(){
        try {
            this.getScenePlayer().showScene("GUI/LoginPage.fxml");
        }catch(SetSceneException sE){
            System.exit(-1);
        }
    }
    @FXML
    public void onRegisterButtonClick() {

        String usernameString = this.username.getText();
        String emailString = this.email.getText();
        String passwordString = this.password.getText();
        String retypedPassword = this.confirmPassword.getText();
        UserBean newUser;
        RegistrationController controller = new RegistrationController();
        try {

            if( passwordString.equals(retypedPassword)) {
                newUser = new UserBean(usernameString, emailString, passwordString, Role.EMPLOYEE);
            }else{ throw new CredentialException("Passwords do not match"); }
            controller.insertUser(newUser);
            System.out.println("User written");


        } catch (CredentialException | ServiceException e) {
            this.showError(e.getMessage());
        }
    }
    public Label getErrorLabel(){
            return this.errorLabel;
    }

}
