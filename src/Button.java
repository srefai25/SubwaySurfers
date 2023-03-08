import java.awt.*;

public class Button {

        //VARIABLE DECLARATION SECTION
        //Here's where you state which variables you are going to use.
        public int xpos;				//the x position
        public int ypos;				//the y position

        public int width;
        public int height;
        public Rectangle rec;			//declare a rectangle variable

        // METHOD DEFINITION SECTION

        // Constructor Definition
        // A constructor builds the object when called and sets variable values.
        public Button(int xParameter, int yParameter, int widthParameter, int heightParameter)
        {
            xpos = xParameter;
            ypos = yParameter;
            width = widthParameter;
            height = heightParameter;
            rec= new Rectangle (xpos,ypos,width,height);	//construct a rectangle
        } // constructor


    }
