import com.pi4j.util.Console;
import converter.impl.GeneralMessageConverter;
import converter.impl.MidiNoteConverter;
import exceptions.ReaderException;
import exceptions.WriterException;
import helper.OptionsHelper;
import org.apache.commons.cli.*;
import reader.IFileReader;
import reader.impl.SequencedMidiFileReader;
import reader.impl.SimpleMidiFileReader;
import writer.IWriter;
import writer.impl.I2CWriter;
import writer.impl.LogWriter;
import writer.impl.MultiI2CWriter;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import static helper.FileHelper.selectMidiFile;

public class Main {
    private static final Logger logger = Logger.getLogger(I2CWriter.class.getName());

    private static final InputStream inStream = System.in;
    private static final PrintStream outStream = System.out;

    private static final Console console = new Console();
    private static CommandLine cmd;

    private static File inputFile;
    private static File midiDirectory = new File("./midi_test_files");

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

    public static void main(String[] args) {
        console.title("Controller for DRV2605");
        console.promptForExit();

        parseArguments(args);

        if (cmd.hasOption("help")) {
            printUsage();
            return;
        }

        if (!midiDirectory.isDirectory()) {
            console.println("Directory %s not found", midiDirectory);
            System.exit(1);
        }

        while (true) {
            if (inputFile == null) {
                // Run file selection if no file specified in program arguments
                inputFile = selectMidiFile(midiDirectory, inStream, outStream);
            }

            if (inputFile == null) {
                break;
            }

            logger.info(String.format("Selected file %s\n", inputFile.getName()));

            if (writer == null) {
                if (cmd.hasOption("simplewriter")) {
                    writer = new I2CWriter();
                } else {
                    writer = new MultiI2CWriter();
                }
            }

            try {
                writer.initialize();
            } catch (WriterException e) {
                logger.log(Level.SEVERE, "Error during writer initialization: " + e.getMessage());
                System.exit(1);
            }

            if (cmd.hasOption("simplereader")) {
                logger.info("Using simple file reader");
                reader = new SimpleMidiFileReader(inputFile, new GeneralMessageConverter(), writer);
            } else {
                reader = new SequencedMidiFileReader(inputFile, new MidiNoteConverter(), writer);
            }

            try {
                reader.initialize();
            } catch (ReaderException e) {
                logger.log(Level.SEVERE, "Error during initialization: " + e.getMessage());
                System.exit(1);
            }

            try {
                reader.readAll();
            } catch (ReaderException e) {
                logger.log(Level.SEVERE, "Error while reading: " + e.getMessage());
                System.exit(1);
            }

            if (cmd.hasOption("input")) {
                break;
            } else {
                inputFile = null;
            }
        }
        if (reader != null) {
            try {
                reader.shutDown();
            } catch (ReaderException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }


    private static void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("drv2605-midi", OptionsHelper.getOptions());
    }

}
