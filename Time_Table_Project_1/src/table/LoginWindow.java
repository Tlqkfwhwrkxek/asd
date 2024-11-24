package table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintStream;

public class LoginWindow {
    static String id;
    static String pw;
    private static final Object lock = new Object(); // 동기화 객체
    private static boolean isButtonClicked = false; // 버튼 클릭 여부

    // 로그인 창 메서드
    static void Login_Win() {
        JFrame f = new JFrame("로그인 창");          // 프레임 생성
        JLabel l1 = new JLabel("사용자:");
        l1.setBounds(20, 20, 80, 30);
        JTextField text = new JTextField();
        text.setBounds(100, 20, 100, 30);
        JLabel l2 = new JLabel("비밀번호:");
        l2.setBounds(20, 75, 80, 30);
        JPasswordField value = new JPasswordField();
        value.setBounds(100, 75, 100, 30);
        JButton b = new JButton("로그인");
        b.setBounds(100, 120, 80, 30);

        f.add(l1);
        f.add(text);
        f.add(l2);
        f.add(value);
        f.add(b);
        f.setSize(300, 250);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 로그인 버튼 클릭 이벤트
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                id = text.getText();
                pw = new String(value.getPassword());
                isButtonClicked = true;
                synchronized (lock) {
                    lock.notify();
                }
                openHomeScreen(f);
            }
        });

        synchronized (lock) {
            while (!isButtonClicked) {
                try {
                    lock.wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // 홈 화면 (결과 출력 창)
    public static void openHomeScreen(JFrame loginFrame) {
        loginFrame.dispose();

        JFrame homeFrame = new JFrame("결과 출력 창");
        homeFrame.setSize(800, 600);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        homeFrame.setLayout(new BorderLayout());
        homeFrame.add(scrollPane, BorderLayout.CENTER);

        PrintStream printStream = new PrintStream(new TextAreaOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);

        homeFrame.setVisible(true);
    }

    // TextAreaOutputStream 클래스 위치
    // JTextArea에 콘솔 출력을 실시간으로 연결하는 OutputStream
    static class TextAreaOutputStream extends OutputStream {
        private final JTextArea textArea;
        private final StringBuilder buffer = new StringBuilder();

        public TextAreaOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) {
            synchronized (buffer) {
                buffer.append((char) b);
                if ((char) b == '\n') {
                    flushBufferToTextArea();
                }
            }
        }

        @Override
        public void write(byte[] b, int off, int len) {
            synchronized (buffer) {
                buffer.append(new String(b, off, len));
                if (buffer.toString().contains("\n")) {
                    flushBufferToTextArea();
                }
            }
        }

        private void flushBufferToTextArea() {
            SwingUtilities.invokeLater(() -> {
                synchronized (buffer) {
                    textArea.append(buffer.toString());
                    textArea.setCaretPosition(textArea.getDocument().getLength());
                    buffer.setLength(0);
                }
            });
        }

        @Override
        public void flush() {
            flushBufferToTextArea();
        }
    }
}