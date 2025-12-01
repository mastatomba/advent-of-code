package nl.schoutens.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<String> fileToList(String filePath) throws IOException {
        var path = Path.of(filePath);
        var lines = new ArrayList<>(Files.readAllLines(path));
        return lines;
    }
}
