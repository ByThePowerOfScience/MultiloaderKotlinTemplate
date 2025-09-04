package btpos.mcmods.cutscenelib.fabric

import btpos.mcmods.cutscenelib.CutsceneLibMod
import net.fabricmc.api.ModInitializer
import net.minecraft.world.level.block.Block

class CutsceneLibFabric : ModInitializer {
	override fun onInitialize() {
		CutsceneLibMod.init()
	}
}