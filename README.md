# photo-corrector :camera:
Java program that applies two algorithms to an image's RGB components. 
As a result it creates two images one for each algorithm.

### Description :
First the program needs to separate each RGB components from all the pixels. Next, it creates three histogram, one for each component (Red, Green, Blue) that shows each occurrences of each values going from 0 to 255. After that it takes the intervals where the value is above 0 then it uses them to apply the two algorithms on each pixels of the picture. Finally, the program adds two modified images to the working directory.

### Algorithms :


### Examples :
