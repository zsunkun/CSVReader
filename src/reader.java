import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.csvreader.CsvReader;

public class reader {

    private static JSONObject obj = new JSONObject();

    /**
     * @param args
     */
    public static void main(String[] args) {
        File csv = new File("src/query_result.csv");
        BufferedReader reader = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter("out.txt");
            reader = new BufferedReader(new FileReader(csv));
            String line = "";
            String[] record = new String[] {};
            String token = "";
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            CsvReader creader = new CsvReader(reader, ',');
            long swt;
            long srt;
            creader.readRecord();
            while (creader.readRecord()) {
                record = creader.getValues();
                line = record[32];
                token = record[4];
                System.out.println(line);
                System.out.println(token);
                if (line.equals("papsrc")) {
                    continue;
                }
                jsonObject = new JSONObject(line);
                jsonArray = jsonObject.getJSONArray("stats");
                if (jsonArray.length() == 0) {
                    continue;
                }
                swt = jsonObject.getLong("swt");
                srt = jsonObject.getLong("srt");
                output(jsonArray, swt, srt, fw, token);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e1) {
            }
        }
    }

    private static void output(JSONArray jsonArray, long swt, long srt, FileWriter fw, String token) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                obj = jsonArray.getJSONObject(i);
                fw.write(String.valueOf(obj.get("wat")) + ";");
                fw.write(String.valueOf(obj.get("wt")) + ";");
                fw.write(String.valueOf(obj.get("ct")) + ";");
                fw.write(String.valueOf(obj.get("wst")) + ";");
                fw.write(String.valueOf(obj.get("bt")) + ";");
                fw.write(String.valueOf(obj.get("up")) + ";");
                fw.write(String.valueOf(obj.get("wot")) + ";");
                fw.write(String.valueOf(obj.get("cp")) + ";");
                fw.write(String.valueOf(obj.get("wft")) + ";");
                fw.write(String.valueOf(obj.get("gt")) + ";");
                fw.write(String.valueOf(obj.get("ft")) + ";");
                fw.write(String.valueOf(obj.get("st")) + ";");
                fw.write(String.valueOf(obj.get("tbs")) + ";");
                fw.write(String.valueOf(obj.get("tbr")) + ";");
                fw.write(String.valueOf(obj.get("pk")) + ";");
                fw.write(String.valueOf(swt)+ ";");
                fw.write(String.valueOf(srt)+ ";");
                fw.write(token + "\n");
            } catch (JSONException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
