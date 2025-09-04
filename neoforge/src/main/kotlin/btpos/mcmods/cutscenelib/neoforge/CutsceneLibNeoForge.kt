package btpos.mcmods.cutscenelib.neoforge

import btpos.mcmods.devutil.neoforge.multiplatform.services.NeoForgeRegistryFactory
import btpos.mcmods.cutscenelib.CutsceneLibMod
import btpos.mcmods.cutscenelib.CutsceneLibMod.MODID
import net.neoforged.fml.ModLoader
import net.neoforged.fml.common.Mod
import net.neoforged.fml.loading.FMLEnvironment
import thedarkcolour.kotlinforforge.neoforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(MODID)
object CutsceneLibNeoForge {
	init {
		NeoForgeRegistryFactory.registerModEventBus(MODID, MOD_BUS)
		CutsceneLibMod.init()
	}
}