import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class App extends Application {

    public TextField nrVert = null;
    public TextField fill_prob = null;
    public Button graph = null;
    public Button fcButton = null;
    public RadioButton acyclic = null;
    public RadioButton cyclic = null;
    public RadioButton directed = null;
    public RadioButton undirected = null;
    public CheckBox doubleWeight = null;
    public ComboBox<String> type = new ComboBox<>();
    public Label file = null;

    public int vertNum;
    public float fill;
    public boolean acyc;
    public boolean direct;
    public boolean doubleWeighted;
    public int graph_type;
    public String fileSave = "";

    @FXML
    public void fileChooser() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("txt", "*.txt"));
        File f = fc.showOpenDialog(null);

        if (f != null) {
            file.setText("Selected file: " + f.getAbsolutePath());
            fileSave = f.getAbsolutePath();
        }
    }

    @FXML
    public void graph() {
        if (!validateInput()) return;
        acyc = acyclic.isSelected();
        direct = directed.isSelected();
        doubleWeighted = doubleWeight.isSelected();
        graph_type = selectType();
        generate();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Graph saved", ButtonType.OK);
        alert.showAndWait();
    }

    public boolean validateInput() {
        int vertNum;
        int fillLevel;
        String errMsg = "";
        // Check nr vertices
        try {
            vertNum = Integer.parseInt(nrVert.getText());
            if (vertNum < 2 || vertNum > 5000) {
                errMsg = "Number of vertices must be greater than 1";
            } else {
                this.vertNum = vertNum;
            }
        } catch (NumberFormatException err) {
            errMsg = "An error occurred while reading number of Vertices";
        }
        // Check fill level (or probability value)
        try {
            fillLevel = Integer.parseInt(fill_prob.getText());
            if (fillLevel < 0 || fillLevel > 100) {
                errMsg = "Level of filling must be number from 0 to 100";
            } else {
                fill = fillLevel;
            }
        } catch (NumberFormatException err) {
            errMsg = "An error occurred while reading filling level";
        }
        if (type.getValue() == null) {
            errMsg = "Choose graph type";
        }
        if (fileSave.equals("")) {
            errMsg = "Choose file to save";
        }

        if (!(errMsg.equals(""))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, errMsg, ButtonType.OK);
            alert.showAndWait();
            return false;
        } else {
            System.out.println("Input ok");
            return true;
        }

    }

    public int selectType() {
        if (type.getValue().equals("Sieć przepływowa")) {
            return 1;
        }
        if (type.getValue().equals("Drzewa")) {
            return 6;
        }
        if (type.getValue().equals("Grafy pełne")) {
            return 2;
        }
        if (type.getValue().equals("Grafy spójne losowe")) {
            return 3;
        }
        if (type.getValue().equals("Grafy losowe")) {
            return 4;
        }
        if (type.getValue().equals("Grafy Erdosa-Renyi'ego")) {
            return 5;
        }
        return -1;
    }

    public void generate() {
        long startTime = System.currentTimeMillis();
        //Creating graph and filling
        if(!direct && acyc) graph_type = 6; // Protect weird graph parameters
        Graph g = new Graph(vertNum, direct, acyc, doubleWeighted);
        long inTime = System.currentTimeMillis() - startTime;
        System.out.println(inTime);
        g.fill(graph_type, fill);
        //g.showMatrix();
        g.save(fileSave);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println(endTime);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL(Paths.get(System.getProperty("user.dir")).toUri() + "src/GUI.fxml"));
        Pane pane = loader.load();

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
