package rocks.ninjachen.hbridgek.events;

import rocks.ninjachen.hbridgek.ui.MainActivity;

/**
 * Pub/Sub event used to communicate between fragment and activity.
 * Subscription occurs in the {@link MainActivity}
 */
public class NavItemSelectedEvent {
    private int itemPosition;

    public NavItemSelectedEvent(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public int getItemPosition() {
        return itemPosition;
    }
}
