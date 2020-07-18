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
        SortedMap<Long, Course> plan = new TreeMap<Long, Course>();
        //First Traversal: Take All Classes without PreReqs
//        for (Map.Entry mapElement : courseList.entrySet()) {
//            Long key = (Long) mapElement.getKey();
//            Course course = ((Course) mapElement.getValue());
//            //No PreReqs take it first!
//            if (course.getPreReq().getPreReqList().size() == 0) {
//                plan.put(key, course);
//            }
//        }

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
                entryIt.remove();
            }
        }

        for (Map.Entry mapElement : courseList.entrySet()) {
            Long key = (Long) mapElement.getKey();
            Course course = ((Course) mapElement.getValue());
            PreReq<PreReq> preReq = course.getPreReq();
            String mainType = preReq.getType();
            List<PreReq> preReqList = preReq.getPreReqList();
            if (mainType.equals("and")) {
//                System.out.println(" and condition" + course);
            }
            for(PreReq innerPreReq: preReqList) {
                String innerType = innerPreReq.getType();
                //empty string = noReq
                if(innerType.equals("")) {
                    List<Course> noReqCourseList = innerPreReq.getPreReqList();
                    for(Course innerCourse: noReqCourseList) {
                        Long innerKey = innerCourse.getClassId();
                        plan.put(innerKey, innerCourse);
                    }
                    System.out.println(key);
                    plan.put(key, course);
                }
            }

            //checking prereq --> list<prereqg> --> iterate --> course

            //main Course after checking prereq
            plan.put(key, course);

        }
//        //not nested prereqs
//        for (Map.Entry mapElement : courseList.entrySet()) {
//            Long key = (Long) mapElement.getKey();
//            Course course = ((Course) mapElement.getValue());
//            PreReq<PreReq> preReq = course.getPreReq();
//            List<PreReq> preReqList = preReq.getPreReqList();
//            for (PreReq courseP : preReqList) {
//                List<Course> innerPreReq = courseP.getPreReqList();
//                if (preReq.getType().equals("and")) {
//                    Boolean takenAllPreReqAnd = true;
//                    for (Course innerPreReqCourse : innerPreReq) {
////                        System.out.println(course + " -//" + innerPreReqCourse.getSubject() + " " + innerPreReqCourse.getClassId());
//                        Long preReqId = innerPreReqCourse.getClassId();
//                        if (!plan.containsKey(innerPreReqCourse.getClassId())) {
//                            takenAllPreReqAnd = false;
//                        }
//                    }
//                    if (takenAllPreReqAnd) {
//                        for (Course innerPreReqCourse : innerPreReq) {
////                        System.out.println(course + " -//" + innerPreReqCourse.getSubject() + " " + innerPreReqCourse.getClassId());
//                            Long preReqId = innerPreReqCourse.getClassId();
//                            plan.put(preReqId, innerPreReqCourse);
//                        }
//                    }
//                }
//                else if(preReq.getType().equals("or")){
//                    List<PreReq> orPreReq = preReq.getPreReqList();
//                    for (PreReq orPreReqCourse : orPreReq) {
//                        Course c = (Course) orPreReqCourse.getPreReqList().get(0);
//                        Long courseId = c.getClassId();
//                        if (plan.containsKey(courseId)) {
//                            plan.put(courseId, c);
//                        }
//                    }
//                }
//            }
//        }

            //The rest of the courses with preReqs
            for (Map.Entry mapElement : courseList.entrySet()) {
                Long key = (Long) mapElement.getKey();
                Course course = ((Course) mapElement.getValue());
                //Have you Taken the PreReqs
                //SinglePreReq
                if (course.getPreReq().getPreReqList().size() > 0) {
                    PreReq<PreReq> coursePreReq = course.getPreReq();
                    List<PreReq> preReqList = coursePreReq.getPreReqList();
                    for (int i = 0; i < preReqList.size(); i++) {
                        PreReq preReqCourse = preReqList.get(i);
                        List<Course> innerPreReqList = preReqCourse.preReqList;
                        for (Course c : innerPreReqList) {
                            Long preReqId = c.getClassId();
                            if (plan.containsKey(preReqId)) {
                                plan.put(preReqId, course);
                            }
                        }
                    }
                }
//
//                plan.put(classId, course);

//                Map<Long, Course> courseMap = this.putCourseInMap(course, coursePreReq, key);
//                plan.putAll(courseMap);
            }
//                }
//            //MultiPreReq
//            else if (course.getPreReq() != null) {
////               worry about and and or of multi
////                if or just check for one
////                 if and check for both plan.contains
//                PreReq<PreReq> multiPreReq = course.getPreReq();
//                List<PreReq> singlePreReqList = multiPreReq.getPreReqList();
//                for (PreReq<Course> singlePreReq : singlePreReqList) {
//                    Map<Long, Course> courseMap = this.putCourseInMap(course, singlePreReq, key);
//                    plan.putAll(courseMap);
//                }
//            } else {
//                System.out.println("failed " + course);
//            }
//        }
//        //if no solution send no solution
//        if (plan.size() != courseList.size()) {
//            plan = new TreeMap<>();
//        }
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
