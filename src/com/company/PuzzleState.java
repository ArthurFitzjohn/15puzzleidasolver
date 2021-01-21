package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PuzzleState
{
    static int[][] INITIAL_STATE;
    static int[][] GOAL_STATE;
    private int spacePos1;
    private int spacePos2;
    final int[][] puzzle;
    int prevState;
    
    public PuzzleState(int[][] puzzle)
    {
        this.puzzle = puzzle;
        boolean spacePosFound = false;
        for(int i = 0; i < puzzle.length; i++)
        {
            for(int j = 0; j < puzzle[i].length; j++)
            {
                if(puzzle[i][j] == 0)
                {
                    this.spacePos1 = i;
                    this.spacePos2 = j;
                    spacePosFound = true;
                    break;
                }
            }
            if(spacePosFound)
            {
                break;
            }
        }
    }
    
    public PuzzleState clone()
    {
        int[][] clonedBoard = Arrays.stream(this.puzzle).map(int[]::clone).toArray(int[][]::new);
        return new PuzzleState(clonedBoard);
    }

    public String toString()
    {
        String output = "[";
        for(int i = 0; i < this.puzzle.length;i++)
        {
            output+="[";
            for(int j = 0; j<this.puzzle[i].length; j++)
            {
                if(j!=this.puzzle[i].length-1)
                {
                    output+=this.puzzle[i][j]+",";
                }
                else
                {
                    output += this.puzzle[i][j];
                }
            }
            output+="]";
        }
        output+="]";
        return output;
    }

    public boolean isGoal()
    {
        return Arrays.deepEquals(this.puzzle, GOAL_STATE);
    }

    public boolean sameBoard(PuzzleState ps)
    {
        return Arrays.deepEquals(this.puzzle, ps.puzzle);
    }

    public ArrayList<PuzzleState> possibleMoves() {
        ArrayList<PuzzleState> moves = new ArrayList<PuzzleState>();
        if (this.prevState != 3 && (this.spacePos1 + 1) <= (puzzle.length-1)) {
            PuzzleState newState = this.clone();
            newState.puzzle[this.spacePos1][this.spacePos2] = this.puzzle[this.spacePos1 + 1][this.spacePos2];
            newState.puzzle[this.spacePos1 + 1][this.spacePos2] = 0;
            newState.spacePos1 = this.spacePos1 + 1;
            newState.spacePos2 = this.spacePos2;
            newState.prevState = 1;
            moves.add(newState);
        }
        if (this.prevState != 4 && (this.spacePos2 + 1) <= (puzzle[0].length-1)) {
            PuzzleState newState = this.clone();
            newState.puzzle[this.spacePos1][this.spacePos2] = this.puzzle[this.spacePos1][this.spacePos2 + 1];
            newState.puzzle[this.spacePos1][this.spacePos2 + 1] = 0;
            newState.spacePos1 = this.spacePos1;
            newState.spacePos2 = this.spacePos2 + 1;
            newState.prevState = 2;
            moves.add(newState);
        }
        if (this.prevState != 1 && (this.spacePos1 - 1) >= 0) {
            PuzzleState newState = this.clone();
            newState.puzzle[this.spacePos1][this.spacePos2] = this.puzzle[this.spacePos1 - 1][this.spacePos2];
            newState.puzzle[this.spacePos1 - 1][this.spacePos2] = 0;
            newState.spacePos1 = this.spacePos1 - 1;
            newState.spacePos2 = this.spacePos2;
            newState.prevState = 3;
            moves.add(newState);
        }
        if (this.prevState != 2 && (this.spacePos2 - 1) >= 0) {
            PuzzleState newState = this.clone();
            newState.puzzle[this.spacePos1][this.spacePos2] = this.puzzle[this.spacePos1][this.spacePos2 - 1];
            newState.puzzle[this.spacePos1][this.spacePos2 - 1] = 0;
            newState.spacePos1 = this.spacePos1;
            newState.spacePos2 = this.spacePos2 - 1;
            newState.prevState = 4;
            moves.add(newState);
        }
        return moves;
    }

    public int heuristic(int[][] puzzle)
    {

        ArrayList row = new ArrayList();
        ArrayList column = new ArrayList();

        int linearConflicts = 0;



        //adds each column in goal state to column, then runs through
        //input puzzle and looks at each element and the next element
        //if both are in column, and element n > element n+1,
        //add 1 to linearConflicts
        for(int i = 0; i < GOAL_STATE.length; i++)
        {
            for(int j = 0; j < GOAL_STATE.length; j++)
            {
                column.add(GOAL_STATE[j][i]);
            }
            Collections.addAll(row, GOAL_STATE[i]);

            for(int k = 0; k < puzzle.length-1; k++)
            {
                if(puzzle[k][i]!=0 && puzzle[k+1][i]!=0) {
                    if (column.contains(puzzle[k][i])) {
                        if (column.contains(puzzle[k + 1][i])) {
                            if (puzzle[k][i] > puzzle[k + 1][i])
                            {
                                linearConflicts+=1;
                            }
                        }
                    }
                }
                if(puzzle[i][k]!=0 && puzzle[i][k+1] != 0) {
                    if (row.contains(puzzle[i][k])) {
                        if (row.contains(puzzle[i][k + 1])) {
                            if (puzzle[i][k] > puzzle[i][k + 1]) {
                                linearConflicts += 1;
                            }
                        }
                    }
                }
            }
            column.clear();
            row.clear();
        }

        int x1;
        int x2;
        int y1;
        int y2;

        int totalDistance=0;

        boolean xyFound;
        for(x1=0; x1<puzzle.length; x1++)
        {
            for(y1=0; y1<puzzle[x1].length; y1++)
            {
                if(puzzle[x1][y1] == 0)
                {
                    break;
                }
                xyFound=false;
                for(x2=0; x2<GOAL_STATE.length; x2++)
                {
                    for(y2=0; y2<GOAL_STATE[x2].length; y2++)
                    {
                        if(puzzle[x1][y1]==GOAL_STATE[x2][y2])
                        {
                            totalDistance+=Math.abs(x1-x2)+Math.abs(y1-y2);
                            xyFound=true;
                            break;
                        }
                    }
                    if(xyFound)
                    {
                        break;
                    }
                }
            }
        }

        return totalDistance + (2 * linearConflicts);
    }
}
