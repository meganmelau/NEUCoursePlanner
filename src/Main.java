import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        URLConnectionReader connectionReader = new URLConnectionReader();
        String URLString = "https://challenge.sandboxneu.com/s/PMRGIYLUMERDU6ZCMVWWC2LMEI5CE3DBOUXG2LTNMVTWC3SAM5WWC2LMFZRW63JCFQRGI5LFEI5DCNJZGUYTGMJRHE4SYITTORQWOZJCHIRFEMKFMFZXSIT5FQRGQYLTNARDUITOOJSE6TDJK53FOT3SG5CVC5K2GNHVSPJCPU======";
        String jsonString = connectionReader.connectionReader(URLString);

        //Read JSON into Map
        Map<Long, Course> courseMap = connectionReader.jsonParser(jsonString);

        CoursePlanSender sender = new CoursePlanSender();
        //create plan
        Map<Long, Course> plan = sender.createPlan(courseMap);

        //convert plan to json
        String body = sender.convertToJSON(plan);
        System.out.println(body);

        //post json back to NU Sandbox
        sender.send(URLString, body);
    }
}
