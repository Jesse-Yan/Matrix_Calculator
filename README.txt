#README
(This is a txt version of README, for more details, please see README.md)

#Course: cs400
Semester: Spring 2020
Project name: Matrix calculator

#Team Members:
Chengpo Yan, LEC001, cyan46@wisc.edu
Jinming Zhang, LEC001, jzhang2279@wisc.edu
Archer Li LEC001, zli885@wisc.edu
Houming Chen, LEC001, hchen634@wisc.edu
Chengxu Bian, LEC001, cbian7@wisc.edu

#Which team members were on same xteam together?
*no one.

#Other notes or comments to the grader:
Program can simply running by using command line to run the executable.jar file. Running in Java code required JavaFx and JDK11 pre-installed.

#Running demo
PLEASE USING FOLLOWING COMMAND FOR RUNNING .JAR FILE
REMEMBER TO CHANGE "Path-to-javafx-lib" TO YOUR OWN PATH
java --module-path "Path-to-javafx-lib" --add-modules javafx.controls,javafx.fxml -jar executable.jar

#Installation
Program can simply running by using command line to run the executable.jar file.
Running in Java code required JavaFx and JDK11 pre-installed.

#Program Description
This project “Matrix calculator” by CS400 Ateam 2 aims to help students studying linear algebra to understand the calculations linear algebra better. This “Matrix calculator” can not only do many matrix calculations like matrix multiplication, finding eigenvalues, and do LUP, QR, or Cholesky decompositions, but it can also support basic algebra calculations like a normal calculator and analyzing sequence. 
This “Matrix calculator” consists of two parts, a math calculator on the left side, which supports basic algebra calculations and analyzing sequence, and a matrix calculator on the right side, which supports calculations of matrices.
For matrix calculations, this “Matrix calculator” also supports file inputs and outputs. The input files should be json files in a specific format, and the output files will also be json files.
“Matrix calculator” is also friendly to the computer user who does not have a keyboard. Users can input their data by clicking buttons provided on the user interface. 
This is a extremely complicated project, and we all performed our virtuosities in this project. We hope you will enjoy it!!! [\^_\^]

#Instuctions to User
1. To perform a normal calculation, simply click on the white input field of the calculator to activate the function if some buttons are disabled, when you finish inputting, you can click on the '=' button to see the result.
2. To perform analyze sequence function, click on button of 'analyze sequence' and you should see that the space button and the '=' button have been activated. Now, you can click the nubmer buttons to input numbers and use the space button to separate them. After finishing inputting, you can click on '=' to see the output
3. To perform matrix calculation, you need to choose between whether you need to do operations on a single matrix or two matrixes. To perform calculation on a single matrix, simply click on the row & col input field and type in an integer to tell the calculator the dimension of the matrix, then you can type the value(Integer or Double) to the specific entry. After that, you can click on the buttons below the matrix to see the result. If you are using the power button, you need to type in the value first. To perform calculation on two matrixes, you need to click on the CheckBox '?' to active the second matrix, at the same time, the operation buttons below will be disabled and the operation buttons between the two matrixes will be activated. After finishing inputting, click on '+', '-' or '*' to see the result. 'c1', 'c2' is used to clear the matrix
4. Our Matrix calculator supports file input/output. To open a file, click on Menu and select open to choose your file. Your file must be ended with .json otherwise we will not accept it. In addition, the content of your file input must be correct to be accepted by our calculator. If your input file contains too many calculations, it may take a while for the calculator to show the operations you have plugged in. After you see your operations, you can click on the buttons on the top of the application since they have been activated. You can view back and forth, you can even type in the input field and click on the confirm button to lead you to the operation you want. You can edit your operation, and new changes will be recorded if you do some calculations on those matrixes and they are correct, otherwise we will not record it. Our calculator also supports add new operation and delete operation. You can add a new operation by clicking on 'Add' button. After producing correct output, you can record it by clicking on 'Add Confirm' button. You can delete an operation by clicking on 'Delete' button. Remember: You cannot delete the last operation.
5. In 'reading file mode', we have filtering function, it is located in the left of the top button bars. It is a dynamic ComboBox and its content will be adjusted based on your input and your manipulations. During the filtering mode, you can switch between operations using the back and forth button. You cannot add and remove operations and use the page input TextField. In addition, you cannot save the file or quit the 'reading file mode'. You can return back to normal state by selecting 'All' in the ComboBox.
6. If you finished editing your operations, you can quit the 'reading file mode' by clicking on 'quit' button, and it will ask you whether you want to save the file. 'Yes' will open a fileChooser, 'No' will quit the 'reading file mode', 'Cancel' will return back. Note: If you have save your file through the 'save' in Menu, it will quit directly if during this period you do not make any change.
7. Out calculator is friendly to the computer user who does not have a keyboard. Out input focus can be switching between TextFields to give you the best user experience(Not for the power TextField).
!Mark! To open .json file, click Menu then click Open.
       To save result of opened .json file, please click Menu-Save and enter the file name

Please see SimpleData.json for the sample format. (other json file is design for testing purpose, each is built base on its name. Result.json is sample result output.) 
You can click '<' '>' or '√' to change from different operations that been read from the correct .json files.
Warning, If code in ateam.zip won't run, please check CS400_final_project.zip (for original directory path)

# Be sure to check README.md for more detailed describtion for the program!

.Json Format Rule
calculations(Json Array)
->operations(String element)
->datas(Stores Matrix)
->->Matrix[es](Matrix in form of 2d Json string array, each element should maped exactly as the order in matrix)

remember Only "+","*","-" will accept 2 matrix element, else operations will only accept one.

# Known Issues
1. Cannot always find all the real eigenvalues of a Matrix.
Theoretically, a QR algorithm with Wilkinon-Shift will always converge for hessenburg matrices. See https://www.ams.org/journals/mcom/2002-71-240/S0025-5718-01-01387-4/S0025-5718-01-01387-4.pdf. However, although a QR algorithm with Wilkinon-Shift is implemented in this program, it seems sometimes, though very rare, the QR algorithm still doesn't converge. To deal with this, we tried to do some QR iterration with normal shift if the QR algorithm with Wilkinon-Shift doesn't converge in 1000 iterations, but still it cannot make sure that the algorithm always converge. If the QR algorithm doesn't converge in 4000 iterations, the QR algorithm will be stopped. This lead to the fact that our algorithm cannot always find all the real eigenvalues of a Matrix. This bug might be fixed in the future by more studies on the QR algorithm and its implementation in the program.

2. Floating-point error might cause inaccuracies
In this program, since many calculations are operations with double, due to the floating-point error, the result might not be accurate. Sometimes, the floating-point error might be enlarged significantly by the Butterfly Effect in the complicated calculation processes. To deal with this, in order to decrease the impact of floating-point error, we now require the input matrix to be no larger than 9*9, and the input numbers must be in the range [-100000, 100000] with no more than 3 decimal digits. This issue might be fixed in the future by changing some double type to java.math.BigDecimal to decrease the floating-point error.

# Other Future Work
1. Matrix calculator allowing Users to choose Fraction output or Decimal output.
Sometimes user might need decimal outputs. Therefore, it would be better if we could allow the user to choose the output format. This function is already realized in the back-end, see MatrixCalculator.java, but we haven't used it in the program. We might add this feature in the future.

2. Matrix calculator allowing Users to input in Fraction
Sometimes user might want to input their data in fraction format. Therefore, it would be better if we could allow the user to input their data in fraction format. We might add this feature in the future.

／ イ  　　　(((ヽ        +++++++++++++++
(　 ﾉ　　　　  ￣Ｙ ＼   （Future updates）
|　(＼　(\   /)  ｜  ) / +++++++++++++++++
ヽ　ヽ` ( ﾟ∀ﾟ ) _ノ /
　＼ |　⌒Ｙ⌒　/  /
　  ｜ヽ　 ｜　 ﾉ ／
　   ＼トー仝ーイ
　　  ｜ ミ土彡/
      ) \  °   /  
     (   \  /  \
   / /  /      \ \ \
   ( (  ).       )  ).)
   (    ).       ( |  |
    |  /           \  |
  nn.  ).           (  nnm
  
  @arthor - Archer
