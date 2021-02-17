package TheTemplar.relics;

import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;

import static TheTemplar.TemplarMod.makeRelicOutlinePath;
import static TheTemplar.TemplarMod.makeRelicPath;

public class KingsCrown extends CustomRelic {
    public static final String ID = TemplarMod.makeID("KingsCrown");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("KingsCrown.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("KingsCrown.png"));

    private boolean pickCard = false;
    private boolean showBanner = false;

    public KingsCrown() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
    }

    @Override
    public void update() {
        super.update();
        if (this.pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            this.pickCard = false;
            AbstractCard c = (AbstractDungeon.gridSelectScreen.selectedCards.get(0)).makeCopy();
            c.upgrade();

            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            if (showBanner) {
                AbstractDungeon.dynamicBanner.appear();
            }
        }
    }

    @Override
    public void onEquip() {
        this.pickCard = true;
        CardGroup cardChoices = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AbstractCard choice;

        for (AbstractCard c : this.getCardPool()) {
            choice = c.makeCopy();
            choice.upgrade();

            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onPreviewObtainCard(choice);
            }
            cardChoices.addToBottom(choice);
        }

        if (AbstractDungeon.dynamicBanner != null) {
            showBanner = true;
            AbstractDungeon.dynamicBanner.hide();
        }
        AbstractDungeon.gridSelectScreen.open(cardChoices, 1, DESCRIPTIONS[1], false);
    }

    private ArrayList<AbstractCard> getCardPool() {
        CardGroup rares = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        rares.group.addAll(AbstractDungeon.srcRareCardPool.group);
        rares.sortByType(false);

        CardGroup uncommons = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        uncommons.group.addAll(AbstractDungeon.srcUncommonCardPool.group);
        uncommons.sortByType(false);

        CardGroup commons = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        commons.group.addAll(AbstractDungeon.srcCommonCardPool.group);
        commons.sortByType(false);

        ArrayList<AbstractCard> srcPool = new ArrayList<>(rares.group);
        srcPool.addAll(uncommons.group);
        srcPool.addAll(commons.group);
        return srcPool;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new KingsCrown();
    }
}
