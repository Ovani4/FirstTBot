import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transliter {

    static public Map parseJson() {

        JSONParser jsonParser = new JSONParser();
        Map<String, String> abc = new HashMap<>();
        try (FileReader fr = new FileReader("src/main/resources/abc.json")) {

            JSONObject litterJsonObject = (JSONObject) jsonParser.parse(fr);
            JSONArray abcJsonArray = (JSONArray) litterJsonObject.get("abc");

            for (Object obj : abcJsonArray) {
                JSONObject literJsonObject = (JSONObject) obj;
                abc.put(literJsonObject.get("key").toString(), literJsonObject.get("value").toString());
            }

        } catch (Exception e) {
            System.out.println("error parse " + e.toString());
        }

        return abc;
    }

    public String transliteMessage(String string) {

        List list = new ArrayList();
        Map<String, String> map = (HashMap) Transliter.parseJson();
        for (int i = 0; i < string.length(); i++) {
            for (Map.Entry<String, String> entry :
                    map.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(String.valueOf(string.charAt(i)))) {
                    list.add(entry.getValue());
                }
            }
        }
        return list.toString();
    }
}
