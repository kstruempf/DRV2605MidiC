package constants;

public class Constants {


    public static final byte TCA9548A_ADDRESS = 0x70;
    public static final byte DRV2605_ADDRESS = 0x5a;

    public static final byte REGISTER_RTP_ADDRESS = 0x02;
    public static final byte REGISTER_MODE_ADDRESS = 0x01;

    public static final byte RTP_MODE = 0x05;
    public static final byte REMOVE_FROM_STANDBY_MODE = 0x00;

    public static final byte[] MUX_CONTROL_REGISTER_VALUES = new byte[]{0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, (byte) 0x80, (byte) 0xFF};

}
