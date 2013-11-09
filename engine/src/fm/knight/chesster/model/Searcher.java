package fm.knight.chesster.model;

import fm.knight.chesster.model.piece.Piece;
import com.google.common.collect.Lists;
import java.util.List;

public class Searcher {

  private final MoveGenerator moveGenerator;
  private final BoardScorer boardScorer;
  private final int searchDepth;

  class MoveWithScore {
    Move move;
    int score = Integer.MIN_VALUE;
  }

  public Searcher(MoveGenerator moveGenerator, BoardScorer boardScorer, int searchDepth) {
    this.moveGenerator = moveGenerator;
    this.boardScorer = boardScorer;
    this.searchDepth = searchDepth;
  }

  public Move findBestMove(Board board, Color whoseTurn) {
    MoveWithScore result = findBestMove(board, whoseTurn, 0, searchDepth);
    return result.move;
  }

  public MoveWithScore findBestMove(Board board, Color whoseTurn, int depth, int maxDepth) {
    MoveWithScore best = new MoveWithScore();
    for (Move move : moveGenerator.generateMoves(board, whoseTurn)) {
      board.makeMove(move);
      //      System.out.println(board.toString());
      int thisScore;
      if (depth == maxDepth) {
        thisScore = boardScorer.calculateScore(board, whoseTurn);
      } else {
        MoveWithScore negaBest = findBestMove(board, whoseTurn.flip(), depth +1, maxDepth);
        thisScore=-negaBest.score;
      }
        
      if (thisScore>best.score) {
        best.move = move;
        best.score = thisScore;
      }

      board.undo();
    }
    return best;
  }
}
