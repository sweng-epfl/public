// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

package internal.reflect.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * (For SwEng check tests only. Students don't need to use this.)
 *
 * This typetoken allows you to extract a Type object from a parameterized type.<br>
 *
 * For example, if you need a {@link java.lang.reflect.Type} object for a {@link java.util.function.Consumer<String>},
 * you can create a new subclass of {@link TypeToken<java.util.function.Consumer<String>>} and use
 * {@link TypeToken#getType()} on it to get the type.
 *
 * Note: this is a bit "hacky". In Java, because of type erasure, you need to extend a generic class in order to be
 * able to get the value of the type parameter. Therefore, it is very important that this class stays abstract and
 * that you subclass it (even with anonymous subclasses) whenever you need it :)
 *
 * @param <T> the parameterized type for which you need to extract a java Type
 */
public abstract class TypeToken<T> {
    private ParameterizedType getGenericSuperclass() {
        return (ParameterizedType) this.getClass().getGenericSuperclass();
    }

    /**
     * Get the Type representation of the type passed as a generic parameter to this class
     */
    public Type getType() {
        return getGenericSuperclass().getActualTypeArguments()[0];
    }
}
