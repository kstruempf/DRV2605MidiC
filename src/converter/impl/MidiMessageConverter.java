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
        outStream.printf("Converting Midi Note: %s\n", MidiHelper.getReadable(midiMessage));

        var newValue = new Value((long) midiMessage.getMessage()[0] * -1 / 3);

        outStream.println("Converted to " + newValue);

        return newValue;
    }
}
