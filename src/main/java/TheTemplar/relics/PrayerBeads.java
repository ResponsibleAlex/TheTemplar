package TheTemplar.relics;

import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static TheTemplar.TemplarMod.makeRelicOutlinePath;
import static TheTemplar.TemplarMod.makeRelicPath;

public class PrayerBeads extends CustomRelic {
    public static final String ID = TemplarMod.makeID("PrayerBeads");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PrayerBeads.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PrayerBeads.png"));

    public PrayerBeads() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PrayerBeads();
    }
}
