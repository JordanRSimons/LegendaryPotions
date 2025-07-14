package legendarypotionsmod.potions;

import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FirePotion;
import potionbundle.potions.Test448;

public class BundlePotionsPotions {
    public static AbstractPotion getPotion() {
        try {
            return new Test448();
        } catch (Exception ignored) {//We use "Exception" because the above thing actually throws multiple different exceptions all at once if it does not exist. NPE, NotFound, ect.
        }

        return new FirePotion();//If potion no longer exists, use backup.

    }
}

// old version with switch for multiple cases
/* public static AbstractPotion getPotionByIndex(int toGet){//This must go in a separate file as per soft-dependency rules for hard item imports, else it will still crash on startup.

        switch (toGet){
            case 0:{//Grab them when the mods are loaded if you chose the method of manually adding them!
                if (Loader.isModLoaded("bundle_of_potions")) {
                    return new Test448();//Requires Dependency in pom, user does not need mod loaded.
                }
                break;
            }
            case 1:{
                if (Loader.isModLoaded("bundle_of_terra")) {
                    return new RedPotionLegendary();//Requires Dependency in pom, user does not need mod loaded.
                }
                break;
            }
            default:{
                return new FirePotion();//Fire potions are always used as a failsafe in vanilla.
            }
        }
        return new FirePotion(); // Fire potions are always used as a failsafe in vanilla.
    } */