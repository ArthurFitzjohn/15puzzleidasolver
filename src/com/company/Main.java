package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args)
    {
	    //create gui - will have text file to set rows and columns, which then creates panel of that size
        //panel can then be clicked on and set to numbers wanted
        //have solve button, which when clicked converts panel to arrayList, and runs solver, outputting to text area
        //must be able to solve 2x3, 3x3 puzzles, try to also solve 3x4
        //can limit max moves to 50

        GUI test = new GUI();
        test.initialise();
    }
}
