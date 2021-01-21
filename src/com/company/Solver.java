package com.company;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Solver
{
    ArrayList<Node> unexpanded = new ArrayList<Node>(); // Holds unexpanded node list
    ArrayList<Node> expanded = new ArrayList<Node>();   // Holds expanded node list
    Node rootNode;                                      // Node representing initial state

    /*
       Solver is a constructor that sets up an instance of the class with a node corresponding
       to the initial state as the root node.
     */
    public Solver(int[][] initialBoard) {
        PuzzleState initialState = new PuzzleState(initialBoard);
        rootNode = new Node(initialState);
    }

    //astar solver
    public void solve(PrintWriter output) {
        long startTime = System.nanoTime();
        unexpanded.add(rootNode);          // Initialise the unexpanded node list with the root node.
        int nodeLoc;
        while(unexpanded.size() > 0)
        {
            nodeLoc = getLowestA(unexpanded);

            //adds nodeloc returned from getLowest to expanded and removes it from unexpanded
            Node n = unexpanded.get(nodeLoc);
            expanded.add(n);
            unexpanded.remove(nodeLoc);


            //checks to see if the node state equals the goal, else it runs through possible moves from the current blank space, and adds it to unexpanded
            // if it isn't on both unexpanded and expanded
            if(n.state.isGoal())
            {
                System.out.println((System.nanoTime()-startTime)/1000000);
                reportSolution(n, output);
                return;
            }
            else
            {
                ArrayList<PuzzleState> moveList = n.state.possibleMoves();
                for(PuzzleState gs : moveList)
                {
                    int newCost = n.getCost() + 1;
                    Node newNode = new Node(gs, n, newCost);
                    if(gs.isGoal())
                    {
                        System.out.println((System.nanoTime()-startTime)/1000000);
                        reportSolution(newNode, output);
                        return;
                    }
                    if((Node.findNodeWithState(expanded, gs) == null)) {
                        if ((Node.findNodeWithState(unexpanded, gs) == null) ||
                                Node.findNodeWithState(unexpanded, gs).getHeuristicCost() > newNode.getHeuristicCost()) {
                            unexpanded.add(newNode);
                        }
                    }
                }
            }
        }
        output.println("No solution found");
    }

    public int idaSolve(PrintWriter output)
    {
        int threshold = rootNode.getHeuristic();
        while(true)
        {
            System.out.println("Iteration with threshold: " + threshold);

            int distance = IDA(rootNode, 0, threshold, output);

            if(distance == Integer.MAX_VALUE)
            {
                System.out.println("node not found");
                return -1;
            }
            else if(distance < 0)
            {
                System.out.println("found node");
                return -distance;
            }
            threshold = distance;
        }
    }

    public int IDA(Node n, int distance, int threshold, PrintWriter output)
    {
        //System.out.println("visiting node: " + n.toString());

        int estimate = distance+n.getHeuristic();

        if(estimate > threshold)
        {
            //System.out.println("threshold breached");
            return estimate;
        }
        if(n.state.isGoal())
        {
            reportSolution(n, output);
            return -(n.getCost());
        }

        int min = Integer.MAX_VALUE;

        for(PuzzleState ps : n.state.possibleMoves())
        {
            if((n.getCost()+1)<=100)
            {
                Node newNode = new Node(ps, n, n.getCost()+1);

                int temp = IDA(newNode, newNode.getCost(), threshold, output);
                if (temp < 0) {
                    return temp;
                }
                if (temp < min) {
                    min = temp;
                }
            }
        }

        return min;
    }

    public int getLowestA(ArrayList<Node> unexpanded)
    {
        int lowest = unexpanded.get(0).getHeuristicCost();
        int lowestLoc=0;
        for(int i = 1; i < unexpanded.size(); i++)
        {
            if(unexpanded.get(i).getHeuristicCost()<lowest)
            {
                lowest = unexpanded.get(i).getHeuristicCost();
                lowestLoc=i;
            }
        }
        return lowestLoc;
    }

    public void printSolution(Node n, PrintWriter output) {
        if (n.parent != null) printSolution(n.parent, output);
        output.println(n.state);
    }

    public void reportSolution(Node n, PrintWriter output) {
        output.println("Solution found!");
        printSolution(n, output);
        output.println(n.getCost() + " Moves");
        output.println("Nodes expanded: " + this.expanded.size());
        output.println("Nodes unexpanded: " + this.unexpanded.size());
        output.println();
    }
}
