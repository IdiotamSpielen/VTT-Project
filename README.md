# Open VTT

![Kotlin](https://img.shields.io/badge/Kotlin-1.9-purple) ![Compose](https://img.shields.io/badge/Jetpack%20Compose-Desktop-blue) ![License](https://img.shields.io/badge/License-MIT-green)

**Current Status:** *Active Development (Alpha)*

A lightweight, **local-first** Virtual Tabletop (VTT) designed primarily for Dungeons and Dragons 5E.  
Unlike web-based VTTs, this application runs natively on your desktop, ensuring full control over your data without subscriptions or external servers.

------------------------------------------------------

## Key Features

### Database Backed Library

Powered by an SQLite backend with Exposed ORM

- Create and manage Abilities, Items, Characters, and much more.
- Import your maps and Tokens via a simple Drag-and-Drop.

### Tabletop

- A full-fledged layer system with up to five map and token layers (NOT FULLY IMPLEMENTED)
- Move maps and Tokens via simple Drag-and-Drop
- State persistence over sessions (PLANNED)

### Character Sheet Management

- Create and edit character sheets for PCs and NPCs
- Integrated references to your library
- Note there is no preloaded content or copyrighted characters provided.

------------------------------------------------------

## Tech Stack

For anyone interested in contributing:
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose for Desktop
- **Database:** SQLite (via Exposed Framework)
- **Media:** Native image loading / (Planned) VLCj for Audio

------------------------------------------------------

## Getting Started

### Compatibility

### Prerequisites
- **Java 17+** (Required for running the JAR/Compose runtime).
   - [Download JDK](https://adoptium.net/)

### Installation

1. Download the latest release `.jar` or installer.
2. Run the application.
3. **Zero-Config Database:** The application will automatically generate a local `vtt.db` file in your Documents folder (`~/Documents/VTT/`) upon first launch.

### OS Compatibility
- **Windows:** Primary support.
- **Linux/macOS:** Supported via JVM (Experimental).

------------------------------------------------------

## Roadmap (Nice-to-Have)

- **Music Player:** Integrated audio control (VLC based) for local playlists.
- **Dice Roller:** 3D or 2D physics-based dice rolling.
- **Random Generators:** Names, Loot, and Encounters.

## Contribute

Contributions are always welcome! Follow these steps:

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes.
4. Open a Pull Request.

------------------------------------------------------

## Disclaimer

This software is distributed under the **MIT-License**.  
As such it is free to use, modify, and redistribute. However:

- I am **not affiliated** with Wizards of the Coast or any other entity.
- The software **does not** include preloaded content (no SRD data, no copyrighted images).
- Users are **solely responsible** for any copyrighted material (maps, music, spell descriptions) imported into the app.
- All data is stored locally in a private database on the user's machine; **No data is transmitted to external servers.**

*I do not condone the unlawful distribution of copyrighted materials. Use responsibly.*
