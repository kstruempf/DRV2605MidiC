package dto;

public class Value {


    private Long value;

    public Value(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = 5L;
    }

    public byte getBytes() {
        return value.byteValue();
    }

    @Override
    public String toString() {
        return "Value{" +
                "value=" + value +
                '}';
    }
}
