import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.FIELD)
@interface NotBlank { }

@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.FIELD)
@interface MaxLength { int value(); }

class SignupForm {
    @NotBlank @MaxLength(10) String username;
    @NotBlank String email;
    @MaxLength(8) String nickname;

    SignupForm(String username, String email, String nickname) {
        this.username = username; this.email = email; this.nickname = nickname;
    }
}

public class FormValidator {

    static List<String> check(Object obj) throws IllegalAccessException {
        List<String> errors = new ArrayList<>();
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            String value = (String) f.get(obj);
            if (f.isAnnotationPresent(NotBlank.class) && (value == null || value.isBlank()))
                errors.add(f.getName() + " must not be blank");
            if (f.isAnnotationPresent(MaxLength.class) && value != null) {
                int max = f.getAnnotation(MaxLength.class).value();
                if (value.length() > max)
                    errors.add(f.getName() + " longer than " + max + " characters");
            }
        }
        return errors;
    }

    public static void main(String[] args) throws Exception {
        SignupForm good = new SignupForm("riya", "riya@mail.com", "ri");
        SignupForm bad  = new SignupForm("a_very_long_username", " ", "nickname123");
        System.out.println("good form errors: " + check(good));
        System.out.println("bad form errors : " + check(bad));
    }
}
