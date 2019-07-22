package items;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Modality;
import javafx.geometry.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.control.TextInputControl;
import java.io.BufferedWriter;
import java.io.PrintWriter;

/**
 * The following class manages the save/don't save options
 */
public class SaveDontSave {
	
	// Globals
	Stage dialog;
	TextArea ta;
	String text, directory, fileName;
	public Button cancel;
	File file;
	char[] s;
	FileWriter fwriter;

	public SaveDontSave(TextArea ta, String text) {
		this.ta = ta;
		this.text = text;
	}

	public void makeDialog() {
		if (ta.getText().equals("")) {
			// Do nothing
		} else {
			dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);// use WINDOW_MODAL to remove modality
			dialog.setTitle("NotePad");
			dialog.setMinWidth(300);
			Label l1 = new Label("Do you want to save changes to Untitled");
			Button save = new Button("Save");
			save.setOnAction(e -> {
				saveFile(ta);
				text = "";
				ta.setText(text);
				dialog.close();
			});

			Button dont_save = new Button("Don't Save");
			dont_save.setOnAction(e -> {
				text = "";
				ta.setText(text);
				dialog.close();
			});
			cancel = new Button("Cancel");
			cancel.setOnAction(e -> dialog.close());

			VBox toplayout = new VBox(30);
			toplayout.setPadding(new Insets(20, 20, 20, 20));
			toplayout.getChildren().add(l1);
			toplayout.setAlignment(Pos.TOP_CENTER);// Inside javafx.geometry

			HBox bottomlayout = new HBox(15);
			bottomlayout.getChildren().addAll(save, dont_save, cancel);
			bottomlayout.setAlignment(Pos.TOP_CENTER);

			BorderPane bpane = new BorderPane();
			bpane.setCenter(toplayout);
			bpane.setBottom(bottomlayout);

			Scene scene = new Scene(bpane, 400, 150);
			dialog.setScene(scene);
			dialog.showAndWait();
		}
	}
	
	public File saveFile(TextArea ta) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Documents (*.txt)", "*.txt"),
				new FileChooser.ExtensionFilter("All files", "*.*"));
		file = fileChooser.showSaveDialog(dialog);
		directory = file.getAbsolutePath();
		fileName = file.getName();

		try {
			fwriter = new FileWriter(file, false);
			BufferedWriter out = new BufferedWriter(fwriter);
			PrintWriter pw = new PrintWriter(out);
			pw.println(text);
			pw.close();
		} catch (IOException ioexc) {
			ioexc.printStackTrace();
		}
		return file;
	}

	/**
	 * The following function overwrites a file with the text written in the notepad
	 * 
	 * @param ta it is the main text area of the notepad
	 * @param file it is the file which has to be overwritten
	 */
	public void overwrite(TextArea ta , File file) {
		String text = ta.getText();
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			bw.write(text);
			bw.close();
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
}
