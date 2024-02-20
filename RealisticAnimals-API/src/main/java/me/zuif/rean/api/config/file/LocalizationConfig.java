package me.zuif.rean.api.config.file;


import me.zuif.rean.api.util.TimeConverter;
import org.bukkit.ChatColor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LocalizationConfig extends AbstractConfiguration {

    public LocalizationConfig(String locale) {
        super("/locale/", locale + ".yml", true);
    }

    public String getMessage(Message message) {
        return getYamlConfiguration().getString(message.getPath());
    }

    public String getColorizedMessage(Message message) {
        return MessagesUtils.colorize(getMessage(message));
    }

    public List<String> getMessagesList(Message message) {
        return message.isList() ? getYamlConfiguration().getStringList(message.getPath()) :
                new ArrayList<>(List.of(getMessage(message)));
    }


    @Override
    public void reload() {
        super.reload();
    }

    @Override
    public void save() {
        super.save();
    }

    public List<String> getMessagesColorizedList(Message message) {
        return MessagesUtils.colorizeList(getMessagesList(message));
    }

    private String replacePlaceholder(String message, Map<String, Object> placeholderReplacement) {
        String result = message;
        for (Map.Entry<String, Object> entry : placeholderReplacement.entrySet()) {
            String replace = entry.getKey();
            Object replacement = entry.getValue();
            result = result.replaceAll(replace, String.valueOf(replacement));
        }
        return result;
    }

    public String replaceColorizedPlaceholder(Message message, Map<String, Object> placeholderReplacement) {
        return MessagesUtils.colorize(replacePlaceholder(message, placeholderReplacement));
    }

    public List<String> replaceListColorizedPlaceholder(Message message, Map<String, Object> placeholderReplacement) {
        return MessagesUtils.colorizeList(replaceListPlaceholder(message, placeholderReplacement));
    }

    public String replacePlaceholder(Message message, Map<String, Object> placeholderReplacement) {
        return replacePlaceholder(getMessage(message), placeholderReplacement);
    }

    public List<String> replaceListPlaceholder(Message message, Map<String, Object> placeholderReplacement) {
        List<String> result = new ArrayList<>();
        for (String msg : getMessagesList(message)) {
            result.add(replacePlaceholder(msg, placeholderReplacement));
        }
        return result;
    }

    public void setMessage(Message message, String newValue) {
        getYamlConfiguration().set(message.getPath(), newValue);
    }

    public String getTime(long ticks) {
        TimeConverter converter = new TimeConverter(ticks);
        double seconds = converter.convertToSeconds();

        Duration duration = Duration.ofSeconds((long) seconds);

        long years = duration.toDays() / 365;
        long months = duration.toDays() % 365 / 30;
        long days = duration.toDays() % 365 % 30;
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long secondsRemainder = duration.getSeconds() % 60;
        return getTime(years, months, days, hours, minutes, secondsRemainder);
    }

    public String getTime(long years, long months, long days, long hours, long minutes, long seconds) {
        Map<String, Object> placeholders = new HashMap<>();
        placeholders.put("%years%", years);
        placeholders.put("%month%", months);
        placeholders.put("%days%", days);
        placeholders.put("%hours%", hours);
        placeholders.put("%minutes%", minutes);
        placeholders.put("%seconds%", seconds);
        return replacePlaceholder(Message.TIME, placeholders);
    }

    public enum Message {
        NO_PERM("no-perm", false),
        TIME("time", false),
        CONSOLE_DEV_RESTART("console.dev-restart", false),
        CONSOLE_ENABLED("console.enabled", false),
        CONSOLE_DISABLED("console.disabled", false),
        CONSOLE_LOADED_COMMANDS("console.loaded-commands", false),
        CONSOLE_LOADED_LISTENERS("console.loaded-listeners", false),
        CONSOLE_RELOAD_SEVERE("console.reload-severe", false),
        CARD_ON_RIGHT_CLICK("card.on-right-click", true),
        CARD_SUGGEST_COMMAND("card.suggest-command", false),
        CARD_SUGGEST_HOVER("card.suggest-hover", false),
        COMMAND_DESC_HELP("command.desc.help", false),
        COMMAND_DESC_TOO_FAR("command.desc.too-far", false),
        COMMAND_DESC_SET_SUCCESS("command.desc.set-success", false),
        COMMAND_CONFIG_RELOADED("command.config-reloaded", false),
        COMMAND_RUN_AS_PLAYER("command.run-as-player", false),
        COMMAND_DESC_CHANGES_CANCELLED("command.desc.changes-cancelled", false),
        CARD_SHOW_CANCELLED("card.show-cancelled", false),
        COMMAND_DEBUG_ENABLED("command.debug.enabled", false),
        COMMAND_DEBUG_DISABLED("command.debug.disabled", false),
        COMMAND_DEBUG_ENABLED_YOU("command.debug.enabled-you", false),
        COMMAND_DEBUG_DISABLED_YOU("command.debug.disabled-you", false);

        private final String path;
        private final boolean list;

        Message(String path, boolean list) {
            this.path = path;
            this.list = list;
        }

        public boolean isList() {
            return list;
        }


        public String getPath() {
            return path;
        }
    }

    public static class MessagesUtils {
        /**
         * @param input
         * @return Return colorized string
         */
        public static String colorize(String input) {
            if (input != null)
                return ChatColor.translateAlternateColorCodes('&', input);
            else
                return input;
        }

        /**
         * @param lore
         * @return Return colorized string list(i use this for lore)
         */
        public static List<String> colorizeList(List<String> lore) {
            return lore.stream().map(MessagesUtils::colorize).collect(Collectors.toList());
        }
    }

}
