package TheTemplar.variables;

//TODO Let's look at making this an enum
public abstract class HolyWeapons {
    public static final String Default = "Mace";
    public static final String Aegis = "Aegis";
    public static final String Torch = "Torch";
    public static final String Sword = "Sword";
    public static final String Hammer = "Hammer";
    public static final String Book = "Book";

    private static String equipped = Default;

    public static String GetEquipped() {
        return equipped;
    }

    public static void SetEquipped(String newWeapon) {
        equipped = newWeapon;
    }

    public static boolean IsEquipped(String weapon) {
        return equipped.equals(weapon);
    }
}
