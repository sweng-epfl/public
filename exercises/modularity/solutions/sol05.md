# Minimal interfaces

- [ ] public void draw(Surface s);

This function is indeed necessary for this interface, as it describes the main behaviour.

- [x] public int getWidth(); public int getHeight();

Although both of this functions are often useful, they are not strictly necessary. In fact, there exist file formats which do not have a fixed size but instead can adapt to the dimensions of the surface in which it is drawn.

- [x] public void saveAsPNG(String filename);

This function is clearly not strictly necessary, as you do not need it for the main behaviour described in the problem.

- [x] public void resize(int width, int height);

Similar to getWidth and getHeight, the image does not necessarily need to have a fixed size, and as such resizing it does not necessarily make sense.
