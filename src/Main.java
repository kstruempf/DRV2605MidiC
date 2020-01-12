import converter.impl.MidiMessageConverter;
import exceptions.ReaderException;
import reader.IFileReader;
import reader.impl.SimpleMidiFileReader;
import writer.IWriter;
import writer.impl.ConsoleWriter;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;

import static helper.FileHelper.selectMidiFile;

public class Main {

    private static final InputStream inStream = System.in;
    private static final PrintStream outStream = System.out;
    private static final PrintStream errStream = System.err;

    public static void main(String[] args) throws ReaderException {
        var midiDirectory = new File(args[0]);

        if (args.length != 1) {
            printUsage(outStream);
            System.exit(1);
        }

        outStream.println(midiDirectory.getAbsolutePath());

        if (!midiDirectory.isDirectory()) {
            errStream.printf("Directory %s not found", midiDirectory);
            System.exit(1);
        }

        var selectedFile = selectMidiFile(midiDirectory, inStream, outStream);

        outStream.printf("Selected file %s\n", selectedFile.getName());

        IWriter writer = new ConsoleWriter(outStream);

        IFileReader reader = new SimpleMidiFileReader(selectedFile, new MidiMessageConverter(), writer, outStream);

        setupShutdownHook(reader);

        reader.readAll();

        outStream.println("Done reading.");
    }

    private static void setupShutdownHook(IFileReader reader) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            outStream.println("Shutting down...");
            reader.stop();
        }));
    }

    private static void printUsage(PrintStream stream) {
        stream.print("program directory");
    }

}
