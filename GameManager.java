public class GameManager {
    private GameScene[] scenes;          // كل مشاهد اللعبة
    private int currentSceneIndex;       // المشهد الحالي
    private String playerName;           // اسم اللاعب

    public GameManager(String playerName) {
        this.playerName = playerName;
        setupScenes();                   // بناء المشاهد
        currentSceneIndex = 0;           // نبدأ من أول مشهد
    }

    // ترجع المشهد الحالي
    public GameScene getCurrentScene() {
        return scenes[currentSceneIndex];
    }

    // تتأكد إذا خلصت اللعبة (ما فيه خيارات)
    public boolean isGameOver() {
        return getCurrentScene().getOptions().length == 0;
    }

    // للمشهد تنقل التالي حسب اختيار اللاعب
    public void goToNextScene(int choiceIndex) {
        int[] next = getCurrentScene().getNextSceneIndexes();
        if (choiceIndex >= 0 && choiceIndex < next.length) {
            currentSceneIndex = next[choiceIndex];
        }
    }

    public void resetGame() {
        currentSceneIndex = 0;
    }



    // إعداد كل مشاهد اللعبة
    private void setupScenes() {
        scenes = new GameScene[12];

        scenes[0] = new GameScene(
                "Forest Entrance",
                playerName + ", you are standing at the edge of a mysterious forest.",
                new String[]{"Enter the forest", "Go back to the village"},
                new int[]{1, 2} ,
                "images/1.jpg"
        );

        scenes[1] = new GameScene(
                "Inside the Forest",
                "The trees whisper your name... \"" + playerName + "\"...",
                new String[]{"Follow the voice", "Climb a tree"},
                new int[]{3, 4} ,
                "images/2.jpg"
        );

        scenes[2] = new GameScene(
                "Back to the Village",
                playerName + ", you return to the village, wondering what could have been.",
                new String[]{},
                new int[]{} ,
                "images/3.jpg"
        );

        scenes[3] = new GameScene(
                "Mysterious Voice",
                playerName + ", a lost child appears and asks for your help.",
                new String[]{"Help the child", "Ignore him"},
                new int[]{5, 6} ,
                "images/4.jpg"
        );

        scenes[4] = new GameScene(
                "The Hidden Camp",
                "You, " + playerName + ", find an abandoned camp with old maps and tools.",
                new String[]{"Search the camp", "Leave immediately"},
                new int[]{5, 2}, // بدل 5 و 1 → خليه يروح للنهاية المنطقية
                "images/5.jpg"
        );

        scenes[5] = new GameScene(
                "Talking Tree",
                "A magical tree speaks to you, " + playerName + ", offering you guidance.",
                new String[]{"Listen to the tree", "Cut a branch"},
                new int[]{6, 7} ,
                "images/6.jpg"
        );

        scenes[6] = new GameScene(
                "Underground Tunnel",
                playerName + ", you discover a hidden tunnel beneath the forest floor.",
                new String[]{"Enter the tunnel", "Stay above ground"},
                new int[]{7, 8} ,
                "images/7.jpg"
        );

        scenes[7] = new GameScene(
                "The Guardian Beast",
                "A giant beast blocks your way. It looks directly at you, " + playerName + ".",
                new String[]{"Talk to the beast", "Try to sneak past"},
                new int[]{8, 9}, // بدل ما يرجعك لمشهد 1، يوديك للنهاية
                "images/8.jpg"
        );

        scenes[8] = new GameScene(
                "The Ancient Shrine",
                playerName + ", you arrive at a glowing ancient shrine filled with mysterious energy.",
                new String[]{"Touch the shrine", "Walk away"},
                new int[]{9, 2} ,
                "images/9.jpg"
        );

        scenes[9] = new GameScene(
                "The Final Choice",
                "You reach a treasure chest. What will you do, " + playerName + "?",
                new String[]{"Open it", "Destroy it"},
                new int[]{10, 11}, // ممكن تعملي هنا نهايتين منفصلتين بدل نفس المشهد
                "images/10.jpg"
        );

        scenes[10] = new GameScene(
                "Victory",
                playerName + ", you opened the chest and found a powerful artifact! You feel peace and purpose.",
                new String[]{},
                new int[]{},
                "images/11.jpg"
        );

        scenes[11] = new GameScene(
                "Cursed Ending",
                "The moment you destroy the chest, a shadow curses the land forever. You feel cold and alone.",
                new String[]{},
                new int[]{},
                "images/12.jpg"
        );
    }

}

