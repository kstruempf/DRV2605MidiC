package converter.impl;

import converter.ValueConverter;
import dto.Value;
import helper.MidiHelper;

import javax.sound.midi.MidiMessage;
import java.util.logging.Logger;

public class MidiMessageConverter implements ValueConverter<MidiMessage> {
    private static final Logger logger = Logger.getLogger(MidiMessageConverter.class.getName());

    @Override
    public Value convert(MidiMessage midiMessage) {
        var newValue = new Value((long) midiMessage.getMessage()[0] * -1 / 3);

        logger.info(String.format("Converting Midi Note: %s to value: %s", MidiHelper.getReadable(midiMessage), newValue.toString()));

        return newValue;
    }
}
