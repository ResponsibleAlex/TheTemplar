package TheTemplar.cards;

import TheTemplar.actions.GlyphInscribeAction;
import TheTemplar.glyphs.Zeal;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class RecklessSweep extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(RecklessSweep.class.getSimpleName());
    public static final String IMG = makeCardPath(RecklessSweep.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 1;

    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int FRAIL = 1;

    // /STAT DECLARATION/


    public RecklessSweep() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = FRAIL;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

        addToBot(new GlyphInscribeAction(new Zeal()));
        addToBot(new ApplyPowerAction(p, p, new FrailPower(p, magicNumber, true), 2));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new RecklessSweep();
    }
}
