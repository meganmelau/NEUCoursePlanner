import java.util.List;

public class SinglePreReq extends PreReq {
    List<Course> preReqCourses;

    public SinglePreReq(String type, List<Course> prereqCourses) {
        super(type);
        this.preReqCourses = prereqCourses;
    }

    //GETTERS
    public List<Course> getPreReqCourses()
    {
        return this.preReqCourses;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.getType() + " ");
        List<Course> courseList = this.getPreReqCourses();
        for (Course course : courseList) {
            str.append(course.toString());
        }
        return str.toString();
    }
}
