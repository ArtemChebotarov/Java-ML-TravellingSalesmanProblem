import java.awt.*;

public class MyPoint extends Point {

    static TSP tsp;

    boolean asStart = false;
    boolean visited = false;

    public MyPoint(int x,int y){
        super(x,y);
    }



    public double calculateDistance(MyPoint p){
        return Math.sqrt( (p.x-x)*(p.x-x) +  (p.y-y)*(p.y-y) );

    }

    public MyPoint findClosest(){
        boolean first = true;
        MyPoint res = null;
        double prevDist = 0.0;
        for(MyPoint z:tsp.points){
            if(!z.visited){
                if(first){
                    res = z;
                    prevDist = calculateDistance(z);
                    first = false;
                }
                else {
                    if (prevDist > calculateDistance(z)) {
                        res = z;
                        prevDist = calculateDistance(z);
                    }
                }
            }
        }
        tsp.distance += prevDist;
        return res;
    }

    @Override
    public String toString() {
        return "(" + x + ";" + y + ")";
    }
}
