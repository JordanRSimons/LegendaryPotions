package legendarypotionsmod.potions;

import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FirePotion;
import terrabundle.potions.RedPotionLegendary;

public class TerraPotions {
    public static AbstractPotion getPotion() {
        try {
            return new RedPotionLegendary();
        } catch (Exception ignored) {//We use "Exception" because the above thing actually throws multiple different exceptions all at once if it does not exist. NPE, NotFound, ect.
        } finally {
            return new FirePotion();//If potion no longer exists, use backup.
        }
    }
}
