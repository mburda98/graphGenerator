import javax.swing.*;
import java.awt.*;

public class Hello {
    public static void main(String[] args){
        Graph g = new Graph(10);
        g.show();
        g.fill(70);
        g.show();
        //g.save();
    }
}
