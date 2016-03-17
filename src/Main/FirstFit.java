package Main;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.HashMap;

/**
 * This memory model allocates memory cells based on the first-fit method.
 *
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class FirstFit extends Memory {
    private Status[] status;
    private HashMap <Pointer,Integer>hash;
    private Pointer p;
    private int count=1;
    int counter=1;

    /**
     * Initializes an instance of a first fit-based memory.
     *
     * @param size The number of cells.
     */
    public FirstFit(int size) {
        super(size);

        hash = new HashMap<>();
        status = new Status[size];
        for (int i = 0; i < status.length; i++) {
            status[i] = Status.Empty;
        }
    }

    /**
     * Allocates a number of memory cells.
     *
     * @param size the number of cells to allocate.
     * @return The address of the first cell.
     */
    @Override
    public Pointer alloc(int size) {


        count+=size;
        if(size< count){
            p = new Pointer(this);
        for(int i=0; i<=count; i++) {
            if ((status[i] == Status.Empty)) {
                p.pointAt(i);
                status[p.pointsAt()] = Status.New;
                hash.put(p, counter);
            }
        }
        }
        counter++;
        return p;
    }

    /**
     * Releases a number of data cells
     *
     * @param p The pointer to release.
     */
    @Override
    public void release(Pointer p) {

      System.out.println(p.pointsAt()+"PPPPPPPPPP");
        System.out.println(hash.get(p)+"QQQQQQQQ");
        hash.remove(p);
    }

    /**
     * Prints a simple model of the memory. Example:
     * <p>
     * |    0 -  110 | Allocated
     * |  111 -  150 | Free
     * |  151 -  999 | Allocated
     * | 1000 - 1024 | Free
     */
    @Override
    public void printLayout() {
            for (int i = 0; i < cells.length; i++) {


                p.read(i);
                if (status[i] == Status.New) {
                    System.out.println("Used Memory:" + i);


                }
                if (status[i] == Status.Empty) {
                    System.out.println("Free Memory:" + i);

                }

        }
    }
}

