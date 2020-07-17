import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.*;
import java.io.*;
import java.util.*;

public class URLConnectionReader {
    public String connectionReader(String URLString) throws Exception {

        URL sandboxURL = new URL(URLString);
        URLConnection connection = sandboxURL.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        String inputLine = in.readLine();
        in.close();
        return inputLine;
    }

    public Map<Long, Course> jsonParser(String jsonString) {
        SortedMap<Long, Course> courseMap = new TreeMap<>();
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonString);
            JSONArray courses = (JSONArray) json.get("courses");
            Iterator i = courses.iterator();

            //Look through Json course by course
            while (i.hasNext()) {
                JSONObject course = (JSONObject) i.next();
                String mainSubject = (String) course.get("subject");
                Long mainClassId = (Long) course.get("classId");
                JSONObject preReqs = (JSONObject) course.get("prereqs");
                JSONArray values = (JSONArray) preReqs.get(("values"));
                String mainType = (String) preReqs.get(("type"));

                //No Reqs
                if (values.size() == 0) {
                    Course newCourse = this.createNoReqCourse(course);
                    courseMap.put(mainClassId, newCourse);
                }
                // Has Reqs
                else if (values.size() > 0) {
                    //check if you can call .values again --> if true create multiprereq course
                    List<SinglePreReq> singlePreReqList = new ArrayList<>();
                        JSONObject obj = (JSONObject) values.get(0);
//                        System.out.println(mainClassId  + " " +obj + " SINGLE " + (obj.get("classId") != null) + " " + "//MULTI " + (obj.get("values") != null));
                    //SinglePreReq
                    if (obj.get("classId") != null) {
                            List<Course> preReqCourseList = new ArrayList<>();
                            Iterator valuesIterator = values.iterator();
                            while (valuesIterator.hasNext()) {
                                JSONObject preReqValue = (JSONObject) valuesIterator.next();
                                Course preReqCourse = this.createNoReqCourse(preReqValue);
                                preReqCourseList.add(preReqCourse);
                            }
                            SinglePreReq preReq = new SinglePreReq(mainType, preReqCourseList);
                            Course mainCourse = new Course(mainSubject, mainClassId, preReq);
                            courseMap.put(mainClassId, mainCourse);

                            //check for the case of another preReq JSONObject obj = (JSONObject) values.get(1);


                        if (values.size() >1) {

                            System.out.println("size "  +values.size() + " values1 " + values.get(1));

                        }
                        }
                    //Multi PreReq
                        else if (obj.get("values") != null) {
                            String innerType = (String) obj.get("type");
                            JSONArray arr = (JSONArray) obj.get("values");
                            //
                            List<Course> courseList = new ArrayList<>();
                            for (int arrIndex = 0; arrIndex < arr.size(); arrIndex++) {
                                JSONObject courseObj = (JSONObject) arr.get(arrIndex);
                                Course newCourse = this.createNoReqCourse(courseObj);
                                courseList.add(newCourse);
                            }
                            SinglePreReq innerPreReq = new SinglePreReq(innerType, courseList);
                            singlePreReqList.add(innerPreReq);
                            MultiPreReq multiPreReq = new MultiPreReq(mainType, singlePreReqList);
                            Course mainCourse = new Course(mainSubject, mainClassId, multiPreReq);
                            courseMap.put(mainClassId, mainCourse);
                        }

                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return courseMap;
    }

    public Course createNoReqCourse(JSONObject course) {
        SinglePreReq preReq = new SinglePreReq("and", new ArrayList<>());

        String subject = (String) course.get("subject");
        Long classId = (Long) course.get("classId");
        Course newCourse = new Course(subject, classId, preReq);
        return newCourse;
    }

    Map<Long, Course> createPlan(Map<Long, Course> courseList) {
        SortedMap<Long, Course> plan = new TreeMap<Long, Course>();
        //First Traversal: Take All Classes without PreReqs
        for (Map.Entry mapElement : courseList.entrySet()) {
            Long key = (Long) mapElement.getKey();
            Course course = ((Course) mapElement.getValue());
            //No PreReqs take it first!
            if (course.getPreReq() instanceof SinglePreReq) {
                plan.put(key, course);
            }
        }

        //The rest of the courses with preReqs
        for (Map.Entry mapElement : courseList.entrySet()) {
            Long key = (Long) mapElement.getKey();
            Course course = ((Course) mapElement.getValue());
            //Have you Taken the PreReqs
            //SinglePreReq
            if (course.getPreReq() != null) {
                SinglePreReq coursePreReq = course.getPreReq();
                Map<Long, Course> courseMap = this.putCourseInMap(course, coursePreReq, key);
                plan.putAll(courseMap);
            }
            //MultiPreReq
           else if (course.getMultiPreReq() != null) {
//               worry about and and or of multi
//                if or just check for one
//                 if and check for both plan.contains
               MultiPreReq multiPreReq = course.getMultiPreReq();
                List<SinglePreReq> singlePreReqList = multiPreReq.getPreReqList();
                for (SinglePreReq singlePreReq: singlePreReqList) {
                    Map<Long, Course> courseMap = this.putCourseInMap(course, singlePreReq, key);
                    plan.putAll(courseMap);
                }
            }
           else {
                System.out.println("failed " + course);
            }
        }
        //if no solution send no solution
        if (plan.size() != courseList.size()) {
            plan = new TreeMap<>();
        }
        return plan;
    }

    Map<Long, Course> putCourseInMap(Course course, SinglePreReq singlePreReq, Long key) {
        SortedMap<Long, Course> plan = new TreeMap<Long, Course>();
        List<Course> preReqCourses = singlePreReq.getPreReqCourses();
        for (Course preReqCourse : preReqCourses) {
            Long preReqId = preReqCourse.getClassId();
//            System.out.println( course + " " + preReqCourse + " " + preReqId);
            if (plan.containsKey(preReqId)) {
                plan.put(key, course);
            }
        }
        return plan;
    }

    List<Course> convertInnerPreReq(JSONArray jsonArray) {
        List<Course> courseList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject courseObj = (JSONObject) jsonArray.get(i);
//            String subject = (String) courseObj.get("subject");
//            Long classId = (Long) courseObj.get("classId");
//            SinglePreReq empty = new SinglePreReq("and", new ArrayList<>());
//            Course newCourse = new Course(subject, classId, empty);
            Course newCourse = this.createNoReqCourse(courseObj);
            courseList.add(newCourse);
        }
        return courseList;
    }

    JSONObject convertToJSON(Map<Long, Course> plan) {
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
            SinglePreReq p = value.getPreReq();

        }
        return json;
    }
}
