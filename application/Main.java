package application;


import Controller.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loder = new FXMLLoader(getClass().getResource("../View/login.fxml"));
		Parent root = loder.load();
		RootController rootController = loder.getController();
		rootController.primaryStage = primaryStage;
		
		
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("add.css").toString());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
