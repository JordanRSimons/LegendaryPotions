package legendarypotionsmod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import legendarypotionsmod.potions.LegendaryPotionPool;

import java.util.ArrayList;

import static legendarypotionsmod.legendarypotions.makeID;

public class ReplacePotionsUC extends BaseRelic {
    private static final String NAME = "ReplacePotionsUC"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final AbstractRelic.RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK; //The sound played when the relic is clicked.

    public ReplacePotionsUC() {
        super(ID, NAME, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        ArrayList<AbstractPotion> potionsToReplace = new ArrayList<>();

        // Debug: show potion info per slot
        System.out.println("[ReplacePotionsUC] Checking current potions:");

        for (int i = 0; i < AbstractDungeon.player.potionSlots; i++) {
            AbstractPotion potion = AbstractDungeon.player.potions.get(i);

            if (potion == null) {
                System.out.println("  Slot " + i + ": null");
            } else {
                System.out.println("  Slot " + i + ": " + potion.ID + ", isObtained=" + potion.isObtained);
            }

            // Only add potions that are obtained and not empty placeholder
            if (potion != null && potion.isObtained && !potion.ID.equals("Empty")) {
                potionsToReplace.add(potion);
            }
        }

        System.out.println("[ReplacePotionsUC] Removing " + potionsToReplace.size() + " potions and replacing with legendary potions.");

        // Remove all potions to be replaced
        // Remove all potions to be replaced
        for (AbstractPotion potion : potionsToReplace) {
            AbstractDungeon.player.removePotion(potion);
        }

        // Add legendary potions equal to how many were removed
        for (int i = 0; i < potionsToReplace.size(); i++) {
            try {
                Class<? extends AbstractPotion> potionClass = LegendaryPotionPool.getRandomLegendaryPotion();
                AbstractPotion newPotion = potionClass.getDeclaredConstructor().newInstance();
                AbstractDungeon.player.obtainPotion(newPotion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


}