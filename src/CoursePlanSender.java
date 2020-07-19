import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class CoursePlanSender {
    public CoursePlanSender() {
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
            if (course.getPreReq().getPreReqList().size() == 0) {
                plan.put(key, course);
            }
        }

        //Second Traversal with PreReqs
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
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("{\n" + "\"plan\": [");
        for (Map.Entry course : plan.entrySet()) {
            Course value = (Course) course.getValue();
            jsonString.append(value.toString());
        }
        int len = jsonString.length();
        char checkComma = jsonString.charAt(len - 1);
        if (checkComma == ',') {
            jsonString.deleteCharAt(len - 1);
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
