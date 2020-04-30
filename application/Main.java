package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.json.simple.parser.ParseException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static java.util.stream.Collectors.toList;

/**
 * This class is the Main class for JavaFx application
 * 
 * @author Jesse, Archer
 *
 */
public class Main extends Application {

  // Counting for different OS setup
  private final String lineSeparator = System.lineSeparator();

  // Spectating caret Position
  int caretPosition;

  // Spectating focusedTextField
  private TextField focusedTextField = null;

  // Whether is undergoing analyzing process
  private boolean analyze = false;

  // Result shower
  BorderPane resultShower = new BorderPane();

  // CSS style for label
  String labelStyle = "-fx-font-size: 16px;-fx-text-fill: #333333;"
      + "-fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );"
      + "-fx-border-style: solid inside;-fx-border-width: 2;"
      + "-fx-border-insets: 5;-fx-border-color: black;";

  // lists storing steps
  List<CalSteps> lists = null;

  // Category list
  List<CalSteps> categoryList = new ArrayList<>();

  // Existing Operators
  HashSet<String> existingOperations = new HashSet<>();

  // Whether user has modify the content
  boolean state = false;

  // Whether the user's input can be computed
  boolean correctness = true;

  // Whether the user has save the file
  boolean saved = false;

  // Whether the result is number
  boolean isNum = false;

  // Whether need to invoke updater to update the result
  boolean update = true;

  // Whether in filtering mode
  boolean filtering = false;

  // Previous page recorder
  int prevPage = 0;

  // Recorder of buttons
  Button latestMOpera = null;

  // Recorder of operation
  String operation = null;

  // Recorder of Power
  String power = "";

  // Recorder of results
  String resultNum = null;
  List<String[][]> results = new ArrayList<>();

  /**
   * This is the start method of the Main class. It constructs many components
   * and adds many EventHandlers to components.
   * 
   * @param primaryStage the main Stage
   */
  @Override
  public void start(Stage primaryStage) {

    // Set the title of the primaryStage
    primaryStage.setTitle("Matrix Calculator - Developed by ateam2");

    try {
      // Set the application icon
      primaryStage.getIcons()
                  .add(new Image(getClass().getResource("calculator.png")
                                           .toExternalForm()));
    } catch (Exception e) {

    }

    // Set the style of Matrix result shower
    resultShower.setStyle("-fx-background-color: lightgray;");


    // Below is the setter of Top pane


    // Set the fileChooser
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

    // Set save file tag
    MenuItem save = new MenuItem("Save");

    // Set Exit tag
    MenuItem exit = new MenuItem("Exit");
    exit.setOnAction(event -> System.exit(0));
    menu.getItems().addAll(open, save, exit);

    // Set About Tag with alert
    Menu about = new Menu("About");
    MenuItem developer = new MenuItem("Developer");
    MenuItem instruction = new MenuItem("Instruction");

    // Set the EventHandler for Menu
    menuEventHandlerSetter(about, developer, instruction);

    // Add all to the MenuBar
    menuBar.getMenus().addAll(menu, about);

    // Set the top scene And Selector Pane
    HBox selector = new HBox();

    // Set the filter pane
    ComboBox<String> filter = new ComboBox<>();
    filter.getItems().add("All");
    filter.getSelectionModel().selectFirst();

    // A set of inputs, including forward, page, total pages and backward
    Button forward = new Button("<");
    TextField pages = new TextField();
    pages.setMaxWidth(40);
    Label slash = new Label("/");
    slash.setStyle("-fx-font-size: 8px;-fx-text-fill: #333333;"
        + "-fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );"
        + "-fx-border-style: solid inside;-fx-border-width: 2;"
        + "-fx-border-insets: 5;-fx-border-color: black;");
    TextField total = new TextField();
    total.setMaxWidth(40);
    total.setEditable(false);
    Button backward = new Button(">");
    Button confirm = new Button("\u221A");
    Button add = new Button("Add Cal");
    Button addConfirm = new Button("AddConfirm");
    Button delete = new Button("Delete Cal");
    Button quit = new Button("Quit");

    // Set the functionality of two buttons
    addConfirm.setDisable(true);
    add.setDisable(false);

    // Add to the pane of selector
    selector.getChildren()
            .addAll(filter, forward, pages, slash, total, backward, confirm,
                add, addConfirm, delete, quit);
    // Align to the center
    selector.alignmentProperty().set(Pos.CENTER);

    // Create a VBox and add to the top of root Border Pane
    VBox vBox = new VBox();
    vBox.getChildren().addAll(menuBar, selector);
    root.setTop(vBox);
    selector.setDisable(true);


    // Below is the setter of Left simple calculator


    // Set for input and output field and organize their dimensions
    VBox vBoxL = new VBox();
    TextField input = new TextField();
    input.setMaxWidth(360.0);
    TextArea result = new TextArea();
    result.setWrapText(true);
    result.setEditable(false);
    result.setMaxWidth(360.0);
    result.setMinHeight(220.0);

    // Reset focus
    focusedTextField = input;

    // Set two parallel buttons for analyze sequence
    HBox hBoxL = new HBox();
    Button analyzeSequence = new Button("Analyze Sequence");
    analyzeSequence.setMinWidth(180);
    analyzeSequence.setMinHeight(44);
    Button space = new Button("Space");
    space.setMinWidth(180);
    space.setMinHeight(44);

    // Activated under analyzing sequence
    space.setDisable(true);
    // Add to the HBox for parallel visual effect
    hBoxL.getChildren().addAll(analyzeSequence, space);

    // Set the gridPane of simple calculator operations
    GridPane gridPaneL = new GridPane();
    // Map from String to Buttons
    List<Button> buttons =
        List.of("\u03c0", "   e   ", "   C   ", "  <-   ", "   (   ", "   )   ",
            "  |x|  ", "   /   ", "   7   ", "   8   ", "   9   ", "   *   ",
            "   4   ", "   5   ", "   6   ", "   -   ", "   1   ", "   2   ",
            "   3   ", "   +   ", "  +/-  ", "   0   ", "   .   ", "   =   ")
            .stream()
            .map(Button::new)
            .collect(toList());

    // Add these Buttons to the GridPane
    int number = 0;
    for (int row = 0; row < 6; row++) {
      for (int column = 0; column < 4; column++) {
        Button button = buttons.get(number++);
        button.setMinSize(90.0, 40.0);
        gridPaneL.add(button, column, row);
      }
    }

    // Special List of Non-number buttons for analyze sequence
    List<Button> notNumber =
        buttons.stream()
               .filter(b -> !(b.getText().trim().matches("\\d")
                   || b.getText().trim().matches("\\.")
                   || b.getText().trim().matches("\\+\\/\\-")))
               .collect(toList());

    // Set the caretPosition
    input.setOnMouseClicked(event -> {
      inputSetter(input);
    });

    // Add event handler to the buttons for input and output
    buttons.stream().forEach(eachButton -> {
      buttonsSetter(input, result, space, notNumber, eachButton);
    });

    // Set for Sequence Actions
    analyzeSequence.setOnMouseClicked(event -> {
      analyzeSetter(input, space, buttons, notNumber);
    });

    // Set for the EventHandler of button space
    space.setOnAction(event -> {
      spaceSetter(input);
    });

    // Add to the root and finished setting left pane
    vBoxL.getChildren().addAll(input, result, hBoxL, gridPaneL);
    root.setLeft(vBoxL);


    // Below is the setter of right scene, the Matrix calculator


    // Set the right scene
    VBox vBoxR = new VBox();

    // Set for Right and Left Disabling, switching between simple calculation
    // and matrix calculation
    vBoxR.setOnMouseEntered(e -> {
      vBoxRSetter(buttons, notNumber);
    });

    // Set the event handler when user mouse is switching between right and left
    input.setOnMouseEntered(e -> {
      notNumber.stream().forEach(button -> button.setDisable(false));
    });
    result.setOnMouseClicked(e -> {
      notNumber.stream().forEach(button -> button.setDisable(false));
    });

    // The pane for two matrixes
    BorderPane matrixes = new BorderPane();

    // Set the Matrix Panel and generate related panel
    List<TextField> matrix1Data = new ArrayList<>();
    List<TextField> matrix2Data = new ArrayList<>();
    List<TextField> rowAndCol1 = new ArrayList<>();
    List<TextField> rowAndCol2 = new ArrayList<>();
    VBox matrix1 = matrixGenerator(matrix1Data, rowAndCol1);
    VBox matrix2 = matrixGenerator(matrix2Data, rowAndCol2);

    // Add focus function for each row and col inputField of matrixes
    rowAndCol1.stream()
              .forEach(textField -> textField.setOnMouseClicked(event -> {
                focusSetter(textField);
              }));
    rowAndCol2.stream()
              .forEach(textField -> textField.setOnMouseClicked(event -> {
                focusSetter(textField);
              }));

    // matrix2 will be enable when doing '+', '-', or '*'
    matrix2.setDisable(true);

    // Set the operation of Two Matrixes, including '+', '-', and '*'
    GridPane matrixOperators = new GridPane();

    // Special check box to switch the state between one matrix and two matrixes
    CheckBox enableSecond = new CheckBox("?");
    matrixOperators.add(enableSecond, 0, 0);

    // Setter of two MatrixOperators

    // Add two matrixes operators to the pane
    Button c1 = new Button("c1");
    c1.setMinWidth(35);
    matrixOperators.add(c1, 0, 1);

    // Map from String to Buttons
    List<Button> operators = List.of("c2", "+", "-", "*").stream().map(str -> {
      Button temp = new Button(str);
      temp.setMinWidth(35);
      return temp;
    }).collect(toList());
    // Add to the panel
    for (int i = 0; i < 4; i++) {
      matrixOperators.add(operators.get(i), 0, i + 2);
    }

    // Adjustor for the visual and arrangement of the pane
    matrixOperators.setVgap(19);
    operators.stream().forEach(b -> b.setDisable(true));
    matrix1.setMinWidth(400);
    matrix2.setMinWidth(400);
    matrix1.setMinHeight(233);
    matrix2.setMinHeight(233);
    matrixes.setLeft(matrix1);
    HBox cAndR = new HBox();
    cAndR.getChildren().addAll(matrixOperators, matrix2);
    matrixes.setCenter(cAndR);

    // Set EventHandler for stateless buttons

    // Set the EventHandler for c1 matrixOperator
    c1.setOnMouseClicked(event -> {
      try {
        matrix1Data.stream().forEach(TextField::clear);
      } catch (Exception e1) {
        /* If caught, do nothing */
      }
    });

    // Set the EventHandler for c2 matrixOperator
    operators.get(0).setOnMouseClicked(event -> {
      try {
        matrix2Data.stream().forEach(TextField::clear);
      } catch (Exception e1) {
        /* If caught, do nothing */
      }
    });

    // Setter for single MatrixOperators

    // Set the operation panel
    GridPane singleOperationPanel = new GridPane();
    singleOperationPanel.setHgap(145);
    singleOperationPanel.setVgap(10);

    // Set the Operations of one Matrix by mapping from String to Buttons
    List<Button> singleMatrixOperations =
        List.of("Det", "Inverse", "QR", "Nullity", "Trace", "LUP", "Gauss-Elim",
            "Cholesky", "EiValue", "Rank", "Transpose")
            .stream()
            .map(operator -> {
              Button temp = new Button(operator);
              temp.setMinWidth(100);
              return temp;
            })
            .collect(toList());

    // Add to the GridPane of single matrix operation
    int count = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        singleOperationPanel.add(singleMatrixOperations.get(count++), j, i);
        if (count == 11) {
          break;
        }
      }
    }

    // Add the special Power button and its inputField
    HBox power = new HBox();
    Button powerButton = new Button("Power");
    TextField powerInput = new TextField();
    powerInput.setMaxWidth(55);
    power.getChildren().addAll(powerButton, powerInput);
    singleOperationPanel.add(power, 3, 2);

    // Adjust the output pane
    resultShower.setMinHeight(207);
    resultShower.setMaxWidth(836);


    // Below is the section that add EventListener to Buttons


    // Add eventListener of enableSecond, the button that change the state of
    // one or two matrixes
    enableSecond.setOnAction(event -> {
      enableSetter(matrix2, enableSecond, operators, singleOperationPanel);
    });

    try {

      // Add Operations related to MatrixCalculator

      // Add EventHandler to the '+' operation
      operators.get(1).setOnAction(event -> {
        plusSetter(vBoxR, matrix1Data, matrix2Data, rowAndCol1, rowAndCol2,
            operators);
      });

      // Add EventHandler to the '-' operation
      operators.get(2).setOnAction(event -> {
        minusSetter(vBoxR, matrix1Data, matrix2Data, rowAndCol1, rowAndCol2,
            operators);
      });

      // Add EventHandler to the '*' operation
      operators.get(3).setOnAction(event -> {
        multSetter(vBoxR, matrix1Data, matrix2Data, rowAndCol1, rowAndCol2,
            operators);
      });

      // Add EventHandler to matrix operations determinant
      singleMatrixOperations.get(0).setOnAction(event -> {
        detSetter(vBoxR, matrix1Data, rowAndCol1, singleMatrixOperations);
      });

      // Add EventHandler to matrix operations inverse matrix
      singleMatrixOperations.get(1).setOnAction(event -> {
        inverseSetter(vBoxR, matrix1Data, rowAndCol1, singleMatrixOperations);
      });

      // Add EventHandler to matrix operations QR decomposition
      singleMatrixOperations.get(2).setOnAction(event -> {
        qRSetter(vBoxR, matrix1Data, rowAndCol1, singleMatrixOperations);
      });

      // Add EventHandler to matrix operations nullity
      singleMatrixOperations.get(3).setOnAction(event -> {
        nullitySetter(vBoxR, matrix1Data, rowAndCol1, singleMatrixOperations);
      });

      // Add EventHandler to matrix operations the trace
      singleMatrixOperations.get(4).setOnAction(event -> {
        traceSetter(vBoxR, matrix1Data, rowAndCol1, singleMatrixOperations);
      });

      // Add EventHandler to matrix operations LUP decomposition
      singleMatrixOperations.get(5).setOnAction(event -> {
        lUPSetter(vBoxR, matrix1Data, rowAndCol1, singleMatrixOperations);
      });

      // Add EventHandler to matrix operations gaussian elimination
      singleMatrixOperations.get(6).setOnAction(event -> {
        geSetter(vBoxR, matrix1Data, rowAndCol1, singleMatrixOperations);
      });

      // Add EventHandler to matrix operations Cholesky decomposition
      singleMatrixOperations.get(7).setOnAction(event -> {
        choleskySetter(vBoxR, matrix1Data, rowAndCol1, singleMatrixOperations);
      });

      // Add EventHandler to matrix operations eigenvalue
      singleMatrixOperations.get(8).setOnAction(event -> {
        eivSetter(vBoxR, matrix1Data, rowAndCol1, singleMatrixOperations);
      });

      // Add EventHandler to matrix operations rank
      singleMatrixOperations.get(9).setOnAction(event -> {
        rankSetter(vBoxR, matrix1Data, rowAndCol1, singleMatrixOperations);
      });

      // Add EventHandler to matrix operations transpose 
      singleMatrixOperations.get(10).setOnAction(event -> {
        transposeSetter(vBoxR, matrix1Data, rowAndCol1, singleMatrixOperations);
      });

      // Add EventHandler to matrix operations power
      powerButton.setOnAction(event -> {
        powerSetter(vBoxR, matrix1Data, rowAndCol1, powerButton, powerInput);
      });

      powerInput.setOnMouseClicked(event -> {
        focusSetter(powerInput);
      });
    } catch (RuntimeException e) {
      correctness = false;
      alert("Error", "" + e.getMessage());
    } catch (Exception e1) {
      correctness = false;
      alert("Error", "Your input may contain invalid characters or empty");
    }

    // Add to the overall panel
    vBoxR.getChildren().addAll(matrixes, singleOperationPanel, resultShower);
    root.setRight(vBoxR);


    // Below is the section adding open-save-quit EventHandlers


    // Set the action for FileChooser-open
    open.setOnAction(event -> {

      openSetter(primaryStage, fileChooser, selector, filter, pages, total,
          confirm, matrix1Data, matrix2Data, rowAndCol1, rowAndCol2,
          enableSecond, operators, singleMatrixOperations, powerButton,
          powerInput);
    });

    // Add focus function
    pages.setOnMouseClicked(e -> {
      pagesSetter(pages);
    });

    // Add EventListener to confirm button
    confirm.setOnAction(event -> {
      confirmSetter(pages, matrix1Data, matrix2Data, rowAndCol1, rowAndCol2,
          enableSecond, operators, singleMatrixOperations, powerButton,
          powerInput);
    });

    // Add EventListener to forward button
    forward.setOnAction(event -> {
      forWardSetter(pages, confirm, matrix1Data, matrix2Data, rowAndCol1,
          rowAndCol2, enableSecond, operators, singleMatrixOperations,
          powerButton, powerInput);
    });

    // Add EventListener to backward button
    backward.setOnAction(event -> {
      backWardSetter(pages, confirm, matrix1Data, matrix2Data, rowAndCol1,
          rowAndCol2, enableSecond, operators, singleMatrixOperations,
          powerButton, powerInput);
    });

    // Set the action for add a calculation
    add.setOnAction(event -> {
      addSetter(filter, forward, pages, total, backward, confirm, add,
          addConfirm, delete, quit, matrix1Data, matrix2Data, rowAndCol1,
          rowAndCol2, enableSecond);
    });

    // Set the action for addConfirm
    addConfirm.setOnAction(event -> {
      addConfirmSetter(filter, forward, pages, total, backward, confirm, add,
          addConfirm, delete, quit, matrix1Data, matrix2Data, rowAndCol1,
          rowAndCol2, enableSecond);
    });

    // Set the action for delete
    delete.setOnAction(event -> {
      deleteSetter(filter, pages, total, confirm);
    });

    // Add Eventlistener to the filter button
    filter.valueProperty().addListener(event -> {
      filterSetter(open, save, filter, pages, total, confirm, add, delete, quit,
          matrix1Data, matrix2Data, rowAndCol1, rowAndCol2, matrix1, matrix2,
          matrixOperators, enableSecond, operators, singleOperationPanel,
          singleMatrixOperations, powerButton, powerInput);
    });

    // Set the action for FileChooser-save
    save.setOnAction(event -> {
      saveSetter(primaryStage, fileChooser);
    });

    // Add EventListener to quit button
    quit.setOnAction(event -> {
      quitSetter(save, selector, filter, pages, total, quit, rowAndCol1,
          rowAndCol2);
    });

    // Use the optimized width and height
    Scene mainScene =
        new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
    primaryStage.setScene(mainScene);
    try {
      mainScene.getStylesheets()
               .add(getClass().getResource("styleSheet.css").toExternalForm());
    } catch (Exception e) {

    }
    primaryStage.show();

    // Show the instruction page
    instruction.fire();
  }

  /**
   * Adjust focus of the TextField
   * 
   * @param field the TextField
   */
  private void focusSetter(TextField field) {
    if (field.isFocused()) {
      caretPosition = field.getCaretPosition();
      focusedTextField = field;
    }
  }

  /**
   * Set the EventHandler for space
   * 
   * @param input the input TextField
   */
  private void spaceSetter(TextField input) {
    try {
      input.insertText(caretPosition, " ");
      ++caretPosition;
    } catch (Exception e) {
      /* If catch, do nothing */
    }
  }

  /**
   * Set the EventHandler for analyzer
   * 
   * @param input     the input TextField
   * @param space     the space button
   * @param buttons   list of buttons
   * @param notNumber list of the buttons which is not number
   */
  private void analyzeSetter(TextField input, Button space,
      List<Button> buttons, List<Button> notNumber) {
    try {
      analyzeSequenceSetter(input, space, buttons, notNumber);
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Set the EventHandler for button
   * 
   * @param input     the TextField
   * @param result    the TextArea
   * @param space     space button
   * @param notNumber the list of notNumber buttons
   * @param btn       the button
   */
  private void buttonsSetter(TextField input, TextArea result, Button space,
      List<Button> notNumber, Button button) {
    try {
      button.setOnAction(event -> {
        buttonActionSetter(input, result, space, notNumber, button);
      });
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Set the EventHandler for input
   * 
   * @param input the TextField
   */
  private void inputSetter(TextField input) {
    try {
      caretPosition = input.getCaretPosition();
      focusedTextField = input;
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Set the EventHandler for vBoxR
   * 
   * @param buttons   List<Button> buttons
   * @param notNumber List<Button> notNumber
   */
  private void vBoxRSetter(List<Button> buttons, List<Button> notNumber) {
    try {
      notNumber.stream().forEach(b -> b.setDisable(true));
      buttons.get(3).setDisable(false);
      if (analyze) {
        buttons.get(23).setDisable(false);
      }
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for enable CheckBox
   * 
   * @param matrix2              VBox matrix2
   * @param enableSecond         CheckBox enableSecond
   * @param operators            List<Button> operators
   * @param singleOperationPanel GridPane singleOperationPanel
   */
  private void enableSetter(VBox matrix2, CheckBox enableSecond,
      List<Button> operators, GridPane singleOperationPanel) {
    try {
      if (enableSecond.isSelected()) {
        singleOperationPanel.setDisable(true);
        operators.stream().forEach(b -> b.setDisable(false));
        matrix2.setDisable(false);
      } else {
        singleOperationPanel.setDisable(false);
        operators.stream().forEach(b -> b.setDisable(true));
        matrix2.setDisable(true);
      }
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for plus
   * 
   * @param vBoxR       VBox vBoxR
   * @param matrix1Data List<TextField> matrix1Data
   * @param matrix2Data List<TextField> matrix2Data
   * @param rowAndCol1  List<TextField> rowAndCol1
   * @param rowAndCol2  List<TextField> rowAndCol2
   * @param operators   List<Button> operators
   */
  private void plusSetter(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> matrix2Data, List<TextField> rowAndCol1,
      List<TextField> rowAndCol2, List<Button> operators) {
    try {
      // Recorder the latest operation
      latestMOpera = operators.get(1);

      // Invoke to process and output data
      twoMatrixOperator(vBoxR, matrix1Data, matrix2Data, rowAndCol1, rowAndCol2,
          "Operation: Add", "+");
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for minus
   * 
   * @param vBoxR       VBox vBoxR
   * @param matrix1Data List<TextField> matrix1Data
   * @param matrix2Data List<TextField> matrix2Data
   * @param rowAndCol1  List<TextField> rowAndCol1
   * @param rowAndCol2  List<TextField> rowAndCol2
   * @param operators   List<Button> operators
   */
  private void minusSetter(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> matrix2Data, List<TextField> rowAndCol1,
      List<TextField> rowAndCol2, List<Button> operators) {
    try {
      // Recorder the latest operation
      latestMOpera = operators.get(2);

      // Invoke to process and output data
      twoMatrixOperator(vBoxR, matrix1Data, matrix2Data, rowAndCol1, rowAndCol2,
          "Operation: Subtract", "-");
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for mult
   * 
   * @param vBoxR       VBox vBoxR
   * @param matrix1Data List<TextField> matrix1Data
   * @param matrix2Data List<TextField> matrix2Data
   * @param rowAndCol1  List<TextField> rowAndCol1
   * @param rowAndCol2  List<TextField> rowAndCol2
   * @param operators   List<Button> operators
   */
  private void multSetter(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> matrix2Data, List<TextField> rowAndCol1,
      List<TextField> rowAndCol2, List<Button> operators) {
    try {
      // Recorder the latest operation
      latestMOpera = operators.get(3);

      // Invoke to process and output data
      twoMatrixOperator(vBoxR, matrix1Data, matrix2Data, rowAndCol1, rowAndCol2,
          "Operation: Multiply", "*");
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for determinant
   * 
   * @param vBoxR                  VBox vBoxR
   * @param matrix1Data            List<TextField> matrix1Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   */
  private void detSetter(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> rowAndCol1, List<Button> singleMatrixOperations) {
    try {
      latestMOpera = singleMatrixOperations.get(0);
      String[][] dataFromMatrix = reader(matrix1Data, rowAndCol1);
      MatrixCalculator matrixCalculator = new MatrixCalculator(dataFromMatrix);

      // get the determinant of the matrix and change to border pane
      String resultDeterminant = matrixCalculator.getDeterminant();
      resultNum = resultDeterminant;
      resultShower = resultBuilder("Operation: Det", "Determinant",
          dataFromMatrix, resultDeterminant);
      scrollPane(vBoxR, resultShower);
      stateModifer();

    } catch (MatrixDimensionsMismatchException e1) {
      correctness = false;
      alert("MatrixDimensionError",
          "Sorry, the matrix you entered is not a square matrix" + lineSeparator
              + "To compute the determinant of a matrix," + lineSeparator
              + " it has to be a square matrix");
    } catch (NumberFormatException e1) {
      correctness = false;
      alert("Error", e1.getMessage());
    } catch (Exception e2) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for inverse
   * 
   * @param vBoxR                  VBox vBoxR
   * @param matrix1Data            List<TextField> matrix1Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   */
  private void inverseSetter(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> rowAndCol1, List<Button> singleMatrixOperations) {
    try {
      latestMOpera = singleMatrixOperations.get(1);
      String[][] dataFromMatrix = reader(matrix1Data, rowAndCol1);
      MatrixCalculator matrixCalculator = new MatrixCalculator(dataFromMatrix);

      // get the inverse matrix and change to boreder pane
      String[][] resultInverse = matrixCalculator.getInverse();
      results.clear();
      results.add(resultInverse);
      resultShower = resultBuilder("Operation: Inverse", "Inverse",
          dataFromMatrix, resultInverse);
      scrollPane(vBoxR, resultShower);
      stateModifer();
    } catch (MatrixDimensionsMismatchException e) {
      correctness = false;
      alert("MatrixDimensionError",
          "Sorry, the matrix you entered is not a square matrix" + lineSeparator
              + "To compute the inverse of a matrix," + lineSeparator
              + " it has to be a square matrix");
    } catch (MatrixArithmeticException e2) {
      correctness = false;
      alert("MatriArithmeticError",
          "Sorry, the matrix you entered is not invertible");
    } catch (NumberFormatException e1) {
      correctness = false;
      alert("Error", e1.getMessage());
    } catch (Exception e2) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for QR
   * 
   * @param vBoxR                  VBox vBoxR
   * @param matrix1Data            List<TextField> matrix1Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   */
  private void qRSetter(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> rowAndCol1, List<Button> singleMatrixOperations) {
    try {
      latestMOpera = singleMatrixOperations.get(2);
      String[][] dataFromMatrix = reader(matrix1Data, rowAndCol1);
      MatrixCalculator matrixCalculator = new MatrixCalculator(dataFromMatrix);

      // get the QR decomposition of the matrix and change to border pane
      List<String[][]> resultQR = matrixCalculator.getQRDecomposition();
      results.clear();
      results.addAll(resultQR.stream().collect(toList()));
      resultShower = resultBuilderQR("Operation: QR", "QR", dataFromMatrix,
          resultQR.get(0), resultQR.get(1));
      scrollPane(vBoxR, resultShower);
      stateModifer();
    } catch (NumberFormatException e2) {
      correctness = false;
      alert("Error", e2.getMessage());
    } catch (Exception e3) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for nullity
   * 
   * @param vBoxR                  VBox vBoxR
   * @param matrix1Data            List<TextField> matrix1Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   */
  private void nullitySetter(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> rowAndCol1, List<Button> singleMatrixOperations) {
    try {
      latestMOpera = singleMatrixOperations.get(3);
      String[][] dataFromMatrix = reader(matrix1Data, rowAndCol1);
      MatrixCalculator matrixCalculator = new MatrixCalculator(dataFromMatrix);

      // get the nullity of the matrix and change to border pane
      resultNum = matrixCalculator.getNullity();
      resultShower = resultBuilder("Operation: Nullity", "Nullity",
          dataFromMatrix, resultNum);
      scrollPane(vBoxR, resultShower);
      stateModifer();
    } catch (NumberFormatException e) {
      correctness = false;
      alert("Error", e.getMessage());
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for Trace
   * 
   * @param vBoxR                  VBox vBoxR
   * @param matrix1Data            List<TextField> matrix1Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   */
  private void traceSetter(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> rowAndCol1, List<Button> singleMatrixOperations) {
    try {
      latestMOpera = singleMatrixOperations.get(4);
      String[][] dataFromMatrix = reader(matrix1Data, rowAndCol1);
      MatrixCalculator matrixCalculator = new MatrixCalculator(dataFromMatrix);

      // get the trace of the matrix and change to border pane
      String resultTrace = matrixCalculator.getTrace();
      resultNum = resultTrace;
      resultShower = resultBuilder("Operation: Trace", "Trace", dataFromMatrix,
          resultTrace);
      scrollPane(vBoxR, resultShower);
      stateModifer();
    } catch (MatrixDimensionsMismatchException e) {
      correctness = false;
      alert("MatrixDimensionError",
          "Sorry, the matrix you entered is not a square matrix" + lineSeparator
              + "To compute the trace, " + lineSeparator
              + "it has to be a square matrix");
    } catch (NumberFormatException e1) {
      correctness = false;
      alert("Error", e1.getMessage());
    } catch (Exception e2) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for LUP
   * 
   * @param vBoxR                  VBox vBoxR
   * @param matrix1Data            List<TextField> matrix1Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   */
  private void lUPSetter(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> rowAndCol1, List<Button> singleMatrixOperations) {
    try {
      latestMOpera = singleMatrixOperations.get(5);
      String[][] dataFromMatrix = reader(matrix1Data, rowAndCol1);
      MatrixCalculator matrixCalculator = new MatrixCalculator(dataFromMatrix);

      // get the LUP decomposition of the matrix and change to border pane
      List<String[][]> resultLUP = matrixCalculator.getLUPDecomposition();
      results.clear();
      results.addAll(resultLUP.stream().collect(toList()));
      if (resultLUP.size() > 2) {
        resultShower =
            resultBuilderLUP("Operation: LUP", "LUP", resultLUP.get(2),
                dataFromMatrix, resultLUP.get(0), resultLUP.get(1));
      } else {
        resultShower = resultBuilderQR("Operation: LUP", "LUP", dataFromMatrix,
            resultLUP.get(0), resultLUP.get(1));
      }
      scrollPane(vBoxR, resultShower);
      stateModifer();
    } catch (MatrixDimensionsMismatchException e) {
      correctness = false;
      alert("MatrixDimensionError",
          "Sorry, the matrix you entered is not a square matrix" + lineSeparator
              + "To do the LUP decomposition, " + lineSeparator
              + "it has to be a square matrix");
    } catch (NumberFormatException e1) {
      correctness = false;
      alert("Error", e1.getMessage());
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for GE
   * 
   * @param vBoxR                  VBox vBoxR
   * @param matrix1Data            List<TextField> matrix1Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   */
  private void geSetter(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> rowAndCol1, List<Button> singleMatrixOperations) {
    try {
      latestMOpera = singleMatrixOperations.get(6);
      String[][] dataFromMatrix = reader(matrix1Data, rowAndCol1);
      MatrixCalculator matrixCalculator = new MatrixCalculator(dataFromMatrix);

      // get the guassianelimination of the matrix
      String[][] resultGE = matrixCalculator.getGuassianElimination();
      results.clear();
      results.add(resultGE);
      resultShower =
          resultBuilder("Operation: GE", "GE", dataFromMatrix, resultGE);
      scrollPane(vBoxR, resultShower);
      stateModifer();
    } catch (NumberFormatException e) {
      correctness = false;
      alert("Error", e.getMessage());
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for cholesky
   * 
   * @param vBoxR                  VBox vBoxR
   * @param matrix1Data            List<TextField> matrix1Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   */
  private void choleskySetter(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> rowAndCol1, List<Button> singleMatrixOperations) {
    try {
      latestMOpera = singleMatrixOperations.get(7);
      String[][] dataFromMatrix = reader(matrix1Data, rowAndCol1);
      MatrixCalculator matrixCalculator = new MatrixCalculator(dataFromMatrix);

      // get the cholesky decomposition of the matrix
      List<String[][]> resultCholesky =
          matrixCalculator.getCholeskyDecomposition();
      results.clear();
      results.addAll(resultCholesky.stream().collect(toList()));
      resultShower = resultBuilderQR("Operation: Cholesky", "Cholesky",
          dataFromMatrix, resultCholesky.get(0), resultCholesky.get(1));
      scrollPane(vBoxR, resultShower);
      stateModifer();
    } catch (MatrixDimensionsMismatchException e1) {
      correctness = false;
      alert("MatrixDimensionError",
          "Sorry, the matrix you entered is not a square symmetic matrix"
              + lineSeparator + "To do the Cholesky decomposition, "
              + lineSeparator + "it has to be a square symmetic matrix");
    } catch (MatrixArithmeticException e2) {
      correctness = false;
      alert("MatriArithmeticError",
          "Sorry, the matrix you entered is not a square symmetic matrix"
              + lineSeparator + "To do the Cholesky decomposition, "
              + lineSeparator + "it has to be a square symmetic matrix");
    } catch (NumberFormatException e3) {
      correctness = false;
      alert("Error", e3.getMessage());
    } catch (ArithmeticException e4) {
      correctness = false;
      alert("Error", "Sorry, Exception: " + e4.getMessage());
    } catch (Exception e5) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for EIV
   * 
   * @param vBoxR                  VBox vBoxR
   * @param matrix1Data            List<TextField> matrix1Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   */
  private void eivSetter(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> rowAndCol1, List<Button> singleMatrixOperations) {
    try {
      latestMOpera = singleMatrixOperations.get(8);
      String[][] dataFromMatrix = reader(matrix1Data, rowAndCol1);
      MatrixCalculator matrixCalculator = new MatrixCalculator(dataFromMatrix);

      // get the eigenvalue of the matrix
      String resultEIV = matrixCalculator.getEigenValues();
      resultNum = resultEIV;
      resultShower =
          resultBuilder("Operation: EIV", "EIV", dataFromMatrix, resultEIV);
      scrollPane(vBoxR, resultShower);
      stateModifer();
    } catch (MatrixDimensionsMismatchException e) {
      correctness = false;
      alert("MatrixDimensionError",
          "Sorry, the matrix you entered is not a square matrix" + lineSeparator
              + "To compute the eigenvalue of a matrix, " + lineSeparator
              + "it has to be a square matrix");
    } catch (NumberFormatException e1) {
      correctness = false;
      alert("Error", e1.getMessage());
    } catch (Exception e2) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for Rank
   * 
   * @param vBoxR                  VBox vBoxR
   * @param matrix1Data            List<TextField> matrix1Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   */
  private void rankSetter(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> rowAndCol1, List<Button> singleMatrixOperations) {
    try {
      latestMOpera = singleMatrixOperations.get(9);
      String[][] dataFromMatrix = reader(matrix1Data, rowAndCol1);
      MatrixCalculator matrixCalculator = new MatrixCalculator(dataFromMatrix);

      // get the rank of the matrix
      resultNum = matrixCalculator.getRank();
      resultShower =
          resultBuilder("Operation: Rank", "Rank", dataFromMatrix, resultNum);
      scrollPane(vBoxR, resultShower);
      stateModifer();
    } catch (NumberFormatException e) {
      correctness = false;
      alert("Error", e.getMessage());
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for Transpose
   * 
   * @param vBoxR                  VBox vBoxR
   * @param matrix1Data            List<TextField> matrix1Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   */
  private void transposeSetter(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> rowAndCol1, List<Button> singleMatrixOperations) {
    try {
      latestMOpera = singleMatrixOperations.get(10);
      String[][] dataFromMatrix = reader(matrix1Data, rowAndCol1);
      MatrixCalculator matrixCalculator = new MatrixCalculator(dataFromMatrix);

      // get the transpose matrix
      String[][] resultTS = matrixCalculator.getTranspose();
      results.clear();
      results.add(resultTS);
      resultShower =
          resultBuilder("Operation: TS", "TS", dataFromMatrix, resultTS);
      scrollPane(vBoxR, resultShower);
      stateModifer();
    } catch (NumberFormatException e) {
      alert("Error", e.getMessage());
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for power
   * 
   * @param vBoxR       VBox vBoxR
   * @param matrix1Data List<TextField> matrix1Data
   * @param rowAndCol1  List<TextField> rowAndCol1
   * @param powerButton Button powerButton
   * @param powerInput  TextField powerInput
   */
  private void powerSetter(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> rowAndCol1, Button powerButton, TextField powerInput) {
    try {
      latestMOpera = powerButton;
      int n = Integer.parseInt(powerInput.getText());
      this.power = String.valueOf(n);
      try {
        String[][] dataFromMatrix = reader(matrix1Data, rowAndCol1);
        MatrixCalculator matrixCalculator =
            new MatrixCalculator(dataFromMatrix);

        // get the matrix of n power
        String[][] resultPw = matrixCalculator.getPow(n);
        results.clear();
        results.add(resultPw);
        resultShower = resultBuilder("Operation: POWER", "PowerOf " + n,
            dataFromMatrix, resultPw);
        scrollPane(vBoxR, resultShower);
        stateModifer();

      } catch (MatrixDimensionsMismatchException e1) {
        correctness = false;
        alert("MatrixDimensionError",
            "Sorry, the matrix you entered is not a square matrix"
                + lineSeparator + "To compute the power of a matrix,"
                + lineSeparator + " it has to be a square matrix");
      } catch (MatrixArithmeticException e2) {
        correctness = false;
        alert("MatriArithmeticError",
            "Sorry, the matrix you entered is non-invertible," + lineSeparator
                + " so it does not have negative exponent");
      } catch (NumberFormatException e3) {
        correctness = false;
        alert("Error", e3.getMessage());
      } catch (ArithmeticException e4) {
        correctness = false;
        alert("Error", "Sorry, Exception: " + e4.getMessage());
      } catch (Exception e5) {
        /* If caught, do nothing */
      }
    } catch (NumberFormatException e) {
      correctness = false;
      alert("NumberFormatError",
          "Sorry, the number you entered is not an Integer");
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for open
   * 
   * @param primaryStage           Stage primaryStage
   * @param fileChooser            FileChooser fileChooser
   * @param selector               HBox selector
   * @param filter                 ComboBox<String> filter
   * @param pages                  TextField pages
   * @param total                  TextField total
   * @param confirm                Button confirm
   * @param matrix1Data            List<TextField> matrix1Data
   * @param matrix2Data            List<TextField> matrix2Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param rowAndCol2             List<TextField> rowAndCol2
   * @param enableSecond           CheckBox enableSecond
   * @param operators              List<Button> operators
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   * @param powerButton            Button powerButton
   * @param powerInput             TextField powerInput
   */
  private void openSetter(Stage primaryStage, FileChooser fileChooser,
      HBox selector, ComboBox<String> filter, TextField pages, TextField total,
      Button confirm, List<TextField> matrix1Data, List<TextField> matrix2Data,
      List<TextField> rowAndCol1, List<TextField> rowAndCol2,
      CheckBox enableSecond, List<Button> operators,
      List<Button> singleMatrixOperations, Button powerButton,
      TextField powerInput) {
    try {
      // Use the FileChooser to retrieve the file path
      File file = fileChooser.showOpenDialog(primaryStage);
      if (file == null || !file.getName().endsWith(".json")) {
        alert("Error: File name mismatch", "Please rechoose the file"
            + lineSeparator + "The name of the file must end with '.json'!");
      } else {
        // Invoke OpeartionParser
        try {

          // Invoke parser to acquire data
          OpeartionParser parser = new OpeartionParser(file);
          // Acquire lists calculations
          lists = parser.getCalculations();
          // Enable the selector Pane
          selector.setDisable(false);
          total.setText(String.valueOf(lists.size()));

          // Compute each calculation and update the list
          for (int i = 1; i <= lists.size(); i++) {
            CalSteps step = lists.get(i - 1);
            String operationOperator = step.getOperation();
            isNum = switcher(matrix1Data, matrix2Data, rowAndCol1, rowAndCol2,
                enableSecond, operators, singleMatrixOperations, powerButton,
                powerInput, step, operationOperator);
            ArrayList<String[][]> cloneData = new ArrayList<>();
            step.getDatas().forEach(el -> cloneData.add(el));
            if (!correctness) {
              clearerAfterQuit(selector, pages, total, rowAndCol1, rowAndCol2,
                  filter);
              return;
            }

            // get the clone result the and update
            ArrayList<String[][]> cloneResult = new ArrayList<>();
            cloneResult.addAll(results);
            CalSteps c = new CalSteps("" + operationOperator, cloneData,
                isNum ? "" + resultNum : cloneResult);
            lists.set(i - 1, c);
            existingOperations.add(
                operationOperator.startsWith("PowerOf") ? "Power"
                    : operationOperator);
          }
          filter.getItems().removeIf(i -> !i.equals("All"));
          filter.getItems().addAll(existingOperations);
          filter.getSelectionModel().selectFirst();
          pages.setText("1");
          state = false;
          correctness = true;
          confirm.fire();
        } catch (ParseException e2) {
          alert("Error", "Parse fail, please check you .json file");
        } catch (IOException e3) {
          alert("Error", "Fatal issues during IO processing");
        } catch (Exception e4) {
          clearerAfterQuit(selector, pages, total, rowAndCol1, rowAndCol2,
              filter);
          alert("Error", "Your json file contains invalid operations,"
              + lineSeparator + " please rechoose the file");
        }
      }
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for pages
   * 
   * @param pages TextField pages
   */
  private void pagesSetter(TextField pages) {
    try {
      if (pages.isFocused()) {
        caretPosition = pages.getCaretPosition();
        focusedTextField = pages;
      }
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for confirm
   * 
   * @param pages                  TextField pages
   * @param matrix1Data            List<TextField> matrix1Data
   * @param matrix2Data            List<TextField> matrix2Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param rowAndCol2             List<TextField> rowAndCol2
   * @param enableSecond           CheckBox enableSecond
   * @param operators              List<Button> operators
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   * @param powerButton            Button powerButton
   * @param powerInput             TextField powerInput
   */
  private void confirmSetter(TextField pages, List<TextField> matrix1Data,
      List<TextField> matrix2Data, List<TextField> rowAndCol1,
      List<TextField> rowAndCol2, CheckBox enableSecond, List<Button> operators,
      List<Button> singleMatrixOperations, Button powerButton,
      TextField powerInput) {
    try {
      // Update the prevPage and save the result
      updater(prevPage, matrix1Data, rowAndCol1, matrix2Data, rowAndCol2,
          enableSecond, isNum, false, update);
      state = false;
      correctness = true;
      // Proceed to next page
      int page = Integer.parseInt(pages.getText());
      prevPage = page;
      if (page < 1 || page > lists.size()) {
        throw new IllegalArgumentException();
      }
      CalSteps step = lists.get(page - 1);
      String operationOperator = step.getOperation();
      isNum = switcher(matrix1Data, matrix2Data, rowAndCol1, rowAndCol2,
          enableSecond, operators, singleMatrixOperations, powerButton,
          powerInput, step, operationOperator);
    } catch (IllegalArgumentException e1) {
      alert("Error", "The page number you entered is invalid");
    } catch (Exception e) {
      alert("Error", "Your input is incorrect");
    }
  }

  /**
   * Setter for forward
   * 
   * @param pages                  TextField pages
   * @param confirm                Button confirm
   * @param matrix1Data            List<TextField> matrix1Data
   * @param matrix2Data            List<TextField> matrix2Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param rowAndCol2             List<TextField> rowAndCol2
   * @param enableSecond           CheckBox enableSecond
   * @param operators              List<Button> operators
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   * @param powerButton            Button powerButton
   * @param powerInput             TextField powerInput
   */
  private void forWardSetter(TextField pages, Button confirm,
      List<TextField> matrix1Data, List<TextField> matrix2Data,
      List<TextField> rowAndCol1, List<TextField> rowAndCol2,
      CheckBox enableSecond, List<Button> operators,
      List<Button> singleMatrixOperations, Button powerButton,
      TextField powerInput) {
    try {

      if (!filtering) {
        // Update the prevPage
        int num = Integer.parseInt(pages.getText());
        updater(num, matrix1Data, rowAndCol1, matrix2Data, rowAndCol2,
            enableSecond, isNum, false, update);
        state = false;
        correctness = true;

        // Resetting the page number
        if (num != 1) {
          num -= 1;
          pages.setText(String.valueOf(num));
          confirm.fire();
        }
      } else {
        int num = Integer.parseInt(pages.getText());
        if (num != 1) {
          num -= 1;
          pages.setText(String.valueOf(num));
          CalSteps step = categoryList.get(num - 1);
          String operationOperator = step.getOperation();
          switcher(matrix1Data, matrix2Data, rowAndCol1, rowAndCol2,
              enableSecond, operators, singleMatrixOperations, powerButton,
              powerInput, step, operationOperator);
        }
      }
    } catch (Exception e) {
      alert("Error", "The page number you entered is invalid");
    }
  }

  /**
   * Setter for backward
   * 
   * @param pages                  TextField pages
   * @param confirm                Button confirm
   * @param matrix1Data            List<TextField> matrix1Data
   * @param matrix2Data            List<TextField> matrix2Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param rowAndCol2             List<TextField> rowAndCol2
   * @param enableSecond           CheckBox enableSecond
   * @param operators              List<Button> operators
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   * @param powerButton            Button powerButton
   * @param powerInput             TextField powerInput
   */
  private void backWardSetter(TextField pages, Button confirm,
      List<TextField> matrix1Data, List<TextField> matrix2Data,
      List<TextField> rowAndCol1, List<TextField> rowAndCol2,
      CheckBox enableSecond, List<Button> operators,
      List<Button> singleMatrixOperations, Button powerButton,
      TextField powerInput) {
    try {

      if (!filtering) {
        // Update the prevPage
        int num = Integer.parseInt(pages.getText());
        updater(num, matrix1Data, rowAndCol1, matrix2Data, rowAndCol2,
            enableSecond, isNum, false, update);
        state = false;
        correctness = true;

        // Resetting the page number
        if (num != lists.size()) {
          num += 1;
          pages.setText(String.valueOf(num));
          confirm.fire();
        }
      } else {
        int num = Integer.parseInt(pages.getText());
        if (num != categoryList.size()) {
          num += 1;
          pages.setText(String.valueOf(num));
          CalSteps step = categoryList.get(num - 1);
          String operationOperator = step.getOperation();
          switcher(matrix1Data, matrix2Data, rowAndCol1, rowAndCol2,
              enableSecond, operators, singleMatrixOperations, powerButton,
              powerInput, step, operationOperator);
        }
      }
    } catch (Exception e) {
      alert("Error", "The page number you entered is invalid");
    }
  }

  /**
   * Setter for add
   * 
   * @param filter       ComboBox<String> filter
   * @param forward      Button forward
   * @param pages        TextField pages
   * @param total        TextField total
   * @param backward     Button backward
   * @param confirm      Button confirm
   * @param add          Button add
   * @param addConfirm   Button addConfirm
   * @param delete       Button delete
   * @param quit         Button quit
   * @param matrix1Data  List<TextField> matrix1Data
   * @param matrix2Data  List<TextField> matrix2Data
   * @param rowAndCol1   List<TextField> rowAndCol1
   * @param rowAndCol2   List<TextField> rowAndCol2
   * @param enableSecond CheckBox enableSecond
   */
  private void addSetter(ComboBox<String> filter, Button forward,
      TextField pages, TextField total, Button backward, Button confirm,
      Button add, Button addConfirm, Button delete, Button quit,
      List<TextField> matrix1Data, List<TextField> matrix2Data,
      List<TextField> rowAndCol1, List<TextField> rowAndCol2,
      CheckBox enableSecond) {
    try {
      int page = Integer.parseInt(pages.getText());
      updater(page, matrix1Data, rowAndCol1, matrix2Data, rowAndCol2,
          enableSecond, isNum, false, true);
      state = false;
      correctness = true;
      pages.setText(String.valueOf(page + 1));
      total.setText(String.valueOf(lists.size() + 1));
      clearer(rowAndCol1);
      clearer(rowAndCol2);
      resultShower.getChildren().clear();
      addVisualModifer(filter, forward, pages, backward, confirm, add,
          addConfirm, delete, quit, true);
    } catch (Exception e1) {

    }
  }

  /**
   * Setter for addConfirm
   * 
   * @param filter       ComboBox<String> filter
   * @param forward      Button forward
   * @param pages        TextField pages
   * @param total        TextField total
   * @param backward     Button backward
   * @param confirm      Button confirm
   * @param add          Button add
   * @param addConfirm   Button addConfirm
   * @param delete       Button delete
   * @param quit         Button quit
   * @param matrix1Data  List<TextField> matrix1Data
   * @param matrix2Data  List<TextField> matrix2Data
   * @param rowAndCol1   List<TextField> rowAndCol1
   * @param rowAndCol2   List<TextField> rowAndCol2
   * @param enableSecond CheckBox enableSecond
   */
  private void addConfirmSetter(ComboBox<String> filter, Button forward,
      TextField pages, TextField total, Button backward, Button confirm,
      Button add, Button addConfirm, Button delete, Button quit,
      List<TextField> matrix1Data, List<TextField> matrix2Data,
      List<TextField> rowAndCol1, List<TextField> rowAndCol2,
      CheckBox enableSecond) {
    try {
      int page = Integer.parseInt(pages.getText()) - 1;
      if (correctness) {

        // update the calstep
        updater(page, matrix1Data, rowAndCol1, matrix2Data, rowAndCol2,
            enableSecond, isNum, true, true);
        state = false;
        addVisualModifer(filter, forward, pages, backward, confirm, add,
            addConfirm, delete, quit, false);
        String operation = latestMOpera.getText();
        if (operation.equals("Gauss-Elim")) {
          operation = "GE";
        } else if (operation.equals("EiValue")) {
          operation = "EIV";
        } else if (operation.equals("Transpose")) {
          operation = "Trans";
        } else if (operation.equals("Power")) {
          operation = "Power";
        }
        existingOperations.add(operation);
        filter.getItems().removeIf(i -> !i.equals("All"));
        filter.getItems().addAll(existingOperations);
        filter.getSelectionModel().selectFirst();
      } else {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit add?");
        alert.setContentText("The calculation is incomplete" + lineSeparator
            + "Do you want to cancel addition?");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        // Two types of buttons, yes, no
        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        alert.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> diagResult = alert.showAndWait();

        // If the user chose yes
        if (diagResult.get() == yes) {
          pages.setText(String.valueOf(page));
          total.setText(String.valueOf(Integer.parseInt(total.getText()) - 1));
          addVisualModifer(filter, forward, pages, backward, confirm, add,
              addConfirm, delete, quit, false);
          confirm.fire();
        }
      }
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for delete
   * 
   * @param filter  ComboBox<String> filter
   * @param pages   TextField pages
   * @param total   TextField total
   * @param confirm Button confirm
   */
  private void deleteSetter(ComboBox<String> filter, TextField pages,
      TextField total, Button confirm) {
    try {
      if (lists.size() != 1) {
        int page = Integer.parseInt(pages.getText());
        operation = lists.get(page - 1).getOperation();
        if (page == lists.size()) {
          update = false;
          pages.setText(String.valueOf(page - 1));
          confirm.fire();
          update = true;
          lists.remove(page - 1);
        } else {
          lists.remove(page - 1);
          update = false;
          confirm.fire();
          update = true;
        }
        if (!lists.stream()
                  .anyMatch(i -> i.getOperation().contains(operation))) {
          existingOperations.remove(operation);
          filter.getItems().removeIf(i -> !i.equals("All"));
          filter.getItems().addAll(existingOperations);
          filter.getSelectionModel().selectFirst();
        }
        total.setText(String.valueOf(lists.size()));
      } else {
        alert("Attention", "You cannot delete the last calculation");
      }
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Setter for filter
   * 
   * @param open                   MenuItem open
   * @param save                   MenuItem save
   * @param filter                 ComboBox<String> filter
   * @param pages                  TextField pages
   * @param total                  TextField total
   * @param confirm                Button confirm
   * @param add                    Button add
   * @param delete                 filter
   * @param quit                   Button quit
   * @param matrix1Data            List<TextField> matrix1Data
   * @param matrix2Data            List<TextField> matrix2Data
   * @param rowAndCol1             List<TextField> rowAndCol1
   * @param rowAndCol2             List<TextField> rowAndCol2
   * @param matrix1                VBox matrix1
   * @param matrix2                VBox matrix2
   * @param matrixOperators        GridPane matrixOperators
   * @param enableSecond           CheckBox enableSecond
   * @param operators              List<Button> operators
   * @param singleOperationPanel   GridPane singleOperationPanel
   * @param singleMatrixOperations List<Button> singleMatrixOperations
   * @param powerButton            Button powerButton
   * @param powerInput             TextField powerInput
   */
  private void filterSetter(MenuItem open, MenuItem save,
      ComboBox<String> filter, TextField pages, TextField total, Button confirm,
      Button add, Button delete, Button quit, List<TextField> matrix1Data,
      List<TextField> matrix2Data, List<TextField> rowAndCol1,
      List<TextField> rowAndCol2, VBox matrix1, VBox matrix2,
      GridPane matrixOperators, CheckBox enableSecond, List<Button> operators,
      GridPane singleOperationPanel, List<Button> singleMatrixOperations,
      Button powerButton, TextField powerInput) {
    try {
      String opr = filter.getSelectionModel().getSelectedItem();
      if (opr != null) {
        if (filtering && opr.equals("All")) {

          // modify the button and update
          buttonsModifers(matrix1, matrix2, matrixOperators,
              singleOperationPanel, open, save, confirm, add, delete, quit,
              pages, false);
          rowAndCol1.forEach(i -> i.setEditable(true));
          rowAndCol2.forEach(i -> i.setEditable(true));
          filtering = false;
          total.setText(String.valueOf(lists.size()));
          update = false;
          confirm.fire();
          update = true;
          filter.getSelectionModel().selectFirst();
        } else {
          filtering = true;
          categoryList.clear();
          selectorInto(opr);
          CalSteps step = categoryList.get(0);
          String operationOperator = step.getOperation();
          switcher(matrix1Data, matrix2Data, rowAndCol1, rowAndCol2,
              enableSecond, operators, singleMatrixOperations, powerButton,
              powerInput, step, operationOperator);
          buttonsModifers(matrix1, matrix2, matrixOperators,
              singleOperationPanel, open, save, confirm, add, delete, quit,
              pages, true);
          pages.setText(String.valueOf(1));
          total.setText(String.valueOf(categoryList.size()));
          rowAndCol1.forEach(i -> i.setEditable(false));
          rowAndCol2.forEach(i -> i.setEditable(false));
        }
      }
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Set the EventHandler for save
   * 
   * @param primaryStage Stage primaryStage
   * @param fileChooser  FileChooser fileChooser
   */
  private void saveSetter(Stage primaryStage, FileChooser fileChooser) {
    try {
      // Acquire filePath from the file Chooser
      File file = fileChooser.showSaveDialog(primaryStage);
      if (file == null || !file.getName().endsWith(".json")) {
        alert("Error: File name mismatch", "Please rechoose the file"
            + lineSeparator + "The name of the file must end with '.json'!");
      } else {
        // Invoke Writer
        try {
          // Compute each calculation and update the list
          Writer writer = new Writer(lists);
          writer.save(file);
          saved = true;
        } catch (FileNotFoundException e1) {
          /* Should not encounter */
        }catch (NullPointerException e2) {
          alert("Error", "Sorry, please first load a file before saving the file!");
        } catch (Exception e) {
          alert("Error", "Fatal issues during IO processing");
        }
      }
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Set the EventHandler for quit
   * 
   * @param save       MenuItem save
   * @param selector   HBox selector
   * @param filter     ComboBox<String> filter
   * @param pages      TextField pages
   * @param total      TextField total
   * @param quit       Button quit
   * @param rowAndCol1 List<TextField> rowAndCol1
   * @param rowAndCol2 List<TextField> rowAndCol2
   */
  private void quitSetter(MenuItem save, HBox selector, ComboBox<String> filter,
      TextField pages, TextField total, Button quit, List<TextField> rowAndCol1,
      List<TextField> rowAndCol2) {
    try {
      // If the user has not saved the data, invoke the alert pane
      if (!saved) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Quit?");
        alert.setContentText(
            "Do you want to save the calculation before you leave?");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        // Three types of buttons, yes, no and cancel
        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, no, cancel);
        Optional<ButtonType> diagResult = alert.showAndWait();

        // If the user chose yes, invoke save button
        if (diagResult.get() == yes) {
          save.fire();

          // If save failed, re-Invoke quit button
          if (!saved) {
            quit.fire();
          } else {

            // If success, clear the screen of Matrix Calculator
            clearerAfterQuit(selector, pages, total, rowAndCol1, rowAndCol2,
                filter);
          }

          // If the user chose no, then clear the screen of Matrix Calculator
        } else if (diagResult.get() == no) {
          clearerAfterQuit(selector, pages, total, rowAndCol1, rowAndCol2,
              filter);
        }
        // If user choose Cancel, then return to previous state

        // If the user has saved, then quit directly
      } else {
        clearerAfterQuit(selector, pages, total, rowAndCol1, rowAndCol2,
            filter);
      }
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Add EventHandler to menu
   * 
   * @param about       MenuBar about
   * @param developer   MenuBar developer
   * @param instruction MenuBar instruction
   */
  private void menuEventHandlerSetter(Menu about, MenuItem developer,
      MenuItem instruction) {
    // Add the EventHandler when clicking instruction
    instruction.setOnAction(event -> {
      try {
        instructionSetter();
      } catch (Exception e) {
        /* If caught, do nothing */
      }
    });

    // Add the EventHandler when clicking the developer
    developer.setOnAction(event -> {
      try {
        developerSetter();
      } catch (Exception e) {
        /* If caught, do nothing */
      }
    });
    about.getItems().addAll(instruction, developer);
  }

  /**
   * Set for developer
   */
  private void developerSetter() {
    Alert alert = new Alert(AlertType.WARNING);
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    alert.setTitle("About Developer");
    alert.setHeaderText("Matrix-Calculator");
    alert.setContentText("Developedby:        " + lineSeparator
        + "    Chengpo Yan - cyan46@wisc.edu" + lineSeparator
        + "    Jinming Zhang - jzhang2279@wisc.edu" + lineSeparator
        + "    Zexin Li - zli885@wisc.edu" + lineSeparator
        + "    Houming Chen - hchen634@wisc.edu" + lineSeparator
        + "    Chengxu Bian - cbian7@wisc.edu");
    alert.showAndWait();
  }

  /**
   * Set for instruction
   */
  private void instructionSetter() {
    Alert alert = new Alert(AlertType.WARNING);
    alert.getDialogPane().setMinHeight(450);
    alert.setTitle("Instruction to use");
    alert.setHeaderText("Please Read");

    TextArea area = new TextArea();
    area.setWrapText(true);
    area.setEditable(false);
    area.setText("Welcome to our Matrix Calculator!" + lineSeparator
        + "This is a sophisticated and complicated Calculator, and it "
        + "supports the operations including normal calculation, sequence analyzing, and matrix calculation."
        + lineSeparator + lineSeparator
        + "Attention: You may notice some buttons being disabled when you switching to the Matrix Calculation "
        + "section, to re-enable them you should click on the white area(TextArea) on the left of the "
        + "calculator or move the mouse to the top white area(TextField). We are designing this switching "
        + "because some buttons are not allowed during the matrix calculation."
        + lineSeparator + lineSeparator
        + "1. To perform a normal calculation, simply click on the white "
        + "input field of the calculator to activate the function if some buttons are disabled, "
        + "when you finish inputting, you can click on the '=' button to see the result."
        + lineSeparator + lineSeparator
        + "2. To perform analyze sequence function, click on button of 'analyze sequence' "
        + "and you should see that the space button and the '=' button have been activated. "
        + "Now, you can click the nubmer buttons to input numbers and use the space button "
        + "to separate them. After finishing inputting, you can click on '=' to see the output"
        + lineSeparator + lineSeparator
        + "3. To perform matrix calculation, you need to choose between whether you need to "
        + "do operations on a single matrix or two matrixes. To perform calculation on a single matrix, "
        + "simply click on the row & col input field and type in an integer to tell the calculator the "
        + "dimension of the matrix, then you can type the value(Integer or Double) to the specific entry. "
        + "After that, you can click on the buttons below the matrix to see the result. "
        + "If you are using the power button, you need to type in the value first. To perform "
        + "calculation on two matrixes, you need to click on the CheckBox '?' to active the second "
        + "matrix, at the same time, the operation buttons below will be disabled and the operation "
        + "buttons between the two matrixes will be activated. After finishing inputting, click on '+', "
        + "'-' or '*' to see the result. 'c1', 'c2' is used to clear the matrix"
        + lineSeparator + lineSeparator
        + "4. Our Matrix calculator supports file input/output. To open a file, click on Menu and select open to choose your file. "
        + "Your file must be ended with .json otherwise we will not accept it. In addition, the content of your "
        + "file input must be correct to be accepted by our calculator. If your input file contains "
        + "too many calculations, it may take a while for the calculator to show the operations you have "
        + "plugged in. After you see your operations, you can click on the buttons on the top of the "
        + "application since they have been activated. You can view back and forth, you can even "
        + "type in the input field and click on the confirm button to lead you to the operation you want. "
        + "You can edit your operation, and new changes will be recorded if you do some calculations on "
        + "those matrixes and they are correct, otherwise we will not record it. Our calculator "
        + "also supports add new operation and delete operation. You can add a new operation by clicking "
        + "on 'Add' button. After producing correct output, you can record it by clicking on 'Add Confirm' "
        + "button. You can delete an operation by clicking on 'Delete' button. Remember: You cannot delete the last operation."
        + lineSeparator + lineSeparator
        + "5. In 'reading file mode', we have filtering function, it is located in the left of the top button bars. "
        + "It is a dynamic ComboBox and its content will be adjusted based on your input and your manipulations. "
        + "During the filtering mode, you can switch between operations using the back and forth button. You cannot "
        + "add and remove operations and use the page input TextField. In addition, you cannot save the file or quit "
        + "the 'reading file mode'. You can return back to normal state by selecting 'All' in the ComboBox."
        + lineSeparator + lineSeparator
        + "6. If you finished editing your operations, you can quit the 'reading file mode' by clicking on 'quit' "
        + "button, and it will ask you whether you want to save the file. 'Yes' will open a fileChooser, 'No' "
        + "will quit the 'reading file mode', 'Cancel' will return back. Note: If you have save your file through "
        + "the 'save' in Menu, it will quit directly if during this period you do not make any change."
        + lineSeparator + lineSeparator
        + "7. Out calculator is friendly to the computer user who does not have a keyboard. Out input focus can be "
        + "switching between TextFields to give you the best user experience."
        + lineSeparator + lineSeparator
        + "This is a extremely complicated project, and we all performed our virtuosities in this project. We hope you will enjoy it!!!");
    area.setStyle("-fx-font-size: 16px;");
    alert.getDialogPane().setContent(area);
    alert.setResizable(true);
    alert.showAndWait();
  }

  /**
   * Add EventHandler to sequence analyzing
   * 
   * @param input     TextField input
   * @param space     Button space
   * @param buttons   List<Button> buttons
   * @param notNumber List<Button> notNumber
   */
  private void analyzeSequenceSetter(TextField input, Button space,
      List<Button> buttons, List<Button> notNumber) {
    analyze = true;
    space.setDisable(false);

    // Disabling related buttons
    notNumber.stream().forEach(b -> b.setDisable(true));
    buttons.get(buttons.size() - 1).setDisable(false);
    input.setOnMouseEntered(event -> {
      buttons.get(buttons.size() - 1).setDisable(false);
    });
  }

  /**
   * Set EventHandler to button
   * 
   * @param input     TextField input
   * @param result    TextArea result
   * @param space     Button space
   * @param notNumber List<Button> notNumber
   * @param btn       Button btn
   */
  private void buttonActionSetter(TextField input, TextArea result,
      Button space, List<Button> notNumber, Button btn) {
    try {
      // Set the related operation according to their inside text
      String temp = btn.getText().trim();

      // Case of Clear
      if (temp.equals("C")) {
        input.clear();
        caretPosition = 0;

        // Case of delete one character
      } else if (temp.equals("<-")) {
        try {
          focusedTextField.setText(
              focusedTextField.getText().substring(0, caretPosition - 1)
                  + focusedTextField.getText().substring(caretPosition));
          caretPosition--;
        } catch (Exception e) {
          /* If catch, do nothing */
        }

        // Case of positive or negative
      } else if (temp.equals("+/-")) {
        try {
          String fromInput = focusedTextField.getText();
          focusedTextField.setText(
              fromInput.startsWith("-") ? fromInput.substring(1)
                  : "-" + fromInput);
        } catch (Exception e) {
          /* If catch, do nothing */
        }

        // Case for output
      } else if (temp.equals("=")) {
        try {

          // '=' served for sequence and simple calculation

          // Case of simple calculation
          if (!analyze) {
            result.appendText(input.getText() + "" + lineSeparator + "="
                + Calculator.calcul("0" + input.getText()) + "" + lineSeparator
                + "");

            // Case of analyze sequence
          } else {
            result.appendText(SequenceSummary.summary(input.getText()));
            analyze = false;
            space.setDisable(true);
            notNumber.stream().forEach(b -> b.setDisable(false));
            input.setOnMouseEntered(e -> {
              notNumber.stream().forEach(b -> b.setDisable(false));
            });
          }

          // Catch for wrong expression
        } catch (Exception e) {
          alert("Wrong Expression",
              "The equation you entered cannot be calculated" + lineSeparator
                  + "Please press 'C' and try again");
        }

        // Case for number and dot
      } else if (temp.matches("\\d") || temp.matches("\\.")) {
        try {
          focusedTextField.insertText(caretPosition, temp.trim());
          ++caretPosition;
        } catch (Exception e) {
          /* If catch, do nothing */
        }

        // Case for absolute value
      } else {
        try {
          input.insertText(caretPosition, temp.replace("x", "").trim());
          ++caretPosition;
        } catch (Exception e) {
          /* If catch, do nothing */
        }
      }
    } catch (Exception e1) {
      /* If caught, do nothing */
    }
  }

  /**
   * Add into the categoryList
   * 
   * @param opr the operation
   */
  private void selectorInto(String opr) {
    categoryList.addAll(lists.stream()
                             .filter(i -> i.getOperation().contains(opr))
                             .collect(toList()));
  }

  /**
   * Modify the state of several buttons
   * 
   * @param matrix1              VBox
   * @param matrix2              VBox
   * @param matrixOperators      GridPane
   * @param singleOperationPanel GridPane
   * @param open                 MenuItem
   * @param save                 MenuItem
   * @param confirm              Button
   * @param add                  Button
   * @param delete               Button
   * @param quit                 Button
   * @param disabled             boolean value
   */
  private void buttonsModifers(VBox matrix1, VBox matrix2,
      GridPane matrixOperators, GridPane singleOperationPanel, MenuItem open,
      MenuItem save, Button confirm, Button add, Button delete, Button quit,
      TextField pages, boolean disabled) {
    matrix1.setDisable(disabled);
    matrix2.setDisable(disabled);
    singleOperationPanel.setVisible(!disabled);
    matrixOperators.setVisible(!disabled);
    add.setDisable(disabled);
    delete.setDisable(disabled);
    quit.setDisable(disabled);
    pages.setEditable(!disabled);
    confirm.setDisable(disabled);
    open.setDisable(disabled);
    save.setDisable(disabled);
  }

  /**
   * Setter for the buttons and textField related to add
   * 
   * @param filter     ComboBox
   * @param forward    Button
   * @param pages      TextField
   * @param backward   Button
   * @param confirm    Button
   * @param add        Button
   * @param addConfirm Button
   * @param delete     Button
   * @param quit       Button
   * @param disabled   boolean value
   */
  private void addVisualModifer(ComboBox<String> filter, Button forward,
      TextField pages, Button backward, Button confirm, Button add,
      Button addConfirm, Button delete, Button quit, boolean disabled) {
    filter.setDisable(disabled);
    addConfirm.setDisable(!disabled);
    add.setDisable(disabled);
    forward.setDisable(disabled);
    backward.setDisable(disabled);
    pages.setEditable(!disabled);
    confirm.setDisable(disabled);
    quit.setDisable(disabled);
    delete.setDisable(disabled);
  }

  /**
   * Process result and generate the output
   * 
   * @param vBoxR       parent of resultShower
   * @param matrix1Data data from matrix1
   * @param matrix2Data data from matrix2
   * @param rowAndCol1  row and col of matrix1
   * @param rowAndCol2  row and col of matrix2
   * @param string      operation String
   * @param mathString  math operation of string
   */
  private void twoMatrixOperator(VBox vBoxR, List<TextField> matrix1Data,
      List<TextField> matrix2Data, List<TextField> rowAndCol1,
      List<TextField> rowAndCol2, String string, String mathString) {

    // Get data from input field
    String[][] dataFromMatrix1 = reader(matrix1Data, rowAndCol1);
    String[][] dataFromMatrix2 = reader(matrix2Data, rowAndCol2);
    MatrixCalculator matrixCalculator =
        new MatrixCalculator(dataFromMatrix1, dataFromMatrix2);
    try {

      // Compute result
      String[][] resultMatrix;

      switch (mathString) {
        case "+":
          resultMatrix = matrixCalculator.add();
          break;
        case "-":
          resultMatrix = matrixCalculator.subtract();
          break;
        default:
          resultMatrix = matrixCalculator.multiply();
          break;
      }

      // Add result to the recorder of result
      results.clear();
      results.add(resultMatrix);

      // Generate the resultShower
      resultShower = resultBuilder(string, mathString, dataFromMatrix1,
          dataFromMatrix2, resultMatrix);
      // Add ScrollPane and update the scene
      scrollPane(vBoxR, resultShower);

      // Change for related states
      stateModifer();
    } catch (MatrixDimensionsMismatchException e) {
      correctness = false;
      alert("MatrixDimensionError",
          "The dimensions of the Matrixs you entered did not match");
    } catch (NumberFormatException e1) {
      correctness = false;
      alert("Error", e1.getMessage());
    } catch (Exception e2) {
      /* If caught, do nothing */
    }
  }

  /**
   * Modify the State
   */
  private void stateModifer() {
    correctness = true;
    state = true;
    saved = false;
  }

  /**
   * Clear the data from the file
   * 
   * @param selector   selector pane
   * @param pages      pages textField
   * @param total      total textField
   * @param rowAndCol1 row and col of Matrix1
   * @param rowAndCol2 row and col of Matrix2
   * @param filter     ComboBox
   */
  private void clearerAfterQuit(HBox selector, TextField pages, TextField total,
      List<TextField> rowAndCol1, List<TextField> rowAndCol2,
      ComboBox<String> filter) {
    rowAndCol1.get(0).setText("3");
    rowAndCol1.get(1).setText("3");
    rowAndCol2.get(0).setText("3");
    rowAndCol2.get(1).setText("3");

    // clear the result
    resultShower.getChildren().clear();
    pages.clear();
    total.clear();
    selector.setDisable(true);
    prevPage = 0;
    filter.getItems().add("All");
    filter.getSelectionModel().selectFirst();
  }

  /**
   * Switcher of Operation and invoke to calculate
   * 
   * @param  matrix1Data            matrix1
   * @param  matrix2Data            matrix2
   * @param  rowAndCol1             row and col of matrix1
   * @param  rowAndCol2             row and col of matrix2
   * @param  enableSecond           a checkbox
   * @param  operators              the operators
   * @param  singleMatrixOperations matrix buttons
   * @param  powerButton            invoke for power calculation
   * @param  powerInput             input for power
   * @param  step                   CalStep
   * @param  operationOperator      operation
   * @return                        isNum whether is a list matrixes of a
   *                                  StringNums
   */
  private boolean switcher(List<TextField> matrix1Data,
      List<TextField> matrix2Data, List<TextField> rowAndCol1,
      List<TextField> rowAndCol2, CheckBox enableSecond, List<Button> operators,
      List<Button> singleMatrixOperations, Button powerButton,
      TextField powerInput, CalSteps step, String operationOperator) {
    boolean isNum = false;

    // choose different matrix operation
    switch (operationOperator) {
      case "+":
        setterOfTwoMatrixes(step, rowAndCol1, rowAndCol2, matrix1Data,
            matrix2Data);
        if (!enableSecond.isSelected()) {
          enableSecond.fire();
        }
        operators.get(1).fire();
        break;
      case "-":
        setterOfTwoMatrixes(step, rowAndCol1, rowAndCol2, matrix1Data,
            matrix2Data);
        if (!enableSecond.isSelected()) {
          enableSecond.fire();
        }
        operators.get(2).fire();
        break;
      case "*":
        setterOfTwoMatrixes(step, rowAndCol1, rowAndCol2, matrix1Data,
            matrix2Data);
        if (!enableSecond.isSelected()) {
          enableSecond.fire();
        }
        operators.get(3).fire();
        break;
      case "Det":
        cleanAndSet(matrix1Data, rowAndCol1, rowAndCol2, enableSecond, step);
        singleMatrixOperations.get(0).fire();
        isNum = true;
        break;
      case "Inverse":
        cleanAndSet(matrix1Data, rowAndCol1, rowAndCol2, enableSecond, step);
        singleMatrixOperations.get(1).fire();
        break;
      case "QR":
        cleanAndSet(matrix1Data, rowAndCol1, rowAndCol2, enableSecond, step);
        singleMatrixOperations.get(2).fire();
        break;
      case "Nullity":
        cleanAndSet(matrix1Data, rowAndCol1, rowAndCol2, enableSecond, step);
        singleMatrixOperations.get(3).fire();
        break;
      case "Trace":
        cleanAndSet(matrix1Data, rowAndCol1, rowAndCol2, enableSecond, step);
        singleMatrixOperations.get(4).fire();
        isNum = true;
        break;
      case "LUP":
        cleanAndSet(matrix1Data, rowAndCol1, rowAndCol2, enableSecond, step);
        singleMatrixOperations.get(5).fire();
        break;
      case "GE":
        cleanAndSet(matrix1Data, rowAndCol1, rowAndCol2, enableSecond, step);
        singleMatrixOperations.get(6).fire();
        break;
      case "Cholesky":
        cleanAndSet(matrix1Data, rowAndCol1, rowAndCol2, enableSecond, step);
        singleMatrixOperations.get(7).fire();
        break;
      case "EIV":
        cleanAndSet(matrix1Data, rowAndCol1, rowAndCol2, enableSecond, step);
        singleMatrixOperations.get(8).fire();
        isNum = true;
        break;
      case "Rank":
        cleanAndSet(matrix1Data, rowAndCol1, rowAndCol2, enableSecond, step);
        singleMatrixOperations.get(9).fire();
        isNum = true;
        break;
      case "Trans":
        cleanAndSet(matrix1Data, rowAndCol1, rowAndCol2, enableSecond, step);
        singleMatrixOperations.get(10).fire();
        break;
      default:
        String powerString = operationOperator.replace("PowerOf", "");
        cleanAndSet(matrix1Data, rowAndCol1, rowAndCol2, enableSecond, step);
        powerInput.setText(powerString.trim());
        powerButton.fire();
        break;
    }
    return isNum;
  }

  /**
   * Updater of the CalStep
   * 
   * @param page         page number
   * @param matrix1Data  data from matrix1
   * @param rowAndCol1   row and col for matrix1
   * @param matrix2Data  data from matrix2
   * @param rowAndCol2   row and col for matrix2
   * @param enableSecond a checkbox
   * @param isNum        whether the operation is num or not
   * @param isAdd        whether user want to add a new operation
   */
  private void updater(int page, List<TextField> matrix1Data,
      List<TextField> rowAndCol1, List<TextField> matrix2Data,
      List<TextField> rowAndCol2, CheckBox enableSecond, boolean isNum,
      boolean isAdd, boolean update) {
    if (update) {
      if (prevPage != 0 && state && correctness) {

        // clone the result and update
        List<String[][]> cloneResult = new ArrayList<>();
        cloneResult.addAll(results);
        String[][] wMatrix1 = reader(matrix1Data, rowAndCol1);
        String operation = latestMOpera.getText();

        // if check box checked, basic calstep
        if (enableSecond.isSelected()) {
          String[][] wMatrix2 = reader(matrix2Data, rowAndCol2);

          // check if new operation added
          if (!isAdd) {
            lists.set(page - 1,
                new CalSteps(operation, new ArrayList<String[][]>() {
                  private static final long serialVersionUID = 1L;
                  {
                    add(wMatrix1);
                    add(wMatrix2);
                  }
                }, isNum ? "" + resultNum : cloneResult));
          } else {
            lists.add(page,
                new CalSteps(operation, new ArrayList<String[][]>() {
                  private static final long serialVersionUID = 1L;
                  {
                    add(wMatrix1);
                    add(wMatrix2);
                  }
                }, isNum ? "" + resultNum : cloneResult));
          }

          // if check box not selected, matrix calstep
        } else {
          if (operation.equals("Gauss-Elim")) {
            operation = "GE";
          } else if (operation.equals("EiValue")) {
            operation = "EIV";
          } else if (operation.equals("Transpose")) {
            operation = "Trans";
          } else if (operation.equals("Power")) {
            operation = "PowerOf" + this.power;
          }
          if (!isAdd) {
            lists.set(page - 1,
                new CalSteps(operation, new ArrayList<String[][]>() {
                  private static final long serialVersionUID = 1L;
                  {
                    add(wMatrix1);
                  }
                }, isNum ? "" + resultNum : cloneResult));
          } else {
            lists.add(page,
                new CalSteps(operation, new ArrayList<String[][]>() {
                  private static final long serialVersionUID = 1L;
                  {
                    add(wMatrix1);
                  }
                }, isNum ? "" + resultNum : cloneResult));
          }
        }
      }
    }
  }

  /**
   * Routine steps
   * 
   * @param matrix1Data  data from matrix 1
   * @param rowAndCol1   row and column textfield
   * @param rowAndCol2   row and column textfield
   * @param enableSecond the checkbox
   * @param step         information
   */
  private void cleanAndSet(List<TextField> matrix1Data,
      List<TextField> rowAndCol1, List<TextField> rowAndCol2,
      CheckBox enableSecond, CalSteps step) {
    clearer(rowAndCol2);
    setterOfSingleMatrix(step, rowAndCol1, matrix1Data);
    if (enableSecond.isSelected()) {
      enableSecond.fire();
    }
  }

  /**
   * Reset the second Matrix
   * 
   * @param rowAndCol2 row and column textfield
   */
  private void clearer(List<TextField> rowAndCol) {
    rowAndCol.get(0).setText("3");
    rowAndCol.get(1).setText("3");
  }

  /**
   * Setter for Single Matrix environment
   * 
   * @param step        information
   * @param rowAndCol1  row and column textfield
   * @param matrix1Data data from matrix1
   */
  private void setterOfSingleMatrix(CalSteps step, List<TextField> rowAndCol1,
      List<TextField> matrix1Data) {

    // perform calstep and generate matrix
    String[][] matrix1 = step.getDatas().get(0);
    rowAndCol1.get(0).setText(String.valueOf(matrix1.length));
    rowAndCol1.get(1).setText(String.valueOf(matrix1[0].length));
    int count = 0;
    for (int i = 0; i < matrix1.length; i++) {
      for (int j = 0; j < matrix1[i].length; j++) {
        matrix1Data.get(count++).setText(matrix1[i][j]);
      }
    }
  }

  /**
   * Setter for Double Matrixes environment
   * 
   * @param step        information
   * @param matrix2Data data from matrix2
   * @param matrix1Data data from matrix1
   * @param rowAndCol2  row and column textfield
   * @param rowAndCol1  row and column textfield
   */
  private void setterOfTwoMatrixes(CalSteps step, List<TextField> rowAndCol1,
      List<TextField> rowAndCol2, List<TextField> matrix1Data,
      List<TextField> matrix2Data) {

    // perform different calstep
    String[][] matrix1 = step.getDatas().get(0);
    String[][] matrix2 = step.getDatas().get(1);
    rowAndCol1.get(0).setText(String.valueOf(matrix1.length));
    rowAndCol1.get(1).setText(String.valueOf(matrix1[0].length));
    rowAndCol2.get(0).setText(String.valueOf(matrix2.length));
    rowAndCol2.get(1).setText(String.valueOf(matrix2[0].length));

    // generate two matrix setting
    int count = 0;
    for (int i = 0; i < matrix1.length; i++) {
      for (int j = 0; j < matrix1[i].length; j++) {
        matrix1Data.get(count++).setText(matrix1[i][j]);
      }
    }
    count = 0;
    for (int i = 0; i < matrix2.length; i++) {
      for (int j = 0; j < matrix2[i].length; j++) {
        matrix2Data.get(count++).setText(matrix2[i][j]);
      }
    }
  }

  /**
   * Add scrollPane
   * 
   * @param vBoxR        vBox
   * @param resultShower result
   */
  private void scrollPane(VBox vBoxR, BorderPane resultShower) {
    ScrollPane sP = new ScrollPane(resultShower);
    sP.setStyle("-fx-background-color: lightgray;");
    sP.setMinHeight(207);
    sP.setMaxHeight(207);
    sP.setMaxWidth(836);
    vBoxR.getChildren().remove(2);
    vBoxR.getChildren().add(sP);
  }

  /**
   * Method that returns a BorderPane of finished result
   * 
   * @param  string         operation
   * @param  mathString     operation
   * @param  dataFromMatrix source Matrix
   * @param  resultTrace    the result
   * @return                resulted BorderPane
   */
  private BorderPane resultBuilder(String string, String mathString,
      String[][] dataFromMatrix, String result) {
    BorderPane resultedPane = new BorderPane();

    resultedPane.setStyle("-fx-background-color: lightgray;");

    // Set the title of the operation
    Label operationName = new Label(string);
    operationName.setStyle(labelStyle);
    resultedPane.setTop(operationName);

    Label operationMath = new Label(mathString);
    operationMath.setStyle(labelStyle);

    Label equals = new Label("=");
    equals.setStyle(labelStyle);

    // generate the result to the border pane
    GridPane gridSrc = matrixGenerator(dataFromMatrix);

    Label resultedLabel = new Label(result);
    resultedLabel.setStyle(labelStyle);

    HBox resultedHBox = new HBox();
    resultedHBox.getChildren()
                .addAll(operationMath, gridSrc, equals, resultedLabel);

    resultedPane.setCenter(resultedHBox);

    return resultedPane;
  }

  /**
   * Method that returns a BorderPane of finished result
   * 
   * @param  string         operation
   * @param  mathString     operation
   * @param  dataFromMatrix source Matrix
   * @param  l              the L
   * @param  u              the U
   * @param  p              the P
   * @return                resulted BorderPane
   */
  private BorderPane resultBuilderLUP(String string, String mathString,
      String[][] p, String[][] dataFromMatrix, String[][] l, String[][] u) {
    BorderPane resultedPane = new BorderPane();

    resultedPane.setStyle("-fx-background-color: lightgray;");

    // Set the title of the operation
    Label operationName = new Label(string);

    operationName.setStyle(labelStyle);
    resultedPane.setTop(operationName);

    Label operationMath = new Label(mathString);
    operationMath.setStyle(labelStyle);

    Label equals = new Label("=");
    equals.setStyle(labelStyle);

    Label multiply1 = new Label("*");
    equals.setStyle(labelStyle);

    Label multiply2 = new Label("*");
    equals.setStyle(labelStyle);

    // generate the result to the border pane
    GridPane gridSrc = matrixGenerator(dataFromMatrix);
    GridPane lResult = matrixGenerator(l);
    GridPane uResult = matrixGenerator(u);
    GridPane pResult = matrixGenerator(p);

    HBox resultedHBox = new HBox();
    resultedHBox.getChildren()
                .addAll(operationMath, pResult, multiply1, gridSrc, equals,
                    lResult, multiply2, uResult);

    resultedPane.setCenter(resultedHBox);

    return resultedPane;
  }

  /**
   * Method that returns a BorderPane of finished result
   * 
   * @param  string         operation
   * @param  mathString     operation
   * @param  dataFromMatrix source Matrix
   * @param  q              the Q
   * @param  r              the R
   * @return                resulted BorderPane
   */
  private BorderPane resultBuilderQR(String string, String mathString,
      String[][] dataFromMatrix, String[][] q, String[][] r) {
    BorderPane resultedPane = new BorderPane();

    resultedPane.setStyle("-fx-background-color: lightgray;");

    // Set the title of the operation
    Label operationName = new Label(string);
    operationName.setStyle(labelStyle);
    resultedPane.setTop(operationName);

    Label operationMath = new Label(mathString);
    operationMath.setStyle(labelStyle);

    Label equals = new Label("=");
    equals.setStyle(labelStyle);

    Label multiply = new Label("*");
    equals.setStyle(labelStyle);

    // generate the result to the border pane
    GridPane gridSrc = matrixGenerator(dataFromMatrix);
    GridPane qResult = matrixGenerator(q);
    GridPane rResult = matrixGenerator(r);

    HBox resultedHBox = new HBox();
    resultedHBox.getChildren()
                .addAll(operationMath, gridSrc, equals, qResult, multiply,
                    rResult);

    resultedPane.setCenter(resultedHBox);

    return resultedPane;
  }

  /**
   * Method that returns a BorderPane of finished result
   * 
   * @param  string         operation
   * @param  mathString     operation
   * @param  dataFromMatrix source Matrix
   * @param  result         the result
   * @return                resulted BorderPane
   */
  private BorderPane resultBuilder(String string, String mathString,
      String[][] dataFromMatrix, String[][] result) {

    // generate a border pane to contain the result
    BorderPane resultedPane = new BorderPane();

    resultedPane.setStyle("-fx-background-color: lightgray;");

    // Set the title of the operation
    Label operationName = new Label(string);
    operationName.setStyle(labelStyle);
    resultedPane.setTop(operationName);

    Label operationMath = new Label(mathString);
    operationMath.setStyle(labelStyle);

    Label equals = new Label("=");
    equals.setStyle(labelStyle);

    // add the result to the border pane
    GridPane gridSrc = matrixGenerator(dataFromMatrix);
    GridPane resultG = matrixGenerator(result);

    HBox resultedHBox = new HBox();
    resultedHBox.getChildren().addAll(operationMath, gridSrc, equals, resultG);

    resultedPane.setCenter(resultedHBox);

    return resultedPane;
  }

  /**
   * Method that returns a BorderPane of finished result
   * 
   * @param  string       operation
   * @param  mathString   operation
   * @param  src1         source Matrix1
   * @param  src2         source Matrix2
   * @param  resultMatrix resulted Matrix
   * @return              resulted BorderPane
   */
  private BorderPane resultBuilder(String string, String mathString,
      String[][] src1, String[][] src2, String[][] resultMatrix) {

    // generate a border pane to contain the result
    BorderPane resultedPane = new BorderPane();

    resultedPane.setStyle("-fx-background-color: lightgray;");

    // Set the title of the operation
    Label operationName = new Label(string);
    operationName.setStyle(labelStyle);
    resultedPane.setTop(operationName);

    Label operationMath = new Label(mathString);
    operationMath.setStyle(labelStyle);

    Label equals = new Label("=");
    equals.setStyle(labelStyle);

    // add matrix to the border pane
    GridPane gridSrc1 = matrixGenerator(src1);
    GridPane gridSrc2 = matrixGenerator(src2);
    GridPane resultedGrid = matrixGenerator(resultMatrix);

    HBox resultedHBox = new HBox();
    resultedHBox.getChildren()
                .addAll(gridSrc1, operationMath, gridSrc2, equals,
                    resultedGrid);

    resultedPane.setCenter(resultedHBox);

    return resultedPane;
  }

  /**
   * Generate a GridPane representation of Matrix
   * 
   * @param  matrix parameter matrix
   * @return        GridPane representation of the matrix
   */
  private GridPane matrixGenerator(String matrix[][]) {

    // generate a grid pane
    GridPane resultedGrid = new GridPane();
    resultedGrid.setStyle(
        "-fx-background-color: lightgray;-fx-vgap: 1;-fx-hgap: 1;-fx-padding: 1;");
    resultedGrid.setMinHeight(207);
    List<Label> allLabels = new ArrayList<>();

    // add label to the grid pane
    for (int i = 0; i < matrix.length; i++) {
      List<Label> labels = Arrays.stream(matrix[i]).map(str -> {
        Label strLabel = new Label(str);
        strLabel.setStyle(labelStyle);
        strLabel.autosize();
        return strLabel;
      }).collect(toList());
      allLabels.addAll(labels);
      for (int j = 0; j < labels.size(); j++) {
        resultedGrid.add(labels.get(j), j, i);
      }
    }

    int length = 0;
    for (Label single : allLabels) {
      int singleLength = single.getText().length();
      length = singleLength > length ? singleLength : length;
    }
    for (Label single : allLabels) {
      single.setMinWidth(length * 16);
    }
    return resultedGrid;
  }

  /**
   * Matrix's TextFields Reader
   * 
   * @param  matrix1Data data from matrix1
   * @param  rowAndCol1  data from row and col of matrix1
   * @return             String[][] representation of the data within the Matrix
   */
  private String[][] reader(List<TextField> matrixData,
      List<TextField> rowAndCol) {

    int row = Integer.parseInt(rowAndCol.get(0).getText());
    int col = Integer.parseInt(rowAndCol.get(1).getText());

    String[][] stringMatrix = new String[row][col];
    int count = 0;
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        stringMatrix[i][j] = matrixData.get(count++).getText();
      }
    }

    return stringMatrix;
  }

  /**
   * Generate a Matrix
   * 
   * @param  textFields   Recorder of TextField
   * @param  rowAndColumn Recorder of TextField
   * @return              VBox of the Matrix
   */
  private VBox matrixGenerator(List<TextField> textFields,
      List<TextField> rowAndColumn) {

    // Create the Panel of the Matrix
    VBox vBoxMatrix = new VBox();

    // Row Label
    HBox rowMatrix = new HBox();
    Label labelRowMatrix = new Label("Row:      ");
    labelRowMatrix.setMinWidth(52);
    TextField inputRowMatrix = new TextField();
    inputRowMatrix.setMaxWidth(50);
    rowMatrix.getChildren().addAll(labelRowMatrix, inputRowMatrix);

    // Column Label
    HBox columnMatrix = new HBox();
    Label labelColumnMatrix = new Label("Column: ");
    labelColumnMatrix.setMaxWidth(52);
    TextField inputColumnMatrix = new TextField();
    inputColumnMatrix.setMaxWidth(50);
    columnMatrix.getChildren().addAll(labelColumnMatrix, inputColumnMatrix);

    rowAndColumn.add(inputRowMatrix);
    rowAndColumn.add(inputColumnMatrix);

    HBox dimension = new HBox();
    dimension.getChildren().addAll(rowMatrix, columnMatrix);

    // The GridPane for the Matrix
    GridPane gridMatrix = new GridPane();
    gridMatrix.setMaxWidth(400);
    inputRowMatrix.setText("3");
    inputColumnMatrix.setText("3");

    textFields.clear();

    // Constructing Input fields
    for (int i = 0; i < Integer.parseInt(inputRowMatrix.getText()); i++) {
      for (int j = 0; j < Integer.parseInt(inputColumnMatrix.getText()); j++) {

        TextField temp = new TextField();
        temp.textProperty().addListener(event -> {
          state = true;
          saved = false;
        });
        correctness = false;
        textFields.add(temp);
        gridMatrix.add(temp, j, i);
      }
    }

    // Set focusedTextField
    textFields.stream().forEach(t -> t.setOnMouseClicked(e -> {
      if (t.isFocused()) {
        caretPosition = t.getCaretPosition();
        focusedTextField = t;
      }
    }));

    // Add EventListener to inputRowMatrix
    inputRowMatrix.textProperty().addListener(event -> {
      try {

        // change the state
        state = true;
        saved = false;
        correctness = false;
        // Avoid to throw Exception when the TextField is empty
        if (inputRowMatrix.getText().equals("")) {
          return;
        }

        // For IllegalArgumentException
        if (Integer.parseInt(inputRowMatrix.getText()) <= 0
            || Integer.parseInt(inputRowMatrix.getText()) > 9) {
          throw new IllegalArgumentException();
        }

        gridMatrix.getChildren().clear();

        textFields.clear();

        // Constructing Input fields
        for (int i = 0; i < Integer.parseInt(inputRowMatrix.getText()); i++) {
          for (int j =
              0; j < Integer.parseInt(inputColumnMatrix.getText()); j++) {

            TextField temp = new TextField();
            temp.textProperty().addListener(e -> {
              state = true;
              saved = false;
            });
            textFields.add(temp);
            gridMatrix.add(temp, j, i);
          }
        }

        // Set focusedTextField
        textFields.stream().forEach(t -> t.setOnMouseClicked(e -> {
          if (t.isFocused()) {
            caretPosition = t.getCaretPosition();
            focusedTextField = t;
          }
        }));
      } catch (Exception e) {

        // Alert when detecting IllegalArgument
        alert("Error", "Number you entered is invalid" + lineSeparator
            + "Please reenter an positive integer");
        inputRowMatrix.setText("2");
      }
    });

    // Add EventListener to inputColumnMatrix
    inputColumnMatrix.textProperty().addListener(event -> {
      try {

        // change the state
        state = true;
        saved = false;
        correctness = false;
        // Avoid to throw Exception when the TextField is empty
        if (inputColumnMatrix.getText().equals("")) {
          return;
        }

        // For IllegalArgumentException
        if (Integer.parseInt(inputColumnMatrix.getText()) <= 0
            || Integer.parseInt(inputColumnMatrix.getText()) > 9) {
          throw new IllegalArgumentException();
        }

        gridMatrix.getChildren().clear();
        textFields.clear();

        // Constructing Input fields
        for (int i = 0; i < Integer.parseInt(inputRowMatrix.getText()); i++) {
          for (int j =
              0; j < Integer.parseInt(inputColumnMatrix.getText()); j++) {
            TextField temp = new TextField();
            temp.textProperty().addListener(e -> {
              state = true;
              saved = false;
            });
            textFields.add(temp);
            gridMatrix.add(temp, j, i);
          }
        }

        // Set focusedTextField
        textFields.stream().forEach(t -> t.setOnMouseClicked(e -> {
          if (t.isFocused()) {
            caretPosition = t.getCaretPosition();
            focusedTextField = t;
          }
        }));
      } catch (Exception e) {

        // Alert when detecting IllegalArgument
        alert("Error", "Number you entered is invalid" + lineSeparator
            + "Please reenter an positive integer");
        inputColumnMatrix.setText("2");
      }
    });

    // Add event handler to the TextField
    inputRowMatrix.setOnKeyReleased(event -> {
      try {

        // change the state
        state = true;
        saved = false;
        correctness = false;
        // Avoid to throw Exception when the TextField is empty
        if (inputRowMatrix.getText().equals("")) {
          return;
        }

        // For IllegalArgumentException
        if (Integer.parseInt(inputRowMatrix.getText()) <= 0
            || Integer.parseInt(inputRowMatrix.getText()) > 9) {
          throw new IllegalArgumentException();
        }

        gridMatrix.getChildren().clear();

        textFields.clear();

        // Constructing Input fields
        for (int i = 0; i < Integer.parseInt(inputRowMatrix.getText()); i++) {
          for (int j =
              0; j < Integer.parseInt(inputColumnMatrix.getText()); j++) {

            TextField temp = new TextField();
            temp.textProperty().addListener(e -> {
              state = true;
              saved = false;
            });
            textFields.add(temp);
            gridMatrix.add(temp, j, i);
          }
        }

        // Set focusedTextField
        textFields.stream().forEach(t -> t.setOnMouseClicked(e -> {
          if (t.isFocused()) {
            caretPosition = t.getCaretPosition();
            focusedTextField = t;
          }
        }));
      } catch (Exception e) {

        // Alert when detecting IllegalArgument
        alert("Error", "Number you entered is invalid" + lineSeparator
            + "Please reenter an positive integer");
        inputRowMatrix.setText("2");
      }
    });

    // Add event handler to the TextField
    inputColumnMatrix.setOnKeyReleased(event -> {
      try {

        // change the state
        state = true;
        saved = false;
        correctness = false;
        // Avoid to throw Exception when the TextField is empty
        if (inputColumnMatrix.getText().equals("")) {
          return;
        }

        // For IllegalArgumentException
        if (Integer.parseInt(inputColumnMatrix.getText()) <= 0
            || Integer.parseInt(inputColumnMatrix.getText()) > 9) {
          throw new IllegalArgumentException();
        }

        gridMatrix.getChildren().clear();
        textFields.clear();

        // Constructing Input fields
        for (int i = 0; i < Integer.parseInt(inputRowMatrix.getText()); i++) {
          for (int j =
              0; j < Integer.parseInt(inputColumnMatrix.getText()); j++) {
            TextField temp = new TextField();
            temp.textProperty().addListener(e -> {
              state = true;
              saved = false;
            });
            textFields.add(temp);
            gridMatrix.add(temp, j, i);
          }
        }
      } catch (Exception e) {

        // Alert when detecting IllegalArgument
        alert("Error", "Number you entered is invalid" + lineSeparator
            + "Please reenter an positive integer");
        inputColumnMatrix.setText("2");
      }
    });

    // Add to the overall Panel
    vBoxMatrix.getChildren().addAll(dimension, gridMatrix);

    return vBoxMatrix;
  }

  /**
   * Show alert to remind user
   * 
   * @param title   title of the alert
   * @param content content of the alert
   */
  private void alert(String title, String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    alert.setContentText(content);
    alert.showAndWait();
  }

  /**
   * Main method for this class
   * 
   * @param args args from Program Arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}
