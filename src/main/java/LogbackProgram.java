import java.util.Scanner;

public class LogbackProgram {
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Path to log file: ");
        String pathToFile = reader.nextLine();

        if (pathToFile != null) {
            LogReader logReader = new LogReader(pathToFile);
        }
    }
}
