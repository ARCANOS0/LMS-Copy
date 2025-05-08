import java.awt.*;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;


public class Main {


    public static void main(String[] args) {
        UsersStorage usersStorage = new UsersStorage();
        LogInFrame logInPage = new LogInFrame(usersStorage.getUsersInfo());
        // the login page takes the username and password stored in the hashMap as an argument, modified in the constructor

        //set the font in the Main class, because it will not work if you use it only in the LogInFrame Only

    }
}