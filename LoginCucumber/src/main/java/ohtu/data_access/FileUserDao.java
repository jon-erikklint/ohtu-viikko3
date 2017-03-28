package ohtu.data_access;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ohtu.domain.User;

public class FileUserDao implements UserDao{

    String filename;
    
    public FileUserDao(String filename){
        this.filename = filename;
    }
    
    @Override
    public List<User> listAll() {
        Scanner fileReader = createScanner();
        List<User> users = new ArrayList<>();
        
        if(fileReader == null){
            return users;
        }
        
        while(fileReader.hasNextLine()){
            users.add( createUser(fileReader.nextLine()) );
        }
        
        fileReader.close();
        
        return users;
    }

    @Override
    public User findByName(String name) {
        Scanner fileReader = createScanner();
        if(fileReader == null){
            return null;
        }
        
        while(fileReader.hasNextLine()){
            User user = createUser(fileReader.nextLine());
            if(user.getUsername().equals(name)){
                return user;
            }
        }
        
        fileReader.close();
        
        return null;
    }

    @Override
    public void add(User user) {
        FileWriter writer;
        try{writer = new FileWriter(filename, true);}
        catch(Exception e){return;}
        
        String toAdd = user.getUsername()+" "+user.getPassword()+"\n";
        
        try{writer.append(toAdd);}catch(Exception e){};
        
        try{writer.close();}catch(Exception e){};
    }
    
    private User createUser(String line){
        int whitespace = 0;
            
            for(int i = 0 ; i < line.length() ; i++){
                if(line.charAt(i) == ' '){
                    whitespace = i;
                    break;
                }
            }
            
        return new User(line.substring(0, whitespace), line.substring(whitespace+1));
    }
    
    private Scanner createScanner(){
        try{return new Scanner(new File(filename));}
        catch(Exception e){return null;}
    }
    
}
