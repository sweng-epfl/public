# TDD / Part 1

You will find here a possible step-by-step solution of this part. A full possible solution is available in the corresponding files ['CounterfeitDetector.java'](CounterfeitDetector.java) and ['CounterfeitDetectorTest.java'](CounterfeitDetectorTest.java).

First step:

```java
class CounterfeitDetector {

    public boolean isValid(Currency currency) {
        return (currency.getValue() >= 0);
    }

}

```
```java
class CounterfeitDetectorTest {
    CounterfeitDetector detector = new CounterfeitDetector();
    Dollars currencyValid = new Dollars(10, 2100);
    Dollars currencyInvalidValue = new Dollars(-5, 1978);

    @Test
    public void isValidTrueTest() {
        assertTrue(detector.isValid(valid));
    }

    @Test
    public void isValidFalseTest() {
        assertFalse(detector.isValid(currencyInvalidValue));
    }

}

```

Second step:

```java
class CounterfeitDetector {
    ...

    public boolean isExpired(Currency currency) {
        return (currency.getExpiration() <= 2018);
    }

    ...
}

```
```java
class CounterfeitDetectorTest {
    ...

    @Test
    public void isExpiredTrueTest() {
        assertTrue(detector.isExpired(currencyInvalidValue));
    }

    @Test
    public void isExpiredFalseTest() {
        assertFalse(detector.isExpired(currencyValid));
    }

}

```


Third step:

```java
class CounterfeitDetector {

    public boolean isValid(Currency currency) {
        return (currency.getValue() >= 0 && currency.getExpiration() >= 2018);
    }
}

```
```java
class CounterfeitDetectorTest {
    CounterfeitDetector detector = new CounterfeitDetector();
    Dollars currencyValid = new Dollars(10, 2100);
    Dollars currencyInvalidValue = new Dollars(-5, 1978);

    @Test
    public void isValidTrueTest() {
        assertTrue(detector.isValid(currencyValid));
    }

    @Test
    public void isValidFalseTest() {
        assertFalse(detector.isValid(currencyInvalidValue));
    }

}

```
Fourth step: 

```java
class CounterfeitDetector {
    ...

    public boolean isFrancsValid(Francs francs) {
        return isValid(francs) && (String.equals(francs.getAuthority(), "CH") || String.equals(francs.getAuthority(), "SNB"));
    }

    public boolean isEurosValid(Euros euros) {
        return isValid(euros) && euros.getCreation() > 2004;
    }
}

```
```java
class CounterfeitDetectorTest {
    ...

    Francs francsValid = new Francs(10, 2019, "CH");
    Francs francsExpired = new Francs(10, 1970, "CH");
    Francs francsInvalidValue = new Francs(-2, 2019, "SNB");
    Francs francsInvalidAuthority = new Francs(10, 2019, "FR");

    Euros eurosValid = new Euros(10, 2019, 2005);
    Euros eurosExpired = new Euros(10, 1970, 2005);
    Euros eurosInvalidValue = new Euros(-2, 2019, 2005);
    Euros eurosInvalidCreation = new Euros(-2, 2019, 200);

    ...

    @Test
    public void isFrancsValidTrueTest() {
        assertTrue(detector.isFrancsValid(francsValid));
    }

    @Test
    public void isFrancsValidFalseTest() {
        assertFalse(detector.isFrancsValid(francsExpired));
        assertFalse(detector.isFrancsValid(francsInvalidValue));
        assertFalse(detector.isFrancsValid(francsInvalidAuthority));
    }

    @Test
    public void isEurosValidTrueTest() {
        assertTrue(detector.isEurosValid(eurosValid));
    }

    @Test
    public void isEurosValidFalseTest() {
        assertFalse(detector.isEurosValid(eurosExpired));
        assertFalse(detector.isEurosValid(eurosInvalidValue));
        assertFalse(detector.isEurosValid(eurosInvalidCreation));
    }

}

```
