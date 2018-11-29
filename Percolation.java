import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.In;

public class Percolation {
    private boolean [][] table;
    private int size;
    private int open_sites=0;
    private WeightedQuickUnionUF connections;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n)    {

       if(n>0){
           table=new boolean [n+1][n+1];
           size=n+1;
           connections = new WeightedQuickUnionUF((n+1)*(n+1));
       }

       else {
           throw new java.lang.IllegalArgumentException ("Invalid entry. Enter a number >0");
       }

    }
    // open site (row, col) if it is not open already
    public    void open(int row, int col) {
        check_index(row,col);
        if(!(this.table[row][col])){
            this.table[row][col]=true;
            open_sites+=1;
            int N=this.size;

            if((row-1)>0) { if(this.isOpen(row-1,col)){ this.connections.union(N*(row-1)+col,(N*row)+col);}}
            if((row+1)<N) { if(this.isOpen(row+1,col)){ this.connections.union(N*(row+1)+col,(N*row)+col);}}
            if((col-1)>0) { if(this.isOpen(row,col-1)){ this.connections.union((N*row)+col-1,(N*row)+col);}}
            if((col+1)<N) { if(this.isOpen(row,col+1)){ this.connections.union((N*row)+col+1,(N*row)+col);}}

            if (row==1){ this.connections.union(0,(N*row)+col);}
            //if (row==N-1 ){ this.connections.union(1,(N*row)+col);}
            //if (N-row<3 && this.isOpen(N-1,col)) {this.connections.union(1,(N*row)+col);}
            if (N-row<3 && this.isOpen(N-1,col)) {
                if (!this.connections.connected(1, 0) && this.connections.connected(0, (N * row) + col)) {
                    this.connections.union(1, (N * row) + col);
                }
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col){
        check_index(row,col);
        return this.table[row][col];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col){
        check_index(row,col);

        return this.isOpen(row, col) && this.connections.connected(0, (this.size * row) + col);

    }

    // number of open sites
    public     int numberOfOpenSites() {
        return this.open_sites;
    }

    // does the system percolate?
    public boolean percolates(){
       if( this.connections.connected(0,1)){ return true;}

        return false;
    }

    private void check_index(int row, int col){
        if(row<=0 || col<=0 || row>=this.size || col>=this.size) {
            throw new java.lang.IllegalArgumentException ("Column and/or row index out of range");
        }
        else {
            return;
        }

    }

    // test client (optional)
    public static void main(String[] args){
        //int N=5;
        //int row=0,col =0;
        int count=0;

        In in = new In(args[0]);      // input file
        int N = in.readInt();

       // System.out.println("N is "+ N);
        Percolation Test = new Percolation(N);


        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();


            Test.open(i, j);

          /*  count+=1;

            //System.out.println("row is "+ i + "col is "+ j);
            //PercolationVisualizer.draw(Test,N);

            try
            {
                if(count>229){
                    PercolationVisualizer.draw(Test,N);
                    System.out.println("row is "+ i + "col is "+ j);
                    //System.out.println(Test.connections.connected((19*N)+1,0));
                    //System.out.println(Test.isFull(i,j));
                    Thread.sleep(2000);}
                else  if (count==229) {
                    System.out.println("row is "+ i + "col is "+ j);
                    PercolationVisualizer.draw(Test,N);
                    Thread.sleep(5000);}
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }

            */

        }
    }
}



