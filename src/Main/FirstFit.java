package Main;

/**
 * This memory model allocates memory cells based on the first-fit method.
 *
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class FirstFit extends Memory {
    private int pointers[] ;
    private Pointer point;
    private int count=0 ;
    /**
     * Initializes an instance of a first fit-based memory.
     *
     * @param size The number of cells.
     * @param point
     */
    public FirstFit(int size, Pointer point) {
        super(size);
        this.point=point;
        pointers = new int[size];


    }

    /**
     * Allocates a number of memory cells.
     *
     * @param size the number of cells to allocate.
     * @return The address of the first cell.
     */
    @Override
    public Pointer alloc(int size) {

            if(size>pointers.length){
                System.out.println("Out of memory");
            }else {
                for (int i = 0; i < pointers.length; i++) {
                    pointers[i] = count;
                    if (pointers[count] < size) {
                        count++;
                    }

                }
                point.write(pointers);
            }
        return point;
    }

    /**
     * Releases a number of data cells
     *
     * @param p The pointer to release.
     */
    @Override
    public void release(Pointer p) {
        // TODO Implement this!
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
        // TODO Implement this!
    }


}
