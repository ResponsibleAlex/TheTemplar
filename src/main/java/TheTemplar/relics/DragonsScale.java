package TheTemplar.relics;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GainBulwarkAction;
import TheTemplar.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static TheTemplar.TemplarMod.makeRelicOutlinePath;
import static TheTemplar.TemplarMod.makeRelicPath;

public class DragonsScale extends CustomRelic {
    public static final String ID = TemplarMod.makeID("DragonsScale");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DragonsScale.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DragonsScale.png"));

    private static final int BULWARK = 1;

    public DragonsScale() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        this.flash();
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new RelicAboveCreatureAction(p, this));
        this.addToBot(new GainBulwarkAction(BULWARK));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DragonsScale();
    }
}
