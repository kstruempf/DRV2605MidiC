package writer.impl;

import dto.Value;
import exceptions.WriterException;
import writer.IWriter;

import java.util.logging.Logger;

public class LogWriter implements IWriter {
    private static final Logger logger = Logger.getLogger(LogWriter.class.getName());

    @Override
    public void initialize() throws WriterException {

    }

    @Override
    public void writeNext(Value value) {
        logger.info(value.toString());
    }
}
