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
    private final byte REGISTER_RTP_ADDRESS = 0x02;
    private final byte REGISTER_MODE_ADDRESS = 0x01;

    private final byte RTP_MODE = 0x05;

    private final byte[] MOTOR_ADDRESSES = new byte[]{0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7};

    private I2CDevice mux;

    @Override
    public void initialize() throws WriterException {
        if (this.mux == null) {
            try {
                logger.info("Initializing multiplexer...");
                I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
                this.mux = i2c.getDevice(TCA9548A_ADDRESS);
                Thread.sleep(1000);
                setAllMotorsToRtp();
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

    private void setAllMotorsToRtp() throws WriterException {
        logger.info("Setting device to RTP mode");

        for (byte motor_address : MOTOR_ADDRESSES) {
            logger.info("Setting motor " + motor_address + " to RTP Mode");
            if (mux != null) {
                try {
                    mux.write(motor_address, new byte[] {REGISTER_MODE_ADDRESS, RTP_MODE});
                    Thread.sleep(500);
                } catch (IOException e) {
                    throw new WriterException("Failed to set device to RTP Mode", e);
                } catch (InterruptedException e) {
                    throw new WriterException("RTP Mode init interrupted", e);
                }
            } else {
                throw new WriterException("Device is not set");
            }
        }
    }

    @Override
    public void writeNext(Value value) throws WriterException {
        if (mux == null) {
            throw new WriterException("Not initialized correctly");
        }

        logger.info("Writing " + value + " to motor " + value.getAddress());
        /*try {
            mux.write(value.getAddress());
        } catch (IOException e) {
            logger.info(e.getMessage());
            throw new WriterException("Failed to address motor " + value.getAddress(), e);
        }*/

        try {
            mux.write(value.getAddress(), new byte[] {REGISTER_RTP_ADDRESS, value.getBytes()});
        } catch (IOException e) {
            logger.info(e.getMessage());
            throw new WriterException("Failed to write " + value + " to motor " + value.getAddress(), e);
        }
    }
}
