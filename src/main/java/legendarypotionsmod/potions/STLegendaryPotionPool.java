package legendarypotionsmod.potions;

// created by SandTag
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FirePotion;
import com.megacrit.cardcrawl.random.Random;
import java.util.ArrayList;

public class PotionTest {

    // included potion list
    public static ArrayList<AbstractPotion> getLegendaryPotions = new ArrayList<AbstractPotion>();

    private static final ArrayList<String> excluded = new ArrayList<String>(){{
        add(OmniscientPotion.POTION_ID); //Hardcoded method - Use for potions from this mod.
        add("bundle_of_potions:Test448"); //Use strings with the full ID for other mods, even if you import them to manually add stuff you cant put loader checks in this list creator.
    }};

    public static void loadPool(){
        getLegendaryPotions.clear();//Ensure it is cleaned up incase items are accidentally being lost somewhere.

        getLegendaryPotions.add(new EchoPotion());//Repeat for each potion...
        //...

        if (Loader.isModLoaded("bundle_of_potions")) {//Compatibility blocks...
            getLegendaryPotions.add(otherFile(0));//Add a switch case for the potion...
        }

        for (AbstractPotion potion : PotionHelper.getPotionsByRarity(AbstractPotion.PotionRarity.PLACEHOLDER)){//Automated filling method requires manual exceptions in the "excluded" section.
            if (!excluded.contains(potion.ID)){//If the potion is not on the list of naughty things we don't want...
                getLegendaryPotions.add(potion.makeCopy());//Make a copy of it and stick it in the pool!
            }
        }
    }

    public static AbstractPotion otherFile(int toGet){//This must go in a separate file as per soft-dependency rules for hard item imports, else it will still crash on startup.
        switch (toGet){
            case 0:{//Grab them when the mods are loaded if you chose the method of manually adding them!
                if (Loader.isModLoaded("bundle_of_potions")) {
                    return new Test448();//Requires Dependency in pom, user does not need mod loaded.
                }
            }
            default:{
                return new FirePotion();//Fire potions are always used as a failsafe in vanilla.
            }
        }
    }

    public AbstractPotion getRandomLegendaryPotion(Random rng, boolean limited) {//This allows items to use the appropriate RnG rolls, so events and relics can use their respective RnG option.
        if (rng == null){//Extra option for truly random legendary potions, this includes your healing rarity potions if set to false, useful for events!
            return AbstractDungeon.returnRandomPotion(AbstractPotion.PotionRarity.PLACEHOLDER, limited);
        }
        else{
            return getLegendaryPotions.get(rng.random(getLegendaryPotions.size()-1));//This one would be most commonly called, it does not include healing potions.
        }
    }
}