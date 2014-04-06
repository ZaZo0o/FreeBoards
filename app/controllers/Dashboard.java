package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;

@Security.Authenticated(Secured.class)
public class Dashboard extends Controller {



static Form<User> userForm = form(User.class);
static Form<Board> boardForm = form(Board.class);

public static Result index() {
  return ok(
	index.render(
		Board.all(),
		boardForm,
		User.find.byId(request().username())
	)
  );
}

public static Result myAccount() {
  return ok(
	myAccount.render(
	  User.find.byId(request().username())
	)
  );
}

  public static Result users() {
  return ok(
    users.render(
		User.all(),
		userForm,
	  User.find.byId(request().username())
		)
  );
  }
  
  public static Result newUser() {
  Form<User> filledForm = userForm.bindFromRequest();
  if(filledForm.hasErrors()) {
    return badRequest(
      users.render(User.all(), filledForm, 	  User.find.byId(request().username()))
    );
  } else {
    User.create(filledForm.get());
    return redirect(routes.Dashboard.users());  
  }
  }
  

}

