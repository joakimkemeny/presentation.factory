package se.joakimkemeny.demo.factory.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class AnnotationUtils {

    private AnnotationUtils() {
    }

    public static <A extends Annotation> A findAnnotation(Method method, Class<A> annotationType) {
        return org.springframework.core.annotation.AnnotationUtils.findAnnotation(method, annotationType);
    }

    public static Annotation[][] getParameterAnnotations(Method method) {

        Annotation[][] annotations = method.getParameterAnnotations();
        Class<?> cl = method.getDeclaringClass();

        while (annotations == null) {
            cl = cl.getSuperclass();
            if (cl == null || cl == Object.class) {
                break;
            }

            try {
                Method equivalentMethod = cl.getDeclaredMethod(method.getName(), method.getParameterTypes());
                annotations = equivalentMethod.getParameterAnnotations();
            } catch (NoSuchMethodException e) {
                // We're done...
            }
        }

        return annotations;
    }
}
