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
        str.append(this.getType() + " (");
        List<T> courseList = this.getPreReqList();
        if (courseList.size() == 0) {
            str.append("no reqs// ");
        }
        else if (courseList.get(0) instanceof Course) {
            for (T preReq : courseList) {
                str.append("**single " + preReq.toString());
            }
        } else {
            for (T preReq : courseList) {
                str.append("**multi " + preReq.toString());
            }
        }
        return str.toString();
    }
}
