import java.util.List;

public class MultiPreReq extends PreReq {
    List<SinglePreReq> preReqList;

    public MultiPreReq(String type, List<SinglePreReq> preReqList) {
        super(type);
        this.preReqList = preReqList;
    }

    //GETTERS
    public List<SinglePreReq> getPreReqList()
    {
        return this.preReqList;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.getType() + " ");
        for(SinglePreReq preReq: this.getPreReqList()) {
            str.append(preReq.toString());
        }
        return str.toString();

    }
}
