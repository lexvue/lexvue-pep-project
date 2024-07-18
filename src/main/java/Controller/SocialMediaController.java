package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import io.javalin.Javalin;
import io.javalin.http.Context;
import javafx.scene.control.Accordion;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //localhost:8080/register
        app.post("/register", this::postAccountHandler);

        return app;
    }

    /**
     * Handler to post a new Account
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ctx.json("sample text");
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        //Account addedAcc = 
    }


}