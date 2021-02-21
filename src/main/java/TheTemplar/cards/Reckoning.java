package TheTemplar.cards;

import TheTemplar.actions.GlyphInscribeAction;
import TheTemplar.glyphs.Justice;
import TheTemplar.glyphs.Valor;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static TheTemplar.TemplarMod.makeCardPath;

@SuppressWarnings("unused")
public class Reckoning extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(Reckoning.class.getSimpleName());
    public static final String IMG = makeCardPath(Reckoning.class.getSimpleName());

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTemplar.Enums.TEMPLAR_COLOR;

    private static final int COST = 2;

    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 4;

    // /STAT DECLARATION/


    public Reckoning() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GlyphInscribeAction(new Valor()));
        this.addToBot(new GlyphInscribeAction(new Justice()));

        this.addToBot(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.0F));
        this.addToBot(new SFXAction("ORB_LIGHTNING_EVOKE"));
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
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
        return new Reckoning();
    }
}
