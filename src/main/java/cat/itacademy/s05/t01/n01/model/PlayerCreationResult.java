package cat.itacademy.s05.t01.n01.model;

public class PlayerCreationResult {
    private final Player player;
    private final boolean wasCreated;

    public PlayerCreationResult(Player player, boolean wasCreated) {
        this.player = player;
        this.wasCreated = wasCreated;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean wasCreated() {
        return wasCreated;
    }
}
