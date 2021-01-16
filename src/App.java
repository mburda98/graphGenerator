import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App {

    private final JTextField textNrVert;
    private final JTextField textLevel;
    private final JTextField fileName;

    public App() {
        //String modes[] = {"Percentage filling", "Nr edges"};
        JFrame f = new JFrame();
        //f.setLayout(new GridLayout(3, 2));
        //f.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
        f.setLayout(new FlowLayout());
        f.add(new JLabel("Number of vertices"));

        textNrVert = new JTextField(10);
        textNrVert.setEditable(true);
        f.add(textNrVert);

        JRadioButton percentage = new JRadioButton("Percentage filling");
        ButtonGroup mode = new ButtonGroup();
        mode.add(percentage);
        JRadioButton edges = new JRadioButton("Nr edges");
        mode.add(edges);
        f.add(percentage);
        f.add(edges);

        f.add(new JLabel("Level of filling graph (in %)"));

        textLevel = new JTextField(10);
        textLevel.setEditable(true);
        f.add(textLevel);

        f.add(new JLabel("File name to save"));

        fileName = new JTextField(10);
        fileName.setEditable(true);
        f.add(fileName);

        JButton button = new JButton("Create graph");
        f.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int vertNum;
                int mode;
                if (percentage.isSelected()) {
                    mode = 1;
                } else if (edges.isSelected()) {
                    mode = 2;
                } else {
                    infoBox("Please choose filling method", "Mode error");
                    return;
                }
                int fillLevel;
                try {
                    vertNum = Integer.parseInt(textNrVert.getText());
                    if (vertNum < 2) {
                        infoBox("Number of vertices must be greater than 1", "Vertices error");
                        return;
                    }
                } catch (NumberFormatException err) {
                    infoBox("An error occurred while reading number of Vertices", "Vertices error");
                    return;
                }
                try {
                    fillLevel = Integer.parseInt(textLevel.getText());
                    if (mode == 1) {
                        if (fillLevel < 1 || fillLevel > 100) {
                            infoBox("Level of filling must be number from 0 to 100", "Filling error");
                            return;
                        }
                    } else {
                        if (fillLevel < 1 || fillLevel > vertNum - 1) {
                            String s = String.format("Level of filling must be number (from 1 to %d))", vertNum - 1);
                            infoBox(s, "Filling error");
                            return;
                        }
                    }
                } catch (NumberFormatException err) {
                    infoBox("An error occurred while reading input values", "Filling error");
                    return;
                }
                String file;
                if (!fileName.getText().equals("")) file = fileName.getText();
                else {
                    infoBox("Please write valid file name", "File name error");
                    return;
                }
                //Creating graph and filling
                Graph g = new Graph(vertNum);
                g.fill(mode, fillLevel);
                g.show();
                g.save(file);
            }
        });
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit program if close-window button clicked
        f.setTitle("Graph generator");
        f.setSize(300, 200);

        f.setVisible(true);

    }

    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        // Invoke the constructor to setup the GUI, by allocating an instance
        App app = new App();
        // or simply "new AWTCounter();" for an anonymous instance
        //App app = new App();
    }


//    static int readVertNum(Scanner scanner) {
//        int vertNum;
//        try {
//            System.out.println("Enter number of vertices: ");
//            vertNum = Integer.parseInt(scanner.nextLine());
//            if (vertNum < 1) {
//                throw new NumberFormatException();
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("An error occurred while reading number of Vertices");
//            vertNum = -1;
//        }
//        return vertNum;
//    }
//
//    static int readMode(Scanner scanner) {
//        int mode;
//        try {
//            System.out.println("Enter mode of filling: (1 - by % of Edges, 2 - by average level of vertex)");
//            mode = Integer.parseInt(scanner.nextLine());
//            if (mode != 1 && mode != 2) {
//                throw new NumberFormatException();
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("An error occurred while reading input values");
//            mode = -1;
//        }
//        return mode;
//    }
//
//    static int readFillingLevel(Scanner scanner, int vertNum, int mode) {
//        int fillLevel;
//        try {
//            if (mode == 1) {
//                System.out.println("Enter fill level (in %, from 1 to 100): ");
//                fillLevel = Integer.parseInt(scanner.nextLine());
//                if (fillLevel < 1 || fillLevel > 100) {
//                    throw new NumberFormatException();
//                }
//            } else { // mode = 2
//                System.out.println("Enter average level of vertex: (from 1 to " + (vertNum - 1) + ")");
//                fillLevel = Integer.parseInt(scanner.nextLine());
//                if (fillLevel < 1 || fillLevel > vertNum - 1) {
//                    throw new NumberFormatException();
//                }
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("An error occurred while reading input values");
//            fillLevel = -1;
//        }
//        return fillLevel;
//    }
}
