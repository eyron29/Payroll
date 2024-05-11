package Project1;

public class InvalidInputException extends Exception{
    public InvalidInputException(String str){
        System.out.println("Exception: " + str);
    }
}
