package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * This memory model allocates memory cells based on the best-fit method.
 *
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class BestFit extends Memory {
    private Status[] status;
    private Map<Pointer, Integer> hash;
    private LinkedList<Pointer> pointerList;

    private ArrayList<Integer> list;
    private ArrayList<Integer> list1;
    private Pointer p;
    private int writePos;
    private int readPos;

    /**
     * Initializes an instance of a best fit-based memory.
     *
     * @param size The number of cells.
     */
    public BestFit(int size) {
        super(size);
        pointerList = new LinkedList<Pointer>();
        hash = new HashMap<>();
        list = new ArrayList<Integer>();
        list1 = new ArrayList<Integer>();
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
        boolean h = true;
        int e = 0;
        p = new Pointer(this);
        checkFreeMemory(size);
        while (status[writePos] == Status.USED && h) {
            writePos++;
            if (status[writePos] == Status.Empty && status[writePos + size] == Status.Empty) {

                while (status[writePos] == Status.Empty && e <= size) {
                    writePos++;
                    e++;
                    if (status[writePos] == Status.USED) {
                        e = 0;
                    }
                    if (e == size) {
                        h = false;
                    }
                }
                writePos -= e;
            }
        }
        int i = 0;
        while ((status[writePos] == Status.Empty) && i < size) {
            p.pointAt(writePos);
            status[writePos] = Status.USED;
            writePos++;
            i++;
        }
        pointerList.add(p);
        hash.put(p, size);
        writePos = 0;
        return p;
    }

    /**
     * Releases a number of data cells
     *
     * @param p The pointer to release.
     */
    @Override
    public void release(Pointer p1) {
        int remover = 0;
        readPos = (p1.pointsAt());

        try {
            while (status[readPos] == Status.USED && hash.get(p1) - remover > 0) {
                status[readPos] = Status.Empty;
                readPos--;
                remover++;
            }
            pointerList.remove(p);
            hash.remove(p);

        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    /**
     * Prints a simple model of the memory. Example:
     *
     * |    0 -  110 | Allocated
     * |  111 -  150 | Free
     * |  151 -  999 | Allocated
     * | 1000 - 1024 | Free
     */
    @Override
    public void printLayout() {
        for (int i = 0; i < status.length; i++) {

            if (status[i] == Status.USED) {
                System.out.println("----- Used Memory:" + i);
            }
            if (status[i] == Status.Empty) {
                System.out.println("+++++ Empty Memory:" + i);
            }

        }
    }
    public void checkFreeMemory(int size) {
        int count = 0;

        for (int i = 0; i < status.length-1; i++) {
                if (status[i] == Status.Empty) {
                    if(count==0){
                        list1.add(i);
                        System.out.println(i+ " STARTPOS");
                    }
                        if (status[i+1] == Status.USED || size>=count) {
                            if(size==count){
                                list.add(i);
                                System.out.println(i +" ENDPOS");
                            }
                        }
                    count++;
                        }
                    }
             }
        }


