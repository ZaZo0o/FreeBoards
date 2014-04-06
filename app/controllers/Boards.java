package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import play.mvc.BodyParser;
import play.libs.Json;
import play.libs.Json.*;
import static play.libs.Json.toJson;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.node.ObjectNode;

import models.*;
import views.html.*;

@Security.Authenticated(Secured.class)
public class Boards extends Controller {

static Form<Board> boardForm = form(Board.class);


  public static Result newBoard() {
  Form<Board> filledForm = boardForm.bindFromRequest();
  if(filledForm.hasErrors()) {
    return badRequest(
      index.render(
		Board.all(),
		boardForm,
		User.find.byId(request().username())
	)
    );
  } else {
    Board.create(filledForm.get(), User.find.byId(request().username()).email);
    return redirect(routes.Dashboard.index());  
  }
  }
  

  public static Result board(Long boardId, String boardTitle) {
  return ok(
    board.render(
		Board.findById(boardId),
		User.find.byId(request().username())
		)
  );
  }

	public static Result updateBoard(Long boardId) {
		JsonNode boardContent = request().body().asJson();
		String boardContentString=boardContent.toString();
		Board.update(boardId, boardContentString);
		return ok();
	}


}
