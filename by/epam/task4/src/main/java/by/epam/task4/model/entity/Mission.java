package by.epam.task4.model.entity;

public class Mission {
    private int id;
    private Participant participant;
    private Action action;
    private Plant plant;
    private boolean status;

    public Mission(Participant participant, Action action, Plant plant, boolean status) {
        this.participant = participant;
        this.action = action;
        this.plant = plant;
        this.status = status;
    }

    public Mission(Participant participant, Action action, Plant plant) {
        this.participant = participant;
        this.action = action;
        this.plant = plant;
    }

    public Mission(int id, Participant participant, Action action, Plant plant, boolean status) {
        this.id = id;
        this.participant = participant;
        this.action = action;
        this.plant = plant;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "participant=" + participant +
                ", action=" + action +
                ", plant=" + plant +
                ", status=" + status +
                '}';
    }

    // override methods equals and hashCode
}
