package northern.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

	public void start(final Stage stage) throws Exception {
		final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Ui.fxml"));
		final HBox root = loader.load();
		final Scene scene = new Scene(root, 600.0, 400.0);
		final Controller controller = loader.getController();
		stage.widthProperty().addListener((observable, oldValue, newValue) -> controller.onResize(stage.getWidth(), stage.getHeight()));
		stage.heightProperty().addListener((observable, oldValue, newValue) -> controller.onResize(stage.getWidth(), stage.getHeight()));
		stage.setTitle("Northern Ui");
		stage.setScene(scene);
		stage.show();
		stage.setOnCloseRequest(event -> {
			if (VpnController.INSTANCE.isConnected())
				Notification.INSTANCE.showNotification(scene);
		});
	}
}
