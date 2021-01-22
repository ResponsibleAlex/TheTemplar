package TheTemplar.cards;

import TheTemplar.actions.EquipHolyWeaponAction;
import TheTemplar.variables.HolyWeapons;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TheTemplar.TemplarMod;
import TheTemplar.characters.TheTemplar;

import static TheTemplar.TemplarMod.makeCardPath;

public class TestAnimations extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TemplarMod.makeID(TestAnimations.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTemplar.Enums.COLOR_GRAY;

    private static final int COST = 0;
    // private static final int UPGRADED_COST = 0;

    private static final int BLOCK = 0;
    //private static final int UPGRADE_PLUS_BLOCK = 0;

    // /STAT DECLARATION/

    public TestAnimations() {
        this(false);
    }
    public TestAnimations(boolean upgraded) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;

        isInnate = true;
        exhaust = true;
        this.upgraded = upgraded;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        switch (HolyWeapons.GetEquipped()) {
            case HolyWeapons.Default:
                this.addToBot(new EquipHolyWeaponAction(HolyWeapons.Aegis, this.upgraded));
                break;
            case HolyWeapons.Aegis:
                this.addToBot(new EquipHolyWeaponAction(HolyWeapons.Book, this.upgraded));
                break;
            case HolyWeapons.Book:
                this.addToBot(new EquipHolyWeaponAction(HolyWeapons.Hammer, this.upgraded));
                break;
            case HolyWeapons.Hammer:
                this.addToBot(new EquipHolyWeaponAction(HolyWeapons.Sword, this.upgraded));
                break;
            case HolyWeapons.Sword:
                this.addToBot(new EquipHolyWeaponAction(HolyWeapons.Torch, this.upgraded));
                break;
            case HolyWeapons.Torch:
                this.addToBot(new EquipHolyWeaponAction(HolyWeapons.Default, this.upgraded));
                break;
        }

        this.addToBot(new MakeTempCardInHandAction(new TestAnimations(this.upgraded)));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new TestAnimations();
    }
}
