package TheTemplar.cards;

import TheTemplar.actions.GlyphInscribeAction;
import TheTemplar.glyphs.Valor;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class GatherCourage extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(GatherCourage.class.getSimpleName());
    public static final String IMG = makeCardPath(GatherCourage.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 1;
    private static final int VALOR = 1;
    private static final int UPGRADE_PLUS_VALOR = 1;
    private static final int ENEMY_STRENGTH = 1;

    // /STAT DECLARATION/


    public GatherCourage() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = VALOR;

        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster unused) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new GlyphInscribeAction(new Valor()));
        }

        for (AbstractMonster m: AbstractDungeon.getMonsters().monsters) {
            if(!m.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(m, m, new StrengthPower(m, ENEMY_STRENGTH), ENEMY_STRENGTH));
                addToBot(new ApplyPowerAction(m, m, new LoseStrengthPower(m, ENEMY_STRENGTH), ENEMY_STRENGTH));
            }
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_VALOR);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new GatherCourage();
    }
}
