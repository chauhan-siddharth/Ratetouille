import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class read_analysis_words {

//    public  String readFile(String fileName) {
//        String Data = "";
//        try {
//            Path path = Paths.get(fileName);
//            System.out.println(path);
//            Data = new String(Files.readAllBytes(path));
//            Data = Data.toLowerCase();
//        } catch(IOException error){
//            System.out.println(error);
//        }
//        return Data;
//    }

    public String[] tokenize(String delimiter, String fileName){
        String Data = "";
        try {
            Path path = Paths.get(fileName);
            System.out.println(path);
            Data = new String(Files.readAllBytes(path));
            Data = Data.toLowerCase();
        } catch(IOException error){
            System.out.println(error);
        }

        String[] tokens = null;

        tokens = Data.split(delimiter);
        String[] stemmered = new String[tokens.length];
        int i = 0;
        //stemming
        Stemmer st = new Stemmer();
        for(String token:tokens) {
            st.add(token.toCharArray(), token.length());
            st.stem();
            stemmered[i] = st.toString();
            st = new Stemmer();
            i++;
        }
        return stemmered;
    }

    public static void main(String[] args){
        String delimiter = ",";
        read_analysis_words r1 = new read_analysis_words();
       String[] positive = r1.tokenize(delimiter, "positive.txt");
        System.out.println(positive);
        String[] negative = r1.tokenize(delimiter, "negative.txt");
        System.out.println(negative);
        String[] neutral = r1.tokenize(delimiter, "neutral.txt");
        System.out.println(neutral);
    }
}
