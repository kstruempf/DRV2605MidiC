package writer.impl;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import dto.Value;
import exceptions.WriterException;
import writer.IWriter;

import java.io.IOException;
import java.io.PrintStream;

@SuppressWarnings("FieldCanBeLocal")
public class I2CWriter implements IWriter {

    private final byte DRV2605_ADDRESS = 0x58;

    private final byte REGISTER_RTP_ADDRESS = 0x02;

    private I2CDevice device;
    private PrintStream outStream;

    public I2CWriter(PrintStream outStream) {

        this.outStream = outStream;
    }

    public void initialize() throws WriterException {
        if (this.device == null) {
            try {
                outStream.println("Initializing device...");
                I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
                this.device = i2c.getDevice(DRV2605_ADDRESS);
            } catch (IOException e) {
                throw new WriterException("Failed to initialize", e);
            } catch (I2CFactory.UnsupportedBusNumberException e) {
                throw new WriterException("Bus not supported", e);
            }
        } else {
            outStream.println("Already initialized");
        }
    }

    @Override
    public void writeNext(Value value) throws WriterException {
        try {
            outStream.println("Writing " + value + " to register " + REGISTER_RTP_ADDRESS);
            device.write(REGISTER_RTP_ADDRESS, value.getBytes());
        } catch (IOException e) {
            throw new WriterException("Failed to write " + value.toString(), e);
        }
    }
}
