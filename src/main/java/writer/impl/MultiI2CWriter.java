package writer.impl;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import dto.Value;
import exceptions.WriterException;
import writer.IWriter;

import java.io.IOException;
import java.util.logging.Logger;

public class MultiI2CWriter implements IWriter {
    private static final Logger logger = Logger.getLogger(MultiI2CWriter.class.getName());

    private final byte TCA9548A_ADDRESS = 0x70;
    private final byte DRV2605_ADDRESS = 0x5a;

    private final byte REGISTER_RTP_ADDRESS = 0x02;
    private final byte REGISTER_MODE_ADDRESS = 0x01;

    private final byte RTP_MODE = 0x05;

    private final byte[] MOTOR_ADDRESSES = new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};

    private I2CDevice mux;
    private I2CDevice motor;

    @Override
    public void initialize() throws WriterException {
        if (this.mux == null) {
            try {
                logger.info("Initializing multiplexer...");
                I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);

                logger.info("Connecting multiplexer");
                mux = i2c.getDevice(TCA9548A_ADDRESS);
                Thread.sleep(1000);
                mux.write((byte) 0xFF); // setting Control Register to forward to all channels
                logger.info("Connecting vibration motor");
                motor = i2c.getDevice(DRV2605_ADDRESS);
                Thread.sleep(1000);
                setDeviceToRTPMode();
                logger.info("Init complete.");
            } catch (IOException e) {
                throw new WriterException("Failed to initialize", e);
            } catch (I2CFactory.UnsupportedBusNumberException e) {
                throw new WriterException("Bus not supported", e);
            } catch (InterruptedException e) {
                throw new WriterException("Init interrupted", e);
            }
        } else {
            logger.info("Already initialized");
        }
    }

    public void setDeviceToRTPMode() throws WriterException {
        logger.info("Setting device to RTP mode");
        if (motor != null) {
            try {
                motor.write(REGISTER_MODE_ADDRESS, RTP_MODE);
            } catch (IOException e) {
                throw new WriterException("Failed to set device to RTP Mode", e);
            }
        } else {
            throw new WriterException("Device is not set");
        }
    }

    @Override
    public void writeNext(Value value) throws WriterException {
        if (mux == null) {
            throw new WriterException("Not initialized correctly");
        }

        logger.info("Writing " + value + " to motor " + value.getAddress());

        try {
            mux.write(value.getAddress()); // switch channel to device
            motor.write(REGISTER_RTP_ADDRESS, value.getBytes()); // send value to device
        } catch (IOException e) {
            logger.info(e.getMessage());
            throw new WriterException("Failed to write " + value + " to motor " + value.getAddress(), e);
        }
    }
}
