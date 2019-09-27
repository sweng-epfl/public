# Solutions: Interfaces - Advanced

There is no exact solution here, we just hope you had fun implementing this. How did you handle the difference in inputs between yr.no (location name) and SRG-SSR (coordinates)? One way would be to add another "location service" interface that takes in a location name and returns the location's coordinates. The weather service interface then asks for a location name, and the SRG-SSR client internally converts it to the coordinates expected by the SRG-SSR API.

