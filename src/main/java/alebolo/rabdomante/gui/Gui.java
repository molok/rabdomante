package alebolo.rabdomante.gui;

import alebolo.rabdomante.Msg;
import alebolo.rabdomante.cli.RabdoException;
import alebolo.rabdomante.core.App;
import alebolo.rabdomante.core.Defect;
import alebolo.rabdomante.core.VersionProvider;
import com.google.common.base.Charsets;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static alebolo.rabdomante.xlsx.Utils.COLOR_USER_INPUT;

public class Gui extends Application {
    public static final String WEBSITE = "https://github.com/molok/rabdo-cli/";
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final App app = new App();
    private TextField selectedFileTxt;
    private Button run;
    private ProgressIndicator progressIndicator;
    private ButtonType openBtn = new ButtonType("Open Spreadsheet", ButtonBar.ButtonData.OK_DONE);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Rabdomante");

        VBox all = new VBox();
        all.getChildren().add(menuBar(primaryStage));
        all.getChildren().add(mainGrid(primaryStage));

        Scene scene = new Scene(all, 500, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar menuBar(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(a -> System.exit(0));
        menuFile.getItems().add(exit);

        Menu menuHelp = new Menu("Help");
        MenuItem about = new MenuItem("About");
        about.setOnAction(a -> displayAboutWindow(primaryStage));
        menuHelp.getItems().add(about);


        menuBar.getMenus().addAll(menuFile, menuHelp);
        return menuBar;
    }

    private void displayAboutWindow(Stage parentStage) {
        VBox grid = new VBox();
        grid.setPadding(new Insets(25));
        grid.setSpacing(10);

        Label welcome = new Label("Rabdomante " + new VersionProvider().fetchVersion());
        welcome.setMaxWidth(Double.MAX_VALUE);
        welcome.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        welcome.setAlignment(Pos.CENTER);

        grid.getChildren().add(welcome);

        Label copyright = new Label("Copyright \u00a9 2018 Alessio Bolognino");
        copyright.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        copyright.setMaxWidth(Double.MAX_VALUE);
        copyright.setAlignment(Pos.CENTER);
        grid.getChildren().add(copyright);

        Hyperlink link = new Hyperlink(WEBSITE);
        link.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        link.setOnAction(__ -> HostServicesDelegate.getInstance(this).showDocument(WEBSITE));
        link.setMaxWidth(Double.MAX_VALUE);
        link.setBorder(Border.EMPTY);
        link.setAlignment(Pos.CENTER);
        grid.getChildren().add(link);

        Label license = new Label("License AGPL3");
        license.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        license.setMaxWidth(Double.MAX_VALUE);
        license.setAlignment(Pos.CENTER);
        grid.getChildren().add(license);

        try {
            TextArea fullLicense = new TextArea(IOUtils.toString(this.getClass().getResourceAsStream("/AGPL3.txt"), Charsets.UTF_8));
            fullLicense.setFont(Font.font("Monospaced", FontWeight.NORMAL, 10));
            fullLicense.setWrapText(true);
            grid.getChildren().add(fullLicense);

        } catch (IOException e) {
            throw new Defect("License not found", e);
        }

        Scene aboutScene = new Scene(grid, 500, 300);
        Stage aboutWindow = new Stage();
        aboutWindow.setTitle("About Rabdomante");
        aboutWindow.initModality(Modality.WINDOW_MODAL);
        aboutWindow.initOwner(parentStage);
        aboutWindow.setScene(aboutScene);
        aboutWindow.show();
    }

    private GridPane mainGrid(Stage primaryStage) {
        GridPane mainGrid = new GridPane();
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(100);
        mainGrid.getColumnConstraints().add(c1);
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);
        mainGrid.setPadding(new Insets(15, 25, 25, 25));
//        mainGrid.setGridLinesVisible(true);

        int mainGridRow = 0;

        Label welcome = new Label("Welcome to Rabdomante");
        welcome.setAlignment(Pos.CENTER);
        welcome.setMaxWidth(Double.MAX_VALUE);
        welcome.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        mainGrid.add(welcome, 0, mainGridRow++, 2, 1);

        GridPane grid1 = new GridPane();
        Label desc1 = new Label("0. Get the template");
        desc1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        Button createTemplate = new Button("Save it somewhere...");
        createTemplate.setOnAction(event -> createTemplate(primaryStage));
        grid1.add(desc1, 0, 0);
        grid1.setHgap(10);
        grid1.setVgap(10);
        grid1.add(createTemplate, 0, 1);
        mainGrid.add(grid1, 0, mainGridRow++);

        GridPane grid2 = new GridPane();
        Label desc2 = new Label("1. Open it and edit the file filling the cells in light blue");
        desc2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid2.setHgap(10);
        grid2.setVgap(10);
        grid2.add(desc2, 0, 0);

        Rectangle blue = new Rectangle(50, 20);
        blue.setFill(Color.rgb(COLOR_USER_INPUT.getRed(), COLOR_USER_INPUT.getGreen(), COLOR_USER_INPUT.getBlue()));
        blue.setStroke(Color.BLACK);
        grid2.add(blue, 1, 0);

        mainGrid.add(grid2, 0, mainGridRow++);

        GridPane grid3 = new GridPane();
        Label desc3 = new Label("2. Save and close the file");
        desc3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid3.setHgap(10);
        grid3.setVgap(10);
        grid3.add(desc3, 0, 0);
        mainGrid.add(grid3, 0, mainGridRow++);

        GridPane grid4 = new GridPane();
        grid4.setHgap(10);
        grid4.setVgap(10);

        Label desc4 = new Label("3. If you moved or renamed it, select the file (or drag it here)");
        desc4.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        selectedFileTxt = new TextField();
//        selectedFileTxt.setEditable(false);
        selectedFileTxt.setPrefWidth(240.0);
        Button selectFile = new Button("Choose file...");
        selectFile.setOnAction(e -> selectFile(primaryStage));
        grid4.add(desc4, 0, 0, 2, 1);
        grid4.add(selectedFileTxt, 0, 1);
        grid4.add(selectFile, 1, 1);

        mainGrid.add(grid4, 0, mainGridRow++);

        GridPane grid5 = new GridPane();
//        grid5.setGridLinesVisible(true);
        ColumnConstraints c = new ColumnConstraints();
        c.setPercentWidth(100);
        grid5.getColumnConstraints().add(c);
        Label desc5 = new Label("4. Let Rabdomante find the best combination!");
        desc5.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid5.setHgap(10);
        grid5.setVgap(10);
        grid5.add(desc5, 0, 0);

        BorderPane runBox = new BorderPane();

        HBox leftRunBox = new HBox();
        leftRunBox.setFillHeight(true);
        leftRunBox.setAlignment(Pos.CENTER_LEFT);
        leftRunBox.setSpacing(10);
        Label labelTimeout = new Label("Time Limit (minutes)");
        Spinner timeout = new Spinner(1, 120, 1);
        leftRunBox.getChildren().add(labelTimeout);
        leftRunBox.getChildren().add(timeout);
        timeout.setPrefWidth(60);

        run = new Button("Run!");
        run.setOnAction(e -> calc(run));
        run.setPrefWidth(80);
        run.setAlignment(Pos.CENTER);
        run.setDisable(true);

        selectedFileTxt.textProperty().addListener((__, ___, newValue) -> run.setDisable(!isValidPath(newValue)));

        HBox rightRunBox = new HBox();
        rightRunBox.setFillHeight(true);
        rightRunBox.setAlignment(Pos.CENTER_RIGHT);
        rightRunBox.setSpacing(10);

        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        rightRunBox.getChildren().add(progressIndicator);
        rightRunBox.getChildren().add(run);

        runBox.setLeft(leftRunBox);
        runBox.setRight(rightRunBox);

        grid5.add(runBox, 0, 1);

        mainGrid.add(grid5, 0, mainGridRow++);
        return mainGrid;
    }

    private boolean isValidPath(String candidate) {
        try {
            File f = new File(candidate);
            return f.exists();
        } catch (Exception e) {
            return false;
        }
    }

    private void calc(Button run) {
        File ioFile = new File(Objects.requireNonNull(selectedFileTxt.getText()));
        run.setDisable(true);
        progressIndicator.setProgress(-1.0f);
        progressIndicator.setVisible(true);

        CompletableFuture
                .supplyAsync(() -> doCalc(ioFile))
                .handle((res, err) -> handle(res, err, endOfCalc(run)));
    }

    private Runnable endOfCalc(Button run) {
        return () -> {
            run.setDisable(false);
            progressIndicator.setProgress(1.0f);
        };
    }

    private App.Result doCalc(File ioFile) {
        log.info("start");
        try {
            return app.calc(ioFile, ioFile, 9999L);
        } finally {
            log.info("end");
        }
    }

    private Void handle(App.Result res, Throwable err, Runnable run) {
        Platform.runLater( () -> {
            try {
                run.run();
                boolean shouldOpen;
                if (err != null) {
                    shouldOpen = alertError(err);
                } else {
                    switch (res.res) {
                        case NONE:
                            shouldOpen = alertNoSolution(res.elapsedMs / 1000.);
                            break;
                        case INCOMPLETE:
                            shouldOpen = alertIncomplete(res.elapsedMs / 1000.);
                            break;
                        case OPTIMAL:
                            shouldOpen = alertSuccess(res.elapsedMs / 1000.);
                            break;
                        default:
                            throw new Defect("this shouldn't happen");
                    }
                }

                if (shouldOpen) {
                    Desktop.getDesktop().open(new File(selectedFileTxt.getText()));
                }
            } catch (Throwable t) {
                log.error("", t);
            }
        });
        return null;
    }

    private boolean alertError(Throwable err) {
        return alert(Alert.AlertType.ERROR, "Unexpected error:"+ExceptionUtils.getStackTrace(err), "Error");
    }

    private boolean alertSuccess(double seconds) {
        return alert(
                Alert.AlertType.INFORMATION,
                String.format("Solution found in %.01f seconds! Check it in the %s sheet", seconds, Msg.recipe()),
                "Success");
    }

    private boolean alert(Alert.AlertType type, String msg, String title) {
        Alert alert = new Alert(
                type,
                msg,
                openBtn );
        alert.setTitle(title);
        alert.setHeaderText(null);
        Optional<ButtonType> res = alert.showAndWait();
        return (res.isPresent() && res.get().equals(openBtn));
    }

    private boolean alertIncomplete(double seconds) {
        return alert(
                Alert.AlertType.WARNING,
                String.format("A solution was found after %.01f seconds! Check it in the %s sheet. " +
                        "This may not be the optimal solution, the search was interrupted after timeout was reached. " +
                        "Try to reduce the number of salts and/or waters", seconds, Msg.recipe()),
                "Warning");
    }

    private boolean alertNoSolution(double seconds) {
        return alert(
                Alert.AlertType.ERROR,
                String.format("No solution could be found after %.01f seconds, sorry :(", seconds),
                "Warning");
    }

    private void selectFile(Stage primaryStage) {
        File value = fileChooser().showOpenDialog(primaryStage);
        if (value != null) {
            selectedFileTxt.setText(value.getAbsolutePath());
        }
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
                new FileChooser.ExtensionFilter("Excel 2007+ (*.xlsx)", "*.xlsx"));
        return fc;
    }

    private void saveFile(File input) {
        try {
            app.generate(input, true);
            selectedFileTxt.setText(input.getAbsolutePath());
            Desktop.getDesktop().open(input);
        } catch (IOException e) {
            throw new RabdoException(e);
        }
    }
}
