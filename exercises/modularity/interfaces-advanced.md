# Interfaces - Advanced

Now that you have seen examples of interfaces, understood how to group objects by interface, and learned that interfaces change based on one's point of view, here is a practical exercise.

Define an interface representing a weather service that can report past and current weather, as well as forecast future weather.
You will most likely need to also define classes or enums for the inputs and outputs of this interface.

Write a trivial fake implementation of this interface, e.g. one that pretends it's always sunny.

Write a client for this interface, e.g. a simple command-line app that displays the weather.

Now write an implementation of this interface backed by [the yr.no API](https://hjelp.yr.no/hc/en-us/articles/360001940793-Free-weather-data-service-from-Yr).

Did you have to change your original interface? Why?

How much of the implementation for this interface could you reuse if you had to write a client for the [SRG-SSR weather API](https://developer.srgssr.ch/apis/srgssr-weather)? Would you need to change the interface again? Would this new client need make use of additional interfaces internally?

