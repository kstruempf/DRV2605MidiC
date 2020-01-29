package writer.impl;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import dto.Value;
import exceptions.WriterException;
import writer.IWriter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static constants.Constants.*;

public class MultiI2CWriter implements IWriter {
    private static final Logger logger = Logger.getLogger(MultiI2CWriter.class.getName());

    private I2CDevice mux;
    private I2CDevice motor;

    private Set<Integer> detectedMotors;

    public MultiI2CWriter() {
        this.detectedMotors = new HashSet<>();
    }

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
                Thread.sleep(500);
                detectMotors();
                Thread.sleep(500);
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

    private void detectMotors() {
        logger.info("Detecting motors...");

        for (int i = 0; i < 10; i++) { // TODO move 8 to constant
            try {
                logger.info("Pinging motor " + i);
                Thread.sleep(500);
                mux.write(MUX_CONTROL_REGISTER_VALUES[i]); // switch channel to device
                Thread.sleep(500);
                motor.write(REGISTER_RTP_ADDRESS, (byte) 50);
                Thread.sleep(500);
                motor.write(REGISTER_RTP_ADDRESS, (byte) 0);
            } catch (InterruptedException | IOException e) {
                logger.info("No motor detected at address " + i);
                continue;
            }
            this.detectedMotors.add(i);
        }

        logger.info("Detected motors at " + this.detectedMotors.toString());
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
    public synchronized void writeNext(Value value) throws WriterException {
        if (mux == null) {
            throw new WriterException("Not initialized correctly");
        }

        logger.info(String.format("Writing %s to motor %d via channel 0x%02X", value, value.getDestination(), MUX_CONTROL_REGISTER_VALUES[value.getDestination()]));

        try {
            mux.write(MUX_CONTROL_REGISTER_VALUES[value.getDestination()]); // switch channel to device
            Thread.sleep(0);
            motor.write(REGISTER_RTP_ADDRESS, value.getBytes()); // send value to device
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, String.format("Motor %d on channel 0x%02X not reachable (ERROR: %s)",
                    value.getDestination(), MUX_CONTROL_REGISTER_VALUES[value.getDestination()], e.getMessage()));
        }
    }
}
