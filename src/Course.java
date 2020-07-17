import java.util.List;

public class Course<T> {
    String subject;
    Long classId;
    PreReq preReq;
    public T t;

    public Course(String subject, Long classId, T t) {
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

    public PreReq<> getPreReq() {
        return this.preReq;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(this.getSubject() + " ");
        str.append(this.getClassId() + " ");
        str.append(this.getPreReq() + "");
        return str.toString();
    }
}
