package ch.epfl.sweng.project.geocoding;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * This class represents a real world address. The `toString` method enables to get a
 * representation of this address.
 * <p>
 * Solution notes:
 * - This data class could contain way more fields - and in reality you could possibly even use the
 * Android one, depending on how your project works.
 */
public final class Address {
    private final List<String> addressLines;

    public Address(List<String> addressLines) {
        this.addressLines = addressLines;
    }

    @NonNull
    public String toString(String separator) {
        StringBuilder buffer = new StringBuilder();
        for (String line : addressLines) buffer.append(line).append(separator);

        return buffer.toString();
    }

    @NonNull
    @Override
    public String toString() {
        return this.toString("\n");
    }
}
