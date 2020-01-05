import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;

import static helper.StaticFileHelper.selectMidiFile;

public class Main {

    private static final InputStream inStream = System.in;
    private static final PrintStream outStream = System.out;
    private static final PrintStream errStream = System.err;

    public static void main(String[] args) {
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

        if (selectedFile == null) {
            System.exit(1);
        }

        outStream.printf("Selected file %s\n", selectedFile.getName());
    }

    private static void printUsage(PrintStream stream) {
        stream.print("program directory");
    }

}
