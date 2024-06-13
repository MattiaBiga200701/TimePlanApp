package it.florentino.dark.timeplanapp.graphiccontroller;


import it.florentino.dark.timeplanapp.appcontroller.RegistrationController;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.NotUniqueEmailException;
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
    @FXML
    private Label managerEmailLabel;
    @FXML
    private TextField idField;

    private static UserBean newUser;
    private static RegistrationController controller;

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

        try {

            if(!(passwordString.equals(retypedPassword))) throw new CredentialException("Passwords do not match");

            if(checkRole.isSelected()) {
                newUser = new UserBean(usernameString, emailString, passwordString, Role.MANAGER);
                this.getScenePlayer().showScene("GUI/RegistrationPage2Man.fxml");
            }else{
                newUser = new UserBean(usernameString, emailString, passwordString, Role.EMPLOYEE);
                System.out.println(newUser);
                this.getScenePlayer().showScene("GUI/RegistrationPage2.fxml");
            }

        } catch (CredentialException e) {
            this.showError(e.getMessage());
        } catch (SetSceneException e) {
            throw new RuntimeException(e);
        }
        controller = new RegistrationController();
    }

    @FXML
    public void onGenerateButtonClick(){}  //MAnager

    @FXML
    public  void onVerifyButtonClick(){    //EMployee

        System.out.println(newUser);
        UserBean managerAssociated = null;
        String managerIdStr = this.idField.getText();

        try {

            if (!(managerIdStr.matches("\\d+"))){ throw new CredentialException("ManagerID not valid"); }
            int managerID = Integer.parseInt(this.idField.getText());
            newUser.setManagerID(managerID);
            managerAssociated = controller.checkManagerID(newUser);

        }catch(CredentialException | ServiceException e){
            this.showError(e.getMessage());
        }

        if(managerAssociated != null){
            this.managerEmailLabel.setText(managerAssociated.getEmail());
        }



    }

    @FXML
    public void onRegisterButtonClick() {

        try {

            controller.insertUser(newUser);
            System.out.println("User written");

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (NotUniqueEmailException e ){
            this.showError(e.getMessage());
        }
    }
    public Label getErrorLabel(){
            return this.errorLabel;
    }

}
