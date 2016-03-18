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
        int i = 0;
        p = new Pointer(this);
        int bestAddress = checkFreeMemory(size);


        while ((status[bestAddress] == Status.Empty) && i < size) {
            p.pointAt(bestAddress);
            status[bestAddress] = Status.USED;
            bestAddress++;
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
     * <p>
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

    public int checkFreeMemory(int size) {
        int address = -1;
        int minSpace = Integer.MAX_VALUE;
        int count = 0;
        int first = 0;
        int last = 0;
        Status s = status[0];


        for (int i = 1; i < status.length; i++) {
            if (status[i].equals(s)) {
                last++;
            } else {
            if (status[i] == Status.Empty && ((last - first + 1) >= size)) {
                if ((last - first + 1) - size < minSpace) {
                    address = first;
                    minSpace = (last - first + 1) - size;
                }
            }
            last++;
            s = status[i];
            first = last;
        }
    }

        if (s.equals(Status.Empty) && ((last - first + 1) >= size)) {
            if ((last - first + 1) - size < minSpace) {
                address = first;
            }
        }

        return address;

    }
}


