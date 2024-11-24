package table;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static table.text_Parsing.day_time_list;

public class Time_Search {
    public static void Time_Finder() {
        // GUI 창 생성
        JFrame frame = new JFrame("빈 시간 결과");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);

        // 데이터 처리
        List<String> days = List.of("월", "화", "수", "목", "금", "토", "일");
        Map<String, boolean[]> weeklySchedule = new HashMap<>();

        // 초기화: 하루를 10분 단위 72개의 슬롯으로 나눔
        for (String day : days) {
            weeklySchedule.put(day, new boolean[72]);
        }

        // 입력 데이터를 읽어 시간 슬롯 채우기
        for (List<String> entry : day_time_list) {
            String day = entry.get(1);
            int startIndex = timeToIndex(entry.get(2));
            int endIndex = timeToIndex(entry.get(3));

            boolean[] daySchedule = weeklySchedule.get(day);
            for (int i = startIndex; i < endIndex; i++) {
                daySchedule[i] = true; // 예약된 시간
            }
        }

        // 요일별 빈 시간 결과 출력
        SwingUtilities.invokeLater(() -> {
            textArea.append("=== 빈 시간 결과 ===\n");
            for (String day : days) {
                boolean[] daySchedule = weeklySchedule.get(day);
                textArea.append(day + "요일의 빈 시간:\n");
                printFreeTimes(daySchedule, textArea);

                for (List<String> entry : day_time_list) {
                    if (Objects.equals(entry.get(1), day)) {
                        textArea.append("  - " + entry + "\n");
                    }
                }

                textArea.append("\n");
            }
        });
    }

    // 시간 문자열을 인덱스로 변환 (09:00 ~ 21:00 기준)
    private static int timeToIndex(String time) {
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return (hour - 9) * 6 + (minute / 10); // 하루를 10분 단위로 나눔
    }

    // 빈 시간 출력 (JTextArea에 결과를 출력)
    private static void printFreeTimes(boolean[] schedule, JTextArea textArea) {
        int start = -1;
        for (int i = 0; i < schedule.length; i++) {
            if (!schedule[i]) { // 빈 시간
                if (start == -1) start = i; // 빈 시간 시작
            } else {
                if (start != -1) { // 빈 시간이 끝난 지점
                    textArea.append("  " + indexToTime(start) + " ~ " + indexToTime(i) + "\n");
                    start = -1;
                }
            }
        }

        // 마지막 빈 시간 처리
        if (start != -1) {
            textArea.append("  " + indexToTime(start) + " ~ " + indexToTime(schedule.length) + "\n");
        }
    }

    // 인덱스를 시간 문자열로 변환
    private static String indexToTime(int index) {
        int hour = 9 + index / 6; // 9시부터 시작
        int minute = (index % 6) * 10;
        return String.format("%02d:%02d", hour, minute);
    }
}
