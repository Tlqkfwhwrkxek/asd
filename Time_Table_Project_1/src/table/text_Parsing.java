package table;

import java.util.ArrayList;
import java.util.List;

import static table.user_Input.name_List;

public class text_Parsing{

    static List<List<String>> day_time_list = new ArrayList<List<String>>(); // 과목 명, 요일, 시작시간, 끝나는 시간, 이름

    public static void parse_text(List<List<String>> Time_Table_List){
        for (List<String> row : Time_Table_List) {
            for (String data : row) {
                try{
                    String[] parts = data.split("_");
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("올바르지 않은 데이터 헝식1.");
                    }
                    String subject = parts[0];
                    String[] time_day = parts[1].split(" ");
                    if(time_day.length != 3){
                        throw new IllegalArgumentException("올바르지 않은 데이터 헝식2.");
                    }
                    String day = time_day[0];
                    String start_time = time_day[1];
                    String end_time = time_day[2];
                    if (!start_time.matches("\\d{2}:\\d{2}") || !end_time.matches("\\d{2}:\\d{2}")) {
                        throw new IllegalArgumentException("잘못된 시간 형식");
                    }
                    List<String> dse = new ArrayList<>();
                    dse.add(subject);
                    dse.add(day);
                    dse.add(start_time);
                    dse.add(end_time);
                    day_time_list.add(dse);
                }
                catch (Exception e){
                    System.err.println("파싱 데이터 에러:  \"" + data + "\" - " + e.getMessage());
                }
            }
        }
    }

    public static void parsetext2(List<String> Time_Table_List){ // txt.이름이 존재 시
        for (String data : Time_Table_List) {
            try {
                String[] parts = data.split("");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("올바르지 않은 데이터 헝식1.");
                }
                String subject = parts[0];
                String[] time_day = parts[1].split(" ");
                if (time_day.length != 3) {
                    throw new IllegalArgumentException("올바르지 않은 데이터 헝식2.");
                }
                String day = time_day[0];
                String start_time = time_day[1];
                String end_time = time_day[2];
                if (!start_time.matches("\\d{2}:\\d{2}") || !end_time.matches("\\d{2}:\\d{2}")) {
                    throw new IllegalArgumentException("잘못된 시간 형식");
                }
                List<String> dse = new ArrayList<>();
                dse.add(subject);
                dse.add(day);
                dse.add(start_time);
                dse.add(end_time);
                day_time_list.add(dse);
            }
            catch (Exception e){
                System.err.println("파싱 데이터 에러:  " + data + " - " + e.getMessage());
            }
        }
    }

    public static void Show(){
        for (List<String> innerList : day_time_list) {
            for (String data : innerList) {
                System.out.print(data + " ");
            }
            System.out.println();
        }
    }

}
