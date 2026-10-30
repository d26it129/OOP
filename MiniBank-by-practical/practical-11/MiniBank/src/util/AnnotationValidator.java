package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import model.annotation.MaxLength;
import model.annotation.Positive;

public class AnnotationValidator {

    // Reads the annotations of every field (including inherited ones) and returns the error messages.
    public static String[] validate(Object obj) {
        List<String> errors = new ArrayList<>();
        for (Class<?> c = obj.getClass(); c != null && c != Object.class; c = c.getSuperclass()) {
            for (Field field : c.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);

                    if (field.isAnnotationPresent(Positive.class)
                            && value instanceof Number n && n.doubleValue() <= 0) {
                        errors.add(field.getAnnotation(Positive.class).message());
                    }
                    if (field.isAnnotationPresent(MaxLength.class)
                            && value instanceof String s
                            && s.length() > field.getAnnotation(MaxLength.class).value()) {
                        errors.add("length must be <= " + field.getAnnotation(MaxLength.class).value());
                    }
                } catch (IllegalAccessException e) {
                    errors.add("cannot read field " + field.getName());
                }
            }
        }
        return errors.toArray(new String[0]);
    }
}
