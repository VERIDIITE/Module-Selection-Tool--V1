package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;


public class ModuleSelectionToolRootPane extends BorderPane {

	private CreateStudentProfilePane cspp;
	private CreateModulesSelectionGridView cmsg;
        private CreateModulesReservationAccordion cmra;
        private CreateOverviewSectionGridView cosg;
	private ModuleSelectionToolMenuBar mstmb;
	private TabPane tp;
	
	public ModuleSelectionToolRootPane() {
		//create tab pane and disable tabs from being closed
		tp = new TabPane();
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		//create panes
		cspp = new CreateStudentProfilePane();
		cmsg = new CreateModulesSelectionGridView();
                cmra = new CreateModulesReservationAccordion();
                cosg = new CreateOverviewSectionGridView();
		
		//create tabs with panes added
		Tab t1 = new Tab("Create Profile", cspp);
		Tab t2 = new Tab("Select Modules", cmsg);
		Tab t3 = new Tab("Reserve Modules", cmra);
		Tab t4 = new Tab("Overview Section", cosg);
		
		//add tabs to tab pane
		tp.getTabs().addAll(t1,t2, t3, t4);
		
		//create menu bar
		mstmb = new ModuleSelectionToolMenuBar();
		
		//add menu bar and tab pane to this root pane
		this.setTop(mstmb);
		this.setCenter(tp);
		
	}

	//methods allowing sub-containers to be accessed by the controller.
	public CreateStudentProfilePane getCreateStudentProfilePane() {
		return cspp;
	}
	public CreateModulesSelectionGridView getCreateModuleSelectionPane() {
		return cmsg;
	}
	public CreateModulesReservationAccordion getCreateReserveModulesPane() {
		return cmra;
	}
	public CreateOverviewSectionGridView getCreateOverviewSectionPane() {
		return cosg;
	}
	
	public ModuleSelectionToolMenuBar getModuleSelectionToolMenuBar() {
		return mstmb;
	}
	
	//method to allow the controller to change tabs
	public void changeTab(int index) {
		tp.getSelectionModel().select(index);
	}
}
