package items;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ComboBox;
import java.awt.GraphicsEnvironment;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import java.awt.Font.*;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import java.util.List;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.control.Cell;
import javafx.geometry.Insets;

public class Fonts {
	
	// Globals
	Stage dialog;
	Font f;
	Label font, font_style, font_size;
	TextField font_tf, fontstyle_tf, size_tf;
	List<String> fontlist;
	ListView<String> font_style_list, font_size_list;
	ListView<Label> font_list;
	Label preview, script, sample;
	Button ok, cancel;
	TextArea ta;
	String selectedFont, selectedFontSize, selectedFontStyle;
	String currentFont = "", currentSize = "11";

	public Fonts(TextArea ta) {
		this.ta = ta;
	}

	/**
	 * The following function sets up the GUI for fonts window
	 */
	public void setupGUI() {
		dialog = new Stage();
		VBox main = new VBox(20);
		VBox font_chooser = new VBox(5);
		// VBox fontstyle_chooser = new VBox(5);
		VBox size_chooser = new VBox(5);

		HBox top = new HBox(10);

		top.setPadding(new Insets(10, 10, 10, 10));
		// Adding the 3 vboxes in the to the hbox which will be on TOP of the main VBOX
		top.getChildren().addAll(font_chooser, /* fontstyle_chooser, */ size_chooser);

		// Adding the label textfield and listView<String> to the font_chooser VBOX
		font = new Label("Font :");
		font_tf = new TextField();
		font_list = new ListView<>();
		font_chooser.getChildren().addAll(font, font_tf, font_list);
		addFontToListView();

		font_size = new Label("Font Size :");
		size_tf = new TextField();
		font_size_list = new ListView<>();
		preview = new Label("Preview");
		preview.setPadding(new Insets(50, 10, 10, 10));
		preview.setFont(new Font(15));
		size_chooser.getChildren().addAll(font_size, size_tf, font_size_list, preview);
		addSizesToListView();

		// Adding preview and sample and script
		HBox middle = new HBox();
		middle.setAlignment(Pos.BASELINE_RIGHT);

		VBox insideMiddle = new VBox(5);
		sample = new Label("AaBbXxYy");
		sample.setPadding(new Insets(0, 0, 0, 100));
		checkForChanges();

		// the preview does not change when one of the item is same and other is
		// changing.....both needs to get Changed
		font_list.setPrefHeight(100);
		font_list.setPrefWidth(100);
		insideMiddle.getChildren().add(sample);

		middle.getChildren().add(insideMiddle);

		// Setting the bottom Layout for the two buttons
		HBox bottom = new HBox();
		bottom.setAlignment(Pos.BASELINE_RIGHT);
		bottom.setPadding(new Insets(20, 20, 30, 30));

		ok = new Button("Ok");
		ok.setOnAction(e -> {
			ta.setFont(Font.font(currentFont, Integer.parseInt(currentSize)));
			dialog.close();
		});

		cancel = new Button("Cancel");
		cancel.setOnAction(e -> dialog.close());

		bottom.getChildren().addAll(ok, cancel);

		// Adding all to main layout
		main.getChildren().addAll(top, middle, bottom);
		Scene sc = new Scene(main, 330, 400);
		dialog.setScene(sc);
		dialog.show();

	}

	/**
	 * The following function adds font to the listView components in the fonts
	 * window
	 */
	private void addFontToListView() {
		fontlist = f.getFamilies();

		for (int i = 0; i < fontlist.size(); i++) {
			font_list.getItems().add(new Label(fontlist.get(i)));
			font_list.getItems().get(i).setFont(Font.font(fontlist.get(i), 11));
		}

		font_list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		font_list.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			selectedFont = newValue.getText();
			font_tf.setText(selectedFont);
		});

		font_list.setPrefHeight(100);
		font_list.setPrefWidth(100);
	}

	/**
	 * The following function adds font sizes to the listview
	 */
	private void addSizesToListView() {
		int j = 0;
		int[] fontSizes = new int[65];
		for (int i = 8; i <= 72; i++) {
			fontSizes[j++] = i;
		}
		for (int i = 0; i < fontSizes.length; i++)
			font_size_list.getItems().addAll(Integer.toString(fontSizes[i]));

		font_size_list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		font_size_list.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			selectedFontSize = newValue.toString();
			size_tf.setText(selectedFontSize);
		});

		size_tf.setMaxWidth(50);
		font_size_list.setMaxWidth(50);
		font_size_list.setMaxHeight(100);
	}

	/**
	 * The following function applies font to preview when listview selection is
	 * changed
	 */
	private void checkForChanges() {
		font_tf.textProperty().addListener((e, oldString, newString) -> {
			currentFont = newString; // font_tf.getText();
			sample.setFont(Font.font(currentFont, Integer.parseInt(currentSize)));
		});

		size_tf.textProperty().addListener((e, oldValue, newValue) -> {
			currentSize = newValue.toString();
			sample.setFont(Font.font(currentFont, Integer.parseInt(currentSize)));
		});
	}

}
