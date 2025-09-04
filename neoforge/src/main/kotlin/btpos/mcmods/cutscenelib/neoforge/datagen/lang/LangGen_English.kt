@file:Suppress("NOTHING_TO_INLINE")

package btpos.mcmods.cutscenelib.neoforge.datagen.lang

import btpos.mcmods.cutscenelib.TerminusMod.MODID
import btpos.mcmods.cutscenelib.common.registry.TerminusBlocks
import net.minecraft.data.PackOutput
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.common.data.LanguageProvider

class LangGen_English(output: PackOutput) : LanguageProvider(output, MODID, "en_us") {
	override fun addTranslations() {
		addBlocks()
		addItems()
		addCreativeTabs()
	}
	
	fun addBlocks() {
	
	}
	
	fun addItems() {
		
	}
	
	
	fun addCreativeTabs() {
	
	}
	
	
	
	
	// endregion
}

// region Utils
context(gen: LanguageProvider)
operator fun Block.invoke(translation: String, tooltip: String? = null, customItemName: String? = null) {
	gen.add(this, translation)
	
	// Add item name
	(customItemName ?: translation).let {
		gen.add(this.asItem().descriptionId, it)
	}
	
	// Add tooltip
	if (tooltip != null) {
		gen.add(this.descriptionId + ".tooltip", tooltip)
	}
}

context(gen: LanguageProvider)
operator fun Item.invoke(translation: String, vararg tooltip: String) {
	gen.add(this, translation)
	tooltip.forEachIndexed { i, it ->
		gen.add(this.descriptionId + ".tooltip$i", it)
	}
}