import javafx.application.Application;
import javafx.scene.Scene;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.image.Image;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import java.io.FileWriter;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.geometry.*;
import javafx.stage.Modality;
import java.io.IOException;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.Event;

import items.*;

/**
 * The following class brings the main display for the text editor with the main function.
 */
public class TextEditor extends Application {

    // Globals
    Stage window;
    MenuBar mbar;
    Menu file, edit, format, view, help;
    MenuItem find, find_next, replace;
    CheckMenuItem status_bar;
    TextArea ta;
    String text;
    File f;
    TextField status;

    int flag = 0;

    // Main
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("NotePad");
        window.getIcons().add(new Image("file:Icon.png"));
        window.setOnCloseRequest(e -> {
            e.consume();
            if (flag == 0) {
                SaveDontSave s_s = new SaveDontSave(ta, text);
                s_s.makeDialog();
                window.close();
            } else
                window.close();

        });

        status = new TextField();
        status.setEditable(false);
        ta = new TextArea();
        ta.setWrapText(true);
        ta.setOnKeyReleased(ke -> {
            text = ta.getText();
            updateStatusBar();
        });

        // ---------Menus and menuBars---------
        createMenu();
        addFileItems();
        addEditItems();
        addViewItems();
        addFormatItems();
        addHelpItems();
        // ------------------------------------

        BorderPane p = new BorderPane();
        p.setTop(mbar);
        p.setCenter(ta);
        p.setBottom(status);

        Scene sc = new Scene(p, 800, 600);
        window.setScene(sc);
        window.show();
    }

    /**
     * The following function will create the menubar and the menu items
     */
    private void createMenu() {
        mbar = new MenuBar();

        file = new Menu("File");
        edit = new Menu("Edit");
        format = new Menu("Format");
        view = new Menu("View");
        help = new Menu("Help");

        mbar.getMenus().addAll(file, edit, format, view, help);
        mbar.prefWidthProperty().bind(window.widthProperty());
    }

    /**
     * The following function will create the submenu items in the "File" menu
     */
    private void addFileItems() {
        MenuItem newf = new MenuItem("New");
        newf.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newf.setOnAction(e -> {
            SaveDontSave sds = new SaveDontSave(ta, text);
            sds.makeDialog();
        });

        MenuItem open = new MenuItem("Open...");
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        open.setOnAction(e -> {
            Open o = new Open(window, ta, text);
            o.openFile(ta);
            f = o.getFile();
            flag = 1;
        });

        MenuItem save = new MenuItem("Save");
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        save.setOnAction(e -> {
            SaveDontSave save_file = new SaveDontSave(ta, text);
            if (flag == 0) {
                f = save_file.saveFile(ta);
                flag = 1;
            } else {
                save_file.overwrite(ta , f);
            }
        });

        MenuItem save_as = new MenuItem("Save As...");
        save_as.setAccelerator(
                new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        save_as.setOnAction(e -> {
            SaveDontSave saveas = new SaveDontSave(ta, text);
            saveas.saveFile(ta);
        });

        MenuItem page_setup = new MenuItem("Page Setup..."); // remaining
        MenuItem print = new MenuItem("Print..."); // remaining
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> {
            if (flag == 0) {
                SaveDontSave s_d_s = new SaveDontSave(ta, text);
                s_d_s.makeDialog();

                window.close();
            } else {
                window.close();
            }
        });
        file.getItems().addAll(newf, open, save, save_as, page_setup, print, exit);
    }

    /**
     * The following function will create the submenu items in the "Edit" menu
     */
    private void addEditItems() {
        MenuItem undo = new MenuItem("Undo");
        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        undo.setOnAction(e -> ta.undo());

        MenuItem cut = new MenuItem("Cut");
        cut.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        cut.setOnAction(e -> ta.cut());

        MenuItem copy = new MenuItem("Copy");
        copy.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        copy.setOnAction(e -> ta.copy());

        MenuItem paste = new MenuItem("Paste");
        paste.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        paste.setOnAction(e -> ta.paste());

        MenuItem delete = new MenuItem("Delete");
        // delete.setAccelerator(KeyCode.L);
        delete.setOnAction(e -> ta.deleteNextChar());

        MenuItem find = new MenuItem("Find...");
        find.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));
        find.setOnAction(e -> {
            FindAndReplace far = new FindAndReplace(ta);
            far.findGUI();
        });

        MenuItem find_next = new MenuItem("Find Next");

        MenuItem replace = new MenuItem("Replace... ");
        replace.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN));
        replace.setOnAction(e -> {
            FindAndReplace far1 = new FindAndReplace(ta);
            far1.replaceGUI();
        });

        MenuItem go_to = new MenuItem("Go To...");
        MenuItem select_all = new MenuItem("Select All");
        select_all.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));

        MenuItem time_date = new MenuItem("Time/Date    F5");
        time_date.setOnAction(e -> {
            String timeStamp = new SimpleDateFormat("a h:mm dd-MM-yyyy").format(new Date());
            ta.appendText(timeStamp);
        });

        SeparatorMenuItem item1 = new SeparatorMenuItem();
        item1.setStyle("-fx-border-style: solid;-fx-border-width: 1px; -fx-background-color-#34495e");

        SeparatorMenuItem item2 = new SeparatorMenuItem();
        item2.setStyle("-fx-border-style: solid;-fx-border-width: 1px; -fx-background-color-#34495e");

        SeparatorMenuItem item3 = new SeparatorMenuItem();
        item3.setStyle("-fx-border-style: solid;-fx-border-width: 1px; -fx-background-color-#34495e");

        edit.getItems().add(undo);
        edit.getItems().add(cut);
        edit.getItems().add(copy);
        edit.getItems().add(paste);
        edit.getItems().add(delete);
        edit.getItems().add(find);
        edit.getItems().add(replace);
        edit.getItems().add(go_to);
        edit.getItems().add(select_all);
        edit.getItems().add(time_date);
    
    }

    /**
     * The following function will create the submenu items in the "Format" menu
     */
    private void addFormatItems() {
        CheckMenuItem word_wrap = new CheckMenuItem("Word Wrap");
        word_wrap.setSelected(true);
        if (word_wrap.isSelected()) {
            ta.setWrapText(true);
        }

        word_wrap.setOnAction(e -> {
            if (!word_wrap.isSelected()) {
                ta.setWrapText(false);
                status_bar.setDisable(false);
                status.setVisible(true);
            } else {
                ta.setWrapText(true);
                status_bar.setDisable(true);
                status.setVisible(false);
            }
        });

        MenuItem font = new MenuItem("Font...");
        font.setOnAction(e -> {
            Fonts f = new Fonts(ta);
            f.setupGUI();
        });

        format.getItems().add(word_wrap);
        format.getItems().add(font);
    }

    /**
     * The following function will create the submenu items in the "File" menu
     */
    private void addViewItems() {
        status_bar = new CheckMenuItem("Status Bar");
        view.getItems().add(status_bar);
        if(ta.isWrapText()){
            status_bar.setDisable(true);
            status.setVisible(false);
        }
        status_bar.setOnAction(e -> {
            if(!status_bar.isSelected()){
                status.setVisible(false);
            } else {
                status.setVisible(true);
            }
        });
    }

    /**
     * The following function will create the submenu items in the "Help" menu
     */
    private void addHelpItems() {
        MenuItem view_help = new MenuItem("View Help");
        MenuItem about_notepad = new MenuItem("About Notepad");
        help.getItems().add(view_help);
        help.getItems().add(new SeparatorMenuItem());
        help.getItems().add(about_notepad);
    }

    /**
     * The following function will update the status bar
     */
    private void updateStatusBar() {
        int caretpos = ta.getCaretPosition();
        String string = text.substring(0 , caretpos);
        int line = 1;
        int column = 1;

        for(int i = 0 ; i < string.length() ; i++) {
            if(string.charAt(i) == '\n') {
                line++;
            }
        }
        column = caretpos - string.lastIndexOf("\n");
        status.setText("Line : " + line + " Column : " + column);
    }

}
