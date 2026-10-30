package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportGenerator {

    // Walks the logs folder, adds up deposits and withdrawals and writes a summary file.
    public static String generate(Path logsDir, Path reportFile) throws IOException {
        long totalDeposits = 0, totalWithdrawals = 0;
        int files = 0;
        StringBuilder details = new StringBuilder();

        List<Path> logs = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsDir, "*.log")) {
            for (Path p : stream) logs.add(p);
        }
        Collections.sort(logs);

        for (Path log : logs) {
            BasicFileAttributes attrs = Files.readAttributes(log, BasicFileAttributes.class);
            if (attrs.size() == 0) continue;                         // skip empty files
            files++;
            details.append(String.format("  %-14s %4d bytes   modified %s%n",
                    log.getFileName(), attrs.size(), attrs.lastModifiedTime()));

            try (BufferedReader reader = Files.newBufferedReader(log)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length != 3) continue;                 // ignore malformed lines
                    long amount = Long.parseLong(parts[2]);
                    if (parts[0].equals("DEPOSIT")) totalDeposits += amount;
                    else if (parts[0].equals("WITHDRAW")) totalWithdrawals += amount;
                }
            }
        }

        String report = "END-OF-DAY REPORT\n"
                + "Log files read    : " + files + "\n" + details
                + "Total deposits    : " + totalDeposits + "\n"
                + "Total withdrawals : " + totalWithdrawals + "\n"
                + "Net change        : " + (totalDeposits - totalWithdrawals) + "\n";
        if (reportFile.getParent() != null) Files.createDirectories(reportFile.getParent());
        Files.writeString(reportFile, report);
        return report;
    }
}
