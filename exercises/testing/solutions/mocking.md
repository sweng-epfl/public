# Solutions: Mocking

There is no single solution here. As long as your tests do not use any custom implementations any more but only mocks, you're good!

Here is an example:

```java
@Test
void questionMarksMeanItsRainingMen() throws IOException {
    HttpClient client = mock(HttpClient.class);
    when(client.get("http://example.org/weather/today")).thenReturn("???");
    WeatherService service = new WeatherService(client);

    assertThat(service.getWeatherToday(), is(Weather.ITS_RAINING_MEN_HALLELUJAH));
}
```
