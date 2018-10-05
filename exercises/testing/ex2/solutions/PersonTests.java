import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PersonTests {
    @Test
    public void minorAt17() {
        Person person = new Person("SwEng", "Student", 17);
        assertThat(person.isMinor(), is(true));
    }

    @Test
    public void differentLastNameMeansNotEqual() {
        Person person1 = new Person("Bob", "West", 30);
        Person person2 = new Person("Bob", "East", 30);
        assertThat(person1.equals(person2), is(false));
    }
}
