package make_a_square_gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Start {
    JFrame frame;
    PiecesModel piecesModel;
    private JPanel StartFrame = new JPanel(new GridBagLayout()); // Use GridBagLayout
    private JTextField textI = new JTextField();
    private JTextField textJ = new JTextField();
    private JTextField textL = new JTextField();
    private JTextField textO = new JTextField();
    private JTextField textS = new JTextField();
    private JTextField textT = new JTextField();
    private JTextField textZ = new JTextField();
    private JButton button1 = new JButton();
    int[][] finalBoard;
    Map<Character, Integer> hashMap = new HashMap<>();

    void setHashMap() {
        hashMap.put('L', Integer.parseInt(textL.getText()));
        hashMap.put('Z', Integer.parseInt(textZ.getText()));
        hashMap.put('I', Integer.parseInt(textI.getText()));
        hashMap.put('J', Integer.parseInt(textJ.getText()));
        hashMap.put('T', Integer.parseInt(textT.getText()));
        hashMap.put('S', Integer.parseInt(textS.getText()));
        hashMap.put('O', Integer.parseInt(textO.getText()));
    }

    public Start() {
        piecesModel = new PiecesModel();
        StartFrame.setBorder(new EmptyBorder(10, 10, 10, 10));
        button1.setText("Start");
        button1.setSize(100, 50);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.anchor = GridBagConstraints.WEST;

        // Add labels and text fields to the JPanel
        addLabelAndTextField("I", textI, constraints);
        addLabelAndTextField("J", textJ, constraints);
        addLabelAndTextField("L", textL, constraints);
        addLabelAndTextField("O", textO, constraints);
        addLabelAndTextField("S", textS, constraints);
        addLabelAndTextField("T", textT, constraints);
        addLabelAndTextField("Z", textZ, constraints);

        // Add button to the JPanel
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 2; // Make the button span two columns
        constraints.anchor = GridBagConstraints.CENTER; // Center the button in its cell
        StartFrame.add(button1, constraints);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // This code will be executed when the button is clicked
                setHashMap();
                // sum of all pieces must be 4
                if (hashMap.values().stream().mapToInt(Integer::intValue).sum() != 4) {
                    JOptionPane.showMessageDialog(null, "Impossible to make a square", "Make A Square",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                frame.dispose();
                solveProblem();
            }
        });
    }

    private void addLabelAndTextField(String labelText, JTextField textField, GridBagConstraints constraints) {
        ImageIcon icon = new ImageIcon(
                "C:/Users/Mazin/Downloads/Make_A_Square-master/Make_A_Square-master/image/" + labelText + ".png");
        JLabel label = new JLabel("", icon, JLabel.LEFT);
        label.setBorder(new EmptyBorder(10, 0, 10, 20)); // Top, left, bottom, right
        textField.setPreferredSize(new Dimension(120, 30));
        // default value 0
        textField.setText("0");
        constraints.gridx = 0;
        StartFrame.add(label, constraints);
        constraints.gridx = 1;
        StartFrame.add(textField, constraints);
    }

    public void solveProblem() {
        setHashMap();
        HashMap<Integer, int[][]> sendPieces = new HashMap<>();
        int id = 0;
        for (Map.Entry<Character, Integer> set : hashMap.entrySet()) {
            int cnt = set.getValue();
            while (cnt > 0) {
                sendPieces.put(id++, piecesModel.retrievePiece(set.getKey()));
                cnt--;
            }
        }
        try {
            finalBoard = MakeASquare.Square(sendPieces);
            if (finalBoard == null) {
                JOptionPane.showMessageDialog(null, "No Solution Founded", "Make A Square", JOptionPane.ERROR_MESSAGE);
            } else {
                Paralleling.allBoards.add(finalBoard);
                ArrayList<int[][]> arr = new ArrayList<>();
                arr.addAll(Paralleling.allBoards);
                ArrayList<int[][]> arr2 = new ArrayList<>();
                // remove duplicates
                for (int i = arr.size() / 2; i < arr.size(); i++) {
                    boolean flag = true;
                    for (int j = i + 1; j < arr.size(); j++) {
                        if (Arrays.equals(arr.get(i), arr.get(j))) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag)
                        arr2.add(arr.get(i));
                }
                arr2.add(finalBoard);
                // loop over the last 50 boards
                if (arr2.size() > 50) {
                    new Timer(300, new ActionListener() {
                        int i = arr2.size() - 50;
                        Painter currentFrame = null;

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Close the previous window
                            if (currentFrame != null) {
                                currentFrame.closeWindow();
                            }

                            // Show the next window
                            currentFrame = Painter.showGUI(arr2.get(i));
                            i++;

                            if (i >= arr2.size()) {
                                ((Timer) e.getSource()).stop();
                            }
                        }
                    }).start();
                } else {
                    new Timer(300, new ActionListener() {
                        int i = 0;
                        Painter currentFrame = null;

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Close the previous window
                            if (currentFrame != null) {
                                currentFrame.closeWindow();
                            }

                            // Show the next window
                            currentFrame = Painter.showGUI(arr2.get(i));
                            i++;

                            if (i >= arr2.size()) {
                                ((Timer) e.getSource()).stop();
                            }
                        }
                    }).start();
                }
                System.out.println(arr2.size());
                // Platform.runLater(() -> paintButton(finalBoard));
                // Platform.runLater(() ->
                // paintButton(Paralleling.allBoards.iterator().next()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Start start = new Start();
        start.frame = new JFrame("Make A Square"); // Modify this line
        start.frame.setContentPane(start.StartFrame);
        start.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // open it in the middle of the screen
        start.frame.pack();
        start.frame.setLocationRelativeTo(null);
        start.frame.setVisible(true);
    }
}