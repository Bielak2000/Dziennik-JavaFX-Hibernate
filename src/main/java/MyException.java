public class MyException extends Exception{
    public MyException() {}

    public void printError(String s){
        System.out.println(s);
    }
}