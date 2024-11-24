package table;

public class PATH{
    public final String URL = "https://account.everytime.kr/login";
    public final String HOME_URL = "https://account.everytime.kr/login";
    String ID_XPATH = "/html/body/div[1]/div/form/div[1]/input[1]";
    String PASSWORD_XPATH = "/html/body/div[1]/div/form/div[1]/input[2]";
    String BUTTON_XPATH = "/html/body/div[1]/div/form/input";

    String ADMIN_TIMETABLE_BUTTON_XPATH = "//*[@id=\"menu\"]/li[2]/a";     // 로그인 한 사용자 시간표 패스 //*[@id="menu"]/li[2]/a
    String FRIENDS_BUTTON_XPATH         = "//*[@id=\"menu\"]/li[5]/a";     // 사용자 친구              //*[@id="menu"]/li[5]/a
    String FRIENDS_LIST_XPATH           = "//*[@id=\"container\"]/div[2]"; // 사용자 친구 목록          //*[@id="container"]/div[2]

    //String TIME_TABLE_XPATH = "//*[@id=\"container\"]/div";
    //String CLASS_INFORMATION="//*[@id=\"container\"]/div/div[2]/table/tbody/tr/td[1]";
    // 시간 월 화 수 목 금
    //String TIMES_XPATH ="//*[@id=\"container\"]/div/div[2]/table/tbody/tr/th/div";
    //String DAYS_XPATH  ="//*[@id=\"container\"]/div/div[1]";
}
