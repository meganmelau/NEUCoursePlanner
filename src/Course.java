import java.util.List;

public class Course<T> {
    String subject;
    Long classId;
    PreReq<T> preReq;

    public Course(String subject, Long classId, PreReq<T> preReq) {
        this.subject = subject;
        this.classId = classId;
        this.preReq = preReq;
    }

    // GETTERS
    public String getSubject() {
        return this.subject;
    }

    public Long getClassId() {
        return this.classId;
    }

    public PreReq<T> getPreReq() {
        return this.preReq;
    }

    public String toJSONString(boolean isNoPreReqPrint, boolean isNoComma) {
        StringBuilder str = new StringBuilder();
        if (isNoPreReqPrint) {
            str.append("\n{\n\"subject\": \"" + this.getSubject() + "\", \n");
            str.append("\"classId\": " + this.getClassId() + "\n},");
            if (!isNoComma) {
                str.append(",");
            }
        } else {
            str.append("\n{\n\"subject\": \"" + this.getSubject() + "\", \n");
            str.append("\"classId\": " + this.getClassId() + ", \n");
            PreReq<T> p = this.getPreReq();
            List<T> l = p.getPreReqList();
            for (int i = 0; i < l.size(); i++) {
                T obj = l.get(i);
                if (i != l.size() - 1) {
                    str.append(obj);
                    str.append(",");
                } else {
                    str.append(obj);
                }
            }
        }
        return str.toString();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\n{\n\"subject\": \"" + this.getSubject() + "\", \n");
        str.append("\"classId\":" + this.getClassId() + ", \n");
        str.append(this.getPreReq() + "");

        return str.toString();
    }
}
