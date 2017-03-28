package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    private UserDao userDao;

    @Autowired
    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            System.out.println(user.getUsername()+" "+user.getPassword());
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {
        return !( areRightSize(username, password) && isUsernameAlphabetic(username) 
                && passwordContainsSpecial(password) && uniqueUsername(username) );
    }
    
    private boolean areRightSize(String username, String password){
        return username.length() >= 3 && password.length() >= 8;
    }
    
    private boolean isUsernameAlphabetic(String username){
        for(int i = 0 ; i < username.length() ; i++){
            if(!Character.isAlphabetic( username.charAt(i)) ){
                return false;
            }
        }
        
        return true;
    }
    
    private boolean passwordContainsSpecial(String password){
        for(int i = 0 ; i < password.length() ; i++){
            if(!Character.isAlphabetic( password.charAt(i) )){
                return true;
            }
        }
        
        return false;
    }
    
    private boolean uniqueUsername(String username){
        for(User user : userDao.listAll()){
            if(user.getUsername().equals(username)){
                return false;
            }
        }
        
        return true;
    }
   
}
