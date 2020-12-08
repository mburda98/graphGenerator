import java.util.*;

public class Hello {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int vertNum = readVertNum(scanner);
        if (vertNum == -1) {
            System.exit(-1);
        }
        int mode = readMode(scanner);
        if (mode == -1) {
            System.exit(-1);
        }
        int fillLevel = readFillingLevel(scanner, vertNum, mode);
        if (fillLevel == -1) {
            System.exit(-1);
        }

        Graph g = new Graph(vertNum);
        g.fill(mode, fillLevel);
        g.show();
        g.save();
    }

    static int readVertNum(Scanner scanner) {
        int vertNum;
        try {
            System.out.println("Enter number of vertices: ");
            vertNum = Integer.parseInt(scanner.nextLine());
            if (vertNum < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("An error occurred while reading number of Vertices");
            vertNum = -1;
        }
        return vertNum;
    }

    static int readMode(Scanner scanner) {
        int mode;
        try {
            System.out.println("Enter mode of filling: (1 - by % of Edges, 2 - by average level of vertex)");
            mode = Integer.parseInt(scanner.nextLine());
            if (mode != 1 && mode != 2) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("An error occurred while reading input values");
            mode = -1;
        }
        return mode;
    }

    static int readFillingLevel(Scanner scanner, int vertNum, int mode) {
        int fillLevel;
        try {
            if (mode == 1) {
                System.out.println("Enter fill level (in %, from 1 to 100): ");
                fillLevel = Integer.parseInt(scanner.nextLine());
                if (fillLevel < 1 || fillLevel > 100) {
                    throw new NumberFormatException();
                }
            } else { // mode = 2
                System.out.println("Enter average level of vertex: (from 1 to " + (vertNum - 1) + ")");
                fillLevel = Integer.parseInt(scanner.nextLine());
                if (fillLevel < 1 || fillLevel > vertNum - 1) {
                    throw new NumberFormatException();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("An error occurred while reading input values");
            fillLevel = -1;
        }
        return fillLevel;
    }
}
