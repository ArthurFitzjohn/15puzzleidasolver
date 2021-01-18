package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GUI extends JFrame
{
    JPanel grid_panel;
    JPanel[][] grid_array;
    JPanel inputs;

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

        inputs.add(Rows);
        inputs.add(Columns);
        inputs.add(createPuzzle);
        inputs.add(Solve);

        this.add(inputs, BorderLayout.SOUTH);

        //frame.add(grid_panel);

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

                    grid_array = new JPanel[rowsInput][columnsInput];

                    for(int i = 0; i < grid_array.length; i++)
                    {
                        for(int j = 0; j < grid_array[i].length; j++)
                        {
                            grid_array[i][j] = new JPanel();
                            if((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
                                grid_array[i][j].setBackground(Color.BLACK);
                            } else {
                                grid_array[i][j].setBackground(Color.WHITE);
                            }
                            grid_panel.add(grid_array[i][j]);
                        }
                    }


                    this.add(grid_panel);

                    //this.repaint();
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
