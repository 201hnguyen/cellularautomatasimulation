package config;

import java.io.File;

public class XMLGameParser extends XMLParser {

    private static final String VALID_GAME_TAG = "game";

    public XMLGameParser(File file) {
        super(file);
        if (!isValidFile(VALID_GAME_TAG)) {
            // TODO: Notify user that this is invalid file
        }
    }

    public String getTitle() {
        return super.getRoot().getAttribute("title");
    }

    public int getSceneWidthFull() {
        return Integer.parseInt(super.getRoot().getElementsByTagName("scene_width_full").item(0).getTextContent());
    }

    public int getSceneWidth() {
        return Integer.parseInt(super.getRoot().getElementsByTagName("scene_width_cells").item(0).getTextContent());
    }

    public int getSceneHeight() {
        return Integer.parseInt(super.getRoot().getElementsByTagName("scene_height").item(0).getTextContent());
    }

    private String[] getButtonHelper(String buttonTags) {
        String[] buttons = super.getRoot().getElementsByTagName(buttonTags).item(0).getTextContent()
                .trim().split("\\s+");
        for (int i=0; i<buttons.length; i++) {
            buttons[i] = buttons[i].replaceAll("/", " ");
        }
        return buttons;
    }

    public String[] getSimulationButtons() {
        return getButtonHelper("simulation_buttons");
    }

    public String[] getIntroButton() {
        return getButtonHelper("intro_buttons");
    }
}

