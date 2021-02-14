package TheTemplar.vfx;

import TheTemplar.glyphs.AbstractGlyph;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class GlyphAboveCreatureEffect extends AbstractGameEffect {
    private final Texture img;
    private final float x;
    private final float y;
    private static final float scaleMin = 1.1f;
    private static final float scaleMax = 1.7f;

    protected static final float SIZE = 130.0F;
    protected static final float HALF_SIZE = SIZE / 2.0F;
    private final Color glowColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);

    public GlyphAboveCreatureEffect (AbstractCreature c, AbstractGlyph g) {
        x = c.drawX;
        y = c.drawY + c.hb_h;

        img = g.glowImg;

        duration = startingDuration = Settings.ACTION_DUR_MED;
        scale = scaleMin;
    }

    @Override
    public void update() {
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0) {
            isDone = true;
        }

        scale = Interpolation.linear.apply(scaleMax, scaleMin, duration / startingDuration);
        glowColor.a = Interpolation.linear.apply(0f, 1.0f, duration / startingDuration);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(glowColor);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

        sb.draw(img,
                x - HALF_SIZE,
                y - HALF_SIZE,
                HALF_SIZE, HALF_SIZE,
                SIZE, SIZE,
                Settings.scale * scale,
                Settings.scale * scale,
                0, 0, 0,
                (int)SIZE, (int)SIZE,
                false, false);

        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void dispose() {

    }
}
