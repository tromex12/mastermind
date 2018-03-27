package mastermind2;

//import org.json.JSONException;
//import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javafx.util.Pair;
import org.json.JSONObject;
/* Using standard java URL, HTTP libraries and POST requests*/
public class JSONcommunicator {

        private String gameID;
	// http://localhost:8080/RESTfulExample/json/product/post
	public String startGame() {
          String toReturn = null;
	  try {
		URL url = new URL("https://ikariera.etnetera.cz/veletrhy/start");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		String input = "{\"nickname\": \"Tromex\",\"email\": \"veletrh@mail.ru\",\"slots\": 100}";

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();


		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
                StringBuilder builder = new StringBuilder();
		while ((output = br.readLine()) != null) {
                        builder.append(output);
		}
                JSONObject gameID = new JSONObject(builder.toString());
                toReturn = gameID.getString("gameId");
		conn.disconnect();
              } 
                catch (MalformedURLException e) {
                      e.printStackTrace();
                } 
                catch (IOException e) {
                      e.printStackTrace();
                }
                gameID=toReturn;
                return toReturn;
        }
        public Pair<Integer,Integer> send(List<Integer> list){
            Pair<Integer,Integer> toReturn = null;
             try {
		URL url = new URL("https://ikariera.etnetera.cz/veletrhy/guess");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		String input = "{\"gameId\": "+"\""+gameID+"\"," + "\"guess\": [";
                for ( int i = 0 ; i < list.size(); i ++ ){
                    input += list.get(i);
                    if ( i!= list.size()-1 )
                     input += ", ";
                }
                input+="] }";
                // System.out.println(input);
		OutputStream os = conn.getOutputStream();
                os.flush();
		os.write(input.getBytes());
		os.flush();


		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		//System.out.println("Output from Server .... \n");
                StringBuilder builder = new StringBuilder();
		while ((output = br.readLine()) != null) {
                        builder.append(output);
		}
                JSONObject gameResponse = new JSONObject(builder.toString());
                JSONObject eval = gameResponse.getJSONObject("evaluation");
                int black =0;
                int white = 0;
                black = eval.getInt("black");
                white =eval.getInt("white");
                if ( black == 100 ){
                    System.out.println("GameCnt = "+ gameResponse.getInt("guessCount"));
                }
                toReturn = new Pair(black,white);
		conn.disconnect();
            } 
                catch (MalformedURLException e) {
		e.printStackTrace();
                } 
                catch (IOException e) {
		e.printStackTrace();
            }
             return toReturn;
        }

}