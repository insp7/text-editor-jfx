package items;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import java.awt.GraphicsEnvironment;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import java.awt.Font.*;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class FontChooser {

	Stage dialog;
	TextArea ta;
	Font currentFont;
	ListView<String> fontList, fstyleList , fsizeList;
	Button ok, cancel;
	Label fontLabel , fStyleLabel , sizeLabel  , sample , taPreview;  
	TextField fntm , fntstyle , size;
	boolean flag;
	String font  , fontStyle , fontSize;
	
	public FontChooser(TextArea ta) {
		this.ta = ta;
		dialog = new Stage();
		Pane p = new Pane();
		p.setLayout(null);

		VBox font = new VBox(5);
		VBox fstyle = new VBox(5);
		VBox sizelayout = new VBox(5);
		VBox sample_layout = new VBox(20);

		//Set bounds of the VBoxes
		p.getChildren().add(font);
		//font.setBounds(10 , 10 , 100 , 200);

		p.getChildren().add(fstyle);
		//fstyle.setBounds(70 , 10 , 70 , 200);

		p.getChildren().add(sizelayout);
		//sizelayout.setBounds( 110 , 10 , 40 , 100);

		p.getChildren().add(sample_layout);
		//sample_layout.setBounds(90 , 130 , 70 , 50);
		sample =  new Label("Sample");
		taPreview = new Label("AaBbXxYy");
		sample_layout.getChildren().addAll(sample , taPreview);

		p.getChildren().add(ok);
		ok = new Button("Ok");
		ok.setOnAction(e -> ta.getText().setFont(createFont()));
		//ok.setBounds(50 ,300 , 30 , 30);
		
		p.getChildren().add(cancel);
		cancel = new Button("Cancel");
		cancel.setOnAction(e -> dialog.close());
		//cancel.setBounds(50 , 340 , 30 , 30);

		Scene sc =  new Scene(p , 300 , 400);
		dialog.setScene(sc);
		dialog.show();

	}


	private void fontPanel() {
		//Label
		fontLabel = new Label("Font :");
		
		//TextField
		fntm = new TextField();
		font = fontList.getSelectionModel().getSelectedItem();
		fntm.setText(font);

		
		//ListVIew
		fontList = new ListView<>();
		fontList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for(int i = 0 ; i < fontNames.length ; i++ )
				fontList.getItems().add(fontNames[i]);

		fontList.setOrientation(Orientation.VERTICAL);
		fontList.getSelectionModel().getItemProperty().addListener( (v, oldValue, newValue ) -> {	
				if(oldValue.equals(newValue)) {
					System.out.println("Same font selected");
				}
				else
					taPreview.setFont(createFont());
			});

		font.getChildren().addAll(fontLabel, fntm, fontList);
	}



	private void fstylePanel() {
			//Label
			fStyleLabel = new Label("Font Style :");

			//TextField
			fntstyle = new TextField();
			fontStyle = fstyleList.getSelectionModel().getSelectedItem();
			fntStyle.setText(fontStyle);

			//ListView
			fstyleList = new ListView<>();
			String[] fontStyleNames = {"Regular", "Italic", "Bold", "Bold Italic"};
			for(int i = 0 ; i < fontStyleNames.length ; i++ )
					fntStyleList.getItems().add(fontStyleNames[i]);

			fstyleList.getSelectionModel().getItemProperty().addListener( (v, oldValue, newValue ) -> {	
				if(oldValue.equals(newValue)) {
					System.out.println("Same font selected");
				}
				else
					taPreview.setFont(createFont());
			});

			fstyle.getChildren().addAll(fStyleLabel , fntStyle , fstyleList);
		}



	private void fontSizePanel() {
		//label
		sizeLabel = new Label();

		//TextField
		size = new TextField();
		fontSize = fsizeList.getSelectionModel().getSelectedItem();
		size.setText(fontStyle);

		//ListView
		fsizeList = new ListView<>();
		
		String[] fontSizes = new String[35];
		for(int i=8, j=0; i<=72; i+=2, j++)
			fontSizes[j] = new String(i+"");

		for(int i = 0 ; i < fontSizes.length ; i++ )
				fsizeList.getItems().add(fontSizes[i]);

		fsizeList.setOrientation(Orientation.VERTICAL);

		fsizeList.getSelectionModel().getItemProperty().addListener( (v,oldValue,newValue ) -> {	
				if(oldValue.equals(newValue)) {
					System.out.println("Same font selected");
				}
				else
					taPreview.setFont(createFont());
			});

			sizelayout.getChildren().addAll(fStyleLabel , fntStyle , fstyleList);
	}



	private Font createFont() {
		Font temp = currentFont;
		int varFontStyle = Font.PLAIN;
		String selectedFont = font.getText();
		int selectedFontSize = Integer.parseInt((String)fontSize.getText());
		int selectedStyle =  fstyleList.getSelectionModel().getSelectedIndex();

		switch(selectedStyle) {
			case 0:
				varFontStyle = Font.PLAIN;
				break;
			case 1:
				varFontStyle = Font.ITALIC;
				break;
			case 2:
				varFontStyle = Font.BOLD;
				break;
			case 3:
				varFontStyle = Font.BOLD + Font.ITALIC;
				break;
		}

		temp = new Font(selectedFont, varFontStyle, selectedFontSize);
		return temp;
	}

} // end of class
