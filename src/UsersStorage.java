import java.util.HashMap;

public class UsersStorage {
    HashMap<String, String> usersInfo = new HashMap<String, String>();

    UsersStorage(){
        usersInfo.put("admin", "admin"); // puts a key-value pair into the hashMap
    }

   protected HashMap getUsersInfo(){
        return usersInfo;
    }
}
