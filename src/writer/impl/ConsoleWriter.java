package writer.impl;

import dto.Value;
import exceptions.WriterException;
import writer.IWriter;

import java.io.PrintStream;

public class ConsoleWriter implements IWriter {

    private PrintStream outStream;

    public ConsoleWriter(PrintStream outStream) {
        this.outStream = outStream;
    }

    @Override
    public void initialize() throws WriterException {

    }

    @Override
    public void writeNext(Value value) {
        outStream.printf("%d\n", value.getValue());
    }
}
