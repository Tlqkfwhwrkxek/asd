package table;

import static table.LoginWindow.Login_Win;
import static table.LoginWindow.pw;
import static table.LoginWindow.id;
import static table.Time_Search.Time_Finder;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.flush();
            System.err.flush();
        }));

        Login_Win();
        Try_Login tl = new Try_Login(id,pw);
        Time_Finder();
    }
}