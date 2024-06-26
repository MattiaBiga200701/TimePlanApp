package it.florentino.dark.timeplanapp.graphiccontroller;


import it.florentino.dark.timeplanapp.appcontroller.RegistrationController;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.NotUniqueEmailException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.logging.Level;
import java.util.logging.Logger;

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
    @FXML
    private Button registerButton;

    private  UserBean newUser;
    private  RegistrationController controller;

    @FXML
    public void initialize(){
        this.controller =  new RegistrationController();
    }

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

            if(this.checkRole.isSelected()) {
                this.newUser = new UserBean(usernameString, emailString, passwordString, Role.MANAGER);
                this.getScenePlayer().showScene("GUI/RegistrationPage2Man.fxml", this.newUser);
            }else{

                this.newUser = new UserBean(usernameString, emailString, passwordString, Role.EMPLOYEE);
                this.getScenePlayer().showScene("GUI/RegistrationPage2.fxml", this.newUser);
            }

        } catch (CredentialException e) {
            this.showError(e.getMessage());
        } catch (SetSceneException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

    }

    @FXML
    public void onGenerateButtonClick(){}  //Manager

    @FXML
    public  void onVerifyButtonClick(){    //Employee


        UserBean managerAssociated = null;
        String managerIdStr = this.idField.getText();

        try {

            if (!(managerIdStr.matches("\\d+"))){ throw new CredentialException("ManagerID not valid"); }
            int managerID = Integer.parseInt(this.idField.getText());
            this.newUser.setManagerID(managerID);
            managerAssociated = this.controller.checkManagerID(this.newUser);

        }catch(CredentialException e){
            this.showError(e.getMessage());
        }catch(ServiceException e){
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        registerButton.setOnAction(this::onRegisterButtonClick);
        this.managerEmailLabel.setText(managerAssociated.getEmail());


    }


    @FXML
    public void onPreviousRegisterClick(){

        this.showError("Please verify your MangerID");

    }

    @FXML
    public void onRegisterButtonClick(ActionEvent event) {

        try {

            this.controller.insertUser(this.newUser);

        } catch (ServiceException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        } catch (NotUniqueEmailException e ){
            this.showError(e.getMessage());
        }
    }

    public Label getErrorLabel(){
            return this.errorLabel;
    }

    public void setAttribute(UserBean newUser){
        this.newUser = newUser;
    }

}
