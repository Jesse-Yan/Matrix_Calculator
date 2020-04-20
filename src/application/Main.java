package application;

import java.util.List;
import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static java.util.stream.Collectors.toList;

/**
 * This class is the Main class for JavaFx application
 * 
 * @author Jesse
 *
 */
public class Main extends Application {

  private final String lineSeparator = System.lineSeparator();

  /**
   * This is the start method of the Main class
   * 
   * @param primaryStage
   */
  @Override
  public void start(Stage primaryStage) {

    // Set the title of the primaryStage
    primaryStage.setTitle("Matrix Calculator - Developed by ateam2");

    try {
      // Set the application icon
      primaryStage.getIcons().add(new Image("./assets/calculator.png"));
    } catch (Exception e) {

    }

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");

    // Main layout
    BorderPane root = new BorderPane();

    // Add to the top of root pane
    MenuBar menuBar = new MenuBar();

    // Add related MenuBar, Menu, and MenuItem

    // Set open file tag
    Menu menu = new Menu("Menu");
    MenuItem open = new MenuItem("Open");
    open.setOnAction(event -> {
      File file = fileChooser.showOpenDialog(primaryStage);
      if (file == null || !file.getName().endsWith(".json")) {
        alert("Error: File name mismatch", "Please rechoose the file"
            + lineSeparator + "The name of the file must end with '.json'!");
      } else {
        // Invoke Parser
      }
    });

    // Set save file tag
    MenuItem save = new MenuItem("Save");
    save.setOnAction(event -> {
      File file = fileChooser.showSaveDialog(primaryStage);
      if (file == null || !file.getName().endsWith(".json")) {
        alert("Error: File name mismatch", "Please rechoose the file"
            + lineSeparator + "The name of the file must end with '.json'!");
      } else {
        // Invoke Parser
      }
    });

    // Set Exit tag
    MenuItem exit = new MenuItem("Exit");
    exit.setOnAction(event -> System.exit(0));
    menu.getItems().addAll(open, save, exit);

    // Set About Tag with alert
    Menu about = new Menu("About");
    MenuItem developer = new MenuItem("Developer");
    developer.setOnAction(event -> {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("About Developer");
      alert.setHeaderText("Matrix-Calculator");
      alert.setContentText("Developedby:        " + lineSeparator
          + "    Chengpo Yan - cyan46@wisc.edu" + lineSeparator
          + "    Jinming Zhang - jzhang2279@wisc.edu" + lineSeparator
          + "    Zexin Li - zli885@wisc.edu" + lineSeparator
          + "    Houming Chen - hchen634@wisc.edu" + lineSeparator
          + "    Chengxu Bian - cbian4@wisc.edu");
      alert.showAndWait();
    });
    about.getItems().add(developer);

    menuBar.getMenus().addAll(menu, about);
    root.setTop(menuBar);

    // Set the left scene
    VBox vBoxL = new VBox();
    TextField input = new TextField();
    input.setMaxWidth(360.0);
    input.setEditable(false);
    TextArea result = new TextArea();
    result.setWrapText(true);
    result.setEditable(false);
    result.setMaxWidth(360.0);

    // Set two parallel buttons
    HBox hBoxL = new HBox();
    Button analyzeSequence = new Button("Analyze/nSequence");
    analyzeSequence.setMinWidth(180);
    Button space = new Button("Space");
    space.setMinWidth(180);

    // Activated under analyzing sequence
    space.setDisable(true);
    hBoxL.getChildren().addAll(analyzeSequence, space);

    // Set the gridPane
    // Applying Unicode for pi = u+03c0, sqrt = U+221A
    GridPane gridPaneL = new GridPane();
    List<Button> buttons =
        List.of("|x|", "  x!   ", "\u03c0", "   e   ", "   C   ", "  <-   ",
            "\u221A" + "x", "  x^2  ", "   (   ", "   )   ", "  exp  ",
            "   /   ", "y" + "\u221A" + "x", "  x^y  ", "   7   ", "   8   ",
            "   9   ", "   *   ", " logx  ", " 10^x  ", "   4   ", "   5   ",
            "   6   ", "   -   ", "log2(x)", "  2^x  ", "   1   ", "   2   ",
            "   3   ", "   +   ", "logy(x)", "  ln(x)  ", "  +/-  ", "   0   ",
            "   .   ", "   =   ").stream().map(Button::new).collect(toList());
    int number = 0;
    for (int row = 0; row < 6; row++) {
      for (int column = 0; column < 6; column++) {
        Button button = buttons.get(number++);
        button.setMinSize(60.0, 40.0);
        gridPaneL.add(button, column, row);
      }
    }

    // Add event handler to the buttons

    buttons.stream().forEach(btn -> {
      btn.setOnAction(event -> {
        String temp = btn.getText();
        if (temp.equals("exp")) {
          input.appendText("exp");
        } else if (temp.equals("y" + "\u221A" + "x")) {
          input.appendText("\u221A");
        } else if (temp.equals("  x^y  ")) {
          input.appendText("^");
        } else if (temp.equals("   C   ")) {
          input.clear();
        } else if (temp.equals("  <-   ")) {
          try {
            input.setText(
                input.getText().substring(0, input.getText().length() - 1));
          } catch (Exception e) {

          }
        } else if (temp.equals("  +/-  ")) {
          String fromInput = input.getText();
          input.setText(fromInput.startsWith("-") ? fromInput.substring(1)
              : "-" + fromInput);
        } else if (temp.equals("   =   ")) {
          try {
            result.appendText(input.getText() + "\n="
                + Calculator.calcul("0" + input.getText()) + "\n");
          } catch (Exception e) {
            alert("Wrong Expression",
                "The equation you entered cannot be calculated\nPlease press 'C' and try again");
          }
        } else {
          input.appendText(temp.replace("x", "").trim());
        }
      });
    });

    // Add to the root
    vBoxL.getChildren().addAll(input, result, hBoxL, gridPaneL);
    root.setLeft(vBoxL);

    // Set the right scene
    VBox vBoxR = new VBox();
    HBox hBoxR = new HBox();

    // Set the Matrix Panel
    VBox matrix1 = matrixGenerator();
    VBox matrix2 = matrixGenerator();
    matrix2.setDisable(true);

    // Set the operation
    GridPane matrixOperators = new GridPane();
    List<Button> operators = List.of("c", "+", "-", "*").stream().map(str -> {
      Button temp = new Button(str);
      temp.setMinWidth(30);
      return temp;
    }).collect(toList());
    for (int i = 0; i < 4; i++) {
      matrixOperators.add(operators.get(i), 0, i);
    }
    matrixOperators.setVgap(23);
    hBoxR.getChildren().addAll(matrix1, matrixOperators, matrix2);

    // Set the operation panel
    GridPane mOperations = new GridPane();
    mOperations.setHgap(76.7);
    mOperations.setVgap(10);

    List<Button> mButtons =
        List.of("Det", "Inverse", "QR", "SVD", "Trace", "LU", "Gauss-Elim",
            "EiVector", "EiValue", "exp", "ln").stream().map(operator -> {
              Button temp = new Button(operator);
              temp.setMinWidth(100);
              return temp;
            }).collect(toList());
    int count = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        mOperations.add(mButtons.get(count++), j, i);
        if (count == 11) {
          break;
        }
      }
    }

    HBox power = new HBox();
    Button powerButton = new Button("Power");
    TextField powerInput = new TextField();
    powerInput.setMaxWidth(55);
    power.getChildren().addAll(powerButton, powerInput);
    mOperations.add(power, 3, 2);

    TextArea mResult = new TextArea();
    mResult.setMinHeight(207);

    vBoxR.getChildren().addAll(hBoxR, mOperations, mResult);
    root.setRight(vBoxR);



    // Use the optimized width and height
    Scene mainScene =
        new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
    primaryStage.setScene(mainScene);
    try {
      mainScene.getStylesheets().add("./application/styleSheet.css");
    } catch (Exception e) {

    }
    primaryStage.show();
  }

  /**
   * Generate a Matrix
   * 
   * @return
   */
  private VBox matrixGenerator() {
    VBox vBoxMatrix = new VBox();
    HBox rowMatrix = new HBox();
    Label labelRowMatrix = new Label("Row:      ");
    labelRowMatrix.setMinWidth(52);
    TextField inputRowMatrix = new TextField();
    inputRowMatrix.setMaxWidth(50);
    rowMatrix.getChildren().addAll(labelRowMatrix, inputRowMatrix);
    HBox columnMatrix = new HBox();
    Label labelColumnMatrix = new Label("Column: ");
    labelColumnMatrix.setMaxWidth(52);
    TextField inputColumnMatrix = new TextField();
    inputColumnMatrix.setMaxWidth(50);
    columnMatrix.getChildren().addAll(labelColumnMatrix, inputColumnMatrix);
    GridPane gridMatrix = new GridPane();
    gridMatrix.setMaxWidth(300);
    inputRowMatrix.setText("5");
    inputColumnMatrix.setText("5");

    for (int i = 0; i < Integer.parseInt(inputRowMatrix.getText()); i++) {
      for (int j = 0; j < Integer.parseInt(inputColumnMatrix.getText()); j++) {

        TextField temp = new TextField();
        gridMatrix.add(temp, j, i);
      }
    }

    inputRowMatrix.setOnKeyReleased(event -> {
      try {

        if (inputRowMatrix.getText().equals("")) {
          return;
        }

        if (Integer.parseInt(inputRowMatrix.getText()) <= 0) {
          throw new Exception();
        }

        gridMatrix.getChildren().clear();

        for (int i = 0; i < Integer.parseInt(inputRowMatrix.getText()); i++) {
          for (int j =
              0; j < Integer.parseInt(inputColumnMatrix.getText()); j++) {

            TextField temp = new TextField();
            gridMatrix.add(temp, j, i);
          }
        }
      } catch (Exception e) {
        alert("Error", "Number you entered is invalid" + lineSeparator
            + "Please reenter an positive integer");
        inputRowMatrix.setText("1");
      }
    });

    inputColumnMatrix.setOnKeyReleased(event -> {
      try {

        if (inputColumnMatrix.getText().equals("")) {
          return;
        }

        if (Integer.parseInt(inputColumnMatrix.getText()) <= 0) {
          throw new Exception();
        }

        gridMatrix.getChildren().clear();

        for (int i = 0; i < Integer.parseInt(inputRowMatrix.getText()); i++) {
          for (int j =
              0; j < Integer.parseInt(inputColumnMatrix.getText()); j++) {
            TextField temp = new TextField();
            gridMatrix.add(temp, j, i);
          }
        }
      } catch (Exception e) {
        alert("Error", "Number you entered is invalid" + lineSeparator
            + "Please reenter an positive integer");
        inputColumnMatrix.setText("1");
      }
    });

    vBoxMatrix.getChildren().addAll(rowMatrix, columnMatrix, gridMatrix);

    return vBoxMatrix;
  }

  /**
   * Show alert to remind user
   * 
   * @param title
   * @param content
   */
  private void alert(String title, String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setContentText(content);
    alert.showAndWait();
  }

  /**
   * Main method for this class
   * 
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }
}
