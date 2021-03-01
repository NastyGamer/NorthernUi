package northern.ui

import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.HBox
import javafx.stage.Modality
import javafx.stage.Stage
import kotlin.io.path.ExperimentalPathApi


object Notification {

    @ExperimentalPathApi
    fun showNotification(scene: Scene) {
        val stage = Stage()
        val root = FXMLLoader.load<Parent>(this.javaClass.classLoader.getResource("Notification.fxml"))
        ((root.childrenUnmodifiable[1] as HBox).children[0] as Button).onMouseClicked =
            EventHandler {
                VpnController.disconnect()
                stage.close()
            }
        ((root.childrenUnmodifiable[1] as HBox).children[1] as Button).onMouseClicked =
            EventHandler { stage.close() }
        stage.scene = Scene(root, 300.0, 80.0)
        stage.title = "Notification"
        stage.isResizable = false
        stage.initModality(Modality.WINDOW_MODAL)
        stage.initOwner(scene.window)
        stage.showAndWait()
    }
}