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
            Text("مهم: برای اینکه بتوانید بازی کنید باید یکی از مودها را انتخاب کنید. انسان در مقابل انسان، انسان در مقابل رایانه، رایانه در مقابل رایانه که تمام توسط هوش مصنوعی انجام می شود.")
        val oneCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list1: ObservableList<Node> = oneCont.children
        list1.addAll(oneContent)
        val fourContent =
            Text("1-اول از همه باید قوانین بازی را یاد بگیرید. در بازی مقابل انسان و یا بازی در مقابل رایانه شما باید بتوانید خودتان بازی کنید.")
        val fourCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list4: ObservableList<Node> = fourCont.children
        list4.addAll(fourContent)
        val fiveContent =
            Text("2- اگر بر روی یک خانه کلیک کنید به رنگ بنفش در می آید که به معنای این است که این خانه اول یا سر می باشد.")
        val fiveCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list5: ObservableList<Node> = fiveCont.children
        list5.addAll(fiveContent)
        val sixContent =
            Text("3- اگر فقط همان مهره را می خواهید انتخاب نمایید کافیست تا دوباره بر روی آن کلیک نمایید. زمانی که به رنگ نارنجی درامد مرحله انتخاب تمام شده است.")
        val sixCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list6: ObservableList<Node> = sixCont.children
        list6.addAll(sixContent)
        val sevenContent =
            Text("4- اگر میخواهید مهره های بیشتری را انتخاب کنید، بر روی خانه اول دوبار کلیک نکنید. بر روی مهره مجاور آن کلیک کنید. می توانید بر روی مهره سوم نیز کلیک کنید. این کار زمانی که این مهره مجاور دومی باشد امکان پذیر می باشد.")
        val sevenCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list7: ObservableList<Node> = sevenCont.children
        list7.addAll(sevenContent)
        val eightContent =
            Text("5- زملنی که میخواهید با دو مهره حرکت کنید کافیست بر روی یکی از این دو مهره دوباره کلیک کنید. با انتخاب سه مهر مرحله انتخاب به طور خودکار پایان می پذیرد. با تمام شدن مرحله انتخاب مهره ها به رنگ زرد و نارنجی در می آیند.")
        val eightCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list8: ObservableList<Node> = eightCont.children
        list8.addAll(eightContent)
        val nineContent =
            Text("6- حالا می توانید بر روی یک شش ضلعی دیگر که کنار سر یا مهره آغازی مجاور باشد کلیک کنید. اگر ممکن باشد مهره ها جابجا می شوند در غیر این صورت حرکت ریست می شود.")
        val nineCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list9: ObservableList<Node> = nineCont.children
        list9.addAll(nineContent)
        val tenContent = Text("7- وقتی حرکت شما انجام شد باید منتظر حریف باشید تا حرکت خود را انجام دهد.")
        val tenCont = TextFlow()
        //Retrieving the observable list of the TextFlow Pane
        val list10: ObservableList<Node> = tenCont.children
        list10.addAll(tenContent)
        val elevenContent =
            Text("8- برای بیرون کردن مهره حریف در صورت امکان باید بر روی مهره ای که می خواهید بیرون کنید کلیک کنید. با بیرون کردن مهره شما یک امتیاز می گیرید.")
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