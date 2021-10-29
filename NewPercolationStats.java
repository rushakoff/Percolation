import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// Estimates percolation threshold for an N-by-N percolation system.
public class NewPercolationStats {
    public New_Percolation p;
    public double[] avgSites;
    public int Tests;
    public int N;
    

    // Perform T independent experiments (Monte Carlo simulations) on an 
    // N-by-N grid.
    public NewPercolationStats(int N, int T) {
        System.out.print("Testing");
        Tests = T;
        this.N = N;
        if (N <= 0 || T < 0){throw new java.lang.IllegalArgumentException("Neither N nor T can be 0");}
        avgSites = new double[T];
        for (int t = 0; t < T; t++)
        {
            p = new New_Percolation(N);
            int[][] spaces = new int[N*N][2];
            int x = 0;
            int y = 0;
            for(int m = 0; m < N*N; m++)
            {   
                
                spaces[m] = new int[] {x, y};
                y++;
                if (y >= N)
                {
                    x++;
                    y = 0;
                }
            }
            for (int r = 0; r < N*N * 100; r++)
            {
                int i = StdRandom.uniform(0, N*N);
                int j = StdRandom.uniform(0, N*N);
                
                int[] temp = spaces[i];
                spaces[i] = spaces[j];
                spaces[j] = temp;
                //System.out.println(i + ", " + j);
            }
            
            int i = 0;
            while (!p.percolates() && i < N*N)
            {   
                //System.out.println(spaces[i][0] + ", " + spaces[i][1]);
                p.open(spaces[i][0],spaces[i][1],1);
                i++;
                //System.out.print(i + " ");
            }
            avgSites[t] = (double)p.numberOfOpenSites()/(double)(N*N);
            System.out.print(".");
            //System.out.println(p.numberOfOpenSites() + ", " + (N*N) + ", " + avgSites[t]);
        }
            System.out.println("");
    }
    
    // Sample mean of percolation threshold.
    public double mean() {
        /* Created this before finding out stdstats included a function for it*/
        //reimplemented after the email about ommitting tests with max open sites
        double sum = 0;
        int errorTest = 0;
        for (int i = 0; i < avgSites.length; i++)
        {
            if (avgSites[i] < 1)
            {
                sum += avgSites[i];
            }else
            {
                errorTest++;
            }
        }
        return sum/(Tests - errorTest);
        //return StdStats.mean(avgSites);
    }

    // Sample standard deviation of percolation threshold.
    public double stddev() {
        /* Created this before finding out stdstats included a function for it
        double variance = 0;
        double mean = mean();
        for (int i = 0; i < avgSites.length; i++)
        {
            variance += (avgSites[i] - mean) * (avgSites[i] - mean);
        }
            return variance/(avgSites.length - 1)*/
        return StdStats.stddev(avgSites);
    }

    // Low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        double stddev = stddev();
        double mean = mean();
        return mean - 1.96 * stddev / Math.sqrt(Tests); 
    }

    // High endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        double stddev = stddev();
        double mean = mean();
        return mean + 1.96 * stddev / Math.sqrt(Tests); 
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        NewPercolationStats stats = new NewPercolationStats(N, T);
        StdOut.printf("mean           = %f\n", stats.mean());
        StdOut.printf("stddev         = %f\n", stats.stddev());
        StdOut.printf("confidenceLow  = %f\n", stats.confidenceLow());
        StdOut.printf("confidenceHigh = %f\n", stats.confidenceHigh());
    }
}
