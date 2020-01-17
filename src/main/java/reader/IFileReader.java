package reader;

import exceptions.ReaderException;

public interface IFileReader {

    /**
     * Allocates all system resources and prepares the reader for operation.
     */
    void initialize() throws ReaderException;

    /**
     * Reads the entire contents of the input file and forwards the converted values to the writer.
     */
    void readAll() throws ReaderException;

    /**
     * Shut's the reader down and releases allocated resources.
     */
    void shutDown() throws ReaderException;
}
