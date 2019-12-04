package ch.epfl.sweng.graded;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ch.epfl.sweng.AddressNotFoundException;
import ch.epfl.sweng.InvalidAddressException;
import ch.epfl.sweng.graded.grading.GradedCategory;
import ch.epfl.sweng.graded.grading.GradedTest;
import ch.epfl.sweng.graded.grading.JUnitGradeSheetTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitGradeSheetTestRunner.class)
@GradedCategory(name = "Q3 : `Message Broadcast`",
                description = "These tests check whether your implementation " +
                              "of the PrintAddressVisitor is correct")
public class MessageBroadcastTests extends DatabaseTests {


    private final ByteArrayOutputStream outContent =
        new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private Set<Set<String>> getCityAddressSet(List<String> prefix, int size) {
        Set<Set<String>> res = new HashSet<>();
        for (int i = 0; i < size; i++) {
            List<String> address = new ArrayList<>(prefix);
            address.add(Integer.toString(i));
            res.add(new HashSet<>(address));
        }
        return res;
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "printAddresses should throw invalidAddress on " +
                       "address size > 4",
                points = 1)
    public void testPrintAddressesDB() throws InvalidAddressException,
                                              AddressNotFoundException {
        moi.printAddresses(fiveAddr);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "printAddresses should throw invalidAddress for " +
                       "a null address",
                points = 1)
    public void testPrintAddressesNullAddr() throws InvalidAddressException,
                                                    AddressNotFoundException {
        moi.printAddresses(null);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "printAddresses should throw invalidAddress when " + 
                       "given a list of all null String values",
                points = 1)
    public void testPrintAddressesDBNull() throws InvalidAddressException,
                                                  AddressNotFoundException {
        moi.printAddresses(nulledList);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "printAddresses should throw invalidAddress when " +
                       "given a list of valid Strings and null values " +
                       "e.g. [\"blah\", null, null, null]",
                points = 1)
    public void testPrintAddressesDBMixNull() throws InvalidAddressException,
                                                     AddressNotFoundException {
        moi.printAddresses(mixNullList);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "printAddresses should throw invalidAddress when " +
                       "passed a list of all empty strings",
                points = 1)
    public void testPrintAddressesDBEmpty() throws InvalidAddressException,
                                                   AddressNotFoundException {
        moi.printAddresses(strEmptyList);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "printAddresses should throw invalidAddress when " +
                       "passed a list with some empty strings",
                points = 1)
    public void testPrintAddressesDBMixedEmpty() throws InvalidAddressException,
                                                        AddressNotFoundException {
        moi.printAddresses(strMixedEmptyList);
    }

    @Test
    @GradedTest(name = "printAddresses should print a correct output when " +
                       "passed an address with very large strings (8KB)",
                points = 1)
    public void testPrintAddressesDBLen() throws InvalidAddressException,
                                                 AddressNotFoundException {
        checkGivenHouseBroadcast(lausannePrefix, lausanneSize);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "printAddresses should throw invalidAddress when " +
                       "passed an empty address (list size 0)",
                points = 1)
    public void testPrintAddressesDBEmptyAddr()
        throws InvalidAddressException, AddressNotFoundException {
        moi.printAddresses(emptyAddr);
    }

    @Test(expected = AddressNotFoundException.class)
    @GradedTest(name = "printAddresses should throw AddressNotFound when " +
                       "given non-existent full address",
                points = 1)
    public void testPrintAddressesNotInDB() throws InvalidAddressException,
                                                   AddressNotFoundException {
        List<String> address = new ArrayList<>(sionPrefix);
        address.add("9999");
        moi.printAddresses(address);
    }

    @Test(expected = AddressNotFoundException.class)
    @GradedTest(name = "printAddresses should throw address not found for " +
                       "an non-existing prefix address",
                points = 1)
    public void testPrintAddressesInDBWithPartial()
        throws InvalidAddressException, AddressNotFoundException {
        // partial addresses are invalid for finding houses
        List<String> address = new ArrayList<>(sionPrefix);
        address.set(2, "notexistingaddress");
        moi.printAddresses(address);
    }

    @Test
    @GradedTest(name = "Calling printAddresses on a house should print the " +
                       "address of the house",
                points = 1)
    public void houseBroadcastTest() throws InvalidAddressException,
                                            AddressNotFoundException,
                                            IOException {
        checkGivenHouseBroadcast(lausannePrefix, lausanneSize);
        checkGivenHouseBroadcast(renensPrefix, renensSize);
        checkGivenHouseBroadcast(lutryPrefix, lutrySize);
        checkGivenHouseBroadcast(genevaPrefix, genevaSize);
        checkGivenHouseBroadcast(carougePrefix, carougeSize);
        checkGivenHouseBroadcast(sionPrefix, sionSize);
    }

    @Test
    @GradedTest(name = "Calling printAddresses on a city should print the " +
                       "addresses of all houses in the given city (district)",
                points = 1)
    public void cityBroadcastTest() throws InvalidAddressException,
                                           AddressNotFoundException,
                                           IOException {
        checkGivenCityBroadcast(lausannePrefix, lausanneSize);
        checkGivenCityBroadcast(renensPrefix, renensSize);
        checkGivenCityBroadcast(lutryPrefix, lutrySize);
        checkGivenCityBroadcast(genevaPrefix, genevaSize);
        checkGivenCityBroadcast(carougePrefix, carougeSize);
        checkGivenCityBroadcast(sionPrefix, sionSize);
    }

    @Test
    @GradedTest(name = "Calling printAddresses on a canton should print the " +
                       "address of all houses in the given canton (island)",
                points = 1)
    public void cantonBroadcastTest() throws InvalidAddressException,
                                             AddressNotFoundException,
                                             IOException {
        vaudBroadcastTest();
        genevaBroadcastTest();
        valaisBroadcastTest();
    }

    @Test
    @GradedTest(name = "Calling printAddresses on a country should print the " +
                       "address of all houses in the given country",
                points = 1)
    public void countryBroadcastTest() throws InvalidAddressException,
                                              AddressNotFoundException,
                                              IOException {
        moi.printAddresses(Collections.singletonList("Switzerland"));
        Set<Set<String>> printedAddress =
            addressStringToSet(outContent.toString());
        Set<Set<String>> expected = getCityAddressSet(lausannePrefix,
                                                      lausanneSize);
        expected.addAll(getCityAddressSet(renensPrefix, renensSize));
        expected.addAll(getCityAddressSet(lutryPrefix, lutrySize));
        expected.addAll(getCityAddressSet(genevaPrefix, genevaSize));
        expected.addAll(getCityAddressSet(carougePrefix, carougeSize));
        expected.addAll(getCityAddressSet(sionPrefix, sionSize));
        List<String> a = strBigList.subList(0, 2);
        expected.addAll(getCityAddressSet(a, 1));


        assertEquals("Check print addresses in Switzerland",
                     expected, printedAddress);
        outContent.reset();
    }

    private void vaudBroadcastTest() throws InvalidAddressException,
                                            AddressNotFoundException {
        moi.printAddresses(Arrays.asList("Switzerland", "Vaud"));
        Set<Set<String>> printedAddress =
            addressStringToSet(outContent.toString());
        Set<Set<String>> expected = getCityAddressSet(lausannePrefix,
                                                      lausanneSize);
        expected.addAll(getCityAddressSet(renensPrefix, renensSize));
        expected.addAll(getCityAddressSet(lutryPrefix, lutrySize));
        assertEquals("Check print addresses in Vaud",
                     expected, printedAddress);
        outContent.reset();
    }

    private void genevaBroadcastTest() throws InvalidAddressException,
                                              AddressNotFoundException {
        moi.printAddresses(Arrays.asList("Switzerland", "Geneva"));
        Set<Set<String>> printedAddress =
            addressStringToSet(outContent.toString());
        Set<Set<String>> expected = getCityAddressSet(genevaPrefix, genevaSize);
        expected.addAll(getCityAddressSet(carougePrefix, carougeSize));
        assertEquals("Check print addresses in Geneva",
                     expected, printedAddress);
        outContent.reset();
    }

    private void valaisBroadcastTest() throws InvalidAddressException,
                                              AddressNotFoundException {
        moi.printAddresses(Arrays.asList("Switzerland", "Valais"));
        Set<Set<String>> printedAddress =
            addressStringToSet(outContent.toString());
        Set<Set<String>> expected = getCityAddressSet(sionPrefix, sionSize);
        assertEquals("Check print addresses in Valais",
                     expected, printedAddress);
        outContent.reset();
    }

    private Set<Set<String>> addressStringToSet(String s) {
        Set<String> lines =
            new HashSet<>(Arrays.asList(s.split(System.lineSeparator())));
        return lines.stream().map(l -> new HashSet<>(Arrays.
                                                     asList(l.split(", ")))).
                                       collect(Collectors.toSet());
    }

    private void checkGivenCityBroadcast(List<String> prefix, int size)
        throws InvalidAddressException, AddressNotFoundException {
        Set<Set<String>> expected = getCityAddressSet(prefix, size);
        moi.printAddresses(prefix);
        Set<Set<String>> printedAddress =
            addressStringToSet(outContent.toString());
        assertEquals("Check print addresses in " + prefix.toString(),
                     expected, printedAddress);
        outContent.reset();
    }

    private void checkGivenHouseBroadcast(List<String> prefix, int size)
        throws InvalidAddressException, AddressNotFoundException {
        for (int i = 0; i < size; i++) {
            List<String> address = new ArrayList<>(prefix);
            address.add(Integer.toString(i));
            Set<String> expected = new HashSet<>(address);
            moi.printAddresses(address);
            Set<String> printedAddress =
                new HashSet<>(Arrays.asList(outContent.
                                            toString().
                                            replace(System.lineSeparator(), "").
                                            split(", ")));
            assertEquals("Check print addresses of house " +
                         prefix.toString() +
                         " number " +
                         i,
                         expected, printedAddress);
            outContent.reset();
        }
    }
}
