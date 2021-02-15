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
        this.duration = this.startingDuration = DURATION;
        this.color = Color.WHITE.cpy();
        this.scale = Settings.scale;
        this.rotation = 0;
        this.img = TEXTURES.get(effect);

        // load width, height, x, y based on effect
        switch (effect) {
            case HolyWeapons.Sword:
                this.width = 974;
                this.height = 106;
                this.x = (float)Settings.WIDTH * 0.7F - this.width / 2.0F;
                this.y = AbstractDungeon.floorY + 100.0F * Settings.scale - this.height / 2.0F;
                this.originY = this.height / 2.0F;
                if (playSound) {
                    CardCrawlGame.sound.play("ATTACK_HEAVY", 0.2f);
                }
                break;
            case HolyWeapons.Hammer:
                this.width = 256;
                this.height = 256;
                this.x = x - this.width / 2.0F;
                this.y = y - this.height / 2.0F;
                this.originY = this.height / 2.0F;
                if (playSound) {
                    CardCrawlGame.sound.play("BLUNT_HEAVY", 0.3f);
                    CardCrawlGame.sound.play("BLUNT_HEAVY", 0.2f);
                }
                break;
            case HolyWeapons.Torch:
                this.width = 198;
                this.height = 188;
                this.x = x - this.width / 2.0F;
                this.y = y - this.height / 2.0F;
                this.originY = this.height / 2.0F;
                if (playSound) {
                    CardCrawlGame.sound.play("ATTACK_FIRE", 0.3f);
                    CardCrawlGame.sound.play("ATTACK_FIRE", 0.2f);
                }
                break;
            case HolyWeapons.Book:
                this.width = 400;
                this.height = 834;
                this.x = x - this.width / 2.0F;
                this.y = y;
                this.originY = 0;
                if (playSound) {
                    CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT", 0.3f);
                }
                break;
            case "StaggeringAwe":
                this.width = 600;
                this.height = 834;
                this.x = x - this.width / 2.0F;
                this.y = y;
                this.originY = 0;
                if (playSound) {
                    CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT", 0.3f);
                }
                break;
            default:
                this.width = 0;
                this.height = 0;
                this.originY = 0;
                break;
        }
    }

    public void render(SpriteBatch sb) {
        if (this.img != null) {
            sb.setColor(this.color);
            sb.draw(this.img,
                    this.x, this.y,
                    this.width / 2.0F, this.originY,
                    this.width, this.height,
                    this.scale, this.scale, this.rotation,
                    0, 0,
                    (int)this.width, (int)this.height,
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
