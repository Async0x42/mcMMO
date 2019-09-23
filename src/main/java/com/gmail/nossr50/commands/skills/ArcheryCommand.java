package com.gmail.nossr50.commands.skills;

import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.datatypes.skills.SubSkillType;
import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.skills.archery.Archery;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArcheryCommand extends SkillCommand {
    private String skillShotBonus;
    private String dazeChance;
    private String dazeChanceLucky;
    private String retrieveChance;
    private String retrieveChanceLucky;

    private boolean canSkillShot;
    private boolean canDaze;
    private boolean canRetrieve;

    public ArcheryCommand(mcMMO pluginRef) {
        super(PrimarySkillType.ARCHERY, pluginRef);
    }

    @Override
    protected void dataCalculations(Player player, double skillValue) {
        // ARCHERY_ARROW_RETRIEVAL
        if (canRetrieve) {
            String[] retrieveStrings = getAbilityDisplayValues(player, SubSkillType.ARCHERY_ARROW_RETRIEVAL);
            retrieveChance = retrieveStrings[0];
            retrieveChanceLucky = retrieveStrings[1];
        }

        // ARCHERY_DAZE
        if (canDaze) {
            String[] dazeStrings = getAbilityDisplayValues(player, SubSkillType.ARCHERY_DAZE);
            dazeChance = dazeStrings[0];
            dazeChanceLucky = dazeStrings[1];
        }

        // SKILL SHOT
        if (canSkillShot) {
            skillShotBonus = percent.format(Archery.getDamageBonusPercent(player));
        }
    }

    @Override
    protected void permissionsCheck(Player player) {
        canSkillShot = canUseSubSkill(player, SubSkillType.ARCHERY_SKILL_SHOT);
        canDaze = canUseSubSkill(player, SubSkillType.ARCHERY_DAZE);
        canRetrieve = canUseSubSkill(player, SubSkillType.ARCHERY_ARROW_RETRIEVAL);
    }

    @Override
    protected List<String> statsDisplay(Player player, double skillValue, boolean hasEndurance, boolean isLucky) {
        List<String> messages = new ArrayList<>();

        if (canRetrieve) {
            messages.add(getStatMessage(SubSkillType.ARCHERY_ARROW_RETRIEVAL, retrieveChance)
                    + (isLucky ? pluginRef.getLocaleManager().getString("Perks.Lucky.Bonus", retrieveChanceLucky) : ""));
        }

        if (canDaze) {
            messages.add(getStatMessage(SubSkillType.ARCHERY_DAZE, dazeChance)
                    + (isLucky ? pluginRef.getLocaleManager().getString("Perks.Lucky.Bonus", dazeChanceLucky) : ""));
        }

        if (canSkillShot) {
            messages.add(getStatMessage(SubSkillType.ARCHERY_SKILL_SHOT, skillShotBonus));
        }

        if (canUseSubSkill(player, SubSkillType.ARCHERY_ARCHERY_LIMIT_BREAK)) {
            messages.add(getStatMessage(SubSkillType.ARCHERY_ARCHERY_LIMIT_BREAK,
<<<<<<< HEAD
                    String.valueOf(pluginRef.getCombatTools().getLimitBreakDamage(player, SubSkillType.ARCHERY_ARCHERY_LIMIT_BREAK))));
=======
                String.valueOf(CombatUtils.getLimitBreakDamageAgainstQuality(player, SubSkillType.ARCHERY_ARCHERY_LIMIT_BREAK, 1000))));
>>>>>>> 308e3a4b1f46e9e3de28d6d540dd055a540ed4d5
        }

        return messages;
    }

    @Override
    protected List<TextComponent> getTextComponents(Player player) {
        List<TextComponent> textComponents = new ArrayList<>();

        pluginRef.getTextComponentFactory().getSubSkillTextComponents(player, textComponents, PrimarySkillType.ARCHERY);

        return textComponents;
    }
}
