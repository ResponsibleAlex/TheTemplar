package TheTemplar.powers;

import TheTemplar.util.HolyWeaponPower;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import TheTemplar.TemplarMod;
import TheTemplar.util.TextureLoader;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static TheTemplar.TemplarMod.makePowerPath;

public class SacredHammerPower extends HolyWeaponPower implements CloneablePowerInterface {
    public static final String POWER_ID = TemplarMod.makeID(SacredHammerPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("SacredHammer84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("SacredHammer32.png"));

    public SacredHammerPower(final boolean upgraded) {
        name = NAME;
        ID = POWER_ID;

        owner = AbstractDungeon.player;
        amount = 1;

        type = PowerType.BUFF;

        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        refresh(upgraded);
    }

    public void stackPower(int unused) { }

    public static boolean freeToPlay(AbstractCard card) {
        if (AbstractDungeon.player != null &&
            AbstractDungeon.currMapNode != null &&
            AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&
            card.type == AbstractCard.CardType.ATTACK &&
            AbstractDungeon.player.hasPower(SacredHammerPower.POWER_ID)) {

            SacredHammerPower power = (SacredHammerPower) AbstractDungeon.player.getPower(SacredHammerPower.POWER_ID);
            return power.upgraded && power.amount == 1;
        } else {
            return false;
        }
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && amount > 0) {
            flash();
            --amount;
            updateDescription();
        }
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        if (amount == 1) {
            return type == DamageInfo.DamageType.NORMAL ? damage * 2.0F : damage;
        } else {
            return damage;
        }
    }

    @Override
    public void atStartOfTurn() {
        amount = 1;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount == 0) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1];
        }
        description += DESCRIPTIONS[2];
        if (upgraded) {
            description += DESCRIPTIONS[3];
        }
        description += DESCRIPTIONS[4];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SacredHammerPower(upgraded);
    }
}
