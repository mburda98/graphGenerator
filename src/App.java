import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App extends JFrame {
    private JTextField textNrVert;
    private JTextField textLevel;
    private JButton button;

    public App() {
        JFrame frame = new JFrame("JFrame Example");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        //Number of vertices
        JLabel label = new JLabel("Number of vertices");
        label.setPreferredSize(new Dimension(50, 50));
        JTextField nrVertices = new JTextField();
        nrVertices.setPreferredSize(new Dimension(50, 50));
        nrVertices.setEditable(true);

        JButton button = new JButton();
        button.setText("Button");
        panel.add(label);
        panel.add(nrVertices);
        //panel.add(button);
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);





        Container cp = getContentPane();
        cp.setLayout(new GridLayout(3, 2));

        this.add(new JLabel("Number of vertices"));

        textNrVert = new JTextField();
        textNrVert.setEditable(true);
        this.add(textNrVert);

        this.add(new JLabel("Level of filling graph (in %)"));

        textLevel = new JTextField();
        textLevel.setEditable(true);
        this.add(textLevel);

        button = new JButton("Create graph");
        add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int vertNum = Integer.parseInt(textNrVert.getText());
                int mode = 1;
                int fillLevel = Integer.parseInt(textLevel.getText());
                //Creating graph and
                Graph g = new Graph(vertNum);
                g.fill(mode, fillLevel);
                g.show();
                g.save();
                //System.out.println("END");
            }
        });
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit program if close-window button clicked
        setTitle("Graph generator");
        setSize(400, 200);

        setVisible(true);
    }

    public static void main(String[] args) {
        App app = new App();
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
