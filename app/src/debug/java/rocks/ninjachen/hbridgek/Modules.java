package rocks.ninjachen.hbridgek;


import rocks.ninjachen.hbridgek.AndroidModule;
import rocks.ninjachen.hbridgek.BootstrapModule;

final class Modules {
    static Object[] list() {
        return new Object[] {
                new AndroidModule(),
                new BootstrapModule()
        };
    }

    private Modules() {
        // No instances.
    }
}
