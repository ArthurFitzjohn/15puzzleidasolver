package com.company;

import java.util.ArrayList;

public class Node
{
    PuzzleState state;    // The state associated with the node
    Node parent;        // The node from which this node was reached.
    private int cost;   // The cost of reaching this node from the initial node.
    private int heuristicCost;

    /*
      Constructor used to create new nodes.
     */
    public Node(PuzzleState state, Node parent, int cost) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
    }

    /*
      Constructor used to create initial node.
     */
    public Node(PuzzleState state) {
        this(state,null,0);
    }


    //returns cost
    public int getCost() {
        return cost;
    }


    //returns heuristic cost
    public int getHeuristicCost()
    {
        return cost+state.heuristic(state.puzzle);
    }

    public int getHeuristic()
    {
        return state.heuristic(state.puzzle);
    }

    public String toString() {
        return "Node:" + state + " ";
    }

    /*
      Given a list of nodes as first argument, findNodeWithState searches the list for a node
       whose state is that specified as the second argument.
       If such a node is in the list, the first one encountered is returned.
       Otherwise null is returned.
     */
    public static Node findNodeWithState(ArrayList<Node> nodeList, PuzzleState gs) {
        for (Node n : nodeList) {
            if (gs.sameBoard(n.state)) return n;
        }
        return null;
    }
}
