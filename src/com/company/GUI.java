package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class GUI extends JFrame
{
    JPanel grid_panel;
    JLabel[][] grid_array;
    JPanel inputs;

    int[][] puzzle;

    private JButton Solve;
    private JTextField Rows;
    private JTextField Columns;
    private JButton createPuzzle;

    void initialise()
    {

        inputs = new JPanel();

        Solve = new JButton("Solve");
        Rows = new JTextField("Rows");
        Columns = new JTextField("Columns");
        createPuzzle = new JButton("Create Puzzle");
        grid_panel = new JPanel(new GridLayout(1, 1));

        createPuzzle.addActionListener(this::ActionListener);
        Solve.addActionListener(this::ActionListener);

        inputs.add(Rows, BorderLayout.SOUTH);
        inputs.add(Columns, BorderLayout.SOUTH);
        inputs.add(createPuzzle, BorderLayout.SOUTH);
        inputs.add(Solve, BorderLayout.SOUTH);

        this.add(inputs, BorderLayout.SOUTH);

        this.setSize(new Dimension(400, 400));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void ActionListener(ActionEvent e)
    {
        if(e.getSource() == createPuzzle)
        {
            int rowsInput;
            int columnsInput;
            if(isInt(Rows.getText()))
            {

                if(isInt(Columns.getText()))
                {
                    if(grid_panel!=null)
                    {
                        this.remove(grid_panel);
                        grid_panel = null;
                    }
                    rowsInput = Integer.parseInt(Rows.getText());
                    columnsInput = Integer.parseInt(Columns.getText());
                    grid_panel = new JPanel(new GridLayout(rowsInput, columnsInput));

                    grid_array = new JLabel[rowsInput][columnsInput];

                    puzzle = new int[rowsInput][columnsInput];


                    Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
                    for(int i = 0; i < grid_array.length; i++)
                    {
                        for(int j = 0; j < grid_array[i].length; j++)
                        {
                            grid_array[i][j] = new JLabel("", SwingConstants.CENTER);
                            grid_array[i][j].setFont(new Font("Serif", Font.PLAIN, 50));
                            grid_array[i][j].setBorder(border);

                            grid_array[i][j].addMouseListener(new MouseClickListener(this));
                            grid_array[i][j].setName(i+","+j);
                            grid_panel.add(grid_array[i][j]);
                        }
                    }


                    this.add(grid_panel, BorderLayout.CENTER);

                    this.revalidate();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "you need to input a valid number for Columns");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "you need to input a valid number for Rows");
            }
        }
        else
        {
            boolean validPuzzle = false;


            if(validPuzzle(puzzle))
            {
                System.out.println("working!");
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
            inputTest = JOptionPane.showInputDialog("Please enter a number between 0 and " + ((gui.grid_array.length * gui.grid_array[0].length) - 1));
            if(gui.isInt(inputTest) && Integer.parseInt(inputTest) <= ((gui.grid_array.length * gui.grid_array[0].length) - 1) && Integer.parseInt(inputTest) >= 0)
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

        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);



        gui.puzzle[x][y] = setNumber;
        gui.grid_array[x][y].setText(inputTest);
        System.out.println(Arrays.deepToString(gui.puzzle));
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

class Solver
{
    int[][] puzzle;
}