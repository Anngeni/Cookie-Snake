import java.awt.geom.Line2D;

public class SnakeLine extends Line2D.Double {


     boolean isMovingLaterally;
     boolean isDirectionEast; //ie is direction positive

     //change colour too


    public SnakeLine(boolean isMovingLaterally, boolean isDirectionEast, double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);

        this.isMovingLaterally = isMovingLaterally;
        this.isDirectionEast = isDirectionEast;
    }

    public double calculateLength() {


        double length = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return length;
    }

    

}