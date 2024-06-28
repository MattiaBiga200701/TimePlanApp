package it.florentino.dark.timeplanapp.graphiccontroller;


import it.florentino.dark.timeplanapp.appcontroller.RegistrationController;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.NotUniqueEmailException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;
import it.florentino.dark.timeplanapp.utils.printer.Printer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    @FXML
    private Label idLabel;
    @FXML
    private Button registerButton;
    @FXML
    private Button generateButton;

    private  UserBean newUser;
    private  RegistrationController controller;

    @FXML
    public void initialize(){

        this.controller =  new RegistrationController();
        if(registerButton != null){
            this.registerButton.setOnAction(this::onPreviousRegisterClick);
        }

    }

    @FXML
    public void onHyperLinkClicked(){
        try {
            this.getScenePlayer().showScene("GUI/LoginPage.fxml");
        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
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
            Printer.perror(e.getMessage());
        }

    }

    @FXML
    public void onGenerateButtonClick() {

        try{
            this.newUser = this.controller.createManagerID(this.newUser);
        }catch(CredentialException e){
            this.showError(e.getMessage());
        }catch(ServiceException e){
            Printer.perror(e.getMessage());
        }

        this.idLabel.setText(Integer.toString(this.newUser.getManagerID()));
        this.generateButton.setDisable(true);
        this.registerButton.setOnAction(this::onRegisterButtonClick);


    }  //Manager

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
            Printer.perror(e.getMessage());
        }

        this.registerButton.setOnAction(this::onRegisterButtonClick);
        if (managerAssociated != null) {
            this.managerEmailLabel.setText(managerAssociated.getEmail());
        }

    }


    @FXML
    public void onPreviousRegisterClick(ActionEvent event){

        if(this.newUser.getRole() == Role.MANAGER){
            this.showError("Generate your ManagerID!");
        }else this.showError("Verify your ManagerID!");

    }

    @FXML
    public void onRegisterButtonClick(ActionEvent event) {

        try {

            this.controller.insertUser(this.newUser);

        } catch (ServiceException e) {
            Printer.perror(e.getMessage());
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
