import javafx.application.Application;
import View.MainView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.URL;


//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//      Pane root = new StackPane();
//      primaryStage.setTitle("Hello World");
//      Scene scene = new Scene(root, 800, 700);
//      scene.getStylesheets().add("View/MainScreen.CSS");
//      MainDisplayer displayer = new MainDisplayer();
//      displayer.showMenu(root,scene,primaryStage);
//
//      primaryStage.show();
//      primaryStage.setScene(scene);
//
//
//
//
//    }
public class Main extends Application {
    static Media media;
    static MediaPlayer mediaPlayer;
    @Override

    public void start(Stage primaryStage) throws Exception {
        MainView play = MainView.getInstance();
        final URL resource = getClass().getResource("ACTION.mp3");
        media = new Media(resource.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        play.init(primaryStage);
        play.StartMainScreen();
        mediaPlayer.play();


    }


//    public void start(Stage primaryStage) throws Exception {
//        MyModel model = new MyModel();
//        //model.startServers();
//        ViewModel viewModel = new ViewModel(model);
//        model.addObserver(viewModel);
//        //--------------
//        primaryStage.setTitle("My Application!");
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        Parent root = fxmlLoader.load(getClass().getResource("./View/MyView.fxml").openStream());
//        Scene scene = new Scene(root, 800, 700);
//       // scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
//        primaryStage.setScene(scene);
//        //--------------
//        MyViewController view = fxmlLoader.getController();
//        view.setStage(primaryStage);
//        view.setResizeEvent(scene);
//        view.setViewModel(viewModel);
//        viewModel.addObserver(view);
//        //--------------
//        SetStageCloseEvent(primaryStage);
//        primaryStage.show();
//    }
//
//    private void SetStageCloseEvent(Stage primaryStage) {
//        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            public void handle(WindowEvent windowEvent) {
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                Optional<ButtonType> result = alert.showAndWait();
//                if (result.get() == ButtonType.OK){
//                    // ... user chose OK
//                    // Close program
//                } else {
//                    // ... user chose CANCEL or closed the dialog
//                    windowEvent.consume();
//                }
//            }
//        });
//    }

    public static void main(String[] args) {

        launch(args);
    }
}


