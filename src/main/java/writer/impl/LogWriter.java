package writer.impl;

import dto.Value;
import exceptions.WriterException;
import writer.IWriter;

import java.util.logging.Logger;

import static constants.Constants.MUX_CONTROL_REGISTER_VALUES;

public class LogWriter implements IWriter {
    private static final Logger logger = Logger.getLogger(LogWriter.class.getName());

    @Override
    public void initialize() throws WriterException {
        logger.info("Initializing LogWriter");
    }

    @Override
    public void writeNext(Value value) {
        logger.info(String.format("Writing %s to motor %d via channel 0x%02X", value, value.getDestination(), MUX_CONTROL_REGISTER_VALUES[value.getDestination()]));
    }
}
