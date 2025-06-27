package legendarypotionsmod.potions;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.actions.common.HealAction;

public class HealOnNextDamagePower extends AbstractPower {
    public static final String POWER_ID = "legendarypotionsmod:HealOnNextDamagePower";

    public HealOnNextDamagePower(AbstractCreature owner, int potency) {
        this.name = "Heal On Next Damage";
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = potency;  // store potency here
        this.updateDescription();
        this.type = PowerType.BUFF;
    }

    @Override
    public void updateDescription() {
        this.description = "Heal for the amount of your next unblocked damage dealt to an enemy, multiplied by " + this.amount + ", then remove this power.";
    }

    @Override
    public void onAttack(final DamageInfo info, final int damageAmount, final AbstractCreature target) {
        if (target != null && damageAmount > 0 && info.owner == this.owner) {
            int healAmt = damageAmount * this.amount;  // scale heal by potency
            AbstractDungeon.actionManager.addToBottom(new HealAction(owner, owner, healAmt));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(owner, owner, this.ID));
        }
    }
}

