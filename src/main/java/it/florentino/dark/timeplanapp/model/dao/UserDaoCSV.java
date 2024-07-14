package it.florentino.dark.timeplanapp.model.dao;

import it.florentino.dark.timeplanapp.exceptions.DAOException;
import it.florentino.dark.timeplanapp.model.entities.User;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;

import java.io.*;
import java.util.Random;

public class UserDaoCSV implements UserDao {

    private final Random random = new Random();
    private static final String CSV_PATH = "src/main/resources/it/florentino/dark/timeplanapp/persistence/CSV/User.csv";
    public User loginProcedure(User user) throws DAOException {

        String email = user.getEmail();
        String password = user.getPassword();

        try( BufferedReader reader = new BufferedReader(new FileReader(CSV_PATH))) {

            String tuple;
            while((tuple = reader.readLine()) != null) {

                String[] attribute = tuple.split(",");
                if(attribute[1].equals(email) && attribute[2].equals(password)){

                    Role role = Role.fromInt(Integer.parseInt(attribute[3]));
                    int managerID = Integer.parseInt(attribute[4]);

                    return  new User(attribute[0], attribute[1], attribute[2], role, managerID);

                }
            }


        }catch(IOException e){
            throw new DAOException(e.getMessage());
        }

        return null;
    }

    public void registrationProcedure(User newUser) throws DAOException {

        String username = newUser.getUsername();
        String email = newUser.getEmail();
        String password = newUser.getPassword();
        String role = String.valueOf(newUser.getRole().getId());
        String managerID = String.valueOf(newUser.getManagerID());

        try(BufferedWriter writer = new BufferedWriter( new FileWriter(CSV_PATH, true))){

            String[] tuple = {username , email, password, role, managerID};
            writer.write(String.join(",", tuple));
            writer.newLine();

        }catch(IOException e){
            throw new DAOException(e.getMessage());
        }

    }

    public User getManagerAssociatedTo(User user) throws DAOException {

        String managerIDStr = String.valueOf(user.getManagerID());
        String roleStr = String.valueOf(Role.MANAGER.getId());
        User managerAssociated = null;

        try(BufferedReader reader = new BufferedReader(new FileReader(CSV_PATH))){

            String tuple;
            while((tuple = reader.readLine()) != null){

                String[] attribute = tuple.split(",");
                if(attribute[3].equals(roleStr) && attribute[4].equals(managerIDStr)){

                    String username = attribute[0];
                    String email = attribute[1];
                    String password = attribute[2];
                    Role role = Role.MANAGER;
                    int managerID = Integer.parseInt(attribute[4]);
                    managerAssociated = new User(username, email, password, role, managerID);
                    break;
                }
            }


        }catch(IOException e){
            throw new DAOException(e.getMessage());
        }

        return managerAssociated;
    }

    public User createManagerID(User user) throws DAOException {


        int newManagerID = 0;
        String tuple;
        boolean loopVar = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_PATH))) {

            reader.readLine();
            while (loopVar) {

                loopVar = false;
                newManagerID = 100000 + this.random.nextInt(900000);

                while ((tuple = reader.readLine()) != null) {

                    String[] attribute = tuple.split(",");

                    if ((Integer.parseInt(attribute[4])) == newManagerID) {
                        loopVar = true;
                        break;
                    }
                }
            }

            user.setManagerID(newManagerID);


        } catch (IOException e) {
            throw new DAOException(e.getMessage());
        }
        return user;
    }



    public boolean isEmailUnique(User user) throws DAOException{

        String email = user.getEmail();


        try(BufferedReader reader = new BufferedReader(new FileReader(CSV_PATH))){
            String tuple;
            while((tuple = reader.readLine()) != null){
                String[] attribute = tuple.split(",");

                if(attribute[1].equals(email)) {
                    return false;
                }
            }

        }catch(IOException e){
            throw new DAOException(e.getMessage());
        }

        return true;
    }



}
