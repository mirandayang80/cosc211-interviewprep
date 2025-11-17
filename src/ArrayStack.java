public class ArrayStack  {

    //instance variables
    public String[] arr;
    public int size;

    public ArrayStack() {
        this.arr = new String[10];
        this.size = 0;
    }

    //get the size
    public int size() {
        return size;
    }

    //push item onto stack
    public void push(String toPush) {

        //Check size of array
        if(arr.length <= size){
            //double size of array if array is full
            String[] newarr = new String[arr.length * 2];

            //copy everything over to new array
            for(int i = 0; i < arr.length; i++){
                newarr[i] = arr[i];
            }
            //set arr to the new array
            arr = newarr;
        }

        //push the String onto stack
        arr[size] = toPush;

        //update size
        size += 1;

    }

    //removes last item and returns it
    public void pop() {

        //set last element to empty string
        this.arr[this.size - 1] = "";

        String[] newarr = new String[(2 * arr.length) / 3];

        // if array is less than half filled then reduce size by 1/3
        if(this.arr.length >= this.size * 2){
            for (int i = 0; i < this.size; i++){
                newarr[i] = arr[i];
            }
            this.arr = newarr;
        }

        //Adjust size
        this.size -=1;

    }

    public String peek(int offset){
        return this.arr[this.size - 1 - offset];
    }

    public void insertBottom(String toInsert) {

        String[] newarr;

        if (size >= arr.length){
            newarr = new String[size * 2];
        }
        else {
            newarr = new String[size + 1];
        }

        newarr[0] = toInsert;

        for(int i = 0; i < this.size; i++){
            newarr[i + 1] = this.arr[i];
        }

        size += 1;
        this.arr = newarr;

    }

    public String extractBottom() {

        String[] newarr = new String[size - 1];

        String bottom = this.arr[0];

        for(int i = 0; i < newarr.length; i++){
            newarr[i] = this.arr[i + 1];
        }

        size -= 1;
        this.arr = newarr;

        return bottom;
    }

    //expand if overflow but not using push
    public void expand(){
        if(arr.length <= size){
            //double size of array if array is full
            String[] newarr = new String[arr.length * 2];

            //copy everything over to new array
            for(int i = 0; i < arr.length; i++){
                newarr[i] = arr[i];
            }
            //set arr to the new array
            arr = newarr;
        }
    }

    public void print() {

        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }

    }


}
