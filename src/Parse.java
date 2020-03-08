import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Parse {

    public   String[] words = new String[0];
    public class pureReview{
        String restaurantID;
        ArrayList<String> reviewArray;

        public void setReviewArray(String rid, ArrayList<String> restReview) {
//            Reviews re1 = new Reviews();
            restaurantID = rid;
            reviewArray = restReview;
        }
    }
    public void getStopWords(String fileName) {
        String sw = new String();
        try {
            String lines = "";
            Path path = Paths.get(fileName);
            lines = new String(Files.readAllBytes(path));
            lines = lines.toLowerCase();
            words = lines.split("\\n");
            Arrays.sort(words);
            for(int i=0;i<words.length;i++) {
                sw += words[i] + " ";
            }
            System.out.println("Stopwords => " + sw);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int searchStopWord(String key) {
        int lo=0;
        int hi = words.length-1;
        // System.out.println(stopWords.length);

        while(lo<=hi) {
            int mid = lo +(hi-lo)/2;
            int result = key.compareTo(words[mid]);
            if(result<0) hi = mid-1;
            else if(result > 0) lo = mid+1;
            else return mid;
        }
        return -1;
    }
    public void printreviews(ArrayList<ReadCSV.Reviews> data) {
        String restIdString = new String();
        for (ReadCSV.Reviews r : data) {
            restIdString = "" + r.restaurantID + " : ";
            restIdString += r.review + " ";
            System.out.println(restIdString);
        }
    }
    public ArrayList<pureReview> tokenize(String delimiter, ArrayList<ReadCSV.Reviews> data1) {
        ArrayList<pureReview> stemmedReview = new ArrayList<pureReview>();
//        ArrayList<String> stemmered = new ArrayList<String>();
//        for (ReadCSV.Reviews index: data1) {
          for(int i = 1; i< data1.size();i++){
            ArrayList<String> pureTokens = new ArrayList<String>();
//            System.out.println(data1.get(i).restaurantID + " => " + data1.get(i).review);
            String[] tokens = new String[0];
            tokens = data1.get(i).review.split(delimiter);
              for (String token: tokens) {
//                  System.out.println(token);
                  if(searchStopWord(token) == -1) {
                    pureTokens.add(token);
                  }
                }
//             System.out.println(pureTokens);
            pureReview r1 = new pureReview();
            r1.setReviewArray(data1.get(i).restaurantID, pureTokens);
            stemmedReview.add(r1);
        }
 //         printpure(stemmedReview);
//        System.out.println(stemmedReview.size());
        return stemmedReview;
   }
    public void printpure(ArrayList<pureReview> pure){
//        String restIdString = new String();
        for (pureReview r : pure) {
            System.out.println(r.restaurantID + " ===>>> " + r.reviewArray);
        }
    }
    public static void main(String[] args){
        ArrayList<ReadCSV.Reviews> data1 = new ArrayList<>();
        Parse p1 = new Parse();
        ReadCSV r1 = new ReadCSV();
        String filename = "stopwords.txt";
        String delimiter = "[ '.,&#?!:;$%+()\\-\\/*\"]+";
        data1 = r1.allTask();
        ArrayList<pureReview> pure = new ArrayList<pureReview>();
        p1.getStopWords(filename);
//        p1.printreviews(data1);
        pure = p1.tokenize(delimiter, data1);
//        System.out.println(pure.size());
//        p1.printpure(pure);

        System.out.println("Pure ka size = " + pure.size());
    }

}
