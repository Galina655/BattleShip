import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Opens file and extracts boys' names in one file,
 * and girls' names in another one.
 * Displays total number of boys, girls, and total
 * number of babies
 */
public class BabyNames {
    public static void main(String[] args) throws IOException {
        int totalBabies = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        Scanner in = new Scanner(new File("babynames.txt"));
        FileWriter boys = new FileWriter("boynames.txt");
        FileWriter girls = new FileWriter("girlnames.txt");
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(" ");
            boys.write(line[2] + "\n");
            girls.write(line[8] + "\n");
            totalBoys += Integer.parseInt(line[4]);
            totalGirls += Integer.parseInt(line[10]);
        }
        totalBabies = (totalBoys + totalGirls);
        boys.close();
        girls.close();
        System.out.println("Total number of boys: " + totalBoys);
        System.out.println("Total number of girls: " + totalGirls);
        System.out.println("Total number of babies: " + totalBabies);

    }
}



