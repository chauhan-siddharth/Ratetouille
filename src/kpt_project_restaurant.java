import java.util.ArrayList;

public class kpt_project_restaurant {
    ArrayList<ReadCSV.Reviews> data1;
    public void readReviews(){
        ReadCSV read1 = new ReadCSV();
        data1 = read1.allTask();
        read1.printreviews(data1);
    }

    public void stemReviews(ArrayList<ReadCSV.Reviews> data){
        ArrayList<ReadCSV.Reviews> data1 = new ArrayList<>();
        Parse p1 = new Parse();
        ReadCSV r1 = new ReadCSV();
        String filename = "stopwords.txt";
        String delimiter = "[ '.,&#?!:;$%+()\\-\\/*\"]+";
        data1 = r1.allTask();
        ArrayList<Parse.pureReview> pure = new ArrayList<Parse.pureReview>();
        p1.getStopWords(filename);
        pure = p1.tokenize(delimiter, data1);
        p1.printpure(pure);
        System.out.println("Pure ka size = " + pure.size());
    }



    public static void main(String[] args){
        kpt_project_restaurant p1 = new kpt_project_restaurant();
        p1.readReviews();
        System.out.println(p1.data1.size());
        p1.stemReviews(p1.data1);
    }


}