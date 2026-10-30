package util;

import java.util.regex.Pattern;

public class Validator {

    // compiled once; ^ and $ (matches()) make the whole string match, not just a part
    private static final Pattern MOBILE = Pattern.compile("^[6-9][0-9]{9}$");
    private static final Pattern EMAIL  = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PAN    = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");
    private static final Pattern IFSC   = Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");
    private static final Pattern NAME   = Pattern.compile("^[A-Za-z][A-Za-z .'-]{1,39}$");
    private static final Pattern AMOUNT = Pattern.compile("^[1-9][0-9]*$");

    private static boolean matches(Pattern p, String s) {
        return s != null && p.matcher(s).matches();
    }

    public static boolean isValidMobile(String s) { return matches(MOBILE, s); }
    public static boolean isValidEmail(String s)  { return matches(EMAIL, s); }
    public static boolean isValidPan(String s)    { return matches(PAN, s); }
    public static boolean isValidIfsc(String s)   { return matches(IFSC, s); }
    public static boolean isValidName(String s)   { return matches(NAME, s); }
    public static boolean isValidAmount(String s) { return matches(AMOUNT, s); }
}
