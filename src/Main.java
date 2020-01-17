import com.pi4j.util.Console;
import converter.impl.MidiMessageConverter;
import exceptions.ReaderException;
import exceptions.WriterException;
import helper.OptionsHelper;
import org.apache.commons.cli.*;
import reader.IFileReader;
import reader.impl.SimpleMidiFileReader;
import writer.IWriter;
import writer.impl.I2CWriter;
import writer.impl.LogWriter;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.logging.Logger;

import static helper.FileHelper.selectMidiFile;

public class Main {
    private static final Logger logger = Logger.getLogger(I2CWriter.class.getName());

    private static final InputStream inStream = System.in;
    private static final PrintStream outStream = System.out;

    private static final Console console = new Console();
    private static CommandLine cmd;

    private static File inputFile;
    private static File midiDirectory = new File("./midi");

    private static IWriter writer;
    private static IFileReader reader;

    private static void parseArguments(String[] args) {
        Options options = OptionsHelper.getOptions();

        CommandLineParser parser = new DefaultParser();

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            console.println(e.getMessage());
            printUsage();
            System.exit(1);
            return;
        }

        if (cmd.hasOption("input")) {
            inputFile = new File(cmd.getOptionValue("input"));
            logger.info("Input file specified " + inputFile.getAbsolutePath());
        } else {
            if (cmd.hasOption("directory")) {
                midiDirectory = new File(cmd.getOptionValue("directory"));
                logger.info("Searching directory " + midiDirectory.getAbsolutePath() + " for files");
            }
        }

        if (cmd.hasOption("logonly")) {
            logger.info("Only logging produced values");
            writer = new LogWriter();
        }
    }

    public static void main(String[] args) throws ReaderException, WriterException {
        console.title("Controller for DRV2605");
        console.promptForExit();

        parseArguments(args);

        if (!midiDirectory.isDirectory()) {
            console.println("Directory %s not found", midiDirectory);
            System.exit(1);
        }

        if (inputFile == null) {
            // Run file selection if no file specified in program arguments
            inputFile = selectMidiFile(midiDirectory, inStream, outStream);
        }

        logger.info(String.format("Selected file %s\n", inputFile.getName()));

        if (writer == null) {
            writer = new I2CWriter();
        }

        writer.initialize();

        if (cmd.hasOption("simplereader")) {
            logger.info("Using simple file reader");
            reader = new SimpleMidiFileReader(inputFile, new MidiMessageConverter(), writer);
        }

        // TODO replace with proper reader
        reader = new SimpleMidiFileReader(inputFile, new MidiMessageConverter(), writer);

        reader.readAll();

        console.goodbye();
    }


    private static void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("drv2605-midi", OptionsHelper.getOptions());
    }

}
