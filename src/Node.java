class Node {
    String question;
    String answer;
    Node next, previous;

    public Node(String q, String a) {
        this.question = q;
        this.answer = a;
    }

    public Node(String q) {
        this.question = q;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}