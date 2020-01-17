package writer;

import com.pi4j.io.i2c.I2CFactory;
import dto.Value;
import exceptions.WriterException;

import java.io.IOException;

public interface IWriter {
    void initialize() throws WriterException;

    void writeNext(Value value) throws WriterException;
}
