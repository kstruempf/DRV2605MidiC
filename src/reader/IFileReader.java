package reader;

import exceptions.ReaderException;

public interface IFileReader {

    /**
     * Reads the entire contents of the input file and forwards the converted values to the writer.
     */
    void readAll() throws ReaderException;
}
