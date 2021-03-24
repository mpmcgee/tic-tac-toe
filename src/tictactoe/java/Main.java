package tictactoe.java;
import java.util.Scanner;

public class Main {

    //take and validate input for each move
    public static String inputMove(String moves, int turnNum) {
        int nextMove = 0;   //reset move
        boolean inputValid = false; //boolean for input validation loop
        int nextMoveX;  //x coordinate input for move
        int nextMoveY;  //y coordinate input for move
        String symbol;  // 'X' or 'O' to be printed depending on turn

        while (!inputValid) {
            //get input
            System.out.print("Enter the coordinates: ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            try {
                //split input into array and store x and y values into separate variables
                String [] elements = input.split(" ");
                nextMoveX = Integer.parseInt(elements[0]);
                nextMoveY = Integer.parseInt(elements[1]);

                //throw exception if coordinates are out of range
                if (nextMoveX < 0 || nextMoveY < 0 || nextMoveX > 3 || nextMoveY > 3){
                    throw new StringIndexOutOfBoundsException();
                }

                //convert user-entered coordinates to string index value
                if (nextMoveX == 1) {
                    nextMove = nextMoveY - 1;
                } else if (nextMoveX == 2) {
                    nextMove = nextMoveY + 2;
                } else if (nextMoveX == 3) {
                    nextMove = nextMoveY + 5;
                }

                //check to see the selected a space is occupied
                boolean occupied = checkOccupied(moves, nextMove);

                //if space is open, fill with the appropriate symbol
                if (!occupied) {
                    if (turnNum % 2 != 0){
                        symbol = "X";
                    } else {
                        symbol = "O";
                    }
                    //update the board and end the loop
                    moves = updateBoard(moves, nextMove, symbol);
                    inputValid = true;

                  //if space is occupied, output message and return to input
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                }

              //print messages indicating incorrect input
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Coordinates should be from 1 to 3!");
            } catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Coordinates should be from 1 to 3!");
            }



        }
        //return the new moves list
        return moves;
    }

    //update board to show latest move
    public static String updateBoard(String moves, int nextMove, String symbol) {
        String updatedMoves;

        //add the new symbol to the board
        if (nextMove == 0){
            updatedMoves = symbol + moves.substring(nextMove+1, moves.length());
        } else if (nextMove == moves.length()-1) {
            updatedMoves = moves.substring(0, nextMove) + symbol;
        } else{
            updatedMoves = moves.substring(0, nextMove) + symbol + moves.substring(nextMove+1, moves.length());
        }

        //return the new moves list
        return updatedMoves;
    }

    //reset the board
    public static String initializeBoard(){

        //set and return blank moves string
        String moves = "_________";
        return moves;

    }

    //print the game board
    public static void printBoard(String moves) {
        String board;

        //replace '_' with ' ' for printing
        moves = moves.replace('_', ' ');

        //print board
        System.out.println("---------");
        for (int i = 1; i < 4; i++) {
            if (i == 1) {
                board = "| " + moves.charAt(0) + " " + moves.charAt(1) + " " + moves.charAt(2) + " |";
                System.out.println(board);
            } else if (i == 2) {
                board = "| " + moves.charAt(3) + " " + moves.charAt(4) + " " + moves.charAt(5) + " |";
                System.out.println(board);
            } else {
                board = "| " + moves.charAt(6) + " " + moves.charAt(7) + " " + moves.charAt(8) + " |";
                System.out.println(board);
            }
        }
        System.out.println("---------");

    }

    //check to see if a space is occupied
    public static boolean checkOccupied(String moves, int nextMove) {

        boolean occupied = false;

        if (moves.charAt(nextMove) != '_') {
            occupied = true;
        }

        return occupied;
    }
    //check to see if a game is over
    public static String checkWin(String moves) {

        String win = "none";
        int xWins = 0;
        int oWins = 0;

        for (int i = 0; i < moves.length(); i++) {
            //check horizontal win
            if (i == 0 || i == 3 || i == 6) {
                if (moves.charAt(i) == moves.charAt(i + 1) && moves.charAt(i + 1) == moves.charAt(i + 2)) {
                    if (moves.charAt(i) == 'X') {
                        xWins++;
                    } else if (moves.charAt(i) == 'O') {
                        oWins++;
                    }
                }
            }
            //check vertical win
            if (i < 3 && moves.charAt(i) == moves.charAt(i + 3) && moves.charAt(i + 3) == moves.charAt(i + 6)) {
                if (moves.charAt(i) == 'X') {
                    xWins++;
                } else if (moves.charAt(i) == 'O') {
                    oWins++;
                }
            }

        }
        //check diagonal 1 win
        if (moves.charAt(0) == moves.charAt(4) && moves.charAt(4) == moves.charAt(8)) {
            if (moves.charAt(0) == 'X') {
                xWins++;
            } else if (moves.charAt(0) == 'O'){
                oWins++;
            }
        }

        //check diagonal 2 win
        if (moves.charAt(2) == moves.charAt(4) && moves.charAt(4) == moves.charAt(6)) {
            if (moves.charAt(2) == 'X') {
                xWins++;
            } else  if (moves.charAt(2) == 'O'){
                oWins++;
            }

        }

        //check draw
        if (!moves.contains("_") && win.charAt(0) == 'n') {
            win = "Draw";
        }

        //set win variable
        if (xWins == 1 && oWins == 0) {
            win = "X wins";
        } else if (xWins == 0 && oWins == 1) {
            win = "O wins";
        }

        //return game status
        return win;
    }

    // game sequence
    public static void main(String[] args) {
        boolean gameOver = false;
        int turnNum = 1;

        //create and print empty board
        String moves = initializeBoard();
        printBoard(moves);


        //loop to continue while game is not over
        while(!gameOver){

            //check to see if game is over
            String win = checkWin(moves);

            //if game is not over, take move input, increment turn counter, print board
            if (win.charAt(0)== 'n') {
                moves = inputMove(moves, turnNum);
                turnNum++;
                printBoard(moves);

            //if game is over print results and end loop
            } else {
                System.out.println(win);
                gameOver = true;
            }
        }
    }
}
