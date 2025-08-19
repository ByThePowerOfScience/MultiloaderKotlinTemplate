package btpos.mcmods.examplemod.fabric

import btpos.mcmods.examplemod.ExampleMod
import net.fabricmc.api.ModInitializer

class FabricEntry : ModInitializer {
	override fun onInitialize() {
		ExampleMod.init()
	}
}