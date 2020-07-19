import java.util.List;

public class PreReq<T> {
    String type;
    List<T> preReqList; //single = list<Course>, multie = list<PreReq>

    public PreReq(String type, List<T> preReqList) {
        this.type = type;
        this.preReqList = preReqList;
    }

    //GETTERS
    public String getType() {
        return this.type;
    }

    public List<T> getPreReqList() {
        return this.preReqList;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
//        str.append("\"prereqs\": {\n\"type\": \"" + this.getType() + "\",");
        List<PreReq> courseList = (List<PreReq>)this.getPreReqList();
        if (courseList.size() == 0) {
            str.append("\"prereqs\": {\n\"type\": \"" );
            str.append(this.type + "\",\n\"values\": [] \n}\n},");
        }
        else if (this.getType().equals("")) {
            List<Course> cl = (List<Course>)this.getPreReqList();
            for(int i = 0; i < cl.size(); i++) {
                Course course = cl.get(i);
                if (i != cl.size() - 1) {
                    str.append(course.toJSONString(true, false));
                }
                else {
                    str.append(course.toJSONString(true, true));
                }
            }
        }
        else {
            str.append("\"prereqs\": {\n\"type\": \"" );
            str.append(this.type + "\",");
            str.append("\n\"values\": [");
            for (PreReq preReq : courseList) {
                str.append(preReq.toString());
            }
//            for(int i = 0; i < courseList.size(); i++) {
//                PreReq course = courseList.get(i);
//                List<Course> c = course.getPreReqList();
//                for(Course cr: c) {
//                    if (i != courseList.size() - 1) {
//                        str.append(cr.toJSONString(true, false));
//                    }
//                    else {
//                        str.append(cr.toJSONString(true, true));
//                    }
//                }
//
//            }
//            str.append("GAH");
            int len = str.length();
//        str.deleteCharAt(len);
            str.append( "]\n} \n}");
        }
        return str.toString();
    }
}
