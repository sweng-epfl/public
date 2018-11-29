package ch.epfl.sweng.graded;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.text.StyleContext.SmallAttributeSet;

import ch.epfl.sweng.AddressNotFoundException;
import ch.epfl.sweng.CompositeAddressUnit;
import ch.epfl.sweng.House;
import ch.epfl.sweng.InvalidAddressException;
import ch.epfl.sweng.MinistryOfInformation;
import ch.epfl.sweng.graded.grading.GradedCategory;
import ch.epfl.sweng.graded.grading.GradedTest;
import ch.epfl.sweng.graded.grading.JUnitGradeSheetTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitGradeSheetTestRunner.class)
@GradedCategory(name = "Q2 : `Population Census`",
                description = "These tests check whether your " +
                              "implementation of getPopulation " +
                              "returns valid results")
public class PopulationCensusTests extends DatabaseTests {

    private MinistryOfInformation bigMoi;
    private List<String> smallAddress;
    private List<String> bigAddress;

    @Before
    public void initBigDB() throws InvalidAddressException {
        CompositeAddressUnit bigD = new CompositeAddressUnit("Switzerland");
        bigMoi = new MinistryOfInformation(bigD);

        bigAddress = Arrays.asList("Switzerland", "Big Canton",
                                   "Big City", "0");
        int bigSize = Integer.MAX_VALUE;
        String bigPhone = "+41 99 999 99 99";
        bigMoi.addAddress(bigAddress, new House("0", bigSize, bigPhone));

        smallAddress = Arrays.asList("Switzerland", "Small Canton",
                                     "Small City", "0");
        int smallSize = 1;
        String smallPhone = "+41 99 999 00 00";
        bigMoi.addAddress(smallAddress, new House("0", smallSize, smallPhone));
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "getPopulation should throw invalidAddress on " +
                       "address size > 4",
                points = 1)
    public void testGetPopulationDB() throws InvalidAddressException,
                                             AddressNotFoundException {
        moi.getPopulation(fiveAddr);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "getPopulation should throw invalidAddress for " +
                       "a null address",
                points = 1)
    public void testGetPopulationNullAddr() throws InvalidAddressException,
                                                   AddressNotFoundException {
        moi.getPopulation(null);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "getPopulation should throw invalidAddress when " +
                       "given a list of all null String values",
                points = 1)
    public void testGetPopulationDBNull() throws InvalidAddressException,
                                                 AddressNotFoundException {
        moi.getPopulation(nulledList);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "getPopulation should throw invalidAddress when " +
                       "given a list of valid Strings and null values " +
                       "e.g. [\"blah\", null, null, null]",
                points = 1)
    public void testGetPopulationDBMixNull() throws InvalidAddressException,
                                                    AddressNotFoundException {
        moi.getPopulation(mixNullList);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "getPopulation should throw invalidAddress when " +
                       "passed a list of all empty strings",
                points = 1)
    public void testGetPopulationDBEmpty() throws InvalidAddressException,
                                                  AddressNotFoundException {
        moi.getPopulation(strEmptyList);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "getPopulation should throw invalidAddress when " +
                       "passed a list with some empty strings",
                points = 1)
    public void testGetPopulationDBMixedEmpty() throws InvalidAddressException,
                                                       AddressNotFoundException {
        moi.getPopulation(strMixedEmptyList);
    }

    @Test
    @GradedTest(name = "getPopulation should return a correct population " +
                       "when passed an address with very large strings (8KB)",
                points = 1)
    public void testGetPopulationDBLen() throws InvalidAddressException,
                                                AddressNotFoundException {
        int p = moi.getPopulation(strBigList);
        assertEquals("correct population not found when " +
                     "using large strings in address",
                     p, 2);
    }

    @Test(expected = InvalidAddressException.class)
    @GradedTest(name = "getPopulation should throw invalidAddress when " +
                       "passed an empty address (list size 0)",
                points = 1)
    public void testGetPopulationDBEmptyAddr() throws InvalidAddressException,
                                                      AddressNotFoundException {
        moi.getPopulation(emptyAddr);
    }

    @Test(expected = AddressNotFoundException.class)
    @GradedTest(name = "getPopulation should throw AddressNotFound when " +
                       "given non-existent full address",
                points = 1)
    public void testGetPopulationNotInDB() throws InvalidAddressException,
                                                  AddressNotFoundException {
        List<String> address = new ArrayList<>(sionPrefix);
        address.add("9999");
        moi.getPopulation(address);
    }

    @Test(expected = AddressNotFoundException.class)
    @GradedTest(name = "getPopulation should throw address not found for an " +
                       "non-existing prefix address",
                points = 1)
    public void testGetPopulationInDBWithPartial()
        throws InvalidAddressException, AddressNotFoundException {
        // partial addresses are invalid for finding houses
        List<String> address = new ArrayList<>(sionPrefix);
        address.set(2, "notexistingaddress");
        moi.getPopulation(address);
    }

    @Test(expected = Exception.class)
    @GradedTest(name = "getPopulation should not overflow when using MAXINT " +
                       "size values",
                points = 1)
    public void checkBigPopulation() throws InvalidAddressException,
                                            AddressNotFoundException {
        try {
            bigMoi.getPopulation(smallAddress);
        } catch (RuntimeException e) {
            Assert.fail("Check that getPopulation is implemented " +
                        "on MinistryOfInformation");
        }
        bigMoi.getPopulation(Collections.singletonList("Switzerland"));
    }

    @Test
    @GradedTest(name = "A house should have the correct population", points = 1)
    public void checkHousesPopulation() throws InvalidAddressException,
                                               AddressNotFoundException {
        checkGivenHousesPopulation(lausannePrefix, lausanneSize);
        checkGivenHousesPopulation(renensPrefix, renensSize);
        checkGivenHousesPopulation(lutryPrefix, lutrySize);

        checkGivenHousesPopulation(genevaPrefix, genevaSize);
        checkGivenHousesPopulation(carougePrefix, carougeSize);

        checkGivenHousesPopulation(sionPrefix, sionSize);
    }

    @Test
    @GradedTest(name = "A city(district) should have the correct population",
                points = 1)
    public void checkCityPopulation() throws InvalidAddressException,
                                             AddressNotFoundException {
        assertEquals("Check population of city of Lausanne", 36,
                     moi.getPopulation(Arrays.asList("Switzerland",
                                                     "Vaud",
                                                     "Lausanne")));
        assertEquals("Check population of city of Renens", 21,
                     moi.getPopulation(Arrays.asList("Switzerland",
                                                     "Vaud",
                                                     "Renens")));
        assertEquals("Check population of city of Lutry", 10,
                     moi.getPopulation(Arrays.asList("Switzerland",
                                                     "Vaud",
                                                     "Lutry")));

        assertEquals("Check population of city of Geneva", 45,
                     moi.getPopulation(Arrays.asList("Switzerland",
                                                     "Geneva",
                                                     "Geneva")));
        assertEquals("Check population of city of Carouge", 3,
                     moi.getPopulation(Arrays.asList("Switzerland",
                                                     "Geneva",
                                                     "Carouge")));

        assertEquals("Check population of city of Sion", 15,
                     moi.getPopulation(Arrays.asList("Switzerland",
                                                     "Valais",
                                                     "Sion")));
    }

    @Test
    @GradedTest(name = "A canton(island) should have the correct population",
                points = 1)
    public void checkCantonPopulation() throws InvalidAddressException,
                                               AddressNotFoundException {
        assertEquals("Check population of canton of Vaud", 67,
                     moi.getPopulation(Arrays.asList("Switzerland", "Vaud")));
        assertEquals("Check population of canton of Vaud", 48,
                     moi.getPopulation(Arrays.asList("Switzerland", "Geneva")));
        assertEquals("Check population of canton of Vaud", 15,
                     moi.getPopulation(Arrays.asList("Switzerland", "Valais")));
    }

    @Test
    @GradedTest(name = "A country should have the correct population",
                points = 1)
    public void checkCountryPopulation() throws InvalidAddressException,
                                                AddressNotFoundException {
        assertEquals("Check population of Switzerland", 132,
                     moi.getPopulation(Collections.
                                       singletonList("Switzerland")));
    }

    private void checkGivenHousesPopulation(List<String> prefix, int houseNb)
        throws InvalidAddressException, AddressNotFoundException {
        for (int i = 0; i < houseNb; i++) {
            List<String> address = new ArrayList<>(prefix);
            address.add(Integer.toString(i));
            assertEquals("Check population of house " + prefix.toString() +
                         " number " + i,
                         i, moi.getPopulation(address));
        }
    }

}
