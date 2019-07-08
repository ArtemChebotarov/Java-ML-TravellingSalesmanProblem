import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class TSP{

    static final int MAX_POINTS = 400;

    GUI g;

    MyPoint start; //punkt startowy
    double distance = 0; //dystans wynikowy
    long time; //czas wynikowy

    List<MyPoint> points = new ArrayList<MyPoint>();   //lista odczytanych punktów

    List<MyPoint> way = new ArrayList<MyPoint>(); //lista punktow wynikowych



    public TSP(){
        g = new GUI(this);
        SwingUtilities.invokeLater(g);

    }


    public List<MyPoint> greedy(){ //algorytm zachlanny
        long startTime = System.nanoTime()/1000000;
        List<MyPoint> res = new ArrayList<MyPoint>(); //sciezka zwracana

        res.add(start);
        start.visited = true; //odznaczamy start jako odwiedzony

        MyPoint tmp = start.findClosest(); //szukamy najblizszego punktu od punktu startowego

        while (res.size() != points.size()){  //dopoki nie przejdziemy przez wszytkie punkty
            System.out.println("adding to way:  " + tmp);
            tmp.visited = true; //odznaczamy jako odwiedzony
            res.add(tmp);  //dodajemy do sciezki
            tmp = tmp.findClosest(); //szukamy najblizszego punktu
        }

        distance += start.calculateDistance(res.get(res.size()-1)); //dodawanie dystansu od startu do punktu koncowego

        System.out.println(res);

        long endTime = System.nanoTime()/1000000;
        time = endTime-startTime;


        return res;



    }

    public List<MyPoint> hillClimbing(){
        long startTime = System.nanoTime()/1000000;

        List<MyPoint> x0 = getRandomWay(); //zapisanie losowej sciezki



        do {

            List<List<MyPoint>> neighbours = getNeighbours(x0);  //pobieranie wszystkich "sasiadow" sciezki



            double minDistance = obliczDist(x0); //zmienna reprezentujaca dystans minimalny danej sciezki
            List<MyPoint> min = x0; //zmienna reprezentujaca sciezke o najmniejszym dystansie



            for (int i = 0; i < neighbours.size(); i++) {
                if (obliczDist(neighbours.get(i)) < minDistance || minDistance == 0) { //jezeli sasiad okazal sie lepszym
                    minDistance = obliczDist(neighbours.get(i));  //przypisujemy wartosci
                    min = neighbours.get(i);
                }
            }
            System.out.println(minDistance);
            if (min == x0) {  //jesli sciezka minimalna sie nie zmienila konczymy dzialanie algorytmu
                break;
            }

            x0 = min; //w innym przypadku przypisujemy sciezke minimalna do x0
        }while (true);

        distance = obliczDist(x0);


        long endTime = System.nanoTime()/1000000;
        time = endTime-startTime;

        System.out.println(x0);













        return  x0;
    }

    public List<MyPoint> getRandomWay(){ //metoda generujaca losowa sciezke
        List<MyPoint> res = new ArrayList<MyPoint>();
        List<MyPoint> points_copy = new ArrayList<MyPoint>();
        points_copy.addAll(points);
        res.add(start);
        points_copy.remove(start);

        while(points_copy.size() > 0) {

            int rand = (int) (Math.random() * points_copy.size() - 1);
            System.out.println(points_copy.get(rand));
            res.add(points_copy.get(rand));
            points_copy.remove(rand);
        }
        return res;
    }

    public List<List<MyPoint>> getNeighbours(List<MyPoint> s){ //metoda zwracajaca wszystkie sciezki podobne do podanej
        List<List<MyPoint>> res = new ArrayList<>();
        int n = 0;

        for(int i=1;i<s.size();i++){
            for(int j=i+1;j<s.size();j++){
                n++;
                List<MyPoint> tmp = swap(s,i,j);
                res.add(tmp);


            }
        }

        System.out.println("n = " + n);


        return res;


    }

    public List<MyPoint> swap(List<MyPoint> list,int a,int b){ //metoda wykonujaca przestawienie podanych elementow  na liscie punktow
        List<MyPoint> result = new ArrayList<>();
        for(int q=0;q<list.size();q++){
            if(q == a){
                result.add(list.get(b));
            }
            else{
                if(q==b){
                    result.add(list.get(a));
                }
                else
                    result.add(list.get(q));
            }
        }
        return result;
    }


    public double obliczDist(MyPoint[] tablica){
        double res = 0.0;
        for(int i=0;i<tablica.length-1;i++){
            res += tablica[i].calculateDistance(tablica[i+1]);
        }
        res += tablica[tablica.length-1].calculateDistance(start);
        return res;
    }

    public double obliczDist(List<MyPoint> tablica){ //metoda ktora oblicza ogolny dystans sciezki
        double res = 0.0;
        for(int i=0;i<tablica.size()-1;i++){
            res += tablica.get(i).calculateDistance(tablica.get(i+1));
        }
        res += tablica.get(tablica.size()-1).calculateDistance(start);
        return res;
    }


    public void readFilePoints(){ //metoda ktora odczytuje dane z pliku
        try {
            FileReader fr = new FileReader("src/points.txt");
            Scanner sc = new Scanner(fr);
            points.clear();
            while(sc.hasNext() && points.size()<=MAX_POINTS){
                points.add(new MyPoint((int)(sc.nextInt()/1.5),(int)(sc.nextInt()/1.5)));
            }


            System.out.println("Points from file:");
            System.out.println(points);

        }catch (FileNotFoundException ex){
            ex.printStackTrace();

        }


    }



    public void setStart(MyPoint point){ //metoda odznaczajaca punkt jako startowy
        for(MyPoint point1:points)
            point1.asStart = false;



        MyPoint res = points.get(0);
        double prevDist = points.get(0).calculateDistance(point);
        for(MyPoint p:points){
            if(prevDist > p.calculateDistance(point)){
                res = p;
                prevDist = p.calculateDistance(point);
            }
        }
        res.asStart = true;
        start = res;
        System.out.println(points.indexOf(res) + " point  - start point");


    }

    public void print(MyPoint[] tablica){
        for(int e=0;e<tablica.length;e++){
            System.out.print(tablica[e] + " ");
        }
        System.out.println();
    }


}
