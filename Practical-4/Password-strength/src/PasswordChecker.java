public class PasswordChecker {

    static boolean longEnough(String pw)  { return pw.length() >= 8; }
    static boolean hasUpper(String pw)    { return pw.matches(".*[A-Z].*"); }
    static boolean hasDigit(String pw)    { return pw.matches(".*[0-9].*"); }
    static boolean hasSpecial(String pw)  { return pw.matches(".*[^A-Za-z0-9].*"); }

    static int score(String pw) {
        int s = 0;
        if (longEnough(pw)) s++;
        if (hasUpper(pw))   s++;
        if (hasDigit(pw))   s++;
        if (hasSpecial(pw)) s++;
        return s;
    }

    public static String strength(String pw) {
        int s = score(pw);
        if (s <= 1) return "Weak";
        if (s <= 3) return "Medium";
        return "Strong";
    }
}
