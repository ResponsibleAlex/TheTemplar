package TheTemplar.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ImpactSparkEffect;

import static TheTemplar.TemplarMod.makeVfxPath;

public class HolyWeaponEquipEffect extends AbstractGameEffect {
    public static final String IMG = makeVfxPath(HolyWeaponEquipEffect.class.getSimpleName());
    private float x;
    private final float startX;
    private final float y;
    private float width;
    private final float height;
    private final Texture img;
    private float fadeDuration = 0f;
    private static final float fadeDurationMax = 0.4f;

    public HolyWeaponEquipEffect() {
        img = ImageMaster.loadImage(IMG + ".png");

        startX = x = AbstractDungeon.player.drawX - 90.f * Settings.scale;
        y = AbstractDungeon.player.drawY - 20.0f * Settings.scale;
        height = Settings.HEIGHT + 15.0f * Settings.scale;

        color = Color.WHITE.cpy();
        duration = startingDuration = 0.5F;
    }

    public void update() {
        if (duration == startingDuration) {
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.MED, false);

            for(int i = 0; i < 15; ++i) {
                AbstractDungeon.topLevelEffectsQueue.add(new ImpactSparkEffect(x + MathUtils.random(-20.0F, 20.0F) * Settings.scale + 125.0F * Settings.scale, y + MathUtils.random(-20.0F, 20.0F) * Settings.scale));
            }
        }

        if (duration > 0f) {
            duration -= Gdx.graphics.getDeltaTime();
            if (duration <= 0.0F) {
                duration = 0f;
                fadeDuration = fadeDurationMax;

                //TODO floaty particles
            }

            width = 750f * Settings.scale * (0.5f - duration);
            x = startX - width / 4.0f;
        } else if (fadeDuration > 0f) {
            fadeDuration -= Gdx.graphics.getDeltaTime();
            if (fadeDuration <= 0f) {
                isDone = true;
            }

            color.a = Interpolation.pow2Out.apply(0.0F, fadeDurationMax, fadeDuration);
        }
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        sb.setColor(color);

        sb.draw(img,
                x, y,
                150f, 0f,
                width, height,
                Settings.scale, Settings.scale,
                0, 0, 0,
                300, 1,
                false, false);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void dispose() {
    }
}
