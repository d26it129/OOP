public class ChatFilter {

    public static String filter(String[] logs, String keyword) {
        StringBuilder report = new StringBuilder();
        int matches = 0;
        for (String line : logs) {
            String[] parts = line.split(" ", 3);
            if (parts.length < 3) continue;               // malformed line
            String time = parts[0], user = parts[1], message = parts[2];
            if (message.toLowerCase().contains(keyword.toLowerCase())) {
                matches++;
                report.append(time).append(' ').append(user).append(": ").append(message).append('\n');
            }
        }
        return "Matches: " + matches + "\n" + report;
    }
}
