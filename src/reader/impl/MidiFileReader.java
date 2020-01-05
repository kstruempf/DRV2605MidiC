package reader.impl;

import converter.ValueConverter;
import reader.IFileReader;

import javax.sound.midi.MidiMessage;
import java.io.File;
import java.io.PrintStream;

public class MidiFileReader implements IFileReader {

    private File source;
    private ValueConverter<MidiMessage> valueConverter;
    private PrintStream outStream;

    public MidiFileReader(File source, ValueConverter<MidiMessage> valueConverter, PrintStream outStream) {
        this.source = source;
        this.valueConverter = valueConverter;
        this.outStream = outStream;
    }


    @Override
    public void readAll() {
        outStream.printf("Reading from %s...", source.getName());
    }
}
