
package org.sangraama.login.port.handle;

import org.sangraama.login.domain.PortObject;
import org.sangraama.login.domain.PortObjectImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PortLoader {
    private JAXBContext jc;
    public static Map<Integer, PortObject> portList = new HashMap<>();

    public void ParsePortFile() {
        try {
            jc = JAXBContext.newInstance(PortSet.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            InputStream inputstream = this.getClass().getResourceAsStream("/asserts/ports.xml");

            PortSet portSet = (PortSet) unmarshaller.unmarshal(inputstream);
            int count = 0;
            for (Port port : portSet.getPortList()) {

                PortObjectImpl portObjectImpl = new PortObjectImpl();
                portObjectImpl.setX(port.getX());
                portObjectImpl.setY(port.getY());
                portList.put(count, portObjectImpl);
                count++;

            }


        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static Map<Integer, PortObject> getPortList() {
        return portList;
    }
}
