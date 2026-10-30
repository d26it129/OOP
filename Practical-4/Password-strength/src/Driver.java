public class Driver {
    public static void main(String[] args) {
        String[] tests = { "abc", "abcdefgh", "Abcdefg1", "Abcd1234!" };
        for (String pw : tests) {
            System.out.println("Password: " + pw);
            System.out.println("  length >= 8 : " + PasswordChecker.longEnough(pw));
            System.out.println("  uppercase   : " + PasswordChecker.hasUpper(pw));
            System.out.println("  digit       : " + PasswordChecker.hasDigit(pw));
            System.out.println("  special char: " + PasswordChecker.hasSpecial(pw));
            System.out.println("  => " + PasswordChecker.strength(pw));
        }
    }
}
