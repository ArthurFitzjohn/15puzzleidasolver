package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class GUI extends JFrame
{
    JPanel centre;
    JPanel input_grid;
    JLabel[][] initial_array;
    JPanel goal_grid;
    JLabel[][] goal_array;
    JPanel inputs;


    int[][] initial_puzzle;
    int[][] goal_puzzle;

    private JButton Solve;
    private JTextField Rows;
    private JTextField Columns;
    private JButton createPuzzle;

    void initialise()
    {

        inputs = new JPanel();

        centre = new JPanel();
        Solve = new JButton("Solve");
        Rows = new JTextField("Rows");
        Columns = new JTextField("Columns");
        createPuzzle = new JButton("Create Puzzle");
        input_grid = new JPanel(new GridLayout(1, 1));
        goal_grid = new JPanel(new GridLayout(1, 1));

        createPuzzle.addActionListener(this::ActionListener);
        Solve.addActionListener(this::ActionListener);

        inputs.add(Rows, BorderLayout.SOUTH);
        inputs.add(Columns, BorderLayout.SOUTH);
        inputs.add(createPuzzle, BorderLayout.SOUTH);
        inputs.add(Solve, BorderLayout.SOUTH);

        this.add(centre);
        this.add(inputs, BorderLayout.SOUTH);

        this.setSize(new Dimension(400, 400));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void ActionListener(ActionEvent e) {
        if(e.getSource() == createPuzzle)
        {
            int rowsInput;
            int columnsInput;
            if(isInt(Rows.getText()))
            {
                if(isInt(Columns.getText()))
                {
                    if(input_grid!=null)
                    {
                        centre.remove(input_grid);
                        input_grid = null;
                    }
                    if(goal_grid!=null)
                    {
                        centre.remove(goal_grid);
                        goal_grid = null;
                    }
                    rowsInput = Integer.parseInt(Rows.getText());
                    columnsInput = Integer.parseInt(Columns.getText());
                    input_grid = new JPanel(new GridLayout(rowsInput, columnsInput));
                    goal_grid = new JPanel(new GridLayout(rowsInput, columnsInput));

                    //input_grid.setPreferredSize(new Dimension(100,100));
                    //goal_grid.setPreferredSize(new Dimension(100,100));

                    initial_array = new JLabel[rowsInput][columnsInput];
                    goal_array = new JLabel[rowsInput][columnsInput];

                    initial_puzzle = new int[rowsInput][columnsInput];
                    goal_puzzle = new int[rowsInput][columnsInput];


                    Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
                    for(int i = 0; i < initial_array.length; i++)
                    {
                        for(int j = 0; j < initial_array[i].length; j++)
                        {
                            initial_array[i][j] = new JLabel("", SwingConstants.CENTER);
                            initial_array[i][j].setFont(new Font("Serif", Font.PLAIN, 50));
                            initial_array[i][j].setBorder(border);

                            initial_array[i][j].addMouseListener(new MouseClickListener(this));
                            initial_array[i][j].setName("i,"+i+","+j);
                            initial_array[i][j].setPreferredSize(new Dimension(100,100));
                            input_grid.add(initial_array[i][j]);

                            goal_array[i][j] = new JLabel("", SwingConstants.CENTER);
                            goal_array[i][j].setFont(new Font("Serif", Font.PLAIN, 50));
                            goal_array[i][j].setBorder(border);

                            goal_array[i][j].addMouseListener(new MouseClickListener(this));
                            goal_array[i][j].setName("g,"+i+","+j);
                            goal_array[i][j].setPreferredSize(new Dimension(100,100));
                            goal_grid.add(goal_array[i][j]);
                        }
                    }

                    input_grid.setBorder(border);
                    goal_grid.setBorder(border);

                    centre.add(input_grid, BorderLayout.EAST);
                    centre.add(goal_grid, BorderLayout.WEST);

                    this.add(centre);

                    this.pack();
                    this.revalidate();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "you need to input a valid number for columns");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "you need to input a valid number for rows");
            }
        }
        else
        {
            if(validPuzzle(initial_puzzle) && validPuzzle(goal_puzzle))
            {
                //System.out.println("working!");
                //System.out.println(Arrays.deepToString(initial_puzzle));
                //System.out.println(Arrays.deepToString(goal_puzzle));
                PrintWriter output = null;
                File outFile = new File("outputAstarChallengeWeek.txt");
                try {
                    output = new PrintWriter(outFile);
                }
                catch(Exception ex)
                {
                    System.out.println(ex);
                }
                PuzzleState.INITIAL_STATE = initial_puzzle;
                PuzzleState.GOAL_STATE = goal_puzzle;
                System.out.println(Arrays.deepToString(PuzzleState.INITIAL_STATE) + "I");
                System.out.println(Arrays.deepToString(PuzzleState.GOAL_STATE)+"G");
                Solver puzzle = new Solver(PuzzleState.INITIAL_STATE);
                JOptionPane.showMessageDialog(null,"Please wait for puzzle to solve (Solve button will depress), the moves you should make will be output to a file");

                if(PuzzleState.INITIAL_STATE.length <= 3 && PuzzleState.INITIAL_STATE[0].length <= 3) {
                    puzzle.solve(output);
                }
                else
                {
                    puzzle.idaSolve(output);
                }
                output.close();
                JOptionPane.showMessageDialog(null,"Problem solved, output to file");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please fill the puzzle to be solved first, you cannot have duplicates");
            }
        }
    }

    public boolean validPuzzle(int[][] puzzle)
    {
        boolean valid = false;
        ArrayList<Integer> comparisonArr = new ArrayList<>();
        Set comparisonSet = new HashSet();
        for(int i = 0; i < puzzle.length; i++)
        {
            for(int j = 0; j < puzzle[i].length; j++)
            {
                comparisonArr.add(puzzle[i][j]);
                comparisonSet.add(puzzle[i][j]);
            }
        }

        if(comparisonArr.size() == comparisonSet.size())
        {
            valid = true;
        }
        return valid;
    }

    public boolean isInt(String in)
    {
        try
        {
            Integer.parseInt(in);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}

class MouseClickListener implements MouseListener
{

    private GUI gui;

    MouseClickListener(GUI gui)
    {
        this.gui = gui;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int setNumber=0;
        String inputTest="";
        boolean isNum = false;

        while(isNum == false)
        {
            inputTest = JOptionPane.showInputDialog("Please enter a number between 0 and " + ((gui.initial_array.length * gui.initial_array[0].length) - 1));
            if(gui.isInt(inputTest) && Integer.parseInt(inputTest) <= ((gui.initial_array.length * gui.initial_array[0].length) - 1) && Integer.parseInt(inputTest) >= 0)
            {
                setNumber = Integer.parseInt(inputTest);
                isNum = true;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "please enter a valid number");
            }
        }



        String name = e.getComponent().getName();
        String[] xy = name.split(",");
        int x = Integer.parseInt(xy[1]);
        int y = Integer.parseInt(xy[2]);

        if(xy[0].equals("i")) {
            gui.initial_puzzle[x][y] = setNumber;
            gui.initial_array[x][y].setText(inputTest);
            //System.out.println(Arrays.deepToString(gui.initial_puzzle));
        }
        else if(xy[0].equals("g"))
        {
            gui.goal_puzzle[x][y] = setNumber;
            gui.goal_array[x][y].setText(inputTest);
            //System.out.println(Arrays.deepToString(gui.goal_puzzle));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}