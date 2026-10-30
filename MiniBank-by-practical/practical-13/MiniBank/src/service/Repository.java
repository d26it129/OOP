package service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import model.annotation.Id;

// One reusable repository for ANY entity type that has an @Id field.
public class Repository<T> {

    private final HashMap<String, T> store = new HashMap<>();

    public synchronized T save(T entity) {
        store.put(idOf(entity), entity);
        return entity;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) save(item);
    }

    public synchronized T findById(String id) {
        return store.get(id);
    }

    public synchronized ArrayList<T> findAll() {
        return new ArrayList<>(store.values());
    }

    public synchronized boolean delete(String id) {
        return store.remove(id) != null;
    }

    public synchronized int size() {
        return store.size();
    }

    // reflection: find the field marked with @Id and read its value
    private String idOf(T entity) {
        for (Class<?> c = entity.getClass(); c != null; c = c.getSuperclass()) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    try {
                        field.setAccessible(true);
                        return String.valueOf(field.get(entity));
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException("Cannot read @Id field", e);
                    }
                }
            }
        }
        throw new IllegalArgumentException(entity.getClass().getSimpleName() + " has no @Id field");
    }

    // generic method with a bounded type parameter: only Number types are accepted
    public static <N extends Number> double average(N[] values) {
        double sum = 0;
        for (N v : values) sum += v.doubleValue();
        return values.length == 0 ? 0 : sum / values.length;
    }
}
