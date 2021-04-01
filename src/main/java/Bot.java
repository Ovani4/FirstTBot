import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Bot extends TelegramLongPollingBot {
    BotPas bp = new BotPas();

    public static void main(String[] args) throws TelegramApiException {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            System.err.println("error creating bot");
        }
    }

    public String getBotUsername() {
        return bp.getName();
    }

    public String getBotToken() {
        return bp.getKey();
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message.getText().contains("переведи")) {
            Transliter transliter = new Transliter();
            sendMsg(message, "Перевод: " + transliter.transliteMessage(message.getText()));
        } else if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    sendMsg(message, "Стартуем!");
                    break;
                case "/stop":
                    sendMsg(message, "Остановился");
                    break;
                case "/resume": {
                    sendMsg(message, "Продолжаем ;)");
                    sendMsg(message, "И снова продолжаем");
                }
                break;
                case "Привет бот!":
                    sendMsg(message, "привет!");
                    break;
                default:
            }
        }
    }

    private void sendMsg(Message message, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(s);
        try {
            sendApiMethod(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
