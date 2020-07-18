import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.*;
import java.io.*;
import java.sql.SQLOutput;
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
                    List<PreReq> preReqList = new ArrayList<>();
                    JSONObject obj = (JSONObject) values.get(0);
//                  //SinglePreReq
                    if (obj.get("classId") != null) {
                        PreReq<Course> noReqPreReq = this.createNoReqPreReq(course);
                        preReqList.add(noReqPreReq);
                        //check for the case of another preReq JSONObj
                        if (values.size() > 1) {
                            JSONObject secondObj = (JSONObject) values.get(1);
                            if (secondObj.get("type") != null) {
                                String secondType = (String) secondObj.get("type");
                                JSONArray secondValues = (JSONArray) secondObj.get("values");
                                PreReq<Course> secondPreReq = this.createSinglePreReqCourse(secondValues, secondType);
                                preReqList.add(secondPreReq);
                            }
                        }
                        PreReq<PreReq> multiPreReq = new PreReq<>(mainType, preReqList);
                        Course mainCourse = new Course(mainSubject, mainClassId, multiPreReq);
                        courseMap.put(mainClassId, mainCourse);
                    }
                    //Multi PreReq
                    else if (obj.get("values") != null) {
                        String innerType = (String) obj.get("type");
                        JSONArray arr = (JSONArray) obj.get("values");
                        Course mainCourse = this.createMultiPreReqCourse(arr, innerType, mainType, mainSubject, mainClassId);
                        courseMap.put(mainClassId, mainCourse);
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return courseMap;
    }

    private Course createNoReqCourse(JSONObject course) {
        PreReq<Course> preReq = new PreReq("and", new ArrayList<>());

        String subject = (String) course.get("subject");
        Long classId = (Long) course.get("classId");
        Course newCourse = new Course(subject, classId, preReq);
        return newCourse;
    }

    private PreReq<Course>  createNoReqPreReq (JSONObject course) {
        String type = "";
        List<Course> preReqList = new ArrayList<>();
        Course noReqCourse = this.createNoReqCourse(course);
        preReqList.add(noReqCourse);
        PreReq<Course> preReq = new PreReq<>(type,preReqList);
        return  preReq;
    }

    private PreReq<Course> createSinglePreReqCourse(JSONArray values, String type) {
        List<Course> preReqCourseList = new ArrayList<>();
        Iterator valuesIterator = values.iterator();
        while (valuesIterator.hasNext()) {
            JSONObject preReqValue = (JSONObject) valuesIterator.next();
            Course preReqCourse = this.createNoReqCourse(preReqValue);
            preReqCourseList.add(preReqCourse);
        }
        PreReq<Course> preReq = new PreReq<>(type, preReqCourseList);
       return preReq;
    }

    private Course createMultiPreReqCourse(JSONArray arr, String innerType, String mainType, String mainSubject, Long mainClassId) {
        List<PreReq> singlePreReqList = new ArrayList<>();
        List<Course> courseList = new ArrayList<>();
        for (int arrIndex = 0; arrIndex < arr.size(); arrIndex++) {
            JSONObject courseObj = (JSONObject) arr.get(arrIndex);
            Course newCourse = this.createNoReqCourse(courseObj);
            courseList.add(newCourse);
        }
        PreReq<Course> innerPreReq = new PreReq<>(innerType, courseList);
        singlePreReqList.add(innerPreReq);
        PreReq<PreReq> multiPreReq = new PreReq<>(mainType, singlePreReqList);
        Course mainCourse = new Course(mainSubject, mainClassId, multiPreReq);
        return mainCourse;
    }
//
//    List<Course> convertInnerPreReq(JSONArray jsonArray) {
//        List<Course> courseList = new ArrayList<>();
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JSONObject courseObj = (JSONObject) jsonArray.get(i);
//            Course newCourse = this.createNoReqCourse(courseObj);
//            courseList.add(newCourse);
//        }
//        return courseList;
//    }
}
