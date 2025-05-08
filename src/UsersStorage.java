import java.util.HashMap;

public class UsersStorage {
    protected HashMap<String, String> usersInfo = new HashMap<>();

    public UsersStorage() {
        usersInfo.put("admin", "admin");
        usersInfo.put("tariq", "2018");
        usersInfo.put("sara", "2024");
        usersInfo.put("hamada", "2025");
    }

    public HashMap<String, String> getUsersInfo() {
        return usersInfo;
    }
}
