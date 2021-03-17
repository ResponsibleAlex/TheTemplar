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

import static TheTemplar.TemplarMod.makeVfxPath;

public class HammerParticleEffect extends AbstractGameEffect {
    public final static ArrayList<Texture> TEXTURES = InitTextures();

    private final float x;
    private final float y;
    private final Texture img;

    private final static float DURATION = 0.2f;

    public HammerParticleEffect(int textureIndex) {
        x = AbstractDungeon.player.drawX
                + 60.0f * scale
                + MathUtils.random(0f, 20f) * scale;

        y = AbstractDungeon.player.drawY
                + 20.0f * scale
                + MathUtils.random(0f, 40f) * scale;

        img = TEXTURES.get(textureIndex);

        color = Color.WHITE.cpy();

        duration = startingDuration = DURATION;
    }

    @Override
    public void update() {
        duration -= Gdx.graphics.getDeltaTime();
        if (duration <= 0) {
            duration = 0;
            isDone = true;
        }

        color.a = Interpolation.linear.apply(0f, 0.85f, duration / startingDuration);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        draw(sb);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void draw(SpriteBatch sb) {
        sb.draw(img,
                x,
                y,
                45f, 45f,
                90f, 90f,
                Settings.scale,
                Settings.scale,
                0, 0, 0,
                90, 90,
                false, false);
    }

    @Override
    public void dispose() {
        img.dispose();
    }

    private static ArrayList<Texture> InitTextures() {
        ArrayList<Texture> i = new ArrayList<>();
        i.add(ImageMaster.loadImage(makeVfxPath("HammerEffect1.png")));
        i.add(ImageMaster.loadImage(makeVfxPath("HammerEffect2.png")));
        i.add(ImageMaster.loadImage(makeVfxPath("HammerEffect3.png")));
        i.add(ImageMaster.loadImage(makeVfxPath("HammerEffect4.png")));
        i.add(ImageMaster.loadImage(makeVfxPath("HammerEffect5.png")));
        i.add(ImageMaster.loadImage(makeVfxPath("HammerEffect6.png")));
        return i;
    }
}
