import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        URLConnectionReader connectionReader = new URLConnectionReader();
//        String URLString = "https://challenge.sandboxneu.com/s/PMRGIYLUMERDU6ZCMVWWC2LMEI5CE3DBOUXG2LTNMVTWC3SAM5WWC2LMFZRW63JCFQRGI5LFEI5DCNJZGUYTGMJRHE4SYITTORQWOZJCHIRFEMKFMFZXSIT5FQRGQYLTNARDUITOOJSE6TDJK53FOT3SG5CVC5K2GNHVSPJCPU======";
//        String jsonString = connectionReader.connectionReader(URLString);

//        String jsonString = "{\"courses\":[{\"subject\":\"CS\",\"classId\":4500,\"prereqs\":{\"type\":\"and\",\"values\":[{\"classId\":3500,\"subject\":\"CS\"},{\"type\":\"or\",\"values\":[{\"classId\":1111,\"subject\":\"ENGW\"},{\"classId\":1102,\"subject\":\"ENGW\"}]}]}}\n" + "]}";
//        String jsonString = "{\"courses\":[{\"subject\":\"PHYS\",\"classId\":1151,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":1241,\"subject\":\"MATH\"},{\"classId\":1251,\"subject\":\"MATH\"},{\"classId\":1340,\"subject\":\"MATH\"},{\"classId\":1341,\"subject\":\"MATH\"},{\"classId\":1342,\"subject\":\"MATH\"},{\"classId\":2321,\"subject\":\"MATH\"}]}}\n" + "]}";
//        String jsonString = "{\"courses\":[{\"subject\":\"CS\",\"classId\":4410,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":4400,\"subject\":\"CS\"},{\"classId\":5400,\"subject\":\"CS\"},{\"classId\":7400,\"subject\":\"CS\"}]}}\n" + "]}";
//        String jsonString = "{\n" + "\"courses\": [\n" + "{\n" + "\"subject\": \"CS\",\n" + "\"classId\": 2500,\n" + "\"prereqs\": {\n" +
//                "\"type\": \"and\",\n" + "\"values\": []\n" + "}\n" + "},\n" + "{\n" + "\"subject\": \"CS\",\n" +
//                "\"classId\": 1800,\n" + "\"prereqs\": {\n" + "\"type\": \"and\",\n" + "\"values\": []\n" + "}\n" + "},\n" + "{\n" +
//                "\"subject\": \"CS\",\n" + "\"classId\": 3500,\n" + "\"prereqs\": {\n" + "\"type\": \"or\",\n" +
//                "\"values\": [\n" + "{\n" + "\"subject\": \"CS\",\n" + "\"classId\": 2510\n" + "},\n" + "{\n" + "\"subject\": \"EECE\",\n" + "\"classId\": 2560\n" + "}\n" + "]\n" + "}\n" + "},\n" +
//                "{\n" + "\"subject\": \"CS\",\n" + "\"classId\": 2510,\n" + "\"prereqs\": {\n" + "\"type\": \"and\",\n" + "\"values\": [\n" + "{\n" + "\"subject\": \"CS\",\n" +
//                "\"classId\": 2500\n" + "}\n" + "]\n" + "}\n" + "}\n" + "]\n" + "}";

        String jsonString = "{\n" +
                "\"courses\": [\n" +
                "{\n" +
                "\"subject\": \"CS\",\n" +
                "\"classId\": 3500,\n" +
                "\"prereqs\": {\n" +
                "\"type\": \"or\",\n" +
                "\"values\": [\n" +
                "{\n" +
                "\"subject\": \"CS\",\n" +
                "\"classId\": 2510\n" +
                "},\n" +
                "{\n" +
                "\"subject\": \"EECE\",\n" +
                "\"classId\": 2560\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "}\n" +
                "]\n" +
                "}";
        Map<Long, Course> courseMap = connectionReader.jsonParser(jsonString);

        CoursePlanSender sender = new CoursePlanSender();
//        create plan
        Map<Long, Course> plan  = sender.createPlan(courseMap);

        //convert plan to json
        String body = sender.convertToJSON(plan);
        System.out.println(body);

        //post json back to NU Sandbox
//        sender.
    }
}
