OVERVIEW: A model for a two-player reversi game which uses black and white
tiles and a hexagonal board. This code can only be used for a two player game
thus it cannot be extended to having multiple players, and uses black/white tiles only.
This code also uses the axial coordinate system for its hexagonal grid where coordinates
are in the for q, r, and the center of the hexagon is (0,0).


SOURCE ORGANIZATION:
src/cs3500.reversi/controller:
- BlackTileController: src/cs3500.reversi/controller
- ReversiController: src/cs3500.reversi/controller
- WhiteTileController: src/cs3500.reversi/controller

src/cs3500.reversi/model:
- Board class: src/cs3500.reversi/model (part of the model)
- FeaturesHandlerModel: src/cs3500.reversi/model (part of the model)
- FeaturesModel: src/cs3500.reversi/model (part of the model)
- GameState: src/cs3500.reversi/model (part of the model)
- HexCoordinates: src/cs3500.reversi/model (part of the model)
- ReadOnlyReversiModel: src/cs3500.reversi/model (part of the model)
- ReversiModel: src/cs3500.reversi/model (part of the model)
- ReversiModelStandard: src/cs3500.reversi/model (part of the model)
- TurnComponent: src/cs3500.reversi/model (part of the model)

src/cs3500.reversi/player:
- PlayerColor: src/cs3500.reversi/player
- Player: SEE PLAYER.TXT in src/cs3500/reversi/player
- CapturePieceStrategy: src/cs3500.reversi/player
- CheckCornersStrategy: src/cs3500.reversi/player
- MultipleStrategies: src/cs3500.reversi/player
- Strategy: src/cs3500.reversi/player
- HumanPlayer: src/cs3500.reversi/player
- MachinePlayer: src/cs3500.reversi/player
- PlayerActions: src/cs3500.reversi/player

src/cs3500.reversi/view:
- ReversiTextView: src/cs3500.reversi/view (part of the view)
- TextView: src/cs3500.reversi/view (part of the view)
- BoardView: src/cs3500.reversi/view (part of the view)
- KeyComponent: src/cs3500.reversi/view (part of the view)
- PanelInterface: src/cs3500.reversi/view (part of the view)
- ReversiFrame: src/cs3500.reversi/view (part of the view)
- ReversiPanel: src/cs3500.reversi/view (part of the view)

src/cs3500.reversi/features:
- Features: src/cs3500.reversi/features (part of the features)
- FeaturesHandler: src/cs3500.reversi/features (part of the features)


DOCUMENTATION:
    Board class: This class represents the board for the game. The board is represented
    by its size and a hashmap from coordinates to PlayerColor, which represents tiles as specific
    coordinates. Tiles can be one of empty,
    meaning there is no tile at that coordinate, black, meaning there is a black tile at the
    coordinate, or white, meaning there is a white tile at the coordinate. The coordinate system
    for the board is the axial coordinate system, which is commonly used for hexagonal coordinate
    systems and has coordinates q, r. The board keeps track of the validity of moves and does any
    needed flipping and placing of tiles. This occurs by checking diagonals for runs of oppositely
    colored tiles sandwiched with another same colored tile, and flipping any such runs
    if they should exist. The board communicates with the model when a move is made by the model
    to determine if a move can be made and proceeds to make that move, as the board is the one
    keeping track of its state and is the one that modifies its state itself. The board is
    driven by the control flow of the model.


    GameState enum: This represents all possible game states for a model, where a state can be
    PLAYING, W_WIN, B_WIN, or TIE.


    HexCoordinates: This represents the axial coordinates for a hexagon in the board.
    The coordinates have a q component and an r component.


    ReadOnlyReversiModel: An interface for a reversi model containing all observational
    functionality for a reversi model. Does not allow any mutation of the model.

    ReversiModel: An interface for a reversi model. This interface has been included in case
    we may want to add different reversi implementations later that have different rules.
    The interface provides functionality for placing a tile on the board and passing.


    ReversiModelStandard: The model for a standard two-player reversi game which uses
    black and white tiles. The model allows black/white tiles to be placed on the board, a specific
    color to pass, and makes sure that the same color does not go more than once in a row, i.e.
    makes sure no player goes twice in a row, either by trying to place tiles multiple times, or
    by trying to do some combination of passing and placing tiles in a row. This is enforced by the
    model so that the controller for each player communicates directly with the model as opposed to
    communicating between each other, and keeps rule keeping functionality within the model,
    as opposed to allowing the player to keep track of turns. The ReversiModelStandard keeps track
    of how many times a tile of a certain color attempted a move in a round, and how many times a
    certain color has not been placed by passing for each round. To make moves with a tile,
    the model communicates with board to modify its board and determine if a tile can be placed.
    Lastly, the model keeps track of its state and updates if the states if there are no tile
    of either color has been placed in a round. This drives the control-flow of the system.

    FeaturesHandlerModel: The features class for a reversi model, which allows the model to provide
    functionality which allows the view and player to know when it is a specific player's turn,
    when a player has won the game, and forces a player to pass if they have no valid moves. If
    there are no valid moves for either player, the view will automatically update to show the
    winner or tie. This feature handler handles forcing a player to pass since it will
    immediately know when a player's turn is over, and can evaluate whether the current
    player has any valid moves.

    FeaturesModel: The interface for a model features which provides functionality for all
    possible notifications the model can publish to the view.

    TurnComponent: Provides functionality for the reversi model to have multiple features which
    it can associate with specific model commands. These commands allow the model to check the
    current player turn and for the model to determine its status and publish this information
    to the model features.


    PlayerColor enum: Represents the possible tiles at a given hexagonal coordinate in a reversi
    game board. This can be empty, black or white.

    HumanPlayer: Represents a human player in the game of reversi, where a human player is able
    to pass or make a move on the board. This functionality is implemented but this is never used
    as a player should never being using these methods directly and instead this is implemented
    through responses to key presses.

    MachinePlayer: Represents a machine player in the game of reversi, where a machine player has
    a strategy which is uses to determine where to place a tile. In addition, this provides the
    implementation for a machine player to pass its turn.

    PlayerActions: Represents the possible player actions a player can take in the game of reversi,
    where a player can either pass its turn or place a tile on the board.


    ReversiTextView: Provides functionality for a textual view of a reversi model board.
    Black tiles are represented with a "X", white tiles are represent with an "O", and empty
    coordinates are represented with an "_". Extends the generic text view interface.


    TextView: The interface for a text based view for any model.


    Player: SEE PLAYER.TXT in src/cs3500/reversi/player

    Features: interface for all possible requests from a player of the reversi game,
    where all possible requests include pass their turn or placing a tile on the board.

    FeaturesHandler: Provides functionality for handling requests and ties itself to its
    view so that it will handle any requests that deal with key inputs. In the case that the
    "t" key is pressed and a cell is highlighted, will place a tile at this location in later
    implementation. In the case the "p" key is pressed, it will pass the current player's
    turn in later implementation. For now, prints an associated message with each key press.

    Strategy: Represents an interface for all possible strategy implemented by a reversi model.
    A strategy finds the most strategic move for a strategy, and makes a move for that
    strategy.

    MultipleStrategies: Allows for an n number of strategies to be combined and implemented
    until a strategic move is found.

    CheckCornersStrategic: Finds a move in a corner of the board if any such move is possible.
    If multiple corners are available, chooses the uppermost-leftmost coordinate. If no such move
    exists, finds the highest value move available. If no moves are left at all, returns an
    empty move.

    CapturePieceStrategy: Finds the move that captures the highest number of moves possible for
    a given player. If there are multiple such moves that capture the highest number of moves,
    chooses the uppermost-leftmost coordinate. If no such moves exist, returns an empty move,
    which forces the player to pass.

    BoardView: Provides functionality for the frame view of a reversi model, where the
    frame is able to repaint itself, add key press functionality, determine the most recently
    highlighted cell on the board, and stop allowing user clicks(which will be used to enforce
    turns).

    KeyComponent: Provides functionality for the frame to associate specific key commands
    with action commands. Ensures that the only action commands that a player can expect from
    the game are having the ability to pass their move or to place a tile on the board.

    PanelInterface: Represents the panel interface for a game of reversi, where a panel is able to
    draw the current state of the board and handle mouse clicks.

    ReversiFrame: Represents the frame view for a game of reversi and provides functionality for
    a user to interact with the view using key presses, where a key press of "t" would place a tile
    on the board, and a key press of "p" would pass a player's turn. Also provides functionality to
    allow the view to display messages based on the current state of the game.

    ReversiPanel: Represents the panel of a reversi game, where a panel represents the reversi game
    board. The game board is visually represented as a hexagonal board made up of hexagonal cells,
    where each cell is on the axial coordinate system. The cells contain a white circle if there is
    currently a white tile played on that cell, a black circle if there is currently a black tile
    played on that cell, and is empty if there is nothing played on that cell. The panel allows
    users to click on any given cell in the hexagonal board, and can deselect that cell by
    clicking on it again, clicking on another cell, or clicking outside the boundary of the board.

    Note about strategy transcript: the strategy will NOT check the originally placed tiles;
    therefore, the transcript contains all the possible coordinates, excluding the ones that
    have tiles in them when the game starts (e.g. 0, -1).

 
 // TO PLAY THE GAME:
 - The game takes command line arguments to configure the players.
 - The arguments are as follows:
    "human" --> a human player
    "corner" --> ai player that uses corner strategy
    "capture" --> ai player which uses the capture strategy
    "both" --> ai player which uses both capture and corner strategy
