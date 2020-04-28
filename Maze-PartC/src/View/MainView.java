package View;

import Model.MyModel;
import ViewModel.ViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class MainView {
    private static MainView ourInstance = new MainView();
    public static MainView getInstance() {
        return ourInstance;
    }
    private static Stage primaryStage;
    public void init(Stage primaryStage){this.primaryStage=primaryStage;}

    private MainView(){}



    public  Scene StartPlay(){
        MyModel model = new MyModel();
        //model.startServers();
        ViewModel viewModel = new ViewModel(model);
        model.addObserver(viewModel);
        //--------------
       // primaryStage.setTitle("My Application!");
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            Parent root = fxmlLoader.load(getClass().getResource("MyView.fxml").openStream());

            Scene scene = new Scene(root, 800, 700);
            scene.getStylesheets().add("View/PlayScreen.CSS");
        // scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        //--------------
        MyViewController view = fxmlLoader.getController();
        view.setStage(primaryStage);
        view.setResizeEvent(scene);
        view.setViewModel(viewModel);
        viewModel.addObserver(view);
        //--------------
        SetStageCloseEvent();
       // primaryStage.show();
        return scene;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void SetStageCloseEvent() {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    // Close program
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();
                }
            }
        });
    }

    public void StartMainScreen() throws Exception{
        Pane root = new StackPane();
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 800, 700);
        scene.getStylesheets().add("View/MainScreen.CSS");
        MainDisplayer displayer = new MainDisplayer();
        displayer.showMenu(root,scene);

        primaryStage.show();
        primaryStage.setScene(scene);

    }

}
