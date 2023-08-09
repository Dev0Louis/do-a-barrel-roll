package nl.enjarai.doabarrelroll;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import nl.enjarai.cicada.api.util.ProperLogger;
import nl.enjarai.doabarrelroll.api.event.ServerEvents;
import nl.enjarai.doabarrelroll.config.LimitedModConfigServer;
import nl.enjarai.doabarrelroll.config.ModConfigServer;
import nl.enjarai.doabarrelroll.net.HandshakeServer;
import nl.enjarai.doabarrelroll.net.ServerConfigHolder;
import org.slf4j.Logger;

public class DoABarrelRoll {
    public static final String MODID = "do_a_barrel_roll";
    public static final Logger LOGGER = ProperLogger.getLogger(MODID);

    public static ServerConfigHolder<ModConfigServer> CONFIG_HOLDER;
    public static HandshakeServer<ModConfigServer> HANDSHAKE_SERVER;
    public static final Identifier HANDSHAKE_CHANNEL = id("handshake");
    public static final Identifier SERVER_CONFIG_UPDATE_CHANNEL = id("server_config_update");
    public static final Identifier ROLL_CHANNEL = id("player_roll");

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }

    public static void init() {
        var configFile = FabricLoader.getInstance().getConfigDir().resolve(DoABarrelRoll.MODID + "-server.json");

        CONFIG_HOLDER = new ServerConfigHolder<>(configFile,
                ModConfigServer.CODEC, ModConfigServer.DEFAULT, ServerEvents::updateServerConfig);
        HANDSHAKE_SERVER = new HandshakeServer<>(
                CONFIG_HOLDER, player -> !ModConfigServer.canModify(player),
                ModConfigServer.CODEC, LimitedModConfigServer.getCodec()
        );
    }
}
