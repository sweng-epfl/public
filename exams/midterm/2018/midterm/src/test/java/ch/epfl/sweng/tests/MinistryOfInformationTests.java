package ch.epfl.sweng.tests;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Integer;

import ch.epfl.sweng.House;
import ch.epfl.sweng.MinistryOfInformation;
import ch.epfl.sweng.CompositeAddressUnit;
import ch.epfl.sweng.InvalidAddressException;
import ch.epfl.sweng.AddressNotFoundException;

import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * You can add tests to this class
 * This class tests the basic usecases of MinistryOfInformation.
 * Currently, it tests a few general cases, but is missing many cornercases.
 * It sets up a model of a Seychelles address system with some
 * bogus houses. It then exercises the query methods in MinistryOfInformation
 * on the model.
 */
public class MinistryOfInformationTests {
    private MinistryOfInformation moi = null;
    private House aRecipient = new House("234", 1, "+248 4 363 023");

    private List<House> generateHouses(String idPrefix,
                                       String phonePrefix,
                                       int numHouses) {
        List<House> houses = new ArrayList<House>();
        for (int i = 0; i < numHouses; ++i) {
            houses.add(new House(idPrefix + Integer.toString(i),
                                i,
                                phonePrefix + Integer.toString(i)));
        }
        return houses;
    }

    @Before
    public void setUp() throws InvalidAddressException {
        // Create the country root node.
        CompositeAddressUnit d = new CompositeAddressUnit("Seychelles");
        // Initialize the principal interface of the system.
        moi = new MinistryOfInformation(d);
        // Add a few addresses into the directory as the test data.
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Anse Royale", "110"),
                      new House("110", 0, "+248 4 346 00"));
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Anse Royale", "111"),
                      new House("111", 1, "+248 4 346 01"));
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Anse Royale", "112"),
                      new House("112", 2, "+248 4 346 02"));
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Port Glaud", "110"),
                      new House("110", 0, "+248 4 346 00"));
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Port Glaud", "111"),
                      new House("111", 1, "+248 4 346 01"));
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Port Glaud", "112"),
                      new House("112", 2, "+248 4 346 02"));
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Bel Ombre", "110"),
                      new House("110", 0, "+248 4 346 00"));
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Bel Ombre", "111"),
                      new House("111", 1, "+248 4 346 01"));
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Bel Ombre", "112"),
                      new House("112", 2, "+248 4 346 02"));
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Bel Air", "110"),
                      new House("110", 0, "+248 4 346 00"));
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Bel Air", "111"),
                      new House("111", 1, "+248 4 346 01"));
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Bel Air", "112"),
                      new House("112", 2, "+248 4 346 02"));
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Anse Boileau", "110"),
                      new House("110", 0, "+248 4 346 00"));
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Anse Boileau", "111"),
                      new House("111", 1, "+248 4 346 01"));
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Anse Boileau", "112"),
                      new House("112", 2, "+248 4 346 02"));
        moi.addAddress(Arrays.asList("Seychelles", "La Digue", "L'Union", "110"),
                      new House("110", 0, "+248 4 346 00"));
        moi.addAddress(Arrays.asList("Seychelles", "La Digue", "L'Union", "111"),
                      new House("111", 1, "+248 4 346 01"));
        moi.addAddress(Arrays.asList("Seychelles", "La Digue", "L'Union", "112"),
                      new House("112", 2, "+248 4 346 02"));
        moi.addAddress(Arrays.asList("Seychelles", "La Digue", "La Passe", "110"),
                      new House("110", 0, "+248 4 346 00"));
        moi.addAddress(Arrays.asList("Seychelles", "La Digue", "La Passe", "111"),
                      new House("111", 1, "+248 4 346 01"));
        moi.addAddress(Arrays.asList("Seychelles", "La Digue", "La Passe", "112"),
                      new House("112", 2, "+248 4 346 02"));
        moi.addAddress(Arrays.asList("Seychelles", "La Digue", "La Reunion", "110"),
                      new House("110", 0, "+248 4 346 00"));
        moi.addAddress(Arrays.asList("Seychelles", "La Digue", "La Reunion", "111"),
                      new House("111", 1, "+248 4 346 01"));
        moi.addAddress(Arrays.asList("Seychelles", "La Digue", "La Reunion", "112"),
                      new House("112", 2, "+248 4 346 02"));
        moi.addAddress(Arrays.asList("Seychelles", "Eden Island", "Bernitier Beach", "110"),
                      new House("110", 0, "+248 4 346 00"));
        moi.addAddress(Arrays.asList("Seychelles", "Eden Island", "Bernitier Beach", "111"),
                      new House("111", 1, "+248 4 346 01"));
        moi.addAddress(Arrays.asList("Seychelles", "Eden Island", "Bernitier Beach", "112"),
                      new House("112", 2, "+248 4 346 02"));
        moi.addAddress(Arrays.asList("Seychelles", "Eden Island", "Eden Marina", "110"),
                      new House("110", 0, "+248 4 346 00"));
        moi.addAddress(Arrays.asList("Seychelles", "Eden Island", "Eden Marina", "111"),
                      new House("111", 1, "+248 4 346 01"));
        moi.addAddress(Arrays.asList("Seychelles", "Eden Island", "Eden Marina", "112"),
                      new House("112", 2, "+248 4 346 02"));
        moi.addAddress(Arrays.asList("Seychelles", "Praslin", "Anse La Blague", "110"),
                      new House("110", 0, "+248 4 346 00"));
        moi.addAddress(Arrays.asList("Seychelles", "Praslin", "Anse La Blague", "111"),
                      new House("111", 1, "+248 4 346 01"));
        moi.addAddress(Arrays.asList("Seychelles", "Praslin", "Anse La Blague", "112"),
                      new House("112", 2, "+248 4 346 02"));
        moi.addAddress(Arrays.asList("Seychelles", "Praslin", "Baie Ste Anne", "110"),
                      new House("110", 0, "+248 4 346 00"));
        moi.addAddress(Arrays.asList("Seychelles", "Praslin", "Baie Ste Anne", "111"),
                      new House("111", 1, "+248 4 346 01"));
        moi.addAddress(Arrays.asList("Seychelles", "Praslin", "Baie Ste Anne", "112"),
                      new House("112", 2, "+248 4 346 02"));
        moi.addAddress(Arrays.asList("Seychelles", "Praslin", "Anne Posession", "110"),
                      new House("110", 0, "+248 4 346 00"));
        moi.addAddress(Arrays.asList("Seychelles", "Praslin", "Anne Posession", "111"),
                      new House("111", 1, "+248 4 346 01"));
        moi.addAddress(Arrays.asList("Seychelles", "Praslin", "Anne Posession", "112"),
                      new House("112", 2, "+248 4 346 02"));
        moi.addAddress(Arrays.asList("Seychelles", "Praslin", "Cap Samy", "110"),
                      new House("110", 0, "+248 4 346 00"));
        moi.addAddress(Arrays.asList("Seychelles", "Praslin", "Cap Samy", "111"),
                      new House("111", 1, "+248 4 346 01"));
        moi.addAddress(Arrays.asList("Seychelles", "Praslin", "Cap Samy", "112"),
                      new House("112", 2, "+248 4 346 02"));
        moi.addAddress(Arrays.asList("Seychelles", "Praslin", "Grand Anse", "110"),
                      new House("110", 0, "+248 4 346 00"));
        moi.addAddress(Arrays.asList("Seychelles", "Praslin", "Grand Anse", "111"),
                      new House("111", 1, "+248 4 346 01"));
        moi.addAddress(Arrays.asList("Seychelles", "Praslin", "Grand Anse", "112"),
                      new House("112", 2, "+248 4 346 02"));
    }

    @Test
    public void testAddressResolution()
        throws InvalidAddressException, AddressNotFoundException {
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Port Glaud", "234"),
                      aRecipient);
        House r = moi.findHouse(Arrays.asList("Seychelles", "Mahe",
                                              "Port Glaud", "234"));
        assertEquals(r, aRecipient);
    }

    @Test(expected = AddressNotFoundException.class)
    public void testAddressResolutionNotFound()
        throws InvalidAddressException, AddressNotFoundException {
        House r = moi.findHouse(Arrays.asList("Seychelles", "Mahe",
                                              "Port Glaud", "432"));
    }

    @Test
    public void testAddAddress()
        throws InvalidAddressException, AddressNotFoundException {
        House house = new House("8839", 18, "+248 3 225 322");
        List<String> address = Arrays.asList("Seychelles", "Mahe",
                                             "Port Glaud", "8839");
        moi.addAddress(address, house);
        assertEquals(house, moi.findHouse(address));
    }

    @Test
    public void testGetPopulation()
        throws InvalidAddressException, AddressNotFoundException {
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Port Glaud", "234"),
                       aRecipient);
        assertEquals(1, (int)moi.getPopulation(Arrays.asList("Seychelles",
                                                             "Mahe",
                                                             "Port Glaud",
                                                             "234")));
        assertEquals(4, (int)moi.getPopulation(Arrays.asList("Seychelles",
                                                             "Mahe",
                                                             "Port Glaud")));
    }

    @Test
    public void testPrintAddresses()
        throws InvalidAddressException, AddressNotFoundException {
        List<House> dieulandDestinations =
            generateHouses("11", "+248 4 346 0", 5);
        for(House destination : dieulandDestinations) {
            moi.addAddress(Arrays.asList("Seychelles", "Dream Island",
                                         "Dieuland", destination.getName()),
                           destination);
        }

        // Redirecting output stream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Method being tested
        moi.printAddresses(Arrays.asList("Seychelles",
                                         "Dream Island",
                                         "Dieuland"));

        String outputMessage = outContent.toString();

        int len = outputMessage.split(System.getProperty("line.separator")).length;
        assertEquals(5, len);

        for(House destination : dieulandDestinations) {
            String correctMessage = "Seychelles, Dream Island, Dieuland, " + destination.getName();
            assertTrue(outputMessage.contains(correctMessage));
        }

        // Resetting output stream
        System.setOut(null);
    }

    @Test
    public void testGetLandLine()
        throws InvalidAddressException, AddressNotFoundException {
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Port Glaud", "234"),
                       aRecipient);
        House r = moi.findHouse(Arrays.asList("Seychelles", "Mahe",
                                              "Port Glaud", "234"));
        assertEquals(r.getLandLinePhone(), aRecipient.getLandLinePhone());
    }

    @Test(expected = InvalidAddressException.class)
    public void testAddAddressOnHouseFails()
        throws InvalidAddressException, AddressNotFoundException {
        aRecipient.addAddress(new ArrayList<String>(), aRecipient);
    }

    @Test(expected = InvalidAddressException.class)
    public void testFindUnitOverspecified()
        throws InvalidAddressException, AddressNotFoundException {
        aRecipient.findUnit(Arrays.asList(aRecipient.getName(), "bld 1"));
    }

    @Test(expected = InvalidAddressException.class)
    public void testFindHouseEmptyAddress()
        throws InvalidAddressException, AddressNotFoundException {
        moi.findHouse(new ArrayList<String>());
    }

    @Test(expected = InvalidAddressException.class)
    public void testFindHouseWrongCountry()
        throws InvalidAddressException, AddressNotFoundException {
        moi.findHouse(Arrays.asList("Switzerland", "Vaud", "Lausanne", "EPFL"));
    }

    @Test(expected = InvalidAddressException.class)
    public void testAddAddressIncomplete()
        throws InvalidAddressException, AddressNotFoundException {
        moi.addAddress(Arrays.asList("Seychelles", "Mahe"), aRecipient);
    }

    @Test(expected = InvalidAddressException.class)
    public void testAddAddressIncomplete2()
        throws InvalidAddressException, AddressNotFoundException {
        moi.addAddress(Arrays.asList("Seychelles"), aRecipient);
    }

    @Test(expected = InvalidAddressException.class)
    public void testAddAddressEmpty()
        throws InvalidAddressException, AddressNotFoundException {
        moi.addAddress(Arrays.asList(), aRecipient);
    }

    @Test(expected = InvalidAddressException.class)
    public void testAddAddressWrongCountry()
        throws InvalidAddressException, AddressNotFoundException {
        moi.addAddress(Arrays.asList("Switzerland", "Vaud", "Lausanne"), aRecipient);
    }

    @Test(expected = InvalidAddressException.class)
    public void testFindHouseIncomplete()
        throws InvalidAddressException, AddressNotFoundException {
        House r = moi.findHouse(Arrays.asList("Seychelles", "Mahe", "Port Glaud"));
    }

    @Test(expected = InvalidAddressException.class)
    public void testAddAddressInconsistent()
        throws InvalidAddressException, AddressNotFoundException {
        moi.addAddress(Arrays.asList("Seychelles", "Mahe", "Port Glaud", "235"),
                       aRecipient);
    }
}
