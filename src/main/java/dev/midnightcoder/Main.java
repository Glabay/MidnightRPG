import dev.midnightcoder.engine.core.Engine;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.assets.AssetManager;

void main() {
    // TODO: Load Splash Screen while assets load
    var am = AssetManager.getInstance();
        am.loadTiles();
    Engine.start(MidnightRPG.getInstance());
}