package TheTemplar.vfx;

import TheTemplar.variables.HolyWeapons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.HashMap;

import static TheTemplar.TemplarMod.makeVfxPath;

public class FlashCustomAttackEffect extends AbstractGameEffect {
    private final static HashMap<String, Texture> TEXTURES = InitTextures();

    public final Texture img;
    private final float width;
    private final float height;
    private float x;
    private float y;
    private final float originY;
    public static final float DURATION = 0.6F;

    public FlashCustomAttackEffect(float x, float y, String effect, boolean playSound) {
        duration = startingDuration = DURATION;
        color = Color.WHITE.cpy();
        scale = Settings.scale;
        rotation = 0;
        img = TEXTURES.get(effect);

        // load width, height, x, y based on effect
        switch (effect) {
            case HolyWeapons.Sword:
                width = 974;
                height = 106;
                this.x = (float)Settings.WIDTH * 0.7F - width / 2.0F;
                this.y = AbstractDungeon.floorY + 100.0F * Settings.scale - height / 2.0F;
                originY = height / 2.0F;
                if (playSound) {
                    CardCrawlGame.sound.play("ATTACK_HEAVY", 0.2f);
                }
                break;
            case HolyWeapons.Hammer:
                width = 256;
                height = 256;
                this.x = x - width / 2.0F;
                this.y = y - height / 2.0F;
                originY = height / 2.0F;
                if (playSound) {
                    CardCrawlGame.sound.play("BLUNT_HEAVY", 0.3f);
                    CardCrawlGame.sound.play("BLUNT_HEAVY", 0.2f);
                }
                break;
            case HolyWeapons.Torch:
                width = 198;
                height = 188;
                this.x = x - width / 2.0F;
                this.y = y - height / 2.0F;
                originY = height / 2.0F;
                if (playSound) {
                    CardCrawlGame.sound.play("ATTACK_FIRE", 0.3f);
                    CardCrawlGame.sound.play("ATTACK_FIRE", 0.2f);
                }
                break;
            case HolyWeapons.Book:
                width = 400;
                height = 834;
                this.x = x - width / 2.0F;
                this.y = y;
                originY = 0;
                if (playSound) {
                    CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT", 0.3f);
                }
                break;
            case "StaggeringAwe":
                width = 600;
                height = 834;
                this.x = x - width / 2.0F;
                this.y = y;
                originY = 0;
                if (playSound) {
                    CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT", 0.3f);
                }
                break;
            default:
                width = 0;
                height = 0;
                originY = 0;
                break;
        }
    }

    public void render(SpriteBatch sb) {
        if (img != null) {
            sb.setColor(color);
            sb.draw(img,
                    x, y,
                    width / 2.0F, originY,
                    width, height,
                    scale, scale, rotation,
                    0, 0,
                    (int) width, (int) height,
                    false, false);
        }

    }

    public void dispose() {
    }

    private static HashMap<String, Texture> InitTextures() {
        HashMap<String, Texture> i = new HashMap<>();
        i.put(HolyWeapons.Book, ImageMaster.loadImage(makeVfxPath("AttackBook.png")));
        //i.put(HolyWeapons.Aegis, ImageMaster.loadImage(makeVfxPath("AttackAegis.png")));
        i.put(HolyWeapons.Hammer, ImageMaster.loadImage(makeVfxPath("AttackHammer.png")));
        i.put(HolyWeapons.Sword, ImageMaster.loadImage(makeVfxPath("AttackSword.png")));
        i.put(HolyWeapons.Torch, ImageMaster.loadImage(makeVfxPath("AttackTorch.png")));
        i.put("StaggeringAwe", ImageMaster.loadImage(makeVfxPath("StaggeringAwe.png")));
        return i;
    }
}
