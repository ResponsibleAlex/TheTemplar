package TheTemplar.relics;

import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static TheTemplar.TemplarMod.makeRelicOutlinePath;
import static TheTemplar.TemplarMod.makeRelicPath;

public class RunedArmor extends CustomRelic {
    public static final String ID = TemplarMod.makeID("RunedArmor");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RunedArmor.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RunedArmor.png"));

    public RunedArmor() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.HEAVY);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RunedArmor();
    }
}
