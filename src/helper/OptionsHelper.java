package helper;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class OptionsHelper {

    public static Options getOptions() {
        var options = new Options();

        Option inputFileOption = new Option("i", "input", true, "input file path");
        inputFileOption.setRequired(false);
        options.addOption(inputFileOption);

        Option searchDirectoryOption = new Option("d", "directory", true, "search directory for files");
        searchDirectoryOption.setRequired(false);
        options.addOption(searchDirectoryOption);

        Option useConsoleWriter = new Option("L", "logonly", false, "only log the produced values (use for testing without DR2605)");
        useConsoleWriter.setRequired(false);
        options.addOption(useConsoleWriter);

        Option useSimpleFileReader = new Option("s", "simplereader", false, "uses a simple midi file reader with a fixed read interval of 500 ms");
        useSimpleFileReader.setRequired(false);
        options.addOption(useSimpleFileReader);

        return options;
    }
}
