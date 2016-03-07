package Main;

/**
 * Created by Henke on 2016-03-07.
 */
public class Main {

    public static void main(String [] args){
        RawMemory rawMemory = new RawMemory(256);
        Pointer point = new Pointer(rawMemory);
        FirstFit firstFit = new FirstFit(256, point);
        firstFit.alloc(20);




    }
}
