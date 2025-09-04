package btpos.mcmods.cutscenelib

import net.minecraft.resources.ResourceLocation
import org.slf4j.LoggerFactory

internal const val MOD_ID = CutsceneLibMod.MODID

/**
 * Common mod entry point
 */
object CutsceneLibMod {
	const val MODID = "cutscenelib"
	
	val LOGGER = LoggerFactory.getLogger("CutsceneLib")
	
	fun init() {
	}
}

internal fun loc(path: String): ResourceLocation {
	return ResourceLocation.fromNamespaceAndPath(MOD_ID, path)
}