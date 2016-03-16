package Main;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

/**
 * This memory model allocates memory cells based on the first-fit method.
 *
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class FirstFit extends Memory {
    private Status[] status;
    private int writePos = 0;
    private int readPos;
    private Pointer p;
    private int count;

    /**
     * Initializes an instance of a first fit-based memory.
     *
     * @param size The number of cells.
     */
    public FirstFit(int size) {
        super(size);
        p = new Pointer(this);
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

        if(status.length-size >0){
            for(int i=0; i<=size; i++){
                count=p.pointsAt();
            if ((status[count] == Status.Empty)) {
                p.pointAt(i);
                status[p.pointsAt()] = Status.New;

            }
            }
                }
    return p;
}

    /**
     * Releases a number of data cells
     *
     * @param p The pointer to release.
     */
    @Override
    public void release(Pointer p) {
        int lenght = p.pointsAt();
        int arr[];

        while (status[readPos] == Status.New) {
            status[readPos] = Status.Empty;
            readPos++;
            arr = p.read(readPos);

            for (int i : arr) {
                System.out.println(i + " ARR");
            }
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
       for(int i =0; i < p.read(status.length).length; i++){
        if(status[i]==Status.New){
    System.out.println("Used Memory:" + i);
           }
           if(status[i]==Status.Empty){
               System.out.println("Free Memory:" + i);

           }

       }
    }
}
