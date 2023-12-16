package com.example.make_a_square_gui;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;

public class Painter extends JFrame {
    private JFrame frame;
    private JPanel Main_Panel;
    private JPanel panel_1;
    private JPanel panel_2;
    private JPanel panel_3;
    private JPanel panel_4;
    private JPanel panel_5;
    private JPanel panel_6;
    private JPanel panel_7;
    private JPanel panel_8;
    private JPanel panel_9;
    private JPanel panel_10;
    private JPanel panel_11;
    private JPanel panel_12;
    private JPanel panel_13;
    private JPanel panel_14;
    private JPanel panel_15;
    private JPanel panel_16;
    private JPanel[][] panels;

    public Painter(int[][] board) {

        Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW };
        Main_Panel = new JPanel(new GridLayout(4, 4));

        panels = new JPanel[4][4];
        Dimension panelSize = new Dimension(75, 70);
        Border blackBorder = new LineBorder(Color.BLACK);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                panels[i][j] = new JPanel();
                panels[i][j].setPreferredSize(panelSize);
                if (board[i][j] == -1)
                    panels[i][j].setBackground(Color.WHITE);
                else
                    panels[i][j].setBackground(colors[board[i][j]]);
                panels[i][j].setBorder(blackBorder);
                Main_Panel.add(panels[i][j]);
            }
        }
    }

    // public static void main(String[] args) {
    // int[][] board = {
    // { 0, 1, 2, 3 },
    // { 1, 2, 3, 0 },
    // { 2, 3, 0, 1 },
    // { 3, 0, 1, 2 }
    // };
    // showGUI(board);
    // }

    public static Painter showGUI(int[][] board) {
        Painter test = new Painter(board);
        // Change panel colors
        createAndShowGUI(test);
        return test;
    }

    public void closeWindow() {
        if (frame != null) {
            frame.dispose();
        }
    }

    public void changePanelColors(int[][] board) {
        Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW };
        // board is 4x4
        for (int i = 0; i < 4; i++) {
            int[] row = board[i];
            for (int j = 0; j < 4; j++) {
                panels[i][j].setBackground(colors[row[j]]);
                System.out.print(row[j] + " ");
            }
            System.out.println("");
        }
    }

    private static void createAndShowGUI(Painter test) {
        // Create and set up the window.
        test.frame = new JFrame("Make A Square");
        test.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create and set up the content pane.
        // Test test = new Test();
        test.Main_Panel.setOpaque(true); // content panes must be opaque
        test.frame.setContentPane(test.Main_Panel);

        // Display the window.
        test.frame.pack();
        test.frame.setLocationRelativeTo(null);
        test.frame.setVisible(true);
    }
}
