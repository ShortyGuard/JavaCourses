package lesson8.gameengine;

import lombok.Getter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Это компонент кнопки ячейки поля
 *
 * Добавлены координаты ячейки на поле, статические иконки, и свойсво кем занята ячейка
 */
public class FieldCell extends JButton {
    @Getter
    private int xPosition;
    @Getter
    private int yPosition;

    private static ImageIcon emptyImageIcon;
    private static ImageIcon crossImageIcon;
    private static ImageIcon zeroImageIcon;


    @Getter
    private Players accupation = Players.EMPTY;

    public FieldCell(int xPosition, int yPosition){
        super();
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.setSize(50,50);
        this.setIcon(FieldCell.getEmptyImageIcon());
    }

    public void setAccupation(Players accupation) {
        this.accupation = accupation;
        switch (this.accupation) {
            case HUMAN: {
                this.setIcon(FieldCell.getCrossImageIcon());
                break;
            }
            case AI: {
                this.setIcon(FieldCell.getZeroImageIcon());
                break;
            }
            case EMPTY: {
                this.setIcon(FieldCell.getEmptyImageIcon());
                break;
            }
        }
    }


    public static ImageIcon getEmptyImageIcon() {
        if (emptyImageIcon == null)
        {
            emptyImageIcon  = getImage("../../images/empty.png");
        }
        return emptyImageIcon;
    }

    public static ImageIcon getCrossImageIcon() {
        if (crossImageIcon == null)
        {
            crossImageIcon = getImage("../../images/cross.png");
        }
        return crossImageIcon;
    }

    public static ImageIcon getZeroImageIcon() {
        if (zeroImageIcon == null)
        {
            zeroImageIcon = getImage("../../images/zero.png");
        }
        return zeroImageIcon;
    }

    private static ImageIcon getImage(String s) {
        try {
            Image image = ImageIO.read(FieldCell.class.getResourceAsStream(s));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
