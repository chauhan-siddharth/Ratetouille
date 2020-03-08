// Read CSV class reads the file with restaurantID and the reviews and that data will be used to form the positional index dictionary.

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;

public class ReadCSV {
    ArrayList<Reviews> data1 = new ArrayList<>();

    public class Reviews {
        String restaurantID;
        String review;

        public void setreviews(String rid, String restReview) {
//            Reviews re1 = new Reviews();
            restaurantID = rid;
            review = restReview;
        }
    }

    public void printreviews(ArrayList<Reviews> data) {
        String restIdString = new String();
        for (Reviews r : data) {
            restIdString = "" + r.restaurantID + " : ";
            restIdString += r.review + " ";
            System.out.println(restIdString);
        }
    }

    public ArrayList<Reviews> allTask(){
       // ReadCSV r1 = new ReadCSV();
        CSVReader reader = null;
        ArrayList<Reviews> data = new ArrayList<>();
        try {
//parsing a CSV file into CSVReader class constructor
            reader = new CSVReader(new FileReader("new.csv"));
            String[] nextLine;
            int i = 0;
//reads one line at a time
            while ((nextLine = reader.readNext()) != null) {
//                System.out.println(nextLine);
                Reviews re1 = new Reviews();
                re1.setreviews(nextLine[0], nextLine[1]);
//                      System.out.print(Reviews.review + "\n");
                data.add(re1);
//                        r1.data.insert(restaurantID, nextLine[0]);
//                        r1.data.insert(review,nextLine[1]);
                i++;
            }
            System.out.println("Count of reviews = " + i);
//                    System.out.print("\n");

//               System.out.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void main(String[] args) {
        ReadCSV read1 = new ReadCSV();
        read1.data1 = read1.allTask();
        read1.printreviews(read1.data1);

        }
    }


