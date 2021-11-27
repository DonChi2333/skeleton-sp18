public class ArrayDeque<T> {
    //后面初始化时再分配空间
    private T[] items;
    //因为数组可以下标检索，所以此处指针使用整型定义即可
    //此处可以定义一个size变量来统计队列长度也可以直接通过首尾指针和容量编写size()方法
    private int front;
    private int rear;
    private int capacity = 8;
    private int size;
    /** Creates an empty array deque.即使用构造器初始化顺序队列
     * 此处可以设置成传入一个数组容量
     * 但由于题目已经把容量设成8了，所以我就不设置传入容量参数了*/
    public ArrayDeque() {
        //+1是因为要故意浪费一个空间，来判断循环队列是否满了，否则循环队列中判断队空和队满条件一样了
        items = (T[]) new Object[capacity + 1];//new T[capacity]也行
        front = rear = 0;
        size = 0;
    }
    /**此处的入队需要判断是否真溢出，然后还得调用扩容方法
     * 使这个用数组实现的循环双端队列实现自动扩容
     */
    public void addLast(T item) {
        /**因为故意空了一个空间出来，所以rear+1==front的话就是队满了
         * 要对数组长度取余是因为是循环队列
         * 取余后可以让它从数组最后跳到数组最前（就是下标为0）
         */
        if ((rear+1) % items.length == front) {
            resize(getCapacity()*2);//扩容1.5或2都行，扩容方法在后面
        }
        items[rear] = item;//把传进来的数据赋给尾指针所指
        rear = (rear + 1) % items.length;//尾指针向后移
        size++;
    }
    public void addFirst(T item){
        //先判断是否队满，是则扩容
        if ((rear+1) % items.length == front) {
            resize(getCapacity()*2);//扩容1.5或2都行，扩容方法在后面
        }
        //注意这里是先把头指针向前移一格
        //如果下标为0往前移一格则使之移到item.length-1的下标（数组的最后）（不存放元素）
        front = (front - 1 + items.length) % items.length;
        items[front] = item;
        size++;
    }
    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return front == rear;
    }

    /** 此处我直接返回变量size,当然也可以用公式
     * return (rear - front + capacity) % capacity;
     * 注意此处的capacity是items.length数组长度哦！
     */
    public int size() {
        return size;
    }
    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque(){
        //不写破环性方法，所以让i、j替换掉指针
        int i = front;
        int j = rear;
        //当队不为空且指针还没遍历到队满时进入循环
        while (i != j && (j+1) % items.length != i){
            System.out.print(items[i] + " ");
            i = (i + 1) % items.length;
        }
    }
    /**
     * Removes and returns the item at the front of the deque. If no such item
     * exists, returns null.
     */
    public T removeFirst(){
        if(front == rear){
            return null;
        }
        T data = items[front];
        front = (front + 1) % items.length;
        size--;
        return data;
    }
    /**
     * Removes and returns the item at the back of the deque. If no such item
     * exists, returns null.
     */
    public T removeLast() {
        if(front == rear){
            return null;
        }
        rear = (rear - 1 + items.length) % items.length;
        T data = items[rear];
        return data;
    }
    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item,
     * and so forth. If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index){
        int start = front;
        for (int i = 0;i < index;i++){
            start = (start + 1) % items.length;
        }
        return items[start];
    }
    //因为有一个不存数据，所以实际容量是数组长度-1
    public int getCapacity() {
        return items.length-1;
    }
    private void resize(int newCapacity) {
        T[] newData = (T[])new Object[newCapacity + 1];
        for(int i = 0;i < size;i++) {
            //将原来的循环队列里的元素从队首开始，依次放到新循环队列里
            //临界是小于队列长度或等于size-1
            newData[i] = items[(i + front) % items.length];
        }
        items = newData;
        front = 0;//头指针回到最前
        rear = size;//尾指针去到队列最后一个元素的后一个位置
    }
}
