import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLOutput;
import java.util.*;

public class CoursePlanSender {
    public CoursePlanSender() throws Exception {
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
                            System.out.println(key + " " + innerCourse);
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

                            System.out.println(innerKey);
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
                            System.out.println(noReqCourseList + "  " + innerKey + " no key " + !plan.containsKey(innerKey));
                            System.out.println();
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

    public JSONObject convertToJSON(Map<Long, Course> plan) {
        JSONObject json = new JSONObject();
        json.put("name", "student");

        JSONArray preReqs = new JSONArray();
        JSONObject course = new JSONObject();
        course.put("information", "test");
        course.put("id", 3);
        course.put("name", "course1");
        preReqs.add(course);

        json.put("plan", preReqs);
// {"course":[{"id":3,"information":"test","name":"course1"}],"name":"student"}

//        "values": [
//        {
//            "subject": "CS",
//                "classId": 2500
//        }
//]


        for (Long name : plan.keySet()) {
            String key = name.toString();
            Course value = plan.get(name);
            value.getClassId();
            value.getSubject();
            PreReq p = value.getPreReq();

        }
        return json;
    }
}
