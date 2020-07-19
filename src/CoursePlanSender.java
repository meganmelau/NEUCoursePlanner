import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class CoursePlanSender {
    public CoursePlanSender() throws Exception {
//
    }


    public Map<Long, Course> createPlan(Map<Long, Course> courseList) {
        Map<Long, Course> plan = new LinkedHashMap<>();
        //First Traversal: Take All Classes without PreReqs
        // Create a Iterator to EntrySet of HashMap
        Iterator<Map.Entry<Long, Course>> entryIt = courseList.entrySet().iterator();
        // Iterate over all the elements
        while (entryIt.hasNext()) {
            Map.Entry<Long, Course> entry = entryIt.next();
            Long key = (Long) entry.getKey();
            Course course = ((Course) entry.getValue());
            // Check if Value associated with Key is 10
            if (course.getPreReq().getPreReqList().size() == 0) {
                plan.put(key, course);
//                entryIt.remove();
            }
        }

        for (Map.Entry mapElement : courseList.entrySet()) {
            Long key = (Long) mapElement.getKey();
            Course course = ((Course) mapElement.getValue());
            PreReq<PreReq> preReq = course.getPreReq();
            String mainType = preReq.getType();
            List<PreReq> preReqList = preReq.getPreReqList();

            if (mainType.equals("and")) {
                boolean takeAllPreReq = true;
                for (PreReq innerPreReq : preReqList) {
                    String innerType = innerPreReq.getType();
                    //empty string = noReq
                    if (innerType.equals("")) {
                        List<Course> noReqCourseList = innerPreReq.getPreReqList();
                        for (Course innerCourse : noReqCourseList) {
                            Long innerKey = innerCourse.getClassId();
                            //if contains one then take the class
                            if (!plan.containsKey(innerKey)) {
                                takeAllPreReq = false;
                            }
                        }
                        if (takeAllPreReq) {
                            plan.put(key, course);
                        }
                    }
                    if (innerType.equals("or")) {
                        List<Course> noReqCourseList = innerPreReq.getPreReqList();
                        for (Course innerCourse : noReqCourseList) {
                            Long innerKey = innerCourse.getClassId();
                            //if contains one then take the class
                            if (plan.containsKey(innerKey)) {
                                plan.put(key, course);
                                break;
                            } else {
                                plan.put(innerKey, innerCourse);
                            }
                        }
                    }
                }
            } else if (mainType.equals("or")) {
                for (PreReq innerPreReq : preReqList) {
                    String innerType = innerPreReq.getType();
                    //empty string = noReq
                    if (innerType.equals("")) {
                        List<Course> noReqCourseList = innerPreReq.getPreReqList();
                        for (Course innerCourse : noReqCourseList) {
                            Long innerKey = innerCourse.getClassId();
                            //if contains one then take the class
                            if (!plan.containsKey(innerKey)) {
                                plan.put(innerKey, innerCourse);
                            }
                        }
                    }
                }
                //checking prereq --> list<prereqg> --> iterate --> course
                //main Course after checking prereq
                plan.put(key, course);
            }

        }
        return plan;
    }


    private Map<Long, Course> putCourseInMap(Course course, PreReq<Course> singlePreReq, Long key) {
        SortedMap<Long, Course> plan = new TreeMap<Long, Course>();
        List<Course> preReqCourses = singlePreReq.getPreReqList();
        for (Course preReqCourse : preReqCourses) {
            Long preReqId = preReqCourse.getClassId();
            if (plan.containsKey(preReqId)) {
                plan.put(key, course);
            }
        }
        return plan;
    }

    public String convertToJSON(Map<Long, Course> plan) {
        StringBuilder jsonString= new StringBuilder();
        jsonString.append("{\n" + "\"plan\": [");
        for (Map.Entry course : plan.entrySet()) {
            Course value = (Course) course.getValue();
            jsonString.append(value.toString());
        }
        int len = jsonString.length();
        char checkComma = jsonString.charAt(len-1);
        if (checkComma == ',') {
            jsonString.deleteCharAt(len-1);
        }
        jsonString.append("\n]\n}");
        return jsonString.toString();
    }

    public void send(String URLString, String body) throws Exception {
        URL object = new URL(URLString);
        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");

//        String reply = "{\"plan\":[{\"subject\":\"CS\",\"classId\":1200,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":1800,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":1802,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":2500,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":2501,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":2510,\"prereqs\":{\"type\":\"and\",\"values\":[{\"classId\":2500,\"subject\":\"CS\"}]}},{\"subject\":\"CS\",\"classId\":2511,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":2800,\"prereqs\":{\"type\":\"and\",\"values\":[{\"type\":\"or\",\"values\":[{\"classId\":1800,\"subject\":\"CS\"},{\"classId\":1365,\"subject\":\"MATH\"}]},{\"classId\":2500,\"subject\":\"CS\"}]}},{\"subject\":\"CS\",\"classId\":2801,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"MATH\",\"classId\":1341,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"MATH\",\"classId\":1342,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"MATH\",\"classId\":3081,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":1342,\"subject\":\"MATH\"},{\"classId\":1252,\"subject\":\"MATH\"},{\"classId\":1242,\"subject\":\"MATH\"}]}},{\"subject\":\"EECE\",\"classId\":2160,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":1111,\"subject\":\"GE\"},{\"classId\":1502,\"subject\":\"GE\"},{\"classId\":3500,\"subject\":\"CS\"}]}},{\"subject\":\"PHYS\",\"classId\":1151,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":1241,\"subject\":\"MATH\"},{\"classId\":1251,\"subject\":\"MATH\"},{\"classId\":1340,\"subject\":\"MATH\"},{\"classId\":1341,\"subject\":\"MATH\"},{\"classId\":1342,\"subject\":\"MATH\"},{\"classId\":2321,\"subject\":\"MATH\"}]}},{\"subject\":\"PHYS\",\"classId\":1152,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"PHYS\",\"classId\":1153,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"PHYS\",\"classId\":1155,\"prereqs\":{\"type\":\"and\",\"values\":[{\"type\":\"or\",\"values\":[{\"classId\":1151,\"subject\":\"PHYS\"},{\"classId\":1161,\"subject\":\"PHYS\"},{\"classId\":1171,\"subject\":\"PHYS\"}]},{\"type\":\"or\",\"values\":[{\"classId\":1252,\"subject\":\"MATH\"},{\"classId\":1342,\"subject\":\"MATH\"},{\"classId\":2321,\"subject\":\"MATH\"}]}]}},{\"subject\":\"PHYS\",\"classId\":1156,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"PHYS\",\"classId\":1157,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"ENGW\",\"classId\":1111,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":1990,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"HIST\",\"classId\":1130,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"MATH\",\"classId\":2321,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"HONR\",\"classId\":1310,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":3000,\"prereqs\":{\"type\":\"or\",\"values\":[{\"type\":\"and\",\"values\":[{\"classId\":2510,\"subject\":\"CS\"},{\"classId\":1800,\"subject\":\"CS\"}]},{\"classId\":2160,\"subject\":\"EECE\"}]}},{\"subject\":\"CS\",\"classId\":3500,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":2510,\"subject\":\"CS\"},{\"classId\":2560,\"subject\":\"EECE\"}]}},{\"subject\":\"CS\",\"classId\":3650,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":2510,\"subject\":\"CS\"},{\"classId\":2560,\"subject\":\"EECE\"}]}},{\"subject\":\"CS\",\"classId\":3800,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":2510,\"subject\":\"CS\"},{\"classId\":2160,\"subject\":\"EECE\"}]}},{\"subject\":\"MATH\",\"classId\":2331,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":1342,\"subject\":\"MATH\"},{\"classId\":1242,\"subject\":\"MATH\"},{\"classId\":1252,\"subject\":\"MATH\"},{\"classId\":1800,\"subject\":\"CS\"}]}},{\"subject\":\"PHIL\",\"classId\":1145,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":1210,\"prereqs\":{\"type\":\"and\",\"values\":[{\"classId\":2510,\"subject\":\"CS\"}]}},{\"subject\":\"CS\",\"classId\":3700,\"prereqs\":{\"type\":\"and\",\"values\":[{\"classId\":2510,\"subject\":\"CS\"}]}},{\"subject\":\"CS\",\"classId\":4400,\"prereqs\":{\"type\":\"and\",\"values\":[{\"classId\":3500,\"subject\":\"CS\"},{\"type\":\"or\",\"values\":[{\"classId\":3000,\"subject\":\"CS\"}]}]}},{\"subject\":\"CS\",\"classId\":4500,\"prereqs\":{\"type\":\"and\",\"values\":[{\"classId\":3500,\"subject\":\"CS\"},{\"type\":\"or\",\"values\":[{\"classId\":1111,\"subject\":\"ENGW\"},{\"classId\":1102,\"subject\":\"ENGW\"}]}]}},{\"subject\":\"THTR\",\"classId\":1170,\"prereqs\":{\"type\":\"and\",\"values\":[]}},{\"subject\":\"CS\",\"classId\":4410,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":4400,\"subject\":\"CS\"},{\"classId\":5400,\"subject\":\"CS\"},{\"classId\":7400,\"subject\":\"CS\"}]}},{\"subject\":\"ENGW\",\"classId\":3315,\"prereqs\":{\"type\":\"or\",\"values\":[{\"classId\":1111,\"subject\":\"ENGW\"},{\"classId\":1102,\"subject\":\"ENGW\"}]}},{\"subject\":\"CS\",\"classId\":3001,\"prereqs\":{\"type\":\"and\",\"values\":[]}}]}";
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(body);
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
