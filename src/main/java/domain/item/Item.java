package domain.item;

/**
 * Created by Gilles on 13/05/2016.
 */
public class Item {
    private final int id;
    private int amount;

    public Item(int id) {
        this.id = id;
    }

    public Item(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
