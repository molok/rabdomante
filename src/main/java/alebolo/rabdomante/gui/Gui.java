package alebolo.rabdomante.gui;

import alebolo.rabdomante.Msg;
import alebolo.rabdomante.cli.RabdoException;
import alebolo.rabdomante.core.App;
import alebolo.rabdomante.core.Defect;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static alebolo.rabdomante.xlsx.Utils.COLOR_USER_INPUT;

public class Gui extends Application {
    App app = new App();
    private Optional<File> selectedFile = Optional.empty();
    private TextField selectedFileTxt;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Rabdomante");
        GridPane mainGrid = new GridPane();
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);
        mainGrid.setPadding(new Insets(25, 25, 25, 25));

        GridPane grid1 = new GridPane();
        Text desc1 = new Text("0. Get the template");
        desc1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        Button createTemplate = new Button("Save it somewhere...");
        createTemplate.setOnAction(event -> createTemplate(primaryStage));
        grid1.add(desc1, 0, 0);
        grid1.setHgap(10);
        grid1.setVgap(10);
        grid1.add(createTemplate, 0, 1);
        mainGrid.add(grid1, 0, 0);

        GridPane grid2 = new GridPane();
        Text desc2 = new Text("1. Open it and edit the file filling the cells in light blue");
        desc2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid2.setHgap(10);
        grid2.setVgap(10);
        grid2.add(desc2, 0, 0);

        Rectangle blue = new Rectangle(50, 20);
        blue.setFill(Color.rgb(COLOR_USER_INPUT.getRed(), COLOR_USER_INPUT.getGreen(), COLOR_USER_INPUT.getBlue()));
        blue.setStroke(Color.BLACK);
        grid2.add(blue, 1, 0);

        mainGrid.add(grid2, 0, 1);

        GridPane grid3 = new GridPane();
        Text desc3 = new Text("2. Save and close the file");
        desc3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid3.setHgap(10);
        grid3.setVgap(10);
        grid3.add(desc3, 0, 0);
        mainGrid.add(grid3, 0, 2);

        GridPane grid4 = new GridPane();
        grid4.setHgap(10);
        grid4.setVgap(10);

        Text desc4 = new Text("3. If you moved or renamed it, select the file (or drag it here)");
        desc4.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        selectedFileTxt = new TextField(selectedFile.map(sf -> sf.getAbsolutePath()).orElse(""));
        selectedFileTxt.setDisable(true);
        selectedFileTxt.setPrefWidth(240.0);
        Button selectFile = new Button("Choose file...");
        selectFile.setOnAction(e -> selectFile(primaryStage));
        grid4.add(desc4, 0, 0, 2, 1);
        grid4.add(selectedFileTxt, 0, 1);
        grid4.add(selectFile, 1, 1);

        mainGrid.add(grid4, 0, 3);

        GridPane grid5 = new GridPane();
        Text desc5 = new Text("4. Let Rabdomante find the best combination!");
        desc5.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid5.setHgap(10);
        grid5.setVgap(10);
        grid5.add(desc5, 0, 0);

        Button run = new Button("Run!");
        run.setOnAction(e -> calc());
        run.setPrefWidth(80);
        run.setAlignment(Pos.CENTER);

        grid5.add(run, 0, 1);
        mainGrid.add(grid5, 0, 4);

        Scene scene = new Scene(mainGrid, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void calc() {
        if (selectedFile.isPresent()) {
            /* TODO don't run in the gui thread ffs */
            App.Result res = app.calc(selectedFile.get(), selectedFile.get(), 60L);
            switch (res.res) {
                case NONE: alertNoSolution(res.elapsedMs /1000.); break;
                case INCOMPLETE: alertIncomplete(res.elapsedMs /1000.); break;
                case OPTIMAL: alertSuccess(res.elapsedMs /1000.); break;
                default: throw new Defect("this shouldn't happen");
            }

        }
    }

    private void alertSuccess(double seconds) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(String.format("Solution found in %.03f seconds! Check it in the %s sheet", seconds, Msg.recipe()));
        alert.showAndWait();
    }
    private void alertIncomplete(double seconds) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(
                String.format("A solution was found after %.03f seconds! Check it in the %s sheet. " +
                        "This may not be the optimal solution, the search was interrupted after timeout was reached. " +
                        "Try to reduce the number of salts and/or waters", seconds, Msg.recipe()));
        alert.showAndWait();
    }

    private void alertNoSolution(double seconds) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(String.format("No solution could be found after %.03f seconds, sorry :(", seconds));
        alert.showAndWait();
    }

    private void selectFile(Stage primaryStage) {
        selectedFile = Optional.ofNullable(fileChooser().showOpenDialog(primaryStage));
    }

    private void createTemplate(Stage primaryStage) {
        FileChooser fc = fileChooser();
        fc.setInitialFileName("rabdomante.xlsx");
        Optional.ofNullable(fc.showSaveDialog(primaryStage))
                .ifPresent(f -> saveFile(f));
    }

    private FileChooser fileChooser() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel 2007+ (*.xlsx)", "xlsx"));
        return fc;
    }

    private void saveFile(File input) {
        try {
            app.generate(input, true);
            this.selectedFile = Optional.of(input);
            selectedFileTxt.setText(selectedFile.map(sf -> sf.getAbsolutePath()).orElse(""));
            Desktop dt = Desktop.getDesktop();
            dt.open(input);
        } catch (IOException e) {
            throw new RabdoException(e);
        }
    }
}
