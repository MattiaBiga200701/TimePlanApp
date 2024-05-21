package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.ScenePlayer;
import it.florentino.dark.timeplanapp.appcontroller.LoginController;
import it.florentino.dark.timeplanapp.beans.LoginBean;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;



public class LoginGraphicController {


    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorLabel;
    private final ScenePlayer player = ScenePlayer.getScenePlayerInstance(null);
    @FXML
    public void onHyperLinkClicked() {
        try {
            this.player.showScene("GUI/RegistrationPage.fxml", "StyleSheets/RegistrationStyle.css");
        } catch(SetSceneException sE) {
           System.err.println(sE.getMessage()); //Da gestire graficamente
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

    public void showError(String message){
        this.errorLabel.setText(message);
        this.errorLabel.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> errorLabel.setVisible(false));
        pause.play();

    }
}
