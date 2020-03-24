import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Ratetouille {
    // Global Variable deceleration.
    ArrayList<Reviews> data1;
    ArrayList<Parse.pureReview> pure = new ArrayList<Parse.pureReview>();
    String[] positive = new String[0];
    String[] negative = new String[0];
    String[] neutral = new String[0];
    ArrayList<String> restID = new ArrayList<>();
    ArrayList<ArrayList<Integer>> reviewRate = new ArrayList<ArrayList<Integer>>();
    // Global Variable deceleration ends.

    /**
     * readReview class used to create a review object read1
     * further read1.allTask will give us the reviews stored
     * in the csv file.
     * read1.printreviews is used to print the reviews;
     */
    public void readReviews() {
        // Local Variable deceleration.
        Reviews read1 = new Reviews();
        // Local Variable deceleration ends.
        data1 = read1.allTask();
        read1.printReviews(data1);
    }// end readReviews

    /**
     * Reviews class which contains two strings first
     * contains the restaurantID of the type "R0001,
     * R002, R0003,......, R0211 and the second the reviews.
     * The object contains data in the form R0001 - Review1,
     * R0001 - Review2, R0001 - Review3,...... R0001 - ReviewN,
     * R0002 - Review1........... R0211 - ReviewN.
     */
    public class Reviews {
        // Object deceleration.
        String restaurantID;
        String review;
        // Object deceleration ends.

        /**
         * allTask method in the Reviews class reads the CSV file
         * new.csv that contains the reviews in a comma delimited
         * format and then storing it in an ArrayList "data".
         * @return ArrayList of type Reviews
         */
        public ArrayList<Reviews> allTask() {
            // Local Variable deceleration.
            CSVReader reader = null;
            // Local Variable deceleration ends.
            ArrayList<Reviews> data = new ArrayList<>();
            try {
                // Local Variable deceleration.
                //parsing a CSV file into CSVReader class constructor
                reader = new CSVReader(new FileReader("new.csv"));
                String[] nextLine;
                int i = 0;
                // Local Variable deceleration ends.
                //reads one line at a time
                while ((nextLine = reader.readNext()) != null) {
                    Reviews re1 = new Reviews();
                    re1.setReviews(nextLine[0], nextLine[1]);
                    data.add(re1);
                    i++;
                }// end while
                System.out.println("Count of reviews = " + i);
            }// end try
            catch (Exception e) {
                e.printStackTrace();
            }// end catch
            return data;
        }// end allTask

        /**
         * setReviews method is used to store the read rid and
         * review which are passed into the method as arguments.
         * @param rid        - Restaurant ID
         * @param restReview - Restaurant Review.
         */
        public void setReviews(String rid, String restReview) {
            restaurantID = rid;
            review = restReview;
        }// end setReviews

        /**
         * Method call used to print all the restaurants ID and
         * their corresponding reviews.
         * @param data - ArrayList of the type Reviews which contains
         *             all the read reviews.
         */
        public void printReviews(ArrayList<Reviews> data) {
            // Local Variable deceleration.
            String restIdString = new String();
            // Local Variable deceleration ends.
            for (Reviews r : data) {
                restIdString = "" + r.restaurantID + " : ";
                restIdString += r.review + " ";
                System.out.println(restIdString);
            }// end for
        }// end printReviews
    }// end Reviews

    /**
     * Method stemReviews is a go to method for all the tasks that need to be
     * performed in order to preprocess the reviews.
     * @param data -     Array list of the object type Reviews.
     */
    public void stemReviews(ArrayList<Reviews> data) {
        // Local Variable deceleration.
        Parse p1 = new Parse();
        Reviews r1 = new Reviews();
        String filename = "stopwords.txt";
        String delimiter = "[ '.,&#?!:;$%+()\\-\\/*\"]+";
        // Local Variable deceleration ends.
        p1.getStopWords(filename);
        pure = p1.tokenize(delimiter, data1);
        p1.printPure(pure);
        System.out.println("Pure ka size = " + pure.size());
    }// end stemReviews

    /**
     * Class Parse used to preprocess, tokenize and stem the
     * read reviews.
     */
    public class Parse {
        // Global-Parse Variable deceleration.
        public String[] words = new String[0];
        // Global-Parse Variable deceleration ends.

        /**
         * Class pureReview which is created to store a
         * preprocessed version of the reviews with their
         * restaurant IDs. The String restaurantID contains
         * the ID and the ArrayList reviewArray contains the
         * Array that has words that are free of stop words
         * and is tokenized.
         */
        public class pureReview {
            // Object deceleration.
            String restaurantID;
            ArrayList<String> reviewArray;
            // Object deceleration ends.

            /**
             * Method setReviewArray is used to initialize the
             * values of an instance of the pureReview object
             * into the array.
             * @param rid -         Restaurant ID
             * @param restReview -  Original Reviews read
             */
            public void setReviewArray(String rid, ArrayList<String> restReview) {
                restaurantID = rid;
                reviewArray = restReview;
            }// setReviewArray ends.
        }//pureReview ends.

        /**
         * Method used to read the stop words from the txt
         * file and store it into a String[] words
         * @param fileName -    reads the name of file
         *                      containing the list of stop
         *                      words.
         */
        public void getStopWords(String fileName) {
            // Local Variable deceleration.
            String sw = new String();
            // Local Variable deceleration ends.
            try {
                String lines = "";
                Path path = Paths.get(fileName);
                lines = new String(Files.readAllBytes(path));
                lines = lines.toLowerCase();
                words = lines.split("\\n");
                Arrays.sort(words);
                for (int i = 0; i < words.length; i++) {
                    sw += words[i] + " ";
                }// for ends.
                System.out.println("Stopwords => " + sw);
            }// try ends.
            catch (Exception e) {
                e.printStackTrace();
            }// catch ends.
        }// getStopWords ends.

        /**
         *  tokenize function is used to split the sentences into word tokens
         *  and the these words are stemmed and stored in an ArrayList of the
         *  pureReview object type.
         * @param delimiter -    String containing the symbols used for tokenizing the reviews
         * @param data1 -        is an ArrayList of Strings containing the object type Reviews
         * @return -             ArrayList of String stemmered - containing the stemmed document
         *                       using the Porter's Stemmer Algorithm
         */
        public ArrayList<pureReview> tokenize(String delimiter, ArrayList<Reviews> data1) {
            // Local Variable deceleration.
            ArrayList<pureReview> stemmedReview = new ArrayList<pureReview>();
            // Local Variable deceleration ends.
            for (int i = 0; i < data1.size(); i++) {
                ArrayList<String> pureTokens = new ArrayList<String>();
                String[] tokens = new String[0];
                tokens = data1.get(i).review.toLowerCase().split(delimiter);
                for (String token : tokens) {
                    if (searchStopWord(token) == -1) {
                        pureTokens.add(token);
                    }// if ends.
                }// for ends.
                //stemming
                Stemmer st = new Stemmer();
                ArrayList<String> stemmed = new ArrayList<>();
                for (String token : pureTokens) {
                    st.add(token.toCharArray(), token.length());
                    st.stem();
                    stemmed.add(st.toString());
                    st = new Stemmer();
                }// for ends.
                pureReview r1 = new pureReview();
                r1.setReviewArray(data1.get(i).restaurantID, stemmed);
                stemmedReview.add(r1);
            }// for ends
//         printPure(stemmedReview);
            return stemmedReview;
        }// tokenize ends.

        /**
         *  In the searchStopWord method the global variable the String is
         *  searched against words containing the stopwords using Binary Search.
         * @param key -     of string datatype
         * @return int -    returns -1 is the key is not found in the stop word list
         */
        public int searchStopWord(String key) {
            // Local Variable deceleration.
            int lo = 0;
            int hi = words.length - 1;
            // Local Variable deceleration ends.
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int result = key.compareTo(words[mid]);
                if (result < 0) hi = mid - 1;
                else if (result > 0) lo = mid + 1;
                else return mid;
            }// while ends.
            return -1;
        }// searchStopWord ends.

        /**
         * printPure function is used to print the ArrayList of the
         * object type pureReview.
         * @param pure -    An ArrayList of type pureReview which
         *                  contains Restaurant IDs and the tokenized
         *                  stemmed reviews.
         */
        public void printPure(ArrayList<pureReview> pure) {
            for (pureReview r : pure) {
                System.out.println(r.restaurantID + " ===>>> " + r.reviewArray);
            }// for ends.
        }// printPure ends.
    }// Parse ends.

    /**
     * readWords method is the method which combines function call for
     * tokenizeWords for the 3 files containing positive, negative and
     * the neutral words and storing them in 3 different arrays.
     */
    public void readWords() {
        // Local Variable deceleration.
        String delimiter = ",";
        // Local Variable deceleration ends.
        positive = tokenizeWords(delimiter, "positive.txt");
        Arrays.sort(positive);
        for (String word : positive) {
            System.out.print(word + " ");
        }// for ends.
        negative = tokenizeWords(delimiter, "negative.txt");
        Arrays.sort(negative);
        for (String word : negative) {
            System.out.print(word + " ");
        }// for ends.
        neutral = tokenizeWords(delimiter, "neutral.txt");
        Arrays.sort(neutral);
        for (String word : neutral) {
            System.out.print(word + " ");
        }// for ends.
    }// readWords ends.

    /**
     * tokenizeWords is a method that stems the words reads from
     * the respective files.
     * @param delimiter -   String containing the symbols used for tokenizing the word list
     * @param fileName  -   filename to read the words from
     * @return String[] -   Array Storing the stemmed words
     */
    public String[] tokenizeWords(String delimiter, String fileName) {
        // Local Variable deceleration.
        String Data = "";
        // Local Variable deceleration ends.
        try {
            Path path = Paths.get(fileName);
            System.out.println(path);
            Data = new String(Files.readAllBytes(path));
            Data = Data.toLowerCase();
        }// try ends.
        catch (IOException error) {
            System.out.println(error);
        }// catch ends.
        String[] tokens = null;
        tokens = Data.split(delimiter);
        String[] stemmered = new String[tokens.length];
        int i = 0;
        //stemming
        Stemmer st = new Stemmer();
        for (String token : tokens) {
            st.add(token.toCharArray(), token.length());
            st.stem();
            stemmered[i] = st.toString();
            st = new Stemmer();
            i++;
        }// for ends
        return stemmered;
    }// tokenizeWords ends.

    /**
     * sentimentAnalysis method runs the calculateReviewRating
     * for all the reviews and creates a normalized data structure
     * storing the ratings of all the reviews for the restaurant by
     * their IDs.
     */
    public void sentimentAnalysis() {
        // Local Variable deceleration.
        ArrayList<Integer> rate;
        // Local Variable deceleration ends.
        System.out.println(pure);
        for (int i = 0; i < pure.size(); i++) {
            String string = pure.get(i).restaurantID;
            int value = calculateReviewRating(pure.get(i));
            if (!restID.contains(string)) {
                restID.add(string);
                rate = new ArrayList<Integer>();
                rate.add(value);
                reviewRate.add(rate);
            }//if ends.
            else {
                int index = restID.indexOf(string);
                rate = reviewRate.get(index);
                rate.add(value);
                reviewRate.set(index, rate);
            }// else ends.
        }// for ends.
        String result = "";
        for (int i = 0; i< reviewRate.size(); i++) {
            result += String.format("%-15s", restID.get(i));
            rate = reviewRate.get(i);
            for(int j = 0 ; j < rate.size(); j++)
            {
                result += rate.get(j) + "\t";
            }// for ends.
            result += "\n";
        }// for ends.
        System.out.println(restID.size());
        System.out.println(result);
    }// sentimentAnalysis ends.

    /**
     * method calculateReviewRating checks if each term in
     * a review exists in either one of the lists and gives
     * them an integer value of 1 for positive, 0 for neutral
     * and -1 for negative.
     * @param entry -   holds a single instance of the pureReview
     * @return
     */
    public int calculateReviewRating (Parse.pureReview entry){
        // Local Variable deceleration.
        int positiveCount = 0;
        int negativeCount = 0;
        int neutralCount = 0;
        int netValue = 0;
        // Local Variable deceleration ends.
        for (String word:entry.reviewArray) {
            if(isInPositive(word) != -1){
                positiveCount = positiveCount + 1;
            }// if ends.
            else if(isInNegative(word) != -1){
                negativeCount = negativeCount - 1;
            }// else if ends.
            else if(isInNeutral(word) != -1){
                neutralCount = neutralCount + 0;
            }// else if ends.
            else{
                continue;
            }// else ends.
        }// for ends.
        netValue = positiveCount + negativeCount + neutralCount;
        return netValue;
    }// calculateReviewRatings ends.

    /**
     *  isInPositive Method checks if the given key word exists in the
     *  positive list
     * @param key -     The term to be searched
     * @return  -       return -1 when the word is not in the list
     */
    public int isInPositive(String key) {
        // Local Variable deceleration.
        int lo = 0;
        int hi = positive.length - 1;
        // Local Variable deceleration ends.
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int result = key.compareTo(positive[mid]);
            if (result < 0) {
                hi = mid - 1;
            }// if ends.
            else if (result > 0) {
                lo = mid + 1;
            }// else if ends.
            else {
                return mid;
            }// else ends.
        }// while ends.
        return -1;
    }//isInPositive ends.

    /**
     *  isInNegative Method checks if the given key word exists in the
     *  negative list
     * @param key -     The term to be searched
     * @return  -       return -1 when the word is not in the list
     */
    public int isInNegative(String key) {
        // Local Variable deceleration.
        int lo = 0;
        int hi = negative.length - 1;
        // Local Variable deceleration ends.
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int result = key.compareTo(negative[mid]);
            if (result < 0) {
                hi = mid - 1;
            }//if ends.
            else if (result > 0) {
                lo = mid + 1;
            }// else if ends.
            else {
                return mid;
            }// else ends.
        }// while ends.
        return -1;
    }// isInNegative ends.

    /**
     *  isInNeutral Method checks if the given key word exists in the
     *  neutral list
     * @param key -     The term to be searched
     * @return  -       return -1 when the word is not in the list
     */
    public int isInNeutral(String key) {
        // Local Variable deceleration.
        int lo = 0;
        int hi = neutral.length - 1;
        // Local Variable deceleration ends.
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int result = key.compareTo(neutral[mid]);
            if (result < 0) {
                hi = mid - 1;
            }// if ends.
            else if (result > 0) {
                lo = mid + 1;
            }// else if ends.
            else {
                return mid;
            }// else ends.
        }// while ends.
        return -1;
    }// isInNeutral ends.

    /**
     * compute method checks the sum of the sentiment analysed
     * ratings for each restaurant and checks if the overall
     * rating for the restaurant rating would be positive,
     * negative or neutral.
     */
    public void compute(){
        // Local Variable deceleration.
        ArrayList<Integer> Average = new ArrayList<Integer>();
        int count = 0;
        // Local Variable deceleration ends.
        for (int i =0; i< restID.size(); i++) {
            ArrayList<Integer> rate =  reviewRate.get(i);
            int sum = 0;
            int value = 0;
            for (Integer rating: rate) {
                count = count + 1;
                if(rating > 0){
                    value = 1;
                }// if ends.
                else if(rating < 0){
                    value = -1;
                }// else if ends.
                else{
                    value = 0;
                }// else ends.
                sum = sum + value;
            }// for ends.
            Average.add(i, sum);
        }// for ends.
        for (int i = 0; i < restID.size(); i++) {
            System.out.println(restID.get(i) + "\t" + Average.get(i));
        }// for ends.
        System.out.println("Total Review Count is = " + count);
    }// compute ends.

    //Main function of the program
    public static void main(String[] args) {
        Ratetouille p1 = new Ratetouille();
        p1.readReviews();
        System.out.println(p1.data1.size());
        p1.stemReviews(p1.data1);
        p1.readWords();
        p1.sentimentAnalysis();
        p1.compute();
    }// end main
}// end Ratetouille