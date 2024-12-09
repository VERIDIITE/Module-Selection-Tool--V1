package view;

import java.util.Iterator;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Module;

public class CreateOverviewSectionGridView extends GridPane {

    private final TextArea studentData, modulesSelected, modulesReserved;

    private final Button saveBtn;

    public CreateOverviewSectionGridView() {

        //styling
        this.setVgap(15);
        this.setHgap(20);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.CENTER);

        //initialise TextAreas
        studentData = new TextArea("Data 1");
        modulesSelected = new TextArea("Data 2");
        modulesReserved = new TextArea("Date 3");

        studentData.prefWidthProperty().bind(this.widthProperty());
        studentData.prefHeightProperty().bind(this.heightProperty());
        modulesSelected.prefWidthProperty().bind(this.widthProperty());
        modulesSelected.prefHeightProperty().bind(this.heightProperty());
        modulesReserved.prefWidthProperty().bind(this.widthProperty());
        modulesReserved.prefHeightProperty().bind(this.heightProperty());

        //initialise buttons
        saveBtn = new Button("Save Overview");
        saveBtn.setPrefWidth(102);

        HBox hboxButton = new HBox(saveBtn);
        hboxButton.setAlignment(Pos.CENTER);

        this.add(studentData, 0, 0, 2, 1);
        this.add(modulesSelected, 0, 1);
        this.add(modulesReserved, 1, 1);
        this.add(hboxButton, 0, 2, 2, 1);

    }

    public void populateStudentDetails(String s) {
        studentData.setText(s);
    }

    public void addModulesSelectedDetails(Set<Module> allSelectedModules) {
        modulesSelected.setText("Selected Modules\n..................\n");
        for (Iterator<Module> iterator = allSelectedModules.iterator(); iterator.hasNext();) {
            Module next = iterator.next();
            modulesSelected.appendText(next.actualToString() + "\n");
        }
    }

    public void addModulesReservedDetails(Set<Module> allReservedModules) {
        modulesReserved.setText("Reserved Modules:\n..................\n");
        for (Iterator<Module> iterator = allReservedModules.iterator(); iterator.hasNext();) {
            Module next = iterator.next();
            modulesReserved.appendText(next.actualToString() + "\n");
        }
    }

    public void addSaveOverviewHandler(EventHandler<ActionEvent> handler) {
        saveBtn.setOnAction(handler);
    }

}
