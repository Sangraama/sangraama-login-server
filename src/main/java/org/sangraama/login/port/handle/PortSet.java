
package org.sangraama.login.port.handle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "portSet")
public class PortSet {
    @XmlElement(name = "port")
    private List<Port> portList = new ArrayList<>();

    public List<Port> getPortList() {
        return portList;
    }

    public void setPortList(List<Port> portList) {
        this.portList = portList;
    }

    @Override
    public String toString() {
        return "PortSet{" +
                "portList=" + portList +
                '}';
    }
}
