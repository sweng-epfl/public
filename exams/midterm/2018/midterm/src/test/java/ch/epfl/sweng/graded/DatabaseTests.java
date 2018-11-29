package ch.epfl.sweng.graded;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.CompositeAddressUnit;
import ch.epfl.sweng.House;
import ch.epfl.sweng.InvalidAddressException;
import ch.epfl.sweng.MinistryOfInformation;

import ch.epfl.sweng.graded.grading.GradedCategory;
import ch.epfl.sweng.graded.grading.GradedTest;
import ch.epfl.sweng.graded.grading.JUnitGradeSheetTestRunner;


@RunWith(JUnitGradeSheetTestRunner.class)
@GradedCategory(name = "Q1 : `Find A House`",
                description = "These tests check whether your implementation" +
                              " of findAHouse returns valid results")
public abstract class DatabaseTests {

    protected MinistryOfInformation moi;

    protected List<String> lausannePrefix;
    protected int lausanneSize;
    protected String lausannePhone;

    protected List<String> renensPrefix;
    protected int renensSize;
    protected String renensPhone;

    protected List<String> lutryPrefix;
    protected int lutrySize;
    protected String lutryPhone;

    protected List<String> genevaPrefix;
    protected int genevaSize;
    protected String genevaPhone;

    protected List<String> carougePrefix;
    protected int carougeSize;
    protected String carougePhone;

    protected List<String> sionPrefix;
    protected int sionSize;
    protected String sionPhone;

    protected List<String> fiveAddr = null; // to test long addresses too

    protected List<String> nulledList = null;
    protected List<String> mixNullList = null;
    protected List<String> strEmptyList = null;
    protected List<String> strMixedEmptyList = null;
    protected List<String> strBigList = null;
    protected String bigStr = null;
    protected House testHouse = null;

    protected List<String> emptyAddr = null;

    private void addCity(MinistryOfInformation po,
                         List<String> city,
                         int nbOfHouses,
                         String phoneIndicator)
        throws InvalidAddressException {
        for (int i = 0; i < nbOfHouses; i++) {
            po.addAddress(Arrays.asList(city.get(0), city.get(1),
                                        city.get(2), Integer.toString(i)),
                          new House(Integer.toString(i),
                                    i,
                                    phoneIndicator + i));
        }
    }

    @Before
    public void setUp() throws InvalidAddressException {
        fiveAddr = Arrays.asList("Africa", "Seychelles",
                                 "Mahex", "Anse Royalex",
                                 "130");

        CompositeAddressUnit d = new CompositeAddressUnit("Switzerland");

        moi = new MinistryOfInformation(d);

        lausannePrefix = Arrays.asList("Switzerland", "Vaud", "Lausanne");
        lausanneSize = 9;
        lausannePhone = "+41 211 000 00 0";
        addCity(moi, lausannePrefix, lausanneSize, lausannePhone);

        renensPrefix = Arrays.asList("Switzerland", "Vaud", "Renens");
        renensSize = 7;
        renensPhone = "+41 212 000 00 0";
        addCity(moi, renensPrefix, renensSize, renensPhone);

        lutryPrefix = Arrays.asList("Switzerland", "Vaud", "Lutry");
        lutrySize = 5;
        lutryPhone = "+41 21 300 00 0";
        addCity(moi, lutryPrefix, lutrySize, lutryPhone);

        genevaPrefix = Arrays.asList("Switzerland", "Geneva", "Geneva");
        genevaSize = 10;
        genevaPhone = "+41 22 100 00 0";
        addCity(moi, genevaPrefix, genevaSize, genevaPhone);

        carougePrefix = Arrays.asList("Switzerland", "Geneva", "Carouge");
        carougeSize = 3;
        carougePhone = "+41 22 200 00 0";
        addCity(moi, carougePrefix, carougeSize, carougePhone);

        sionPrefix = Arrays.asList("Switzerland", "Valais", "Sion");
        sionSize = 6;
        sionPhone = "+41 24 100 00 0";
        addCity(moi, sionPrefix, sionSize, sionPhone);

        testHouse = new House("0", 2, "+248 4 376 00");

        // make a giant string
        String a = "a";
        bigStr = a;
        for(int i=0;i<4096*2;++i){
            bigStr += a;
        }

        nulledList = Arrays.asList(null, null, null, null);
        mixNullList = Arrays.asList("Switzerland", null, null, null);
        strEmptyList = Arrays.asList("", "", "", "");
        strMixedEmptyList = Arrays.asList("Switzerland", "", "", "");
        strBigList = Arrays.asList("Switzerland", bigStr, bigStr, "0");

        moi.addAddress(strBigList,
                testHouse);

        emptyAddr = new ArrayList<String>();
    }

}
