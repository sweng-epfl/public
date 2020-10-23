/**
 * Created by Armin on 4/29/2016.
 */
public class StringHelper {
    public static int countLines(String str){
        String[] lines = str.split("\r\n|\r|\n");
        return  lines.length;
    }
}
