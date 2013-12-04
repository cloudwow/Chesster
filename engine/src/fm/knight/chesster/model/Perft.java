package fm.knight.chesster.model;


import java.util.List;


public class Perft {
  MoveGenerator moveGenerator = new MoveGenerator();

  public int countLeaves(Board board,
      int maxDepth) {

    Color whoseTurn = board.getWhoseTurn();

    return countLeaves(board, whoseTurn, maxDepth);
  }

  private int countLeaves(
                          Board board,
                          Color whoseTurn,
                          int maxDepth) {
    int result = 0;
    List<Move> moves = moveGenerator.generateMoves(board, whoseTurn);

    if (maxDepth == 1) {
      result += moves.size();
    } else {
      for (Move move : moves) {
        board.makeMove(move);
        result += countLeaves(board, whoseTurn.flip(), maxDepth - 1);
        board.undo();
      }
    }
    return result;
  }
}
