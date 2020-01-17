package dto;

import enumeration.ValueType;

public class Value {

    private Long amplitude;
    private Byte address;
    private ValueType valueType;

    public Value(Long amplitude, Byte destination, ValueType valueType) {
        this.amplitude = amplitude;
        this.address = destination;
        this.valueType = valueType;
    }

    public Long getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(long amplitude) {
        this.amplitude = 5L;
    }

    public byte getBytes() {
        return amplitude.byteValue();
    }

    public Byte getAddress() {
        return address;
    }

    public void setAddress(Byte address) {
        this.address = address;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    @Override
    public String toString() {
        return "Value{" +
                "amplitude=" + amplitude +
                ", address=" + address +
                ", valueType=" + valueType +
                '}';
    }
}
