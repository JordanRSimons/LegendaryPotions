package legendarypotionsmod.potions;

import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FirePotion;
import downfall.potions.CursedFountainPotion;
import terrabundle.potions.RedPotionLegendary;

public class DownfallPotions {
    public static AbstractPotion getPotion() {
        try {
            //System.out.println("LOOK HERE: Attempting Fountain");
            return new CursedFountainPotion();
        } catch (Exception ignored) {//We use "Exception" because the above thing actually throws multiple different exceptions all at once if it does not exist. NPE, NotFound, ect.
        }

        return new FirePotion();//If potion no longer exists, use backup.

    }
}
