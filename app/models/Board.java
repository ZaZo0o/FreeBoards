package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

import play.data.validation.Constraints.*;

/**
 * User entity managed by Ebean
 */
@Entity 

public class Board extends Model {

    @Id
	public Long boardId;
    
    @Required
    public String boardTitle;

	public String boardOwnerId;
	public String boardOwnerName;

	@Lob
	@Basic(fetch = FetchType.EAGER)
	public String boardContent;
    
    // -- Queries
    
    public static Model.Finder<String,Board> find = new Model.Finder(String.class, Board.class);

    /**
     * Retrieve all users.
     */
    public static List<Board> all() {
        return find.all();
    }

    public static Board findById(Long Id) {
        return find.where().eq("boardId", Id).findUnique();
    }

	/**
	 * Create a Board.
	 */
  public static void create(Board board, String owner) {
	board.boardOwnerId = owner;
	board.boardOwnerName = User.findByEmail(owner).name;
	board.boardContent = "{}";
    board.save();
  }

	/**
	 * Update a Board.
	 */
	public static void update(Long boardId, String boardContent) {
		Board currentBoard = Board.findById(boardId);
		currentBoard.boardContent=boardContent;
		currentBoard.update();
	}


}

