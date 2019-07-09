# Travelling-salesman-problem
Description of the problem:

The problem is to find the shortest path that passes through all cities and returns to the initial city.

Data set:

Input data set: 400 points on the Euclidean plane represented by x and y coordinates.

Method of solving:

In a given project, we use the greedy (Greedy algorythmm) and climbing (Hill-climbing algorythm) methods:
• The greedy method consists in selecting the best point in the next steps (the distance to which it is the shortest)
• For the climbing method, first set a random path (x0). In the next step, we generate all paths similar to x0 (defined by the neighborhood relation, i.e. those paths that differ by a maximum of two points that are part of the path). In this case, the number of similar paths will be around 80,000 (after the formula n (n-1) / 2). From the generated paths, we choose the best one. If the selected path is the same as x0, we end the program. Otherwise, we set the chosen path as x0 and repeat the algorithm.
