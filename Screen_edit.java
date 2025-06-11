import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Screen_edit extends Application {

    private Stage primaryStage;
    private GameManager gameManager;
    private List<Button> optionButtons = new ArrayList<>();



    public static void main(String[] args) {
        launch(args); // تشغيل JavaFX
    }



    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showNameInputScreen(); // نبدأ بطلب اسم اللاعب
    }

    private void showNameInputScreen() {
        Label prompt = new Label("Enter your name:");
        TextField nameField = new TextField();
        Button startButton = new Button("Start Game");

        startButton.setOnAction(e -> {
            String name = nameField.getText().trim();

            if (!name.isEmpty()) {
                try {
                    gameManager = new GameManager(name);  // إنشاء كائن مدير اللعبة
                    showGameScene();                      // عرض أول مشهد
                } catch (Exception ex) {
                    ex.printStackTrace();                 // طباعة الخطأ في الكونسول
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter your name!");
                alert.showAndWait();
            }
        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(prompt, nameField, startButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setTitle("Adventure Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showGameScene() {

        if (gameManager.isGameOver()) {
            showGameOver();
            return;
        }

        GameScene current = gameManager.getCurrentScene();

        // تحميل صورة المشهد كخلفية
        Image image = new Image(getClass().getResource("/" + current.getImagePath()).toExternalForm(), false);
        ImageView background = new ImageView(image);
        background.setFitWidth(600);
        background.setFitHeight(400);
        background.setPreserveRatio(false); // تغطية الشاشة بالكامل

        // العنوان والوصف
        Label titleLabel = new Label(current.getTitle());
        Label descriptionLabel = new Label(current.getDescription());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(550);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
        descriptionLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        // قائمة الأزرار
        optionButtons.clear();
        AtomicInteger selectedIndex = new AtomicInteger(0);

        VBox contentBox = new VBox(10);
        contentBox.setStyle("-fx-padding: 20; -fx-alignment: center;");
        contentBox.getChildren().addAll(titleLabel, descriptionLabel);

        String[] options = current.getOptions();
        int[] next = current.getNextSceneIndexes();

        for (int i = 0; i < options.length; i++) {
            Button optionButton = new Button(options[i]);
            int choice = i;

            optionButton.setOnAction(e -> {
                gameManager.goToNextScene(choice);
                showGameScene();
            });

            optionButtons.add(optionButton);
            contentBox.getChildren().add(optionButton);
        }

        if (!optionButtons.isEmpty()) {
            optionButtons.get(0).requestFocus();
        }

        // StackPane: الصورة في الخلف، والنصوص فوقها
        StackPane root = new StackPane();
        root.getChildren().addAll(background, contentBox);
        StackPane.setAlignment(contentBox, Pos.CENTER);

        // خلفية شفافة خفيفة للنصوص لتوضيحها فوق الصورة
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setMaxWidth(400); // عرض مريح في المنتصف
        contentBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 20;");


        // التحكم بالكيبورد
        Scene scene = new Scene(root, 600, 400);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> {
                    int index = selectedIndex.get();
                    if (index > 0) {
                        selectedIndex.set(index - 1);
                        optionButtons.get(index - 1).requestFocus();
                    }
                }
                case DOWN -> {
                    int index = selectedIndex.get();
                    if (index < optionButtons.size() - 1) {
                        selectedIndex.set(index + 1);
                        optionButtons.get(index + 1).requestFocus();
                    }
                }
                case ENTER -> {
                    int index = selectedIndex.get();
                    optionButtons.get(index).fire();
                }
                case DIGIT1 -> triggerOption(0);
                case DIGIT2 -> triggerOption(1);
                case DIGIT3 -> triggerOption(2);
                case DIGIT4 -> triggerOption(3);
            }
        });

        primaryStage.setScene(scene);
    }



    private void triggerOption(int index) {
        if (index >= 0 && index < optionButtons.size()) {
            optionButtons.get(index).fire(); // كأننا ضغطنا الزر بالماوس
        }
    }



    private void showGameOver() {
        GameScene current = gameManager.getCurrentScene();

        String endingTitle = switch (current.getTitle()) {
            case "Victory" -> "🎉 YOU WON! 🎉";
            case "Back to the Village" -> "🌿 You Returned Safely";
            case "Cursed Ending" -> "☠️ You Are Cursed!";
            default -> "=== GAME OVER ===";
        };

        Image image = new Image(getClass().getResource("/" + current.getImagePath()).toExternalForm(), false);
        ImageView background = new ImageView(image);
        background.setFitWidth(600);
        background.setFitHeight(400);
        background.setPreserveRatio(false);

        Label endLabel = new Label(endingTitle);
        endLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: white;");

        Label finalText = new Label(current.getDescription());
        finalText.setWrapText(true);
        finalText.setMaxWidth(500);
        finalText.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Button exitButton = new Button("Exit Game");
        exitButton.setOnAction(e -> primaryStage.close());


        // زر إعادة اللعب (يرجع لمشهد 0 مباشرة)
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setOnAction(e -> {

        // نرجّع currentSceneIndex إلى 0
            gameManager.resetGame();
            showGameScene(); // نعرض أول مشهد من جديد
        });

        // ترتيب الأزرار
        HBox buttonsBox = new HBox(20);
        buttonsBox.getChildren().addAll(playAgainButton, exitButton);
        buttonsBox.setAlignment(Pos.CENTER);

        VBox contentBox = new VBox(20);
        contentBox.getChildren().addAll(endLabel, finalText, buttonsBox);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setMaxWidth(400);
        contentBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 20;");

        StackPane root = new StackPane();
        root.getChildren().addAll(background, contentBox);
        StackPane.setAlignment(contentBox, Pos.CENTER);


        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
    }


}
