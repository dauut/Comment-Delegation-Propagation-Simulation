import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        String path = "C:\\Users\\dauut\\Desktop\\12813704_Simulation_general_info.txt";

        File file = new File(path);
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> users = new ArrayList<>();
        ArrayList<String> filesPath = new ArrayList<>();
        ArrayList<String> files = new ArrayList<>();


        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }

            String delims = "[ ]";
            String delims1 = "\t";
            for (int i = 0; i < lines.size(); i++) {
                String[] firstLineToken = lines.get(i).split(delims);
                String[] inFirs = firstLineToken[0].split(delims1);
                users.add(inFirs[2]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File results = new File("C:\\Users\\dauut\\Desktop\\sub");
        File[] listofFiles = results.listFiles();

        String delims2 = "_";
        String[] token;
        for (int i = 0; i < listofFiles.length; i++) {
            token = listofFiles[i].getName().split(delims2);
            files.add(token[0]);
            filesPath.add(listofFiles[i].getAbsolutePath());
        }

        for (int i = 0; i < users.size(); i++) {
            int counter = 0;
            boolean found = false;
            while (!found && counter < files.size()) {
                if (!users.get(i).equals(files.get(counter))) {
                    counter++;
                }else{
                    found = true;
                }
            }
            if (counter < files.size()) {
                File deleteFile = new File(filesPath.get(counter));
                if (deleteFile.delete()) {
                    System.out.println(filesPath.get(counter) + "File deleted successfully");
                } else {
                    System.out.println(files.get(counter) + "Failed to delete the file");
                }
            }
        }


    }
}
