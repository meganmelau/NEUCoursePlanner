import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class post {
    public static void main(String[] args) throws Exception {
        String URLString = "https://challenge.sandboxneu.com/s/PMRGIYLUMERDU6ZCMVWWC2LMEI5CE3DBOUXG2LTNMVTWC3SAM5WWC2LMFZRW63JCFQRGI5LFEI5DCNJZGUYTGMJRHE4SYITTORQWOZJCHIRFEMKFMFZXSIT5FQRGQYLTNARDUITOOJSE6TDJK53FOT3SG5CVC5K2GNHVSPJCPU======";
        URL object = new URL(URLString);

        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");

        String reply = "{\"plan\":[{\"subject\":\"CS\",\"classId\":1200,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":1800,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":1802,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":2500,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":2501,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":2510,\"prereqs\":{\"type\":\"and\",\"values\":[{\"classId\":2500,\"subject\":\"CS\"}]}},{\"subject\":\"CS\",\"classId\":2511,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":2800,\"prereqs\":{\"type\":\"and\",\"values\":[{\"type\":\"or\",\"values\":[{\"classId\":1800,\"subject\":\"CS\"},{\"classId\":1365,\"subject\":\"MATH\"}]},{\"classId\":2500,\"subject\":\"CS\"}]}},{\"subject\":\"CS\",\"classId\":2801,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"MATH\",\"classId\":1341,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"MATH\",\"classId\":1342,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"MATH\",\"classId\":3081,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":1342,\"subject\":\"MATH\"},{\"classId\":1252,\"subject\":\"MATH\"},{\"classId\":1242,\"subject\":\"MATH\"}]}},{\"subject\":\"EECE\",\"classId\":2160,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":1111,\"subject\":\"GE\"},{\"classId\":1502,\"subject\":\"GE\"},{\"classId\":3500,\"subject\":\"CS\"}]}},{\"subject\":\"PHYS\",\"classId\":1151,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":1241,\"subject\":\"MATH\"},{\"classId\":1251,\"subject\":\"MATH\"},{\"classId\":1340,\"subject\":\"MATH\"},{\"classId\":1341,\"subject\":\"MATH\"},{\"classId\":1342,\"subject\":\"MATH\"},{\"classId\":2321,\"subject\":\"MATH\"}]}},{\"subject\":\"PHYS\",\"classId\":1152,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"PHYS\",\"classId\":1153,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"PHYS\",\"classId\":1155,\"prereqs\":{\"type\":\"and\",\"values\":[{\"type\":\"or\",\"values\":[{\"classId\":1151,\"subject\":\"PHYS\"},{\"classId\":1161,\"subject\":\"PHYS\"},{\"classId\":1171,\"subject\":\"PHYS\"}]},{\"type\":\"or\",\"values\":[{\"classId\":1252,\"subject\":\"MATH\"},{\"classId\":1342,\"subject\":\"MATH\"},{\"classId\":2321,\"subject\":\"MATH\"}]}]}},{\"subject\":\"PHYS\",\"classId\":1156,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"PHYS\",\"classId\":1157,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"ENGW\",\"classId\":1111,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":1990,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"HIST\",\"classId\":1130,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"MATH\",\"classId\":2321,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"HONR\",\"classId\":1310,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":3000,\"prereqs\":{\"type\":\"or\",\"values\":[{\"type\":\"and\",\"values\":[{\"classId\":2510,\"subject\":\"CS\"},{\"classId\":1800,\"subject\":\"CS\"}]},{\"classId\":2160,\"subject\":\"EECE\"}]}},{\"subject\":\"CS\",\"classId\":3500,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":2510,\"subject\":\"CS\"},{\"classId\":2560,\"subject\":\"EECE\"}]}},{\"subject\":\"CS\",\"classId\":3650,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":2510,\"subject\":\"CS\"},{\"classId\":2560,\"subject\":\"EECE\"}]}},{\"subject\":\"CS\",\"classId\":3800,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":2510,\"subject\":\"CS\"},{\"classId\":2160,\"subject\":\"EECE\"}]}},{\"subject\":\"MATH\",\"classId\":2331,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":1342,\"subject\":\"MATH\"},{\"classId\":1242,\"subject\":\"MATH\"},{\"classId\":1252,\"subject\":\"MATH\"},{\"classId\":1800,\"subject\":\"CS\"}]}},{\"subject\":\"PHIL\",\"classId\":1145,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":1210,\"prereqs\":{\"type\":\"and\",\"values\":[{\"classId\":2510,\"subject\":\"CS\"}]}},{\"subject\":\"CS\",\"classId\":3700,\"prereqs\":{\"type\":\"and\",\"values\":[{\"classId\":2510,\"subject\":\"CS\"}]}},{\"subject\":\"CS\",\"classId\":4400,\"prereqs\":{\"type\":\"and\",\"values\":[{\"classId\":3500,\"subject\":\"CS\"},{\"type\":\"or\",\"values\":[{\"classId\":3000,\"subject\":\"CS\"}]}]}},{\"subject\":\"CS\",\"classId\":4500,\"prereqs\":{\"type\":\"and\",\"values\":[{\"classId\":3500,\"subject\":\"CS\"},{\"type\":\"or\",\"values\":[{\"classId\":1111,\"subject\":\"ENGW\"},{\"classId\":1102,\"subject\":\"ENGW\"}]}]}},{\"subject\":\"THTR\",\"classId\":1170,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":4410,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":4400,\"subject\":\"CS\"},{\"classId\":5400,\"subject\":\"CS\"},{\"classId\":7400,\"subject\":\"CS\"}]}},{\"subject\":\"ENGW\",\"classId\":3315,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":1111,\"subject\":\"ENGW\"},{\"classId\":1102,\"subject\":\"ENGW\"}]}},{\"subject\":\"CS\",\"classId\":3001,\"prereqs\":{\"type\":\"and\",\"values\":[]}}]}";
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(reply);
        wr.flush();

        //display what returns the POST request
        StringBuilder sb = new StringBuilder();
        int HttpResult = con.getResponseCode();
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            System.out.println(sb.toString());
        } else {
            System.out.println(con.getResponseMessage() + con.getResponseCode());
        }
    }
}