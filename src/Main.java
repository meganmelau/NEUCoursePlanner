import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        URLConnectionReader connectionReader = new URLConnectionReader();
//        String URLString = "https://challenge.sandboxneu.com/s/PMRGIYLUMERDU6ZCMVWWC2LMEI5CE3DBOUXG2LTNMVTWC3SAM5WWC2LMFZRW63JCFQRGI5LFEI5DCNJZGUYTGMJRHE4SYITTORQWOZJCHIRFEMKFMFZXSIT5FQRGQYLTNARDUITOOJSE6TDJK53FOT3SG5CVC5K2GNHVSPJCPU======";
//        String jsonString = connectionReader.connectionReader(URLString);


        String jsonString = "{\"courses\":[{\"subject\":\"PHYS\",\"classId\":1151,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":1241,\"subject\":\"MATH\"},{\"classId\":1251,\"subject\":\"MATH\"},{\"classId\":1340,\"subject\":\"MATH\"},{\"classId\":1341,\"subject\":\"MATH\"},{\"classId\":1342,\"subject\":\"MATH\"},{\"classId\":2321,\"subject\":\"MATH\"}]}}\n" +
                "]}";
//        String jsonString = "{\"courses\":[{\"subject\":\"ENGW\",\"classId\":1111,\"prereqs\":{\"type\":\"and\",\"values\":[]}}\n" +
//                "]}";
//        String jsonString = "{\n" + "\"courses\": [\n" + "{\n" + "\"subject\": \"CS\",\n" + "\"classId\": 2500,\n" + "\"prereqs\": {\n" +
//                "\"type\": \"and\",\n" + "\"values\": []\n" + "}\n" + "},\n" + "{\n" + "\"subject\": \"CS\",\n" +
//                "\"classId\": 1800,\n" + "\"prereqs\": {\n" + "\"type\": \"and\",\n" + "\"values\": []\n" + "}\n" + "},\n" + "{\n" +
//                "\"subject\": \"CS\",\n" + "\"classId\": 3500,\n" + "\"prereqs\": {\n" + "\"type\": \"or\",\n" +
//                "\"values\": [\n" + "{\n" + "\"subject\": \"CS\",\n" + "\"classId\": 2510\n" + "},\n" + "{\n" + "\"subject\": \"EECE\",\n" + "\"classId\": 2560\n" + "}\n" + "]\n" + "}\n" + "},\n" +
//                "{\n" + "\"subject\": \"CS\",\n" + "\"classId\": 2510,\n" + "\"prereqs\": {\n" + "\"type\": \"and\",\n" + "\"values\": [\n" + "{\n" + "\"subject\": \"CS\",\n" +
//                "\"classId\": 2500\n" + "}\n" + "]\n" + "}\n" + "}\n" + "]\n" + "}";

        Map<Long, Course> courseMap = connectionReader.jsonParser(jsonString);

        CoursePlanSender sender = new CoursePlanSender();
//        create plan
//        Map<Long, Course> plan  = sender.createPlan(courseMap);

        //convert plan to json

        //post json back to NU Sandbox
//
        for (Long name: courseMap.keySet()){
            String key = name.toString();
            Course value = courseMap.get(name);
            System.out.println("course " + value.toString());
        }

//        for (Long name: plan.keySet()){
//            String key = name.toString();
//            Course value = plan.get(name);
////            System.out.println(key + " ====plan======" + value.toString());
//        }
    }
}
