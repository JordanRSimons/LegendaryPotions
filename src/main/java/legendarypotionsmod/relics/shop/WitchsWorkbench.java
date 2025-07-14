package legendarypotionsmod.relics.shop;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FirePotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import legendarypotionsmod.potions.LegendaryPotionPool;
import legendarypotionsmod.relics.BaseRelic;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.relicRng;
import static legendarypotionsmod.legendarypotions.makeID;

public class WitchsWorkbench extends BaseRelic {
    private static final String NAME = "WitchsWorkbench"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final AbstractRelic.RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK; //The sound played when the relic is clicked.

    public WitchsWorkbench() {
        super(ID, NAME, RARITY, SOUND);
    }


    @Override
    public void onEquip() {
        LegendaryPotionPool.loadPool();
        AbstractPotion legendaryPotion1 = LegendaryPotionPool.getRandomLegendaryPotion(relicRng, false).makeCopy();
        AbstractPotion legendaryPotion2 = LegendaryPotionPool.getRandomLegendaryPotion(relicRng, false).makeCopy();

        // Add both potions to the current room’s rewards
        AbstractDungeon.getCurrRoom().addPotionToRewards(legendaryPotion1);
        AbstractDungeon.getCurrRoom().addPotionToRewards(legendaryPotion2);


        AbstractDungeon.combatRewardScreen.open(this.DESCRIPTIONS[1]);
        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.0F;

        // card rewards gone
        int remove = -1;
        for (int i = 0; i < AbstractDungeon.combatRewardScreen.rewards.size(); i++) {
            RewardItem item = AbstractDungeon.combatRewardScreen.rewards.get(i);
            if (item.type == RewardItem.RewardType.CARD) {
                remove = i;
                break;
            }
        }
        if (remove != -1) {
            AbstractDungeon.combatRewardScreen.rewards.remove(remove);
        }

    }



    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    // old effect
    /* @Override
    public void onEquip() {
        ArrayList<AbstractPotion> potionsToReplace = new ArrayList<>();

        // Collect only real obtained potions (exclude placeholders like "Potion Slot")
        for (AbstractPotion potion : AbstractDungeon.player.potions) {
            if (potion != null && potion.isObtained && !potion.ID.equals("Potion Slot")) {
                potionsToReplace.add(potion);
            }
        }

        // Remove those potions
        for (AbstractPotion potion : potionsToReplace) {
            AbstractDungeon.player.removePotion(potion);
        }

        // Add a random legendary potion for each potion removed
        int legendaryCount = (potionsToReplace.size() + 1) / 2;  // rounds up

        for (int i = 0; i < legendaryCount; i++) {
            LegendaryPotionPool.loadPool();
            AbstractPotion potion = LegendaryPotionPool.getRandomLegendaryPotion(relicRng, false).makeCopy();
            AbstractDungeon.player.obtainPotion(potion);
        }
    } */




}