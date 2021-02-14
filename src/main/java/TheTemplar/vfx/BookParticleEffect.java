package TheTemplar.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

import static TheTemplar.TemplarMod.makeGlyphPath;

public class BookParticleEffect extends AbstractGameEffect {
    private final static ArrayList<Texture> TEXTURES = InitTextures();

    private final float x;
    private final float startY;
    private final float endY;
    private float y;

    private final Texture img;

    public BookParticleEffect() {
        x = AbstractDungeon.player.drawX
                + MathUtils.random(-20f, 50f) * scale;

        startY = y = AbstractDungeon.player.drawY
                + 100.0f * scale
                + MathUtils.random(0f, 15f) * scale;
        endY = startY - MathUtils.random(40f, 100f);

        img = RandomGlyph();
        color = new Color(MathUtils.random(0f, 1f),
                MathUtils.random(0f, 1f),
                MathUtils.random(0f, 1f),
                1f);

        duration = startingDuration = 1.0f;
    }

    @Override
    public void update() {
        duration -= Gdx.graphics.getDeltaTime();
        if (duration <= 0) {
            duration = 0;
            isDone = true;
        }

        y = Interpolation.linear.apply(endY, startY, duration / startingDuration);
        color.a = Interpolation.linear.apply(0f, 1f, duration / startingDuration);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        draw(sb, 1.01f);
        draw(sb, 1.0f);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void draw(SpriteBatch sb, float scaleMultiplier) {
        sb.draw(img,
                x,
                y,
                65f, 65f,
                45f, 45f,
                Settings.scale * scaleMultiplier,
                Settings.scale * scaleMultiplier,
                0, 0, 0,
                130, 130,
                false, false);
    }

    @Override
    public void dispose() {

    }

    private static Texture RandomGlyph() {
        return TEXTURES.get(MathUtils.random(TEXTURES.size() - 1));
    }

    private static ArrayList<Texture> InitTextures() {
        ArrayList<Texture> i = new ArrayList<>();
        i.add(ImageMaster.loadImage(makeGlyphPath("JusticeGlow.png")));
        i.add(ImageMaster.loadImage(makeGlyphPath("FortitudeGlow.png")));
        i.add(ImageMaster.loadImage(makeGlyphPath("ValorGlow.png")));
        i.add(ImageMaster.loadImage(makeGlyphPath("ZealGlow.png")));
        i.add(ImageMaster.loadImage(makeGlyphPath("CharityGlow.png")));
        return i;
    }
}
