import java.io.File;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Created by Howard on 11/1/2017.
 */
public class Assignment_1 {
    public static void main(String[] args) throws Exception {
        File file = new File("test.txt");     // define a File object

        /* define Scanner objects to read the test.txt */
        Scanner scanner1 = new Scanner(file), scanner2 = new Scanner(file);

        /* write a loop to count number of commas in each row */
        String line = null;
        int count = 0;
        int countHeaderComma = 0;
        int countDataComma = 0;
        while (scanner1.hasNextLine()) {
            count++;
            line = scanner1.nextLine();
            if (count == 1) {
                for (int i = 0; i < line.length(); i++)
                    if (line.substring(i, i + 1).equals((","))) countHeaderComma++;
            } else if (count == 2)
                for (int i = 0; i < line.length(); i++)
                    if (line.substring(i, i + 1).equals(",")) countDataComma++;
        }scanner1.close();

        String[][] dataArray = new String[count - 1][countDataComma +1]; // array for student data
        String[][] mergeArray = new String[count - 1][countDataComma+2];
        String[] headerArray = new String[countHeaderComma + 1]; // array for headers
        String[] finalHeaderArray = new String[countDataComma + 2]; // array for headers without weights
        String[] averageArray = new String[countDataComma + 2]; // array for calculated averages
        String[] overallArray = new String[count - 1];// array for overall scores
        String[][] displayArray = new String[count + 1][countDataComma + 2]; // array for display
        int maxLength = 0; // longest word length


        int i = 0;
        int lineNo = 0;
        while (scanner2.hasNextLine())
            if (lineNo == 0) { //write headers into headerArray
                line = scanner2.nextLine();
                headerArray = line.split(",");
                lineNo++;
            } else{ //write data into dataArray
                line = scanner2.nextLine();
                dataArray[i++] = line.split(",");
            }scanner2.close();

         /* final header*/
        finalHeaderArray[0] = "ID";
        finalHeaderArray[1] = "Name";
        finalHeaderArray[finalHeaderArray.length - 1] = "Overall";
        for (int c1=2, c2 =2; c1 < headerArray.length && c2 < finalHeaderArray.length; c1 = c1 +2 , c2++)
            finalHeaderArray[c2] = headerArray[c1];
        for (int index = 0; index < finalHeaderArray.length; index++) {
            finalHeaderArray[index] = finalHeaderArray[index].trim();
            if (finalHeaderArray[index].length() > maxLength) maxLength = finalHeaderArray[index].length();
        }

        DecimalFormat df = new DecimalFormat("#0.00"); // decimal format

        double overall;
        double score;
        double weight;
        for (int r = 0; r < dataArray.length; r++) { // calculate and store overall scores in array
            overall = 0;
            for (int x = 2, h = 3; (x < countDataComma + 1) && (h < countHeaderComma); x++, h += 2) {
                score = Double.parseDouble(dataArray[r][x]);
                weight = Double.parseDouble(headerArray[h]) / 100;
                overall += score * weight;
            }
            String formattedOverall = df.format(overall);
            overallArray[r] = formattedOverall;
        }

        /* merge data and overall scores*/
        for (int row = 0; row < mergeArray.length; row++)
            for(int col = 0; col<mergeArray[0].length - 1; col++)
                mergeArray[row][col] = dataArray[row][col];
        for (int row = 0; row < mergeArray.length; row++)
            mergeArray[row][mergeArray[0].length-1] = overallArray[row];


        /* calculate averages*/
        averageArray[0] = "";
        averageArray[1] = "Average: ";
        double sum, num, avg;
        for (int avgCol= 2; avgCol < averageArray.length; avgCol++){
            sum = 0;
            avg = 0;
            for(int avgRow = 0; avgRow < mergeArray.length; avgRow++){
                num = Double.parseDouble(mergeArray[avgRow][avgCol]);
                sum += num;
                avg = sum / mergeArray.length;
            }String overallFormat = df.format(avg);
            averageArray[avgCol] = overallFormat;
        }

        for (int col = 0; col < finalHeaderArray.length; col++) displayArray[0][col]=finalHeaderArray[col];

        for (int col = 0; col < averageArray.length; col++)
            displayArray[displayArray.length - 1][col] = averageArray[col];

        for (int mRow = 0, row = 1; row < displayArray.length - 1; row++, mRow++)
            for (int col = 0; col < displayArray[row].length; col++)
                displayArray[row][col] = mergeArray[mRow][col];

        for (int row = 0; row < displayArray.length; row++)
            for (int col = 0; col < displayArray[row].length; col++) {
                displayArray[row][col] = displayArray[row][col].trim(); // trim excess space of character
                if (displayArray[row][col].length() > maxLength) maxLength = displayArray[row][col].length();
            }

        String format1 = "%-" + (maxLength + 2) + "s";
        String format2 = "%" + (maxLength + 2) + "s";

        for (int row = 0; row < displayArray.length; row++) {
            for (int col = 0; col < displayArray[0].length; col++) System.out.printf(format1, displayArray[row][col]);
            System.out.printf("\n");

        }
    }
}
