public class LinkedListDeque<T> {
    private Node sentinel;
    private int size;
    public LinkedListDeque() {
        //哨兵头节点不储存value.
        sentinel = new Node(null,null ,null);
        //让prev和next都指向sentinel.
        //初始化链表
        sentinel.prev = sentinel.next = sentinel;
        size = 0;
    }
    //这个是内部类nest class.
    public class Node {
        public Node prev;
        public T item;
        public Node next;

        public Node(T i, Node n, Node m) {
            item = i;
            prev = n;
            next = m;
        }
    }
    /** Adds an item of type T to the front of the deque. */
    /** 此方法可以当作插入insert用，非常similar */
    public void addFirst(T item){
        //new一个Node，同时它的prev域指向头节点，next域指向头节点的后一个节点（你可以假设它有后一个节点）
        //因为是循环链表，所以它没有后一个节点的话就是指向头节点
        Node p = new Node(item, sentinel, sentinel.next);
        //把新节点p赋给头节点的下一个节点的prev域
        //注意这一句一定要在下一句之前，不然会覆盖掉头节点的下一个节点
        sentinel.next.prev = p;
        //最后把新节点赋给头结点的next域,finish!!
        sentinel.next = p;
        //此size可以影响到方法外的size，我还没搞清楚为什么？
        size++;
    }
    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item){
        //同样是先new一个p，同时把它的前后域给指定好
        //这里的next域肯定是指向头节点的，prev域怎么办呢
        //注意！prev域是指向sentinel.prev！！因为此时sentinel.prev保存着此链表的最后节点的引用！！
        Node p = new Node(item,sentinel.prev,sentinel);
        //然后把最后一个节点（未添加前的最后）的next域指向p
        sentinel.prev.next = p;
        //最后让头节点的prev域指向新的最后的节点（即新节点p）,KO!!
        sentinel.prev = p;
        size++;
    }//写完后感受到了2.2中的Invariants一致性原则
    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }
    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }
    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque(){
        //注意循环链表的尾节点的next域只是指向了头节点而非为空
        //因此此临界点是判断其不指向头节点而非空
        for (Node i = sentinel.next; i != sentinel; i = i.next) {
            System.out.print(i.item + " ");
        }
    }
    /**
     * Removes and returns the item at the front of the deque. If no such item
     * exists, returns null.
     */
    public T removeFirst(){
        if (isEmpty()) {
            return null;
        }
        //先把要删掉的item信息储存下来，不然等会儿找不到了
        T front = sentinel.next.item;
        //把第二个节点（就是首元节点的后一个节点）赋给头节点的NEXT域
        //使得头节点NEXT域指向了第二个节点
        sentinel.next = sentinel.next.next;
        //然后把头节点的引用赋给第二个节点的prev域即可
        //注意！此时的sentinel.next是第二个节点了而非要被删去的那个，因为上面的语句已经覆盖了它
        //使得第二个节点指向了头节点，首元节点无被指向的引用而被回收
        sentinel.next.prev = sentinel;
        size--;
        return front;
    }
    /**
     * Removes and returns the item at the back of the deque. If no such item
     * exists, returns null.
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T last = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return last;
    }
    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item,
     * and so forth. If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index){
        Node p = sentinel.next;
        int j = 0;
        while(p != sentinel && j<index){
            p = p.next;
            j++;
        }
        if(p == sentinel || j>index){
            return null;
        }
        else return p.item;
    }

}
