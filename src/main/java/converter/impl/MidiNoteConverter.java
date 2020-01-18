package converter.impl;

import converter.ValueConverter;
import dto.Value;
import enumeration.ValueType;
import helper.MidiHelper;

import javax.sound.midi.ShortMessage;
import java.util.logging.Logger;

/**
 * Assumes to only receive NOTE_ON or NOTE_OFF messages.
 */
public class MidiNoteConverter implements ValueConverter<ShortMessage> {
    private static final Logger logger = Logger.getLogger(GeneralMessageConverter.class.getName());


    @Override
    public Value convert(ShortMessage shortMessage) {

        var value = new Value(
                (long) shortMessage.getData2(), // use velocity as amplitude value
                noteToAddress(shortMessage.getData1()), // map the midi note to a I2C address
                shortMessage.getCommand() == ShortMessage.NOTE_ON ? ValueType.START : ValueType.STOP
        );

        if (shortMessage.getData2() == 0) {
            // also interpreting velocity 0 as NOTE_OFF
            value.setValueType(ValueType.STOP);
        }

        logger.info("Converting " + MidiHelper.getReadable(shortMessage) + " to value " + value);

        return value;
    }

    private Byte noteToAddress(int data1) {
        return (byte) (data1 - 36);
    }
}
