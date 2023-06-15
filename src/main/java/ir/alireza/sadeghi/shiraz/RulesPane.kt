package ir.alireza.sadeghi.shiraz

import javafx.geometry.Insets
import javafx.scene.text.Text

import javafx.scene.layout.GridPane
import javafx.scene.text.TextFlow
import javafx.scene.text.TextAlignment
import javafx.collections.ObservableList
import javafx.scene.Node

/*
 * A class for the GUI.
 */
class RulesPane {
    fun create(): GridPane {
        val ruleContent = GridPane()
        ruleContent.style = "-fx-font-size: 18px;"
        ruleContent.padding = Insets(50.0, 50.0, 50.0, 50.0)
        //Setting the vertical and horizontal gaps between the columns
        ruleContent.vgap = 50.0
        ruleContent.hgap = 50.0
        val oneContent = Text("برنده: کسی که شش مهره از مهره های حریف را بیرون انداخته باشد.")
        val oneCont = TextFlow()
        oneCont.textAlignment = TextAlignment.RIGHT
        //Retrieving the observable list of the TextFlow Pane
        val list1: ObservableList<Node> = oneCont.children
        list1.addAll(oneContent)
        val twoContent = Text("مهره مشکی اولین حرکت را می نماید.")
        val twoCont = TextFlow()
        twoCont.textAlignment = TextAlignment.RIGHT
        //Retrieving the observable list of the TextFlow Pane
        val list2: ObservableList<Node> = twoCont.children
        list2.addAll(twoContent)
        val threeContent = Text("شما می توانید یک، دو و یا سه مهره را همزمان حرکت دهید.")
        val threeCont = TextFlow()
        threeCont.textAlignment = TextAlignment.RIGHT
        //Retrieving the observable list of the TextFlow Pane
        val list3: ObservableList<Node> = threeCont.children
        list3.addAll(threeContent)
        val fourContent =
            Text("مهره های شما میتواند اگر جهت حرکت آزاد و یا مهره ای از حریف را به بیرون هدایت کنند تنها در یک جهت به شکل مستقیم و یا اریب حرکت کنند.")
        val fourCont = TextFlow()
        fourCont.textAlignment = TextAlignment.RIGHT
        //Retrieving the observable list of the TextFlow Pane
        val list4: ObservableList<Node> = fourCont.children
        list4.addAll(fourContent)
        val fiveContent =
            Text(" دو مهره می توانند یک مهره حریف، سه مهره دو مهره از حریف را در صورتی که در یک جهت باشند و جهت حرکت آزاد باشد و یا باعث شود که مهره به بیرون هدایت شود حرکت دهد. اگر تعداد مهره ها مساوی باشد امکان هدایت به بیرون وجود ندارد. پس یک مهره نمی تواند یک مهره را حرکت و یا به بیرون هدایت کند. همینطور 2 و 3 مهره نیز همینگونه می باشد.")
        val fiveCont = TextFlow()
        fiveCont.textAlignment = TextAlignment.RIGHT
        //Retrieving the observable list of the TextFlow Pane
        val list5: ObservableList<Node> = fiveCont.children
        list5.addAll(fiveContent)
        oneCont.padding = Insets(10.0, 10.0, 10.0, 10.0)
        twoCont.padding = Insets(10.0, 10.0, 10.0, 10.0)
        threeCont.padding = Insets(10.0, 10.0, 10.0, 10.0)
        fourCont.padding = Insets(10.0, 10.0, 10.0, 10.0)
        fiveCont.padding = Insets(10.0, 10.0, 10.0, 10.0)
        oneCont.style = "-fx-background-color: #dae7f3;"
        twoCont.style = "-fx-background-color: #dae7f3;"
        threeCont.style = "-fx-background-color: #dae7f3;"
        fourCont.style = "-fx-background-color: #dae7f3;"
        fiveCont.style = "-fx-background-color: #dae7f3;"
        ruleContent.add(oneCont, 1, 0)
        ruleContent.add(twoCont, 1, 1)
        ruleContent.add(threeCont, 1, 2)
        ruleContent.add(fourCont, 1, 3)
        ruleContent.add(fiveCont, 1, 4)
        return ruleContent
    }
}