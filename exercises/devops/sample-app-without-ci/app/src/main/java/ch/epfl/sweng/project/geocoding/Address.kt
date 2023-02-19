package ch.epfl.sweng.project.geocoding

import java.lang.StringBuilder

/**
 * This class represents a real world address. The `toString` method enables to get a
 * representation of this address.
 *
 *
 * Solution notes:
 * - This data class could contain way more fields - and in reality you could possibly even use the
 * Android one, depending on how your project works.
 */
class Address(private val addressLines: List<String>) {
    private fun toString(separator: String?): String {
        val buffer = StringBuilder()
        for (line in addressLines) buffer.append(line).append(separator)
        return buffer.toString()
    }

    override fun toString(): String {
        return this.toString("\n")
    }
}