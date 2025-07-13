package legendarypotionsmod.relics.boss;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import legendarypotionsmod.potions.OldLegendaryPotionPool;
import legendarypotionsmod.relics.BaseRelic;

import java.util.ArrayList;

import static legendarypotionsmod.legendarypotions.makeID;

public class BlueBeastStatue extends BaseRelic {
    private static final String NAME = "BlueBeastStatue"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final AbstractRelic.RelicTier RARITY = AbstractRelic.RelicTier.BOSS; //The relic's rarity.
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK; //The sound played when the relic is clicked.

    public BlueBeastStatue() {
        super(ID, NAME, RARITY, SOUND);
    }

    @Override
    public void onVictory() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) {
            if (AbstractDungeon.getCurrRoom().rewards == null) {
                AbstractDungeon.getCurrRoom().rewards = new ArrayList<>();
            }

            // Remove all existing potion rewards
            // AbstractDungeon.getCurrRoom().rewards.removeIf(reward -> reward.type == RewardItem.RewardType.POTION);

            // Add a legendary potion reward
            try {
                Class<? extends AbstractPotion> potionClass = OldLegendaryPotionPool.getRandomLegendaryPotion();
                AbstractPotion potion = potionClass.getDeclaredConstructor().newInstance();
                AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(potion));
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Refresh combat reward screen
            AbstractDungeon.combatRewardScreen.rewards = AbstractDungeon.getCurrRoom().rewards;
            AbstractDungeon.combatRewardScreen.positionRewards();
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
