package TheTemplar.relics;

import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static TheTemplar.TemplarMod.makeRelicOutlinePath;
import static TheTemplar.TemplarMod.makeRelicPath;

public class Reliquary extends CustomRelic {
    public static final String ID = TemplarMod.makeID("Reliquary");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Reliquary.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Reliquary.png"));

    private static final int NUMBER = 2;

    public Reliquary() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
        this.counter = NUMBER;
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.counter = NUMBER;
        this.grayscale = false;
    }

    public void decrementCounter() {
        this.counter--;
        if (this.counter == 0) {
            this.grayscale = true;
            this.counter = -2;
        }
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (this.counter > 0
                && (drawnCard.type == AbstractCard.CardType.CURSE
                || drawnCard.type == AbstractCard.CardType.STATUS)) {

            this.addToTop(new ExhaustSpecificCardAction(drawnCard, AbstractDungeon.player.hand, true));
            this.addToBot(new DrawCardAction(1));

            this.flash();
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

            this.decrementCounter();
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + NUMBER + DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Reliquary();
    }
}
