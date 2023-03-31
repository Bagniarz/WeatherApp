package weatherAppCore.menu;

import lombok.Data;
import weatherAppCore.settings.Settings;
import weatherAppCore.userInput.UserInput;

public @Data class Menu {
    private final Settings settings;
    private final UserInput input;
    private final MenuPrint printer;

    public Menu(Settings settings, UserInput userInput, MenuPrint menuPrint) {
        this.settings = settings;
        this.input = userInput;
        this.printer = menuPrint;
    }

    public void startApp() {
        boolean endApp = false;
        while(!endApp) {
            printer.print();
        }
    }
}
