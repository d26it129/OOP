import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class LogAnalyzer {
    public static void main(String[] args) throws IOException {
        Path dir = Files.createTempDirectory("logs");
        Files.write(dir.resolve("app1.log"), List.of("INFO start", "ERROR disk full", "INFO ok"));
        Files.write(dir.resolve("app2.log"), List.of("ERROR timeout", "ERROR login failed", "INFO bye"));
        Files.write(dir.resolve("app3.log"), List.of("INFO nothing here"));

        int totalLines = 0, errorLines = 0;
        try (DirectoryStream<Path> files = Files.newDirectoryStream(dir, "*.log")) {
            java.util.List<Path> sorted = new java.util.ArrayList<>();
            files.forEach(sorted::add);
            java.util.Collections.sort(sorted);
            for (Path p : sorted) {
                BasicFileAttributes attrs = Files.readAttributes(p, BasicFileAttributes.class);
                System.out.println(p.getFileName() + "  size = " + attrs.size() + " bytes");
                for (String line : Files.readAllLines(p)) {
                    totalLines++;
                    if (line.contains("ERROR")) errorLines++;
                }
            }
        }
        System.out.println("Total lines: " + totalLines);
        System.out.println("Lines containing ERROR: " + errorLines);
    }
}
