package it.florentino.dark.timeplanapp.graphiccontroller;


import it.florentino.dark.timeplanapp.appcontroller.LoginController;
import it.florentino.dark.timeplanapp.beans.LoginBean;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.utils.printer.Printer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginGraphicController extends GraphicController {


    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorLabel;

    @FXML
    public void onHyperLinkClicked() {
        try {
            this.getScenePlayer().showScene("GUI/RegistrationPage.fxml");
        } catch(SetSceneException e) {
           Printer.perror(e.getMessage());
        }
    }

    @FXML
    public void onLoginClick(){

        String email = this.email.getText().trim();
        String pass = this.password.getText().trim();


        LoginController controller = new LoginController();
        try {
            LoginBean credentials = new LoginBean(email, pass);
            UserBean loggedUser = controller.authenticate(credentials);

            switch(loggedUser.getRole()){
                case MANAGER -> this.getScenePlayer().showHomePageMan("GUI/HomePageMan.fxml", loggedUser);
                case EMPLOYEE -> Printer.printf("Employee logged");
                default -> throw new CredentialException();
            }

        }catch(CredentialException e) {
            this.showError(e.getMessage());
        }catch(ServiceException  | SetSceneException e ){
            Printer.perror(e.getMessage());
        }
    }

    public Label getErrorLabel(){
        return this.errorLabel;
    }
}
