/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp212as1_analyse;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
/**
 *
 * @author KSK  
 */
public class LineChartEx extends JFrame {

    public static void main(String[] args) {
        getResultOfsingleSimulate();//get a group of data set
        showGraph();//get the graph of analyse
    }
private static void getResultOfsingleSimulate() {
        int n = 1000;//node number
        int n2 = 3;//alpha
        
        System.out.println("Please input the number of node \"n\" you want to add");
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        
        System.out.println("Please input alpha you would like in ID generation");
        Scanner sc1 = new Scanner(System.in);
        n2 = sc1.nextInt();
        
        System.out.println("NODE  Rounds  Counter  MessageNum");
        //LCR
        Nodes network1 = GrnerateNodesRandomID(n, n2);//GrnerateNodes(n,a)
        //Nodes network1_1 = GrnerateNodesRandomID(n, n2);
        //Nodes network1_2 = GrnerateNodesRandomID(n, n2);

        Nodes network1_3 = GrnerateNodesCounterclockwiseID(n, n2);
        Nodes network1_4 = GrnerateNodesClockwiseID(n, n2);

        //HS
        Nodes network2 = GrnerateNodesRandomID(n, n2);
        //Nodes network2_1 = GrnerateNodesRandomID(n, n2);
        //Nodes network2_2 = GrnerateNodesRandomID(n, n2);

        Nodes network2_3 = GrnerateNodesCounterclockwiseID(n, n2);
        Nodes network2_4 = GrnerateNodesClockwiseID(n, n2);

        //Task1
        Task1LCR(network1);
        //Task1LCR(network1_1);
        //Task1LCR(network1_2);
        Task1LCR(network1_3);
        Task1LCR(network1_4);

        //Task2
        Task2HS(network2);
        //Task2HS(network2_1);
        //Task2HS(network2_2);
        Task2HS(network2_3);
        Task2HS(network2_4);

        //machine test
        System.out.print("LCR random ID result: ");
        BriefResultLCR(network1);
        //BriefResultLCR(network1_1);
        //BriefResultLCR(network1_2);
        System.out.print("LCR clockwise ID result: ");
        BriefResultLCR(network1_3);
        System.out.print("LCR counterclockwise ID result: ");
        BriefResultLCR(network1_4);

        System.out.print("HS random ID result: ");
        BriefResultHS(network2);
        //BriefResultHS(network2_1);
        //BriefResultHS(network2_2);
        System.out.print("HS clockwise ID result: ");
        BriefResultHS(network2_3);
        System.out.print("HS counterclockwise ID result: ");
        BriefResultHS(network2_4);
    }

    private static void showGraph() {
        SwingUtilities.invokeLater(() -> {
            LineChartEx ex = new LineChartEx();
            ex.setVisible(true);
        });
    }

    public LineChartEx() {
        initUI();
    }

    private void initUI() {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDataset() {
        
        //genarate network
        int s=0;//ID generate direction 0 for random, 1 clockwise, 2 counter clockwise
        int cut = 20;// the segment in the graph
        int nodeLimit = 100;// node number
        int a = 3;// alpha in ID generation
        int repeatExp = 3;// experement repeat number
        int p=0; //graph catogory
        
        System.out.println("Please input the number of node \"n\" you want to add");
        Scanner sc = new Scanner(System.in);
        nodeLimit = sc.nextInt();
        
        System.out.println("Please input the ID add function you would like, 0:random; 1:clockwise; 2:counter clockwise");
        Scanner sc1 = new Scanner(System.in);
        s = sc1.nextInt();
        
        System.out.println("Please input the graph you want to get, 0:LCR rounds 1:LCR message 2:HS rounds; 3:HS message; 4:rounds comparation; 5: message comparation");
        Scanner sc2 = new Scanner(System.in);
        p = sc2.nextInt();

        int distance = (int) nodeLimit / cut;
        int [][][] results=new int[6][cut][repeatExp];
        double [][] resultsAvg=new double[6][cut];

        for (int i0 = 0; i0 < repeatExp; i0++) {
            int start = 3;//first network node number
            for (int i = 0; i < cut; i++) {
                //this location is to adjust ID generation
                Nodes network = GrnerateID(s,start, a);
                Nodes network1 = GrnerateID(s,start, a);
                
                //simulate and get results
                Task1LCR(network);
                results[0][i][i0] = network.NodeCount;
                results[1][i][i0] = network.counter;
                results[2][i][i0] = network.MessageNum;
                
                Task2HS(network1);
                results[3][i][i0] = network1.NodeCount;
                results[4][i][i0] = network1.counter;
                results[5][i][i0] = network1.MessageNum;
                start += distance;
            }
        }

        for (int i = 0; i < 6; i++) {
            for (int i1 = 0; i1 < cut; i1++) {
                double avg=0;
                for (int i2 = 0; i2 < repeatExp; i2++) {
                    avg+=results[i][i1][i2];
                }
                avg/= repeatExp;
                resultsAvg[i][i1]=avg;
                avg=0;
            }
        }
        
        XYSeriesCollection mDataset1 = new XYSeriesCollection();
        XYSeriesCollection mDataset2 = new XYSeriesCollection();
        XYSeriesCollection mDataset3 = new XYSeriesCollection();
        XYSeriesCollection mDataset4 = new XYSeriesCollection();
        XYSeriesCollection mDataset5 = new XYSeriesCollection();
        XYSeriesCollection mDataset6 = new XYSeriesCollection();
        
        
        XYSeries series1 = new XYSeries("LCR Round");
        XYSeries series2 = new XYSeries("LCR Message");
        XYSeries series3 = new XYSeries("HS Round");
        XYSeries series4 = new XYSeries("HS Message");

        for (int i = 0; i < 6; i++) {
            for (int i1 = 0; i1 < cut; i1++) {
                series1.add(resultsAvg[0][i1], resultsAvg[1][i1]);
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int i1 = 0; i1 < cut; i1++) {
                series2.add(resultsAvg[0][i1], resultsAvg[2][i1]);
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int i1 = 0; i1 < cut; i1++) {
                series3.add(resultsAvg[3][i1], resultsAvg[4][i1]);
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int i1 = 0; i1 < cut; i1++) {
                series4.add(resultsAvg[3][i1], resultsAvg[5][i1]);
            }
        }
        //prepare graph
        mDataset1.addSeries(series1);
        
        mDataset2.addSeries(series2);
        
        mDataset3.addSeries(series3);
        
        mDataset4.addSeries(series4);
        
        mDataset5.addSeries(series1);
        mDataset5.addSeries(series3);

        mDataset6.addSeries(series2);
        mDataset6.addSeries(series4);

        //ArrayList<XYSeriesCollection> list = new ArrayList<XYSeriesCollection>();
        
        XYSeriesCollection mDataset = new XYSeriesCollection();
        
        switch (p) {
            case 0:
                mDataset = mDataset1;
                break;
            case 1:
                mDataset = mDataset2;
                break;
            case 2:
                mDataset = mDataset3;
                break;
            case 3:
                mDataset = mDataset4;
                break;
            case 4:
                mDataset = mDataset5;
                break;
            case 5:
                mDataset = mDataset6;
                break;
        }
        return mDataset;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Result", 
                "Node", 
                "Number", 
                dataset, 
                PlotOrientation.VERTICAL,
                true, 
                true, 
                false 
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Result",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;

    }

    public static Nodes GrnerateID(int s,int n,int a){
        Nodes network = new Nodes();
        switch (s) {
            case 0:
                network = GrnerateNodesRandomID(n, a);
                break;
            case 1:
                network = GrnerateNodesClockwiseID(n, a);
                break;
            case 2:
                network = GrnerateNodesCounterclockwiseID(n, a);
                break;
        }
        return network;
    }
    
    public static Nodes GrnerateNodesRandomID(int n, int a) {
        Nodes network = new Nodes();
        //Generate the ID pool from 1 to a*n
        int[] IDpool = new int[a * n];
        for (int i = 0; i < a * n; i++) {
            IDpool[i] = i + 1;
        }
        List list = new ArrayList();
        for (int i = 0; i < IDpool.length; i++) {
            list.add(IDpool[i]);
        }
        Collections.shuffle(list);
        for (int i = 0; i < n; i++) {
            network.addNode((int) list.get(i), i + 1);
        }
        network.CorrectleaderID = network.ReadBiggestID();
        return network;
    }

    public static Nodes GrnerateNodesClockwiseID(int n, int a) {
        Nodes network = new Nodes();
        //Generate the ID pool from 1 to a*n
        int[] IDpool = new int[a * n];
        for (int i = 0; i < a * n; i++) {
            IDpool[i] = i + 1;
        }
        List list = new ArrayList();
        for (int i = 0; i < IDpool.length; i++) {
            list.add(IDpool[i]);
        }
        //System.out.println();
        Collections.shuffle(list);
        List list2 = list.subList(0, n);
        list2.sort(null);
        for (int i = 0; i < n; i++) {
            network.addNode((int) list2.get(i), i + 1);
        }
        network.CorrectleaderID = network.ReadBiggestID();
        return network;
    }

    public static Nodes GrnerateNodesCounterclockwiseID(int n, int a) {
        Nodes network = new Nodes();
        //Generate the ID pool from 1 to a*n
        int[] IDpool = new int[a * n];
        for (int i = 0; i < a * n; i++) {
            IDpool[i] = i + 1;
        }
        List list = new ArrayList();
        for (int i = 0; i < IDpool.length; i++) {
            list.add(IDpool[i]);
        }
        Collections.shuffle(list);
        List list2 = list.subList(0, n);
        list2.sort(null);
        for (int i = n - 1; i >= 0; i--) {
            network.addNode((int) list2.get(i), i + 1);
        }
        network.CorrectleaderID = network.ReadBiggestID();
        return network;
    }

    public static int safeLongToInt(long l) {
        int i = (int) l;
        if ((long) i != l) {
            throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
        }
        return i;
    }

    public static void Task1LCR(Nodes network1) {
        network1.connectNode();
        network1.chooseLeaderBiggestTask1();
    }

    public static void Task2HS(Nodes network1) {
        network1.connectNode();
        network1.chooseLeaderBiggestTask2();
    }

    private static void BriefResultLCR(Nodes network) {
        //System.out.println("NODE  Rounds  Counter  MessageNum");
        System.out.println(network.NodeCount + " " + network.Rounds + " " + network.counter + " " + network.MessageNum);
    }

    private static void BriefResultHS(Nodes network) {
        //System.out.println("NODE  Phase  Counter  MessageNum");
        System.out.println(network.NodeCount + " " + network.Phase + " " + network.counter + " " + network.MessageNum);
    }
}
