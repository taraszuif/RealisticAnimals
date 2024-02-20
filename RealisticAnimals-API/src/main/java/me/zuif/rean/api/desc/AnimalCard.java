package me.zuif.rean.api.desc;

import me.zuif.rean.api.ReAnAPI;
import me.zuif.rean.api.compat.RealisticAnimal;
import me.zuif.rean.api.config.file.LocalizationConfig;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnimalCard {
    private final RealisticAnimal animal;
    private TextComponent cardMessage;

    public AnimalCard(RealisticAnimal animal) {
        this.animal = animal;
        load();
    }

    private void load() {
        List<String> sendList;
        List<TextComponent> sendFirst = new ArrayList<>();
        LocalizationConfig config = ReAnAPI.getInstance().getConfigManager().getLocalizationConfig();
        sendList = config.replaceListColorizedPlaceholder(LocalizationConfig.Message.CARD_ON_RIGHT_CLICK,
                Map.of("%age%", config.getTime(animal.getRealisticAge().getAgeInTicks()), "%gender%", animal.getGender().getName(), "%realisticname%", animal.getRealisticName()));
        for (String sl : sendList) {
            TextComponent st = new TextComponent(sl + "\n");
            String show = config.getColorizedMessage(LocalizationConfig.Message.CARD_SUGGEST_HOVER);
            st.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new Text(show))));
            st.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
                    config.replaceColorizedPlaceholder(LocalizationConfig.Message.CARD_SUGGEST_COMMAND,
                            Map.of("%realisticname%", animal.getRealisticName()))));
            sendFirst.add(st);
        }
        TextComponent finalM = new TextComponent();
        for (TextComponent message : sendFirst)
            finalM.addExtra(message);
        this.cardMessage = finalM;
    }

    public RealisticAnimal getAnimal() {
        return animal;
    }

    public TextComponent getCardMessage() {
        return cardMessage;
    }

    public void setCardMessage(TextComponent cardMessage) {
        this.cardMessage = cardMessage;
    }
}
