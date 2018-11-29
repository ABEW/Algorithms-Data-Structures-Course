import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
/**
 * Created by Abew on 2018-11-13.
 */



public class PercolationStats {

    private final  double[] OpenSpaces;
    private final int iter;
    private final static double Tval=1.96;
    private double MeanVal= -1;
    private double StdVal= -1;

    public PercolationStats(int n,int t) {

        if(n<1 || t<1) {throw new java.lang.IllegalArgumentException (" Both n and trials must be >0");}

        int runs=0;
        int row,col;
        iter=t;
        OpenSpaces= new double[t];

        while (runs < t) {
            Percolation Test = new Percolation(n);
            while (!Test.percolates()) {
                row = StdRandom.uniform(1, n+1);
                col = StdRandom.uniform(1, n+1);

                while (Test.isOpen(row, col)) {
                    row = StdRandom.uniform(1, n+1);
                    col = StdRandom.uniform(1, n+1);
                }

                Test.open(row, col);
              /* PercolationVisualizer.draw(Test,n);

                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                */

            }
            OpenSpaces[runs] = Test.numberOfOpenSites() / (n * n* 1.0);
            runs += 1;
        }
    }

    public double confidenceHi(){
        if (StdVal==-1){ StdVal=StdStats.stddev(OpenSpaces);}
        if (MeanVal==-1){ MeanVal=StdStats.mean(OpenSpaces);}
        return MeanVal+((Tval*StdVal)/Math.sqrt(iter));
    }
    public double confidenceLo(){
        if (StdVal==-1){ StdVal=StdStats.stddev(OpenSpaces);}
        if (MeanVal==-1){ MeanVal=StdStats.mean(OpenSpaces);}
        return MeanVal-((Tval*StdVal)/Math.sqrt(iter));
    }
    public double mean(){
        MeanVal=StdStats.mean(OpenSpaces);
        return MeanVal;
    }
    public double stddev(){
        StdVal=StdStats.stddev(OpenSpaces);
        return StdVal;

    }

    public static void main(String [] args){
        int N= Integer.parseInt(args[0]);
        int T= Integer.parseInt(args[1]);

        PercolationStats Teststat= new PercolationStats (N, T);

        System.out.println("Mean:  "+ Teststat.mean());
        System.out.println("Stdev: "+ Teststat.stddev());
        System.out.println("95% CI: ["+ Teststat.confidenceLo()+" , "+Teststat.confidenceHi()+"]");
    }
}
