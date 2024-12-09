package view;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Module;
import model.Schedule;

public class CreateModulesSelectionGridView extends GridPane {

    private final ListView<Module> modulesT1NotSelected, modulesT2NotSelected,
            modulesYearLong, modulesT1Selected, modulesT2Selected;
    private final TextField creditsT1Txt, creditsT2Txt;
    private final Button moduleAddT1Btn, moduleAddT2Btn, moduleRemoveT1Btn,
            moduleRemoveT2Btn, resetBtn, submitBtn;

    private int creditHoursT1Count;
    private int creditHoursT2Count;

    public CreateModulesSelectionGridView() {

        creditHoursT1Count = 0;
        creditHoursT2Count = 0;

        //styling
        this.setVgap(15);
        this.setHgap(20);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);

        ColumnConstraints column0 = new ColumnConstraints();
        column0.setHalignment(HPos.RIGHT);
        this.getColumnConstraints().addAll(column0);

        //create labels
        Label unselectedT1Lbl = new Label("Unselected Term 1 modules");
        Label unselectedT2Lbl = new Label("Unselected Term 2 modules");
        Label yearLongLbl = new Label("Selected Year Long modules");
        Label selectedT1Lbl = new Label("Selected Term 1 modules");
        Label selectedT2Lbl = new Label("Selected Term 2 modules");
        Label term1Lbl = new Label("Term 1");
        Label term2Lbl = new Label("Term 2");
        Label creditsT1Lbl = new Label("Current term 1 credits: ");
        Label creditsT2Lbl = new Label("Current term 2 credits: ");

        //initialise ListViews
        modulesT1NotSelected = new ListView<>();
        modulesT2NotSelected = new ListView<>();
        modulesYearLong = new ListView<>();
        modulesT1Selected = new ListView<>();
        modulesT2Selected = new ListView<>();

        //setup text fields
        creditsT1Txt = new TextField();
        creditsT1Txt.setPrefWidth(53);
        creditsT2Txt = new TextField();
        creditsT2Txt.setPrefWidth(53);

        //initialise buttons
        moduleAddT1Btn = new Button("Add");
        moduleAddT1Btn.setPrefWidth(72);
        moduleAddT2Btn = new Button("Add");
        moduleAddT2Btn.setPrefWidth(72);
        moduleRemoveT1Btn = new Button("Remove");
        moduleRemoveT1Btn.setPrefWidth(72);
        moduleRemoveT2Btn = new Button("Remove");
        moduleRemoveT2Btn.setPrefWidth(72);
        resetBtn = new Button("Reset");
        resetBtn.setPrefWidth(72);
        submitBtn = new Button("Submit");
        submitBtn.setPrefWidth(72);

        HBox hbox1Term1 = new HBox();
        hbox1Term1.setSpacing(5);
        hbox1Term1.setAlignment(Pos.CENTER);
        hbox1Term1.getChildren().addAll(term1Lbl, moduleAddT1Btn, moduleRemoveT1Btn);

        HBox hbox2Term2 = new HBox();
        hbox2Term2.setSpacing(5);
        hbox2Term2.setAlignment(Pos.CENTER);
        hbox2Term2.getChildren().addAll(term2Lbl, moduleAddT2Btn, moduleRemoveT2Btn);

        HBox hbox3Credits = new HBox();
        hbox3Credits.setSpacing(5);
        hbox3Credits.setAlignment(Pos.CENTER);
        hbox3Credits.getChildren().addAll(creditsT1Lbl, creditsT1Txt);

        VBox vbox1Term1 = new VBox();
        vbox1Term1.setSpacing(10);
        vbox1Term1.getChildren().addAll(unselectedT1Lbl, modulesT1NotSelected, hbox1Term1,
                unselectedT2Lbl, modulesT2NotSelected, hbox2Term2, hbox3Credits);

        this.add(vbox1Term1, 0, 0);
        this.add(resetBtn, 0, 1);

        HBox hbox4Credits = new HBox();
        hbox4Credits.setSpacing(5);
        hbox4Credits.setAlignment(Pos.CENTER);
        hbox4Credits.getChildren().addAll(creditsT2Lbl, creditsT2Txt);

        VBox vbox2Main = new VBox();
        vbox2Main.setSpacing(10);
        vbox2Main.getChildren().addAll(yearLongLbl, modulesYearLong, selectedT1Lbl,
                modulesT1Selected, selectedT2Lbl, modulesT2Selected, hbox4Credits);

        vbox1Term1.prefWidthProperty().bind(this.widthProperty());
        vbox2Main.prefWidthProperty().bind(this.widthProperty());
        vbox1Term1.prefHeightProperty().bind(this.heightProperty());
        vbox2Main.prefHeightProperty().bind(this.heightProperty());

        this.add(vbox2Main, 1, 0);
        this.add(submitBtn, 1, 1);

        moduleAddT1Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Module item = modulesT1NotSelected.getSelectionModel().getSelectedItem();
                if (item != null) {
                    addModuleSelectedT1(item);
                    modulesT1NotSelected.getItems().remove(item);
                }
            }
        });
        moduleAddT2Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Module item = modulesT2NotSelected.getSelectionModel().getSelectedItem();
                if (item != null) {
                    addModuleSelectedT2(item);
                    modulesT2NotSelected.getItems().remove(item);
                }
            }
        });
        moduleRemoveT1Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Module item = modulesT1Selected.getSelectionModel().getSelectedItem();
                if (item != null) {
                    removeModuleSelectedT1(item);
                    modulesT1NotSelected.getItems().add(item);
                }
            }
        });
        moduleRemoveT2Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Module item = modulesT2Selected.getSelectionModel().getSelectedItem();
                if (item != null) {
                    removeModuleSelectedT2(item);
                    modulesT2NotSelected.getItems().add(item);
                }
            }
        });

    }

    //method to allow the controller
    public void addModulesUnselectedT1(Collection<Module> modules) {

        modulesT1NotSelected.getItems().addAll(modules);
    }

    //Add Remove
    public void addModuleSelectedT1(Module m) {
        creditHoursT1Count += m.getModuleCredits();
        modulesT1Selected.getItems().add(m);
        creditHoursUpdate();
    }

    public void removeModuleSelectedT1(Module m) {
        creditHoursT1Count -= m.getModuleCredits();
        modulesT1Selected.getItems().remove(m);
        creditHoursUpdate();
    }

    public void addModuleUnselectedT2(Module m) {
        modulesT1NotSelected.getItems().add(m);
    }

    public void addModuleSelectedT2(Module m) {
        creditHoursT2Count += m.getModuleCredits();
        modulesT2Selected.getItems().add(m);
        creditHoursUpdate();
    }

    public void removeModuleSelectedT2(Module m) {
        creditHoursT2Count -= m.getModuleCredits();
        modulesT2Selected.getItems().remove(m);
        creditHoursUpdate();
    }

    public void addModuleYearLong(Module m) {
        creditHoursT1Count += m.getModuleCredits() / 2;
        creditHoursT2Count += m.getModuleCredits() / 2;
        modulesYearLong.getItems().add(m);
        creditHoursUpdate();
    }

    //methods to retrieve the form selection/input
    public List<Module> getModulesYL() {
        return modulesYearLong.getItems();
    }

    public List<Module> getModulesSelectedT1() {
        return modulesT1Selected.getItems();
    }

    public List<Module> getModulesSelectedT2() {
        return modulesT2Selected.getItems();
    }

    public List<Module> getModulesUnSelectedT1() {
        return modulesT1NotSelected.getItems();
    }

    public List<Module> getModulesUnSelectedT2() {
        return modulesT2NotSelected.getItems();
    }

    public void addResetHandler(EventHandler<ActionEvent> handler) {
        resetBtn.setOnAction(handler);
    }

    public void addSubmitHandler(EventHandler<ActionEvent> handler) {
        submitBtn.setOnAction(handler);
    }

    public void addModulesSUList(Collection<Module> allModulesOnCourse) {
        modulesT1NotSelected.getItems().clear();
        modulesT2NotSelected.getItems().clear();
        modulesT1Selected.getItems().clear();
        modulesT2Selected.getItems().clear();
        modulesYearLong.getItems().clear();
        creditHoursT1Count = 0;
        creditHoursT2Count = 0;
        creditHoursUpdate();

        Iterator<Module> iterator = allModulesOnCourse.iterator();
        while (iterator.hasNext()) {
            Module next = iterator.next();
            if (!next.isMandatory()) {
                if (Schedule.TERM_1.equals(next.getDelivery())) {
                    modulesT1NotSelected.getItems().add(next);
                } else if (Schedule.TERM_2.equals(next.getDelivery())) {
                    modulesT2NotSelected.getItems().add(next);
                }
            } else {
                switch (next.getDelivery()) {
                    case TERM_1:
                        addModuleSelectedT1(next);
                        //selectedT1Modules.getItems().add(next);
                        break;
                    case TERM_2:
                        addModuleSelectedT2(next);
                        //selectedT2Modules.getItems().add(next);
                        break;
                    case YEAR_LONG:
                        addModuleYearLong(next);
                        //selectedYearLongModules.getItems().add(next);
                        break;
                    default:
                        break;
                }
            }
        }

    }

    private void creditHoursUpdate() {
        creditsT1Txt.setText(Integer.toString(creditHoursT1Count));
        creditsT2Txt.setText(Integer.toString(creditHoursT2Count));
    }

}
