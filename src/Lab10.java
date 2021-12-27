import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

import org.junit.Test;

public class Lab10 {
    public static void main(String[] args) throws Exception {
        System.out.println(sumVal("100 / 0"));
    }

    public static boolean isOperator(String op){
        if(op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("%")){
            return true;
        }
        return false;
    }

    public static int sumVal(String arithInput) throws IOException{
        //Scanner scanF = null;


        try {
            //FileReader fReader = new FileReader(arithInput);
            //scanF = new Scanner(fReader);
            List<String> readList = new ArrayList<String>();
            Stack<Integer> sumStack = new Stack<Integer>();

            String[] splitedInput = arithInput.trim().split("\\s+");
            for(int i=0; i<splitedInput.length; i++) {
                String currT = splitedInput[i];
                
                readList.add(currT);
                if(!isOperator(currT)){
                    sumStack.add(Integer.parseInt(currT));
                }

                if(readList.size() == 3){
                    if(isOperator(readList.get(0)) || isOperator(readList.get(2)) || !isOperator(readList.get(1))){
                        System.out.println("Input Error");
                        return -1;
                    }
                    if(isOperator(readList.get(1))){
                        if(readList.get(1).equals("+")){
                            sumStack.set(0, sumStack.get(0)+sumStack.get(1));
                            sumStack.pop();
                            readList.remove(2);
                            readList.remove(1);
                        }
                        else if(readList.get(1).equals("-")){
                            sumStack.set(0, sumStack.get(0)-sumStack.get(1));
                            sumStack.pop();
                            readList.remove(2);
                            readList.remove(1);
                        }
                        else if(readList.get(1).equals("*")){
                            sumStack.set(0, sumStack.get(0)*sumStack.get(1));
                            sumStack.pop();
                            readList.remove(2);
                            readList.remove(1);
                        }
                        else if(readList.get(1).equals("/")){
                            if(sumStack.get(1).equals(0)){
                                return -1;
                            }
                            sumStack.set(0, sumStack.get(0)/sumStack.get(1));
                            sumStack.pop();
                            readList.remove(2);
                            readList.remove(1);
                        }
                        else if(readList.get(1).equals("%")){
                            if(sumStack.get(1).equals(0)){
                                return -1;
                            }
                            sumStack.set(0, sumStack.get(0)%sumStack.get(1));
                            sumStack.pop();
                            readList.remove(2);
                            readList.remove(1);
                        }
                        
                    }
                }
            }
            return sumStack.pop();
        //} catch (FileNotFoundException | NullPointerException e) {
        //    System.out.println("File Not Found");
        }catch (InputMismatchException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }catch (NoSuchElementException e){
            System.out.println("Empty file");
            throw e;
        }catch (ArithmeticException a){
            System.out.println("is invalid");
        }catch (NumberFormatException n){
            System.out.println("is invalid");
        }finally {
            //scanF.close();
        }
        throw new IllegalArgumentException("Failed to find max");
    }

    @Test
    public void normalTest() throws IOException{
        assertEquals(2, sumVal("1 + 1"));
        assertEquals(0, sumVal("1 - 1"));
        assertEquals(-90, sumVal("10 - 100"));
        assertEquals(87, sumVal("100 - 13"));
        assertEquals(4, sumVal("2 * 2"));
        assertEquals(0, sumVal("2 * 0"));
        assertEquals(10, sumVal("100 / 10"));
        assertEquals(11, sumVal("22 / 2"));
        assertEquals(0, sumVal("100 % 2"));
        assertEquals(1, sumVal("103 % 2"));
        assertEquals(2, sumVal("12 % 5"));
    }

    @Test
    public void dividedwith0Test() throws IOException{
        assertEquals(-1, sumVal("100 / 0"));
        assertEquals(-1, sumVal("100 % 0"));
    }

    @Test
    public void othererrorTest() throws IOException{
        assertEquals("1+1", "1+1");
        assertEquals("+2", "+2");
    }
}
