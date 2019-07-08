import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI implements Runnable {

    TSP tsp;
    boolean toAddPoint = false,toDrawLines = false,chooseStart = false;
    JButton result,readBtn,reset,choose;

    public GUI(TSP tsp){
        this.tsp = tsp;
    }


    @Override
    public void run() {
        JFrame frame = new JFrame("TSP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(650,650));
        frame.setLocation(500,170);
        frame.setVisible(true);


        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                for(MyPoint p:tsp.points)
                    if(p.asStart){
                        g.setColor(Color.GREEN);
                        g.fillOval(p.x, p.y, 8, 8);
                        g.setColor(Color.BLUE);
                    }
                    else{
                        g.fillOval(p.x, p.y, 8, 8);
                    }


                if(toDrawLines) {
                    g.setColor(Color.RED);
                    for(int i=0;i<tsp.way.size()-1;i++) {
                        g.drawLine(tsp.way.get(i).x+4, tsp.way.get(i).y+4, tsp.way.get(i+1).x+4, tsp.way.get(i+1).y+4);
                    }
                    g.setColor(Color.CYAN);

                    g.drawLine(tsp.way.get(tsp.way.size()-1).x+4,tsp.way.get(tsp.way.size()-1).y+4,tsp.start.x+4,tsp.start.y+4); //rysowanie krawedzi od punktu koncowego do startu
                }


            }
        };
        panel.setBackground(Color.WHITE);

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(chooseStart){ //czy wlaczony wybor punktu startowego
                    tsp.setStart(new MyPoint(e.getX(),e.getY()));
                    chooseStart = false;
                    panel.repaint();
                }else { //czy mozna jescze dodac punkt


                    if (tsp.points.size() == TSP.MAX_POINTS) {
                        JOptionPane.showMessageDialog(panel, "More than " + TSP.MAX_POINTS + " points!", "Adding error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        System.out.println("New point added: x = " + e.getX() + "  y = " + e.getY());
                        tsp.points.add(new MyPoint(e.getX(), e.getY()));
                        panel.repaint();
                    }


                }



            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        JPanel upPanel = new JPanel(new FlowLayout());
        JRadioButton jr1 = new JRadioButton("Algorytm zachlanny",true);
        JRadioButton jr2 = new JRadioButton("Algorytm wspinaczkowy");
        ButtonGroup group = new ButtonGroup();
        group.add(jr1);
        group.add(jr2);
        upPanel.add(jr1);
        upPanel.add(jr2);






        JPanel down = new JPanel(new FlowLayout());

        result = new JButton("Result");
        result.addActionListener((e -> {
            if(jr1.isSelected()) {
                tsp.way = tsp.greedy();
            }
            else {
                tsp.way = tsp.hillClimbing();
            }
            toDrawLines = true;
            panel.repaint();
            JOptionPane.showMessageDialog(panel,"DISTANCE = " + tsp.distance + "\nTIME = " + tsp.time + " MS");
        }));
        result.setEnabled(false);

        readBtn = new JButton("Read points from file");
        readBtn.addActionListener((e)->{
            tsp.readFilePoints();
            panel.repaint();
        });
        reset = new JButton("Reset");
        reset.addActionListener((e)->{
            tsp.points.clear();
            tsp.way.clear();
            tsp.distance = 0.0;
            tsp.start = null;
            toDrawLines = false;
            panel.repaint();
            result.setEnabled(false);

        });
        choose = new JButton("Choose start point");
        choose.addActionListener((ev)->{
            chooseStart = true;
            result.setEnabled(true);
        });




        down.add(result);
        down.add(readBtn);
        down.add(choose);
        down.add(reset);

        frame.add(down,BorderLayout.AFTER_LAST_LINE);




        frame.add(upPanel,BorderLayout.BEFORE_FIRST_LINE);




        frame.add(panel);
        frame.pack();
    }
}
