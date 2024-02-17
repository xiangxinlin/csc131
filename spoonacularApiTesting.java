import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class spoonacularApiTesting {
    
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your query: ");
        String findQuery = scan.nextLine();

        try {
            String apiKey = "41c2b73f2580458ea8e845483f07dbee";
            String urlString = "https://api.spoonacular.com/recipes/complexSearch?query=" + findQuery + "&apiKey=" + apiKey;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }else{
                StringBuilder infoString = new StringBuilder();
                BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;
                while ((line = read.readLine()) != null){
                    infoString.append(line);
                }
                read.close();

                System.out.println(infoString.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            scan.close();
        }
    }
}

