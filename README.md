# Console Conqueror Documentation

## Overview

Console Conqueror is a multiplayer card game with client-server architecture. Players connect to a server, join or create game rooms, configure cards with various abilities, and engage in turn-based combat using different strategies.

## Architecture

The system follows a client-server architecture with the following key components:

1. **Server Side**
   - Server.java - Main server that listens for connections and manages game rooms
   - ClientHandler.java - Handles individual client connections
   - GameRoom.java - Represents a game room where players interact

2. **Client Side**
   - Client.java - Connects to the server and manages communication
   - GUI Components:
     - InitFrame.java - Initial screen
     - LobbyFrame.java - Room selection/creation
     - GameFrame.java - Main game interface

3. **Game Logic**
   - Cards system (`Card.java`, TypeOfCard.java, Weapon.java)
   - Command pattern implementation for game actions
   - Strategy pattern for attack strategies

## How to Play

### Starting the Game

1. Start the server by running Server.java
2. Start the client by running Client.java
3. Enter your username in the initial screen
4. Choose to create a new room or join an existing one

### Game Flow

1. The host can start the game with the `/start` command
2. Players configure their cards (4 cards per player)
3. Turn-based gameplay begins, with players taking actions using commands
4. Players can attack opponents, recharge weapons, or use special abilities

### Commands

| Command | Description |
|---------|-------------|
| `/start` | Start the game (host only) |
| `/attack <target> <cardName> <weapon> [strategy]` | Attack another player |
| `/rechargeWeapons` | Refill all weapons for use |
| `/endTurn` | End your current turn |
| `/skipturn` | Skip your turn |
| `/surrender` | Give up and exit the game |
| `/draw` | Request a draw |
| `/say <message>` | Send a message to all players |
| `/exit` | Leave the game room |

## Card System

Each card has:
- A type (FIRE, WATER, AIR, etc.)
- A name
- An image
- 5 weapons with different attack values
- Life points (starting at 100)

### Attack Strategies

The game implements several attack strategies:
- `RandomDuplex` - Doubles a random attack value
- `RandomCombination` - Combines with a random card for maximum effect
- `BestCombination` - Uses best values from all cards
- `Average` - Uses average values from all cards
- `Optimal` - Uses maximum values for each type

## Networking

The game uses Java socket programming for network communication:
- Server listens on port 8060
- Messages are passed as plain text
- Chat messages and commands are distinguished by format

## Configuration

Card weapons are configured with random values between ranges defined in config.txt:
- `minValue` - Minimum attack value
- `maxValue` - Maximum attack value

## User Interface

The game offers a graphical user interface with:
- Chat system for communication
- Card display area
- Command input area
- Status displays showing player statistics

## Command Pattern Implementation

Game actions use the Command pattern:
- `CommandManager` - Manages available commands
- `Command` interface - Base for all commands
- Specific command implementations (Attack, Surrender, etc.)

## Future Enhancements

Potential areas for improvement:
- Enhanced card visualization
- More complex strategies
- Improved game balance
- Additional card types and weapons
- Rankings and persistent statistics

## Development Environment

- Java 22
- Maven for dependency management
- Swing for GUI components

## Project Structure

The project follows a package structure with:
- `org.abno` - Base package
- `org.abno.logic` - Game logic
- `org.abno.gui` - User interface components
- `org.abno.socket` - Networking code