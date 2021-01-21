package com.company;

import java.util.ArrayList;
import java.util.Arrays;

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
        if ((this.spacePos1 + 1) <= (puzzle.length-1)) {
            PuzzleState newState = this.clone();
            newState.puzzle[this.spacePos1][this.spacePos2] = this.puzzle[this.spacePos1 + 1][this.spacePos2];
            newState.puzzle[this.spacePos1 + 1][this.spacePos2] = 0;
            newState.spacePos1 = this.spacePos1 + 1;
            newState.spacePos2 = this.spacePos2;
            moves.add(newState);
        }
        if ((this.spacePos2 + 1) <= (puzzle[0].length-1)) {
            PuzzleState newState = this.clone();
            newState.puzzle[this.spacePos1][this.spacePos2] = this.puzzle[this.spacePos1][this.spacePos2 + 1];
            newState.puzzle[this.spacePos1][this.spacePos2 + 1] = 0;
            newState.spacePos1 = this.spacePos1;
            newState.spacePos2 = this.spacePos2 + 1;
            moves.add(newState);
        }
        if ((this.spacePos1 - 1) >= 0) {
            PuzzleState newState = this.clone();
            newState.puzzle[this.spacePos1][this.spacePos2] = this.puzzle[this.spacePos1 - 1][this.spacePos2];
            newState.puzzle[this.spacePos1 - 1][this.spacePos2] = 0;
            newState.spacePos1 = this.spacePos1 - 1;
            newState.spacePos2 = this.spacePos2;
            moves.add(newState);
        }
        if ((this.spacePos2 - 1) >= 0) {
            PuzzleState newState = this.clone();
            newState.puzzle[this.spacePos1][this.spacePos2] = this.puzzle[this.spacePos1][this.spacePos2 - 1];
            newState.puzzle[this.spacePos1][this.spacePos2 - 1] = 0;
            newState.spacePos1 = this.spacePos1;
            newState.spacePos2 = this.spacePos2 - 1;
            moves.add(newState);
        }
        return moves;
    }

    public int heuristic(int[][] puzzle)
    {
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
                xyFound=false;
                for(x2=0; x2<GOAL_STATE.length; x2++)
                {
                    /*if(puzzle[x1][y1] == 0)
                    {
                        break;
                    }*/
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

        return totalDistance;
    }
}
