package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFiles {
    public static void main(String[] args) {
        File file = null;
        String filePath = "C:\\Users\\dauut\\Desktop\\Facebook_online_status\\12813704\\online.1400461261.12813704.txt";
        file = new File(filePath);
        ArrayList<String> lines = new ArrayList<>();
        ParseLines parseLines = new ParseLines();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
//                line = scanner.nextLine();
                lines.add(scanner.nextLine());
//                System.out.println(lines);t
            }

            parseLines.parseLines(lines);
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
