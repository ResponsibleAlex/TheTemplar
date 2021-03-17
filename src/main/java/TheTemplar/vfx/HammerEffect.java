package TheTemplar.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HammerEffect {
    private static float timer = 0f;

    private static final float HAMMER_TIME = 0.2f;
    private static final float RESET_MAX = HAMMER_TIME * 0.8f;
    private static int prev_index = 0;

    public static void update() {
        timer += Gdx.graphics.getDeltaTime();
        if (timer >= HAMMER_TIME) {

            int i;
            do {
                i = MathUtils.random(HammerParticleEffect.TEXTURES.size() - 1);
            } while (i == prev_index);

            prev_index = i;

            AbstractDungeon.effectsQueue.add(new HammerParticleEffect(i));
            timer = MathUtils.random(0f, RESET_MAX);
        }
    }
}
