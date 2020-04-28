import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class FileManip {

    public static void Create(String fileName) {

        try {

            File newFile = new File(fileName);

            if (newFile.createNewFile()) {

                System.out.println("File created: " + newFile.getName());

            } else {

                System.out.println("File already exists.");

            }
        } catch (IOException e) {
          
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void Write(String fileName, String data) {
        try {
      
            FileWriter myWriter = new FileWriter(fileName,true);
            myWriter.write(data);
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
      
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}