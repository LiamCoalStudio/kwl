package com.liamcoalstudio.kettle.kwl.inventory

@Suppress("unused")
enum class PlayerInventorySlot(val int: Int) {
    // Crafting
    CraftingOutput(0),
    CraftingInput0(1),
    CraftingInput1(2),
    CraftingInput2(3),
    CraftingInput3(4),

    // Armor
    ArmorHead(5),
    ArmorChest(6),
    ArmorPants(7),
    ArmorShoes(8),

    // Main Inventory

    InventoryRow1Slot1(9+0),
    InventoryRow1Slot2(9+1),
    InventoryRow1Slot3(9+2),
    InventoryRow1Slot4(9+3),
    InventoryRow1Slot5(9+4),
    InventoryRow1Slot6(9+5),
    InventoryRow1Slot7(9+6),
    InventoryRow1Slot8(9+7),
    InventoryRow1Slot9(9+8),

    InventoryRow2Slot1(18+0),
    InventoryRow2Slot2(18+1),
    InventoryRow2Slot3(18+2),
    InventoryRow2Slot4(18+3),
    InventoryRow2Slot5(18+4),
    InventoryRow2Slot6(18+5),
    InventoryRow2Slot7(18+6),
    InventoryRow2Slot8(18+7),
    InventoryRow2Slot9(18+8),

    InventoryRow3Slot1(27+0),
    InventoryRow3Slot2(27+1),
    InventoryRow3Slot3(27+2),
    InventoryRow3Slot4(27+3),
    InventoryRow3Slot5(27+4),
    InventoryRow3Slot6(27+5),
    InventoryRow3Slot7(27+6),
    InventoryRow3Slot8(27+7),
    InventoryRow3Slot9(27+8),

    // Hotbar
    HotbarSlot1(36+0),
    HotbarSlot2(36+1),
    HotbarSlot3(36+2),
    HotbarSlot4(36+3),
    HotbarSlot5(36+4),
    HotbarSlot6(36+5),
    HotbarSlot7(36+6),
    HotbarSlot8(36+7),
    HotbarSlot9(36+8),

    Offhand(45)
}