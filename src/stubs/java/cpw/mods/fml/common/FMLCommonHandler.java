package cpw.mods.fml.common;

import cpw.mods.fml.common.eventhandler.EventBus;

public class FMLCommonHandler {
    private static final FMLCommonHandler INSTANCE = new FMLCommonHandler();

    public static FMLCommonHandler instance() {
        return INSTANCE;
    }

    public EventBus bus() {
        return new EventBus();
    }
}
