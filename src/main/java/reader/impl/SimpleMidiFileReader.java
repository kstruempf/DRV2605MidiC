package reader.impl;

import converter.ValueConverter;
import exceptions.ReaderException;
import exceptions.WriterException;
import writer.IWriter;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import java.io.File;
import java.util.logging.Logger;

public class SimpleMidiFileReader extends MidiFileReader {
    private static final Logger logger = Logger.getLogger(SimpleMidiFileReader.class.getName());

    private final Long fixedNoteLengthMs;
    private final ValueConverter<MidiMessage> valueConverter;
    private final IWriter writer;

    private Boolean running;

    public SimpleMidiFileReader(File source, ValueConverter<MidiMessage> valueConverter, IWriter writer) {
        super(source);
        this.valueConverter = valueConverter;
        this.writer = writer;
        this.fixedNoteLengthMs = 500L;
        this.running = true;
    }

    @Override
    public void initialize() throws ReaderException {
        // nothing to do here
    }

    @Override
    public void readAll() throws ReaderException {
        logger.info(String.format("Reading from %s...\n", super.getSequenceName()));

        Sequence sequence = getSourceSequence();

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

    @Override
    public void shutDown() throws ReaderException {
        // nothing to do here
    }
}
