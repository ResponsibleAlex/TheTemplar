package TheTemplar.relics;

import TheTemplar.TemplarMod;
import TheTemplar.actions.GlyphInscribeRandomAction;
import TheTemplar.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static TheTemplar.TemplarMod.makeRelicOutlinePath;
import static TheTemplar.TemplarMod.makeRelicPath;

public class Apocrypha extends CustomRelic {
    public static final String ID = TemplarMod.makeID("Apocrypha");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Apocrypha.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Apocrypha.png"));

    public Apocrypha() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new GlyphInscribeRandomAction());
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(CodeOfChivalry.ID);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(CodeOfChivalry.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(CodeOfChivalry.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }

            AbstractRelic r = AbstractDungeon.player.getRelic(ID);
            r.flash();
        } else {
            super.obtain();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Apocrypha();
    }
}
