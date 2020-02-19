package system.model.entity;

public class Participant {
    private int id;
    private Human fromHuman;
    private Human forHuman;

    public Participant(int id, Human fromHuman, Human forHuman) {
        this.id = id;
        this.fromHuman = fromHuman;
        this.forHuman = forHuman;
    }

    public Participant(Human fromHuman, Human forHuman) {
        this.fromHuman = fromHuman;
        this.forHuman = forHuman;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Human getFromHuman() {
        return fromHuman;
    }

    public void setFromHuman(Human fromHuman) {
        this.fromHuman = fromHuman;
    }

    public Human getForHuman() {
        return forHuman;
    }

    public void setForHuman(Human forHuman) {
        this.forHuman = forHuman;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "fromHuman=" + fromHuman.getName() +
                ", forHuman=" + forHuman.getName() +
                '}';
    }
}
