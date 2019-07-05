package dclab.powerdatabackend.model;

import java.io.Serializable;
import java.util.List;

public class DeviceTree implements Serializable {

    private static final long serialVersionUID = 1L;
    private String plabel;

    public String getPlabel() {
        return plabel;
    }

    public void setPlabel(String plabel) {
        this.plabel = plabel;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private String label;
    private String value;



    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<DeviceTree> getChildren() {
        return children;
    }

    public void setChildren(List<DeviceTree> children) {
        this.children = children;
    }

    private List<DeviceTree> children;


}
