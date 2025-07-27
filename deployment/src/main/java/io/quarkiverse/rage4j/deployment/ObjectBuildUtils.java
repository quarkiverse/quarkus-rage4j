package io.quarkiverse.rage4j.deployment;

public class ObjectBuildUtils {

    public static Object buildObject(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
