package TheTemplar.relics;

import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static TheTemplar.TemplarMod.makeRelicOutlinePath;
import static TheTemplar.TemplarMod.makeRelicPath;

public class FightersRing extends CustomRelic {
    public static final String ID = TemplarMod.makeID("FightersRing");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("FightersRing.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("FightersRing.png"));

    private static final int VIGOR = 2;

    public FightersRing() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new RelicAboveCreatureAction(p, this));
        addToBot(new ApplyPowerAction(p, p, new VigorPower(p, VIGOR), VIGOR));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FightersRing();
    }
}
