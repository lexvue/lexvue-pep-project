package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message msg) {
        return messageDAO.createMessage(msg);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int message_id) {
        return messageDAO.getMessageByID(message_id);
    }

    public void deleteMessageByID(int message_id) {
        messageDAO.deleteMessageByID(message_id);
    }

    public Message updateMessageByID(int message_id, String msg) {
        return messageDAO.updateMessageByID(message_id, msg);
    }

    public List<Message> getAllMessagesForAccID(int account_id) {
        return messageDAO.getAllMessagesForAccID(account_id);
    }
}
