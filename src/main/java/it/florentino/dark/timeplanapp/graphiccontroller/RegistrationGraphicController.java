package it.florentino.dark.timeplanapp.graphiccontroller;


import it.florentino.dark.timeplanapp.appcontroller.RegistrationController;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
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
            this.getScenePlayer().showScene("GUI/LoginPage.fxml", "StyleSheets/LoginStyle.css");
        }catch(SetSceneException sE){
            System.exit(-1);
        }
    }
    @FXML
    public void onRegisterButtonClick() {

        String user = this.username.getText();
        String emailString = this.email.getText();
        String pass = this.password.getText();
        String retypedPass = this.confirmPassword.getText();

        RegistrationController controller = new RegistrationController();
        try {

            if( pass.equals(retypedPass)) {
                UserBean newUser = new UserBean(user, emailString, pass, Role.EMPLOYEE);
            }else{ throw new CredentialException("Passwords do not match"); }


        } catch (CredentialException e) {
            this.showError(e.getMessage());
        }
    }
    public Label getErrorLabel(){
            return this.errorLabel;
    }

}
