<img src="https://github.com/IkonoDim/SlashSignItem/blob/main/assets/slashsignitem-banner.png?raw=true" alt="banner" height="100"/>
A highly customizable Minecraft Paper plugin that adds the ability to sign items

## Overview
SlashSignItem is a Minecraft plugin designed for Paper servers. It allows players to sign items with specific text, providing functionalities for managing item signatures and their behaviors. The plugin includes features like item signing and unsigning, configuration management, and many permissions.

## Requirements
- Java 21 or newer
- MInecraft (Paper) 1.20.* or newer

## Ingame Example
<img src="https://github.com/IkonoDim/SlashSignItem/blob/main/assets/slashsignitem-screenshot01.png?raw=true" alt="screenshot" height="130"/>
<img src="https://github.com/IkonoDim/SlashSignItem/blob/main/assets/slashsignitem-screenshot02.png?raw=true" alt="screenshot" height="130"/>
<sub>Screenshots made in Minecraft 1.20.6 using the default configuration</sub>


## Features
- Item Signing: Commands to sign and unsign items.
- Configuration Options: Extensive configuration for customizing item signatures.
- bStats Integration: Collects anonymous statistics to help improve the plugin.

## Commands
| Command | Required Permission |
| ------------ | ---------------------- |
| /sign          | SlashSignItem.sign     |
| /unsign      | SlashSignItem.unsign |

## Permissions


| Name                                                  | Description                                                                   | Can be used by |
| ---------------------------------------- | ---------------------------------------------------------- | ------------ |
| SlashSignItem.sign                              | Allows the player to sign an item                                 | everybody |
| SlashSignItem.unsign                          | Allows the player to remove the signature of an item | only op     |
| SlashSignItem.ignore_blacklist            | Allows the player to sign blacklisted items                  | only op     |
| SlashSignItem.ignore_max-stack-size | Allows the player surpass the stack-limit                     | only op     |
| SlashSignItem.overwrite_signature     | Allows the player to overwrite existing signatures       | only op     |

## Configuration

```yml
signature-template: "&8Signed by &a&l%player%&r &8on &7&l%date%&r &8at &7%time%&r"
item-name-template: "&b%name%"

Options:
  item-blacklist: []
  max-stack-size: 1
  enchantment-glint: true
  override-signature: false

Messages:
  prefix: "[SlashSignItem] >> "

  item-no-signature: "%prefix% &cThis item is not signed!"
  item-has-signature: "%prefix% &cThis item is already signed!"
  not-holding-item: "%prefix% &cYou have to be holding an item in your hand in order to sign it!"
  cant-sign-item: "%prefix% &cThis item can't be signed!"
  too-many-items: "%prefix% &cYou are holding too many items!"

Formats:
  date: "MM/dd/yyyy"
  time: "HH:mm"
```

### License
Details on the licensing can be found in the plugin's repository or distribution package. Always ensure compliance with the licensing terms when using or modifying the plugin.

<sub>Made using [IntelliJ IDEA](https://www.jetbrains.com/de-de/idea/)</sub>
