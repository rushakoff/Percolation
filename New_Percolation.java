import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Models an N-by-N percolation system.
public class New_Percolation {
    public int[][] pGrid;
    public int numOpen = 0;
    public boolean[][] fullList;
    
    // Create an N-by-N grid, with all sites blocked.
    public New_Percolation(int N) {
        if (N <= 0){throw new java.lang.IllegalArgumentException("N cannot be 0");}
        pGrid = new int[N][N];
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                pGrid[i][j] = 0;
            } 
        }
    }

    // Open site (i, j) if it is not open already.
    public void open(int i, int j, int k) {
        if (k == 0){throw new java.lang.IllegalArgumentException("Open node may not be k");}
        pGrid[i][j] = k;
        numOpen++;
    }

    // Is site (i, j) open?
    public int isOpen(int i, int j) {
        return (pGrid[i][j]);
  
    }

    // Is site (i, j) full?
    public boolean isFull(int i, int j) {
        return (fullList[i][j]);
    }

    // Number of open sites.
    public int numberOfOpenSites() {
        return numOpen;
    }

    // Does the system percolate?
    public boolean percolates() {
        for (int j = 0; j < pGrid.length; j++)
            if (pGrid[0][j] > 0)
            {   
                int[][] pPath = new int[pGrid.length * pGrid.length][2];
                fullList = new boolean[pGrid.length][pGrid.length];
                for (int i = 0; i < pPath.length; i++){pPath[i] = new int[]{-1,-1};}
                pPath[0] = new int[] {0, j};
                fullList[pPath[0][0]][pPath[0][1]] = true; 
                int k = 0;
                int i = 0;
                while (i < pGrid.length - 1 && pPath[0] != new int[] {-1,-1})
                {
                    if (k < 0){break;}
                    int[] curSpace = pPath[k];
                    
                    pPath[k] = new int[] {-1,-1};
                    k--;
                    try
                    {
                        if (curSpace[0]+1 < pGrid.length)
                        {
                            if (isOpen(curSpace[0], curSpace[1]) == isOpen(curSpace[0]+1, curSpace[1]) && !isFull(curSpace[0]+1, curSpace[1]))
                            {   
                                /* Outputting the current test direction
                                System.out.println("down");*/
                                pPath[k+1] = new int[] {curSpace[0]+1, curSpace[1]};
                                k++;
                                fullList[curSpace[0]+1][curSpace[1]] = true;
                                if (curSpace[0]+1 > i){i = curSpace[0] + 1;}
                            }
                        }
                    }catch(ArrayIndexOutOfBoundsException ex){}
                    try
                    {
                        if (curSpace[1]-1 >= 0)
                        {
                            if (isOpen(curSpace[0], curSpace[1]) == isOpen(curSpace[0], curSpace[1]-1) && !isFull(curSpace[0], curSpace[1]-1))
                            {   
                                /* Outputting the current test direction
                                System.out.println("left");*/
                                pPath[k+1] = new int[] {curSpace[0], curSpace[1]-1};
                                k++;
                                fullList[curSpace[0]][curSpace[1]-1] = true;
                            }
                        }
                    }catch(ArrayIndexOutOfBoundsException ex){}
                    try
                    {
                        if (curSpace[1]+1 < pGrid.length)
                        {
                            if (isOpen(curSpace[0], curSpace[1]) == isOpen(curSpace[0], curSpace[1]+1) && !isFull(curSpace[0], curSpace[1]+1))
                            {   
                                /* Outputting the current test direction
                                System.out.println("right");*/
                                pPath[k+1] = new int[] {curSpace[0], curSpace[1]+1};
                                k++;
                                fullList[curSpace[0]][curSpace[1]+1] = true;
                            }
                        }
                    }catch(ArrayIndexOutOfBoundsException ex){}
                    try
                    {
                        if (curSpace[0]-1 >= 0)
                        {
                            if (isOpen(curSpace[0], curSpace[1]) == isOpen(curSpace[0]-1, curSpace[1]) && !isFull(curSpace[0]-1, curSpace[1]))
                            {   
                                /* Outputting the current test direction
                                System.out.println("up");*/
                                pPath[k+1] = new int[] {curSpace[0]-1, curSpace[1]};
                                k++;
                                fullList[curSpace[0]-1][curSpace[1]] = true;
                            }
                        }
                    }catch(ArrayIndexOutOfBoundsException ex){}
                    /* Testing if my while loops are working
                    for(int y = 0; y < pPath.length; y++)
                    {
                        System.out.print("[" + pPath[y][0]+ ", ");
                        System.out.println(pPath[y][1] + "]");
                    }*/
                }
                if (i == pGrid.length-1){ return true;}
            }
            return false;
    }

    // An integer ID (1...N) for site (i, j).
    private int encode(int i, int j) {
        return 1;
    }

  // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        New_Percolation perc = new New_Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            int k = in.readInt();
            perc.open(i, j, k);
        }
        StdOut.println(perc.numberOfOpenSites() + " open sites");
        if (perc.percolates()) {
            StdOut.println("percolates");
        }
        else {
            StdOut.println("does not percolate");
        }
        
        // Check if site (i, j) optionally specified on the command line
        // is full.
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.println(perc.isFull(i, j));
        }
    }
}
