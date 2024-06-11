package it.florentino.dark.timeplanapp.graphiccontroller;


import it.florentino.dark.timeplanapp.appcontroller.RegistrationController;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
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
    private CheckBox checkRole;

    private UserBean newUser;

    @FXML
    public void onHyperLinkClicked(){
        try {
            this.getScenePlayer().showScene("GUI/LoginPage.fxml");
        }catch(SetSceneException sE){
            throw new RuntimeException(sE);
        }
    }

    @FXML
    public void onNextButtonClick(){
        String usernameString = this.username.getText();
        String emailString = this.email.getText();
        String passwordString = this.password.getText();
        String retypedPassword = this.confirmPassword.getText();
        int control = 0;
        try {

            if(!(passwordString.equals(retypedPassword))) throw new CredentialException("Passwords do not match");

            if(checkRole.isSelected()) {
                this.newUser = new UserBean(usernameString, emailString, passwordString, Role.MANAGER);
            }else{
                this.newUser = new UserBean(usernameString, emailString, passwordString, Role.EMPLOYEE);
            }

        } catch (CredentialException e) {
            this.showError(e.getMessage());
            control = 1;
        }

        if(control == 0) {
            try {
                this.getScenePlayer().showScene("GUI/RegistrationPage2.fxml");
            } catch (SetSceneException e) {
                throw new RuntimeException(e);
            }
        }

    }
    @FXML
    public void onRegisterButtonClick() {

        RegistrationController controller = new RegistrationController();
        try {

            controller.insertUser(this.newUser);
            System.out.println("User written");

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
    public Label getErrorLabel(){
            return this.errorLabel;
    }

}
