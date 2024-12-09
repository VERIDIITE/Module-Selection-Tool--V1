package view;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Module;

public class CreateModulesReservationAccordion extends Accordion {

    private final ListView<Module> modulesUnselectedT1Modules, modulesUnselectedT2Modules,
            modulesSelectedT1, modulesSelectedT2;
    private final Button moduleAddT1Btn, moduleAddT2Btn, moduleRemoveT1Btn,
            moduleRemoveT2Btn, moduleConfirmT1Btn, moduleConfirmT2Btn;

    private final TitledPane titledPane2;

    public CreateModulesReservationAccordion() {
        //styling
        this.setPadding(new Insets(20));

        //create labels
        Label lblT1Unselected = new Label("Unselected Term 1 modules");
        Label lblT2Unselected = new Label("Unselected Term 2 modules");
        Label lblT1Selected = new Label("Reserved Term 1 modules");
        Label lblT2Selected = new Label("Reserved Term 2 modules");
        Label lblterm1Credits = new Label("Reserve 30 credits worth of term 1 modules");
        Label lblterm2Credits = new Label("Reserve 30 credits worth of term 2 modules");

        //initialise ListViews
        modulesUnselectedT1Modules = new ListView<>();
        modulesUnselectedT2Modules = new ListView<>();
        modulesSelectedT1 = new ListView<>();
        modulesSelectedT2 = new ListView<>();

        //initialise buttons
        moduleAddT1Btn = new Button("Add");
        moduleAddT1Btn.setPrefWidth(72);
        moduleAddT2Btn = new Button("Add");
        moduleAddT2Btn.setPrefWidth(72);
        moduleRemoveT1Btn = new Button("Remove");
        moduleRemoveT1Btn.setPrefWidth(72);
        moduleRemoveT2Btn = new Button("Remove");
        moduleRemoveT2Btn.setPrefWidth(72);
        moduleConfirmT1Btn = new Button("Confirm");
        moduleConfirmT1Btn.setPrefWidth(72);
        moduleConfirmT2Btn = new Button("Confirm");
        moduleConfirmT2Btn.setPrefWidth(72);

        VBox vbox1Term1 = new VBox();
        vbox1Term1.getChildren().addAll(lblT1Unselected, modulesUnselectedT1Modules);
        vbox1Term1.prefWidthProperty().bind(this.widthProperty());
        vbox1Term1.prefHeightProperty().bind(this.heightProperty());

        VBox vbox2Term1 = new VBox();
        vbox2Term1.getChildren().addAll(lblT1Selected, modulesSelectedT1);
        vbox2Term1.prefWidthProperty().bind(this.widthProperty());
        vbox2Term1.prefHeightProperty().bind(this.heightProperty());

        HBox hbox1Term1 = new HBox();
        hbox1Term1.setSpacing(5);
        hbox1Term1.setAlignment(Pos.CENTER);
        hbox1Term1.getChildren().addAll(vbox1Term1, vbox2Term1);

        HBox hbox2Term1 = new HBox();
        hbox2Term1.setSpacing(5);
        hbox2Term1.setAlignment(Pos.CENTER);
        hbox2Term1.getChildren().addAll(lblterm1Credits, moduleAddT1Btn, moduleRemoveT1Btn, moduleConfirmT1Btn);

        VBox vbox3Main = new VBox(hbox1Term1, hbox2Term1);
        vbox3Main.setSpacing(10);
        vbox3Main.setPadding(new Insets(20));

        TitledPane titledPane1 = new TitledPane("Term 1 Modules", vbox3Main);

        VBox vbox4Term2 = new VBox(lblT2Unselected, modulesUnselectedT2Modules);
        VBox vbox5Term2 = new VBox(lblT2Selected, modulesSelectedT2);
        vbox4Term2.prefWidthProperty().bind(this.widthProperty());
        vbox4Term2.prefHeightProperty().bind(this.heightProperty());
        vbox5Term2.prefWidthProperty().bind(this.widthProperty());
        vbox5Term2.prefHeightProperty().bind(this.heightProperty());

        HBox hbox3Term2 = new HBox();
        hbox3Term2.setSpacing(5);
        hbox3Term2.setAlignment(Pos.CENTER);
        hbox3Term2.getChildren().addAll(vbox4Term2, vbox5Term2);

        HBox hbox4Term2 = new HBox();
        hbox4Term2.setSpacing(5);
        hbox4Term2.setAlignment(Pos.CENTER);
        hbox4Term2.getChildren().addAll(lblterm2Credits, moduleAddT2Btn, moduleRemoveT2Btn, moduleConfirmT2Btn);

        VBox vbox6Term2 = new VBox();
        vbox6Term2.setSpacing(10);
        vbox6Term2.setPadding(new Insets(20));
        vbox6Term2.getChildren().addAll(hbox3Term2, hbox4Term2);

        titledPane2 = new TitledPane("Term 2 Modules", vbox6Term2);

        getPanes().addAll(titledPane1, titledPane2);
        setExpandedPane(titledPane1);

        moduleAddT1Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Module selectedItem = modulesUnselectedT1Modules.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    modulesSelectedT1.getItems().add(selectedItem);
                    modulesUnselectedT1Modules.getItems().remove(selectedItem);
                }
            }
        });
        moduleAddT2Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Module selectedItem = modulesUnselectedT2Modules.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    modulesSelectedT2.getItems().add(selectedItem);
                    modulesUnselectedT2Modules.getItems().remove(selectedItem);
                }
            }
        });
        moduleRemoveT1Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Module selectedItem = modulesSelectedT1.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    modulesUnselectedT1Modules.getItems().add(selectedItem);
                    modulesSelectedT1.getItems().remove(selectedItem);
                }
            }
        });
        moduleRemoveT2Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Module selectedItem = modulesSelectedT2.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    modulesUnselectedT2Modules.getItems().add(selectedItem);
                    modulesSelectedT2.getItems().remove(selectedItem);
                }
            }
        });

    }

    public void addModulesUnSelectedT1(List<Module> unSelectedT1) {

        modulesUnselectedT1Modules.getItems().clear();
        modulesSelectedT1.getItems().clear();

        unSelectedT1.forEach((module) -> {
            modulesUnselectedT1Modules.getItems().add(module);
        });

    }

    public void addModulesUnSelectedT2(List<Module> unSelectedT2) {
        modulesUnselectedT2Modules.getItems().clear();
        modulesSelectedT2.getItems().clear();

        unSelectedT2.forEach((module) -> {
            modulesUnselectedT2Modules.getItems().add(module);
        });
    }

    public void addReserveHandlerT1(EventHandler<ActionEvent> handler) {
        moduleConfirmT1Btn.setOnAction(handler);
    }

    public void addReserveHandlerT2(EventHandler<ActionEvent> handler) {
        moduleConfirmT2Btn.setOnAction(handler);
    }

    public ObservableList<Module> getModulesReservedT1() {
        return modulesSelectedT1.getItems();
    }

    public ObservableList<Module> getModulesReservedT2() {
        return modulesSelectedT2.getItems();
    }

    public void changeAccordion() {
        setExpandedPane(titledPane2);
    }

}
