# Tablut AI Agent: Intelligent Strategy & Competition

An autonomous AI agent developed to compete in the "Tablut Competition" (Ashton version). The agent implements a custom decision-making engine to play the ancient Nordic board game against other AI players via a server-client architecture.

##  The Challenge
Tablut is an asymmetrical board game where the Swedish King and his guards must escape an army of Muscovites. 
**Key constraints:**
- **Real-time response:** Maximum 1 minute per move.
- **Asynchronous communication:** JSON-based socket communication with a remote referee server.
- **Resource Management:** Hard limits on memory and computational activity during the opponent's turn.

##  Agent Architecture
The agent follows a rigorous game cycle:
1. **State Perception:** Parses JSON game states from the server.
2. **Move Computation:** Implements Minimax with Alpha-Beta Pruning to determine the optimal move.
3. **Action Execution:** Communicates moves back to the server and synchronizes the updated state.

##  Technical Stack
- **Language:** Java
- **Communication:** Sockets & JSON strings.
- **Logic:** Custom implementation of game heuristics and search algorithms.
- **Tools:** Integration with the `TablutCompetition` engine and `TablutClient` abstract classes.

##  Key Features
- **Efficient State Representation:** Optimized data structures to stay within hardware memory bounds.
- **Search Optimization:** Heuristics, Bitboards].
- **Robust Networking:** Handles blocking reads and synchronized state updates to ensure zero timeouts.

##  Competition Rules
- **Victory:** 3 points per win, 1 per draw.
- **Fair Play:** One game as White, one as Black.
- **Timeout:** Strict 60-second limit enforced by the referee server.
