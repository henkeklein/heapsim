package Main;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.LinkedList;

/**
 * This memory model allocates memory cells based on the first-fit method.
 *
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class FirstFit extends Memory {
    private LinkedList<Pointer> free = new LinkedList<>();
    private Pointer pointer;

    /**
     * Initializes an instance of a first fit-based memory.
     *
     * @param size The number of cells.
     */
    public FirstFit(int size) {
        super(size);
        free = new LinkedList<Pointer>();
    }

    /**
     * Allocates a number of memory cells.
     *
     * @param size the number of cells to allocate.
     * @return The address of the first cell.
     */
    @Override
    public Pointer alloc(int size) {
        int counter = 0;

        for (Pointer p : free) {
            if ((p.pointsAt() - counter) > size) {
                pointer = new Pointer(this);
                pointer.pointAt(counter);
                free.add(pointer);
                return pointer;
            }
            counter = p.pointsAt();

        }
        if (cells.length - counter + 1 > size) {
            pointer = new Pointer(this);
            pointer.pointAt(counter);
            free.add(pointer);
            return pointer;


        }

        return null;
    }

    /**
     * Releases a number of data cells
     *
     * @param p The pointer to release.
     */
    @Override
    public void release(Pointer p) {


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
        System.out.println("------------------------------------");


        int counter = 0;
        for (Pointer p : free) {
            if (counter < p.pointsAt()) {
                System.out.println("" + counter + " - " + (p.pointsAt() - 1) + " Free");
            }
            System.out.println("" + p.pointsAt() + " - " + (p.pointsAt() + " Allocated  (pointerSize is " + ")"));
            counter = p.pointsAt();
        }
        if (counter < cells.length) {
            System.out.println("" + counter + " - " + cells.length + " Free");
        }
    }
}
