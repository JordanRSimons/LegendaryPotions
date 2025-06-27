package legendarypotionsmod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import legendarypotionsmod.potions.LegendaryPotionPool;

import static legendarypotionsmod.legendarypotions.makeID;

public class ReplacePotionsUC extends BaseRelic {
    private static final String NAME = "ReplacePotionsUC"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final AbstractRelic.RelicTier RARITY = AbstractRelic.RelicTier.BOSS; //The relic's rarity.
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK; //The sound played when the relic is clicked.

    public ReplacePotionsUC() {
        super(ID, NAME, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        int filledSlots = 0;

        // Count how many potion slots are currently occupied
        for (AbstractPotion potion : AbstractDungeon.player.potions) {
            if (potion != null && potion.isObtained) {
                filledSlots++;
            }
        }

        // Remove all potions
        AbstractDungeon.player.potions.clear();

        // Refill only the previously filled slots with random legendary potions
        for (int i = 0; i < filledSlots; i++) {
            try {
                Class<? extends AbstractPotion> potionClass = LegendaryPotionPool.getRandomLegendaryPotion();
                AbstractPotion potion = potionClass.getDeclaredConstructor().newInstance();
                AbstractDungeon.player.obtainPotion(i, potion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}