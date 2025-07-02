import javax.swing.*;
import java.awt.*;

public class AdventureGame {
    private JFrame frame; // screen
    private JLabel imageLabel; // background
    private JLabel titleLabel;
    private JLabel descriptionLabel;
    private JButton[] optionButtons;
    private GameManager gameManager; // to connect the GUI with the manager and the scenes
    private int selectedIndex = 0; // for the buttons , button to the right = 0 , button to the left = 1

    public AdventureGame() {
        frame = new JFrame("Adventure Game"); // the frame name
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // so if the player press on the X button the game will end without this line maybe the game will continue even though the player press on X button
        frame.setLocationRelativeTo(null); // center the window (null) not in specific area

        imageLabel = new JLabel(); // to put something new as a background
        imageLabel.setLayout(new GridBagLayout()); // center content, (setLayout) = to arrange things in imageLabel in specific way
        // (new GridBagLayout()) = system to keep the items in the center of the background
        frame.setContentPane(imageLabel); // so we can use (imageLabel) not just to fet me image put to make the image container وعاء

        JPanel centerPanel = new JPanel(); // the container to put items in it
        centerPanel.setOpaque(false); // to make the panel اللوحة transparent
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // (new BoxLayout) to arrange things in one line (Y) from top to bottom
        imageLabel.add(centerPanel); // to add the panel to the background

        // Title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 0, 0, 150)); // transparent black
        titlePanel.setMaximumSize(new Dimension(600, 50)); // so the title don't get so much big
        titleLabel = new JLabel("", SwingConstants.CENTER); // the title text we here made it null as a beginning
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26)); // how the title is going to look
        titleLabel.setForeground(Color.WHITE); // and what color the title is going to have
        titlePanel.add(titleLabel); // we put the (titleLabel) on the (titlePanel)
        centerPanel.add(titlePanel); // and the (titlePanel) on the (centerPanel)
        centerPanel.add(Box.createVerticalStrut(10)); // to make a vertical space مسافة عموديه between the title and the description

        // Description
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setBackground(new Color(0, 0, 0, 150));
        descriptionPanel.setMaximumSize(new Dimension(700, 60));
        descriptionLabel = new JLabel("", SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        descriptionLabel.setForeground(Color.WHITE);
        descriptionPanel.add(descriptionLabel);
        centerPanel.add(descriptionPanel);
        centerPanel.add(Box.createVerticalStrut(20));

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // the background to the button panel is transparent black
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // (new FlowLayout) to arrange things in one line (X)
        // (FlowLayout.CENTER) to make the buttons panel in the center
        optionButtons = new JButton[2]; // because we have in this game always two options no more than two or less

        for (int i = 0; i < 2; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 16)); // (Font.PLAIN) = normal font not bold
            optionButtons[i].setPreferredSize(new Dimension(180, 40)); // to make all the buttons the same size
            optionButtons[i].setBackground(Color.WHITE);
            int index = i;
            optionButtons[i].addActionListener(e -> handleChoice(index)); // add a job to the button while pressing
            // (e -> handleChoice(index)) "lambda expression" e → This is the event itself (meaning the button is pressed)
            //-> → This means "do what comes after it"
            // handleChoice(index) → This is the function we want to do when the button is pressed.
            buttonPanel.add(optionButtons[i]); // we put the (optionButtons[i]) on the (buttonPanel)
        }

        centerPanel.add(buttonPanel);  // and the (buttonPanel) on the (centerPanel)

        // Ask for name
        String name = JOptionPane.showInputDialog("Enter your name:"); // to open a small window to ask the player to write his name
        if (name == null || name.trim().isEmpty()) { // (name == null) the player press cancel
            // (name.trim().isEmpty()) the player press ok but didn't put anything in there
            name = "Player"; }
        gameManager = new GameManager(name); // to start the game

        showScene(); // we called this function to show the first scene
        frame.setVisible(true); // so we can see the game screen to play the game
    }

    private void showScene() {
        GameScene scene = gameManager.getCurrentScene();
        ImageIcon icon = new ImageIcon(getClass().getResource("/" + scene.getImagePath())); // to save the image to the current scene in the variable icon
        Image img = icon.getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH); // (getScaledInstance) = make the image suitable with the imagePanel
        // (Image.SCALE_SMOOTH) = to make the photo looks good
        imageLabel.setIcon(new ImageIcon(img)); // the scene image appears in the background

        titleLabel.setText(scene.getTitle()); // to print title text
        descriptionLabel.setText("<html><div style='text-align: center;'>" + scene.getDescription() + "</div></html>");
        // to get the description in center , to print description text

        String[] options = scene.getOptions();
        selectedIndex = 0; // for the buttons , button to the right = 0 , button to the left = 1

        for (int i = 0; i < options.length; i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setVisible(true);
            optionButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }

        if (options.length == 0) {
            int again = JOptionPane.showConfirmDialog(frame,
                    scene.getDescription() + "\nGame Over!\nPlay again?",
                    "Game Over", JOptionPane.YES_NO_OPTION);
            if (again == JOptionPane.YES_OPTION) {
                gameManager.resetGame();
                showScene();
            } else {
                frame.dispose(); // close the game window
            }
        }
    }

    private void handleChoice(int index) { // when the player press a button this function will take it to the new scene and show it
        gameManager.goToNextScene(index);
        showScene();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdventureGame::new); // it's an abbreviation for
        // SwingUtilities.invokeLater(new Runnable() {
        // public void run() {
        //    new SwingTest();
        //}
        //});
    }
}