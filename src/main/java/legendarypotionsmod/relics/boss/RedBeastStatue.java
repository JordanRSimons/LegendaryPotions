package legendarypotionsmod.relics.boss;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import legendarypotionsmod.potions.LegendaryPotionPool;
import legendarypotionsmod.relics.BaseRelic;

import static legendarypotionsmod.legendarypotions.makeID;

public class RedBeastStatue extends BaseRelic {
    private static final String NAME = "RedBeastStatue"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final AbstractRelic.RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.CLINK; //The sound played when the relic is clicked.

    public RedBeastStatue() {
        super(ID, NAME, RARITY, SOUND);
    }

    /* @Override
    public void onEquip() {
        AbstractPlayer p = AbstractDungeon.player;

        // Make sure there is at least one slot to remove
        if (p.potionSlots > 0) {
            int lastIndex = p.potionSlots - 1;

            // If the slot has a potion, mark it as not obtained
            AbstractPotion potion = p.potions.get(lastIndex);
            if (potion != null && potion.isObtained) {
                potion.isObtained = false; // This avoids visual issues
            }

            // Remove the visual slot and update the count
            p.potions.remove(lastIndex); // Remove the last potion slot visually
            p.potionSlots--; // Reduce max slots
        }
    } */

    // Reduces total energy to 2
    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster--;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster++;
    }


    private boolean pendingPotion = false; // Flag to delay potion use

    @Override
    public void atBattleStart() {
        pendingPotion = true;
    }

    @Override
    public void atTurnStartPostDraw() {
        if (pendingPotion) {
            pendingPotion = false;
            flash();

            // Queue an action to run potion usage on next frame, ensuring all draw/discard triggers run first
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    try {
                        Class<? extends AbstractPotion> potionClass = LegendaryPotionPool.getRandomLegendaryPotion();
                        AbstractPotion potion = potionClass.getDeclaredConstructor().newInstance();
                        potion.use(AbstractDungeon.player);

                        // Trigger relic onUsePotion effects
                        for (AbstractRelic relic : AbstractDungeon.player.relics) {
                            relic.onUsePotion();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    isDone = true;
                }
            });
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
