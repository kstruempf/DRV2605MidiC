package helper;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class FileHelper {

    /**
     * Get's a collection of midi files in a specified directory.
     * Subdirectories will be ignored.
     *
     * @param directory The directory that will be searched (non recursive).
     * @return A list of filed with a .mid extension.
     */
    public static List<File> getMidiFilesInDirectory(final File directory) {
        var midiFiles = new LinkedList<File>();
        for (final File fileEntry : Objects.requireNonNull(directory.listFiles())) {
            if (!fileEntry.isDirectory() && fileEntry.getName().endsWith(".mid")) {
                midiFiles.add(fileEntry);
            }
        }
        return midiFiles;
    }

    public static File selectMidiFile(File midiDirectory, InputStream inputStream, PrintStream outStream) {
        outStream.println("Available files:");
        var midiFiles = FileHelper.getMidiFilesInDirectory(midiDirectory);
        var scanner = new Scanner(inputStream);
        int i = 0;

        midiFiles.sort(File::compareTo);

        for (File midiFile : midiFiles) {
            outStream.printf("\t[%d] %s\n", i, midiFile.getName());
            i++;
        }

        outStream.print("Select a midi file to read: ");
        int userInput;
        try {
            userInput = scanner.nextInt();
        } catch (InputMismatchException e) {
            outStream.println("Invalid input");
            return null;
        }
        if (userInput < 0 || userInput >= midiFiles.size()) {
            outStream.println("No file selected");
            return null;
        }

        return midiFiles.get(userInput);
    }
}
