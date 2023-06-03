package ir.alireza.sadeghi.shiraz;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/*
 * A class for the GUI.
 */

public class RulesPane {
    public GridPane create(){
        GridPane ruleContent = new GridPane();
        ruleContent.setStyle("-fx-font-size: 18px;");
        ruleContent.setPadding(new Insets(50, 50, 50, 50));
        //Setting the vertical and horizontal gaps between the columns
        ruleContent.setVgap(50);
        ruleContent.setHgap(50);


        Text oneContent = new Text("برنده: کسی که شش مهره از مهره های حریف را بیرون انداخته باشد.");
        TextFlow oneCont = new TextFlow();
        oneCont.setTextAlignment(TextAlignment.RIGHT);
        //Retrieving the observable list of the TextFlow Pane
        ObservableList list1 = oneCont.getChildren();
        list1.addAll(oneContent);

        Text twoContent = new Text("مهره مشکی اولین حرکت را می نماید.");
        TextFlow twoCont = new TextFlow();
        twoCont.setTextAlignment(TextAlignment.RIGHT);
        //Retrieving the observable list of the TextFlow Pane
        ObservableList list2 = twoCont.getChildren();
        list2.addAll(twoContent);

        Text threeContent = new Text("شما می توانید یک، دو و یا سه مهره را همزمان حرکت دهید.");
        TextFlow threeCont = new TextFlow();
        threeCont.setTextAlignment(TextAlignment.RIGHT);
        //Retrieving the observable list of the TextFlow Pane
        ObservableList list3 = threeCont.getChildren();
        list3.addAll(threeContent);

        Text fourContent = new Text("مهره های شما میتواند اگر جهت حرکت آزاد و یا مهره ای از حریف را به بیرون هدایت کنند تنها در یک جهت به شکل مستقیم و یا اریب حرکت کنند.");
        TextFlow fourCont = new TextFlow();
        fourCont.setTextAlignment(TextAlignment.RIGHT);
        //Retrieving the observable list of the TextFlow Pane
        ObservableList list4 = fourCont.getChildren();
        list4.addAll(fourContent);

        Text fiveContent = new Text(" دو مهره می توانند یک مهره حریف، سه مهره دو مهره از حریف را در صورتی که در یک جهت باشند و جهت حرکت آزاد باشد و یا باعث شود که مهره به بیرون هدایت شود حرکت دهد. اگر تعداد مهره ها مساوی باشد امکان هدایت به بیرون وجود ندارد. پس یک مهره نمی تواند یک مهره را حرکت و یا به بیرون هدایت کند. همینطور 2 و 3 مهره نیز همینگونه می باشد.");
        TextFlow fiveCont = new TextFlow();
        fiveCont.setTextAlignment(TextAlignment.RIGHT);
        //Retrieving the observable list of the TextFlow Pane
        ObservableList list5 = fiveCont.getChildren();
        list5.addAll(fiveContent);

        oneCont.setPadding(new Insets(10, 10, 10, 10));
        twoCont.setPadding(new Insets(10, 10, 10, 10));
        threeCont.setPadding(new Insets(10, 10, 10, 10));
        fourCont.setPadding(new Insets(10, 10, 10, 10));
        fiveCont.setPadding(new Insets(10, 10, 10, 10));


        oneCont.setStyle("-fx-background-color: #dae7f3;");
        twoCont.setStyle("-fx-background-color: #dae7f3;");
        threeCont.setStyle("-fx-background-color: #dae7f3;");
        fourCont.setStyle("-fx-background-color: #dae7f3;");
        fiveCont.setStyle("-fx-background-color: #dae7f3;");


        ruleContent.add(oneCont, 1, 0);
        ruleContent.add(twoCont, 1, 1);
        ruleContent.add(threeCont, 1, 2);
        ruleContent.add(fourCont, 1, 3);
        ruleContent.add(fiveCont, 1, 4);

        return ruleContent;
    }
}
