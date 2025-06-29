package legendarypotionsmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.EntropicBrew;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;


import static legendarypotionsmod.legendarypotions.makeID;

public class BottledEntropy extends BasePotion {
    public static final String ID = makeID(BottledEntropy.class.getSimpleName());

    //private static final Color LIQUID_COLOR = CardHelper.getColor(58, 223, 232); // light blue
    //private static final Color HYBRID_COLOR = CardHelper.getColor(24, 120, 125); // darker blue
    //private static final Color SPOTS_COLOR = null;


    //public BottledEntropy() {
    //super(ID, 1, PotionRarity.PLACEHOLDER, PotionSize.S, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
    //isThrown = true;
    //}

    public BottledEntropy() {
        super(ID, "Evanescent Entropy", PotionRarity.PLACEHOLDER, PotionSize.EYE, PotionEffect.RAINBOW, Color.WHITE, null, null);
        isThrown = false;
        targetRequired = false;
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1; // or 2, or logic if you want to scale it
    }

    @Override
    public String getDescription() {
        if (potency == 1) {
            return DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        } else {
            return DESCRIPTIONS[0] + potency + DESCRIPTIONS[2];
        }
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;

        int maxSlots = 5;
        int slotsToAdd = Math.min(potency, maxSlots - p.potionSlots);

        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (slotsToAdd > 0) {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        p.potionSlots += slotsToAdd;
                        for (int i = 0; i < slotsToAdd; i++) {
                            p.potions.add(new PotionSlot(p.potions.size()));
                        }
                        isDone = true;
                    }
                });
            }

            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if (p.hasRelic("Sozu")) {
                        p.getRelic("Sozu").flash();
                        isDone = true;
                        return;
                    }
                    for (int i = 0; i < p.potionSlots; i++) {
                        if (p.potions.get(i) instanceof PotionSlot) {
                            addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
                        }
                    }
                    isDone = true;
                }
            });
        } else {
            // Outside combat: add slots immediately (since no action queue)
            if (slotsToAdd > 0) {
                p.potionSlots += slotsToAdd;
                for (int i = 0; i < slotsToAdd; i++) {
                    p.potions.add(new PotionSlot(p.potions.size()));
                }
            }

            if (p.hasRelic("Sozu")) {
                p.getRelic("Sozu").flash();
            } else {
                for (int i = 0; i < p.potionSlots; i++) {
                    if (p.potions.get(i) instanceof PotionSlot) {
                        AbstractDungeon.effectsQueue.add(new ObtainPotionEffect(AbstractDungeon.returnRandomPotion()));
                    }
                }
            }
        }

        // Add only 1 Entropic Brew, even if more slots are empty
        /*try {
            AbstractPotion entropic = new EntropicBrew(); // Replace with your custom class if applicable
            p.obtainPotion(entropic);
        } catch (Exception e) {
            e.printStackTrace();
        } */


        // nerf applied, this is the old code to fill all slots
        /*addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (int i = 0; i < p.potionSlots; i++) {
                    if (p.potions.get(i) instanceof PotionSlot) {
                        p.obtainPotion(i, new EntropicBrew());

                        AbstractDungeon.topLevelEffects.add(new ObtainPotionEffect(p.potions.get(i)));

                    }
                }
                isDone = true;
            }
        });*/
    }

    @Override
    public boolean canUse() {
        return true;
    }
}

