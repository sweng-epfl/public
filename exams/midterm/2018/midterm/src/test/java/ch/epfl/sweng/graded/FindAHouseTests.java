package ch.epfl.sweng.graded;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.AddressNotFoundException;
import ch.epfl.sweng.House;
import ch.epfl.sweng.InvalidAddressException;
import ch.epfl.sweng.graded.DatabaseTests;
import ch.epfl.sweng.graded.grading.GradedCategory;
import ch.epfl.sweng.graded.grading.GradedTest;
import ch.epfl.sweng.graded.grading.JUnitGradeSheetTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitGradeSheetTestRunner.class)
@GradedCategory(name = "Q1 : `Find A House`",
                description = "These tests check whether your implementation" +
                              " of findAHouse returns valid results")
public class FindAHouseTests extends DatabaseTests {
    private void checkHouse(House h, int population,
                            String name, String phone,
                            List<String> prefix) {
        assertEquals("Check house name of house " +
                     prefix.toString() + " number " +
                     h.getName(),
                     name,
                     h.getName());
        assertEquals("Check phone indicator of house " + prefix.toString() +
                     " number " + h.getName(),
                     phone,
                     h.getLandLinePhone());
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "findHouse should throw " + 
                       " invalidAddress on address size > 4",
                points = 1)
    public void testFindHouseDB() throws InvalidAddressException,
                                         AddressNotFoundException {
        moi.findHouse(fiveAddr);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "findHouse should throw invalidAddress " +
                       "for a null address",
                points = 1)
    public void testFindHouseNullAddr()
            throws InvalidAddressException, AddressNotFoundException {
        House r = moi.findHouse(null);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "findHouse should throw invalidAddress when given " + 
                       "a list of all null String values",
                points = 1)
    public void testFindHouseDBNull() throws InvalidAddressException,
                                             AddressNotFoundException {
        moi.findHouse(nulledList);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "findHouse should throw invalidAddress when given " +
                       "a list of valid Strings and null values " +
                       "e.g. [\"blah\", null, null, null]",
                points = 1)
    public void testFindHouseDBMixNull() throws InvalidAddressException,
                                                AddressNotFoundException {
        moi.findHouse(mixNullList);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "findHouse should throw invalidAddress when passed " +
                       "a list of all empty strings",
                points = 1)
    public void testFindHouseDBEmpty() throws InvalidAddressException,
                                              AddressNotFoundException {
        moi.findHouse(strEmptyList);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "findHouse should throw invalidAddress when passed " +
                       "a list with some empty strings",
                points = 1)
    public void testFindHouseDBMixedEmpty() throws InvalidAddressException,
                                                   AddressNotFoundException {
        moi.findHouse(strMixedEmptyList);
    }

    @Test
    @GradedTest(name = "findHouse should return a correct house when passed " +
                       "an address with very large strings (8KB)",
                points = 1)
    public void testFindHouseDBLen() throws InvalidAddressException,
                                            AddressNotFoundException {
        House r = moi.findHouse(strBigList);
        assertEquals("correct house not found when using large " +
                     "strings in address",
                     r, testHouse);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "findHouse should throw invalidAddress when passed " +
                       "an empty address (list size 0)",
                points = 1)
    public void testFindHouseDBEmptyAddr() throws InvalidAddressException,
                                                  AddressNotFoundException {
        moi.findHouse(emptyAddr);
    }

    @Test(expected = AddressNotFoundException.class)
    @GradedTest(name = "findHouse should throw AddressNotFound when given " +
                       "non-existent full address",
                points = 1)
    public void testFindHouseNotInDB() throws InvalidAddressException,
                                              AddressNotFoundException {
        List<String> address = new ArrayList<>(sionPrefix);
        address.add("9999");
        House r = moi.findHouse(address);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "findHouse should throw invalid address for an " + 
                       "existing prefix address",
                points = 1)
    public void testFindHouseNotInDBWithPartial() throws InvalidAddressException,
                                                         AddressNotFoundException {
        // partial addresses are invalid for finding houses
        House r = moi.findHouse(sionPrefix);
    }

    @Test(expected = AddressNotFoundException.class)
    @GradedTest(name = "findHouse should throw address not found for an " +
                       "non-existing prefix address",
                points = 1)
    public void testFindHouseInDBWithPartial() throws InvalidAddressException,
                                                      AddressNotFoundException {
        // partial addresses are invalid for finding houses
        List<String> address = new ArrayList<>(sionPrefix);
        address.set(2, "notexistingaddress");
        House r = moi.findHouse(address);
    }

    @Test
    @GradedTest(name = "findHouse should return the correct house", points = 1)
    public void correctlyFindHouses() throws InvalidAddressException,
                                             AddressNotFoundException {
        checkCity(lausannePrefix, lausanneSize, lausannePhone);
        checkCity(renensPrefix, renensSize, renensPhone);
        checkCity(lutryPrefix, lutrySize, lutryPhone);

        checkCity(genevaPrefix, genevaSize, genevaPhone);
        checkCity(carougePrefix, carougeSize, carougePhone);

        checkCity(sionPrefix, sionSize, sionPhone);
    }

    private void checkCity(List<String> prefix, int size, String phone)
        throws InvalidAddressException, AddressNotFoundException {
        for (int i = 0; i < size; i++) {
            String houseName = Integer.toString(i);
            List<String> address = new ArrayList<>(prefix);
            address.add(houseName);
            House h = moi.findHouse(address);
            checkHouse(h, i, houseName, phone + i, prefix);
        }
    }
}
