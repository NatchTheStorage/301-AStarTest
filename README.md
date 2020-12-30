# 301-AStarTest
AStar algorithm, finds the shortest direct path between two selected tiles on a 2D grid and and outputs the path visually and the distance taken.

To use, navigate with CMD to the folder containing AStar.java and its supporting classes and a map file.

Usage is:
java AStar <map file>
An example is:
java AStar map1.txt
  
The program will take the input map and perform the AStar algorithm to find the shortest route between the tile marked S (start) and G (goal).
The program will output the ID number of the current tile it is on, the ID number of the goal tile, the current cost (distance and distance penalties depending on the tile) and the size of the frontier in which tiles are processed in a queue.

Once an ideal path is found, the path will be displayed in a visual format and a numerical format of chained tile IDs.

To create a map, use the "-" and "|" characters to form the borders of a quadrilateral map, which the corners marked by "+".
Place one "S" tile to mark the start point, and a "G" tile for the end point/goal.  "X" represents impassable tiles.
The algorithm will not allow for direct diagonal movement or "overlapping" the borders.
An example map is below (please view in RAW format)

+-------------------------------+
|                               |
|        XXXXXX       X         |
|         XXXXXXX     XX        |
|            XXXX     XG        |
|            XXXX     XXXX      |
|   XX                 XXX      |
|   XXX                         |
|    XXS           XXXXXXX      |
|   XXXXXX       XXX     X      |
|    XXX XXXXXXXXX       XX     |
|         XXXXXX          X     |
|                               |
+-------------------------------+
