package reader.impl;

import exceptions.ReaderException;
import reader.IFileReader;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;
import java.io.IOException;

public abstract class MidiFileReader implements IFileReader {
    private final File source;

    protected MidiFileReader(File source) {
        this.source = source;
    }

    protected String getSequenceName() {
        return source.getName();
    }

    protected Sequence getSourceSequence() throws ReaderException {
        Sequence sequence;
        try {
            sequence = MidiSystem.getSequence(source);
        } catch (InvalidMidiDataException | IOException e) {
            throw new ReaderException("Failed to get sequence from source file", e);
        }
        return sequence;
    }
}
