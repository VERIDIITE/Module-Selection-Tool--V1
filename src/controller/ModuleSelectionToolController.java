package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import model.Course;
import model.Schedule;
import model.Module;
import model.Name;
import model.StudentProfile;
import view.CreateOverviewSectionGridView;
import view.CreateModulesReservationAccordion;
import view.CreateModulesSelectionGridView;
import view.ModuleSelectionToolRootPane;
import view.CreateStudentProfilePane;
import view.ModuleSelectionToolMenuBar;

public class ModuleSelectionToolController {

	// fields to be used throughout class
	private final ModuleSelectionToolRootPane view;
	private StudentProfile model;

	private final CreateStudentProfilePane cspp;
	private final CreateModulesSelectionGridView cmsg;
	private final CreateModulesReservationAccordion cmsa;
	private final CreateOverviewSectionGridView cosg;
	private final ModuleSelectionToolMenuBar mstmb;

	public ModuleSelectionToolController(ModuleSelectionToolRootPane view, StudentProfile model) {
		// initialise view and model fields
		this.view = view;
		this.model = model;

		// initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		cmsg = view.getCreateModuleSelectionPane();
		cmsa = view.getCreateReserveModulesPane();
		cosg = view.getCreateOverviewSectionPane();
		mstmb = view.getModuleSelectionToolMenuBar();

		// add courses to combobox in create student profile pane using the
		// generateAndReturnCourses helper method below
		cspp.addCoursesToComboBox(generateAndReturnCourses());

		// attach event handlers to view using private helper method
		this.attachEventHandlers();
	}

	// helper method - used to attach event handlers
	private void attachEventHandlers() {
		// attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());
		cmsg.addResetHandler(new ResetHandler());
		cmsg.addSubmitHandler(new SubmitHandler());
		cmsa.addReserveHandlerT1(new ConfirmHandlerT1());
		cmsa.addReserveHandlerT2(new ConfirmHandlerT2());

		cosg.addSaveOverviewHandler(new SaveOverviewHandler());

		// attach an event handler to the menu bar that closes the application
		mstmb.addExitHandler(e -> System.exit(0));
		mstmb.addSaveHandler(new SaveProfileHandler());
		mstmb.addLoadHandler(new LoadProfileHandler());
		mstmb.addAboutHandler(e -> {
			Alert al = new Alert(Alert.AlertType.INFORMATION);
			al.setHeaderText("About");
			al.setContentText("Select modules for final year students");
			al.show();

		});
	}

	// event handler , which can be used for creating a profile
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			Course selectedCourse = cspp.getSelectedCourse();
			String pNumber = cspp.getStudentPnumber();
			Name name = cspp.getStudentName();
			String email = cspp.getStudentEmail();
			LocalDate date = cspp.getStudentDate();

			if (pNumber.isEmpty() || name.getFirstName().isEmpty() || name.getFamilyName().isEmpty() || email.isEmpty()
					|| date == null) {
				showAlert("Student Profile", "Input valid information!", Alert.AlertType.ERROR);
			} else {
				model.setStudentCourse(selectedCourse);
				model.setStudentPnumber(pNumber);
				model.setStudentName(name);
				model.setStudentEmail(email);
				model.setSubmissionDate(date);

				cmsg.addModulesSUList(model.getStudentCourse().getAllModulesOnCourse());
				view.changeTab(1);

			}
		}

	}

	private class ResetHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			model.clearSelectedModules();
			Course selectedCourse = model.getStudentCourse();
			cmsg.addModulesSUList(selectedCourse.getAllModulesOnCourse());

		}
	}

	private class SubmitHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			model.clearSelectedModules();

			List<Module> selectedYLModules = cmsg.getModulesYL();
			selectedYLModules.forEach((m) -> {
				model.addSelectedModule(m);
			});
			List<Module> selectedT1Modules = cmsg.getModulesSelectedT1();
			selectedT1Modules.forEach((m) -> {
				model.addSelectedModule(m);
			});
			List<Module> selectedT2Modules = cmsg.getModulesSelectedT2();
			selectedT2Modules.forEach((m) -> {
				model.addSelectedModule(m);
			});

			List<Module> unSelectedT1Modules = cmsg.getModulesUnSelectedT1();
			List<Module> unSelectedT2Modules = cmsg.getModulesUnSelectedT2();

			cmsa.addModulesUnSelectedT1(unSelectedT1Modules);
			cmsa.addModulesUnSelectedT2(unSelectedT2Modules);

			view.changeTab(2);

		}
	}

	private class ConfirmHandlerT1 implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			cmsa.changeAccordion();

		}
	}

	private class ConfirmHandlerT2 implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			model.clearReservedModules();

			ObservableList<Module> reservedT1Modules = cmsa.getModulesReservedT1();
			reservedT1Modules.forEach((m) -> {
				model.addReservedModule(m);
			});

			ObservableList<Module> reservedT2Modules = cmsa.getModulesReservedT2();
			reservedT2Modules.forEach((m) -> {
				model.addReservedModule(m);
			});

			cosg.populateStudentDetails(model.actualToString());
			cosg.addModulesSelectedDetails(model.getAllSelectedModules());
			cosg.addModulesReservedDetails(model.getAllReservedModules());

			view.changeTab(3);

		}
	}

	private class SaveOverviewHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			PrintWriter pw;
			try {
				pw = new PrintWriter("overview.txt");
				pw.write(model.actualToString());

				pw.write("\nSelected Modules:\n");

				for (Module next : model.getAllSelectedModules()) {
					pw.write(next.actualToString() + "\n");
				}

				pw.write("\nReserved Modules:\n");

				for (Module next : model.getAllReservedModules()) {
					pw.write(next.actualToString() + "\n");
				}

				pw.close();
				showAlert("Save Overview", "Successfull!", Alert.AlertType.INFORMATION);
			} catch (FileNotFoundException ex) {
				showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
			}

		}
	}

	private class SaveProfileHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(new FileOutputStream("StudentProfile.bin"));
				oos.writeObject(model);
				oos.close();
				showAlert("Save Profile", "Successfull!", Alert.AlertType.INFORMATION);
			} catch (FileNotFoundException ex) {
				showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
			} catch (IOException ex) {
				showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
			}

		}
	}

	private class LoadProfileHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(new FileInputStream("StudentProfile.bin"));
				model = (StudentProfile) ois.readObject();
				ois.close();

				showAlert("Load Profile", "Successfull!", Alert.AlertType.INFORMATION);
			} catch (FileNotFoundException ex) {
				showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
			} catch (IOException | ClassNotFoundException ex) {
				showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
			}
		}
	}

	private void showAlert(String title, String context, Alert.AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setContentText(context);
		alert.showAndWait();
	}

	// helper method - generates course and module data and returns courses within
	// an array
	private Course[] generateAndReturnCourses() {
		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, Schedule.TERM_1);
		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Schedule.YEAR_LONG);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, Schedule.TERM_2);
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigorous Systems", 15, false, Schedule.TERM_2);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, Schedule.TERM_1);
		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, Schedule.TERM_1);
		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, Schedule.TERM_2);
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, Schedule.TERM_2);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, Schedule.TERM_2);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, Schedule.TERM_2);
		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, Schedule.TERM_1);
		Module ctec3911 = new Module("CTEC3911", "Mobile Application Development", 15, false, Schedule.TERM_1);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, Schedule.TERM_2);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, Schedule.TERM_1);
		Module imat3611 = new Module("IMAT3611", "Computer Ethics and Privacy", 15, false, Schedule.TERM_1);
		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, Schedule.TERM_1);
		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, Schedule.TERM_2);
		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false,
				Schedule.TERM_2);

		Course compSci = new Course("Computer Science");
		compSci.addModuleToCourse(imat3423);
		compSci.addModuleToCourse(ctec3451);
		compSci.addModuleToCourse(ctec3902_CompSci);
		compSci.addModuleToCourse(ctec3110);
		compSci.addModuleToCourse(ctec3605);
		compSci.addModuleToCourse(ctec3606);
		compSci.addModuleToCourse(ctec3410);
		compSci.addModuleToCourse(ctec3904);
		compSci.addModuleToCourse(ctec3905);
		compSci.addModuleToCourse(ctec3906);
		compSci.addModuleToCourse(ctec3911);
		compSci.addModuleToCourse(imat3410);
		compSci.addModuleToCourse(imat3406);
		compSci.addModuleToCourse(imat3611);
		compSci.addModuleToCourse(imat3613);
		compSci.addModuleToCourse(imat3614);
		compSci.addModuleToCourse(imat3428_CompSci);

		Course softEng = new Course("Software Engineering");
		softEng.addModuleToCourse(imat3423);
		softEng.addModuleToCourse(ctec3451);
		softEng.addModuleToCourse(ctec3902_SoftEng);
		softEng.addModuleToCourse(ctec3110);
		softEng.addModuleToCourse(ctec3605);
		softEng.addModuleToCourse(ctec3606);
		softEng.addModuleToCourse(ctec3410);
		softEng.addModuleToCourse(ctec3904);
		softEng.addModuleToCourse(ctec3905);
		softEng.addModuleToCourse(ctec3906);
		softEng.addModuleToCourse(ctec3911);
		softEng.addModuleToCourse(imat3410);
		softEng.addModuleToCourse(imat3406);
		softEng.addModuleToCourse(imat3611);
		softEng.addModuleToCourse(imat3613);
		softEng.addModuleToCourse(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}

}
