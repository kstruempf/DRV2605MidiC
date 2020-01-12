package writer;

import dto.Value;
import exceptions.WriterException;

public interface IWriter {

    void writeNext(Value value) throws WriterException;
}
