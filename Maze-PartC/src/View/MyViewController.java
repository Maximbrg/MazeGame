package View;


import Server.Configurations;
import ViewModel.ViewModel;
import algorithms.search.Solution;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;



import java.io.File;
import java.net.URL;
import java.security.Key;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.ResourceBundle;

public class MyViewController implements IView, Observer {

    @FXML
    private Button Slove;
    protected Button Generate;
    private ViewModel viewModel;
    public TextField ld_rowsNum;
    public TextField ld_colsNum;

    public ComboBox<String> choiceBox1;
    private Stage stage;
    //button sol;
    private boolean mazegenerated = false;
    public MazeDisplayer mazeDisplayer;
    public Boolean Sec = false;
    public Boolean first = true;


    public void KeyPressed(KeyEvent key) {
        viewModel.moveCharacter(key);
        key.consume();
    }


    public void setViewModel(ViewModel v) {
        this.viewModel = v;
    }

    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            mazeDisplayer.setCharacterPosition(viewModel.getCharacterPositionRow(), viewModel.getCharacterPositionColumn());
            //  Generate.setDisable(false);
            this.zoom(mazeDisplayer);
        }
    }

    public void setStage(Stage pri) {
        this.stage = pri;
    }


    public void setResizeEvent(Scene scene) {
        long width = 0;
        long height = 0;
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                //System.out.println("Width: " + newSceneWidth);
                mazeDisplayer.setWidth((scene.getWidth() * 0.75));
                mazeDisplayer.DrawMaze();
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                //   System.out.println("Height: " + newSceneHeight);
                mazeDisplayer.setHeight((scene.getHeight() * 0.85));
                mazeDisplayer.DrawMaze();
            }
        });
    }

    public void ClickedLoadMaze() {
        if (mazegenerated) {
            FileChooser lFile = new FileChooser();
            lFile.setInitialDirectory(new File(System.getProperty("user.home")));
            lFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Maze ", "*.MGmaze"));
            File load = lFile.showOpenDialog(stage);

            if (load != null && load.canRead()) {
                viewModel.viewLoadMaze(load);
                mazeDisplayer.SetLoadMaze(viewModel.getMaze());
                //mazeDisplayer.DeletePos();
                mazeDisplayer.DrawPos(viewModel.getCharacterPositionRow(), viewModel.getCharacterPositionColumn());
            }
            int row = Integer.parseInt(ld_rowsNum.getText());
            int col = Integer.parseInt(ld_colsNum.getText());

        }
    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }


    public void ClickedSaveMaze() {
        if (mazegenerated) {
            FileChooser sFile = new FileChooser();
//            File file = new File("S:\\MazeDir");
//            if (!file.exists()) {
//                if (file.mkdir()) {
//                    System.out.println("Directory is created!");
//                } else {
//                    System.out.println("Failed to create directory!");
//                }
//            }
            sFile.setInitialDirectory(new File(System.getProperty("user.home")));
            sFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MGmaze", "*.MGmaze"));
            sFile.setInitialFileName("");

            //  File f = sFile.showSaveDialog(stage);
            viewModel.viewSaveMaze(sFile.showSaveDialog(stage));
        } else {
            showAlert("You Have To Generate a New Maze Before.");
        }
    }


    public void ClickedGenerateMaze() {
        mazegenerated = true;
        try {
            int row = Integer.parseInt(ld_rowsNum.getText());
            int col = Integer.parseInt(ld_colsNum.getText());
            viewModel.generateMaze(row, col);
            mazeDisplayer.SetMaze(viewModel.getMaze());
            //  Generate.setDisable(true);
            Sec = true;

        } catch (NumberFormatException e) {
            //warning
        }
    }

    public void ClickedSolveMaze() {
        if (mazegenerated) {
            Solution sol = viewModel.solveMaze();
            mazeDisplayer.DrawSol(sol);
        } else {
            showAlert("You Have To Generate a New Maze Before.");
        }


    }


    public void About(javafx.event.ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("About");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            Scene scene = new Scene(root, 400, 350);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
        }
    }

    public void getChoice(ComboBox<String> c) {
        Properties config = new Properties();


    }


    public void SetProperties() {
        try {
            Button apply = new Button("Apply");
            choiceBox1 = new javafx.scene.control.ComboBox<>();

            choiceBox1.getItems().add("DFS");
            choiceBox1.getItems().add("BestFirstSearch");
            choiceBox1.getItems().add("BFS");
            choiceBox1.setValue(("Chose search algo"));

            Stage stage = new Stage();
            stage.setTitle("set");
            FXMLLoader fxmlLoader = new FXMLLoader();
            StackPane root = new StackPane();// fxmlLoader.load(getClass().getResource("properties.fxml").openStream());
            apply.setOnAction(e -> refresh(stage,choiceBox1));
            Scene scene = new Scene(root, 400, 350);
            stage.setScene(scene);
            VBox v = new VBox();
            //Label text = new Label("Chose search algo");
            v.getChildren().add(choiceBox1);
            //  v.getChildren().add(text);
            v.setAlignment(Pos.CENTER);
            v.getChildren().add(apply);
            //     apply.setOnAction(e -> getChoice(choiceBox1)getChoice(choiceBox1));
            root.getChildren().add(v);


            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
            System.out.println("AAAAAAA");
        }


//        try {
//            Stage stage = new Stage();
//            stage.setTitle("set");
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            Parent root = fxmlLoader.load(getClass().getResource("Properties.fxml").openStream());
//            Scene scene = new Scene(root, 400, 350);
//            stage.setScene(scene);
//            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
//            stage.show();
//        } catch (Exception e) {
//        }
    }

    public void zoom(MazeDisplayer pane) {
        pane.setOnScroll(
                new EventHandler<ScrollEvent>() {
                    @Override
                    public void handle(ScrollEvent event) {
                        if (event.isControlDown()) {
                            double zoomFactor = 1.05;
                            double deltaY = event.getDeltaY();

                            if (deltaY < 0) {
                                zoomFactor = 0.95;
                            }
                            pane.setScaleX(pane.getScaleX() * zoomFactor);
                            pane.setScaleY(pane.getScaleY() * zoomFactor);
                            event.consume();
                        }
                    }
                });
    }

    private void refresh(Stage st,ComboBox<String> choiceBox1) {

        Configurations.EditSearchAlgo(choiceBox1.getValue());
        st.close();

    }
}