public class GameScene {
    private String title ;
    private String description ;
    private String[] options ;
    private int[] nextSceneIndexes ;
    private String imagePath ;

    public GameScene(String title , String description , String[] options , int[] nextSceneIndexes , String imagePath) {
        this.title = title ;
        this.description = description ;
        this.options = options ;
        this.nextSceneIndexes = nextSceneIndexes ;
        this.imagePath = imagePath ;
    }


    public String getTitle() {
        return title ;
    }

    public String getDescription() {
        return description ;
    }

    public String[] getOptions() {
        return options ;
    }

    public int[] getNextSceneIndexes() {
        return nextSceneIndexes ;
    }

    public String getImagePath() {
        return imagePath ;
    }

}
