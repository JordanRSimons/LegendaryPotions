package legendarypotionsmod.potions;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import java.util.Arrays;
import java.util.List;

public class LegendaryPotionPool {
    public static List<Class<? extends AbstractPotion>> getLegendaryPotions() {
        return Arrays.asList(
                EchoPotion.class,
                LethargicBrew.class,
                ClawPotion.class,
                BottledEntropy.class,
                OmniscientPotion.class,
                BottledFinale.class
        );
    }

    public static Class<? extends AbstractPotion> getRandomLegendaryPotion() {
        List<Class<? extends AbstractPotion>> pool = getLegendaryPotions();
        return pool.get(AbstractDungeon.cardRandomRng.random(pool.size() - 1));
    }
}
