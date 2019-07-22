package items;

import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import javafx.stage.Stage;

/**
 * The following class gives the functionality of open a file
 */
public class Open {
	TextArea ta;
	String text;
	Stage window;
	File f;
	String filePath;

	public Open(Stage primaryStage ,TextArea ta , String text) {
		this.ta = ta;
		this.text = text;
		this.window = primaryStage;
	}


	/**
	 * The following function reads a file and sets the text in textarea
	 * For reading a file it uses system file chooser gui
	 * 
	 * @param ta it is the main textarea of the notepad
	 */
	public void openFile(TextArea ta) {
		FileChooser fch = new FileChooser();
		fch.setTitle("Open");
		fch.getExtensionFilters().addAll(
        				 new FileChooser.ExtensionFilter("Text Documents (*.txt)", "*.txt"),
        				 new FileChooser.ExtensionFilter("All files" , "*.*")
        				 );
		f = fch.showOpenDialog(window);
		BufferedReader in = null;
		try {
			    in = new BufferedReader(new FileReader(f));
			    int k;
			    while ((k = in.read()) != -1) {
			        ta.appendText(Character.toString((char)k));
			   	}
			   	in.close();
			}
		 catch (IOException e) {}
	}

	/**
	 * @return returns the file which is opened in the notepad
	 */
	public File getFile() {
		return f.getAbsoluteFile();
	}
}