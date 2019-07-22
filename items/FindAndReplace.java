package items;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.CheckBox;
import javafx.scene.control.IndexRange;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.IndexRange;

/**
 * The following class manages the functionality of find and replace
 */
public class FindAndReplace {
	
	// Globals
	Stage window;
	TextArea ta;
	TextField toBeSearched, replacetf;
	RadioButton up, down;
	CheckBox matchCase;
	Button findNext, cancel, replace, replaceall;

	public FindAndReplace(TextArea ta) {
		this.ta = ta;
	}

	/**
	 * The following function creates GUI for "find" command
	 */
	public void findGUI() {
		window = new Stage();
		window.setTitle("Find");
		window.setResizable(false);

		GridPane gpane = new GridPane();
		gpane.setPadding(new Insets(10, 10, 10, 10));
		gpane.setVgap(10);
		gpane.setHgap(5);

		// Label
		Label find = new Label("Find what:");
		gpane.setConstraints(find, 0, 0);

		// TextField
		toBeSearched = new TextField();
		toBeSearched.setPrefWidth(200);
		gpane.setConstraints(toBeSearched, 1, 0);

		// Button
		findNext = new Button("Find Next");
		findNext.setOnAction(e -> find());
		gpane.setConstraints(findNext, 2, 0);

		cancel = new Button("Cancel");
		gpane.setConstraints(cancel, 2, 1);
		cancel.setPrefWidth(65);

		// Direction
		StackPane sp = new StackPane();
		HBox dir = new HBox(5);

		dir.setPadding(new Insets(5, 5, 5, 5));

		Label d = new Label("Direction");

		sp.setAlignment(d, Pos.TOP_LEFT);

		d.setStyle("-fx-translate-y: -10");
		dir.setStyle("-fx-content-display: top");
		dir.setStyle("-fx-border-insets: 20 15 15 15");
		dir.setStyle("-fx-background-color: white");
		dir.setStyle("-fx-border-color: #D3D3D3");

		// ToggleGroup and RadioButtons
		ToggleGroup group = new ToggleGroup();
		up = new RadioButton("Up");
		up.setToggleGroup(group);
		down = new RadioButton("Down");
		down.setToggleGroup(group);
		down.setSelected(true);

		dir.getChildren().addAll(up, down);

		sp.getChildren().addAll(d, dir);

		gpane.setConstraints(sp, 1, 1);

		// CheckBOx
		matchCase = new CheckBox("Match Case");
		matchCase.setIndeterminate(false);
		gpane.setConstraints(matchCase, 0, 2);

		gpane.getChildren().addAll(find, toBeSearched, findNext, cancel, matchCase, sp);
		Scene sc = new Scene(gpane, 400, 110);
		// sc.getStylesheets().add("CSS/Dialog.css");
		window.setScene(sc);
		window.show();
	}

	/**
	 * The following function creates GUI for "find and replace" command
	 */
	public void replaceGUI() {
		window = new Stage();
		window.setTitle("Find");
		window.setResizable(false);

		GridPane gpane = new GridPane();
		gpane.setPadding(new Insets(10, 10, 10, 10));
		gpane.setVgap(10);
		gpane.setHgap(5);

		// Label Find
		Label find = new Label("Find what:");
		gpane.setConstraints(find, 0, 0);

		// TextField Find
		toBeSearched = new TextField();
		toBeSearched.setPrefWidth(200);
		gpane.setConstraints(toBeSearched, 1, 0);

		// button Find
		findNext = new Button("Find Next");
		findNext.setOnAction(e -> find());
		gpane.setConstraints(findNext, 2, 0);

		// Button Cancel
		cancel = new Button("Cancel");
		gpane.setConstraints(cancel, 2, 3);
		cancel.setPrefWidth(65);

		// Label replace
		Label rpl = new Label("Replace What :");
		gpane.setConstraints(rpl, 0, 1);

		// Textfield replace
		replacetf = new TextField();
		replacetf.setPrefWidth(200);
		gpane.setConstraints(replacetf, 1, 1);

		// Button replace
		replace = new Button("Replace");
		replace.setOnAction(e -> replace());
		gpane.setConstraints(replace, 2, 1);

		// Button Replace All
		replaceall = new Button("Replace All");
		replaceall.setOnAction(e -> replaceAll());
		gpane.setConstraints(replaceall, 2, 2);

		// Direction
		StackPane sp = new StackPane();

		HBox dir = new HBox(5);

		dir.setPadding(new Insets(5, 5, 5, 5));

		Label d = new Label("Direction");
		sp.setAlignment(d, Pos.TOP_LEFT);
		d.setStyle("-fx-translate-y: -10");
		dir.setStyle("-fx-content-display: top");
		dir.setStyle("-fx-border-insets: 20 15 15 15");
		dir.setStyle("-fx-background-color: white");
		dir.setStyle("-fx-border-color: #D3D3D3");

		// ToggleGroup and RadioButtons
		ToggleGroup group = new ToggleGroup();
		up = new RadioButton("Up");
		up.setToggleGroup(group);
		down = new RadioButton("Down");
		down.setToggleGroup(group);
		down.setSelected(true);

		dir.getChildren().addAll(up, down);
		sp.getChildren().addAll(d, dir);
		gpane.setConstraints(sp, 1, 3);

		// CheckBOx
		matchCase = new CheckBox("Match Case");
		matchCase.setIndeterminate(false);
		gpane.setConstraints(matchCase, 0, 2);

		gpane.getChildren().addAll(find, toBeSearched, findNext, cancel, matchCase, replaceall, replace, replacetf, sp);
		Scene sc = new Scene(gpane, 400, 150);
		// sc.getStylesheets().add("CSS/Dialog.css");
		window.setScene(sc);
		window.show();
	}

	/**
	 * Core function for the searching the required text.
	 * 
	 * @return -1 if required text is not found otherwise returns the index of the source where text is found
	 */
	int search() {
		int selStart, selEnd;

		String src = ta.getText();
		String key = toBeSearched.getText();

		// setting startIndex to search from Caret by default
		int startIndex = ta.getCaretPosition();

		selStart = ta.getSelection().getStart();
		selEnd = ta.getSelection().getEnd();
		if (up.isSelected()) {
			// This means we want to search up
			if (selStart != selEnd) {
				// something is selected, so our startIndex has to be manipulated
				startIndex = selStart - 1;
			}

			if (matchCase.isSelected())
				startIndex = src.lastIndexOf(key, startIndex);
			else
				startIndex = src.toUpperCase().lastIndexOf(key.toUpperCase(), startIndex);
		} else {
			// This means we want to search downwards
			if (selStart != selEnd) {
				// something is selected, so our startIndex have to be Manipulated
				startIndex = selStart + 1;
			}
			if (matchCase.isSelected())
				startIndex = src.indexOf(key, startIndex);
			else
				startIndex = src.toUpperCase().indexOf(key.toUpperCase(), startIndex);
		}
		return startIndex;
	}


	/**
	 * The following function finds the search input from toBeSearched textField and highlight the text found
	 */
	public void find() {
		int idx = search();

		if (idx != -1) {
			ta.requestFocus();
			ta.selectRange(idx, idx + toBeSearched.getText().length()); // highlight only the searched text
		} else {
			Stage msg = new Stage();
			Label notFound = new Label("Cannot Find" + "'" + toBeSearched.getText() + "'");
			StackPane stackp = new StackPane();
			stackp.getChildren().add(notFound);
			Scene scene = new Scene(stackp, 200, 100);
			msg.setScene(scene);
			msg.show();
		}
	}

	/**
	 * The following function replaces one occurence of the input from replacetf textfield with the text 
	 * from toBeSearched textfield
	 */
	void replace() {
		String selectedText = ta.getSelectedText();
		if (selectedText == null || selectedText.length() == 0) {
			find();
			return;
		}
		// code to replace
		String key = toBeSearched.getText();
		String repText = replacetf.getText();
		if ((matchCase.isSelected() && selectedText.equals(key))
				|| (!matchCase.isSelected() && selectedText.equalsIgnoreCase(key))) {
			// replace the text
			ta.replaceSelection(repText);
		}
		find();
	}

	/**
	 * The following function replaces all occurences of the input from replacetf textfield with the text 
	 * from toBeSearched textfield
	 */
	void replaceAll() {
		String repText = replacetf.getText();

		if (up.isSelected())
			ta.positionCaret(ta.getText().length() - 1);
		else
			ta.positionCaret(0);

		int idx = -1;
		int count = 0;
		
		while (true) {
			idx = search();
			if (idx == -1)
				break;
			count++;
			ta.replaceText(new IndexRange(idx, idx + toBeSearched.getText().length()), repText);
		}

		Stage dialog = new Stage();
		StackPane spane = new StackPane();
		Label l = new Label("Total Replacements made : " + Integer.toString(count));
		spane.getChildren().add(l);
		Scene s = new Scene(spane, 300, 200);
		dialog.setScene(s);
		dialog.show();
	}

}