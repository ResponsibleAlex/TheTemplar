package TheTemplar.relics;

import TheTemplar.util.TextureLoader;
import TheTemplar.TemplarMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static TheTemplar.TemplarMod.makeRelicOutlinePath;
import static TheTemplar.TemplarMod.makeRelicPath;

public class DivineChalice extends CustomRelic {
    public static final String ID = TemplarMod.makeID("DivineChalice");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DivineChalice.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DivineChalice.png"));

    private static final int REGEN = 3;
    private static final int STR_DEX = 1;

    public DivineChalice() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new RegenPower(p, REGEN), REGEN));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, STR_DEX), STR_DEX));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, STR_DEX), STR_DEX));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DivineChalice();
    }
}
