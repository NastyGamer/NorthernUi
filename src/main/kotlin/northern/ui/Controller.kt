package northern.ui

import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class Controller {

    @FXML
    lateinit var countryList: ListView<String>

    @FXML
    lateinit var statusCircle: Circle

    @FXML
    lateinit var infoTextArea: TextArea

    @FXML
    lateinit var disconnectButton: Button

    @FXML
    lateinit var newServerButton: Button

    @FXML
    lateinit var vBox: VBox

    @FXML
    fun initialize() {
        countryList.items.addAll(VpnController.servers.servers.keys)
        countryList.selectionModel.selectedItemProperty().addListener { _, _, new -> VpnController.connect(new) }
        disconnectButton.onMouseClicked = EventHandler { VpnController.disconnect() }
        newServerButton.onMouseClicked =
            EventHandler {
                countryList.selectionModel.selectedItem?.let {
                    if (VpnController.isConnected())
                        VpnController.connect(it)
                }
            }
        GlobalScope.launch {
            while (true) {
                statusCircle.fill = if(VpnController.isConnected()) Color.GREEN else Color.RED
                infoTextArea.text = VpnController.getSessionInfo()
                delay(5000)
            }
        }
    }

    fun onResize(width: Double, height: Double) {
        countryList.setPrefSize(80 percentOf width, height)
        vBox.setPrefSize(20 percentOf width, height)
        infoTextArea.minWidth = 20 percentOf width
        disconnectButton.minWidth = 20 percentOf width
        newServerButton.minWidth = 20 percentOf width
    }

    private infix fun Int.percentOf(base: Double) = (base / 100.0) * this.coerceIn(1, 99)
}