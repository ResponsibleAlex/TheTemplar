package TheTemplar.glyphs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

import java.util.ArrayList;
import java.util.HashMap;

import static TheTemplar.TemplarMod.makeGlyphPath;

public abstract class AbstractGlyph {
    private static AbstractGlyph LeftGlyph;
    private static AbstractGlyph RightGlyph;

    public String name;
    public String description;
    protected ArrayList<PowerTip> tips = new ArrayList<>();
    public float cX = 0.0F;
    public float cY = 0.0F;
    protected Color c;
    protected Color shineColor;
    protected Color glowColor;

    protected static final float SIZE = 130.0F;
    protected static final float PCT_35_SIZE = SIZE * 0.35F;
    protected static final float HALF_SIZE = SIZE / 2.0F;
    protected static final float THREE_QTR_SIZE = SIZE * 3.0F / 4.0F;

    public Hitbox hb;
    protected AbstractPlayer p;
    protected Texture img;
    public Texture glowImg;

    protected float scaleOffset;
    public static final float INSCRIBE_TIME = 0.5F;
    protected float inscribeAnimTimer;

    protected static float pulseScale;
    protected static final float PULSE_TIME = 1.0F;
    protected float pulseAnimTimer;

    protected float fadeAlpha;
    public static final float FADE_TIME = 0.7F;
    protected float fadeAnimTimer;

    protected boolean isLeft = false;
    protected boolean isRight = false;

    protected static final HashMap<String, Texture> textures = InitTextures();
    protected static final HashMap<String, Texture> glowTextures = InitGlowTextures();

    public AbstractGlyph(String glyphName, String glyphDesc) {
        this.img = textures.get(glyphName);
        this.glowImg = glowTextures.get(glyphName);
        this.name = glyphName;
        this.description = glyphDesc;

        this.c = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        this.shineColor = new Color(1.0F, 1.0F, 1.0F, this.c.a / 3.0F);
        this.glowColor = new Color(1.0F, 1.0F, 1.0F, 0.7F);

        this.hb = new Hitbox(SIZE * Settings.scale, SIZE * Settings.scale);
        this.p = AbstractDungeon.player;

        this.inscribeAnimTimer = INSCRIBE_TIME;
        this.pulseAnimTimer = 0.0F;
        this.fadeAnimTimer = 0.0F;
    }

    public static AbstractGlyph getRandomGlyph(boolean useCardRng) {
        ArrayList<AbstractGlyph> glyphs = new ArrayList<>();
        glyphs.add(new Justice());
        glyphs.add(new Fortitude());
        glyphs.add(new Valor());
        glyphs.add(new Charity());
        glyphs.add(new Zeal());
        return useCardRng ? glyphs.get(AbstractDungeon.cardRandomRng.random(glyphs.size() - 1)) : glyphs.get(MathUtils.random(glyphs.size() - 1));
    }

    public static boolean noneInscribed() {
        return LeftGlyph == null;
    }

    public static void inscribeLeft(AbstractGlyph glyph) {
        glyph.isLeft = true;
        LeftGlyph = glyph;
    }

    public static void inscribeRight(AbstractGlyph glyph) {
        glyph.isRight = true;
        RightGlyph = glyph;
    }

    protected void startFadeAnimation() {
        this.fadeAnimTimer = FADE_TIME;
        this.fadeAlpha = 1.0F;
    }

    public abstract void evokeSingle();
    public abstract void evokeDouble();

    public void update() {
        if (isLeft || isRight) {
            float sign = 1.1F;
            if (isLeft) {
                sign = -1.0F;
            }
            float xOffset = THREE_QTR_SIZE * sign * Settings.scale;

            cX = p.drawX + xOffset * Settings.scale;
            cY = p.drawY + p.hb_h +
                    150.0F * Settings.scale;

            hb.resize(SIZE * Settings.scale, SIZE * Settings.scale);
            hb.move(cX, cY);
            this.hb.update();

            if (this.hb.hovered) {
                TipHelper.renderGenericTip(
                        this.cX + HALF_SIZE * Settings.scale,
                        this.cY + PCT_35_SIZE * Settings.scale,
                        this.name, this.description);
            }
        }
    }

    public void updateAnimation() {
        if (this.inscribeAnimTimer > 0.0F) {

            this.inscribeAnimTimer -= Gdx.graphics.getDeltaTime();
            if (this.inscribeAnimTimer < 0.0F) {
                this.inscribeAnimTimer = 0.0F;
            }

            this.scaleOffset = 1 + (this.inscribeAnimTimer / INSCRIBE_TIME * 1.8F);

        } else {

            if (this.isLeft) {
                this.pulseAnimTimer += Gdx.graphics.getDeltaTime();
                if (this.pulseAnimTimer >= PULSE_TIME * 2) {
                    this.pulseAnimTimer -= PULSE_TIME * 2;
                }

                pulseScale = Interpolation.sine.apply(1.0F, 1.09F, this.pulseAnimTimer / PULSE_TIME);
            }
        }

        if (this.fadeAnimTimer > 0.0F) {

            this.fadeAnimTimer -= Gdx.graphics.getDeltaTime();
            if (this.fadeAnimTimer < 0.0F) {
                this.fadeAnimTimer = 0.0F;
            }

            this.fadeAlpha = this.fadeAnimTimer / FADE_TIME;
            this.glowColor.a = fadeAlpha;
            this.c.a = Math.max(0.0F, fadeAlpha - 0.5F);
        }

    }

    public void render(SpriteBatch sb) {
        if (AbstractDungeon.getCurrMapNode() != null
                && (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT
                || AbstractDungeon.getCurrRoom() instanceof MonsterRoom)
                && !p.isDead
        ) {

            if (this.fadeAnimTimer > 0.0F) {

                sb.setColor(this.glowColor);
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
                this.draw(this.glowImg, sb, pulseScale);

                if (this.c.a > 0.0F) {
                    sb.setColor(this.c);
                    this.draw(this.img, sb, this.scaleOffset);
                }

                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            } else {

                if (pulseScale > 1.0F) {
                    sb.setColor(this.glowColor);
                    sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
                    this.draw(this.glowImg, sb, pulseScale);
                    sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                }

                sb.setColor(this.c);
                this.draw(this.img, sb, this.scaleOffset);
            }

            if (this.inscribeAnimTimer > 0.0F) {
                sb.setColor(this.shineColor);
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
                this.draw(this.img, sb, 1.2F * this.scaleOffset);
                this.draw(this.img, sb, 1.5F * this.scaleOffset);
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            }

            this.hb.render(sb);
        }
    }
    private void draw(Texture texture, SpriteBatch sb, float scaleMultiplier) {
        sb.draw(texture,
                this.cX - HALF_SIZE * Settings.scale,
                this.cY - HALF_SIZE * Settings.scale,
                HALF_SIZE, HALF_SIZE,
                SIZE, SIZE,
                Settings.scale * scaleMultiplier,
                Settings.scale * scaleMultiplier,
                0, 0, 0,
                (int)SIZE, (int)SIZE,
                false, false);
    }

    public static void evokeGlyphs() {
        if (LeftGlyph.name.equals(RightGlyph.name)) {
            LeftGlyph.evokeDouble();
        } else {
            LeftGlyph.evokeSingle();
            RightGlyph.evokeSingle();
        }
    }

    public static void renderGlyphs(SpriteBatch sb) {
        if (LeftGlyph != null) {
            LeftGlyph.render(sb);
        }
        if (RightGlyph != null) {
            RightGlyph.render(sb);
        }
    }

    public static void updateGlyphs() {
        if (LeftGlyph != null) {
            LeftGlyph.update();
        }
        if (RightGlyph != null) {
            RightGlyph.update();
        }
    }

    public static void updateGlyphsAnimation() {
        if (LeftGlyph != null) {
            LeftGlyph.updateAnimation();
        }
        if (RightGlyph != null) {
            RightGlyph.updateAnimation();
        }
    }

    public static void fadeGlyphsAnimation() {
        if (LeftGlyph != null) {
            LeftGlyph.startFadeAnimation();
        }
        if (RightGlyph != null) {
            RightGlyph.startFadeAnimation();
        }
    }

    public static void removeGlyphs() {
        LeftGlyph = null;
        RightGlyph = null;
    }

    protected static HashMap<String, Texture> InitTextures() {
        HashMap<String, Texture> map = new HashMap<>();
        map.put("Justice", ImageMaster.loadImage(makeGlyphPath("Justice.png")));
        map.put("Fortitude", ImageMaster.loadImage(makeGlyphPath("Fortitude.png")));
        map.put("Valor", ImageMaster.loadImage(makeGlyphPath("Valor.png")));
        map.put("Zeal", ImageMaster.loadImage(makeGlyphPath("Zeal.png")));
        map.put("Charity", ImageMaster.loadImage(makeGlyphPath("Charity.png")));
        return map;
    }

    protected static HashMap<String, Texture> InitGlowTextures() {
        HashMap<String, Texture> map = new HashMap<>();
        map.put("Justice", ImageMaster.loadImage(makeGlyphPath("JusticeGlow.png")));
        map.put("Fortitude", ImageMaster.loadImage(makeGlyphPath("FortitudeGlow.png")));
        map.put("Valor", ImageMaster.loadImage(makeGlyphPath("ValorGlow.png")));
        map.put("Zeal", ImageMaster.loadImage(makeGlyphPath("ZealGlow.png")));
        map.put("Charity", ImageMaster.loadImage(makeGlyphPath("CharityGlow.png")));
        return map;
    }

    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
}
