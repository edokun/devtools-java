package net.edokun.tools;

/**
 * Class for support unit tests.
 * This class contains a default constructor and
 * one attribute of each primitive data type and
 * it's wrapper plus String and Object.
 *
 * @author Eduardo Vidal
 */
public class TestPojo {

    private String stringVar;
    private Integer integerVar;
    private Double doubleVar;
    private Character charVar;
    private Boolean boolVar;
    private Long longVar;
    private Float floatVar;
    private Byte byteVar;
    private Short shortVar;
    private Object objectVar;

    // primitive values
    private int intValue;
    private float floatValue;
    private char charValue;
    private boolean boolValue;
    private long longValue;
    private double doubleValue;
    private byte byteValue;
    private short shortValue;

    // default constructor
    public TestPojo() {}

    // other constructor
    public TestPojo(String stringVar){
        this.stringVar = stringVar;
    }


    // getters and setters
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

    public Double getDoubleVar() {
        return doubleVar;
    }

    public void setDoubleVar(Double doubleVar) {
        this.doubleVar = doubleVar;
    }

    public Character getCharVar() {
        return charVar;
    }

    public void setCharVar(Character charVar) {
        this.charVar = charVar;
    }

    public Boolean getBoolVar() {
        return boolVar;
    }

    public void setBoolVar(Boolean boolVar) {
        this.boolVar = boolVar;
    }

    public Long getLongVar() {
        return longVar;
    }

    public void setLongVar(Long longVar) {
        this.longVar = longVar;
    }

    public Float getFloatVar() {
        return floatVar;
    }

    public void setFloatVar(Float floatVar) {
        this.floatVar = floatVar;
    }

    public Byte getByteVar() {
        return byteVar;
    }

    public void setByteVar(Byte byteVar) {
        this.byteVar = byteVar;
    }

    public Short getShortVar() {
        return shortVar;
    }

    public void setShortVar(Short shortVar) {
        this.shortVar = shortVar;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public void setCharValue(char charValue) {
        this.charValue = charValue;
    }

    public boolean isBoolValue() {
        return boolValue;
    }

    public void setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte byteValue) {
        this.byteValue = byteValue;
    }

    public short getShortValue() {
        return shortValue;
    }

    public void setShortValue(short shortValue) {
        this.shortValue = shortValue;
    }

    public Object getObjectVar() {
        return objectVar;
    }

    public void setObjectVar(Object objectVar) {
        this.objectVar = objectVar;
    }
}
