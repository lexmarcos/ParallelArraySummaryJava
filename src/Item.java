public class Item {
    private int id;
    private double total;
    private int grupo;

    public Item(int id, double total, int grupo) {
        this.id = id;
        this.total = total;
        this.grupo = grupo;
    }

    public int getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public int getGrupo() {
        return grupo;
    }
}
