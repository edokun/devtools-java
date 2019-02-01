package net.edokun.tools;

/**
 * Class for support unit tests.
 * This class contains only a non default constructor.
 *
 * @author Eduardo Vidal
 */
public class TestPojo2 {

    private String stringVar;
    private Integer integerVar;

    //Class without default constructor
    public TestPojo2(String stringVar, Integer integerVar){
        this.stringVar = stringVar;
        this.integerVar = integerVar;
    }

    public String getStringVar() {
        return stringVar;
    }

    public void setStringVar(String stringVar) {
        this.stringVar = stringVar;
    }

    public Integer getIntegerVar() {
        return integerVar;
    }

    public void setIntegerVar(Integer integerVar) {
        this.integerVar = integerVar;
    }
}
