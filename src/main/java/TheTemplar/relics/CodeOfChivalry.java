package TheTemplar.relics;

import TheTemplar.actions.GlyphInscribeAction;
import TheTemplar.glyphs.Valor;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static TheTemplar.TemplarMod.makeRelicOutlinePath;
import static TheTemplar.TemplarMod.makeRelicPath;

public class CodeOfChivalry extends CustomRelic {
    public static final String ID = TemplarMod.makeID(CodeOfChivalry.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CodeOfChivalry.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CodeOfChivalry.png"));

    public CodeOfChivalry() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        this.addToBot(new GlyphInscribeAction(new Valor()));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CodeOfChivalry();
    }
}
