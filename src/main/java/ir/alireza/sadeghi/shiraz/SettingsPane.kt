package ir.alireza.sadeghi.shiraz

import javafx.collections.ObservableList
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.layout.GridPane
import javafx.scene.text.Text
import javafx.scene.text.TextFlow

/*
 * A class for the GUI.
 */
class SettingsPane {
    fun create(): GridPane {
        val mainCont = GridPane()
        mainCont.style = "-fx-font-size: 18px;"
        mainCont.padding = Insets(50.0, 50.0, 50.0, 50.0)
        //Setting the vertical and horizontal gaps between the columns
        mainCont.vgap = 10.0
        mainCont.hgap = 50.0
        val oneContent =
            Text("Important: To be able to play the game first select the mode with which you prefer to play; human vs human or human vs computer. Computer vs computer can run a game of AI's.")
        val oneCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list1: ObservableList<Node> = oneCont.children
        list1.addAll(oneContent)
        val fourContent =
            Text("1. Firstly, you need to understand the rules of the game. See the rules. In PvP or PvC mode you need to play yourself.")
        val fourCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list4: ObservableList<Node> = fourCont.children
        list4.addAll(fourContent)
        val fiveContent =
            Text(" 2. If you click on one of the marbles then it will become purple. This means that this one is selected to be the 'head' node now. ")
        val fiveCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list5: ObservableList<Node> = fiveCont.children
        list5.addAll(fiveContent)
        val sixContent =
            Text(" 3. If you only want to select the one marble you chose before, click the marble again. Once it's orange,you know the selection step ended.")
        val sixCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list6: ObservableList<Node> = sixCont.children
        list6.addAll(sixContent)
        val sevenContent =
            Text(" 4. If you want to select more marbles, do not click on the first one twice, but click on another marble adjacent to this one. It is possible to click on the third adjacent one now as well, as long as it's adjacent to the second one.")
        val sevenCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list7: ObservableList<Node> = sevenCont.children
        list7.addAll(sevenContent)
        val eightContent =
            Text(" 5. If the selection needs to be ended at two, click on one of the two marbles again. If you choose three, the selection will be ended automatically. If you are done with selection then the marbles will be highlighted with yellow and orange colors.")
        val eightCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list8: ObservableList<Node> = eightCont.children
        list8.addAll(eightContent)
        val nineContent =
            Text(" 6. Now, you can click on another hexagon, that needs to be adjacent to the 'head'. If it's possible, it will move, otherwise, the move will be reset.")
        val nineCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list9: ObservableList<Node> = nineCont.children
        list9.addAll(nineContent)
        val tenContent = Text(" 7. Once you performed a move, you need you have to wait for your opponent to move.")
        val tenCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list10: ObservableList<Node> = tenCont.children
        list10.addAll(tenContent)
        val elevenContent =
            Text("8. To push the opponent marbles (if possible) then click on the marble that you want to push after the selection step is done. Then you gain a point.")
        val elevenCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list11: ObservableList<Node> = elevenCont.children
        list11.addAll(elevenContent)
        oneCont.padding = Insets(10.0, 10.0, 10.0, 10.0)
        fourCont.padding = Insets(10.0, 10.0, 10.0, 10.0)
        fiveCont.padding = Insets(10.0, 10.0, 10.0, 10.0)
        sixCont.padding = Insets(10.0, 10.0, 10.0, 10.0)
        sevenCont.padding = Insets(10.0, 10.0, 10.0, 10.0)
        eightCont.padding = Insets(10.0, 10.0, 10.0, 10.0)
        nineCont.padding = Insets(10.0, 10.0, 10.0, 10.0)
        tenCont.padding = Insets(10.0, 10.0, 10.0, 10.0)
        elevenCont.padding = Insets(10.0, 10.0, 10.0, 10.0)
        oneCont.style = "-fx-background-color: #dae7f3;"
        fourCont.style = "-fx-background-color: #dae7f3;"
        fiveCont.style = "-fx-background-color: #dae7f3;"
        sixCont.style = "-fx-background-color: #dae7f3;"
        sevenCont.style = "-fx-background-color: #dae7f3;"
        eightCont.style = "-fx-background-color: #dae7f3;"
        nineCont.style = "-fx-background-color: #dae7f3;"
        tenCont.style = "-fx-background-color: #dae7f3;"
        elevenCont.style = "-fx-background-color: #dae7f3;"
        mainCont.add(oneCont, 1, 0)
        mainCont.add(fourCont, 1, 3)
        mainCont.add(fiveCont, 1, 4)
        mainCont.add(sixCont, 1, 5)
        mainCont.add(sevenCont, 1, 6)
        mainCont.add(eightCont, 1, 7)
        mainCont.add(nineCont, 1, 8)
        mainCont.add(tenCont, 1, 9)
        mainCont.add(elevenCont, 1, 10)
        return mainCont
    }
}