package Main;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

/**
 * This memory model allocates memory cells based on the first-fit method.
 *
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class FirstFit extends Memory {
    private int memory[] ;
    private Pointer point;
    private Status[] status;
    private int writePos=0;
    private int readPos;
    private int findPos;
    private int pointerCount=0;
    /**
     * Initializes an instance of a first fit-based memory.
     *
     * @param size The number of cells.
     * @param point
     */
    public FirstFit(int size, Pointer point) {
        super(size);
        this.point=point;
        memory = new int[size];
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
        int count=0;

        if(size<=memory.length) {
            while (status[writePos] == Status.Empty && count < size) {
                memory[writePos] = size;
                status[writePos] = Status.New;
               writePos++;
                count++;

                if(writePos==memory.length){
                    writePos-=1;
                }
            }
        }
        point.pointAt(pointerCount++);
        point.write(memory);
        return point;
    }

    /**
     * Releases a number of data cells
     *
     * @param p The pointer to release.
     */
    @Override
    public void release(Pointer p) {
        while (status[readPos] == Status.New) {
    int lenght = p.pointsAt();
        int arr[];

               arr= p.read(writePos);

for(int i:arr){
System.out.println(i+" ARR");

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
        // TODO Implement this!
    }


}
