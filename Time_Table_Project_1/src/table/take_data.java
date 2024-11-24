package table;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static table.text_Parsing.Show;
import static table.text_Parsing.parse_text;
import static table.user_Input.Input_txt_file_location;
import static table.user_Input.Read_txt_file;
import static table.user_Input.name_List;
import static  table.text_Parsing.parsetext2;

public class take_data extends PATH{
    private String id="";
    private String password="";
    protected WebDriver driver;
    static List<List<String>> all_Time_Table_data = new ArrayList<>();
    void Set_date(String id, String password){
        this.id = id;
        this.password = password;
    }
    void Login(){
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized"); // 브라우저 최대 크기로 실행
            options.addArguments("--disable-popup-blocking"); // 팝업 창 무시하기
            options.addArguments("--log-level=3"); // Selenium 로그 레벨 최소화 (INFO 이상 로그만 출력)

            driver = new ChromeDriver(options);
            driver.get(URL);


            Thread.sleep(2000); //페이지 로딩 대기 시간
            WebElement element;

            Thread.sleep(2000);
            element = driver.findElement(By.xpath(ID_XPATH)); // id 입력
            element.sendKeys(id);

            Thread.sleep(2000);
            element = driver.findElement(By.xpath(PASSWORD_XPATH)); // password 입력
            element.sendKeys(password);

            Thread.sleep(2000);
            element = driver.findElement(By.xpath(BUTTON_XPATH));
            element.click();                                         // 버튼 클릭

            Thread.sleep(2000);
            element = driver.findElement(By.xpath(ADMIN_TIMETABLE_BUTTON_XPATH));
            element.click();
            Thread.sleep(2000);
            List<WebElement> divs = driver.findElements(By.cssSelector("div[class^='subject']"));

            // 각 <div> 안에서 <h3> 태그 찾기 및 텍스트 출력
            List<String> admin_time_table = new ArrayList<>();
            System.out.println("어드민 시간표");
            for (WebElement div : divs) {
                List<WebElement> h3Tags = div.findElements(By.tagName("h3"));
                for (WebElement h3 : h3Tags) {
                    admin_time_table.add(h3.getText());
                }
            }
            System.out.println("어드민 시간표"+admin_time_table);

           driver.get(HOME_URL);
           Thread.sleep(2000);
           element = driver.findElement(By.xpath(FRIENDS_BUTTON_XPATH));
           element.click();
           Thread.sleep(2000);

           System.out.println("친구 목록");
           WebElement fr_list = driver.findElement(By.xpath(FRIENDS_LIST_XPATH));
           List<WebElement> aTags = fr_list.findElements(By.tagName("a"));
           List<WebElement> h3Tags = fr_list.findElements(By.tagName("h3"));

           // href와 h3 텍스트를 각각 리스트로 저장
            List<String> hrefList = new ArrayList<>();
            List<String> h3TextList = new ArrayList<>();

            // <a> 태그의 href 추출
            for (WebElement aTag : aTags) {
                String href = aTag.getAttribute("href");
                if (href != null && !href.isEmpty()) {
                    hrefList.add(href);
                }
            }

            // <h3> 태그의 텍스트 추출
            for (WebElement h3Tag : h3Tags) {
                String h3Text = h3Tag.getText();
                if (h3Text != null && !h3Text.isEmpty()) {
                    h3TextList.add(h3Text);
                }
            }

            System.out.println("친구 이름 리스트: " + h3TextList);
            System.out.println("href 리스트: " + hrefList);

            for (String s : hrefList) {
                driver.get(s);
                List<String> Time_Table_datas = new ArrayList<>();
                Thread.sleep(2000);
                List<WebElement> div = driver.findElements(By.cssSelector("div[class^='subject']"));
                for (WebElement div1 : div) {
                    List<WebElement> h3Tag = div1.findElements(By.tagName("h3"));
                    for (WebElement h3 : h3Tag) {
                        Time_Table_datas.add(h3.getText());
                    }
                }
                all_Time_Table_data.add(Time_Table_datas);
                driver.get("https://everytime.kr/friend");
                Thread.sleep(2000);
            }
            int id=0;
            h3TextList.add("admin");
            all_Time_Table_data.add(admin_time_table);

            for (List<String> innerList : all_Time_Table_data) {
               System.out.print(h3TextList.get(id++)+" : ");
                for (String data : innerList) {
                    System.out.print(data + " ");
                }
                System.out.println();
            }
            Input_txt_file_location();
            Read_txt_file();
            if(name_List.contains("all"))
                parse_text(all_Time_Table_data); // 데이터 파싱 스태틱 메소드
            else {
                System.out.println(name_List);
                for (String name : name_List) {
                    int n = 0;
                    for (List<String> innerList : all_Time_Table_data) {
                        if (name.equals(h3TextList.get(n))) {
                            parsetext2(innerList);
                            break;
                        }
                        ++n;
                    }
                }
            }
            Show();
        }catch (Exception e){
            System.err.println("로그인 실패, 아이디나 인터넷 환경을 다시 한번 확인해 주세요.");
        }
        finally {
            System.out.println("접속 종료!\n");
            if (driver != null) {
                driver.quit();
            }
        }
    }
}