package writer.impl;

import dto.Value;
import writer.IWriter;

import java.io.PrintStream;

public class ConsoleWriter implements IWriter {

    private PrintStream outStream;

    public ConsoleWriter(PrintStream outStream) {
        this.outStream = outStream;
    }

    @Override
    public void writeNext(Value value) {
        outStream.printf("Value %d\n", value.getValue());
    }
}
