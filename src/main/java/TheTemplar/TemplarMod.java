package TheTemplar;

import TheTemplar.glyphs.AbstractGlyph;
import TheTemplar.patches.AbstractMonsterPatch;
import TheTemplar.potions.ExaltedEssence;
import TheTemplar.potions.StalwartFlask;
import TheTemplar.relics.*;
import TheTemplar.variables.HolyWeapons;
import TheTemplar.vfx.FlashCustomAttackEffect;
import TheTemplar.vfx.BookEffect;
import basemod.*;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import TheTemplar.cards.*;
import TheTemplar.characters.TheTemplar;
import TheTemplar.potions.PotionOfLore;
import TheTemplar.util.IDCheckDontTouchPls;
import TheTemplar.util.TextureLoader;
import TheTemplar.variables.DefaultCustomVariable;
import TheTemplar.variables.DefaultSecondMagicNumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@SpireInitializer
public class TemplarMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(TemplarMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static final Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "The Templar";
    private static final String AUTHOR = "ResponsibleAlex & Searcian";
    private static final String DESCRIPTION = "A new playable character.";

    // =============== INPUT TEXTURE LOCATION =================

    // Colors (RGB)
    // Character Color
    public static final Color TEMPLAR_BLUE = CardHelper.getColor(212f, 241f, 244f);

    // Potion Colors in RGB
    public static final Color POTION_DARK = CardHelper.getColor(24f, 154f, 180f);  // blue grotto
    public static final Color POTION_MID = CardHelper.getColor(117f, 230f, 218f); // blue green
    public static final Color POTION_LIGHT = CardHelper.getColor(212f, 241f, 244f);  // baby blue

    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_TEMPLAR = "TheTemplarResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_TEMPLAR = "TheTemplarResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_TEMPLAR = "TheTemplarResources/images/512/bg_power_default_gray.png";

    private static final String ENERGY_ORB_DEFAULT_GRAY = "TheTemplarResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "TheTemplarResources/images/512/card_small_orb.png";

    private static final String ATTACK_TEMPLAR_PORTRAIT = "TheTemplarResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_TEMPLAR_PORTRAIT = "TheTemplarResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_TEMPLAR_PORTRAIT = "TheTemplarResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "TheTemplarResources/images/1024/card_default_gray_orb.png";

    // Character assets
    private static final String TEMPLAR_BUTTON = "TheTemplarResources/images/charSelect/DefaultCharacterButton.png";
    private static final String TEMPLAR_PORTRAIT = "TheTemplarResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String TEMPLAR_SHOULDER_1 = "TheTemplarResources/images/char/defaultCharacter/shoulder.png";
    public static final String TEMPLAR_SHOULDER_2 = "TheTemplarResources/images/char/defaultCharacter/shoulder2.png";
    public static final String TEMPLAR_CORPSE = "TheTemplarResources/images/char/defaultCharacter/corpse.png";

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "TheTemplarResources/images/Badge.png";

    // Atlas and JSON files for the Animations
    public static final String SKELETON_PATH = "TheTemplarResources/images/char/defaultCharacter/skeletons/";

    // =============== MAKE IMAGE PATHS =================

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath + (resourcePath.contains(".png") ? "" : ".png");
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeGlyphPath(String resourcePath) {
        return getModID() + "Resources/images/glyphs/" + resourcePath;
    }

    public static String makeVfxPath(String resourcePath) {
        return getModID() + "Resources/images/vfx/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    /*public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }*/

    // =============== /MAKE IMAGE PATHS/ =================

    // =============== /INPUT TEXTURE LOCATION/ =================


    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================

    public TemplarMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */

        setModID("TheTemplar");

        logger.info("Done subscribing");

        logger.info("Creating the color " + TheTemplar.Enums.TEMPLAR_COLOR.toString());

        BaseMod.addColor(TheTemplar.Enums.TEMPLAR_COLOR, TEMPLAR_BLUE, TEMPLAR_BLUE, TEMPLAR_BLUE,
                TEMPLAR_BLUE, TEMPLAR_BLUE, TEMPLAR_BLUE, TEMPLAR_BLUE,
                ATTACK_TEMPLAR, SKILL_TEMPLAR, POWER_TEMPLAR, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_TEMPLAR_PORTRAIT, SKILL_TEMPLAR_PORTRAIT, POWER_TEMPLAR_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        logger.info("Done creating the color");


        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            logger.error("Unable to configure default mod", e);
        }
        logger.info("Done adding mod settings");

    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = TemplarMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = TemplarMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = TemplarMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO

    // ====== YOU CAN EDIT AGAIN ======

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing The Templar. =========================");
        TemplarMod defaultmod = new TemplarMod();
        logger.info("========================= /The Templar Initialized. Hello World./ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================


    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheTemplar.Enums.THE_TEMPLAR.toString());

        BaseMod.addCharacter(new TheTemplar("The Templar", TheTemplar.Enums.THE_TEMPLAR),
                TEMPLAR_BUTTON, TEMPLAR_PORTRAIT, TheTemplar.Enums.THE_TEMPLAR);

        receiveEditPotions();
        logger.info("Added " + TheTemplar.Enums.THE_TEMPLAR.toString());
    }

    // =============== /LOAD THE CHARACTER/ =================


    // =============== POST-INITIALIZE =================

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");

        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:

            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                logger.error("Unable to configure default mod", e);
            }
        });

        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }

    // =============== / POST-INITIALIZE/ =================

    // ================ ADD POTIONS ===================

    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        BaseMod.addPotion(PotionOfLore.class, POTION_LIGHT, POTION_LIGHT, POTION_LIGHT, PotionOfLore.POTION_ID, TheTemplar.Enums.THE_TEMPLAR);
        BaseMod.addPotion(StalwartFlask.class, POTION_MID, POTION_MID, POTION_MID, StalwartFlask.POTION_ID, TheTemplar.Enums.THE_TEMPLAR);
        BaseMod.addPotion(ExaltedEssence.class, POTION_DARK, POTION_MID, POTION_LIGHT, ExaltedEssence.POTION_ID, TheTemplar.Enums.THE_TEMPLAR);

        logger.info("Done editing potions");
    }

    // ================ /ADD POTIONS/ ===================


    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        addRelic(new CodeOfChivalry());
        addRelic(new DivineChalice());
        addRelic(new PrayerBeads());
        addRelic(new Apocrypha());
        addRelic(new FightersRing());
        addRelic(new DragonsScale());
        addRelic(new GildedEgg());
        addRelic(new BottledQuicksilver());
        addRelic(new KingsCrown());
        addRelic(new Reliquary());
        addRelic(new RunedArmor());

        logger.info("Done adding relics!");
    }

    private void addRelic(AbstractRelic r) {
        BaseMod.addRelicToCustomPool(r, TheTemplar.Enums.TEMPLAR_COLOR);
        UnlockTracker.markRelicAsSeen(r.relicId);
    }

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());

        logger.info("Adding cards");

        new AutoAdd("TemplarMod") // ${project.artifactId}
            .packageFilter(AbstractDefaultCard.class) // filters to any class in the same package as AbstractDefaultCard, nested packages included
            .setDefaultSeen(true)
            .cards();

        logger.info("Done adding cards!");
    }

    // ================ /ADD CARDS/ ===================


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/TemplarMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/TemplarMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/TemplarMod-Relic-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/TemplarMod-Potion-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/TemplarMod-Character-Strings.json");

        logger.info("Done editing strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/TemplarMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }

    // ================ /LOAD THE KEYWORDS/ ===================    

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    public static void renderGlyphs(SpriteBatch sb) {
        AbstractGlyph.renderGlyphs(sb);
    }

    public static void clearGlyphs() {
        AbstractGlyph.removeGlyphs();
    }

    public static int valorInscribedThisCombat = 0;
    public static int glyphsInscribedThisCombat = 0;
    public static final boolean[] glyphTypesInscribedThisCombat = new boolean[5];
    public static boolean triggeredBlessingThisTurn = false;
    public static boolean triggeredBlessingLastTurn = false;

    public static void resetTemplarState() {
        clearGlyphs();
        valorInscribedThisCombat = 0;
        glyphsInscribedThisCombat = 0;
        for (int i = 0; i < 5; i++) {
            glyphTypesInscribedThisCombat[i] = false;
        }
        triggeredBlessingThisTurn = false;
        triggeredBlessingLastTurn = false;

        setHolyWeapon(HolyWeapons.Default);
    }

    public static void startOfTurn() {
        triggeredBlessingLastTurn = triggeredBlessingThisTurn;
        triggeredBlessingThisTurn = false;
    }

    public static void combatUpdate() {
        AbstractGlyph.updateGlyphs();

        if (AbstractDungeon.player.chosenClass.toString().equals("THE_TEMPLAR")
                && HolyWeapons.IsEquipped(HolyWeapons.Book)) {
            BookEffect.update();
        }
    }

    public static void updateGlyphsAnimation() { AbstractGlyph.updateGlyphsAnimation(); }

    public static void setHolyWeapon(String newWeapon) {
        AbstractPlayer p = AbstractDungeon.player;
        HolyWeapons.SetEquipped(newWeapon);
        if (p.chosenClass.toString().equals("THE_TEMPLAR")) {
            ((TheTemplar)p).reloadAnimation(newWeapon);
        }
    }

    public static boolean shouldUseCustomAttackEffect() {
        return !HolyWeapons.IsEquipped(HolyWeapons.Default)
                && !HolyWeapons.IsEquipped(HolyWeapons.Aegis);
    }

    public static void flashCustomAttackEffect(AbstractCreature target) {
        flashCustomAttackEffect(target, true);
    }
    public static void flashCustomAttackEffect(AbstractCreature target, boolean playSound) {
        float x = 0;
        float y = 0;
        if (target != null) {
            x = target.hb.cX;
            if (HolyWeapons.IsEquipped(HolyWeapons.Book)) {
                y = target.drawY;
            } else {
                y = target.hb.cY;
            }
        }

        AbstractDungeon.effectList.add(
                new FlashCustomAttackEffect(x, y, HolyWeapons.GetEquipped(), playSound));
    }

    public static void flashCustomAttackAllEffect() {
        if (HolyWeapons.IsEquipped(HolyWeapons.Sword)) {
            flashCustomAttackEffect(null);
        } else {
            boolean playSound = true;
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDeadOrEscaped()) {
                    flashCustomAttackEffect(m, playSound);
                    if (playSound) playSound = false;
                }
            }
        }
    }

    public static boolean isEmpowered(AbstractMonster m) {
        return (m.hasPower(StrengthPower.POWER_ID) && m.getPower(StrengthPower.POWER_ID).amount > 0)
                || m.hasPower(MetallicizePower.POWER_ID)
                || m.hasPower(RegenerateMonsterPower.POWER_ID)
                || AbstractMonsterPatch.maxHpBuff.get(m);
    }

    public static boolean areAnyEmpowered() {
        for (AbstractMonster m: AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) {
                if (isEmpowered(m)) {
                    return true;
                }
            }
        }
        return false;
    }
}
