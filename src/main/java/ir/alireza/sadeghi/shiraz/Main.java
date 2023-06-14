package ir.alireza.sadeghi.shiraz;

import ir.alireza.sadeghi.shiraz.ai.ReadMatrix;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;

/*
 * Sets up the whole game. Creates the start screen and links everything.
 */

public class Main extends Application {

    private final static Logger logger = LogManager.getLogger(Main.class);
    DropShadow shadow = new DropShadow();
    public GameGui Game;

    public void start(Stage primaryStage) throws Exception {

        logger.trace("Running on: "+System.getProperty("os.name"));
        if(System.getProperty("os.name").contains("Windows")){
            ReadMatrix.slash = "\\";
        }else{
            ReadMatrix.slash = "//";
        }

        primaryStage.setTitle("Team 1 -  Project 2.1");
        
        VBox sliderBox = new VBox();
        sliderBox.setAlignment(Pos.CENTER);
        sliderBox.setPrefHeight(500);
        sliderBox.setSpacing(50);
        
        HBox chooseP = new HBox();
        chooseP.setAlignment(Pos.CENTER);
        chooseP.setSpacing(20);
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));

        Text title = new Text();
        title.setEffect(dropShadow);
        title.setCache(true);
        title.setX(10.0);
        title.setY(70.0);
        title.setFill(Color.web("0x3b596d"));
        title.setText("آبالون: لذت ببرید!");
        title.setFont(Font.font(null, FontWeight.BOLD, 70));

        Button settings = new Button("نحوه بازی");
        settings.getStyleClass().add("menu_items");
        Button rules = new Button("قوانین");
        rules.getStyleClass().add("menu_items");

        HBox menuBox = new HBox();
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPrefWidth(400);
        menuBox.setSpacing(20);
        menuBox.getChildren().addAll(settings, rules);

        sliderBox.getChildren().addAll(title, menuBox, chooseP);

        //Add game mode images
        ImageView iv_1 = new ImageView();
        iv_1.setImage(new Image(new FileInputStream("PvP.png")));
        ImageView iv_2 = new ImageView();
        iv_2.setImage(new Image(new FileInputStream("PvC.png")));
        ImageView iv_3 = new ImageView();
        iv_3.setImage(new Image(new FileInputStream("CvC.png")));

        //Add game mode labels
        Label modeOneLabel = new Label("انسان در مقابل انسان");
        //modeOneLabel.setStyle("-fx-font: 20 arial;");
        Label modeTwoLabel = new Label("انسان در مقابل رایانه");
        //modeTwoLabel.setStyle("-fx-font: 20 arial;");
        Label modeThreeLabel = new Label("رایانه در مقابل رایانه");

        //Defining layouts
        VBox mainContainer = new VBox();
        VBox modeOneContainer = new VBox();
        VBox modeTwoContainer = new VBox();
        VBox modeThreeContainer = new VBox();

        HBox modeBox = new HBox();

        modeOneContainer.getStyleClass().add("modeButton" );
        modeOneContainer.setPadding(new Insets(10,10,10,10));
        modeTwoContainer.getStyleClass().add("modeButton");
        modeTwoContainer.setPadding(new Insets(10,10,10,10));
        modeThreeContainer.getStyleClass().add("modeButton");
        modeThreeContainer.setPadding(new Insets(10,10,10,10));

        //some fancy shadow effects
        modeOneContainer.setOnMouseEntered(t -> iv_1.setEffect(shadow));
        modeOneContainer.setOnMouseExited(t -> iv_1.setEffect(null));
        modeTwoContainer.setOnMouseEntered(t -> iv_2.setEffect(shadow));
        modeTwoContainer.setOnMouseExited(t -> iv_2.setEffect(null));
        modeThreeContainer.setOnMouseEntered(t -> iv_3.setEffect(shadow));
        modeThreeContainer.setOnMouseExited(t -> iv_3.setEffect(null));

        modeOneContainer.getChildren().addAll(iv_1,modeOneLabel );
        modeOneContainer.setAlignment(Pos.CENTER);
        modeTwoContainer.getChildren().addAll(iv_2,modeTwoLabel);
        modeTwoContainer.setAlignment(Pos.CENTER);
        modeThreeContainer.getChildren().addAll(iv_3,modeThreeLabel);
        modeThreeContainer.setAlignment(Pos.CENTER);

        modeBox.getChildren().addAll(createSpacer(),modeOneContainer,createSpacer(), modeTwoContainer,createSpacer(), modeThreeContainer,createSpacer());
        modeBox.setPadding(new Insets(0,0,0,0));

        StackPane pane = new StackPane();
        mainContainer.getChildren().addAll(sliderBox, modeBox );
        pane.getChildren().addAll(mainContainer);
        String style = "-fx-background-color: lightblue;";
        pane.setStyle(style);

        Scene scene = new Scene(pane,3000,2000);

        primaryStage.setScene(scene);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        //set Stage boundaries to visible bounds of the main screen
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());


        //Show stage
        primaryStage.show();
        Button back = new Button("بازگشت");
        HBox backCont = new HBox();
        backCont.getChildren().add(back);
        backCont.setAlignment(Pos.CENTER_RIGHT);
        backCont.setPadding(new Insets(50, 50, 50, 50));

        Button back2 = new Button("بازگشت");
        HBox backCont2 = new HBox();
        backCont2.getChildren().add(back2);
        backCont2.setAlignment(Pos.CENTER_RIGHT);
        backCont2.setPadding(new Insets(50, 50, 50, 50));

        //Rules:
        VBox rulesMainCont = new VBox();
        HBox rulesTitleCont = new HBox();
        rulesTitleCont.setAlignment(Pos.CENTER);
        Text rulesTitle = new Text("قوانین");
        rulesTitle.setStyle("-fx-font-size: 30px;");
        rulesTitleCont.getChildren().add(rulesTitle);
        rulesTitleCont.setPrefHeight(100);

        HBox rulSubTitleCont = new HBox();
        rulSubTitleCont.setAlignment(Pos.CENTER);
        Text ruleSubTitle = new Text("قوانین بازی به صورت زیر هستند:");
        rulSubTitleCont.getChildren().add(ruleSubTitle);
        rulSubTitleCont.setPrefHeight(50);
        ruleSubTitle.setStyle("-fx-font-size: 20px;");
        GridPane ruleMainCont = new RulesPane().create();

        rulesMainCont.getChildren().addAll(rulesTitleCont, rulSubTitleCont, ruleMainCont,backCont2);
        Scene ruleScene = new Scene(rulesMainCont);


        Button back3 = new Button("بازگشت");
        HBox backCont3 = new HBox();
        backCont3.getChildren().add(back3);
        backCont3.setAlignment(Pos.CENTER_RIGHT);
        backCont3.setPadding(new Insets(50, 50, 50, 50));


        //Settings:
        VBox setMainCont = new VBox();
        HBox settingsTitleCont = new HBox();
        settingsTitleCont.setAlignment(Pos.CENTER);
        Text settingsTitle = new Text("نحوه بازی");
        settingsTitle.setStyle("-fx-font-size: 30px;");
        settingsTitleCont.getChildren().add(settingsTitle);
        settingsTitleCont.setPrefHeight(100);
        HBox setSubTitleCont = new HBox();
        setSubTitleCont.setAlignment(Pos.CENTER);
        Text setSubTitle = new Text("چگونه بازی کنید:");
        setSubTitleCont.getChildren().add(setSubTitle);
        setSubTitleCont.setPrefHeight(50);
        setSubTitle.setStyle("-fx-font-size: 20px;");
        GridPane SetCont = new SettingsPane().create();

        setMainCont.getChildren().addAll(settingsTitleCont, setSubTitleCont,SetCont, backCont3);

        Scene setScene = new Scene(setMainCont);


        rules.setOnAction(e ->{
            primaryStage.setScene(ruleScene);
            back2.setOnAction(f-> primaryStage.setScene(scene));
        });

        settings.setOnAction(e ->{
            primaryStage.setScene(setScene);
            back3.setOnAction(f-> primaryStage.setScene(scene));
        });

        //changing to the board scene
        modeOneContainer.setPickOnBounds(true); // allows click on transparent areas
        modeOneContainer.setOnMouseClicked((MouseEvent e) -> {
            Game = new GameGui();
            Game.start(primaryStage);
            
        });

        modeTwoContainer.setPickOnBounds(true); // allows click on transparent areas
        modeTwoContainer.setOnMouseClicked(event -> {
            Move.player1AI = false;
            if (GameData.numberPlayers == 2) {
                Move.player2AI = true;
            } else {
                Move.player2AI = true;
                Move.player3AI = true;
            }

            Game = new GameGui();
            Move.PvC = true;
            Game.start(primaryStage);
        });

        modeThreeContainer.setPickOnBounds(true); // allows click on transparent areas
        modeThreeContainer.setOnMouseClicked(event -> {
            Move.player1AI = true;
            Move.player2AI = true;

            if (GameData.numberPlayers ==3) {
                Move.player3AI = true;
            }

            Game = new GameGui();
            Game.setAIAI(true);
            Game.start(primaryStage);
        });
    }
    
    private Node createSpacer() {
        final Region spacer = new Region();
        // Make it always grow or shrink according to the available space
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
