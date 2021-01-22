package TheTemplar.vfx;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BookEffect {
    private static float timer = 0f;

    private static final float BOOK_TIME = 0.2f;

    public static void update() {
        timer += Gdx.graphics.getDeltaTime();
        if (timer >= BOOK_TIME) {
            AbstractDungeon.effectsQueue.add(new BookParticleEffect());
            timer = 0f;
        }
    }
}
