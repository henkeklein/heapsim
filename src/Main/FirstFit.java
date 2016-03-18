package Main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * This memory model allocates memory cells based on the first-fit method.
 *
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class FirstFit extends Memory {
    private Status[] status;
    private Map<Pointer, Integer> hash;
    private LinkedList<Pointer> pointerList;
    private Pointer p;
    private int writePos;
    private int readPos;

    /**
     * Initializes an instance of a first fit-based memory.
     *
     * @param size The number of cells.
     */
    public FirstFit(int size) {
        super(size);
        pointerList = new LinkedList<Pointer>();
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
        boolean h = true;
        int i = 0;
        p = new Pointer(this);

        while (status[writePos] == Status.USED && h) {
            writePos++;
            if (status[writePos] == Status.Empty) {
                if (status[writePos + size] == Status.Empty) {
                    int e = 0;

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
        }
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
            sortPointers();
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
                System.out.println("+++++ Free Memory:" + i);
            }

        }
    }

    private void sortPointers() {
        pointerList.sort((p1, p2) -> {
            return p1.pointsAt() - p2.pointsAt();
        });
    }
}

