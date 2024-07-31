package Controller;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //localhost:8080/register
        //app.get("/register", this::postAccountHandler);
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getMessagesHandler);
        //GET localhost:8080/messages/{message_id}.
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIDHandler);
        app.get("/accounts/{account_id}/messages", this::retrieveAllMessagesForUserHandler);

        return app;
    }

    /**
     * Handler to post a new Account
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        Account addedAcc = accountService.addAccount(acc);
        if (addedAcc != null) {

            if (addedAcc.getUsername() != "") {
                if (addedAcc.getPassword().length() >= 4) {
                    if (accountService.getAccountByID(addedAcc.getAccount_id()) != null) {
                        ctx.json(mapper.writeValueAsString(addedAcc));
                        ctx.status(200);
                    } else {
                        ctx.status(400);
                    }
                } else {
                    ctx.status(400);
                }
            } else {
                ctx.status(400);
            }
        } else {
            ctx.status(400);
        }
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        Account loginAcc = accountService.getAccountByUsernameAndPassword(acc.getUsername(), acc.getPassword());
        
        if (loginAcc != null) {
            ctx.json(mapper.writeValueAsString(loginAcc));
            ctx.status(200);
        } else {
            ctx.status(401);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        Message newMsg = messageService.createMessage(msg);
        
        if (newMsg != null) {
            if (newMsg.getMessage_text() != "") {
                if (accountService.getAccountByID(newMsg.getPosted_by()) != null) {
                    //ctx.json(mapper.writeValueAsString(newMsg));
                    ctx.json(newMsg);
                    ctx.status(200);
                } else {
                    ctx.status(400);
                }
            } else {
                ctx.status(400);
            }
        } else {
            ctx.status(400);
        }
    }

    private void getMessagesHandler(Context ctx) {
        List<Message> allMsgs = messageService.getAllMessages();
        ctx.json(allMsgs);
        ctx.status(200);
    }

    private void getMessageByIDHandler(Context ctx) throws JsonProcessingException {
        Message msg = messageService.getMessageByID(Integer.parseInt(ctx.pathParam("message_id")));
        if (msg != null) 
            ctx.json(msg);
        else
            ctx.json("");
            
        ctx.status(200);
    }

    private void deleteMessageByIDHandler(Context ctx) throws JsonProcessingException {
        Message msg = messageService.getMessageByID(Integer.parseInt(ctx.pathParam("message_id")));
        if (msg != null) {
            messageService.deleteMessageByID(msg.getMessage_id());
            ctx.json(msg);
        } else {
            ctx.json("");
        }
        ctx.status(200);

    }

    private void updateMessageByIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = messageService.getMessageByID(Integer.parseInt(ctx.pathParam("message_id")));
        Message newMsg = mapper.readValue(ctx.body(), Message.class);
        if (msg == null) {
            ctx.status(400);
            return;
        }
        Message updatedMsg = messageService.updateMessageByID(msg.getMessage_id(), newMsg.getMessage_text());
        if (updatedMsg != null) {
            if (updatedMsg.getMessage_text() != "") {
                if (updatedMsg.getMessage_text().length() <= 255) {

                    ctx.json(updatedMsg);
                    ctx.status(200);
                } else {
                    ctx.status(400);
                }
            } else {
                ctx.status(400);
            }
        } else {
            ctx.status(400);
        }
    }

    private void retrieveAllMessagesForUserHandler(Context ctx) throws JsonProcessingException {
        Account acc = accountService.getAccountByID(Integer.parseInt(ctx.pathParam("account_id")));

        if (acc != null) {
            List<Message> msgs_by_acc = messageService.getAllMessagesForAccID(acc.getAccount_id());
            ctx.json(msgs_by_acc);
        } else {
            List<Message> no_msgs = new ArrayList<>();
            ctx.json(no_msgs);
        }
        ctx.status(200);
    }

}