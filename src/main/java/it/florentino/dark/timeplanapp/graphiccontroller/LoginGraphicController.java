package it.florentino.dark.timeplanapp.graphiccontroller;


import it.florentino.dark.timeplanapp.appcontroller.LoginController;
import it.florentino.dark.timeplanapp.beans.LoginBean;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;




public class LoginGraphicController extends GraphicController {


    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorLabel;

    @FXML
    public void onHyperLinkClicked() {
        try {
            this.getScenePlayer().showScene("GUI/RegistrationPage.fxml");
        } catch(SetSceneException sE) {
           System.exit(-1); //Da gestire graficamente
        }
    }

    @FXML
    public void onLoginClick(){

        String user = this.username.getText().trim();
        String pass = this.password.getText().trim();


        LoginController controller = new LoginController();
        try {
            LoginBean credentials = new LoginBean(user, pass);
            UserBean verifiedUser = controller.authenticate(credentials);

            switch(verifiedUser.getRole()){
                case MANAGER -> System.out.println("Manager logged");
                case EMPLOYEE -> System.out.println("Employee logged");
                default -> throw new CredentialException();
            }

        }catch(ServiceException | CredentialException e) {
            this.showError(e.getMessage());
        }
    }

    public Label getErrorLabel(){
        return this.errorLabel;
    }
}
