import dev.midnightcoder.engine.core.Engine;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.assets.AssetManager;

void main() {
    var rpg = new MidnightRPG();
    // TODO: Load Splash Screen while assets load
    var am = AssetManager.getInstance();
        am.loadTiles();
    Engine.start(rpg);
}