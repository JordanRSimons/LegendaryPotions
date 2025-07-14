package legendarypotionsmod.potions;

import betterAltar.potions.AltarPotion;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FirePotion;

public class AltarPotions {
    public static AbstractPotion getPotion() {
        try {
            return new AltarPotion();
        } catch (Exception ignored) {//We use "Exception" because the above thing actually throws multiple different exceptions all at once if it does not exist. NPE, NotFound, ect.
        }

        return new FirePotion();//If potion no longer exists, use backup.

    }
}
