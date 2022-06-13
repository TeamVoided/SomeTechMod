package team.voided.sometechmod.energyunit;

import net.minecraft.network.chat.Component;
import team.voided.quiltenergy.HSV;
import team.voided.quiltenergy.energy.EnergyUnit;
import team.voided.sometechmod.SomeTechMod;

public final class STMUnits {
	public static final EnergyUnit ATMOSPHERIC_EXTRACT = new EnergyUnit(8.0F, SomeTechMod.modLoc("atmospheric_extract_unit"), Component.translatable(SomeTechMod.MOD_ID+".energy.unit.aeu"), new HSV(224, 44, 100));
}
