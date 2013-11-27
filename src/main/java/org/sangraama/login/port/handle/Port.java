
package org.sangraama.login.port.handle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Port {
    @XmlAttribute(name = "x")
    private int x;
    @XmlAttribute(name = "y")
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Port{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
