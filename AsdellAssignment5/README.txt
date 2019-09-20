This folder contains all materials required for Assignment 5.
Assignment 5 asks the programmer to implement a series of "Photoshop-esque" tools for a prebuilt GUI.
I coded eight different tools/effects to complete this assignment, all of which required extensive and creative use
of arrays, 2D pixel arrays, and ArrayLists.
90 degree rotations in either direction and horizontal flip were simple implementations of array manipulation and inversion.
Color negative was also relatively intuitive, as it inverts the RGB values of each individual pixel in the pixel array.
Crop essentially builds a new array out of the old pixel array using the pixel address given by the selected box.
Green Screen removes all pixels with reasonably high green values in order to create an image that can be easily overlaid onto 
a background.
Blur utilizes parallel arrays to calculate the average RGB values of a pixel and any adjacent pixels to then build a new 
pixel array using those averages.
Equalize computes two histograms to store the luminosity (given function) values of an entire image, then rebuilds the pixel
array while adjusting the luminosities of each pixel to evenly spread the luminosities over all 256 values.
