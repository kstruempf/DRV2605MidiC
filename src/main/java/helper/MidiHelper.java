package helper;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

public class MidiHelper {

    private static final String[] keyNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    public static String getKeyName(int nKeyNumber) {
        if (nKeyNumber < 0 || nKeyNumber > 127) {
            return null;
        } else {
            int nNote = nKeyNumber % 12;
            int nOctave = nKeyNumber / 12;
            return keyNames[nNote] + (nOctave - 1);
        }
    }

    public static String getReadable(MidiMessage midiMessage) {
        return String.format("[%d] [%d %d]", midiMessage.getStatus(), midiMessage.getMessage()[0], midiMessage.getMessage()[1]);
    }

    public static String getReadable(ShortMessage message) {
        if (message.getCommand() == ShortMessage.NOTE_ON || message.getCommand() == ShortMessage.NOTE_OFF) {
            return String.format("[%d] [%d (%s) %d]", message.getCommand(), message.getData1(), getKeyName(message.getData1()), message.getData2());
        }
        return String.format("[%d] [%d %d]", message.getCommand(), message.getData1(), message.getData2());
    }
}
