import java.io.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

// Natch Sadindum - ns174 - ID:1269188
// Usage: java AStar <textfile.txt>

public class AStar {
    static List<Node> nodeList = new ArrayList<Node>();
    static Node start; static Node goal;
    static String finalPath;

    public static void main(String[] args) {
        try {
            // Check that we have a valid map file as input
            if (args.length == 1) {
                int lineLength = ReadTextFile(args[0]);
                Queue queue = new Queue();

                AStar(start, goal, queue, lineLength);

                if (finalPath != null) {
                    FinalMap(finalPath, lineLength);
                }
            }
        }
        catch (Exception e) {
        }
    }
    // Reads our map and saves each position as a Node with its type and location on the map
    private static int ReadTextFile(String file) {
        try {
            // setup variables
            int lineNum = 1;
            InputStream is = new FileInputStream(file);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));

            // Skips the first line as it is a border
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            int count = 0;

            // Read all lines of the textfile
            while(line != null){
                line = buf.readLine();

                // If we have reached the border line
                if (line.charAt(0) == '+')
                    break;
                // records all characters except for the borders
                for (int xPos = 1; xPos < line.length() - 1; xPos++) {
                    Node n = new Node(count,String.valueOf(line.charAt(xPos)), xPos, lineNum, 1);

                    // Check symbol, set the start and end nodes
                    if (n.s.equals("S")) {
                        start = n;
                    }
                    else if (n.s.equals("G"))
                        goal = n;
                    //n.Dump();
                    nodeList.add(n);
                    count++;
                }
                lineNum++;
            }
            return line.length() - 2;

        }
        catch (IOException e) {
            System.out.println("Could not find input text file!  Please check your filename");
            return 0;
        }
    }

    // Runs the ASTAR algorithm
    private static void AStar(Node startNode, Node goalNode, Queue q, int lineLength) {
        // Print out Starting Conditions
        System.out.println("Line Length: " + lineLength);
        System.out.println("Start Location: " + startNode.xPos + "," + startNode.yPos);
        System.out.println("Goal Location: " + goalNode.xPos + "," + goalNode.yPos);

        // Insert the start node
        q.InsertPriority(startNode);
        Node current;

        // Main loop
        while (!q.IsEmpty())
        {
            // Deque a priority node
            current = q.GetPriority();
            System.out.println("Current ID: " + current.id);
            System.out.println("Goal ID: " + goalNode.id);
            System.out.println("Current Cost: " + current.cost);

            // If the current node we are looking at is the goal
            if (current.id == goalNode.id) {
                System.out.println("Ideal path found of length " + (current.cost - 1));
                System.out.println("Travel path - " + current.pathway);
                finalPath = current.pathway;
                return;
            }

            // check the neighbor nodes and add the valid ones to the frontier
            List<Node> neighbors = getNeighbors(current, lineLength);
            for (int i = 0; i < neighbors.size(); i++) {
                Node node = Move(current,neighbors.get(i));
                q.InsertPriority(node);
            }

            // Print out some info
            System.out.println("Frontier Size: " + q.FrontierSize());
            //q.DumpTraverse();
            System.out.println("");

        }
        // If we have reached this point, then there is no path to the goal
        System.out.println("No path found...");
    }

    // Checks the neighboring nodes whether they are traversable or not, and whether they have been visited in less cost
    private static List<Node> getNeighbors(Node node, int lineLength) {
        List<Node> neighbors = new ArrayList<Node>();
        Node create;
        // if the left node exists and it is a traversable space
        if ((nodeList.get(node.id).id  % lineLength) != 0
                && nodeList.get(node.id).id - 1 >= 0
                && !nodeList.get(node.id - 1).s.equals("X")) {
            //if (!CheckOverlap(node, nodeList.get(node.id - 1))) {
            if (!nodeList.get(node.id - 1).discovered) {
                nodeList.get(node.id - 1).discovered = true;
                create = new Node(nodeList.get(node.id - 1).id,
                        nodeList.get(node.id - 1).s,
                        nodeList.get(node.id - 1).xPos,
                        nodeList.get(node.id - 1).yPos, 1);
                neighbors.add(create);
                //System.out.println("Left node id: " + create.id);
            }

        }
        // if the right node exists and it is a traversable space
        if ((nodeList.get(node.id).id % lineLength) != lineLength - 1
                && nodeList.get(node.id).id + 1 < nodeList.size()
                && !nodeList.get(node.id + 1).s.equals("X")) {
            if (!nodeList.get(node.id + 1).discovered) {
                nodeList.get(node.id + 1).discovered = true;
                create = new Node(nodeList.get(node.id + 1).id,
                        nodeList.get(node.id + 1).s,
                        nodeList.get(node.id + 1).xPos,
                        nodeList.get(node.id + 1).yPos, 1);
                neighbors.add(create);
                //System.out.println("Right node id: " + create.id);
            }
        }
        // if the top node exists and it is a traversable space
        if (nodeList.get(node.id).id - lineLength >= 0
                && !nodeList.get(node.id - lineLength).s.equals("X")) {
            if (!nodeList.get(node.id - lineLength).discovered) {
                nodeList.get(node.id - lineLength).discovered = true;
                create = new Node(nodeList.get(node.id - lineLength).id,
                        nodeList.get(node.id - lineLength).s,
                        nodeList.get(node.id - lineLength).xPos,
                        nodeList.get(node.id - lineLength).yPos, 1);
                neighbors.add(create);
                //System.out.println("Top node id: " + create.id);
            }
        }
        // if the bottom node exists and it is a traversable space
        if (nodeList.get(node.id).id + lineLength < nodeList.size()
                && !nodeList.get(node.id + lineLength).s.equals("X")) {
            if (!nodeList.get(node.id + lineLength).discovered) {
                nodeList.get(node.id + lineLength).discovered = true;
                create = new Node(nodeList.get(node.id + lineLength).id,
                        nodeList.get(node.id + lineLength).s,
                        nodeList.get(node.id + lineLength).xPos,
                        nodeList.get(node.id + lineLength).yPos, 1);
                neighbors.add(create);
                //System.out.println("Bottom node id: " + create.id);
            }
        }
        return neighbors;
    }

    // Updates the cost and path of the node to be added to the priority queue
    private static Node Move(Node c, Node next) {
        Node temp = new Node(next.id,next.s,next.xPos,next.yPos,c.cost + 1);
        temp.pathway = c.pathway.concat("," + next.id);
        return temp;
    }

    // if the ID of the next square we wish to move to matches the ID of something we have already used
    private static boolean CheckOverlap(Node node, Node next) {
        String p[] = node.pathway.split(",");
        System.out.println(node.pathway);
        for (int i = 0; i < p.length; i++) {

            if (String.valueOf(next.id).equals(p[i])) {
                //System.out.println("Overlap at " + p[i]);
                return true;
            }

        }
        return false;
    }

    //
    private static void PrunePath(Node node, Queue q, Node current) {
        if (q.Repeats(node)) {
            q.Remove(node);
        }
    }

    // Prints out visual representation of the path and map
    private static void FinalMap(String path, int lineLength) {
        // go through all the nodes in the list
        String[] p = path.split(",");
        for (int i = 0; i < lineLength + 2; i++)
            System.out.print("+");
        System.out.println("");
        for (int i = 0; i < nodeList.size(); i++) {
            if (i % lineLength == 0 )
                System.out.print("+");
            boolean draw = true;
            for (int j = 0; j < p.length; j++) {
                if (nodeList.get(i).id == Integer.valueOf(p[j]) && nodeList.get(i).s.equals(" ") ) {
                    System.out.print(0);
                    draw = false;
                }
            }
            if (draw == true)
                System.out.print(nodeList.get(i).s);
            if (i % lineLength == lineLength - 1) {
                System.out.print("+");
                System.out.println("");
            }
        }

        for (int i = 0; i < lineLength + 2; i++)
            System.out.print("+");
    }
}
// Node object for use in the queue and to determine the path
class Node {
    public int id = 0;
    public String s = "";
    public int cost = 0;
    public int xPos = 0;
    public int yPos = 0;
    public String pathway = "";
    public boolean discovered = false;

    public String Value() { return s; }

    public Node next = null;
    public Node(int i, String ss, int x, int y, int c) {id = i; s = ss; xPos = x; yPos = y;  cost = c;  pathway = String.valueOf(id); }
    public void Dump() {
        System.out.println("Node " + s + " at position " + xPos + ", " + yPos);
    }
}

// Queue object, with enqueue, dequeue and sort capability
class Queue {
    private Node head = null;
    private Node tail = null;

    public Queue() {}

    public void InsertPriority(Node node) {
        // If the queue is empty(initial conditions)
        if (tail == null && head == null) {
            tail = node;
            head = tail;
            head.next = null;
        }
        else {
            // Inserts the node based on the cost
            Node current = head;
            Node previous = head;
            while (current != null) {
                // if the added node has a lower cost than the current add it
                // and se pointer of previous to new node, point new node to current
                if (node.cost < current.cost) {
                    // special case, node has lower cost than head
                    node.next = current;
                    if (current == head) {
                        head = node;
                    }
                    // cases after first
                    else {
                        previous.next = node;
                    }


                    return;
                }
                previous = current;
                current = current.next;
            }
            // if all other nodes have lower cost
            tail.next = node;
            tail = node;
        }
    }
    public Node GetPriority() {
        Node temp = head;
        if (head.next == null) {
            head = null;
            tail = null;
        }
        else
            head = head.next;
        return temp;
    }

    // Part of the pruning function, finds nodes on the same square and deletes the one with the higher cost
    public void Remove(Node node) {

    }
    // check if a node id is repeated in the queue
    public boolean Repeats(Node node) {
        Node current = head;
        while (current != null) {
            if (current.id == node.id)
                return true;
            current = current.next;
        }
        return false;
    }

    public boolean IsEmpty() {
        if (head == null && tail == null)
            return true;
        else
            return false;
    }

    public int FrontierSize() {
        int num = 0;
        Node current = head;
        while (current != null) {
            num++;
            current = current.next;
        }
        return num;
    }
    public void DumpTraverse() {
        Node current = head;
        while (current != null) {
            System.out.println(current.xPos + "," + current.yPos);
            current = current.next;
        }
    }

    public int Length() {
        int len = 0;
        Node current = head;
        while (current != null) {
            len++;
            current = current.next;
        }
        return len;
    }
}