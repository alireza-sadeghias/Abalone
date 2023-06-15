package ir.alireza.sadeghi.shiraz

import ir.alireza.sadeghi.shiraz.*
import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.effect.DropShadow
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.*
import javafx.stage.Screen
import org.apache.logging.log4j.LogManager

import ir.alireza.sadeghi.shiraz.ai.ReadMatrix
import ir.alireza.sadeghi.shirazimport.GameGui
import javafx.scene.Scene
import javafx.stage.Stage
import java.io.FileInputStream
import kotlin.Throws
import kotlin.jvm.JvmStatic

/*
 * Sets up the whole game. Creates the start screen and links everything.
 */
class Main : Application() {
    private var shadow = DropShadow()
    private var gameGui: GameGui? = null

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        Main.logger.trace("Running on: " + System.getProperty("os.name"))
        if (System.getProperty("os.name").contains("Windows")) {
            ReadMatrix.slash = "\\"
        } else {
            ReadMatrix.slash = "//"
        }
        primaryStage.title = "Team 1 -  Project 2.1"
        val sliderBox = VBox()
        sliderBox.alignment = Pos.CENTER
        sliderBox.prefHeight = 500.0
        sliderBox.spacing = 50.0
        val chooseP = HBox()
        chooseP.alignment = Pos.CENTER
        chooseP.spacing = 20.0
        val dropShadow = DropShadow()
        dropShadow.radius = 5.0
        dropShadow.offsetX = 3.0
        dropShadow.offsetY = 3.0
        dropShadow.color = Color.color(0.4, 0.5, 0.5)
        val title = Text()
        title.effect = dropShadow
        title.isCache = true
        title.x = 10.0
        title.y = 70.0
        title.fill = Color.web("0x3b596d")
        title.text = "آبالون: لذت ببرید!"
        title.font = Font.font(null, FontWeight.BOLD, 70.0)
        val settings = Button("نحوه بازی")
        settings.styleClass.add("menu_items")
        val rules = Button("قوانین")
        rules.styleClass.add("menu_items")
        val menuBox = HBox()
        menuBox.alignment = Pos.CENTER
        menuBox.prefWidth = 400.0
        menuBox.spacing = 20.0
        menuBox.children.addAll(settings, rules)
        sliderBox.children.addAll(title, menuBox, chooseP)

        //Add game mode images
        val hvh = ImageView()
        hvh.image = Image(ClassLoader.getSystemResource("PvP.png")?.openStream())
        val hvc = ImageView()
        hvc.image = Image(ClassLoader.getSystemResource("PvC.png").openStream())
        val cvc = ImageView()
        cvc.image = Image(ClassLoader.getSystemResource("CvC.png").openStream())

        //Add game mode labels
        val modeOneLabel = Label("انسان در مقابل انسان")
        //modeOneLabel.setStyle("-fx-font: 20 arial;");
        val modeTwoLabel = Label("انسان در مقابل رایانه")
        //modeTwoLabel.setStyle("-fx-font: 20 arial;");
        val modeThreeLabel = Label("رایانه در مقابل رایانه")

        //Defining layouts
        val mainContainer = VBox()
        val modeOneContainer = VBox()
        val modeTwoContainer = VBox()
        val modeThreeContainer = VBox()
        val modeBox = HBox()
        modeOneContainer.styleClass.add("modeButton")
        modeOneContainer.padding = Insets(10.0, 10.0, 10.0, 10.0)
        modeTwoContainer.styleClass.add("modeButton")
        modeTwoContainer.padding = Insets(10.0, 10.0, 10.0, 10.0)
        modeThreeContainer.styleClass.add("modeButton")
        modeThreeContainer.padding = Insets(10.0, 10.0, 10.0, 10.0)

        //some fancy shadow effects
        modeOneContainer.onMouseEntered = EventHandler { t: MouseEvent? -> hvh.effect = shadow }
        modeOneContainer.onMouseExited = EventHandler { t: MouseEvent? -> hvh.effect = null }
        modeTwoContainer.onMouseEntered = EventHandler { t: MouseEvent? -> hvc.effect = shadow }
        modeTwoContainer.onMouseExited = EventHandler { t: MouseEvent? -> hvc.effect = null }
        modeThreeContainer.onMouseEntered = EventHandler { t: MouseEvent? -> cvc.effect = shadow }
        modeThreeContainer.onMouseExited = EventHandler { t: MouseEvent? -> cvc.effect = null }
        modeOneContainer.children.addAll(hvh, modeOneLabel)
        modeOneContainer.alignment = Pos.CENTER
        modeTwoContainer.children.addAll(hvc, modeTwoLabel)
        modeTwoContainer.alignment = Pos.CENTER
        modeThreeContainer.children.addAll(cvc, modeThreeLabel)
        modeThreeContainer.alignment = Pos.CENTER
        modeBox.children.addAll(
            createSpacer(),
            modeOneContainer,
            createSpacer(),
            modeTwoContainer,
            createSpacer(),
            modeThreeContainer,
            createSpacer()
        )
        modeBox.padding = Insets(0.0, 0.0, 0.0, 0.0)
        val pane = StackPane()
        mainContainer.children.addAll(sliderBox, modeBox)
        pane.children.addAll(mainContainer)
        val style = "-fx-background-color: lightblue;"
        pane.style = style
        val scene = Scene(pane, 3000.0, 2000.0)
        primaryStage.scene = scene
        val primaryScreenBounds = Screen.getPrimary().visualBounds
        //set Stage boundaries to visible bounds of the main screen
        primaryStage.x = primaryScreenBounds.minX
        primaryStage.y = primaryScreenBounds.minY
        primaryStage.width = primaryScreenBounds.width
        primaryStage.height = primaryScreenBounds.height


        //Show stage
        primaryStage.show()
        val back = Button("بازگشت")
        val backCont = HBox()
        backCont.children.add(back)
        backCont.alignment = Pos.CENTER_RIGHT
        backCont.padding = Insets(50.0, 50.0, 50.0, 50.0)
        val back2 = Button("بازگشت")
        val backCont2 = HBox()
        backCont2.children.add(back2)
        backCont2.alignment = Pos.CENTER_RIGHT
        backCont2.padding = Insets(50.0, 50.0, 50.0, 50.0)

        //Rules:
        val rulesMainCont = VBox()
        val rulesTitleCont = HBox()
        rulesTitleCont.alignment = Pos.CENTER
        val rulesTitle = Text("قوانین")
        rulesTitle.style = "-fx-font-size: 30px;"
        rulesTitleCont.children.add(rulesTitle)
        rulesTitleCont.prefHeight = 100.0
        val rulSubTitleCont = HBox()
        rulSubTitleCont.alignment = Pos.CENTER
        val ruleSubTitle = Text("قوانین بازی به صورت زیر هستند:")
        rulSubTitleCont.children.add(ruleSubTitle)
        rulSubTitleCont.prefHeight = 50.0
        ruleSubTitle.style = "-fx-font-size: 20px;"
        val ruleMainCont = RulesPane().create()
        rulesMainCont.children.addAll(rulesTitleCont, rulSubTitleCont, ruleMainCont, backCont2)
        val ruleScene = Scene(rulesMainCont)
        val back3 = Button("بازگشت")
        val backCont3 = HBox()
        backCont3.children.add(back3)
        backCont3.alignment = Pos.CENTER_RIGHT
        backCont3.padding = Insets(50.0, 50.0, 50.0, 50.0)


        //Settings:
        val setMainCont = VBox()
        val settingsTitleCont = HBox()
        settingsTitleCont.alignment = Pos.CENTER
        val settingsTitle = Text("نحوه بازی")
        settingsTitle.style = "-fx-font-size: 30px;"
        settingsTitleCont.children.add(settingsTitle)
        settingsTitleCont.prefHeight = 100.0
        val setSubTitleCont = HBox()
        setSubTitleCont.alignment = Pos.CENTER
        val setSubTitle = Text("چگونه بازی کنید:")
        setSubTitleCont.children.add(setSubTitle)
        setSubTitleCont.prefHeight = 50.0
        setSubTitle.style = "-fx-font-size: 20px;"
        val SetCont = SettingsPane().create()
        setMainCont.children.addAll(settingsTitleCont, setSubTitleCont, SetCont, backCont3)
        val setScene = Scene(setMainCont)
        rules.onAction = EventHandler<ActionEvent> { e: ActionEvent? ->
            primaryStage.scene = ruleScene
            back2.onAction = EventHandler { f: ActionEvent? -> primaryStage.scene = scene }
        }
        settings.onAction = EventHandler<ActionEvent> { e: ActionEvent? ->
            primaryStage.scene = setScene
            back3.onAction = EventHandler { f: ActionEvent? -> primaryStage.scene = scene }
        }

        //changing to the board scene
        modeOneContainer.isPickOnBounds = true // allows click on transparent areas
        modeOneContainer.onMouseClicked = EventHandler { e: MouseEvent? ->
            gameGui = GameGui()
            gameGui!!.start(primaryStage)
        }
        modeTwoContainer.isPickOnBounds = true // allows click on transparent areas
        modeTwoContainer.onMouseClicked = EventHandler<MouseEvent> { event: MouseEvent? ->
            Move.player1AI = false
            if (GameData.numberPlayers == 2) {
                Move.player2AI = true
            } else {
                Move.player2AI = true
                Move.player3AI = true
            }
            gameGui = GameGui()
            Move.PvC = true
            gameGui!!.start(primaryStage)
        }
        modeThreeContainer.isPickOnBounds = true // allows click on transparent areas
        modeThreeContainer.onMouseClicked = EventHandler<MouseEvent> { event: MouseEvent? ->
            Move.player1AI = true
            Move.player2AI = true
            if (GameData.numberPlayers == 3) {
                Move.player3AI = true
            }
            gameGui = GameGui()
            gameGui!!.setAIAI(true)
            gameGui!!.start(primaryStage)
        }
    }

    private fun createSpacer(): Node {
        val spacer = Region()
        // Make it always grow or shrink according to the available space
        HBox.setHgrow(spacer, Priority.ALWAYS)
        return spacer
    }

    companion object {
        private val logger = LogManager.getLogger(Main::class.java)
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java,*args)
        }
    }
}