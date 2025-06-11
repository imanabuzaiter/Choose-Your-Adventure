import java.util.Scanner;

public class AdventureGame {
    private static Scanner scanner = new Scanner(System.in);
    private static GameManager gameManager;

    public static void main(String[] args) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        gameManager = new GameManager(name);

        while (!gameManager.isGameOver()) {
            GameScene scene = gameManager.getCurrentScene();

            System.out.println("=== " + scene.getTitle() + " ===");
            System.out.println(scene.getDescription());

            String[] options = scene.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }

            System.out.print("Your choice: ");
            int choice = scanner.nextInt() - 1;
            scanner.nextLine(); // clean input

            gameManager.goToNextScene(choice);
            System.out.println(); // empty line
        }

        System.out.println("=== GAME OVER ===");
        System.out.println(gameManager.getCurrentScene().getDescription());
    }
}
