package legendarypotionsmod.potions;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FirePotion;

public class OutsidePotions {
    public static AbstractPotion getPotionByIndex(int toGet){//This must go in a separate file as per soft-dependency rules for hard item imports, else it will still crash on startup.
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
    }
}
