package legendarypotionsmod.relics.uncommon;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import legendarypotionsmod.potions.LegendaryPotionPool;
import legendarypotionsmod.relics.BaseRelic;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.relicRng;
import static legendarypotionsmod.legendarypotions.makeID;

public class WitchsDoll extends BaseRelic {
    private static final String NAME = "WitchsDoll"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final AbstractRelic.RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK; //The sound played when the relic is clicked.

    public WitchsDoll() {
        super(ID, NAME, RARITY, SOUND);
        this.counter = 2;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new WitchsDoll();
    }

    @Override
    public void onChestOpen(boolean bossChest) {
        if (!bossChest && this.counter > 0) {
            this.counter--;
            flash();
            AbstractPlayer p = AbstractDungeon.player;
            addToTop(new RelicAboveCreatureAction(p, this));


            LegendaryPotionPool.loadPool();
            AbstractPotion potion = LegendaryPotionPool.getRandomLegendaryPotion(relicRng, false).makeCopy();
            AbstractDungeon.getCurrRoom().addPotionToRewards(potion);

            // Update description and used-up state
            if (this.counter == 0) {
                setCounter(-2);
                this.description = this.DESCRIPTIONS[1];
            }
        }
    }

    @Override
    public void setCounter(int setCounter) {
        this.counter = setCounter;
        if (setCounter == -2) {
            usedUp(); // Makes relic fade out
            this.counter = -2;
        }
    }

    @Override
    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 40;
    }


}
