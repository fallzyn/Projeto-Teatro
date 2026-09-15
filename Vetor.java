public class Vetor<T> {

    private T[] A;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public Vetor(int capacity) {
        A = (T[]) new Object[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T get(int i) throws Exception {
        if (i < 0 || i >= size) {
            throw new Exception("Posição inexistente");
        }
        return A[i];
    }

    public void set(int i, T n) throws Exception {
        if (i < 0 || i >= size) {
            throw new Exception("Posição inexistente");
        }
        A[i] = n;
    }

    public void add(int i, T n) throws Exception {
        if (size == A.length) {
            throw new Exception("Lista cheia");
        }
        if (i < 0 || i > size) {
            throw new Exception("Posição inválida para inserir");
        }

        for (int x = size; x > i; x--) {
            A[x] = A[x - 1];
        }
        A[i] = n;
        size++;
    }

    public void remove(int i) throws Exception {
        if (i < 0 || i >= size) {
            throw new Exception("Posição inexistente");
        }
        for (int x = i; x < size - 1; x++) {
            A[x] = A[x + 1];
        }
        A[size - 1] = null;
        size--;
    }

    public int search(T n) {
        for (int i = 0; i < size; i++) {
            if (A[i].equals(n)) {
                return i;
            }
        }
        return -1;
    }

    public void mostraLista() throws Exception {
        for (int i = 0; i < size; i++) {
            System.out.println(A[i]);
        }
    }
}
