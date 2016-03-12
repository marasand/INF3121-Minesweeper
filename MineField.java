import java.util.Random;

class MineField{

    private boolean[][] mines,visible;
    private boolean boom;
    private final int rowMax = 5;
    private final int colMax = 10;

    MineField(){
        mines=new boolean[rowMax][colMax];
        visible=new boolean[rowMax][colMax];
        boom=false;

        initMap();

        int counter2=15;
        int randomRow,randomCol;
        Random RGenerator=new Random();

        while(counter2>0){

            randomRow=Math.abs(RGenerator.nextInt()%rowMax);
            randomCol=Math.abs(RGenerator.nextInt()%colMax);

            if(trymove(randomRow,randomCol))
                counter2--;
        }
    }

    private void initMap(){
        for(int row=0;row<rowMax;row++){
            for(int col=0;col<colMax;col++){
                mines[row][col]=false;
                visible[row][col]=false;
            }
        }
    }

    private boolean trymove(int randomRow, int randomCol) {
        if(mines[randomRow][randomCol])
            return false;
        
        mines[randomRow][randomCol]=true;
        return true;
    }

    private void boom() {
        for(int row=0; row<rowMax; row++){
            for(int col=0; col<colMax; col++){
                if(mines[row][col])
                    visible[row][col]=true;
            }
        }

        boom=true;
        show();
    }

    private boolean inside(int row, int col) {
        return (col>=0 && col<colMax && row>=0 && row<rowMax);
    }

    private char drawChar(int row, int col) {
        int count=0;

        if (!visible[row][col])
            return boom?'-':'?';

        if(mines[row][col]) return '*';
        
        for(int irow=row-1;irow<=row+1;irow++){
            for(int icol=col-1;icol<=col+1;icol++){
                if(inside(irow, icol) && mines[irow][icol]) 
                    count++;
            }
        }

        if (count>=0 && count<=8)
            return (char)(48+count);

        return 'X';
    }

    public boolean getBoom() {
        return boom;
    }

    public boolean legalMoveString(String input) {
        String[] separated=input.split(" ");
        int row, col;

        try{
            row=Integer.parseInt(separated[0]);
            col=Integer.parseInt(separated[1]);

            if (!inside(row, col))
                throw new java.io.IOException();
        }
        catch(Exception e){
            System.out.println("\nInvalid Input!");
            return false;
        }

        if(legalMoveValue(row,col))
            return true;
            
        return false;
    }

    private boolean legalMoveValue(int row, int col) {

        if(visible[row][col]){
            System.out.println("You stepped in allready revealed area!");
            return false;
        }
            
        visible[row][col]=true;

        if(mines[row][col]) {
            boom();
            return false;
        }

        return true;
    }

    public void show() {
        System.out.println("\n    0 1 2 3 4 5 6 7 8 9 \n   ---------------------");

        for(int row=0;row<rowMax;row++){
            System.out.print(row+" |");
            for(int col=0;col<colMax;col++){
                System.out.print(" "+drawChar(row,col));
            }
            System.out.println(" |");
        }
        System.out.println("   ---------------------");
    }
}
