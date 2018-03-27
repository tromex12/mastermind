package mastermind2;



import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;


/* If you want to use tester, just uncomment Tester declaration and comment JSONcommunicator declaration.
*/

public class Decider2 {
    private List <Integer> mainList = new ArrayList(); // list of numbers
    private Tester game = new Tester();
    //private JSONcommunicator game= new JSONcommunicator(); // communicator with server
    
    public void printArray(List<Integer> l){
        for ( int i = 0 ; i < l.size(); i++ ){
            System.out.print(l.get(i)+" ");
        }
        System.out.println("");
    }
    
    public void Decide(){
        game.startGame();
        for ( int i = 0 ;i  < 100 ; i ++ ){
            mainList.add(0);
        }
        for ( int i = 1 ; i < 101 ; i ++ ){ // Main cycle : all numbers from 1 to 100 are tested
            List <Integer> list = new ArrayList();
            for ( int l=0;l<100;l++)
                list.add(i);
                recursiveSearch(list,100,0,0,i);
        } 
        Pair<Integer,Integer> result = game.send(mainList);
        printArray(mainList);
        System.out.println("BLACK are "+ result.getKey());
        //System.out.println("Counter: "+ game.gameCnt);
        }
    
        public void recursiveSearch (List<Integer> list,int size, int start, int bc,int numb){ // bc is black's count, numb is current number being checked
        if ( size == 1){ // then found position of number
            mainList.set(start, numb);
            return;
        }
        List <Integer> l1= new ArrayList();// two sub-arrays to test recursively
        List <Integer> l2= new ArrayList();
        for ( int i = 0 ; i < 100 ;i ++ ){
            l1.add(0);
            l2.add(0);
        }
        int s1,s2; // size of two sub-arrays
        if ( size % 2 == 1){
            s1 = (size - 1)/2;
            s2 = (size - 1)/2 + 1;
        }
        else{
            s1=(size /2);
            s2=s1;
        }
        for ( int i = start ; i < s1+start ; i ++ ){
            l1.set(i,list.get(i));
        }
        for ( int i = start+s1; i < start+s1+s2; i ++ ){
            l2.set(i,list.get(i));
        }
        if ( size != 100 && bc == 0 ) // no blacks
            return;
        Pair<Integer,Integer> resp = game.send(l1);
        int p2 = 0;
        int p1 = resp.getKey();
        if ( size == 100) // only on first call of function
            p2 = resp.getValue();
        else if ( p1 != bc) // blacks on sub-array are less then was on initial array so remainder is definetely on 2nd half
            p2 = bc-p1;
        
        if ( p1 > 0 ){
            int alreadyFound = 0;
            int tmp =0; // just position of last element not found
            for ( int i = start; i < start+s1; i ++ ){
                if ( mainList.get(i) != 0 ) // if not found
                    alreadyFound++;
                else
                    tmp = i;
            }
            if ( alreadyFound < s1 - 1 )
                recursiveSearch(l1,s1,start,p1,numb);
            else{ // only if not found only one number
                mainList.set(tmp, numb);
            }
        }
        if ( p2 > 0 ){
            int alreadyFound = 0;
            int tmp = 0;
            for ( int i = start+s1; i < start+s1+s2; i ++ ){
                if ( mainList.get(i) != 0 )
                    alreadyFound++;
                else
                    tmp = i;
            }
            if (  alreadyFound < s2 - 1 )
                recursiveSearch(l2,s2,start+s1,p2,numb);
            else{
                mainList.set(tmp, numb);
            }
        }
        
    }
}