import java.util.ArrayList;
import java.util.Arrays;

public class kpt_project_restaurant {
    ArrayList<ReadCSV.Reviews> data1;
    ArrayList<Parse.pureReview> pure = new ArrayList<Parse.pureReview>();
    String[] positive = new String[0];
    String[] negative = new String[0];
    String[] neutral = new String[0];
    ArrayList<String> restID = new ArrayList<>();
    ArrayList<ArrayList<Integer>> reviewRate = new ArrayList<ArrayList<Integer>>();
    public void readReviews(){
        ReadCSV read1 = new ReadCSV();
        data1 = read1.allTask();
        read1.printreviews(data1);
    }

    public void stemReviews(ArrayList<ReadCSV.Reviews> data){
//        ArrayList<ReadCSV.Reviews> data1 = new ArrayList<>();
        Parse p1 = new Parse();
        ReadCSV r1 = new ReadCSV();
        String filename = "stopwords.txt";
        String delimiter = "[ '.,&#?!:;$%+()\\-\\/*\"]+";
//        data1 = r1.allTask();
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


//    public class analysis{
//        String restaurantID;
//        ArrayList<Integer> ratings;
//
//        public void addRestaurant(String rid, Integer individualRating){
//            restaurantID = rid;
//            ratings.set(0,individualRating);
//        }
//
//        public void addRating(Integer individualRating){
//            ratings.add(individualRating);
//        }
//    }

    public void sentimentAnalysis() {
        ArrayList<Integer> rate;
        System.out.println(pure);
        // ArrayList<analysis> list1 = new ArrayList<analysis>();
        for (int i = 0; i < pure.size(); i++) {
            String string = pure.get(i).restaurantID;
            int value = calculateReviewRating(pure.get(i));
            if (!restID.contains(string)) {
                restID.add(string);
                rate = new ArrayList<Integer>();
                rate.add(value);
                reviewRate.add(rate);
            } else {
                int index = restID.indexOf(string);
                rate = reviewRate.get(index);
                rate.add(value);
                reviewRate.set(index, rate);
            }

    }
        String result = "";
        for (int i = 0; i< reviewRate.size(); i++) {
            result += String.format("%-15s", restID.get(i));
            rate = reviewRate.get(i);
            for(int j = 0 ; j < rate.size(); j++)
            {

                result += rate.get(j) + "\t";

            }
            result += "\n";

        }
        System.out.println(restID.size());
        System.out.println(result);


//        for (Parse.pureReview entry:pure) {
//            int value;
//            value = calculateReviewRating(entry);
//            if(value == 0 ){
//                System.out.println("Neutral Review");
//            }
//            else if(value > 0){
//                System.out.println("Positive Review");
//            }
//            else {
//                System.out.println("Negative Review");
//            }
//        }
//        System.out.println(list1);
    }

    public void compute(){
            ArrayList<Integer> Average = new ArrayList<Integer>();
            int count = 0;
        for (int i =0; i< restID.size(); i++) {
            ArrayList<Integer> rate =  reviewRate.get(i);
            int sum = 0;
            int value = 0;
            for (Integer rating: rate) {
                count = count + 1;
                if(rating > 0){
                    value = 1;
                }
                else if(rating < 0){
                    value = -1;
                }
                else{
                    value = 0;
                }

                sum = sum + value;
            }
            Average.add(i, sum);
        }
        for (int i = 0; i < restID.size(); i++) {
            System.out.println(restID.get(i) + "\t" + Average.get(i));
        }
        System.out.println("Total Review Count is = " + count);
    }

    public static void main(String[] args) {
        kpt_project_restaurant p1 = new kpt_project_restaurant();
        p1.readReviews();
        System.out.println(p1.data1.size());
        p1.stemReviews(p1.data1);
        p1.readWords();
        p1.sentimentAnalysis();
        p1.compute();
    }
}