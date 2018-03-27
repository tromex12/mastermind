package mastermind2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.util.Pair;

public class Tester {
     List<Integer> code= new ArrayList();
    private List<Integer> quantity= new ArrayList();;
    private int game =100;
     int gameCnt=0;
   public Tester(){
   }
   public void startGame(){
       createRandomList(); 
       System.out.println("Code is : ");
       for ( int i = 0 ; i < game ; i ++ )
             System.out.print(code.get(i)+ " ");
       System.out.println("");
   
   }
   public void createRandomList(){
             Random rand = new Random();
             for ( int i = 0 ; i < game+1 ; i ++ ){
                 quantity.add(0);
             }
            for ( int i = 0 ; i < game; i++ ){
                int n = rand.nextInt(game) + 1;
                code.add(n);
                quantity.set(n, quantity.get(n)+1);
            }
    }
   public Pair<Integer,Integer> send( List<Integer> guess ){
       gameCnt++;
       int black = 0;
       int white = 0;
       List<Integer> qtmp = new ArrayList();
       for ( int i = 0 ; i < quantity.size(); i++ )
           qtmp.add(quantity.get(i));
       for ( int i = 0 ; i < game ; i ++ ){
         if ( guess.get(i).equals(code.get(i)) && (int)qtmp.get(guess.get(i)) > 0 ){
             black ++;
             qtmp.set(guess.get(i), qtmp.get(guess.get(i))-1);
         }
       }
       for ( int i = 0 ; i < game; i++ ){
             if ( qtmp.get(guess.get(i)) > 0 && !guess.get(i).equals(code.get(i))){
                 white++;
                 qtmp.set(guess.get(i), qtmp.get(guess.get(i))-1);
             }
       }
       if ( black == 100 ){
           System.out.println("Game cnt: "+ gameCnt);
       }
       return new Pair(black,white);
   }
   public void sortedCode(){
       Collections.sort(code);
       for ( int i = 0 ; i <100 ; i ++ )
           System.out.print(code.get(i)+ " ");
       System.out.println("");
   }
}