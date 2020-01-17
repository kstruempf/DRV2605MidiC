package reader.impl;

import converter.ValueConverter;
import exceptions.ReaderException;
import writer.IWriter;

import javax.sound.midi.*;
import java.io.File;
import java.util.logging.Logger;

public class SequencedMidiFileReader extends MidiFileReader {
    private static final Logger logger = Logger.getLogger(SequencedMidiFileReader.class.getName());

    private final ValueConverter<MidiMessage> valueConverter;
    private final IWriter writer;

    private Sequencer sequencer;

    public SequencedMidiFileReader(File source, ValueConverter<MidiMessage> valueConverter, IWriter writer) {
        super(source);
        this.valueConverter = valueConverter;
        this.writer = writer;
    }

    @Override
    public void initialize() throws ReaderException {
        logger.info("Initializing file reader...");

        try {
            sequencer = MidiSystem.getSequencer();
        } catch (MidiUnavailableException e) {
            logger.info(e.getMessage());
            throw new ReaderException("Failed to get sequencer:", e);
        }

        if (!sequencer.isOpen()) {
            try {
                sequencer.open();
            } catch (MidiUnavailableException e) {
                logger.info(e.getMessage());
                throw new ReaderException("Failed to open sequencer", e);
            }
        }

        try {
            sequencer.setSequence(getSourceSequence());
        } catch (InvalidMidiDataException e) {
            logger.info(e.getMessage());
            throw new ReaderException("Failed to set sequence", e);
        }

        logger.info("Sequenced file reader ready to do stuff.");
    }

    @Override
    public void readAll() throws ReaderException {
        if (sequencer == null) {
            throw new ReaderException("Reader not initialized correctly");
        }
    }

    @Override
    public void shutDown() throws ReaderException {
        if (sequencer != null) {
            sequencer.close();
        }
    }
}
