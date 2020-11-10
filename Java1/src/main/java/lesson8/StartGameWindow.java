package lesson8;

import lesson8.gameengine.TicTacToe;

import javax.swing.*;
import java.awt.*;

/**
 * Окно запуска игры
 */
public class StartGameWindow extends JFrame {

    private final int DEFAULT_HEIGHT = 520;
    private final int DEFAULT_WIDTH = 640;

    //панель для игрового поля
    private JPanel gamePanel;

    private JSpinner fieldSize;
    private JSpinner winDotsSize;

    private TicTacToe ticTacToe;

    public StartGameWindow() {
        //создадим окно игры
        this.setTitle("Игра в крестики нолики");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int h = screenSize.height;
        int w = screenSize.width;
        this.setLocation(screenSize.width / 2 - DEFAULT_WIDTH / 2, screenSize.height / 2 - DEFAULT_HEIGHT / 2);

        //Создадим меню
        //Создадим кнопку начать игру
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.setLayout(new FlowLayout());
        JButton jButton = new JButton("Начать игру");

        jButton.addActionListener(e -> {
                startGame();
            }
        );

        //создадим спинер выбора размера поля
        SpinnerModel gameSizeModel = new SpinnerNumberModel(3, 3, 7, 1);
        this.fieldSize = new JSpinner(gameSizeModel);
        this.fieldSize.setSize(20, 20);
        this.fieldSize.addChangeListener(event -> {
            if ((Integer) this.winDotsSize.getValue() > (Integer) this.fieldSize.getValue()) {
                this.winDotsSize.setValue(this.winDotsSize.getPreviousValue());
            }
        });
        //создадим спинер выбора сложности игры (сколько подряд надо занять ячеек)
        SpinnerModel winDotsSizeModel = new SpinnerNumberModel(3, 3, 7, 1);
        this.winDotsSize = new JSpinner(winDotsSizeModel);
        this.winDotsSize.setSize(20, 20);
        this.winDotsSize.addChangeListener(event -> {
            if ((Integer) this.winDotsSize.getValue() > (Integer) this.fieldSize.getValue()) {
                this.winDotsSize.setValue(this.winDotsSize.getPreviousValue());
            }
        });
        jMenuBar.add(new JLabel("Размер поля: "));
        jMenuBar.add(this.fieldSize);
        jMenuBar.add(new JSeparator());
        jMenuBar.add(new JLabel("Количество символов чтобы выиграть: "));
        jMenuBar.add(this.winDotsSize);
        jMenuBar.add(new JSeparator());
        jMenuBar.add(jButton);
        this.getContentPane().add(BorderLayout.NORTH, jMenuBar);

        //создадим панель куда будем добавлять поле игры
        this.gamePanel = new JPanel();
        this.getContentPane().add(BorderLayout.CENTER, gamePanel);

        this.setVisible(true);
    }

    /**
     * стартуем новую игру с выствленными параметрами
     */
    private void startGame() {
        System.out.println("Start new game");
        //создадим движок игры
        this.ticTacToe = new TicTacToe((Integer) this.fieldSize.getValue(), (Integer) this.winDotsSize.getValue());
        this.ticTacToe.initGame();

        //инициализируем поле игры
        this.initGameField();
    }

    /**
     * инициализация поля игры (размещение поля в панель игры, при этом старое поле из панели удаляется)
     */
    private void initGameField() {
        this.gamePanel.removeAll();

        this.gamePanel.setLayout(new FlowLayout());
        this.gamePanel.add(this.ticTacToe.getGameFiled());
        System.out.println("Game field initialized");

        this.setVisible(true);
    }

    public static void main(String[] args) {
        StartGameWindow gameWindow =new StartGameWindow();

    }

}
