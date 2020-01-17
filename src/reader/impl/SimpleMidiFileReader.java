package reader.impl;

import converter.ValueConverter;
import exceptions.ReaderException;
import exceptions.WriterException;
import reader.IFileReader;
import writer.IWriter;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class SimpleMidiFileReader implements IFileReader {
    private static final Logger logger = Logger.getLogger(SimpleMidiFileReader.class.getName());

    private final Long fixedNoteLengthMs;
    private final File source;
    private final ValueConverter<MidiMessage> valueConverter;
    private final IWriter writer;

    private Boolean running;

    public SimpleMidiFileReader(File source, ValueConverter<MidiMessage> valueConverter, IWriter writer) {
        this.source = source;
        this.valueConverter = valueConverter;
        this.writer = writer;
        this.fixedNoteLengthMs = 500L;
        this.running = true;
    }

    @Override
    public void readAll() throws ReaderException {
        logger.info(String.format("Reading from %s...\n", source.getName()));

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
            logger.info("End of file");
        } else {
            logger.info("Reading interrupted");
        }
    }
}
