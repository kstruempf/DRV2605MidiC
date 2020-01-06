package converter.impl;

import converter.ValueConverter;
import dto.Value;
import helper.MidiHelper;

import javax.sound.midi.MidiMessage;
import java.io.PrintStream;

public class MidiMessageConverter implements ValueConverter<MidiMessage> {
    private final PrintStream outStream = System.out;

    @Override
    public Value convert(MidiMessage midiMessage) {
        outStream.printf("- Converting %s\n", MidiHelper.getReadable(midiMessage));

        return new Value(midiMessage.getMessage()[0]);
    }
}
