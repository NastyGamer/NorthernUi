package northern.ui

import javafx.application.Application
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

fun main() {
    Application.launch(Main::class.java)
}

class Main : Application() {

    override fun start(stage: Stage) {
        val loader = FXMLLoader(this.javaClass.classLoader.getResource("Ui.fxml"))
        val root = loader.load<HBox>()
        val scene = Scene(root, 600.0, 400.0)
        val controller = loader.getController<Controller>()
        stage.widthProperty().addListener { _, _, _ -> controller.onResize(stage.width, stage.height) }
        stage.heightProperty().addListener { _, _, _ -> controller.onResize(stage.width, stage.height) }
        stage.title = "Northern Ui"
        stage.scene = scene
        stage.show()
    }

}