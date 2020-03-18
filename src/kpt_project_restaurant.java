import java.util.ArrayList;

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
        System.out.println(positive);
        negative = a1.tokenize(delimiter, "negative.txt");
        System.out.println(negative);
        neutral = a1.tokenize(delimiter, "neutral.txt");
        System.out.println(neutral);
    }
    public int isInPositive(String key) {
        int lo=0;
        int hi = positive.length-1;

        while(lo<=hi) {
            int mid = lo +(hi-lo)/2;
            int result = key.compareTo(positive[mid]);
            if(result<0) hi = mid-1;
            else if(result > 0) lo = mid+1;
            else return mid;
        }
        return -1;
    }

    public int isInNegative(String key) {
        int lo=0;
        int hi = negative.length-1;
        // System.out.println(stopWords.length);

        while(lo<=hi) {
            int mid = lo +(hi-lo)/2;
            int result = key.compareTo(negative[mid]);
            if(result<0) hi = mid-1;
            else if(result > 0) lo = mid+1;
            else return mid;
        }
        return -1;
    }

    public int isInNeutral(String key) {
        int lo=0;
        int hi = neutral.length - 1;

        while(lo<=hi) {
            int mid = lo +(hi-lo)/2;
            int result = key.compareTo(neutral[mid]);
            if(result<0) hi = mid-1;
            else if(result > 0) lo = mid+1;
            else return mid;
        }
        return -1;
    }

    public int calculateReviewRating (Parse.pureReview entry){
        int positiveCount = 0;
        int negativeCount = 0;
        int neutralCount = 0;
        int netValue = 0;
                    System.out.println(entry.restaurantID + " = " + entry.reviewArray);
                    System.out.println("Size of array from calculateReviewRating " + entry.reviewArray.size());
                for (String word:entry.reviewArray) {
                    System.out.println("Word being checked = " + word);
                if(isInPositive(word) != -1){
                    System.out.println("+ word found " + word );
                    positiveCount = positiveCount + 1;
                }
                else if(isInNegative(word) != -1){
                    System.out.println("- word found " + word );
                    negativeCount = negativeCount - 1;
                }
                else if(isInNeutral(word) != -1){
                    System.out.println("neutral word found " + word );
                    neutralCount = neutralCount + 0;
                }
                else{
                    System.out.println("Word not found");
                    continue;
                }
            }

        netValue = positiveCount + negativeCount + neutralCount;
        System.out.println(netValue);
        return netValue;
    }

    public void sentimentAnalysis(){
        System.out.println("THis is the new pure");
        System.out.println(pure);
        for (Parse.pureReview entry:pure) {
            calculateReviewRating(entry);
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