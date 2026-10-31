import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class VersionChecker {

    public static void main(String[] args) throws Exception {

        // Шаг 1–3. Проверка MyAnnotation
        System.out.println("=== MyAnnotation ===");

        Method method = Test.class.getDeclaredMethod("myMethod");

        MyAnnotation annotation =
                method.getAnnotation(MyAnnotation.class);

        if (annotation != null) {
            System.out.println("Значение аннотации: "
                    + annotation.value());
        } else {
            System.out.println("Аннотация отсутствует");
        }

        // Шаг 4–5. Проверка Deprecated
        System.out.println("\n=== Deprecated ===");

        Example example = new Example();
        example.oldMethod();

        // Проверка версий методов
        System.out.println("\n=== Version ===");

        checkVersions(Software.class);

        // Проверка класса без аннотаций
        System.out.println("\n=== EmptySoftware ===");

        checkVersions(EmptySoftware.class);
    }

    public static void checkVersions(Class<?> clazz) {

        Method[] methods = clazz.getDeclaredMethods();

        Arrays.sort(methods, Comparator.comparing(Method::getName));

        int annotatedCount = 0;
        int notAnnotatedCount = 0;

        for (Method method : methods) {

            Version version =
                    method.getAnnotation(Version.class);

            if (version != null) {

                annotatedCount++;

                System.out.println(
                        "Method: " + method.getName()
                        + ", Version: " + version.number()
                        + ", Author: " + version.author()
                );

            } else {

                notAnnotatedCount++;
            }
        }

        System.out.println(
                "Количество методов с @Version: "
                        + annotatedCount
        );

        System.out.println(
                "Количество методов без @Version: "
                        + notAnnotatedCount
        );

        if (annotatedCount == 0) {
            System.out.println(
                    "В классе нет методов с аннотацией @Version"
            );
        }
    }
}
