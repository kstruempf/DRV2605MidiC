package reader.impl;

import converter.ValueConverter;
import exceptions.ReaderException;
import helper.MidiHelper;
import reader.IFileReader;
import writer.IWriter;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class MidiFileReader implements IFileReader {

    private File source;
    private ValueConverter<MidiMessage> valueConverter;
    private IWriter writer;
    private PrintStream outStream;

    public MidiFileReader(File source, ValueConverter<MidiMessage> valueConverter, IWriter writer, PrintStream outStream) {
        this.source = source;
        this.valueConverter = valueConverter;
        this.writer = writer;
        this.outStream = outStream;
    }


    @Override
    public void readAll() throws ReaderException {
        outStream.printf("Reading from %s...\n", source.getName());

        Sequence sequence;

        try {
            sequence = MidiSystem.getSequence(source);
        } catch (InvalidMidiDataException | IOException e) {
            throw new ReaderException("Failed to get sequence from source file", e);
        }

        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                writer.writeNext(valueConverter.convert(track.get(i).getMessage()));
            }
        }
    }
}
