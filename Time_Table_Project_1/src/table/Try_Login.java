package table;

import org.jsoup.Jsoup;

public class Try_Login extends  take_data{
    Try_Login(String id, String password){
        System.out.println("접속중,,,,");
        Set_date(id,password);
        Login();
    }
}
