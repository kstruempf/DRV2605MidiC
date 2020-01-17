package converter;

import dto.Value;

public interface ValueConverter<T> {
    /**
     * Converts a given data type to a value dto.
     *
     * @param t the data type to be converted
     */
    Value convert(T t);
}
