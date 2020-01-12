package writer.impl;

import dto.Value;
import writer.IWriter;

import java.io.IOException;

public class I2CWriter implements IWriter {

    private final byte SLAVE_ADDRESS = 0x58;

    private final byte REGISTER_RTP_ADDRESS = 0x02;

    @Override
    public void writeNext(Value value) {

    }


    private void sendOverBus(byte slave, byte register, byte[] data) throws IOException {

    }
}
