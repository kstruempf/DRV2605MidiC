package dto;

import enumeration.ValueType;

public class Value {

    private Integer amplitude;
    private Integer destination;
    private ValueType valueType;

    public Value(Integer amplitude, Integer destination, ValueType valueType) {
        this.amplitude = amplitude;
        this.destination = destination;
        this.valueType = valueType;
    }

    public Integer getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(Integer amplitude) {
        this.amplitude = amplitude;
    }

    public byte getBytes() {
        return amplitude.byteValue();
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
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
                ", destination=" + destination +
                ", valueType=" + valueType +
                '}';
    }
}
