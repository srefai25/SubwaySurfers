import java.awt.*;

public class Point {
    public int xpos;
    public int ypos;
    public Rectangle rec;

    public Point(int pXpos, int pYpos){
        xpos = pXpos;
        ypos = pYpos;
        rec = new Rectangle(xpos, ypos, 20, 20);
    }
}
