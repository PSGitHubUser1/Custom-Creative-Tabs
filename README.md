# Custom Creative Tabs
Add your own creative tabs to the game with a simple JSON config file!

## Creating Creative tabs
Each creative tab requires its own JSON file that needs to be placed in the config directory (`.../config/cct/`).
- `icon`: The registry name of the item that should be used as its icon.
- `enable_search_bar`: *(Optional)* If true, a search bar will be added to the creative tab.
- `items`: A list of String taking registry names of items that will be added to this creative tab.
  - Note: When using a Minecraft's item, writing `minecraft:` before the item's id is not required. However, `modid:` is required if an item from another mod is desired.

It is important to note that a creative uses a translated text, but they are not included with this mod.
An external resource pack is required to replace `itemGroup.registryName` in the creative menu. `registryName` is the file's name (excluding the extension).


If you want to generate a JSOn file, you can use the new command included with this mod that will take all parameters to make the JSON file for you.
`/cct <registryName> <icon> <enableSearchBar> <items>` where `<registryName>` is a simple word for the file's name, `<enableSearchBar>` is true or false and `<items>` is the rest of the command and will take all item's registry names that should be added.
However, concerning this last one, Minecraft will not display a list as for the icon.

### Example
In the following example, the diamond block will be displayed as the icon and a search bar will be included for this creative tab.
```json
{
  "icon": "minecraft:diamond_block",
  "enable_search_bar": true,
  "items": [
    "minecraft:dirt",
    "minecraft:diamond",
    "minecraft:diamond_sword",
    "gold_ingot"
  ]
}
```
In this second exemple, the cake is displayed as the icon and the creative tab contains no search bar.
```json
{
  "icon": "cake",
  "items": [
    "cake",
    "milk_bucket"
  ]
}
```