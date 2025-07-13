package legendarypotionsmod.potions;

// created by SandTag
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.random.Random;
import java.util.ArrayList;

public class STLegendaryPotionPool {

    // included potion list
    public static ArrayList<AbstractPotion> getLegendaryPotions = new ArrayList<AbstractPotion>();

    private static final ArrayList<String> excluded = new ArrayList<String>(){{
        add(OmniscientPotion.ID); //Hardcoded method - Use for potions from this mod.
        add("bundle_of_potions:Test448"); //Use strings with the full ID for other mods, even if you import them to manually add stuff you cant put loader checks in this list creator.
    }};

    public static void loadPool(){
        getLegendaryPotions.clear();//Ensure it is cleaned up incase items are accidentally being lost somewhere.

        // add my potions to the pool
        getLegendaryPotions.add(new EchoPotion());
        getLegendaryPotions.add(new LethargicBrew());
        getLegendaryPotions.add(new BottledEntropy());
        getLegendaryPotions.add(new OmniscientPotion());
        getLegendaryPotions.add(new BottledFinale());
        getLegendaryPotions.add(new FlashPotion());


        // add modded potions to the pool
        if (Loader.isModLoaded("bundle_of_potions")) {//Compatibility blocks...
            getLegendaryPotions.add(OutsidePotions.getPotionByIndex(0));//Add a switch case for the potion...
        }

        if (Loader.isModLoaded("bundle_of_terra")) {//Compatibility blocks...
            getLegendaryPotions.add(OutsidePotions.getPotionByIndex(1));//Add a switch case for the potion...
        }

        /* for (AbstractPotion potion : PotionHelper.getPotionsByRarity(AbstractPotion.PotionRarity.PLACEHOLDER)){//Automated filling method requires manual exceptions in the "excluded" section.
            if (!excluded.contains(potion.ID)){//If the potion is not on the list of naughty things we don't want...
                getLegendaryPotions.add(potion.makeCopy());//Make a copy of it and stick it in the pool!
            }
        } */
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