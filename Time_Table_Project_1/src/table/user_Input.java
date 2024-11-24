package table;

import com.google.common.io.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class user_Input {
    static String file_location;
    static List<String> name_List = new ArrayList<>();
    static void Read_txt_file() throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(new File(file_location));
            while (scanner.hasNext()) {
                String str = scanner.next();
                name_List.add(str);
            }
            scanner.close();
            if(name_List.isEmpty())
                name_List.add("all");
            if(!name_List.contains("all"))
                name_List.add("admin");
            System.out.println(name_List);
        }
        catch (FileNotFoundException _ ) {
            System.out.println("File not found");
        }
    }
    static void Input_txt_file_location(){
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println("Please enter the location of the file you wish to read: ");
        input = scanner.nextLine();
        file_location = input;
        scanner.close();
    }
}
// /Users/mnsk_kang/Desktop/인원리스트.txt