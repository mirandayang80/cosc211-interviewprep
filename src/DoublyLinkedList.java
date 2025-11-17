class DoublyLinkedList {
    private Node first;
    private Node last;


    public DoublyLinkedList() {

    }

    public int size() {
        Node t = first;

        //Counter for the size
        int s = 0;

        //Iterate through whole list
        while (t != null) {
            //Update counter
            s += 1;
            t = t.next;
        }
        return s;
    }

    public void addQ(String toAdd) {
        Node l = new Node(toAdd);

        //Adding new node to the end of the list

        //If nothing is on the list
        if (this.last == null) {
            last = l;
            first = l;
        }
        //Add after the previous last node
        else {
            Node temp = this.last;
            this.last.next = l;
            //Update new last node
            l.previous = temp;
            this.last = l;
        }


    }

    public void addQA(String q, String a) {

        //find the most recent node with the question  (since questions can get repeated)
        //add the answer to that node
        Node addToThis = find(q);

        if (addToThis != null) {
            addToThis.answer = a;
        }
    }

    public Node find(String q) {
        //Temp node to iterate through the list
        Node t = last;

        while (t != null) {
            //Return true if found the value
            if (t.question.equals(q)) return t;
            t = t.previous;
        }
        return null;
    }

    public void remove(String toRemove) {
        //Remove the whole node, not just the string

        //Temp node to iterate through the list, (going from last to front)
        Node t = last;

        while (t != null) {
            //if we find the question or answer we're removing
            if (toRemove.equals(t.question) || toRemove.equals(t.answer)) {

                //if there is a previous node, adjust the pointer
                if (t.previous != null) {
                    t.previous.next = t.next;
                }
                //if removing the first thing in the list
                else {
                    //update what's first
                    first = t.next;
                }

                //adjust the pointer of the next node
                if (t.next != null) {
                    t.next.previous = t.previous;
                }
                // If removing the last node
                else {
                    //update last
                    last = t.previous;
                }
                break;
            }

            //iterate
            t = t.previous;
        }


    }

    public Node getFirst() {
        return first;
    }

    public void print() {
        Node current = first;
        while (current != null) {
            System.out.println(current.getQuestion() + " " + current.getAnswer());
            current = current.next;
        }
    }


}