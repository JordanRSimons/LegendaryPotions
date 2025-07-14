package legendarypotionsmod.potions;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedBeastPotionPool {
    public static ArrayList<AbstractPotion> getLegendaryPotions = new ArrayList<AbstractPotion>();

    public static void loadPool() {
        getLegendaryPotions.clear();//Ensure it is cleaned up incase items are accidentally being lost somewhere.

        // add my potions to the pool
        getLegendaryPotions.add(new EchoPotion());
        getLegendaryPotions.add(new LethargicBrew());
        getLegendaryPotions.add(new OmniscientPotion());
        getLegendaryPotions.add(new BottledFinale());
        getLegendaryPotions.add(new FlashPotion());
    }

    public static AbstractPotion getRandomLegendaryPotion(Random rng, boolean limited) {//This allows items to use the appropriate RnG rolls, so events and relics can use their respective RnG option.
        if (rng == null) {//Extra option for truly random legendary potions, this includes your healing rarity potions if set to false, useful for events!
            return AbstractDungeon.returnRandomPotion(AbstractPotion.PotionRarity.PLACEHOLDER, limited);
        } else {
            return getLegendaryPotions.get(rng.random(getLegendaryPotions.size() - 1));//This one would be most commonly called, it does not include healing potions.
        }
    }
}




//    public static List<Class<? extends AbstractPotion>> getLegendaryPotions() {
//        return Arrays.asList(
//                EchoPotion.class,
//                LethargicBrew.class,
//                // BottledEntropy.class,
//                OmniscientPotion.class,
//                BottledFinale.class,
//                FlashPotion.class
//        );
//    }





    /* public static Class<? extends AbstractPotion> getRandomLegendaryPotion() {
        List<Class<? extends AbstractPotion>> pool = getLegendaryPotions();
        return pool.get(AbstractDungeon.cardRandomRng.random(pool.size() - 1));
    } */

