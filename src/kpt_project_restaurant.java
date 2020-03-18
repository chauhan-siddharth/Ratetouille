import java.util.ArrayList;
import java.util.Arrays;

public class kpt_project_restaurant {
    ArrayList<ReadCSV.Reviews> data1;
    ArrayList<Parse.pureReview> pure = new ArrayList<Parse.pureReview>();
    String[] positive = new String[0];
    String[] negative = new String[0];
    String[] neutral = new String[0];
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
        p1.getStopWords(filename);
        pure = p1.tokenize(delimiter, data1);
        p1.printpure(pure);
        System.out.println("Pure ka size = " + pure.size());
    }

    public void readWords(){
        String delimiter = ",";
        read_analysis_words a1 = new read_analysis_words();
        positive = a1.tokenize(delimiter, "positive.txt");
        Arrays.sort(positive);
        for (String word:positive) {
            System.out.print(word  + " ");
        }
        negative = a1.tokenize(delimiter, "negative.txt");
        Arrays.sort(negative);
        for (String word:negative) {
            System.out.print(word  + " ");
        }
        neutral = a1.tokenize(delimiter, "neutral.txt");
        Arrays.sort(neutral);
        for (String word:neutral) {
            System.out.print(word + " ");
        }
    }
    public int isInPositive(String key) {
        int lo=0;
        int hi = positive.length-1;

        while(lo<=hi) {
            int mid = lo +(hi-lo)/2;
            int result = key.compareTo(positive[mid]);
            if(result<0) {hi = mid-1;}
            else if(result > 0) {lo = mid+1;}
            else {return mid;}
        }
        return -1;
    }

    public int isInNegative(String key) {
        int lo=0;
        int hi = negative.length-1;

        while(lo<=hi) {
            int mid = lo +(hi-lo)/2;
            int result = key.compareTo(negative[mid]);
            if(result<0) {hi = mid-1;}
            else if(result > 0) {lo = mid+1;}
            else {return mid;}
        }
        return -1;
    }

    public int isInNeutral(String key) {
        int lo=0;
        int hi = neutral.length - 1;

        while(lo<=hi) {
            int mid = lo +(hi-lo)/2;
            int result = key.compareTo(neutral[mid]);
            if(result<0) {hi = mid-1;}
            else if(result > 0) {lo = mid+1;}
            else {return mid;}
        }
        return -1;
    }

    public int calculateReviewRating (Parse.pureReview entry){
        int positiveCount = 0;
        int negativeCount = 0;
        int neutralCount = 0;
        int netValue = 0;
                for (String word:entry.reviewArray) {
                if(isInPositive(word) != -1){
                    positiveCount = positiveCount + 1;
                }
                else if(isInNegative(word) != -1){
                    negativeCount = negativeCount - 1;
                }
                else if(isInNeutral(word) != -1){
                    neutralCount = neutralCount + 0;
                }
                else{
                    continue;
                }
            }

        netValue = positiveCount + negativeCount + neutralCount;
        return netValue;
    }

    public void sentimentAnalysis(){
        System.out.println(pure);
        for (Parse.pureReview entry:pure) {
            int value = 0;
            value = calculateReviewRating(entry);
            if(value == 0 ){
                System.out.println("Neutral Review");
            }
            else if(value > 0){
                System.out.println("Positive Review");
            }
            else {
                System.out.println("Negative Review");
            }
        }
    }

    public static void main(String[] args) {
        kpt_project_restaurant p1 = new kpt_project_restaurant();
        p1.readReviews();
        System.out.println(p1.data1.size());
        p1.stemReviews(p1.data1);
        p1.readWords();
        p1.sentimentAnalysis();
    }
}