# MidnightRPG

**MidnightRPG** is a 2D retro tile-based role-playing game written in Java 25. Inspired by classic retro MMORPG mechanics (such as RuneScape), MidnightRPG features real-time tile movement, gathering skills (woodcutting, mining), a responsive combat system, equipment and inventory management, dynamic node-based dialogue, NPC AI behaviors, drop tables, audio playback, and color-coded PNG map loading.

---

## Table of Contents

- [Overview](#overview)
- [Key Features & Game Subsystems](#key-features--game-subsystems)
  - [Player & Progression System](#player--progression-system)
  - [Skills & Gathering System](#skills--gathering-system)
  - [Combat Subsystem](#combat-subsystem)
  - [NPCs, Monsters & AI Behaviors](#npcs-monsters--ai-behaviors)
  - [Dialogue & Interaction Engine](#dialogue--interaction-engine)
  - [Inventory, Equipment & Items](#inventory-equipment--items)
  - [Drop Tables & Ground Items](#drop-tables--ground-items)
  - [World, Regions & Map Loader](#world-regions--map-loader)
  - [UI & HUD Framework](#ui--hud-framework)
  - [Audio & Asset Pipeline](#audio--asset-pipeline)
- [Package Architecture (`src/main/java`)](#package-architecture-srcmainjava)
- [Resource Structure (`src/main/resources`)](#resource-structure-srcmainresources)
- [Controls & Keybindings](#controls--keybindings)
- [Prerequisites & Getting Started](#prerequisites--getting-started)
  - [Prerequisites](#prerequisites)
  - [Building the Project](#building-the-project)
  - [Running the Game](#running-the-game)
  - [Running Tests](#running-tests)
- [Constants & Identifiers Reference](#constants--identifiers-reference)

---

## Overview

MidnightRPG provides a modular gameplay layer built on top of a 2D engine. The game is designed around an event-driven and tick-based simulation loop:

- **Entry Point**: `dev.midnightcoder.Main` initiates asset caching and launches `MidnightRPG`.
- **Game State Flow**: Boots into a `LoginScreen` allowing players to start a new adventure or load an existing profile, transitioning into the main `GameScreen`.
- **Game Tick & Render Loop**: Updates world physics, mob AI, skilling ticks, depleted resource timers, combat delays, UI elements, and camera follow tracking.

---

## Key Features & Game Subsystems

### Player & Progression System
- **Player State & Movement**: Responsive tile movement (`PlayerMovement`), collision detection with map tiles and solid game objects, walking vs. sprinting (`Shift` key modifier), animated directional sprites (`NORTH`, `SOUTH`, `EAST`, `WEST`), and automatic camera tracking.
- **Skill Progression (`SkillSet`)**: Tracks 14 distinct skills:
  - Combat: `Attack`, `Strength`, `Defense`, `Hitpoints`, `Ranged`, `Magic`
  - Gathering & Crafting: `Woodcutting`, `Mining`, `Fishing`, `Cooking`, `Smithing`, `Firemaking`, `Crafting`
  - Utility: `Agility`
- **Level Scaling**: Experience curves convert raw XP into levels (1–99), dynamically updating combat stats and unlocking higher-tier tools and harvestable resources.
- **Player Profile**: Manages persistent profile metadata, player username, audio preferences, and settings.

### Skills & Gathering System
- **Action Processing (`SkillingAction`)**: Skilling actions run on a tick-delay cycle (`actionDelay` / `actionSpeed`), verifying proximity to the target resource.
- **Mining (`MiningAction`)**:
  - Requires a compatible pickaxe (`Stone`, `Iron`, `Steel`).
  - Harvests rocks (`Stone`, `Copper`, `Tin`, `Iron`, `Coal`, `Mithril`).
  - Success chance formula: $\text{Power} = \text{Mining Level} \times \text{Pickaxe Effectiveness}$; $\text{Chance} = \frac{\text{Ratio}}{\text{Ratio} + 1.0}$ where $\text{Ratio} = \frac{\text{Power}}{\text{Required Level}}$.
  - Resource Depletion & Respawning: Rocks deplete upon harvest and automatically respawn after configured tick delays (`DepletedResource`).
- **Woodcutting (`ChoppingAction`)**:
  - Requires a hatchet (`Stone`, `Iron`, `Steel`).
  - Fells trees (`Normal`, `Oak`, `Maple`, `Willow`).
  - Felled trees transition into stumps, granting logs and experience, and respawn after defined tick intervals.

### Combat Subsystem
- **Real-Time Combat Loop (`Combat`, `PlayerCombat`, `NpcCombat`)**:
  - Attack speed delays, melee attack range, and tick-based retaliation.
  - Damage calculation based on combined Attack and Strength skill levels.
  - Hitsplat rendering (`Hitsplat`, `HitsplatType`) indicating damage dealt and remaining health.
  - Combat XP distribution (allocates Attack XP and Hitpoints XP per successful hit).
  - Death & Respawning: Entities handle death delays, clear combat targets, drop loot, and players respawn with restored health at spawn coordinates.

### NPCs, Monsters & AI Behaviors
- **Modular Behaviors (`Behavior`)**:
  - `WanderBehavior`: Periodic random wandering within bounds.
  - `PassiveBehavior` & `AggressiveBehavior`: Reactive hostility and threat detection.
  - `CombatBehavior`: Handles target tracking, attacking, and retaliating.
  - `DialogueBehavior`: Handles interactive talking and dialogue triggering.
  - `ShopBehavior`: NPC merchant interactions.
- **Predefined Entities**:
  - Friendly NPCs: `Pokey` (interactive dialogue NPC on Tutorial Island).
  - Monsters: `Chicken`, `StoneGolem`, `OceanFaery`.

### Dialogue & Interaction Engine
- **Node-Based Dialogue System (`Dialogue`, `DialogueSession`, `DialogueManager`)**:
  - Supports frame types:
    - `MessageFrame`: Standalone dialogue messages.
    - `PlayerFrame` & `NpcFrame`: Speaker-focused dialogue lines.
    - `ChoiceFrame`: Player branching decisions and conversation paths.
    - `ItemFrame`: Dialogue steps that award or showcase items.
    - `EndFrame`: Dialogue completion.
  - Repository-based registration (`DialogueRepository`) for scriptable NPC conversations.

### Inventory, Equipment & Items
- **Backpack (`Backpack`, `Inventory`)**: 28-slot inventory supporting item stacking, slot hovering, dragging, and context action menus (`Use`, `Equip`, `Drop`, `Examine`).
- **Equipment System (`Equipment`, `EquipmentManager`, `EquipmentSlot`)**:
  - Slots: `HEAD`, `CAPE`, `NECK`, `WEAPON`, `BODY`, `SHIELD`, `LEGS`, `HANDS`, `FEET`, `RING`, `AMMO`.
  - Equipping items automatically manages stat modifiers and inventory slot swapping.
- **Item Definitions (`Item`, `ItemEquipmentData`, `ItemManager`)**: JSON-driven item database (`item-defs.json`) providing names, descriptions, values, stacking rules, equipment slot mappings, and sprite IDs.

### Drop Tables & Ground Items
- **Weighted Drop System (`DropTable`, `NPCDropTable`, `DropItem`, `DropRarity`)**:
  - Tiered rarity probabilities: `ALWAYS` (100%), `COMMON` (1/16), `UNCOMMON` (1/64), `RARE` (1/256), `VERY_RARE` (1/1024).
  - Automatically rolls on monster defeat and spawns items into the world.
- **Ground Items (`GroundItem`, `GroundItemManager`)**: Renders dropped loot in world space with interaction options (`Pick Up`, `Examine`).

### World, Regions & Map Loader
- **Map Decoding (`PngMapLoader`, `TileColorRegistry`)**:
  - Reads color-coded PNG map files to construct 2D tilemaps.
  - Landscape image: decodes RGB pixel values to terrain tiles (grass, water, dirt, sand, stone).
  - Object map image: decodes RGB pixel values into interactive `GameObject` instances (trees, rocks, bushes, scenery).
- **Regions (`Regions`, `TutorialIsland`)**: Structured multi-region architecture using unique asset UUIDs for landscape and object map definitions.

### UI & HUD Framework
- **UIManager & HUDs (`UIManager`, `HUD`)**:
  - `TopHUD`: Displays current date/time, engine FPS, and connection/game status.
  - `BottomHUD`: Quick navigation switching between active inventory views, skill tabs, equipment, audio settings, and logout.
  - `InventoryHUD`: 28-slot visual grid with quantity overlays.
  - `EquipmentHUD`: Visual character equipment layout.
  - `SkillsHUD`: Comprehensive grid displaying skill levels, experience values, and level progress tooltips.
  - `AudioHUD`: Master volume sliders and music track selection.
  - `ContextMenu`: Right-click contextual dropdown menus for world entities, objects, ground items, and inventory slots.
  - `DialogueInterface`: Speech boxes, character dialogue prompts, and interactive choice buttons.

### Audio & Asset Pipeline
- **Sound & Music (`AssetManager`, `MusicTrack`, `SoundEffect`)**:
  - BGM track streaming (tracks include *Autumn Voyage*, *Harmony*, *Newbie Melody*, *Scape Main*, *Wander*).
  - Sound effect triggers for clicks, combat hits, and gathering actions.
  - Volume control settings saved with the player profile.

---

## Package Architecture (`src/main/java`)

```
dev.midnightcoder
├── Main.java                               # Application bootstrap & entry point
└── rpg
    ├── MidnightRPG.java                    # Core game implementation & scene controller
    │
    ├── assets                              # Asset loading and cache decoders
    │   ├── AssetManager.java               # Global asset manager (sprites, audio, defs)
    │   ├── CacheDefinition.java            # Cache metadata constants
    │   ├── PngMapLoader.java               # PNG image-to-tilemap decoder
    │   ├── audio                           # Sound effects and music track definitions
    │   │   ├── MusicTrack.java
    │   │   └── SoundEffect.java
    │   └── tiles                           # Tile loader and RGB color mapping
    │       ├── TileColor.java
    │       ├── TileColorRegistry.java
    │       └── TileLoader.java
    │
    ├── content                             # In-game skills and gameplay mechanics
    │   └── skills
    │       ├── DepletedResource.java       # Responding resource tracker
    │       ├── SkillingAction.java         # Base tick-action skilling handler
    │       ├── mining                      # Mining actions, pickaxes, and rock definitions
    │       │   ├── MiningAction.java
    │       │   ├── PickaxeType.java
    │       │   └── RockType.java
    │       └── woodcutting                 # Woodcutting actions, hatchets, and tree definitions
    │           ├── ChoppingAction.java
    │           ├── Hatchet.java
    │           └── TreeType.java
    │
    ├── dialogue                            # Branching dialogue engine
    │   ├── Dialogue.java                   # Dialogue definition
    │   ├── DialogueBuilder.java            # Fluent dialogue creation
    │   ├── DialogueManager.java            # Active session manager
    │   ├── DialogueRepository.java         # Dialogue registry
    │   ├── DialogueSession.java            # Runtime session state
    │   └── frame                           # Frame types (Player, NPC, Choice, Item, Message, End)
    │
    ├── drop                                # Loot drop tables & probability calculations
    │   ├── DropItem.java
    │   ├── DropManager.java
    │   ├── DropRarity.java
    │   ├── DropTable.java
    │   └── NPCDropTable.java
    │
    ├── entity                              # Base entity & world object system
    │   ├── Entity.java                     # Base entity class
    │   ├── combat                          # Combat controllers & damage formulas
    │   │   ├── Combat.java
    │   │   ├── CombatStats.java
    │   │   ├── Hitsplat.java
    │   │   ├── HitsplatType.java
    │   │   ├── NpcCombat.java
    │   │   └── PlayerCombat.java
    │   ├── item                            # Ground item entities in the world
    │   │   ├── GroundItem.java
    │   │   └── GroundItemManager.java
    │   ├── mob                             # Mobs, movement, animations, and avatars
    │   │   ├── Mob.java
    │   │   ├── PlayerAvatar.java
    │   │   ├── npc                         # NPCs, monster definitions, and AI behaviors
    │   │   │   ├── NPC.java
    │   │   │   ├── NpcDefinition.java
    │   │   │   ├── NpcManager.java
    │   │   │   ├── NpcMovement.java
    │   │   │   ├── behaviors/              # Wander, Aggressive, Passive, Combat, Dialogue
    │   │   │   ├── impl/                   # Pokey
    │   │   │   └── monster/                # Monster base and implementations (Chicken, Golem, Faery)
    │   │   └── player                      # Player entity, profiles, and level handlers
    │   │       ├── Player.java
    │   │       ├── PlayerLevelHandler.java
    │   │       ├── PlayerMovement.java
    │   │       └── PlayerProfile.java
    │   ├── object                          # World interactables (trees, rocks, bushes)
    │   │   ├── GameObject.java
    │   │   ├── ObjectDefinition.java
    │   │   ├── ObjectManager.java
    │   │   └── impl/                       # Bush, BerryBush, RockObject, TreeObject
    │   └── skill                           # Player skill definitions and skill set
    │       ├── Skill.java
    │       ├── SkillSet.java
    │       ├── SkillSlot.java
    │       └── SkillType.java
    │
    ├── inventory                           # Inventory and equipment container management
    │   ├── container
    │   │   ├── Backpack.java               # 28-slot player inventory container
    │   │   └── Equipment.java              # Player equipment container
    │   ├── EquipmentManager.java
    │   ├── EquipmentSlot.java              # Gear slots (Head, Weapon, Shield, etc.)
    │   └── Inventory.java                  # Container interface
    │
    ├── item                                # Item models, definitions, and metadata
    │   ├── Item.java
    │   ├── ItemDefinition.java
    │   ├── ItemEquipmentData.java
    │   └── ItemManager.java
    │
    ├── scene                               # Game screens and transition manager
    │   ├── GameStartMode.java              # NEW_GAME vs. LOAD_GAME
    │   ├── Scene.java
    │   ├── SceneManager.java
    │   └── impl
    │       ├── GameScreen.java             # Main gameplay viewport & HUD compositor
    │       └── LoginScreen.java            # Main menu & title screen
    │
    ├── ui                                  # UI rendering, HUDs, and interactive widgets
    │   ├── UIManager.java
    │   ├── hud                             # Top HUD, Bottom Nav, Volume Controls
    │   ├── interfaces                      # Backpack HUD, Equipment HUD, Skills HUD, Audio HUD
    │   ├── menu                            # Right-click Context Menus & Action Items
    │   └── slot                            # Item and Skill visual slots
    │
    ├── util                                # Global ID constants and helper utilities
    │   ├── ItemId.java                     # Item ID constants
    │   ├── NpcId.java                      # NPC ID constants
    │   ├── ObjectId.java                   # Object ID constants
    │   ├── MenuActionable.java             # Context menu interaction interface
    │   └── TextUtils.java                  # Text layout and formatting utilities
    │
    └── world                               # World definitions and map instantiation
        ├── Regions.java                    # Region landscape and object map GUIDs
        └── TutorialIsland.java             # Tutorial Island map implementation
```

---

## Resource Structure (`src/main/resources`)

The `src/main/resources` folder contains game assets loaded during startup:

```
src/main/resources
├── audio/
│   ├── music/                              # Background music tracks (.wav / .ogg)
│   └── sfx/                                # Sound effect audio files
├── items/
│   └── item-defs.json                      # Definitions for all items (names, values, sprites, slots)
├── login/                                  # Login screen backgrounds, buttons, and branding
├── objects/
│   └── object-defs.json                    # Definitions for interactive objects (examine text, solid flag)
├── sprites/
│   ├── avatars/                            # Player character customization layers
│   ├── mobs/                               # Monster sprite sheets
│   ├── npcs/                               # NPC sprite sheets
│   └── player/                             # Base player movement sprites
├── texture/                                # Environment tilesets and terrain textures
├── ui/                                     # UI frames, icons, navigation buttons, and health bars
└── world/                                  # Region PNG landscape and object map images
```

---

## Controls & Keybindings

| Input | Action |
| :--- | :--- |
| `W` / `Up Arrow` | Move North |
| `S` / `Down Arrow` | Move South |
| `A` / `Left Arrow` | Move West |
| `D` / `Right Arrow` | Move East |
| `Shift` (Hold) | Sprint / Run Mode |
| `Left Mouse Click` | Primary Interaction / Move / Select UI Button / Dialogue Progress |
| `Right Mouse Click` | Open Context Menu (Examine, Talk-To, Attack, Chop, Mine, Pick Up) |
| `Esc` | Close Active UI Windows / Cancel Menu |

---

## Prerequisites & Getting Started

### Prerequisites
- **Java Development Kit (JDK)**: Java 25 or higher with preview features enabled if applicable.
- **Gradle**: Gradle 8+ (or use the included `gradlew` / `gradlew.bat` wrapper).

### Building the Project
To compile all source files and verify the build:

```bash
# On Windows (PowerShell / Command Prompt):
.\gradlew.bat build

# On Linux / macOS:
./gradlew build
```

### Running the Game
To launch MidnightRPG:

```bash
# On Windows:
.\gradlew.bat run

# On Linux / macOS:
./gradlew run
```

### Running Tests
To run unit and integration tests:

```bash
# On Windows:
.\gradlew.bat test

# On Linux / macOS:
./gradlew test
```

---

## Constants & Identifiers Reference

For easy reference during development and modding, standard constants are declared in `dev.midnightcoder.rpg.util`:

### Key Item IDs (`ItemId`)
| ID | Constant | ID | Constant |
| :--- | :--- | :--- | :--- |
| `0` | `HEALTH_POTION` | `12` | `COPPER_ORE` |
| `1` | `EMPTY_VIAL` | `13` | `TIN_ORE` |
| `2` | `WOODEN_STAFF` | `14` | `IRON_ORE` |
| `3` | `IRON_SWORD` | `15` | `COAL` |
| `4` | `STONE_HATCHET` | `16` | `MITHRIL_ORE` |
| `5` | `IRON_HATCHET` | `17` | `BRONZE_BAR` |
| `6` | `STEEL_HATCHET` | `18` | `IRON_BAR` |
| `7` | `STONE_PICKAXE` | `19` | `STEEL_BAR` |
| `8` | `IRON_PICKAXE` | `20` | `MITHRIL_BAR` |
| `9` | `STEEL_PICKAXE` | `21` | `OAK_LOGS` |
| `10` | `NORMAL_LOGS` | `22` | `MAPLE_LOGS` |
| `11` | `STONE` | `23` | `WILLOW_LOGS` |

### Key NPC IDs (`NpcId`)
| ID | Constant | Description |
| :--- | :--- | :--- |
| `0` | `POKEY` | Guide NPC on Tutorial Island with interactive dialogue |
| `1` | `CHICKEN` | Passive farm animal mob |
| `2` | `STONE_GOLEM` | Hostile stone creature |
| `3` | `OCEANFAERY` | Mystical ocean fairy entity |

### Key Object IDs (`ObjectId`)
| ID | Constant | ID | Constant |
| :--- | :--- | :--- | :--- |
| `0` | `STONE_ROCK` | `12` | `BUSHES` |
| `1` | `COPPER_ROCK` | `13` | `BUSHES_BERRY` |
| `2` | `TIN_ROCK` | `14` | `NORMAL_TREE` |
| `3` | `IRON_ROCK` | `15` | `OAK_TREE` |
| `4` | `COAL_ROCK` | `16` | `MAPLE_TREE` |
| `5` | `MITHRIL_ROCK` | `17` | `WILLOW_TREE` |
| `6`–`11` | `NORMAL_TREE_STUMP` variants | | |
