package reader.impl;

import converter.ValueConverter;
import exceptions.ReaderException;
import exceptions.WriterException;
import reader.IFileReader;
import writer.IWriter;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class SimpleMidiFileReader implements IFileReader {

    private final Long fixedNoteLengthMs;
    private final File source;
    private final ValueConverter<MidiMessage> valueConverter;
    private final IWriter writer;
    private final PrintStream outStream;

    private Boolean running;

    public SimpleMidiFileReader(File source, ValueConverter<MidiMessage> valueConverter, IWriter writer, PrintStream outStream) {
        this.source = source;
        this.valueConverter = valueConverter;
        this.writer = writer;
        this.outStream = outStream;
        this.fixedNoteLengthMs = 500L;
        this.running = true;
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
            for (int i = 0; i < track.size() && this.running; i++) {
                try {
                    writer.writeNext(valueConverter.convert(track.get(i).getMessage()));
                } catch (WriterException e) {
                    throw new ReaderException("Failed to write", e);
                }
                try {
                    Thread.sleep(fixedNoteLengthMs);
                } catch (InterruptedException e) {
                    throw new ReaderException("Thread interrupted", e);
                }
            }
            if (!this.running) {
                break;
            }
        }
        if (this.running) {
            outStream.println("End of file");
        } else {
            outStream.println("Reading interrupted");
        }
    }

    @Override
    public void stop() {
        outStream.println("Stopping reader");
        this.running = false;
    }
}
