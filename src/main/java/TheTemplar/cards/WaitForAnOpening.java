package TheTemplar.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class WaitForAnOpening extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(WaitForAnOpening.class.getSimpleName());
    public static final String IMG = makeCardPath(WaitForAnOpening.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 1;

    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;

    // /STAT DECLARATION/


    public WaitForAnOpening() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && isMonsterNotAttacking(m)) {
                glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        if (m != null && isMonsterNotAttacking(m)) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }

    private boolean isMonsterNotAttacking(AbstractMonster m) {
        return (m.intent != AbstractMonster.Intent.ATTACK &&
                m.intent != AbstractMonster.Intent.ATTACK_BUFF &&
                m.intent != AbstractMonster.Intent.ATTACK_DEBUFF &&
                m.intent != AbstractMonster.Intent.ATTACK_DEFEND);
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
        return new WaitForAnOpening();
    }
}
