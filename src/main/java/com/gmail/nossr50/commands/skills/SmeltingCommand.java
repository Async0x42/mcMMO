package com.gmail.nossr50.commands.skills;

import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.datatypes.skills.SubSkillType;
import com.gmail.nossr50.mcMMO;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SmeltingCommand extends SkillCommand {
    private String burnTimeModifier;
    private String str_secondSmeltChance;
    private String str_secondSmeltChanceLucky;
    private String str_fluxMiningChance;
    private String str_fluxMiningChanceLucky;

    private boolean canFuelEfficiency;
    private boolean canSecondSmelt;
    private boolean canFluxMine;
    private boolean canUnderstandTheArt;

    public SmeltingCommand(mcMMO pluginRef) {
        super(PrimarySkillType.SMELTING, pluginRef);
    }

    @Override
    protected void dataCalculations(Player player, double skillValue) {
        // FUEL EFFICIENCY
        if (canFuelEfficiency) {
            burnTimeModifier = String.valueOf(pluginRef.getUserManager().getPlayer(player).getSmeltingManager().getFuelEfficiencyMultiplier());
        }

        // FLUX MINING
        /*if (canFluxMine) {
            String[] fluxMiningStrings = getAbilityDisplayValues(SkillActivationType.RANDOM_LINEAR_100_SCALE_WITH_CAP, player, SubSkillType.SMELTING_FLUX_MINING);
            str_fluxMiningChance = fluxMiningStrings[0];
            str_fluxMiningChanceLucky = fluxMiningStrings[1];
        }*/

        // SECOND SMELT
        if (canSecondSmelt) {
            String[] secondSmeltStrings = getAbilityDisplayValues(player, SubSkillType.SMELTING_SECOND_SMELT);
            str_secondSmeltChance = secondSmeltStrings[0];
            str_secondSmeltChanceLucky = secondSmeltStrings[1];
        }
    }

    @Override
    protected void permissionsCheck(Player player) {
        canFuelEfficiency = canUseSubSkill(player, SubSkillType.SMELTING_FUEL_EFFICIENCY);
        canSecondSmelt = canUseSubSkill(player, SubSkillType.SMELTING_SECOND_SMELT);
        //canFluxMine = canUseSubskill(player, SubSkillType.SMELTING_FLUX_MINING);
        canUnderstandTheArt = pluginRef.getPermissionTools().vanillaXpBoost(player, skill) && pluginRef.getRankTools().hasUnlockedSubskill(player, SubSkillType.SMELTING_UNDERSTANDING_THE_ART);
    }

    @Override
    protected List<String> statsDisplay(Player player, double skillValue, boolean hasEndurance, boolean isLucky) {
        List<String> messages = new ArrayList<>();

        /*if (canFluxMine) {
            messages.add(getStatMessage(SubSkillType.SMELTING_FLUX_MINING, str_fluxMiningChance)
                    + (isLucky ? pluginRef.getLocaleManager().getString("Perks.Lucky.Bonus", str_fluxMiningChanceLucky) : ""));
            //messages.add(pluginRef.getLocaleManager().getString("Smelting.Ability.FluxMining", str_fluxMiningChance) + (isLucky ? pluginRef.getLocaleManager().getString("Perks.Lucky.Bonus", str_fluxMiningChanceLucky) : ""));
        }*/

        if (canFuelEfficiency) {
            messages.add(getStatMessage(false, true, SubSkillType.SMELTING_FUEL_EFFICIENCY, burnTimeModifier));
        }

        if (canSecondSmelt) {
            messages.add(getStatMessage(SubSkillType.SMELTING_SECOND_SMELT, str_secondSmeltChance)
                    + (isLucky ? pluginRef.getLocaleManager().getString("Perks.Lucky.Bonus", str_secondSmeltChanceLucky) : ""));
        }

        if (canUnderstandTheArt) {
            messages.add(getStatMessage(false, true, SubSkillType.SMELTING_UNDERSTANDING_THE_ART,
                    String.valueOf(pluginRef.getUserManager().getPlayer(player).getSmeltingManager().getVanillaXpMultiplier())));
        }

        return messages;
    }

    @Override
    protected List<TextComponent> getTextComponents(Player player) {
        List<TextComponent> textComponents = new ArrayList<>();

        pluginRef.getTextComponentFactory().getSubSkillTextComponents(player, textComponents, PrimarySkillType.SMELTING);

        return textComponents;
    }
}
